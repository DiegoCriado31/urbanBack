package com.urbanstyle.urbanback.blogs.repository;

import com.urbanstyle.urbanback.blogs.model.BlogModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends MongoRepository<BlogModel, Long> {
    Optional<BlogModel> findBySlug(String slug);

    boolean existsBySlug(String slug);
}