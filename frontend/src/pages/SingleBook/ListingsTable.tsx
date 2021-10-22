import React, { useContext } from "react";
import { DataGrid, GridColDef, GridRenderCellParams } from "@mui/x-data-grid";
import { Check, Clear, Delete, ShoppingCartOutlined } from "@material-ui/icons";
import { CircularProgress, IconButton } from "@material-ui/core";
import { useMutation, useQueryClient } from "react-query";
import { snakeCaseToNormalString } from "../../util/string-util";
import { GlobalContext } from "../../components/GlobalContext";
import { BookItemType, Listing, Role } from "../../util/types";
import { deleteListingById } from "../../util/api";

export default function ListingsTable({
  listings,
  book,
}: {
  listings: Listing[];
  book: BookItemType;
}) {
  const { addToCart, user, cartItems } = useContext(GlobalContext);
  const client = useQueryClient();
  const { mutate: deleteMutate, isLoading } = useMutation(deleteListingById, {
    onSuccess: () => {
      client.invalidateQueries("singleBook");
    },
  });
  const listingCols: GridColDef[] = [
    { field: "id", headerName: "ID", width: 100 },
    {
      field: "userFullName",
      headerName: "Seller Name",
      width: 200,
      valueFormatter: (val) => val.value || "No Name",
    },
    {
      field: "price",
      headerName: "Price",
      width: 150,
      sortable: true,
      valueFormatter: (val) => Number(val.value),
    },
    {
      field: "bookCondition",
      headerName: "Book Condition",
      width: 200,
      valueFormatter: ({ value }) =>
        typeof value === "string" ? snakeCaseToNormalString(value) : value,
    },
    {
      field: "available",
      headerName: "Available",
      width: 150,
      renderCell: ({ value, row }) =>
        value && !cartItems.find((item) => item.listing.id === row.id) ? (
          <Check />
        ) : (
          <Clear />
        ),
    },
    {
      field: "cartBtn",
      headerName: "Add to Cart",
      width: 150,
      sortable: false,
      renderCell: ({ row }) => {
        const listing = row as Listing;
        return (
          <IconButton
            onClick={() => addToCart({ book, listing })}
            disabled={!!cartItems.find((item) => item.listing.id === row.id)}
          >
            <ShoppingCartOutlined />
          </IconButton>
        );
      },
    },
    ...(user?.role === Role.ROLE_ADMIN
      ? [
          {
            field: "deleteBtn",
            headerName: "Delete",
            width: 130,
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
        ]
      : []),
  ];
  return <DataGrid autoHeight columns={listingCols} rows={listings} />;
}
