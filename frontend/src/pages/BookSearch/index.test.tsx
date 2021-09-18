import React from "react";
import ReactDOM from "react-dom";
import { render, cleanup } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import LoadingButton from "../../util/LoadingButton";
import BookSearch from "./index";

afterEach(cleanup);
describe("Rendering page BookSearch", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <QueryClientProvider client={new QueryClient()}>
        <BookSearch />
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

describe("BookSearch matches snapshot", () => {
  test("render BookSearch", () => {
    const { container } = render(
      <QueryClientProvider client={new QueryClient()}>
        <BookSearch />
      </QueryClientProvider>
    );
    expect(container).toMatchSnapshot();
  });
});
