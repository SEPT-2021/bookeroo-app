import React from "react";
import { render } from "@testing-library/react";
import Link from ".";

test("renders internal link", () => {
  const { container } = render(<Link to="/example-page">Example</Link>);
  expect(container).toMatchSnapshot();
});
