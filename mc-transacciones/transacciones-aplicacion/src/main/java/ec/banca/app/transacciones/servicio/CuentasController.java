package ec.banca.app.transacciones.servicio;

import ec.banca.app.transacciones.servicio.dominio.dto.CuentaDto;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestCuenta;
import ec.banca.app.transacciones.servicio.dominio.dto.request.RequestCuentaActualizacion;
import ec.banca.app.transacciones.servicio.dominio.dto.response.ResponseCuenta;
import ec.banca.app.transacciones.servicio.dominio.puertos.input.ITransaccionesAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/cuentas", produces = "application/json")
@RequiredArgsConstructor
public class CuentasController {
    private final ITransaccionesAppService transaccionesAppService;

    @PostMapping
    public ResponseEntity<ResponseCuenta> insertarCuentaPersona(@RequestBody RequestCuenta requestCuenta) {
        log.info("Insertando nueva cuenta para la persona: {}", requestCuenta);
        return ResponseEntity.ok(transaccionesAppService.insertarCuentaPersona(requestCuenta));
    }

    @PutMapping
    public ResponseEntity<ResponseCuenta> actualizarCuentaPersona(@RequestBody RequestCuentaActualizacion requestCuenta) {
        log.info("Actualizando cuenta: {}", requestCuenta);
        return ResponseEntity.ok(transaccionesAppService.actualizarCuentaPersona(requestCuenta));
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<CuentaDto> obtenerCuentaPorNumero(@PathVariable String numeroCuenta) {
        log.info("Obteniendo cuenta por número: {}", numeroCuenta);
        return ResponseEntity.ok(transaccionesAppService.obtenerCuentaPorNumero(numeroCuenta));
    }

}
