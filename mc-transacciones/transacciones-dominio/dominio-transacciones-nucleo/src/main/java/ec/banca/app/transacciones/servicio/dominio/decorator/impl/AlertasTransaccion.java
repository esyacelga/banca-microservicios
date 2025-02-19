package ec.banca.app.transacciones.servicio.dominio.decorator.impl;

import ec.banca.app.transacciones.servicio.dominio.decorator.ICuenta;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class AlertasTransaccion extends CuentaDecorador {

    public AlertasTransaccion(ICuenta cuenta) {
        super(cuenta);
    }

    public String obtenerDescripcion() {
        return super.obtenerDescripcion() + " con Alertas de Transacción";
    }

    public BigDecimal obtenerSaldo() {
        log.info("🔔 Alerta: Consulta de saldo realizada.");
        return super.obtenerSaldo();
    }
}
