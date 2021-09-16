import axios, { AxiosResponse } from "axios";
import type { User } from "../components/GlobalContext";
import { CartType, TokenProps } from "./types";

export const api = axios.create({});
const backendUrl = process.env.REACT_APP_BACKEND;

function getRouteURL(service: "books" | "users" | "orders", route: string) {
  const port = (() => {
    switch (service) {
      case "users":
        return 8080;
      case "books":
        return 8081;
      case "orders":
        return 8082;
      default:
        throw new Error(`No port for service: ${service}`);
    }
  })();
  return `${backendUrl}:${port}/api/${service}/${route}`;
}

/**
 * A generic powered API wrapper, allowing us to inject types into our requests
 * Currently doesn't support injecting types into the error state,
 * see: https://github.com/Microsoft/TypeScript/issues/7588#issuecomment-199079907
 * @param apiCall
 */
const makeTypedAPICall =
  <In, Out>(apiCall: (args: In) => Promise<AxiosResponse<Out>>) =>
  (args: In) =>
    apiCall(args).then((res) => res.data);

export const registerUser = makeTypedAPICall<
  {
    username: string;
    firstName: string;
    lastName: string;
    password: string;
    roles: string;
    enabled: true;
  },
  TokenProps & { user: User }
>((args) => api.post(getRouteURL("users", "register"), args));

export const loginUser = makeTypedAPICall<
  { username: string; password: string },
  TokenProps & { user: User }
>((args) => api.post(getRouteURL("users", "login"), args));

export const addBook = makeTypedAPICall<
  {
    title: string;
    author: string;
    isbn: string;
    coverUrl: string;
    description: string;
    price: number;
    pageCount: number;
  },
  unknown
>((args) => {
  const data = new FormData();
  Object.entries(args).forEach(([key, value]) => {
    data.append(key, String(value));
  });
  return api.post(getRouteURL("books", "add"), data, {
    headers: {
      "Content-Type": `multipart/form-data`,
    },
  });
});

export const findBookById = makeTypedAPICall<
  {
    id: string;
  },
  unknown
>((args) => api.get(getRouteURL("books", args.id)));

export const deleteBookById = makeTypedAPICall<
  {
    id: string;
  },
  unknown
>((args) => api.delete(getRouteURL("books", args.id)));

export const getBookBySearchTerm = makeTypedAPICall<
  {
    searchTerm: string;
  },
  unknown
>((args) => api.get(getRouteURL("books", `?search=${args.searchTerm}`)));

export const getBookByType = makeTypedAPICall<
  {
    searchTerm: string;
    type: string;
  },
  unknown
>((args) =>
  api.get(getRouteURL("books", `?search=${args.searchTerm}&type=${args.type}`))
);

export const getAllBooks = makeTypedAPICall<any, any>(() =>
  api.get(getRouteURL("books", "all"))
);

export const profile = makeTypedAPICall<unknown, User>(() =>
  api.get(getRouteURL("users", "profile"))
);

export const checkout = makeTypedAPICall<CartType, any>(() =>
  axios.get(getRouteURL("orders", "checkout"))
);
