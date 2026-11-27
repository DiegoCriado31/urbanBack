package com.urbanstyle.urbanback.usuarios.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class usuarioModel {
    @Id
    private String id;

    @Indexed(unique = true)
    private String run;

    private String nombre;

    @Indexed(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Rol rol = Rol.CLIENTE;

    private Direccion direccion;
}
