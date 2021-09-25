import React, { useCallback, useEffect, useState } from "react";
import {
  Avatar,
  CircularProgress,
  Container,
  IconButton,
  List,
  ListItem,
  ListItemAvatar,
  ListItemSecondaryAction,
  ListItemText,
  Typography,
} from "@material-ui/core";
import { Delete, Person } from "@material-ui/icons";
import { useMutation } from "react-query";
import FormGroup from "@mui/material/FormGroup";
import { Button } from "@mui/material";
import Stack from "@mui/material/Stack";
import { banUserByID, getAllUsers, unbanUserByID } from "../../util/api";
import { User } from "../../components/GlobalContext";

function UserRow({
  user,
  loading,
  onDelete,
}: {
  user: User;
  loading?: boolean;
  onDelete: () => void;
}) {
  const refreshPage = async () => {
    await window.location.reload();
  };

  const { mutate: banMutate, isSuccess: banIsSuccess } =
    useMutation(banUserByID);
  const banUser = async (userId: number) => {
    banMutate({ userId });
    if (banIsSuccess) {
      await refreshPage();
    }
  };

  const { mutate: unBanMutate, isSuccess: unbanIsSuccess } =
    useMutation(unbanUserByID);
  const unbanUser = async (userId: number) => {
    unBanMutate({ userId });
    if (unbanIsSuccess) {
      await refreshPage();
    }
  };

  const handleEnabled = async () => {
    await banUser(user.id);
  };

  const handledDisabled = async () => {
    await unbanUser(user.id);
  };

  const renderButton = () => {
    return (
      <Stack direction="row" spacing={2}>
        <Button variant="contained" color="error" onClick={handledDisabled}>
          Banned
        </Button>
        <Button variant="contained" color="success" onClick={handleEnabled}>
          Un-Ban
        </Button>
      </Stack>
    );
  };

  return (
    <ListItem divider>
      <ListItemAvatar>
        <Avatar>
          <Person />
        </Avatar>
      </ListItemAvatar>
      <ListItemText
        primary={`${user.firstName} ${user.lastName}`}
        secondary={
          <>
            <Typography variant="body2">
              {user.username} - {user.roles}
            </Typography>
            <Typography variant="body2">
              Enabled Status: {String(user.enabled)}
            </Typography>
          </>
        }
      />
      <FormGroup>{renderButton()}</FormGroup>
      <ListItemSecondaryAction>
        <IconButton onClick={onDelete}>
          {loading ? (
            <CircularProgress color="secondary" size={20} />
          ) : (
            <Delete />
          )}
        </IconButton>
      </ListItemSecondaryAction>
    </ListItem>
  );
}

function UsersList() {
  const [sampleUsers, setSampleUsers] = useState<User[]>();

  const { data, mutate, isSuccess } = useMutation(getAllUsers);

  const loadAllUsers = useCallback(() => {
    if (!isSuccess) mutate({ data });
    setSampleUsers(data);
  }, [data, isSuccess, mutate]);

  useEffect(() => {
    loadAllUsers();
  }, [loadAllUsers]);

  return (
    <Container style={{ marginTop: 100 }}>
      <Typography variant="h3" align="center" style={{ margin: "20px 0" }}>
        Manage Users
      </Typography>
      <List>
        {sampleUsers?.map((user) => (
          <UserRow
            user={user}
            key={user.id}
            onDelete={() =>
              console.log(`Implement delete! Deleting ${user.id}`)
            }
          />
        ))}
      </List>
    </Container>
  );
}

export default UsersList;
