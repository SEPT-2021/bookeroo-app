import React from "react";
import ReactDOM from "react-dom";
import { render, cleanup } from "@testing-library/react";
import { Button } from "@material-ui/core";
import { Router } from "@material-ui/icons";
import HomePage from "./index";
import Header from "../../components/Header";
import Footer from "../../components/Footer";

afterEach(cleanup);
describe("Header matches snapshot", () => {
  test("render Header", () => {
    const { container } = render(
      <Router>
        <Header />
      </Router>
    );
    expect(container).toMatchSnapshot();
  });
});

describe("Render component Footer", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(<Footer />, div);
    expect(div).toMatchSnapshot();
  });
});

describe("Button render matches snapshot", () => {
  test("renders button", () => {
    const button = render(<Button />);
    expect(button).toMatchSnapshot();
  });
});

describe("HomePage matches snapshot", () => {
  test("render HomePage", () => {
    const { container } = render(
      <Router>
        <HomePage />
      </Router>
    );
    expect(container).toMatchSnapshot();
  });
});
