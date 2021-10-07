import React from "react";
import ReactDOM from "react-dom";
import { render, cleanup } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import AboutUs from "./index";

afterEach(cleanup);
describe("Rendering page About page", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <QueryClientProvider client={new QueryClient()}>
        <AboutUs />
      </QueryClientProvider>,
      div
    );
    expect(div).toMatchSnapshot();
  });
});

describe("AboutUs matches snapshot", () => {
  test("render AboutUs", () => {
    const { container } = render(
      <QueryClientProvider client={new QueryClient()}>
        <AboutUs />
      </QueryClientProvider>
    );
    expect(container).toMatchSnapshot();
  });
});
