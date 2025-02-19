package ec.banca.app.transacciones.servicio.dominio;

import ec.banca.app.excepcion.comun.dominio.valor.TipoCuenta;
import ec.banca.app.excepcion.comun.dominio.valor.TipoMovimiento;
import ec.banca.app.transacciones.servicio.dominio.dto.CuentaDto;
import ec.banca.app.transacciones.servicio.dominio.dto.MovimientoRegistroDto;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestCuenta;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestCuentaActualizacion;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestMovimiento;
import ec.banca.app.transacciones.servicio.dominio.dto.response.ResponseCuenta;
import ec.banca.app.transacciones.servicio.dominio.dto.response.ResponseMovimiento;
import ec.banca.app.transacciones.servicio.dominio.entidad.CuentaAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.entidad.MovimientoAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.exception.TransaccionDomainException;
import ec.banca.app.transacciones.servicio.dominio.puertos.input.ITransaccionesAppService;
import ec.banca.app.transacciones.servicio.dominio.puertos.output.ICuentaDomainRepository;
import ec.banca.app.transacciones.servicio.dominio.puertos.output.ITransaccionesDomainRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TransaccionTestConfiguration.class)
@Slf4j
public class TransaccionAppTest {

    @Autowired
    ITransaccionesAppService transaccionesAppService;

    @Autowired
    ITransaccionesDomainRepository transaccionesRepository;

    @Autowired
    ICuentaDomainRepository cuentaRepository;


    @Test
    @DisplayName("Registrar cuenta")
    public void testGenerarCuenta() {

        when(cuentaRepository.obtenerSiguienteSecuencial())
                .thenReturn(1);
        when(cuentaRepository.insertarCuentaPersona(any(CuentaAggregateRoot.class)))
                .thenReturn(Optional.of(CuentaDto.builder()
                        .saldo(new BigDecimal(1000))
                        .numeroCuenta("777")
                        .estado(true)
                        .tipoCuenta(TipoCuenta.AHORROS)
                        .uuidCuenta(UUID.randomUUID())
                        .build()));

        ResponseCuenta responseCuenta = transaccionesAppService.insertarCuentaPersona(RequestCuenta.builder()
                .tipoCuenta(TipoCuenta.AHORROS.getTipo())
                .saldo(new BigDecimal(1000))
                .clienteId("CLI002")
                .numeroCuenta(777)
                .estado(true)
                .build());

        assertEquals("Cuenta registrada exitosamente", responseCuenta.getMensaje());
    }

    @Test
    @DisplayName("Actualizar cuenta")
    public void actualizarCuenta() {
        when(cuentaRepository.obtenerCuentaPorNumero("00001"))
                .thenReturn(Optional.of(CuentaDto.builder()
                        .saldo(new BigDecimal(1000))
                        .numeroCuenta("00001")
                        .estado(true)
                        .tipoCuenta(TipoCuenta.AHORROS)
                        .uuidCuenta(UUID.randomUUID())
                        .build()));

        when(cuentaRepository.actualizarCuenta(any(CuentaDto.class)))
                .thenReturn(CuentaDto.builder()
                        .saldo(new BigDecimal(1000))
                        .numeroCuenta("00001")
                        .estado(true)
                        .tipoCuenta(TipoCuenta.AHORROS)
                        .uuidCuenta(UUID.randomUUID())
                        .build());

        ResponseCuenta responseCuenta = transaccionesAppService.actualizarCuentaPersona(RequestCuentaActualizacion.builder()
                .tipoCuenta(TipoCuenta.AHORROS.getTipo())
                .numeroCuenta("00001")
                .estado(true)
                .saldo(new BigDecimal(1000))
                .build());
        assertEquals("Cuenta actualizada exitosamente", responseCuenta.getMensaje());
    }

    @Test
    @DisplayName("Registrar movimiento con saldo")
    public void registrarMovimiento() {
        when(cuentaRepository.obtenerSaldoActual(eq("001001")))
                .thenReturn(new BigDecimal(10000));
        when(transaccionesRepository.insertarMovimiento(any(MovimientoAggregateRoot.class)))
                .thenReturn(MovimientoRegistroDto.builder()
                        .uuidMovimiento(UUID.randomUUID())
                        .valor(new BigDecimal(9000))
                        .build());
        ResponseMovimiento responseMovimiento = transaccionesAppService.insertarMovimiento(RequestMovimiento.builder()
                .tipoMovimiento(TipoMovimiento.DEBITO.getValue())
                .valor(new BigDecimal(1000))
                .numeroCuenta("001001")
                .build());
        assertEquals("Movimiento Registrado exitosamente", responseMovimiento.getMensaje());
    }

    @Test
    @DisplayName("Registrar movimiento sin saldo")
    public void registrarMovimientoSinSaldo() {
        when(cuentaRepository.obtenerSaldoActual(eq("001001")))
                .thenReturn(new BigDecimal(10));
        TransaccionDomainException transaccionDomainException = assertThrows(TransaccionDomainException.class,
                () -> transaccionesAppService.insertarMovimiento(RequestMovimiento.builder()
                        .tipoMovimiento(TipoMovimiento.DEBITO.getValue())
                        .valor(new BigDecimal(1000))
                        .numeroCuenta("001001")
                        .build()));
        assertEquals("Saldo insuficiente", transaccionDomainException.getMessage());
    }


}


