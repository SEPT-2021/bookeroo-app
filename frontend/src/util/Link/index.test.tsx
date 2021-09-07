import React from "react";
import { render } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import Link from ".";

test("renders internal link", () => {
  const { container } = render(
    <BrowserRouter>
      <Link to="/example-page">Example</Link>
    </BrowserRouter>
  );
  expect(container).toMatchSnapshot();
});
