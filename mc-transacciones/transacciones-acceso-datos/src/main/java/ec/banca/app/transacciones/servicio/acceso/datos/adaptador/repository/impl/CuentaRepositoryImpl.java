package ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository.impl;

import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Cuenta;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository.ICuentaRepository;
import ec.banca.app.transacciones.servicio.dominio.exception.CuentaDomainException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Repository
public class CuentaRepositoryImpl implements ICuentaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cuenta insertarCuentaPersona(Cuenta cuenta) {
        entityManager.persist(cuenta);
        return cuenta;
    }

    @Override
    public Integer obtenerSiguienteSecuencial() {
        String hql = "SELECT COALESCE(MAX(c.numeroCuenta), '0000') FROM Cuenta c";
        String ultimoSecuencial = entityManager.createQuery(hql, String.class).getSingleResult();
        if (ultimoSecuencial == null)
            return 1;
        return Integer.parseInt(ultimoSecuencial) + 1;
    }

    @Override
    public Optional<BigDecimal> obtenerSaldoActual(String numeroCuenta) throws EntityNotFoundException {
        String hql = """
                    SELECT 
                        CASE 
                            WHEN t.saldo_neto = 0 THEN t.saldo_inicial 
                            ELSE t.saldo_neto 
                        END 
                    FROM (
                        SELECT 
                            c.saldoInicial AS saldo_inicial,
                            COALESCE(SUM(CASE WHEN mov.tipoMovimiento = 'CREDITO' THEN mov.valor ELSE 0 END), 0) -
                            COALESCE(SUM(CASE WHEN mov.tipoMovimiento = 'DEBITO' THEN mov.valor ELSE 0 END), 0) AS saldo_neto
                        FROM Cuenta c
                        LEFT JOIN Movimientos mov ON c.id = mov.cuenta.id
                        WHERE c.numeroCuenta = :numeroCuenta
                        GROUP BY c.id, c.saldoInicial
                    ) AS t
                """;

        try {
            return Optional.of(entityManager.createQuery(hql, BigDecimal.class)
                    .setParameter("numeroCuenta", numeroCuenta)
                    .getSingleResult());
        } catch (NoResultException noResultException) {
            throw new EntityNotFoundException("No se encontró la cuenta con número " + numeroCuenta);
        }
    }


    @Override
    public Cuenta actualizarCuenta(Cuenta cuenta) throws EntityNotFoundException {
        String hql = "UPDATE Cuenta c " +
                "SET c.tipoCuenta = :tipoCuenta, c.estado=:estado, " +
                "c.saldoInicial=:saldo WHERE c.numeroCuenta = :numeroCuenta";
        int updatedRows = entityManager.createQuery(hql)
                .setParameter("tipoCuenta", cuenta.getTipoCuenta())
                .setParameter("estado", cuenta.getEstado())
                .setParameter("saldo", cuenta.getSaldoInicial())
                .setParameter("numeroCuenta", cuenta.getNumeroCuenta())
                .executeUpdate();
        if (updatedRows == 0) {
            throw new EntityNotFoundException("No se encontró la cuenta con número " + cuenta.getNumeroCuenta());
        }
        return cuenta;
    }

    @Override
    public void inactivarCuentas(String clienteID, Boolean activaDesactiva) throws CuentaDomainException, EntityNotFoundException {
        try {
            log.info("🔄 Inactivando cuentas para el cliente: {}", clienteID);
            String hql = "UPDATE Cuenta c SET c.estado = :nuevoEstado WHERE c.cliente.clienteId = :clienteID";
            int filasActualizadas = entityManager.createQuery(hql)
                    .setParameter("nuevoEstado", activaDesactiva)  // false para inactivar
                    .setParameter("clienteID", clienteID)
                    .executeUpdate();

            if (filasActualizadas == 0) {
                log.warn("No se encontraron cuentas activas para el cliente con ID {}", clienteID);
                throw new EntityNotFoundException("No se encontraron cuentas activas para el cliente con ID " + clienteID);
            }

            log.info("Se inactivaron {} cuentas para el cliente {}", filasActualizadas, clienteID);

        } catch (Exception ex) {
            log.error("Error al inactivar cuentas para el cliente {}: {}", clienteID, ex.getMessage(), ex);
            throw new CuentaDomainException("Error al inactivar cuentas para el cliente " + clienteID, ex);
        }
    }


    @Override
    public Optional<Cuenta> obtenerCuentaPorNumero(String numeroCuenta) throws EntityNotFoundException {
        String hql = "SELECT c FROM Cuenta c WHERE c.numeroCuenta = :numeroCuenta";
        try {
            Cuenta cuenta = entityManager.createQuery(hql, Cuenta.class)
                    .setParameter("numeroCuenta", numeroCuenta)
                    .getSingleResult();
            return Optional.of(cuenta);
        } catch (NoResultException noResultException) {
            throw new EntityNotFoundException("No se encontró la cuenta con número " + numeroCuenta);
        }

    }
}
