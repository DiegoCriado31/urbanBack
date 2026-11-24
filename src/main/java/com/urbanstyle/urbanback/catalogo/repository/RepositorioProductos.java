package com.urbanstyle.urbanback.catalogo.repository;

import com.urbanstyle.urbanback.catalogo.model.Producto;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioProductos extends MongoRepository<Producto, Integer> {
    Optional<Producto> findTopByOrderByIdDesc();
}