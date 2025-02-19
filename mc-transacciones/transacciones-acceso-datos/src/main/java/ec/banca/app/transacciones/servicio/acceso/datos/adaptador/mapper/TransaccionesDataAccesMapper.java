package ec.banca.app.transacciones.servicio.acceso.datos.adaptador.mapper;

import ec.banca.app.excepcion.comun.dominio.valor.TipoMovimiento;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Cliente;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Cuenta;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Movimientos;
import ec.banca.app.transacciones.servicio.dominio.dto.CuentaDto;
import ec.banca.app.transacciones.servicio.dominio.dto.MovimientoRegistroDto;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestMovimiento;
import ec.banca.app.transacciones.servicio.dominio.entidad.CuentaAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.entidad.MovimientoAggregateRoot;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class TransaccionesDataAccesMapper {
    public Optional<CuentaDto> cuentaToCuentaDto(Optional<Cuenta> cuenta) {
        return cuenta.map(this::cuentaToCuentaDto);
    }

    public MovimientoRegistroDto transformarEntidadToDto(Movimientos movimientos) {
        return MovimientoRegistroDto.builder()
                .tipoMovimiento(TipoMovimiento.valueOf(movimientos.getTipoMovimiento()))
                .saldo(movimientos.getSaldo())
                .uuidMovimiento(movimientos.getId())
                .numeroCuenta(movimientos.getCuenta().getNumeroCuenta())
                .valor(movimientos.getValor())
                .build();
    }

    public Movimientos movimientoAggregateRootToEntidad(MovimientoAggregateRoot aggregateRoot, Cuenta cuenta) {
        return Movimientos.builder()
                .tipoMovimiento(aggregateRoot.getTipoMovimiento())
                .cuenta(cuenta)
                .id(UUID.randomUUID())
                .saldo(aggregateRoot.getTotal())
                .fechaMovimiento(LocalDateTime.now())
                .valor(aggregateRoot.getValor())
                .build();
    }

    public Movimientos transformarRequestToEntidad(RequestMovimiento requestMovimiento, Cuenta cuenta, BigDecimal nuevoSaldo) {
        return Movimientos.builder()
                .tipoMovimiento(requestMovimiento.getTipoMovimiento())
                .cuenta(cuenta)
                .id(UUID.randomUUID())
                .saldo(nuevoSaldo)
                .fechaMovimiento(LocalDateTime.now())
                .valor(requestMovimiento.getValor())
                .build();
    }

    public CuentaDto cuentaToCuentaDto(Cuenta cuenta) {
        return CuentaDto.builder()
                .uuidCuenta(cuenta.getId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .saldo(cuenta.getSaldoInicial())
                .estado(cuenta.getEstado())
                .clienteId(cuenta.getCliente().getClienteId())
                .build();
    }

    public Cuenta cuentaDtoToRequestCuenta(CuentaDto cuentaDto, UUID idCuenta, Cliente cliente) {
        return Cuenta.builder()
                .id(idCuenta)
                .numeroCuenta(cuentaDto.getNumeroCuenta().toString())
                .saldoInicial(cuentaDto.getSaldo())
                .cliente(cliente)
                .tipoCuenta(cuentaDto.getTipoCuenta().toString())
                .estado(cuentaDto.getEstado())
                .build();
    }

    public Cuenta cuentaDtoToRequestCuenta(CuentaAggregateRoot cuentaDto, UUID idCuenta, Cliente cliente) {
        return Cuenta.builder()
                .id(idCuenta)
                .numeroCuenta(cuentaDto.getNumeroCuenta().toString())
                .saldoInicial(cuentaDto.getSaldo())
                .cliente(cliente)
                .tipoCuenta(cuentaDto.getTipoCuenta().toString())
                .estado(cuentaDto.getEstado())
                .build();
    }
}
