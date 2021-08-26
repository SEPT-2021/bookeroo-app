import React from "react";
import { render } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import Login from ".";

test("renders learn react link", () => {
  const { container } = render(
    <QueryClientProvider client={new QueryClient()}>
      <Login />
    </QueryClientProvider>
  );
  expect(container).toMatchSnapshot();
});
