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

    @Modifying
    @Transactional
    @Query("DELETE FROM Category c WHERE c.id IN :id")
    void deleteCategoriesById(@Param("id") List<Long> id);

    @Query(value = "SELECT * FROM category WHERE name = :name", nativeQuery = true)
    Optional<Category> findByName(@Param("name") String name);

    @Query(value = "UPDATE category SET is_deleted = true WHERE id = :id", nativeQuery = true)
    void softDeleteById(@Param(value = "id") Long id);

    /**
     * Return a{@link Page} of entities meeting the paging restriction provided.
     *
     * @param pageable Page
     * @return a page of entities
     */
    @Query("SELECT t FROM Category t")
    Page<Category> findAllWithPagination(Pageable pageable);

    @Query("SELECT t FROM Category t WHERE t.id=?1")
    Page<Category> findByPublishedWithPagination(boolean isPublished, Pageable pageable);

    @Query("SELECT t FROM Category t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', ?1,'%'))")
    Page<Category> findByTitleWithPagination(String title, Pageable pageable);
}
