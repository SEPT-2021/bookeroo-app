import * as React from "react";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { useQuery } from "react-query";
import { viewListings } from "../../util/api";

const columns: GridColDef[] = [
  { field: "id", headerName: "ID", width: 70 },
  { field: "price", headerName: "Price", width: 130 },
  { field: "bookCondition", headerName: "Book Condition", width: 130 },
];

export default function ViewListings() {
  const { data, isLoading, error } = useQuery("orders", viewListings);

  console.log(data);

  const rows = [{ id: 1, price: 11, bookCondition: "NEW" }];

  return (
    <div style={{ height: 400, width: "100%" }}>
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
