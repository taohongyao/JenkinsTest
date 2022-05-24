package JenkinsRestAPI.controller;

import JenkinsRestAPI.Exception.ResourceNotFoundException;
import JenkinsRestAPI.dao.BooksRepository;
import JenkinsRestAPI.entity.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BooksController {
    @Autowired
    private BooksRepository booksRepository;

    @GetMapping("/books")
    public List<Books> getAllNotes() {
        return booksRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public Books getAllNotes(@PathVariable(value = "id") Integer bookId) {
        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        return book;
    }

    @PostMapping("/books")
    public Books createBook(@RequestBody Books books){
        return booksRepository.save(books);
    }

    @PutMapping("/books/{id}")
    public Books updateNote(@PathVariable(value = "id") Integer bookId,
                            @RequestBody Books bookDetails) {

        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        book.setBookName(bookDetails.getBookName());
        book.setBookPrice(bookDetails.getBookPrice());
        book.setBookDescription(bookDetails.getBookDescription());

        Books updatedNote = booksRepository.save(book);
        return updatedNote;
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Integer bookId) {
        Books note = booksRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

        booksRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}
