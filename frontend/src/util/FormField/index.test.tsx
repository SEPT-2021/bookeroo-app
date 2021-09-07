import React from "react";
import { render } from "@testing-library/react";
import FormField from ".";

test("renders normal", () => {
  const { container } = render(<FormField name="test" />);
  expect(container).toMatchSnapshot();
});
test("renders with other error", () => {
  const { container } = render(
    <FormField name="test" errors={{ other: "No Error" }} />
  );
  expect(container).toMatchSnapshot();
});
test("renders with real error", () => {
  const { container } = render(
    <FormField name="test" errors={{ test: "actual error" }} />
  );
  expect(container).toMatchSnapshot();
});
