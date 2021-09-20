import React from "react";
import { CssBaseline, ThemeProvider } from "@material-ui/core";
import { QueryClient, QueryClientProvider } from "react-query";
import { ReactQueryDevtools } from "react-query/devtools";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import ReactDOM from "react-dom";
import theme from "./theme";
import Login from "./pages/Login";
import Register from "./pages/Register";
import reportWebVitals from "./reportWebVitals";
import HomePage from "./pages/HomePage";
import NotFoundPage from "./pages/NotFoundPage";
import BookSearch from "./pages/BookSearch";
import AddBook from "./pages/AddBook";
import BookSearchType from "./pages/BookSearchType";
import Books from "./pages/Books";
import CheckOut from "./pages/CheckOut";
import NavBar from "./components/NavBar";
import { GlobalContextProvider } from "./components/GlobalContext";
import PaymentSuccess from "./pages/Payment/success";
import PaymentFailed from "./pages/Payment/failed";

const queryClient = new QueryClient();

ReactDOM.render(
  <React.StrictMode>
    <CssBaseline />
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools />
      <ThemeProvider theme={theme}>
        <GlobalContextProvider>
          <BrowserRouter>
            <NavBar />
            <Switch>
              <Route path="/" exact>
                <HomePage />
              </Route>
              <Route path="/register">
                <Register />
              </Route>
              <Route path="/login">
                <Login />
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

              <Route path="/paymentSuccess">
                <PaymentSuccess />
              </Route>
              <Route path="/paymentFailure/">
                <PaymentFailed />
              </Route>
              <Route path="/checkOut">
                <CheckOut />
              </Route>
              <Route path="/allBooks">
                <Books />
              </Route>
              <Route component={NotFoundPage} />
            </Switch>
          </BrowserRouter>
        </GlobalContextProvider>
      </ThemeProvider>
    </QueryClientProvider>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
