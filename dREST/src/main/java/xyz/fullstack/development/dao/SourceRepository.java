package xyz.fullstack.development.dao;

import org.springframework.data.repository.CrudRepository;
import xyz.fullstack.development.domain.Source;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SourceRepository extends CrudRepository<Source, Long> {

}
