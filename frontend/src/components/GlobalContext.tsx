import React, { createContext, FC, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { api, profile } from "../util/api";
import { TokenProps } from "../util/types";

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
  const {
    data: userData,
    refetch,
    remove,
    isError,
  } = useQuery("user", profile);
  // On token change, update the header and refetch user.
  useEffect(() => {
    if (token) {
      api.defaults.headers.Authorization = `Bearer ${token}`;
      refetch();
    } else {
      delete api.defaults.headers.Authorization;
      remove();
    }
  }, [refetch, remove, token]);
  // On startup, get token from local storage
  useEffect(() => {
    setToken(localStorage.getItem("token") || undefined);
  }, []);
  const signout = () => {
    localStorage.removeItem("token");
    setToken(undefined);
  };
  // Clear token if error
  useEffect(() => {
    if (isError) {
      signout();
    }
  }, [isError]);
  return (
    <GlobalContext.Provider
      value={{
        user: token ? userData : undefined,
        login: (data) => {
          localStorage.setItem("token", data.jwt as string);
          setToken(data.jwt);
        },
        signout,
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};
