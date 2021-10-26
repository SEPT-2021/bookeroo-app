import React from "react";
import ReactDOM from "react-dom";
import { cleanup } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import Component from ".";

afterEach(cleanup);
describe("Rendering page UserDetails", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <QueryClientProvider client={new QueryClient()}>
        <Component />
      </QueryClientProvider>,
      div
    );
    expect(div).toMatchSnapshot();
  });
});
