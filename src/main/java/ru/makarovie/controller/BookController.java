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
import ru.makarovie.repo.TagRepo;

@Controller
public class BookController {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private TagRepo tagRepo;

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        Iterable<Book> books = bookRepo.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/{id}")
    public String getBookDetails(@PathVariable("id") Long id, Model model) {
        if (!bookRepo.findById(id).isPresent()) return "redirect:/books";
        Book book = bookRepo.findById(id).get();
        model.addAttribute("book", book);
        return "books-details";
    }

    @GetMapping("/books/{id}/remove")
    public String removeBook (@PathVariable("id") Long id){
        if (!bookRepo.findById(id).isPresent()) return "redirect:/books";
        bookRepo.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/books/add")
    public String addBookForm() {
        return "books-add";
    }

    @PostMapping("/books/add")
    public String addBook(@RequestParam("author") String author,
                          @RequestParam("title") String title,
                          @RequestParam(value = "tagName", required = false) Tag tagName,
                          @RequestParam(value = "comment", required = false) String comment, Model model) {
        Book book = new Book(author, title);
        if (tagName != null) book.setTag(tagName);
        if (comment != null) book.setComment(comment);
        bookRepo.save(book);

        return "redirect:/books";
    }

    @GetMapping("/books/{id}/edit")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        if (!bookRepo.findById(id).isPresent()) return "redirect:/books";
        Book book = bookRepo.findById(id).get();

        model.addAttribute("book", book);
        return "books-edit";
    }

    @PostMapping("/books/{id}/edit")
    public String editBook(@PathVariable("id") Long id,
                           @RequestParam("author") String author,
                           @RequestParam("title") String title,
                           @RequestParam("tagName") String tagName,
                           @RequestParam("comment") String comment) {
        if (!bookRepo.findById(id).isPresent()) return "redirect:/books";
        Book book = bookRepo.findById(id).get();

        if (!book.getAuthor().equals(author) && author != null) book.setAuthor(author);
        if (!book.getTitle().equals(title) && title != null) book.setTitle(title);
        if (!book.getComment().equals(comment)) book.setComment(comment);

        if (!book.getTagName().equals(tagName)){
            if (tagRepo.findByName(tagName).isPresent()){
                Tag tag = tagRepo.findByName(tagName).get();
                book.setTag(tag);
            } else {
                Tag tag = new Tag(tagName);
                book.setTag(tag);
            }
        }
        bookRepo.deleteById(id);
        bookRepo.save(book);

        return "redirect:/books";
    }
}
