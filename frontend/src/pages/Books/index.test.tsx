import React from "react";
import ReactDOM from "react-dom";
import { render, cleanup } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import LoadingButton from "../../util/LoadingButton";
import Books from "./index";

afterEach(cleanup);
describe("Rendering page Books", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <QueryClientProvider client={new QueryClient()}>
        <Books />
      </QueryClientProvider>,
      div
    );
    expect(div).toMatchSnapshot();
  });
});

describe("LoadingButton render matches snapshot", () => {
  test("renders button", () => {
    const button = render(<LoadingButton />);
    expect(button).toMatchSnapshot();
  });
});

describe("Books matches snapshot", () => {
  test("render Books", () => {
    const { container } = render(
      <QueryClientProvider client={new QueryClient()}>
        <Books />
      </QueryClientProvider>
    );
    expect(container).toMatchSnapshot();
  });
});
