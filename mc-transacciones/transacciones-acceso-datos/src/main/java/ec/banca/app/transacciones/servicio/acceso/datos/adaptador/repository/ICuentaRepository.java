package ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository;

import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Cuenta;
import ec.banca.app.transacciones.servicio.dominio.exception.CuentaDomainException;
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
