import React, { createContext, FC, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { api, profile, TokenProps } from "../util/api";

export interface User {
  createdAt: string;
  enabled: boolean;
  firstName: string;
  id: number;
  lastName: string;
  password: string;
  roles: "ROLE_USER" | "ROLE_ADMIN";
  updatedAt: string | null;
  username: string;
}

interface GlobalContextType {
  user?: User;

  login(data: TokenProps): void;

  signout(): void;
}

export const GlobalContext = createContext<GlobalContextType>({} as never);

export const GlobalContextProvider: FC<unknown> = ({ children }) => {
  const [token, setToken] = useState<string>();
  const { data: userData, refetch } = useQuery("user", profile);
  // On token change, update the header and refetch user.
  useEffect(() => {
    if (token) {
      api.defaults.headers.Authorization = `Bearer ${token}`;
      refetch();
    } else {
      api.defaults.headers.Authorization = undefined;
    }
  }, [refetch, token]);
  // On startup, get token from local storage
  useEffect(() => {
    setToken(localStorage.getItem("token") || undefined);
  }, []);

  return (
    <GlobalContext.Provider
      value={{
        user: userData,
        login: (data) => {
          localStorage.setItem("token", data.jwt as string);
          setToken(data.jwt);
        },
        signout: () => {
          localStorage.removeItem("token");
          setToken(undefined);
        },
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};
