import React from "react";
import ReactDOM from "react-dom";
import { cleanup } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import { BrowserRouter } from "react-router-dom";
import EditBook from "./index";

afterEach(cleanup);
describe("Rendering page EditBook", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <BrowserRouter>
        <QueryClientProvider client={new QueryClient()}>
          <EditBook />
        </QueryClientProvider>
      </BrowserRouter>,
      div
    );
    expect(div).toMatchSnapshot();
  });
});
