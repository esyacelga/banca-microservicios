package ec.banca.app.transacciones.servicio.dominio.handlers;

import ec.banca.app.transacciones.servicio.dominio.dto.CuentaDto;
import ec.banca.app.transacciones.servicio.dominio.exception.CuentaDomainException;
import ec.banca.app.transacciones.servicio.dominio.exception.CuentaNotFoundDomainException;
import ec.banca.app.transacciones.servicio.dominio.helpers.CuentaQueryHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CuentaQueryCommandHandler {
    private final CuentaQueryHelper cuentaQueryHelper;

    public CuentaQueryCommandHandler(CuentaQueryHelper cuentaQueryHelper) {
        this.cuentaQueryHelper = cuentaQueryHelper;
    }

    public CuentaDto obtenerCuentaPorNumero(String numeroCuenta) throws CuentaDomainException, CuentaNotFoundDomainException {
        Optional<CuentaDto> cuenta = cuentaQueryHelper.obtenerCuentaPorNumero(numeroCuenta);
        if (cuenta.isEmpty()) {
            throw new CuentaNotFoundDomainException("Cuenta no encontrada");
        }
        return cuentaQueryHelper.obtenerCuentaPorNumero(numeroCuenta).get();
    }

}
