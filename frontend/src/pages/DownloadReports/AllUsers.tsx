import { useQuery } from "react-query";
import { CSVLink } from "react-csv";
import * as React from "react";
import { getAllUsersCSV } from "../../util/api";

export default function DownloadAllUsers() {
  const { data: GetAllUsers } = useQuery("admins", getAllUsersCSV);

  return (
    <div>
      <h1>Click Below to Download Data</h1>
      <CSVLink data={GetAllUsers} filename="AllUsers.csv">
        Download All Users
      </CSVLink>
    </div>
  );
}
