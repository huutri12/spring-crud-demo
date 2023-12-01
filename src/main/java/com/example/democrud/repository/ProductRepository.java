package com.example.democrud.repository;

import com.example.democrud.entity.CategoryEntity;
import com.example.democrud.entity.ProductEntity;
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
 * ProductRepository interface
 *
 * @author TRI
 */
@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    String sqlFindByNameContaining = "SELECT * FROM product WHERE (:name IS NULL" +
            " OR LOWER(name) LIKE LOWER(:name)) " +
            " AND is_deleted = false";

    List<ProductEntity> findAll();

    @Query(value = sqlFindByNameContaining, nativeQuery = true)
    Page<ProductEntity> findByNameContaining(@Param("name") String name, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE product SET is_deleted = true WHERE id = :id", nativeQuery = true)
    void softDeleteById(Long id);

    @Query(value = "SELECT * FROM product WHERE name = :name and is_deleted = false", nativeQuery = true)
    Optional<ProductEntity> findByNameAndDeletedEqualsFalse(String name);
}
