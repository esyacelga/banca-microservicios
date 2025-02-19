package ec.banca.app.transacciones.servicio.dominio.puertos.input;

import ec.banca.app.transacciones.servicio.dominio.dto.CuentaDto;
import ec.banca.app.transacciones.servicio.dominio.dto.MovientoReporte;
import ec.banca.app.transacciones.servicio.dominio.dto.MovimientoRegistroDto;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestCuenta;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestCuentaActualizacion;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestMovimiento;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestMovimientoActualizacion;
import ec.banca.app.transacciones.servicio.dominio.dto.response.ResponseCuenta;
import ec.banca.app.transacciones.servicio.dominio.dto.response.ResponseMovimiento;
import ec.banca.app.transacciones.servicio.dominio.exception.CuentaDomainException;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ITransaccionesAppService {

    ResponseCuenta insertarCuentaPersona(@Valid RequestCuenta requestCuenta) throws CuentaDomainException;

    ResponseCuenta actualizarCuentaPersona(@Valid RequestCuentaActualizacion cuentaActualizacion) throws CuentaDomainException;

    ResponseMovimiento insertarMovimiento(@Valid RequestMovimiento requestMovimiento) throws TransaccionDomainException;

    CuentaDto obtenerCuentaPorNumero(@Valid String numeroCuenta) throws CuentaDomainException;

    public MovimientoRegistroDto buscarMovimientoPorId(@Valid UUID uuidMovimiento) throws TransaccionDomainException;

    public ResponseMovimiento actualizarMovimiento(@Valid RequestMovimientoActualizacion requestMovimiento) throws TransaccionDomainException;

    public List<MovientoReporte> obtenerMovimientosPorRango(@Valid LocalDateTime fechaInicial, @Valid LocalDateTime fechaFinal) throws TransaccionDomainException;

    public List<MovientoReporte> obtenerMovimientosPorRangoCliente(@Valid LocalDateTime fechaInicial, @Valid LocalDateTime fechaFinal, String clienteId) throws TransaccionDomainException;

}
