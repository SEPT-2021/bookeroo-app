import React from "react";
import ReactDOM from "react-dom";
import { cleanup } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import EditBook from "./index";

afterEach(cleanup);
describe("Rendering page EditBook", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <QueryClientProvider client={new QueryClient()}>
        <EditBook />
      </QueryClientProvider>,
      div
    );
    expect(div).toMatchSnapshot();
  });
});
