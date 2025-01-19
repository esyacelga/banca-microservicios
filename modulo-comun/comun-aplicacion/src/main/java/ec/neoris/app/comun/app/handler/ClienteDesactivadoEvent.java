package ec.neoris.app.comun.app.handler;

public class ClienteDesactivadoEvent {
    private String clienteId;
    private boolean estado;

    public ClienteDesactivadoEvent(String clienteId, boolean estado) {
        this.clienteId = clienteId;
        this.estado = estado;
    }

    public String getClienteId() { return clienteId; }
    public boolean isEstado() { return estado; }
}