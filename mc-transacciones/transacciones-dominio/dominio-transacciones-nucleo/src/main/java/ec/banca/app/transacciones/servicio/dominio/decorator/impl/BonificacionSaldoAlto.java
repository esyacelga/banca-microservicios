package ec.banca.app.transacciones.servicio.dominio.decorator.impl;

import ec.banca.app.transacciones.servicio.dominio.decorator.ICuenta;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BonificacionSaldoAlto extends CuentaDecorador {
    private static final BigDecimal LIMITE_BONIFICACION = new BigDecimal("5000");
    private static final BigDecimal BONIFICACION = new BigDecimal("1.02");

    public BonificacionSaldoAlto(ICuenta cuenta) {
        super(cuenta);
    }

    public String obtenerDescripcion() {
        return super.obtenerDescripcion() + " con Bonificación por Saldo Alto";
    }

    public BigDecimal obtenerSaldo() {
        if (super.obtenerSaldo().compareTo(LIMITE_BONIFICACION) > 0) { // Si saldo > 5000
            return super.obtenerSaldo().multiply(BONIFICACION).setScale(2, RoundingMode.HALF_UP);
        }
        return super.obtenerSaldo().setScale(2, RoundingMode.HALF_UP);
    }
}
