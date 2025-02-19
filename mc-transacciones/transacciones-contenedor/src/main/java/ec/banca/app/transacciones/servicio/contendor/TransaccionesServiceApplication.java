package ec.banca.app.transacciones.servicio.contendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"ec.banca.app.transacciones.servicio.acceso.datos", "ec.banca.app.comun.acceso.datos"})
@EntityScan(basePackages = {"ec.banca.app.transacciones.servicio.acceso.datos", "ec.banca.app.comun.acceso.datos"})
@SpringBootApplication(scanBasePackages = {"ec.banca.app.transacciones.servicio","ec.banca.app.comun"} )

public class TransaccionesServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransaccionesServiceApplication.class, args);
    }
}
