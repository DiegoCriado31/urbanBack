package com.urbanstyle.urbanback.catalogo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "productos")
public class Producto {

    @Id
    @Schema(description = "Identificador del producto", example = "1")
    private int id;

    @Schema(description = "Nombre que se muestra en el catálogo", example = "Polerón Adidas")
    private String nombre;

    @Schema(description = "Precio entero en pesos chilenos (CLP)", example = "39990")
    private int precio;

    @Schema(description = "Categoría del producto", example = "poleras-polerones")
    private String categoria;

    @Schema(description = "Género al que va dirigido", example = "hombre")
    private String genero;

    @Schema(description = "Descripción breve", example = "Polerón urbano cómodo de alta calidad.")
    private String descripcion;

    @Schema(description = "Unidades disponibles", example = "8")
    private int stock;

    @Schema(description = "Ruta o URL de la imagen", example = "/img/polerondidas.jpg")
    private String imagen;
}