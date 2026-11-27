package com.urbanstyle.urbanback.usuarios.model;
import com.urbanstyle.urbanback.usuarios.model.Direccion;
import com.urbanstyle.urbanback.usuarios.model.Rol;
import com.urbanstyle.urbanback.usuarios.model.usuarioModel;
import com.urbanstyle.urbanback.usuarios.repository.usuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class usuarioInitializer {
    @Bean
    CommandLineRunner cargarUsuariosIniciales(usuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                usuarioModel supervisor = new usuarioModel(
                        null,
                        "11111111-1",
                        "Supervisor Urban",
                        "supervisor@urban.cl",
                        "super123",
                        Rol.SUPERVISOR,
                        new Direccion("Metropolitana de Santiago", "Santiago", "Av. Providencia 100")
                );

                usuarioModel comercial = new usuarioModel(
                        null,
                        "22222222-2",
                        "Comercial Urban",
                        "comercial@urban.cl",
                        "comercial123",
                        Rol.COMERCIAL,
                        new Direccion("Metropolitana de Santiago", "Las Condes", "Av. Apoquindo 200")
                );

                usuarioRepository.save(supervisor);
                usuarioRepository.save(comercial);
                System.out.println(">>> Usuarios iniciales cargados en MongoDB Atlas.");
            }
        };
    }
}

