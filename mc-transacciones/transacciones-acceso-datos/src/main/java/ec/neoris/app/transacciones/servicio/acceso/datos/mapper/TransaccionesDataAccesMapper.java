package ec.neoris.app.transacciones.servicio.acceso.datos.mapper;

import ec.neoris.app.transacciones.servicio.acceso.datos.entity.Cliente;
import ec.neoris.app.transacciones.servicio.acceso.datos.entity.Cuenta;
import ec.neoris.app.transacciones.servicio.dominio.dto.CuentaDto;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TransaccionesDataAccesMapper {
    public Optional<CuentaDto> cuentaToCuentaDto(Optional<Cuenta> cuenta) {
        return cuenta.map(this::cuentaToCuentaDto);
    }
    public CuentaDto cuentaToCuentaDto(Cuenta cuenta) {
        return CuentaDto.builder()
                .uuidCuenta(cuenta.getId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .saldo(cuenta.getSaldoInicial())
                .estado(cuenta.getEstado())
                .clienteId(cuenta.getCliente().getClienteId())
                .build();
    }

    public Cuenta cuentaDtoToRequestCuenta(CuentaDto cuentaDto, UUID idCuenta, Cliente cliente) {
        return Cuenta.builder()
                .id(idCuenta)
                .numeroCuenta(cuentaDto.getNumeroCuenta().toString())
                .saldoInicial(cuentaDto.getSaldo())
                .cliente(cliente)
                .tipoCuenta(cuentaDto.getTipoCuenta().toString())
                .estado(cuentaDto.getEstado())
                .build();
    }
}
