package ru.makarovie.repo;

import org.springframework.data.repository.CrudRepository;
import ru.makarovie.model.Tag;

public interface TagRepo extends CrudRepository<Tag, Long> {
}
