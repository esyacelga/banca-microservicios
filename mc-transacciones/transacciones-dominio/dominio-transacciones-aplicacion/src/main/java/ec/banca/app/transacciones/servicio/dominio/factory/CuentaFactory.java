package ec.banca.app.transacciones.servicio.dominio.factory;

import ec.banca.app.excepcion.comun.dominio.valor.CuentaId;
import ec.banca.app.excepcion.comun.dominio.valor.TipoCuenta;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestCuenta;
import ec.banca.app.transacciones.servicio.dominio.entidad.CuentaAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.entidad.CuentaAhorrosAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.entidad.CuentaCorrienteAggregateRoot;

import java.math.BigDecimal;
import java.util.UUID;

public class CuentaFactory {
    public static CuentaAggregateRoot crearCuenta(
            String tipoCuenta, Integer numeroCuenta, String clienteId, BigDecimal saldo) {

        if (TipoCuenta.AHORROS.getTipo().equalsIgnoreCase(tipoCuenta)) {
            return CuentaAhorrosAggregateRoot.builder()
                    .cuentaId(new CuentaId(UUID.randomUUID()))
                    .numeroCuenta(numeroCuenta)
                    .clienteId(clienteId)
                    .tipoCuenta("AHORROS")
                    .saldo(saldo)
                    .estado(true)
                    .build();
        } else if (TipoCuenta.CORRIENTE.getTipo().equalsIgnoreCase(tipoCuenta)) {
            return CuentaCorrienteAggregateRoot.builder()
                    .cuentaId(new CuentaId(UUID.randomUUID()))
                    .numeroCuenta(numeroCuenta)
                    .clienteId(clienteId)
                    .tipoCuenta("CORRIENTE")
                    .saldo(saldo)
                    .estado(true)
                    .build();
        } else {
            throw new IllegalArgumentException("Tipo de cuenta no soportado: " + tipoCuenta);
        }
    }


    public static CuentaAggregateRoot crearCuenta(RequestCuenta request) {
        if (TipoCuenta.AHORROS.getTipo().equalsIgnoreCase(request.getTipoCuenta())) {
            return CuentaAhorrosAggregateRoot.builder()
                    .numeroCuenta(request.getNumeroCuenta())
                    .clienteId(request.getClienteId())
                    .tipoCuenta(TipoCuenta.AHORROS.getTipo())
                    .saldo(request.getSaldo())
                    .estado(request.getEstado())
                    .build();
        } else if (TipoCuenta.CORRIENTE.getTipo().equalsIgnoreCase(request.getTipoCuenta())) {
            return CuentaCorrienteAggregateRoot.builder()
                    .numeroCuenta(request.getNumeroCuenta())
                    .clienteId(request.getClienteId())
                    .tipoCuenta(TipoCuenta.CORRIENTE.getTipo())
                    .saldo(request.getSaldo())
                    .estado(request.getEstado())
                    .build();
        } else {
            throw new IllegalArgumentException("Tipo de cuenta no soportado: " + request.getTipoCuenta());
        }
    }


}
