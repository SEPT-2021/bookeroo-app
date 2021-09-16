import axios, { AxiosResponse } from "axios";

export const api = axios.create({});
const backendUrl = process.env.REACT_APP_BACKEND;

function getRouteURL(service: "books" | "users", route: string) {
  const port = (() => {
    switch (service) {
      case "users":
        return 8080;
      case "books":
        return 8081;
      default:
        throw new Error(`No port for service: ${service}`);
    }
  })();
  return `${backendUrl}:${port}/api/${service}/${route}`;
}

export interface TokenProps {
  success: boolean;
  token: string;
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
  unknown
>((args) => api.post(getRouteURL("users", "register"), args));

export const loginUser = makeTypedAPICall<
  { username: string; password: string },
  TokenProps
>((args) => api.post(getRouteURL("users", "login"), args));

export const addBook = makeTypedAPICall<
  {
    title: string;
    author: string;
    isbn: string;
    pageCount: string;
  },
  unknown
>((args) => api.post(getRouteURL("books", "add"), args));

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

export const getAllBooks = makeTypedAPICall(() =>
  api.get(getRouteURL("books", "all"))
);
