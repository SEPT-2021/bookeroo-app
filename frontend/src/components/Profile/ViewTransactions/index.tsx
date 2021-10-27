import React, { useState } from "react";
import {
  Box,
  CircularProgress,
  createStyles,
  Grid,
  IconButton,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { DataGrid, GridColDef, GridRenderCellParams } from "@mui/x-data-grid";
import SendIcon from "@material-ui/icons/Send";
import { requestRefund, viewTransactions } from "../../../util/api";
import theme from "../../../theme";
import { snakeCaseToNormalString } from "../../../util/string-util";

function ViewTransactions({ classes }: ViewTransactionsProps) {
  const { data, isLoading } = useQuery("orders", viewTransactions);
  const client = useQueryClient();

  const [availability, setAvailability] = useState("");

  const { mutate: refundMutate, isLoading: isRefundLoading } = useMutation(
    requestRefund,
    {
      onSuccess: () => {
        client.invalidateQueries("orders");
      },
    }
  );

  const rows = data || [];

  const columns: GridColDef[] = [
    {
      field: "listingsId",
      headerName: "Listing ID",
      width: 150,
      valueGetter: (params) => {
        let result = [];
        if (params?.row?.id?.listingId) {
          if (params.row.id.listingId) {
            result.push(`${params.row.id.listingId}`);
          }
        } else {
          result = ["No Data"];
        }
        return result.join(", ");
      },
    },
    {
      field: "buyerId",
      headerName: "Buyer ID",
      width: 150,
      valueGetter: (params) => {
        let result = [];
        if (params?.row?.id?.buyerId) {
          result.push(`${params.row.id.buyerId}`);
        } else {
          result = ["No Data"];
        }
        return result.join(", ");
      },
    },
    {
      field: "id",
      headerName: "ID",
      width: 90,
      valueGetter: (params) => {
        let result = [];
        if (params?.row?.listing?.id) {
          result.push(`${params?.row?.listing?.id}`);
        } else {
          result = ["No Data"];
        }
        return result.join(", ");
      },
    },
    {
      field: "price",
      headerName: "Price",
      width: 110,
      valueGetter: (params) => {
        let result = [];
        if (params?.row?.listing?.price) {
          result.push(`${params.row.listing.price}`);
        } else {
          result = ["No Data"];
        }
        return result.join(", ");
      },
    },
    {
      field: "available",
      headerName: "Availability",
      width: 150,
      valueGetter: (params) => {
        let result = [];
        if (params?.row?.listing?.available) {
          result.push(`${params.row.listing.available}`);
        } else {
          result = ["No Data"];
        }
        return result.join(", ");
      },
    },
    {
      field: "bookCondition",
      headerName: "Book Condition",
      width: 170,
      valueGetter: (params) => {
        let result = [];
        if (params?.row?.listing?.bookCondition) {
          result.push(`${params.row.listing.bookCondition}`);
        } else {
          result = ["No Data"];
        }
        return result.join(", ");
      },
      valueFormatter: ({ value }) =>
        typeof value === "string" ? snakeCaseToNormalString(value) : value,
    },
    { field: "orderId", headerName: "Order Id", width: 170 },
    { field: "captureId", headerName: "Capture ID", width: 170 },
    { field: "refundable", headerName: "Refundable", width: 160 },

    {
      field: "Request For Refund",
      headerName: "Request For Refund",
      width: 250,
      valueGetter: (params) => {
        if (params?.row?.listing?.available) {
          setAvailability(`${params.row.listing.available}`);
        } else {
          setAvailability("No Data");
        }
        return availability;
      },
      renderCell: ({ row }: GridRenderCellParams) => (
        <IconButton
          onClick={() => refundMutate({ listingId: row.listing.id })}
          disabled={!row.refundable || isRefundLoading}
        >
          {isRefundLoading ? (
            <CircularProgress color="secondary" size={20} />
          ) : (
            <SendIcon />
          )}
        </IconButton>
      ),
    },
  ];
  if (isLoading) {
    return (
      <>
        <p>Loading...</p>
        <CircularProgress />
      </>
    );
  }
  return (
    <Grid container component="main" className={classes.root}>
      <Grid container spacing={2} justifyContent="center">
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <form className={classes.box} noValidate autoComplete="off">
            <Box>
              <div style={{ height: 400, width: "80vw" }}>
                <DataGrid
                  rows={rows}
                  columns={columns}
                  pageSize={5}
                  rowsPerPageOptions={[5]}
                  checkboxSelection
                />
              </div>
            </Box>
          </form>
        </div>
      </Grid>
    </Grid>
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

type ViewTransactionsProps = WithStyles<typeof styles>;

export default withStyles(styles)(ViewTransactions);
