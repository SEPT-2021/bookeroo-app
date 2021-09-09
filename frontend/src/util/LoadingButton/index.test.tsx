import React from "react";
import { render } from "@testing-library/react";
import LoadingButton from ".";

test("renders loading", () => {
  const { container } = render(<LoadingButton loading>Example</LoadingButton>);
  expect(container).toMatchSnapshot();
});

test("renders default", () => {
  const { container } = render(<LoadingButton>Example</LoadingButton>);
  expect(container).toMatchSnapshot();
});
