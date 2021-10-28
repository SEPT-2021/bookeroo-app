import React from "react";
import ReactDOM from "react-dom";
import { cleanup, render } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import { BrowserRouter } from "react-router-dom";
import CheckOut from "./index";
import LoadingButton from "../../util/LoadingButton";
import { GlobalContextProvider } from "../../components/GlobalContext";

afterEach(cleanup);
describe("Rendering page CheckOut", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <BrowserRouter>
        <QueryClientProvider client={new QueryClient()}>
          <GlobalContextProvider>
            <CheckOut />
          </GlobalContextProvider>
        </QueryClientProvider>
      </BrowserRouter>,
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
