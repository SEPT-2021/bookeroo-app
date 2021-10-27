import * as React from "react";
import { useQuery } from "react-query";
import { CSVLink } from "react-csv";
import { CircularProgress } from "@material-ui/core";
import { getAllBooks } from "../../util/api";

export default function DownloadAllBooks() {
  const { data, isLoading } = useQuery("books", getAllBooks);
  if (isLoading)
    return (
      <>
        <p>Loading...</p>
        <CircularProgress />
      </>
    );
  return !data?.length ? (
    <p>No books found.</p>
  ) : (
    <div>
      <h1>Click Below to Download Data</h1>
      <CSVLink data={data} filename="AllBooks.csv">
        Download All Books
      </CSVLink>
    </div>
  );
}
