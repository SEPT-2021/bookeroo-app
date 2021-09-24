import React from "react";
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

// TODO make sure this matches API response
interface User {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  role: string;
  enabled: boolean;
}

function UserRow({
  user,
  loading,
  onDelete,
}: {
  user: User;
  loading?: boolean;
  onDelete: () => void;
}) {
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
              {user.username} - {user.role}
            </Typography>
            <Typography variant="body2">
              Enabled: {String(user.enabled)}
            </Typography>
          </>
        }
      />
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
  // const {user: currentUser} = useContext(GlobalContext);
  const sampleUsers: User[] = [
    {
      id: 1,
      enabled: true,
      firstName: "Mike",
      lastName: "Hunt",
      username: "AdminBoy",
      // TODO this would need to be cleaned from ROLE_ADMIN to just Admin
      role: "Admin",
    },
    {
      id: 2,
      enabled: false,
      firstName: "Nia",
      lastName: "Dilodaly",
      username: "PlebBoy",
      role: "User",
    },
  ];
  return (
    <Container style={{ marginTop: 100 }}>
      <Typography variant="h3" align="center" style={{ margin: "20px 0" }}>
        Manage Users
      </Typography>
      <List>
        {sampleUsers.map((user) => (
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
