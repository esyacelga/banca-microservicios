package ec.banca.app.transacciones.servicio.dominio.helpers;

import ec.banca.app.excepcion.comun.dominio.valor.TipoCuenta;
import ec.banca.app.excepcion.comun.dominio.valor.TipoMovimiento;
import ec.banca.app.transacciones.servicio.dominio.decorator.ICuenta;
import ec.banca.app.transacciones.servicio.dominio.decorator.impl.AlertasTransaccion;
import ec.banca.app.transacciones.servicio.dominio.dto.CuentaDto;
import ec.banca.app.transacciones.servicio.dominio.dto.MovimientoRegistroDto;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestCuenta;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestCuentaActualizacion;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestMovimiento;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestMovimientoActualizacion;
import ec.banca.app.transacciones.servicio.dominio.dto.response.ResponseCuenta;
import ec.banca.app.transacciones.servicio.dominio.dto.response.ResponseMovimiento;
import ec.banca.app.transacciones.servicio.dominio.entidad.CuentaAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.entidad.MovimientoAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.exception.ClienteNotFoundDomainException;
import ec.banca.app.transacciones.servicio.dominio.exception.CuentaDomainException;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import ec.banca.app.transacciones.servicio.dominio.factory.CuentaFactory;
import ec.banca.app.transacciones.servicio.dominio.factory.MovimientoFactory;
import ec.banca.app.transacciones.servicio.dominio.mapper.TransaccionDomainMapper;
import ec.banca.app.transacciones.servicio.dominio.prototype.CuentaPrototypeRegistry;
import ec.banca.app.transacciones.servicio.dominio.puertos.output.ICuentaDomainRepository;
import ec.banca.app.transacciones.servicio.dominio.puertos.output.ITransaccionesDomainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

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
    public void inactivarCuentasPorCliente(String clienteId, Boolean estado) {
        try {
            cuentaRepository.inactivarCuentas(clienteId, estado);
        } catch (Exception e) {
            log.error("Error al inactivar cuentas por cliente", e);
        }

    }

    @Transactional
    public MovimientoRegistroDto actualizarMovimiento(RequestMovimientoActualizacion requestMovimiento) throws TransaccionDomainException {
        return transaccionesRepository.actualizarMovimiento(requestMovimiento);
    }

    @Transactional
    public ResponseMovimiento insertarMovimiento(RequestMovimiento requestMovimiento) throws TransaccionDomainException {
        BigDecimal saldoActual = cuentaRepository.obtenerSaldoActual(requestMovimiento.getNumeroCuenta());
        MovimientoAggregateRoot aggregateRoot = MovimientoFactory.generarMovimiento(requestMovimiento, saldoActual);
        aggregateRoot.validar();
        aggregateRoot.inicializar();
        MovimientoRegistroDto mov = transaccionesRepository.insertarMovimiento(aggregateRoot);
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
        CuentaAggregateRoot cuentaAhorros = CuentaFactory.crearCuenta(requestCuenta);
        cuentaAhorros.validar();
        cuentaAhorros.inicializar();
        Optional<CuentaDto> cuentaDto = cuentaRepository.insertarCuentaPersona(cuentaAhorros);
        if (requestCuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0)
            registrarMovimiento(requestCuenta, cuentaDto.get().getSaldo());

        return ResponseCuenta.builder()
                .uuidCuenta(cuentaDto.get().getUuidCuenta())
                .mensaje("Cuenta registrada exitosamente")
                .build();
    }

    @Transactional
    public void registrarMovimiento(RequestCuenta requestCuenta, BigDecimal saldo) {
        transaccionesRepository.insertarMovimiento(
                RequestMovimiento.builder()
                        .numeroCuenta(requestCuenta.getNumeroCuenta().toString())
                        .tipoMovimiento(TipoMovimiento.CREDITO.getValue())
                        .valor(requestCuenta.getSaldo())
                        .build(), saldo);
    }


}

