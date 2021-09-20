import React from "react";
import ReactDOM from "react-dom";
import { render, cleanup } from "@testing-library/react";
import { Button, CircularProgress } from "@material-ui/core";

afterEach(cleanup);
describe("Rendering page CircularProgress", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(<CircularProgress />, div);
    expect(div).toMatchSnapshot();
  });
});

describe("Button render matches snapshot", () => {
  test("renders button", () => {
    const button = render(<Button />);
    expect(button).toMatchSnapshot();
  });
});
