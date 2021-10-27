import React from "react";
import ReactDOM from "react-dom";
import { render, cleanup } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import DeleteBook from "./index";
import LoadingButton from "../../../util/LoadingButton";

afterEach(cleanup);
describe("Rendering page DeleteBook", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <QueryClientProvider client={new QueryClient()}>
        <DeleteBook />
      </QueryClientProvider>,
      div
    );
    expect(div).toMatchSnapshot();
  });
});

describe("LoadingButton render matches snapshot", () => {
  test("renders LoadingButton", () => {
    const button = render(<LoadingButton />);
    expect(button).toMatchSnapshot();
  });
});

describe("DeleteBook matches snapshot", () => {
  test("render DeleteBook", () => {
    const { container } = render(
      <QueryClientProvider client={new QueryClient()}>
        <DeleteBook />
      </QueryClientProvider>
    );
    expect(container).toMatchSnapshot();
  });
});
