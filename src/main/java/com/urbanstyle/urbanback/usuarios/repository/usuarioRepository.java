package com.urbanstyle.urbanback.usuarios.repository;

import org.springframework.stereotype.Repository;
import com.urbanstyle.urbanback.usuarios.model.usuarioModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@Repository
public interface usuarioRepository extends MongoRepository<usuarioModel, String>{
    Optional<usuarioModel> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByRun(String run);
}
