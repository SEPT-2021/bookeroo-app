import React from "react";
import { render } from "@testing-library/react";
import Login from ".";

test("renders learn react link", () => {
  const { container } = render(<Login />);
  expect(container).toMatchSnapshot();
});
