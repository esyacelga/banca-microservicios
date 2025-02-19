package ec.banca.app.transacciones.servicio.dominio.puertos.output;

import ec.banca.app.transacciones.servicio.dominio.dto.MovientoReporte;
import ec.banca.app.transacciones.servicio.dominio.dto.MovimientoRegistroDto;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestMovimiento;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestMovimientoActualizacion;
import ec.banca.app.transacciones.servicio.dominio.entidad.MovimientoAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionNotFoundDomainException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ITransaccionesDomainRepository {

    public MovimientoRegistroDto insertarMovimiento(MovimientoAggregateRoot aggregateRoot);

    public MovimientoRegistroDto insertarMovimiento(RequestMovimiento requestMovimiento, BigDecimal nuevoSaldo);

    public MovimientoRegistroDto buscarMovimientoPorId(UUID uuidMovimiento) throws TransaccionNotFoundDomainException;

    public MovimientoRegistroDto actualizarMovimiento(RequestMovimientoActualizacion requestMovimiento) throws TransaccionDomainException;

    public List<MovientoReporte> obtenerMovimientosPorRango(LocalDateTime fechaInicial, LocalDateTime fechaFinal) throws TransaccionDomainException;
    public List<MovientoReporte> obtenerMovimientosPorRango(LocalDateTime fechaInicial, LocalDateTime fechaFinal, String clienteId) throws TransaccionDomainException;

}
