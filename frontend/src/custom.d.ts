import type {
  UseMutationOptions,
  UseMutationResult,
} from "react-query/types/react/types";
import type {
  MutationFunction,
  MutationKey,
} from "react-query/types/core/types";
import type { AxiosError } from "axios";

export * from "react-query";
// We are using axios, so we can permanently set the error type to the axios one
declare module "react-query" {
  // Copied from useMutation.d.ts, and overriding.
  export declare function useMutation<
    TError = AxiosError,
    TData = unknown,
    TVariables = void,
    TContext = unknown
  >(
    options: UseMutationOptions<TData, TError, TVariables, TContext>
  ): UseMutationResult<TData, TError, TVariables, TContext>;
  export declare function useMutation<
    TError = AxiosError,
    TData = unknown,
    TVariables = void,
    TContext = unknown
  >(
    mutationFn: MutationFunction<TData, TVariables>,
    options?: UseMutationOptions<TData, TError, TVariables, TContext>
  ): UseMutationResult<TData, TError, TVariables, TContext>;
  export declare function useMutation<
    TError = AxiosError,
    TData = unknown,
    TVariables = void,
    TContext = unknown
  >(
    mutationKey: MutationKey,
    options?: UseMutationOptions<TData, TError, TVariables, TContext>
  ): UseMutationResult<TData, TError, TVariables, TContext>;
  export declare function useMutation<
    TError = AxiosError,
    TData = unknown,
    TVariables = void,
    TContext = unknown
  >(
    mutationKey: MutationKey,
    mutationFn?: MutationFunction<TData, TVariables>,
    options?: UseMutationOptions<TData, TError, TVariables, TContext>
  ): UseMutationResult<TData, TError, TVariables, TContext>;
}
