import React from "react";
import { CssBaseline, ThemeProvider } from "@material-ui/core";
import { QueryClient, QueryClientProvider } from "react-query";
import { ReactQueryDevtools } from "react-query/devtools";
import { BrowserRouter, Redirect, Route, Switch } from "react-router-dom";
import ReactDOM from "react-dom";
import theme from "./theme";
import Login from "./pages/Login";
import Register from "./pages/Register";
import RegisterSuccess from "./pages/Register/RegisterSuccess";
import RegisterFailed from "./pages/Register/RegisterFailed";
import LoginSuccess from "./pages/Login/LoginSuccess";
import reportWebVitals from "./reportWebVitals";
import BookSearch from "./pages/BookSearch";
import AddBook from "./pages/AddBook";
import AddBookSuccess from "./pages/AddBook/AddBookSuccess";
import DeleteBook from "./pages/DeleteBook";
import BookSearchType from "./pages/BookSearchType";

const queryClient = new QueryClient();

ReactDOM.render(
  <React.StrictMode>
    <CssBaseline />
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools />
      <ThemeProvider theme={theme}>
        <BrowserRouter>
          <Switch>
            <Route path="/register">
              <Register />
            </Route>
            <Route path="/registerSuccess">
              <RegisterSuccess />
            </Route>
            <Route path="/registerFailed">
              <RegisterFailed />
            </Route>
            <Route path="/login">
              <Login />
            </Route>
            <Route path="/loginSuccess">
              <LoginSuccess />
            </Route>
            <Route path="/search">
              <BookSearch />
            </Route>
            <Route path="/searchType">
              <BookSearchType />
            </Route>
            <Route path="/addBook">
              <AddBook />
            </Route>
            <Route path="/addBookSuccess">
              <AddBookSuccess />
            </Route>
            <Route path="/deleteBook">
              <DeleteBook />
            </Route>
            <Redirect to="/login" />
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
