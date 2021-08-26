import React from "react";
import { CssBaseline, ThemeProvider } from "@material-ui/core";
import { QueryClient, QueryClientProvider } from "react-query";
import { BrowserRouter, Redirect, Route, Switch } from "react-router-dom";
import ReactDOM from "react-dom";
import theme from "./theme";

import App from "./pages/App";
import Login from "./pages/Login";
import Register from "./pages/Register";
import RegisterSuccess from "./pages/Register/RegisterSuccess";
import RegisterFailed from "./pages/Register/RegisterFailed";
import LoginSuccess from "./pages/Login/LoginSuccess";
import reportWebVitals from "./reportWebVitals";

const queryClient = new QueryClient();

ReactDOM.render(
  <React.StrictMode>
    <CssBaseline />
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <BrowserRouter>
          <Switch>
            <Route path="/app">
              <App />
            </Route>
            <Route path="/api/users/register">
              <Register />
            </Route>
            <Route path="/api/users/registerSuccess">
              <RegisterSuccess />
            </Route>
            <Route path="/api/users/registerFailed">
              <RegisterFailed />
            </Route>
            <Route path="/api/users/login">
              <Login />
            </Route>
            <Route path="/api/users/loginSuccess">
              <LoginSuccess />
            </Route>
            <Redirect to="/api/users/login" />
          </Switch>
        </BrowserRouter>
      </ThemeProvider>
    </QueryClientProvider>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
