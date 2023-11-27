package com.example.democrud.repository;

import com.example.democrud.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository interface
 *
 * @author TRI
 */
@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
}
