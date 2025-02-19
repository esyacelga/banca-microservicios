package ec.banca.app.transacciones.servicio.dominio.decorator.impl;

import ec.banca.app.transacciones.servicio.dominio.decorator.ICuenta;

import java.math.BigDecimal;

public class SeguroProteccion extends CuentaDecorador {
    public SeguroProteccion(ICuenta cuenta) {
        super(cuenta);
    }

    public String obtenerDescripcion() {
        return super.obtenerDescripcion() + " con Protección contra robo";
    }

    public BigDecimal obtenerSaldo() {
        return super.obtenerSaldo().max(BigDecimal.ZERO);
    }
}
