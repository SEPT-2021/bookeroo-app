import React from "react";
import ReactDOM from "react-dom";
import { render, cleanup } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import Contact from "./index";

afterEach(cleanup);
describe("Rendering page Contact", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <QueryClientProvider client={new QueryClient()}>
        <Contact />
      </QueryClientProvider>,
      div
    );
    expect(div).toMatchSnapshot();
  });
});

describe("Contact matches snapshot", () => {
  test("render DeleteBook", () => {
    const { container } = render(
      <QueryClientProvider client={new QueryClient()}>
        <Contact />
      </QueryClientProvider>
    );
    expect(container).toMatchSnapshot();
  });
});
