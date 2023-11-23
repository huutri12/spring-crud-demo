package com.example.democrud.repository;

import com.example.democrud.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * CategoryRepository interface
 *
 * @author TRI
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    String sqlFindByNameContaining = "SELECT * FROM category WHERE :name IS NULL" +
            " OR LOWER(after_name) LIKE LOWER(:name) OR" +
            " LOWER(name) LIKE LOWER(:name) " +
            "AND is_deleted = false";

    List<Category> findAll();

    @Query(value = sqlFindByNameContaining, nativeQuery = true)
    Page<Category> findByNameContaining(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT * FROM category WHERE name = :name and is_deleted = false", nativeQuery = true)
    Optional<Category> findByNameAndDeletedEqualsFalse(@Param("name") String name);


    @Query(value = "UPDATE category SET is_deleted = true WHERE id = :id", nativeQuery = true)
    void softDeleteById(@Param(value = "id") Long id);
}
