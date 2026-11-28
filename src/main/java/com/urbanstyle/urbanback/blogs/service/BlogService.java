package com.urbanstyle.urbanback.blogs.service;

import com.urbanstyle.urbanback.blogs.model.BlogModel;
import com.urbanstyle.urbanback.blogs.repository.BlogRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<BlogModel> listarTodos() {
        return blogRepository.findAll();
    }

    public BlogModel obtenerPorSlug(String slug) {
        return blogRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Articulo no encontrado: " + slug));
    }

    public BlogModel crear(BlogModel blog) {
        if (blog.getId() == null) {
            long nextId = blogRepository.findAll().stream()
                    .mapToLong(b -> b.getId() != null ? b.getId() : 0L)
                    .max()
                    .orElse(0L) + 1L;
            blog.setId(nextId);
        }
        if (blog.getSlug() == null || blog.getSlug().isBlank()) {
            blog.setSlug(blog.getTitulo().toLowerCase().replaceAll("[^a-z0-9]+", "-"));
        }
        return blogRepository.save(blog);
    }

    public BlogModel actualizar(Long id, BlogModel datos) {
        BlogModel existente = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog no encontrado con ID: " + id));
        existente.setTitulo(datos.getTitulo());
        existente.setResumen(datos.getResumen());
        existente.setImagenPortada(datos.getImagenPortada());
        existente.setCategoria(datos.getCategoria());
        existente.setDetalleJson(datos.getDetalleJson());
        return blogRepository.save(existente);
    }

    public void eliminar(Long id) {
        blogRepository.deleteById(id);
    }
}