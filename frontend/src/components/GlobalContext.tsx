import React, { createContext, FC, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { api, TokenProps } from "../util/api";

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
}

export const GlobalContext = createContext<GlobalContextType>({} as never);

export const GlobalContextProvider: FC<unknown> = ({ children }) => {
  const [user, setUser] = useState<User>();
  const [token, setToken] = useState<string>();
  const getUser = useQuery("user");
  useEffect(() => {
    api.defaults.headers.Authorization = localStorage.getItem("token");
  }, [token]);
  useEffect(() => {});

  return (
    <GlobalContext.Provider
      value={{
        user,
        login: (data) => {
          localStorage.setItem("token", data.jwt as string);
          setToken(data.jwt);
        },
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};
