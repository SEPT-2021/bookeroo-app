import React, { useEffect} from "react";
import { Admin, Resource} from "react-admin";
import { Grid } from "@material-ui/core";
import { useMutation } from "react-query";
import { getAllUsers } from "../../util/api";
import UsersList from "../../components/PostList";

const AdminDashBoard = () => {
  const { data, mutate , isSuccess} = useMutation(getAllUsers);

  useEffect(() => {
    mutate({ data });
  }, [data, mutate]);

  return (
    <Grid>
      <Admin dataProvider={data}>
        <Resource name="users" list={UsersList} />
      </Admin>
    </Grid>
  );
};

export default AdminDashBoard;
