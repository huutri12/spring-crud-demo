package com.example.democrud.repository;

import com.example.democrud.entity.ProductEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * ProductRepository interface
 *
 * @author TRI
 */
@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET is_deleted = true WHERE id = :id", nativeQuery = true)
    void softDeleteById(Long id);
}
