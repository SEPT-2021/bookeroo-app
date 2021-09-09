import axios, { AxiosResponse } from "axios";

const api = axios.create({});
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
    confirmPassword: string;
  },
  unknown
>((args) => api.post("/api/users/register", args));

export const loginUser = makeTypedAPICall<
  { username: string; password: string },
  unknown
>((args) => api.post("/api/users/login", args));

export const addBook = makeTypedAPICall<
  {
    title: string;
    author: string;
    isbn: string;
    pageCount: string;
  },
  unknown
>((args) => api.post("api/books/add", args));

export const findBookById = makeTypedAPICall<
  {
    id: string;
  },
  unknown
>((args) => api.get(`api/books/${args.id}`));

export const deleteBookById = makeTypedAPICall<
  {
    id: string;
  },
  unknown
>((args) => api.delete(`api/books/${args.id}`));

export const getBookBySearchTerm = makeTypedAPICall<
  {
    searchTerm: string;
  },
  unknown
  >((args) => api.get(`api/books/?search=${args.searchTerm}`));