package ec.banca.app.transacciones.servicio.dominio.decorator;

import java.math.BigDecimal;

public interface ICuenta {
    String obtenerDescripcion();

    BigDecimal obtenerSaldo();
}
