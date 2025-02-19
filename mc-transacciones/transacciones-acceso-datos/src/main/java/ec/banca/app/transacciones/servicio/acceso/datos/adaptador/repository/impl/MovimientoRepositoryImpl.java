package ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository.impl;

import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Movimientos;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository.IMovimientoRepository;
import ec.banca.app.transacciones.servicio.dominio.dto.MovientoReporte;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MovimientoRepositoryImpl implements IMovimientoRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Movimientos insertarMovimiento(Movimientos movimientos) {
        entityManager.persist(movimientos);
        return movimientos;
    }

    @Override
    public Optional<Movimientos> buscarMovimientoPorId(UUID uuidMovimiento) {
        return Optional.ofNullable(entityManager.find(Movimientos.class, uuidMovimiento));
    }

    @Override
    public Movimientos actualizarMovimiento(Movimientos movimientos) {
        return entityManager.merge(movimientos);
    }

    @Override
    public List<MovientoReporte> obtenerMovimientosPorRango(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        String jpql = "SELECT new ec.banca.app.transacciones.servicio.dominio.dto.MovientoReporte(" +
                "mov.fechaMovimiento, " +
                "pe.nombre, " +
                "cu.numeroCuenta, " +
                "cu.tipoCuenta, " +
                "cu.saldoInicial, " +
                "cu.estado, " +
                "mov.saldo) " +
                "FROM Movimientos mov " +
                "INNER JOIN mov.cuenta cu " +
                "INNER JOIN cu.cliente pe " +
                "WHERE mov.fechaMovimiento BETWEEN :fechaInicial AND :fechaFinal";
        TypedQuery<MovientoReporte> query = entityManager.createQuery(jpql, MovientoReporte.class);
        query.setParameter("fechaInicial", fechaInicial);
        query.setParameter("fechaFinal", fechaFinal);
        return query.getResultList();
    }

    @Override
    public List<MovientoReporte> obtenerMovimientosPorRango(LocalDateTime fechaInicial, LocalDateTime fechaFinal, String clienteId) {
        String jpql = """
        SELECT new ec.banca.app.transacciones.servicio.dominio.dto.MovientoReporte(
            mov.fechaMovimiento, 
            pe.nombre, 
            cu.numeroCuenta, 
            cu.tipoCuenta, 
            cu.saldoInicial, 
            cu.estado, 
            (case when mov.tipoMovimiento='DEBITO' THEN -mov.valor else mov.valor end),
            mov.saldo) 
        FROM Movimientos mov
        JOIN mov.cuenta cu 
        JOIN cu.cliente pe
        WHERE mov.fechaMovimiento BETWEEN :fechaInicial AND :fechaFinal 
        AND pe.clienteId = :cliente
        AND mov.fechaMovimiento = (
            SELECT MAX(mov2.fechaMovimiento)
            FROM Movimientos mov2
            WHERE mov2.cuenta.id = mov.cuenta.id
        )
    """;

        TypedQuery<MovientoReporte> query = entityManager.createQuery(jpql, MovientoReporte.class);
        query.setParameter("fechaInicial", fechaInicial);
        query.setParameter("fechaFinal", fechaFinal);
        query.setParameter("cliente", clienteId);

        return query.getResultList();
    }
}
