package ec.banca.app.transacciones.servicio.dominio.helpers;

import ec.banca.app.transacciones.servicio.dominio.dto.MovientoReporte;
import ec.banca.app.transacciones.servicio.dominio.dto.MovimientoRegistroDto;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import ec.banca.app.transacciones.servicio.dominio.puertos.output.ITransaccionesDomainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class TransaccionQueryHelper {
    private final ITransaccionesDomainRepository transaccionesDomainRepository;

    public TransaccionQueryHelper(ITransaccionesDomainRepository transaccionesDomainRepository) {
        this.transaccionesDomainRepository = transaccionesDomainRepository;
    }

    public MovimientoRegistroDto buscarMovimientoPorId(UUID uuidMovimiento) throws TransaccionDomainException {
        return transaccionesDomainRepository.buscarMovimientoPorId(uuidMovimiento);
    }

    public List<MovientoReporte> obtenerMovimientosPorRango(LocalDateTime fechaInicial, LocalDateTime fechaFinal) throws TransaccionDomainException {
        if (fechaInicial.isAfter(fechaFinal) || fechaInicial.isEqual(fechaFinal)) {
            throw new TransaccionDomainException("La fecha inicial debe ser menor a la fecha final.");
        }
        return transaccionesDomainRepository.obtenerMovimientosPorRango(fechaInicial, fechaFinal);
    }

    public List<MovientoReporte> obtenerMovimientosPorRango(LocalDateTime fechaInicial, LocalDateTime fechaFinal, String clienteId) throws TransaccionDomainException {
        if (fechaInicial.isAfter(fechaFinal) || fechaInicial.isEqual(fechaFinal)) {
            throw new TransaccionDomainException("La fecha inicial debe ser menor a la fecha final.");
        }
        return transaccionesDomainRepository.obtenerMovimientosPorRango(fechaInicial, fechaFinal, clienteId);
    }

}
