package com.urbanstyle.urbanback.catalogo.service;

import com.urbanstyle.urbanback.catalogo.model.Producto;
import com.urbanstyle.urbanback.catalogo.repository.RepositorioProductos;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductosService {

    private final RepositorioProductos repositorio;

    public ProductosService(RepositorioProductos repositorio) {
        this.repositorio = repositorio;
    }

    public List<Producto> listar() {
        return repositorio.findAll();
    }

    public Optional<Producto> buscarPorId(int id) {
        return repositorio.findById(id);
    }

    public Producto crear(Producto producto) {
        int siguienteId = repositorio.findTopByOrderByIdDesc()
                .map(p -> p.getId() + 1)
                .orElse(1);
        producto.setId(siguienteId);
        return repositorio.save(producto);
    }

    public Optional<Producto> actualizar(int id, Producto datos) {
        return repositorio.findById(id).map(existente -> {
            existente.setNombre(datos.getNombre());
            existente.setPrecio(datos.getPrecio());
            existente.setCategoria(datos.getCategoria());
            existente.setGenero(datos.getGenero());
            existente.setDescripcion(datos.getDescripcion());
            existente.setStock(datos.getStock());
            existente.setImagen(datos.getImagen());
            return repositorio.save(existente);
        });
    }

    public boolean eliminar(int id) {
        if (!repositorio.existsById(id)) {
            return false;
        }
        repositorio.deleteById(id);
        return true;
    }
}