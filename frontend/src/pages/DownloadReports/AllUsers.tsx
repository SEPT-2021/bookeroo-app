import React from "react";
import { useQuery } from "react-query";
import { CSVLink } from "react-csv";
import { CircularProgress } from "@material-ui/core";
import { getAllUsers } from "../../util/api";

export default function DownloadAllUsers() {
  const { data, isLoading } = useQuery("admins", getAllUsers);
  if (isLoading)
    return (
      <>
        <p>Loading...</p>
        <CircularProgress />
      </>
    );

  return !data?.length ? (
    <p>No users found.</p>
  ) : (
    <div>
      <h1>Click Below to Download Data</h1>
      <CSVLink data={data} filename="AllUsers.csv">
        Download All Users
      </CSVLink>
    </div>
  );
}
