// jest-dom adds custom jest matchers for asserting on DOM nodes.
// allows you to do things like:
// expect(element).toHaveTextContent(/react/i)
// learn more: https://github.com/testing-library/jest-dom
import "@testing-library/jest-dom";

// Could be simplified with dynamic module mocking
jest.mock("./util/Link", () => "mock-Link");
jest.mock("./util/LoadingButton", () => "mock-LoadingButton");
jest.mock("./util/LoadingButton", () => "mock-LoadingButton");
jest.mock("./util/FormField", () => "mock-FormField");
