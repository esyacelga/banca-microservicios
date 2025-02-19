package ec.banca.app.persona.servicio.contendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"ec.banca.app.persona.servicio.acceso.datos", "ec.banca.app.comun.acceso.datos"})
@EntityScan(basePackages = {"ec.banca.app.persona.servicio.acceso.datos", "ec.banca.app.comun.acceso.datos"})
@SpringBootApplication(scanBasePackages = {"ec.banca.app.persona.servicio","ec.banca.app.comun"} )

public class ClientePersonaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientePersonaServiceApplication.class, args);
    }
}
