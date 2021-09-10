import React, {useState} from "react";
import axios from "axios";
import {
    Button,
    Card,
    CardActionArea, CardActions, CardContent, CardMedia,
    Grid,
    GridSize,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow, Typography
} from "@material-ui/core";
import logo from "../../assets/logo.svg";

interface Book {
    id: bigint,
    title: string,
    author: string,
    pageCount: string,
    isbn: string
}

// <TableContainer component={Paper}>
//     <Table aria-label="simple table">
//         <TableHead>
//             <TableRow>
//                 <TableCell>Title</TableCell>
//                 <TableCell>Author</TableCell>
//                 <TableCell>Page Count</TableCell>
//                 <TableCell>ISBN</TableCell>
//             </TableRow>
//         </TableHead>
//         <TableBody>
//             {books?.map((book =>
//                     <TableRow key={book.title}>
//                         <TableCell>{book.title}</TableCell>
//                         <TableCell>{book.author}</TableCell>
//                         <TableCell>{book.pageCount}</TableCell>
//                         <TableCell>{book.isbn}</TableCell>
//                     </TableRow>
//             ))}
//         </TableBody>
//     </Table>
// </TableContainer>

export default function Books() {
    const [books, setBooks] = useState<Array<Book>>();
    const fetchBooks = async () => {
        const result = await axios.get(`/api/books/all`);
        setBooks(result.data);
    };

    fetchBooks();
    return (
        <div style={{ marginTop: 40, padding: 30 }}>
            <Grid container justify="center" spacing={8}>
                {books?.map(book =>
                    <Grid item key={book.title}>
                        <Card>
                            <CardActionArea>
                                <CardMedia
                                    component="img"
                                    alt="Bookeroo Logo"
                                    height="360"
                                    width="240"
                                    image={logo}
                                    title="Bookeroo Logo"
                                />
                                <CardContent color="secondary">
                                    <Typography gutterBottom variant="h5" component="h2">
                                        {book.title}
                                    </Typography>
                                    <Typography component="p">{book.author}</Typography>
                                </CardContent>
                            </CardActionArea>
                        </Card>
                    </Grid>
                )}
            </Grid>
        </div>
    );
}
