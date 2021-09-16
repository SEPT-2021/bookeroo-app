import React, { createContext, FC, useEffect, useState } from "react";
import { api, TokenProps } from "../util/api";

interface User {
  // TODO
  x: number;
}

interface GlobalContextType {
  user?: User;

  login(data: TokenProps): void;
}

export const GlobalContext = createContext<GlobalContextType>({} as never);

export const GlobalContextProvider: FC<unknown> = ({ children }) => {
  const [user, setUser] = useState<User>();
  const [token, setToken] = useState<string>();
  useEffect(() => {
    api.defaults.headers.Authorization = localStorage.getItem("token");
  }, [token]);

  return (
    <GlobalContext.Provider
      value={{
        user,
        login: (data) => {
          localStorage.setItem("token", data.token as string);
          setToken(data.token);
        },
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};
