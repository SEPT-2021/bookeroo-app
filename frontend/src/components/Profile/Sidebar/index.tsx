import * as React from "react";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { useContext } from "react";
import RegisterAsASeller from "../RegisterAsASeller";
import UserDetails from "../UserDetails";
import DeleteUser from "../DeleteUser";
import ViewTransactions from "../ViewTransactions";
import ViewListings from "../ViewListings";
import { GlobalContext } from "../../GlobalContext";
import { Role } from "../../../util/types";
import DownloadAllUsers from "../DownloadReports/AllUsers";
import DownloadAllBooks from "../DownloadReports/AllBooks";

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`vertical-tabpanel-${index}`}
      aria-labelledby={`vertical-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

function a11yProps(index: number) {
  return {
    id: `vertical-tab-${index}`,
    "aria-controls": `vertical-tabpanel-${index}`,
  };
}

export default function VerticalTabs() {
  const { user } = useContext(GlobalContext);
  const [value, setValue] = React.useState(0);

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  return (
    <Box
      sx={{
        flexGrow: 1,
        bgcolor: "background.paper",
        display: "flex",
        height: 300,
      }}
    >
      <Tabs
        orientation="vertical"
        variant="scrollable"
        value={value}
        onChange={handleChange}
        aria-label="Vertical tabs example"
        sx={{ borderRight: 1, borderColor: "divider" }}
      >
        <Tab label="Edit Profile" {...a11yProps(0)} />
        <Tab label="Register As A Seller" {...a11yProps(1)} />
        <Tab label="View Transactions" {...a11yProps(2)} />
        <Tab label="View Listings" {...a11yProps(3)} />
        <Tab label="Delete Account" {...a11yProps(4)} />
        {user?.role === Role.ROLE_ADMIN && (
          <Tab label="Download All Users" {...a11yProps(5)} />
        )}
        {user?.role === Role.ROLE_ADMIN && (
          <Tab label="Download All Books" {...a11yProps(6)} />
        )}
      </Tabs>
      <TabPanel value={value} index={0}>
        <UserDetails />
      </TabPanel>
      <TabPanel value={value} index={1}>
        <RegisterAsASeller />
      </TabPanel>
      <TabPanel value={value} index={2}>
        <ViewTransactions />
      </TabPanel>
      <TabPanel value={value} index={3}>
        <ViewListings />
      </TabPanel>
      <TabPanel value={value} index={4}>
        <DeleteUser />
      </TabPanel>
      {user?.role === Role.ROLE_ADMIN && (
        <TabPanel value={value} index={5}>
          <DownloadAllUsers />
        </TabPanel>
      )}
      {user?.role === Role.ROLE_ADMIN && (
        <TabPanel value={value} index={6}>
          <DownloadAllBooks />
        </TabPanel>
      )}
    </Box>
  );
}
