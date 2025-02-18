package ec.neoris.app.transacciones.servicio.contendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"ec.neoris.app.transacciones.servicio.acceso.datos", "ec.neoris.app.comun.acceso.datos"})
@EntityScan(basePackages = {"ec.neoris.app.transacciones.servicio.acceso.datos", "ec.neoris.app.comun.acceso.datos"})
@SpringBootApplication(scanBasePackages = {"ec.neoris.app.transacciones.servicio","ec.neoris.app.comun"} )

public class TransaccionesServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransaccionesServiceApplication.class, args);
    }
}
