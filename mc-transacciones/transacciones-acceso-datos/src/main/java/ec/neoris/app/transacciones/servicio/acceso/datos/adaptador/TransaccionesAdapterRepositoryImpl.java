package ec.neoris.app.transacciones.servicio.acceso.datos.adaptador;

import ec.neoris.app.excepcion.comun.dominio.valor.TipoMovimiento;
import ec.neoris.app.transacciones.servicio.acceso.datos.entity.Cuenta;
import ec.neoris.app.transacciones.servicio.acceso.datos.entity.Movimientos;
import ec.neoris.app.transacciones.servicio.acceso.datos.repository.ICuentaRepository;
import ec.neoris.app.transacciones.servicio.acceso.datos.repository.IMovimientoRepository;
import ec.neoris.app.transacciones.servicio.dominio.dto.MovientoReporte;
import ec.neoris.app.transacciones.servicio.dominio.dto.MovimientoRegistroDto;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestMovimiento;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestMovimientoActualizacion;
import ec.neoris.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import ec.neoris.app.transacciones.servicio.dominio.exception.TransaccionNotFoundDomainException;
import ec.neoris.app.transacciones.servicio.dominio.puertos.output.ITransaccionesDomainRepository;
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

    public TransaccionesAdapterRepositoryImpl(IMovimientoRepository movimientoRepository, ICuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public MovimientoRegistroDto insertarMovimiento(RequestMovimiento requestMovimiento, BigDecimal nuevoSaldo) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.obtenerCuentaPorNumero(requestMovimiento.getNumeroCuenta());
        Cuenta cuenta = cuentaOptional.orElseThrow(() -> new TransaccionDomainException("No se ha encontrado el numero de cuenta " + requestMovimiento.getNumeroCuenta() + " "));
        Movimientos movimientos = movimientoRepository.insertarMovimiento(Movimientos.builder()
                .tipoMovimiento(requestMovimiento.getTipoMovimiento())
                .cuenta(cuenta)
                .id(UUID.randomUUID())
                .saldo(nuevoSaldo)
                .fechaMovimiento(LocalDateTime.now())
                .valor(requestMovimiento.getValor())
                .build());
        return MovimientoRegistroDto.builder()
                .valor(movimientos.getValor())
                .saldo(movimientos.getSaldo())
                .uuidMovimiento(movimientos.getId())
                .tipoMovimiento(TipoMovimiento.valueOf(movimientos.getTipoMovimiento()))
                .build();
    }

    @Override
    public MovimientoRegistroDto buscarMovimientoPorId(UUID uuidMovimiento) throws TransaccionNotFoundDomainException {
        Optional<Movimientos> movimientosOptional = movimientoRepository.buscarMovimientoPorId(uuidMovimiento);
        Movimientos movimientos = movimientosOptional.orElseThrow(() -> new TransaccionNotFoundDomainException("No se ha encontrado movimiento con id: " + uuidMovimiento.toString() + " "));
        return MovimientoRegistroDto.builder()
                .tipoMovimiento(TipoMovimiento.valueOf(movimientos.getTipoMovimiento()))
                .saldo(movimientos.getSaldo())
                .uuidMovimiento(movimientos.getId())
                .numeroCuenta(movimientos.getCuenta().getNumeroCuenta())
                .valor(movimientos.getValor())
                .build();
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
        return movimientoRepository.obtenerMovimientosPorRango(fechaInicial, fechaFinal,clienteId);
    }
}
