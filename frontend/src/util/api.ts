import axios, { AxiosResponse } from "axios";
import {
  AddEditBookType,
  BookCondition,
  BookItemType,
  CartType,
  GetBookType,
  LoginType,
  PaymentTokenType,
  RegisterAsSellerType,
  RegisterUserType,
  ReviewBookType,
  SingleBookType,
  TokenProps,
  TransactionType,
  UpdateUserType,
  User,
  ViewListingsType,
} from "./types";

export const api = axios.create({});
const backendUrl = process.env.REACT_APP_BACKEND;

function getRouteURL(
  service:
    | "books"
    | "listings"
    | "users"
    | "orders"
    | "admins"
    | "newsletter"
    | "sellers",
  route: string
) {
  const port = (() => {
    switch (service) {
      case "users":
      case "newsletter":
        return 8080;
      case "books":
      case "listings":
        return 8081;
      case "orders":
        return 8082;
      case "admins":
        return 8083;
      case "sellers":
        return 8084;
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
  RegisterUserType,
  TokenProps & { user: User }
>((args) => api.post(getRouteURL("users", "register"), args));

export const loginUser = makeTypedAPICall<
  LoginType,
  TokenProps & { user: User }
>((args) => api.post(getRouteURL("users", "login"), args));

export const updateUser = makeTypedAPICall<
  UpdateUserType,
  TokenProps & { user: User }
>((args) => api.put(getRouteURL("users", "update"), args));

export const addBook = makeTypedAPICall<
  AddEditBookType & {
    price: string;
    condition: string;
    coverFile?: File | unknown;
  },
  unknown
>((args) => {
  const data = new FormData();
  data.append("title", args.title);
  data.append("author", args.author);
  data.append("pageCount", args.pageCount);
  data.append("isbn", args.isbn);
  data.append("price", args.price);
  data.append("condition", args.condition);
  data.append("category", args.category);
  data.append("description", args.description);
  data.append("coverFile", args.coverFile as File);

  return api.post(getRouteURL("books", "add"), data, {
    headers: {
      "Content-Type": `multipart/form-data`,
    },
  });
});
export const editBook = makeTypedAPICall<
  AddEditBookType & { id: string; cover?: string },
  unknown
>(({ title, author, category, id, description, isbn, pageCount }) => {
  return api.put(getRouteURL("books", id), {
    title,
    author,
    category,
    description,
    isbn,
    pageCount,
  });
});

export const reviewBook = makeTypedAPICall<ReviewBookType, unknown>(
  ({ text, rating, id }) =>
    api.post(getRouteURL("books", `${id}/review`), { text, rating })
);

export const findBookById = makeTypedAPICall<SingleBookType, BookItemType>(
  (args) => api.get(getRouteURL("books", args.id))
);

export const deleteBookById = makeTypedAPICall<SingleBookType, unknown>(
  (args) => api.delete(getRouteURL("books", args.id))
);

export const deleteListingById = makeTypedAPICall<{ id: string }, unknown>(
  ({ id }) => api.delete(getRouteURL("listings", id))
);
export const addListing = makeTypedAPICall<
  { bookId: string; price: string; condition: BookCondition },
  unknown
>((args) => api.post(getRouteURL("listings", "add"), args));

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

export const getBookByType = makeTypedAPICall<GetBookType, unknown>((args) =>
  api.get(getRouteURL("books", `?search=${args.searchTerm}&type=${args.type}`))
);

export const getAllBooks = makeTypedAPICall<unknown, BookItemType[]>(() =>
  api.get(getRouteURL("books", "all"))
);

export const getAllBooksCSV = makeTypedAPICall<unknown, any>(() =>
  api.get(getRouteURL("books", "all"))
);

export const profile = makeTypedAPICall<unknown, User>(() =>
  api.get(getRouteURL("users", "profile"))
);

export const checkout = makeTypedAPICall<CartType, string>((args) =>
  api.post(getRouteURL("orders", "checkout"), args)
);

export const paymentCapture = makeTypedAPICall<PaymentTokenType, unknown>(
  (args) => api.post(getRouteURL("orders", `capture/${args.token}`))
);

export const getAllUsers = makeTypedAPICall<unknown, User[]>(() =>
  api.get(getRouteURL("admins", "inspect-users"))
);

export const getAllUsersCSV = makeTypedAPICall<unknown, any>(() =>
  api.get(getRouteURL("admins", "inspect-users"))
);

export const banUnBanUser = makeTypedAPICall<
  { userId: number | undefined },
  unknown
>((args) => api.post(getRouteURL("admins", `toggle-ban/${args.userId}`)));

export const approveSeller = makeTypedAPICall<
  { userId: number | undefined },
  unknown
>((args) => api.post(getRouteURL("admins", `approve-seller/${args.userId}`)));

export const rejectSeller = makeTypedAPICall<
  { userId: number | undefined },
  unknown
>((args) => api.post(getRouteURL("admins", `reject-seller/${args.userId}`)));

export const deleteUserByID = makeTypedAPICall<
  { userId: number | undefined },
  unknown
>((args) => api.delete(getRouteURL("admins", `delete-users/${args.userId}`)));

export const registerNewsletter = makeTypedAPICall<unknown, unknown>((args) =>
  api.post(getRouteURL("newsletter", "register"), args)
);

export const registerSeller = makeTypedAPICall<RegisterAsSellerType, unknown>(
  (args) => api.post(getRouteURL("sellers", "register"), args)
);

export const getSellers = makeTypedAPICall<unknown, User[]>(() =>
  api.get(getRouteURL("admins", "inspect-sellers"))
);

export const viewTransactions = makeTypedAPICall<unknown, TransactionType[]>(
  () => api.get(getRouteURL("orders", "transactions"))
);

export const viewListings = makeTypedAPICall<unknown, ViewListingsType[]>(() =>
  api.get(getRouteURL("listings", "user"))
);

export const deleteUser = makeTypedAPICall<unknown, unknown>(() =>
  api.delete(getRouteURL("users", ""))
);

export const deleteListing = makeTypedAPICall<{ id: string }, unknown>((args) =>
  api.post(getRouteURL("listings", `/${args.id}`))
);

export const requestRefund = makeTypedAPICall<{ listingId: string }, unknown>(
  (args) => api.post(getRouteURL("orders", `refund/${args.listingId}`))
);
