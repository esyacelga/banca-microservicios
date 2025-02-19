package ec.banca.app.transacciones.servicio.dominio.decorator.impl;

import ec.banca.app.transacciones.servicio.dominio.decorator.ICuenta;

import java.math.BigDecimal;

public abstract class CuentaDecorador implements ICuenta {
    protected ICuenta cuenta;

    public CuentaDecorador(ICuenta cuenta) {
        this.cuenta = cuenta;
    }

    public String obtenerDescripcion() {
        return cuenta.obtenerDescripcion();
    }

    public BigDecimal obtenerSaldo() {
        return cuenta.obtenerSaldo();
    }
}
