import { BookItemType } from "./types";

export const snakeCaseToNormalString = (str: string) =>
  str
    .split("_")
    .map(
      (word) => word.charAt(0).toUpperCase() + word.substring(1).toLowerCase()
    )
    .join(" ");

export const getBookMinPrice = (listings: BookItemType["listings"]) => {
  if (!listings?.length) return undefined;
  return listings
    ?.map((listing) => Number(listing.price))
    .reduce((a, b) => Math.min(a, b));
};
