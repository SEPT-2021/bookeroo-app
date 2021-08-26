import React from "react";
import { render } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import Login from ".";

jest.mock("../../util/Link", () => "mock-Link");
jest.mock("../../util/LoadingButton", () => "mock-LoadingButton");
jest.mock("../../util/FormField", () => "mock-FormField");

test("renders login", () => {
  const { container } = render(
    <QueryClientProvider client={new QueryClient()}>
      <Login />
    </QueryClientProvider>
  );
  expect(container).toMatchSnapshot();
});
