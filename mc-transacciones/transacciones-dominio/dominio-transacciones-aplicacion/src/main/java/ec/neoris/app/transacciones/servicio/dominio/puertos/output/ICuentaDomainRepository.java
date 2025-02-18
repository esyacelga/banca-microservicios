package ec.neoris.app.transacciones.servicio.dominio.puertos.output;

import ec.neoris.app.transacciones.servicio.dominio.dto.CuentaDto;
import ec.neoris.app.transacciones.servicio.dominio.exception.ClienteNotFoundDomainException;
import ec.neoris.app.transacciones.servicio.dominio.exception.CuentaDomainException;

import java.math.BigDecimal;
import java.util.Optional;

public interface ICuentaDomainRepository {
    Optional<CuentaDto> insertarCuentaPersona(CuentaDto requestCuenta) throws ClienteNotFoundDomainException;

    Integer obtenerSiguienteSecuencial();

    BigDecimal obtenerSaldoActual(String numeroCuenta);


    CuentaDto actualizarCuenta(CuentaDto cuentaDto) throws CuentaDomainException;

    void inactivarCuentas(String idCliente, Boolean activaDesactiva) throws CuentaDomainException;


    Optional<CuentaDto> obtenerCuentaPorNumero(String numeroCuenta) throws CuentaDomainException;



}
