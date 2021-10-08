import React, { useEffect, useState } from "react";
import {
  Avatar,
  CircularProgress,
  Container,
  FormControlLabel,
  IconButton,
  LinearProgress,
  List,
  ListItem,
  ListItemAvatar,
  ListItemSecondaryAction,
  ListItemText,
  Typography,
} from "@material-ui/core";
import { Check, Clear, Delete, Person } from "@material-ui/icons";
import { useMutation, useQuery } from "react-query";
import FormGroup from "@mui/material/FormGroup";
import { styled, Switch } from "@mui/material";
import Stack from "@mui/material/Stack";
import {
  approveSeller,
  banUnBanUser,
  deleteUserByID,
  getAllUsers,
  rejectSeller,
} from "../../util/api";
import { Role, User } from "../../components/GlobalContext";

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
  onChange,
  mode,
}: {
  user: User;
  onChange?: () => void;
  mode: "banUnban" | "approveReject";
}) {
  const [switchChecked, setSwitchChecked] = useState<boolean>(user.enabled);
  const mutateOptions = {
    onSuccess: onChange,
  };
  const { mutate: unBanMutate, isLoading: isBanUnbanLoading } = useMutation(
    banUnBanUser,
    mutateOptions
  );
  const { mutate: deleteUserMutate, isLoading: isDeleteLoading } = useMutation(
    deleteUserByID,
    mutateOptions
  );
  const { mutate: approveSellerMutate, isLoading: isApproveSellerLoading } =
    useMutation(approveSeller, mutateOptions);

  const { mutate: rejectSellerMutate, isLoading: isRejectSellerLoading } =
    useMutation(rejectSeller, mutateOptions);

  const isLoading =
    isDeleteLoading ||
    isBanUnbanLoading ||
    isApproveSellerLoading ||
    isRejectSellerLoading;
  const isBanMode = mode === "banUnban";
  const userAction = async (
    action: "approve" | "reject" | "banUnban" | "delete"
    // eslint-disable-next-line consistent-return
  ) => {
    const args = { userId: user.id };
    // eslint-disable-next-line default-case
    switch (action) {
      case "approve":
        return approveSellerMutate(args);
      case "reject":
        return rejectSellerMutate(args);
      case "banUnban":
        return unBanMutate(args);
      case "delete":
        return deleteUserMutate(args);
    }
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
              {user.username} - {user.role}
            </Typography>
          </>
        }
      />
      <div style={{ marginRight: 16 }}>
        <FormGroup aria-label="position" row>
          <Stack direction="row" spacing={3}>
            {isBanMode ? (
              <FormGroup aria-label="position" row>
                <FormControlLabel
                  control={
                    <OnOffSwitch
                      checked={switchChecked}
                      onChange={() => {
                        userAction("banUnban").then(() =>
                          setSwitchChecked(!switchChecked)
                        );
                      }}
                    />
                  }
                  label=" "
                />
              </FormGroup>
            ) : (
              <IconButton onClick={() => userAction("approve")}>
                {isLoading ? (
                  <CircularProgress color="secondary" size={20} />
                ) : (
                  <Check />
                )}
              </IconButton>
            )}
          </Stack>
        </FormGroup>
      </div>

      <ListItemSecondaryAction>
        <IconButton onClick={() => userAction(isBanMode ? "delete" : "reject")}>
          {/* eslint-disable-next-line no-nested-ternary */}
          {isLoading ? (
            <CircularProgress color="secondary" size={20} />
          ) : isBanMode ? (
            <Delete />
          ) : (
            <Clear />
          )}
        </IconButton>
      </ListItemSecondaryAction>
    </ListItem>
  );
}

function UsersList() {
  const [sellers, setSellers] = useState([] as User[]);
  const {
    data: sampleUsers,
    isLoading,
    error,
    refetch,
  } = useQuery("users", getAllUsers);
  useEffect(() => {
    setSellers(sampleUsers?.filter((u) => u.role !== Role.ROLE_SELLER) || []);
  }, [sampleUsers]);

  if (isLoading) {
    return <LinearProgress style={{ marginTop: "80px" }} />;
  }
  if (error) {
    return <div style={{ marginTop: "80px" }}>Failed to load data</div>;
  }
  return (
    <Container style={{ marginTop: 100 }}>
      <Typography variant="h5">Ban users</Typography>
      <List>
        {sampleUsers?.length ? (
          sampleUsers.map((user) => (
            <UserRow
              user={user}
              key={user.id}
              onChange={refetch}
              mode="banUnban"
            />
          ))
        ) : (
          <Typography>No accounts exist at this time</Typography>
        )}
      </List>
      <Typography variant="h5" style={{ marginTop: 40 }}>
        Approve/Reject Sellers
      </Typography>
      <List>
        {sellers.length ? (
          sellers?.map((user) => (
            <UserRow
              user={user}
              key={user.id}
              mode="approveReject"
              onChange={() => {
                setSellers(sellers.filter((s) => s.id !== user.id));
                refetch();
              }}
            />
          ))
        ) : (
          <Typography>No new sellers at this time</Typography>
        )}
      </List>
    </Container>
  );
}

export default UsersList;
