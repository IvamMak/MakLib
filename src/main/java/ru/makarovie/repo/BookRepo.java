package ru.makarovie.repo;

import org.springframework.data.repository.CrudRepository;
import ru.makarovie.model.Book;

import java.util.List;

public interface BookRepo extends CrudRepository<Book, Long> {
}
