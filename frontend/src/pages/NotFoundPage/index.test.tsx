import React from "react";
import ReactDOM from "react-dom";
import { render, cleanup } from "@testing-library/react";
import { Button } from "@material-ui/core";
import { QueryClient, QueryClientProvider } from "react-query";
import NotFoundPage from "./index";

afterEach(cleanup);
describe("Rendering page NotFoundPage", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(<NotFoundPage />, div);
    expect(div).toMatchSnapshot();
  });
});

describe("Button render matches snapshot", () => {
  test("renders button", () => {
    const button = render(<Button />);
    expect(button).toMatchSnapshot();
  });
});

describe("NotFoundPage matches snapshot", () => {
  test("render NotFoundPage", () => {
    const { container } = render(
      <QueryClientProvider client={new QueryClient()}>
        <NotFoundPage />
      </QueryClientProvider>
    );
    expect(container).toMatchSnapshot();
  });
});
