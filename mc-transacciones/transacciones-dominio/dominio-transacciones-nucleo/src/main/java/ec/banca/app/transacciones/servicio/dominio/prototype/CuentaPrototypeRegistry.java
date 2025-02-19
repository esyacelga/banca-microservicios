package ec.banca.app.transacciones.servicio.dominio.prototype;

import ec.banca.app.excepcion.comun.dominio.valor.TipoCuenta;
import ec.banca.app.transacciones.servicio.dominio.entidad.CuentaAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.entidad.CuentaAhorrosAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.entidad.CuentaCorrienteAggregateRoot;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CuentaPrototypeRegistry {
    private static final Map<String, CuentaAggregateRoot> prototipos = new HashMap<>();

    static {
        prototipos.put(TipoCuenta.AHORROS.getTipo(), CuentaAhorrosAggregateRoot.builder()
                .numeroCuenta(1001)
                .tipoCuenta(TipoCuenta.AHORROS.getTipo())
                .saldo(new BigDecimal("500"))
                .estado(true)
                .build());
        prototipos.put(TipoCuenta.CORRIENTE.getTipo(),
                CuentaCorrienteAggregateRoot.builder()
                        .numeroCuenta(2001)
                        .tipoCuenta(TipoCuenta.CORRIENTE.getTipo())
                        .saldo(new BigDecimal("1000"))
                        .estado(true)
                        .build());
    }

    public static CuentaAggregateRoot obtenerPrototipo(String tipo) {
        return prototipos.get(tipo).clone();
    }
}
