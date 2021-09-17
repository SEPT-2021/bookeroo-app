import axios, { AxiosResponse } from "axios";
import type { User } from "../components/GlobalContext";
import { BookItemType, CartType, TokenProps } from "./types";

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
    pageCount: string;
    isbn: string;
    price: string;
    description: string;
    coverFile: File | unknown;
  },
  unknown
>((args) => {
  const data = new FormData();
  data.append("title", args.title);
  data.append("author", args.author);
  data.append("pageCount", args.pageCount);
  data.append("isbn", args.isbn);
  data.append("price", args.price);
  data.append("description", args.description);
  data.append("coverFile", args.coverFile as File);

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
  BookItemType[]
>((args) => {
  const search = new URLSearchParams();

  search.append("search", args.searchTerm);
  return api.get(
    getRouteURL("books", search.toString() ? `?${search.toString()}` : "")
  );
});

export const getBookByType = makeTypedAPICall<
  {
    searchTerm: string;
    type: string;
  },
  unknown
>((args) =>
  api.get(getRouteURL("books", `?search=${args.searchTerm}&type=${args.type}`))
);

export const getAllBooks = makeTypedAPICall<unknown, BookItemType[]>(() =>
  api.get(getRouteURL("books", "all"))
);

export const profile = makeTypedAPICall<unknown, User>(() =>
  api.get(getRouteURL("users", "profile"))
);

export const checkout = makeTypedAPICall<CartType, string>((args) =>
  api.post(getRouteURL("orders", "checkout"), args)
);

export const paymentCapture = makeTypedAPICall<
  {
    token: string;
  },
  unknown
>((args) => api.post(getRouteURL("orders", `capture/${args.token}`)));
