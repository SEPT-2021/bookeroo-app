import React from "react";
import ReactDOM from "react-dom";
import { render, cleanup } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import CheckOut from "./index";
import LoadingButton from "../../util/LoadingButton";

afterEach(cleanup);
describe("Rendering page CheckOut", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <QueryClientProvider client={new QueryClient()}>
        <CheckOut />{" "}
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

describe("CheckOut matches snapshot", () => {
  test("render CheckOut", () => {
    const { container } = render(
      <QueryClientProvider client={new QueryClient()}>
        <CheckOut />
      </QueryClientProvider>
    );
    expect(container).toMatchSnapshot();
  });
});
