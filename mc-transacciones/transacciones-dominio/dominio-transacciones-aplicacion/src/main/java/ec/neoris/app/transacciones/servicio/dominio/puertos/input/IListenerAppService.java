package ec.neoris.app.transacciones.servicio.dominio.puertos.input;

public interface IListenerAppService {
    public void inactivarCuentas(String clienteId, Boolean activarInactivar);
}
