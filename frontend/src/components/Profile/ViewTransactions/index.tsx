import React from "react";
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

  const { mutate: refundMutate, isLoading: isRefundLoading } = useMutation(
    requestRefund,
    {
      onSuccess: () => {
        client.invalidateQueries("orders");
      },
    }
  );

  const rows =
    data?.map((row) => ({
      ...row,
      id: `${row.id?.listingId} ${row.id?.buyerId}`,
      listingId: row.id?.listingId,
      buyerId: row.id?.buyerId,
      listing: `${row.listing?.id} ${row.listing?.price} ${row.listing?.bookCondition}, ${row.listing?.available}`,
      price: row.listing?.price,
      bookCondition: row.listing?.bookCondition,
      available: row.listing?.available,
    })) || [];

  const columns: GridColDef[] = [
    {
      field: "listingId",
      headerName: "Listing ID",
      width: 150,
    },
    {
      field: "buyerId",
      headerName: "Buyer ID",
      width: 150,
    },
    {
      field: "price",
      headerName: "Price",
      width: 110,
    },
    {
      field: "available",
      headerName: "Availability",
      width: 150,
    },
    {
      field: "bookCondition",
      headerName: "Book Condition",
      width: 170,
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
      renderCell: ({ row }: GridRenderCellParams) => (
        <IconButton
          onClick={() => refundMutate({ listingId: row.listingId })}
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
