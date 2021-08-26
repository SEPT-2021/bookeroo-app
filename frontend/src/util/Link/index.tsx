import React from "react";
import { LinkProps, Link as MuiLink } from "@material-ui/core";
import {
  Link as RouterLink,
  LinkProps as RouterLinkProps,
} from "react-router-dom";

function Link(props: Omit<LinkProps, "component"> & RouterLinkProps) {
  return <MuiLink variant="body2" {...props} component={RouterLink} />;
}

export default Link;
