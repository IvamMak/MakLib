package ru.makarovie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.makarovie.model.Book;
import ru.makarovie.model.Tag;
import ru.makarovie.repo.BookRepo;

@Controller
public class BookController {
    @Autowired
    BookRepo bookRepo;

    @GetMapping("/books")
    public String getAllBooks(Model model){
        Iterable<Book> books = bookRepo.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/{id}")
    public String getBookDetails(@PathVariable("id") Long id, Model model){
        Book book = bookRepo.findById(id).get();
        model.addAttribute("book", book);
        return "books-details";
    }

    @GetMapping("/books/add")
    public String addBookForm(){
        return "books-add";
    }

    @PostMapping("/books/add")
    public String addBook (@RequestParam("author") String author,
                           @RequestParam("title") String title,
                           @RequestParam(value = "tagName", required = false) Tag tagName,
                           @RequestParam(value = "comment", required = false) String comment, Model model){
        Book book = new Book(author, title);
        if (tagName != null) book.setTag(tagName);
        if (comment != null) book.setComment(comment);
        bookRepo.save(book);

        return "redirect:books";
    }
}
