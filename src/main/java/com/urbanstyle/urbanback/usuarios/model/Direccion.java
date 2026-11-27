package com.urbanstyle.urbanback.usuarios.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {
    private String region;
    private String comuna;
    private String calle;
}
