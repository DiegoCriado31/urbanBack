package com.urbanstyle.urbanback.usuarios.service;

import com.urbanstyle.urbanback.usuarios.model.usuarioModel;
import com.urbanstyle.urbanback.usuarios.model.Rol;
import com.urbanstyle.urbanback.usuarios.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class usuarioService {

     @Autowired
    private usuarioRepository usuarioRepository;

     public List<usuarioModel> listarTodos(){
         return usuarioRepository.findAll();
     }

     public usuarioModel registrar(usuarioModel nuevoUsuario){
         if(usuarioRepository.existsByEmailIgnoreCase(nuevoUsuario.getEmail())){
             throw new IllegalArgumentException("Este Email ya existe");
         }
         if(usuarioRepository.existsByRun(nuevoUsuario.getRun())){
             throw new IllegalArgumentException("Este RUN ya se encuentra registrado");
         }

         nuevoUsuario.setRol(Rol.CLIENTE);
         return usuarioRepository.save(nuevoUsuario);
     }

     public Optional<usuarioModel> autenticar(String email, String password){
         Optional<usuarioModel> usuarioOpt = usuarioRepository.findByEmailIgnoreCase(email);
         if(usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password)){
             return usuarioOpt;
         }
         return Optional.empty();
     }

     public usuarioModel actualizarRol(String id, Rol nuevoRol){
         usuarioModel usuario = usuarioRepository.findById(id).
                 orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
         usuario.setRol(nuevoRol);
         return usuarioRepository.save(usuario);
     }

     public void eliminar(String id){
         usuarioRepository.deleteById(id);
     }
}
