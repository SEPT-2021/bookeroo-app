import React from "react";
import { LinearProgress } from "@material-ui/core";

export default function LinearLoading() {
  return (
    <LinearProgress
      style={{ top: "80px", position: "absolute", width: "100vw" }}
    />
  );
}
