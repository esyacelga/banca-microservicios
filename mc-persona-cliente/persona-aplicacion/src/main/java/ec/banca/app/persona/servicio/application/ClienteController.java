package ec.banca.app.persona.servicio.application;

import ec.banca.app.persona.servicio.dominio.dto.request.RequestCliente;
import ec.banca.app.persona.servicio.dominio.dto.response.ResponseCliente;
import ec.banca.app.persona.servicio.dominio.dto.response.ResponseClientePersona;
import ec.banca.app.persona.servicio.dominio.puertos.input.IClienteAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/clientes", produces = "application/json")
@RequiredArgsConstructor
public class ClienteController {

    private final IClienteAppService clienteAppService;

    @PostMapping("/")
    public ResponseEntity<ResponseCliente> insertarCliente(@RequestBody RequestCliente cliente) {
        return ResponseEntity.ok(clienteAppService.insertarCliente(cliente));
    }

    @PutMapping("/{uuidCliente}")
    public ResponseEntity<ResponseCliente> actualizaCliente(
            @PathVariable String uuidCliente,
            @RequestBody RequestCliente cliente) {
        UUID uuid = UUID.fromString(uuidCliente);
        ResponseCliente updatedCliente = clienteAppService.updateCliente(uuid, cliente);
        return ResponseEntity.ok(updatedCliente);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ResponseClientePersona> buscarClientePorID(@PathVariable String clienteId) {
        return ResponseEntity.ok(clienteAppService.buscarClientePorId(clienteId));
    }

    @GetMapping("buscarClientePorIdentificacion/{identificacion}")
    public ResponseEntity<ResponseClientePersona> buscarClientePorIdentificacion(@PathVariable String identificacion) {
        return ResponseEntity.ok(clienteAppService.buscarClientePorIdentificacion(identificacion));
    }

    @DeleteMapping("/{uuidCliente}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable String uuidCliente) {
        clienteAppService.elimininarCliente(UUID.fromString(uuidCliente));
        return ResponseEntity.noContent().build();

    }
}
