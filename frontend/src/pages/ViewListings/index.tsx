import * as React from "react";
import { DataGrid, GridColDef, GridRenderCellParams } from "@mui/x-data-grid";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Delete } from "@material-ui/icons";
import {
  CircularProgress,
  createStyles,
  IconButton,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import { deleteListingById, viewListings } from "../../util/api";
import { snakeCaseToNormalString } from "../../util/string-util";
import theme from "../../theme";

function ViewListings({ classes }: ViewListingsProps) {
  const { data } = useQuery("orders", viewListings);

  const rows = data || [];

  const client = useQueryClient();
  const { mutate: deleteMutate, isLoading } = useMutation(deleteListingById, {
    onSuccess: () => {
      client.invalidateQueries("orders");
    },
  });

  const columns: GridColDef[] = [
    { field: "id", headerName: "ID", width: 90 },
    { field: "price", headerName: "Price", width: 110 },
    { field: "userFullName", headerName: "Full Name", width: 250 },
    {
      field: "bookCondition",
      headerName: "Book Condition",
      width: 175,
      valueFormatter: ({ value }) =>
        typeof value === "string" ? snakeCaseToNormalString(value) : value,
    },
    {
      field: "Delete Listing",
      headerName: "Delete Listing",
      width: 175,
      renderCell: (val: GridRenderCellParams) => (
        <IconButton onClick={() => deleteMutate({ id: val.row.id })}>
          {isLoading ? (
            <CircularProgress color="secondary" size={20} />
          ) : (
            <Delete />
          )}
        </IconButton>
      ),
    },
  ];

  return (
    <div style={{ height: 400, width: "450%" }} className={classes.root}>
      <DataGrid
        rows={rows}
        columns={columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
        checkboxSelection
      />
    </div>
  );
}

const styles = () =>
  createStyles({
    root: {
      height: "30vh",
    },
    box: {
      "& > *": {
        margin: theme.spacing(1),
        width: "25ch",
      },
    },
  });

type ViewListingsProps = WithStyles<typeof styles>;

export default withStyles(styles)(ViewListings);
