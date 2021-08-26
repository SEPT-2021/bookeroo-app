import React from "react";
import { TextField, TextFieldProps } from "@material-ui/core";

/**
 * A wrapper around TextField with easy input to a map of errors, which automatically enters an errored state if an error is present
 */
function FormField({ onChange, name, errors, ...rest }: FormFieldProps) {
  return (
    <TextField
      {...rest}
      name={name}
      error={!!errors?.[name]}
      helperText={errors?.[name]}
      variant="outlined"
      margin="normal"
      required
      fullWidth
      onChange={(e) => onChange && onChange(e.target.value)}
    />
  );
}

type FormFieldProps<T extends string = string> = Omit<
  TextFieldProps,
  "onChange"
> & {
  onChange?: (val: string) => void;
  name: T;
  // Not strict types, but we do expect name to be a possible key of errors and data
  errors?: Record<string | T, string>;
};

export default FormField;
