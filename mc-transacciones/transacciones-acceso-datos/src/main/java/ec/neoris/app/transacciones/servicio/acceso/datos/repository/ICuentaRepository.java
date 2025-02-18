package ec.neoris.app.transacciones.servicio.acceso.datos.repository;

import ec.neoris.app.transacciones.servicio.acceso.datos.entity.Cuenta;
import ec.neoris.app.transacciones.servicio.dominio.exception.CuentaDomainException;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

public interface ICuentaRepository {

    public Cuenta insertarCuentaPersona(Cuenta cuenta);

    Integer obtenerSiguienteSecuencial();

    Optional<BigDecimal> obtenerSaldoActual(String numeroCuenta) throws EntityNotFoundException;

    Cuenta actualizarCuenta(Cuenta cuenta) throws EntityNotFoundException;

    void inactivarCuentas(String idCliente, Boolean activaDesactiva) throws CuentaDomainException, EntityNotFoundException;

    Optional<Cuenta> obtenerCuentaPorNumero(String numeroCuenta);
}
