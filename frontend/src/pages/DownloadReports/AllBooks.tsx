import * as React from "react";
import { useQuery } from "react-query";
import { CSVLink } from "react-csv";
import { getAllBooksCSV } from "../../util/api";

export default function DownloadAllBooks() {
  const { data: GetAllBooks } = useQuery("books", getAllBooksCSV);

  return (
    <div>
      <h1>Click Below to Download Data</h1>
      <CSVLink data={GetAllBooks} filename="AllBooks.csv">
        Download All Users
      </CSVLink>
    </div>
  );
}
