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
