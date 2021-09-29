import React, { useCallback, useEffect, useState } from "react";
import {
  Avatar,
  CircularProgress,
  Container,
  FormControlLabel,
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
import { styled, Switch } from "@mui/material";
import Stack from "@mui/material/Stack";
import { banUnBanUser, getAllUsers } from "../../util/api";
import { User } from "../../components/GlobalContext";

const OnOffSwitch = styled(Switch)(({ theme }) => ({
  padding: 8,
  "& .MuiSwitch-track": {
    borderRadius: 22 / 2,
    "&:before, &:after": {
      content: '""',
      position: "absolute",
      top: "50%",
      transform: "translateY(-50%)",
      width: 16,
      height: 16,
    },
    "&:before": {
      backgroundImage: `url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 24 24"><path fill="${encodeURIComponent(
        theme.palette.getContrastText(theme.palette.primary.main)
      )}" d="M21,7L9,19L3.5,13.5L4.91,12.09L9,16.17L19.59,5.59L21,7Z"/></svg>')`,
      left: 12,
    },
    "&:after": {
      backgroundImage: `url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 24 24"><path fill="${encodeURIComponent(
        theme.palette.getContrastText(theme.palette.primary.main)
      )}" d="M19,13H5V11H19V13Z" /></svg>')`,
      right: 12,
    },
  },
  "& .MuiSwitch-thumb": {
    boxShadow: "none",
    width: 16,
    height: 16,
    margin: 2,
  },
}));

function UserRow({
  user,
  loading,
  onDelete,
}: {
  user: User;
  loading?: boolean;
  onDelete: () => void;
}) {
  const [switchChecked, setSwitchChecked] = useState<boolean>(user.enabled);

  const { mutate: unBanMutate } = useMutation(banUnBanUser);

  const unbanUser = async (userId: number) => {
    unBanMutate({ userId });
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
          </>
        }
      />
      <div style={{ marginRight: 16 }}>
        <FormGroup aria-label="position" row>
          <Stack direction="row" spacing={3}>
            <FormGroup aria-label="position" row>
              <FormControlLabel
                labelPlacement="start"
                label="Account Status"
                control={
                  <OnOffSwitch
                    checked={switchChecked}
                    onChange={() => {
                      unbanUser(user.id).then(() =>
                        setSwitchChecked(!switchChecked)
                      );
                    }}
                  />
                }
              />
            </FormGroup>
          </Stack>
        </FormGroup>
      </div>

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
