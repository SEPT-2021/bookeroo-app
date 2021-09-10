import { createTheme } from "@material-ui/core/styles";
import orange from "@material-ui/core/colors/orange";
import brown from "@material-ui/core/colors/brown";
import {grey} from "@material-ui/core/colors";

// TODO produce a theme we like
const theme = createTheme({
  palette: {
    primary: {
      main: orange[500],
    },
    secondary: {
      main: brown[500],
    },
  },
  overrides: {
    MuiButton: {
      root: {
        borderRadius: 50,
      },
      label: {
        color: "white",
        textTransform: "none",
        fontWeight: "bold",
      },
    },
  },
});
export default theme;
