import React from "react";
import { cleanup, render } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import Login from ".";
import LoadingButton from "../../util/LoadingButton";
import FormField from "../../util/FormField";

jest.mock("../../util/Link", () => "mock-Link");
jest.mock("../../util/LoadingButton", () => "mock-LoadingButton");
jest.mock("../../util/FormField", () => "mock-FormField");

afterEach(cleanup);
describe("LoadingButton render matches snapshot", () => {
  test("renders LoadingButton", () => {
    const loadingButton = render(<LoadingButton />);
    expect(loadingButton).toMatchSnapshot();
  });
});

describe("FormField username render matches snapshot", () => {
  test("renders FormField username", () => {
    const formField = render(<FormField name="username" />);
    expect(formField).toMatchSnapshot();
  });
});

describe("FormField password render matches snapshot", () => {
  test("renders FormField password", () => {
    const formField = render(<FormField name="password" />);
    expect(formField).toMatchSnapshot();
  });
});

describe("LoginPage matches snapshot", () => {
  test("renders LoginPage", () => {
    const { container } = render(
      <QueryClientProvider client={new QueryClient()}>
        <Login />
      </QueryClientProvider>
    );
    expect(container).toMatchSnapshot();
  });
});
