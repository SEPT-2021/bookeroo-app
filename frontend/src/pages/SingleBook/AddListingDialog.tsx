import React, { useEffect, useState } from "react";
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  SelectProps,
} from "@material-ui/core";
import { FormControl } from "@mui/material";
import { useMutation, useQueryClient } from "react-query";
import { BookCondition, BookItemType } from "../../util/types";
import LoadingButton from "../../util/LoadingButton";
import FormField from "../../util/FormField";
import { addBook } from "../../util/api";

function AddListingDialog({ book }: { book: BookItemType }) {
  const [open, setOpen] = useState(false);
  const [price, setPrice] = useState("");
  const [condition, setCondition] = useState(BookCondition.NEW);
  const handleChangeBookCondition: SelectProps["onChange"] = (event) => {
    setCondition((event.target.value as BookCondition) || BookCondition.NEW);
  };
  const client = useQueryClient();
  const { mutate, isLoading, isSuccess } = useMutation(addBook, {
    onSuccess: () => {
      client.invalidateQueries("singleBook");
    },
  });
  const handleCloseCondition = (
    event: React.SyntheticEvent<unknown>,
    reason?: string
  ) => {
    if (reason !== "backdropClick") {
      setOpen(false);
    }
  };
  // close on success add listing
  useEffect(() => {
    if (isSuccess) setOpen(false);
  }, [isSuccess]);

  return (
    <Box>
      <Button variant="contained" color="primary" onClick={() => setOpen(true)}>
        Add Listing
      </Button>
      <Dialog disableEscapeKeyDown open={open} onClose={handleCloseCondition}>
        <DialogTitle>Fill the form</DialogTitle>
        <DialogContent>
          <Box component="form" sx={{ display: "flex", flexWrap: "wrap" }}>
            <FormControl sx={{ m: 1, minWidth: 200 }}>
              <InputLabel id="demo-dialog-select-label">
                Book Condition
              </InputLabel>
              <Select
                labelId="demo-dialog-select-label"
                id="demo-dialog-select"
                value={condition}
                onChange={handleChangeBookCondition}
                input={<OutlinedInput label="Book Condition" />}
              >
                <MenuItem value="">
                  <em>None</em>
                </MenuItem>
                <MenuItem value="NEW">New</MenuItem>
                <MenuItem value="FINE">Fine</MenuItem>
                <MenuItem value="VERY_GOOD">Very Good</MenuItem>
                <MenuItem value="FAIR">Fair</MenuItem>
                <MenuItem value="POOR">Poor</MenuItem>
              </Select>
            </FormControl>
            <FormField
              onChange={setPrice}
              value={price}
              label="Price"
              name="price"
            />
          </Box>
          <LoadingButton
            loading={isLoading}
            onClick={() =>
              mutate({ ...book, price, condition, category: book.bookCategory })
            }
            variant="contained"
            color="primary"
          >
            Add Listing
          </LoadingButton>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseCondition}>Close</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}

export default AddListingDialog;
