package ec.neoris.app.transacciones.servicio.dominio.helpers;

import ec.neoris.app.excepcion.comun.dominio.valor.TipoCuenta;
import ec.neoris.app.excepcion.comun.dominio.valor.TipoMovimiento;
import ec.neoris.app.transacciones.servicio.dominio.dto.CuentaDto;
import ec.neoris.app.transacciones.servicio.dominio.dto.MovimientoRegistroDto;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestCuenta;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestCuentaActualizacion;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestMovimiento;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestMovimientoActualizacion;
import ec.neoris.app.transacciones.servicio.dominio.dto.response.ResponseCuenta;
import ec.neoris.app.transacciones.servicio.dominio.dto.response.ResponseMovimiento;
import ec.neoris.app.transacciones.servicio.dominio.exception.ClienteNotFoundDomainException;
import ec.neoris.app.transacciones.servicio.dominio.exception.CuentaDomainException;
import ec.neoris.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import ec.neoris.app.transacciones.servicio.dominio.mapper.TransaccionDomainMapper;
import ec.neoris.app.transacciones.servicio.dominio.puertos.output.ICuentaDomainRepository;
import ec.neoris.app.transacciones.servicio.dominio.puertos.output.ITransaccionesDomainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class TransaccionPersistHelper {
    private final TransaccionDomainMapper transaccionDomainMapper;
    private final ICuentaDomainRepository cuentaRepository;
    private final ITransaccionesDomainRepository transaccionesRepository;


    public TransaccionPersistHelper(TransaccionDomainMapper transaccionDomainMapper, ICuentaDomainRepository cuentaRepository,
                                    ITransaccionesDomainRepository transaccionesRepository) {
        this.transaccionDomainMapper = transaccionDomainMapper;
        this.cuentaRepository = cuentaRepository;
        this.transaccionesRepository = transaccionesRepository;
    }

    @Transactional
    public MovimientoRegistroDto actualizarMovimiento(RequestMovimientoActualizacion requestMovimiento) throws TransaccionDomainException {
        return transaccionesRepository.actualizarMovimiento(requestMovimiento);
    }

    @Transactional
    public ResponseMovimiento insertarMovimiento(RequestMovimiento requestMovimiento) throws TransaccionDomainException {
        BigDecimal saldoActual = cuentaRepository.obtenerSaldoActual(requestMovimiento.getNumeroCuenta());
        TipoMovimiento tipoMovimiento;
        try {
            tipoMovimiento = TipoMovimiento.valueOf(requestMovimiento.getTipoMovimiento());
        } catch (IllegalArgumentException ex) {
            throw new TransaccionDomainException("Tipo de movimiento incorrecto, tipo de movimiento correcto es DEBITO o CREDITO ", ex);
        }
        if (requestMovimiento.getValor().compareTo(BigDecimal.ONE) < 0) {
            throw new TransaccionDomainException("El valor no puede ser negativo o no puede ser 0");
        }
        if (tipoMovimiento == TipoMovimiento.DEBITO &&
                saldoActual.compareTo(requestMovimiento.getValor()) < 0) {
            throw new TransaccionDomainException("Saldo insuficiente");
        }

        BigDecimal nuevoSaldo = tipoMovimiento == TipoMovimiento.CREDITO
                ? saldoActual.add(requestMovimiento.getValor())
                : saldoActual.subtract(requestMovimiento.getValor());

        MovimientoRegistroDto mov = transaccionesRepository.insertarMovimiento(requestMovimiento, nuevoSaldo);
        return ResponseMovimiento.builder()
                .uuidMovimiento(mov.getUuidMovimiento())
                .mensaje("Movimiento Registrado exitosamente")
                .build();
    }

    @Transactional
    public ResponseCuenta actualizarCuentaPersona(RequestCuentaActualizacion cuentaActualizacion) throws CuentaDomainException {
        Optional<CuentaDto> cuenta = cuentaRepository.obtenerCuentaPorNumero(cuentaActualizacion.getNumeroCuenta());
        try {
            TipoCuenta.valueOf(cuentaActualizacion.getTipoCuenta());
        } catch (IllegalArgumentException ex) {
            throw new CuentaDomainException("El tipo de cuenta valido es AHORROS, CORRIENTE", ex);
        }
        if (cuentaActualizacion.getSaldo().compareTo(BigDecimal.ZERO) < 0)
            throw new CuentaDomainException("El saldo no puede ser negativo ");
        CuentaDto cuentaDto = cuentaRepository.actualizarCuenta(transaccionDomainMapper.requestCuentaActualizacionToCuentaDto(cuentaActualizacion, cuenta.get().getClienteId()));
        return ResponseCuenta.builder()
                .uuidCuenta(cuentaDto.getUuidCuenta())
                .mensaje("Cuenta actualizada exitosamente")
                .build();
    }

    @Transactional
    public ResponseCuenta insertarCuentaPersona(RequestCuenta requestCuenta) throws CuentaDomainException, ClienteNotFoundDomainException {
        try {
            TipoCuenta.valueOf(requestCuenta.getTipoCuenta());
        } catch (IllegalArgumentException ex) {
            throw new CuentaDomainException("El tipo de cuenta debe ser AHORROS, CORRIENTE", ex);
        }
        if (requestCuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0)
            throw new CuentaDomainException("El saldo no puede ser negativo ");
        Optional<CuentaDto> cuentaDto = cuentaRepository.insertarCuentaPersona(transaccionDomainMapper.requestCuentaToCuentaDto(UUID.randomUUID(), requestCuenta));
        if (requestCuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0)
            transaccionesRepository.insertarMovimiento(RequestMovimiento.builder()
                    .numeroCuenta(requestCuenta.getNumeroCuenta().toString())
                    .tipoMovimiento(TipoMovimiento.CREDITO.getValue())
                    .valor(requestCuenta.getSaldo())
                    .build(), cuentaDto.get().getSaldo());

        return ResponseCuenta.builder()
                .uuidCuenta(cuentaDto.get().getUuidCuenta())
                .mensaje("Cuenta registrada exitosamente")
                .build();
    }


}

