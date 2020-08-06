package ru.makarovie.repo;

import org.springframework.data.repository.CrudRepository;
import ru.makarovie.model.Tag;

import java.util.Optional;

public interface TagRepo extends CrudRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
