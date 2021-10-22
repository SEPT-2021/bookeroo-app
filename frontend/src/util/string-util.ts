// eslint-disable-next-line import/prefer-default-export
export const snakeCaseToNormalString = (str: string) =>
  str
    .split("_")
    .map(
      (word) => word.charAt(0).toUpperCase() + word.substring(1).toLowerCase()
    )
    .join(" ");
