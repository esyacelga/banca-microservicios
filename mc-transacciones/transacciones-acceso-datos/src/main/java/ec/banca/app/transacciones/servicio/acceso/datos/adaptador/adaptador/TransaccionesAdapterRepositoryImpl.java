package ec.banca.app.transacciones.servicio.acceso.datos.adaptador.adaptador;

import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Cuenta;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Movimientos;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.mapper.TransaccionesDataAccesMapper;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository.ICuentaRepository;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository.IMovimientoRepository;
import ec.banca.app.transacciones.servicio.dominio.dto.MovientoReporte;
import ec.banca.app.transacciones.servicio.dominio.dto.MovimientoRegistroDto;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestMovimiento;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestMovimientoActualizacion;
import ec.banca.app.transacciones.servicio.dominio.entidad.MovimientoAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionNotFoundDomainException;
import ec.banca.app.transacciones.servicio.dominio.puertos.output.ITransaccionesDomainRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TransaccionesAdapterRepositoryImpl implements ITransaccionesDomainRepository {
    private final IMovimientoRepository movimientoRepository;
    private final ICuentaRepository cuentaRepository;
    private final TransaccionesDataAccesMapper transaccionesDataAccesMapper;

    public TransaccionesAdapterRepositoryImpl(IMovimientoRepository movimientoRepository,
                                              ICuentaRepository cuentaRepository,
                                              TransaccionesDataAccesMapper transaccionesDataAccesMapper) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
        this.transaccionesDataAccesMapper = transaccionesDataAccesMapper;
    }

    @Override
    public MovimientoRegistroDto insertarMovimiento(MovimientoAggregateRoot aggregateRoot) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.obtenerCuentaPorNumero(aggregateRoot.getNumeroCuenta());
        Cuenta cuenta = cuentaOptional.orElseThrow(() -> new TransaccionDomainException("No se ha encontrado el numero de cuenta " + aggregateRoot.getNumeroCuenta() + " "));
        Movimientos movimientos = movimientoRepository.insertarMovimiento(transaccionesDataAccesMapper.movimientoAggregateRootToEntidad(aggregateRoot, cuenta));
        return transaccionesDataAccesMapper.transformarEntidadToDto(movimientos);
    }

    @Override
    public MovimientoRegistroDto insertarMovimiento(RequestMovimiento requestMovimiento, BigDecimal nuevoSaldo) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.obtenerCuentaPorNumero(requestMovimiento.getNumeroCuenta());
        Cuenta cuenta = cuentaOptional.orElseThrow(() -> new TransaccionDomainException("No se ha encontrado el numero de cuenta " + requestMovimiento.getNumeroCuenta() + " "));
        Movimientos movimientos = movimientoRepository.insertarMovimiento(transaccionesDataAccesMapper.transformarRequestToEntidad(requestMovimiento, cuenta, nuevoSaldo));
        return transaccionesDataAccesMapper.transformarEntidadToDto(movimientos);
    }

    @Override
    public MovimientoRegistroDto buscarMovimientoPorId(UUID uuidMovimiento) throws TransaccionNotFoundDomainException {
        Optional<Movimientos> movimientosOptional = movimientoRepository.buscarMovimientoPorId(uuidMovimiento);
        Movimientos movimientos = movimientosOptional.orElseThrow(() -> new TransaccionNotFoundDomainException("No se ha encontrado movimiento con id: " + uuidMovimiento.toString() + " "));
        return transaccionesDataAccesMapper.transformarEntidadToDto(movimientos);
    }

    @Override
    public MovimientoRegistroDto actualizarMovimiento(RequestMovimientoActualizacion requestMovimiento) throws TransaccionNotFoundDomainException {
        Optional<Movimientos> movimientosOptional = movimientoRepository.buscarMovimientoPorId(requestMovimiento.getUuidMovimiento());
        Movimientos movimientos = movimientosOptional.orElseThrow(() -> new TransaccionNotFoundDomainException("No se ha encontrado movimiento con id: " + requestMovimiento.getUuidMovimiento().toString() + " "));
        movimientos.setTipoMovimiento(requestMovimiento.getTipoMovimiento().getValue());
        movimientos.setValor(requestMovimiento.getValor());
        movimientoRepository.actualizarMovimiento(movimientos);
        return MovimientoRegistroDto.builder()
                .uuidMovimiento(movimientos.getId())
                .build();
    }

    @Override
    public List<MovientoReporte> obtenerMovimientosPorRango(LocalDateTime fechaInicial, LocalDateTime fechaFinal) throws TransaccionDomainException {
        return movimientoRepository.obtenerMovimientosPorRango(fechaInicial, fechaFinal);
    }

    @Override
    public List<MovientoReporte> obtenerMovimientosPorRango(LocalDateTime fechaInicial, LocalDateTime fechaFinal, String clienteId) throws TransaccionDomainException {
        return movimientoRepository.obtenerMovimientosPorRango(fechaInicial, fechaFinal, clienteId);
    }
}
