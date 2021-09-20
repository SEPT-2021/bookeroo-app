import React from "react";
import ReactDOM from "react-dom";
import { render, cleanup } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import LoadingButton from "../../util/LoadingButton";
import BookSearchType from "./index";

afterEach(cleanup);
describe("Rendering page BookSearchType", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <QueryClientProvider client={new QueryClient()}>
        <BookSearchType />
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

describe("BookSearchType matches snapshot", () => {
  test("render BookSearchType", () => {
    const { container } = render(
      <QueryClientProvider client={new QueryClient()}>
        <BookSearchType />
      </QueryClientProvider>
    );
    expect(container).toMatchSnapshot();
  });
});
