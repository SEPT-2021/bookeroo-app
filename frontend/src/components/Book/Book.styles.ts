import styled from "styled-components";

// eslint-disable-next-line import/prefer-default-export
export const Wrapper = styled.div`
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  width: 100%;
  border: 1px solid black;
  border-radius: 20px;
  height: 100%;
  margin-top: 80px;

  button {
    border-radius: 0 0 20px 20px;
    background-color: black;
    color: white;
    font-size: 20px;
    padding: 10px 60px;
    margin: 10px 0;
    cursor: pointer;

    &:disabled {
      color: grey;
      opacity: 0.7;
      cursor: default;
    }
  }

  img {
    max-height: 450px;
    object-fit: contain;
    border-radius: 20px 20px 0 0;
    background-color: #eef;
  }

  div {
    font-family: Arial, Helvetica, sans-serif;
    padding: 1rem;
    height: 100%;
  }
`;
