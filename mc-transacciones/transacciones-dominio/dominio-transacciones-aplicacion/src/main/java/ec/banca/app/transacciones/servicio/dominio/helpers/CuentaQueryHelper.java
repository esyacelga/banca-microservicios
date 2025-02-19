package ec.banca.app.transacciones.servicio.dominio.helpers;

import ec.banca.app.transacciones.servicio.dominio.dto.CuentaDto;
import ec.banca.app.transacciones.servicio.dominio.exception.CuentaDomainException;
import ec.banca.app.transacciones.servicio.dominio.puertos.output.ICuentaDomainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CuentaQueryHelper {
    private final ICuentaDomainRepository cuentaRepository;

    public CuentaQueryHelper(ICuentaDomainRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }


    public Optional<CuentaDto> obtenerCuentaPorNumero(String numeroCuenta) throws CuentaDomainException {
        return cuentaRepository.obtenerCuentaPorNumero(numeroCuenta);
    }
}
