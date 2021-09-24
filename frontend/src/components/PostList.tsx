import React from "react";
import {
  List,
  Datagrid,
  TextField,
  DateField,
  EditButton,
  DeleteButton,
} from "react-admin";
import { UserType } from "../util/types";

const UsersList = (props: any) => {
  return (
    <List {...props}>
      <Datagrid>
        <TextField source="id" />
        <TextField source="username" />
        <TextField source="password" />
        <TextField source="firstName" />
        <TextField source="lastName" />
        <TextField source="role" />
        <TextField source="enabled" />
        <DateField source="created" />
        <DateField source="updated" />
        <EditButton basePath="/users" />
        <DeleteButton basePath="/users" />
      </Datagrid>
    </List>
  );
};

export default UsersList;
