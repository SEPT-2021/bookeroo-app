import React from "react";
import { Button, ButtonProps, CircularProgress } from "@material-ui/core";

function LoadingButton({ loading, ...rest }: LoadingButtonProps) {
  return (
    <Button disabled={loading} {...rest}>
      {loading ? (
        <CircularProgress size={24} color="secondary" />
      ) : (
        rest.children
      )}
    </Button>
  );
}

interface LoadingButtonProps extends ButtonProps {
  loading?: boolean;
}

export default LoadingButton;
