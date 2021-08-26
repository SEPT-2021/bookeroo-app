import React from "react";
import { render } from "@testing-library/react";
import Register from ".";

test("renders register page", () => {
  const { container } = render(<Register />);
  expect(container).toMatchSnapshot();
});
