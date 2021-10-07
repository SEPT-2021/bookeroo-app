import React, { useContext, useState } from "react";
import { useQuery } from "react-query";
import { Box, Container, LinearProgress } from "@material-ui/core";
import styled from "styled-components";
import DrawerCart from "../../components/DrawerCart";
import { getBookBySearchTerm } from "../../util/api";
import BookList from "../../components/BookList";
import SearchBar from "../../components/searchBar";
import { GlobalContext } from "../../components/GlobalContext";

const Wrapper = styled.div`
  margin-top: 80px;
`;

const Books = () => {
  const { addToCart } = useContext(GlobalContext);
  const [search, setSearch] = useState("");
  const { data, isLoading, error, refetch } = useQuery("books", () =>
    getBookBySearchTerm({ searchTerm: search })
  );
  const onSearch = () => {
    refetch();
  };

  if (error) {
    // eslint-disable-next-line no-console
    console.error(error);
    return (
      <Wrapper>
        <div>Failed to load books. Check console for details</div>
      </Wrapper>
    );
  }

  if (isLoading)
    return (
      <Wrapper>
        <LinearProgress />
      </Wrapper>
    );

  return (
    <Wrapper>
      <DrawerCart />
      <Container>
        <Box display="flex" justifyContent="space-evenly">
          <SearchBar
            searchTerm={search}
            setSearchTerm={setSearch}
            search={onSearch}
          />
        </Box>
        {!data || (data?.length === 0 && <h3>No books found!</h3>)}
        <BookList books={data || []} onClick={addToCart} checked />
      </Container>
    </Wrapper>
  );
};

export default Books;
