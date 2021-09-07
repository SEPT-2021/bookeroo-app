import { CssBaseline, ThemeProvider } from "@material-ui/core";
import React from "react";
import { BrowserRouter, Redirect, Route, Switch } from "react-router-dom";
import ReactDOM from "react-dom";
import App from "./pages/App";
import reportWebVitals from "./reportWebVitals";
import theme from "./theme";
import Login from "./pages/Login";
import PageNotfound from "./pages/PageNotFound/PageNotFound";
import HomePage from "./pages/HomePage/HomePage";

ReactDOM.render(
  <React.StrictMode>
    <CssBaseline />
    <ThemeProvider theme={theme}>
      <BrowserRouter>
        <Switch>
          <Route path="/home">
            <HomePage />
          </Route>
          <Route path="/app">
            <App />
          </Route>
          <Route path="/login">
            <Login />
          </Route>
          <Route path="">
            <PageNotfound />
          </Route>
          <Redirect to="/home" />
        </Switch>
      </BrowserRouter>
    </ThemeProvider>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
