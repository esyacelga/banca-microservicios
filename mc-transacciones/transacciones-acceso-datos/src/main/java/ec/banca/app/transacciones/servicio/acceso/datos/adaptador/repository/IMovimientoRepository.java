package ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository;

import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Movimientos;
import ec.banca.app.transacciones.servicio.dominio.dto.MovientoReporte;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMovimientoRepository {

    public Movimientos insertarMovimiento(Movimientos movimientos);

    public Optional<Movimientos> buscarMovimientoPorId(UUID uuidMovimiento);

    public Movimientos actualizarMovimiento(Movimientos movimientos);

    public List<MovientoReporte> obtenerMovimientosPorRango(LocalDateTime fechaInicial, LocalDateTime fechaFinal);

    public List<MovientoReporte> obtenerMovimientosPorRango(LocalDateTime fechaInicial, LocalDateTime fechaFinal, String clienteId);
}
