import React from "react";
import { render } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import Register from ".";

test("renders register page", () => {
  const { container } = render(
    <QueryClientProvider client={new QueryClient()}>
      <Register />
    </QueryClientProvider>
  );
  expect(container).toMatchSnapshot();
});
