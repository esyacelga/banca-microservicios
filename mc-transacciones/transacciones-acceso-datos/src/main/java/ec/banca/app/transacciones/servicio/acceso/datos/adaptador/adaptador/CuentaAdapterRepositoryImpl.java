package ec.banca.app.transacciones.servicio.acceso.datos.adaptador.adaptador;

import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Cliente;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.entity.Cuenta;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.mapper.TransaccionesDataAccesMapper;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository.ICuentaRepository;
import ec.banca.app.transacciones.servicio.acceso.datos.adaptador.repository.IJPAClienteRepository;
import ec.banca.app.transacciones.servicio.dominio.dto.CuentaDto;
import ec.banca.app.transacciones.servicio.dominio.entidad.CuentaAggregateRoot;
import ec.banca.app.transacciones.servicio.dominio.exception.ClienteNotFoundDomainException;
import ec.banca.app.transacciones.servicio.dominio.exception.CuentaDomainException;
import ec.banca.app.transacciones.servicio.dominio.exception.CuentaNotFoundDomainException;
import ec.banca.app.transacciones.servicio.dominio.puertos.output.ICuentaDomainRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class CuentaAdapterRepositoryImpl implements ICuentaDomainRepository {
    private final ICuentaRepository cuentaDomainRepository;
    private final TransaccionesDataAccesMapper transaccionesDataAccesMapper;
    private final IJPAClienteRepository jpaClienteRepository;


    public CuentaAdapterRepositoryImpl(ICuentaRepository cuentaDomainRepository,
                                       TransaccionesDataAccesMapper transaccionesDataAccesMapper,
                                       IJPAClienteRepository jpaClienteRepository) {
        this.cuentaDomainRepository = cuentaDomainRepository;
        this.transaccionesDataAccesMapper = transaccionesDataAccesMapper;
        this.jpaClienteRepository = jpaClienteRepository;
    }

    @Override
    public Optional<CuentaDto> insertarCuentaPersona(CuentaDto requestCuenta) throws ClienteNotFoundDomainException {
        Optional<Cliente> clienteOptional = jpaClienteRepository.findClienteByClienteId(requestCuenta.getClienteId());
        Cliente cliente = clienteOptional.orElseThrow(() ->
                new ClienteNotFoundDomainException("Cliente no encontrado con el ID ESPECIFICADO"));
        Cuenta cuenta = cuentaDomainRepository.insertarCuentaPersona(transaccionesDataAccesMapper.cuentaDtoToRequestCuenta(requestCuenta, UUID.randomUUID(), cliente));
        return Optional.of(transaccionesDataAccesMapper.cuentaToCuentaDto(cuenta));
    }

    @Override
    public Optional<CuentaDto> insertarCuentaPersona(CuentaAggregateRoot aggregateRoot) throws ClienteNotFoundDomainException {
        Optional<Cliente> clienteOptional = jpaClienteRepository.findClienteByClienteId(aggregateRoot.getClienteId());
        Cliente cliente = clienteOptional.orElseThrow(() ->
                new ClienteNotFoundDomainException("Cliente no encontrado con el ID ESPECIFICADO"));
        Cuenta cuenta = cuentaDomainRepository.insertarCuentaPersona(transaccionesDataAccesMapper.cuentaDtoToRequestCuenta(aggregateRoot, UUID.randomUUID(), cliente));
        return Optional.of(transaccionesDataAccesMapper.cuentaToCuentaDto(cuenta));
    }

    @Override
    public Integer obtenerSiguienteSecuencial() {
        return cuentaDomainRepository.obtenerSiguienteSecuencial();
    }

    @Override
    public BigDecimal obtenerSaldoActual(String numeroCuenta) throws CuentaNotFoundDomainException {
        try {
            Optional<BigDecimal> saldoOptional = cuentaDomainRepository.obtenerSaldoActual(numeroCuenta);
            return saldoOptional.orElseThrow(() -> new CuentaNotFoundDomainException("Cuenta no ha sido encontrada"));
        } catch (EntityNotFoundException exception) {
            throw new CuentaNotFoundDomainException("No se ha encontrado la cuenta " + numeroCuenta + " ", exception);
        }
    }

    @Override
    public CuentaDto actualizarCuenta(CuentaDto cuentaDto) throws CuentaDomainException {
        Optional<Cliente> clienteOptional = jpaClienteRepository.findClienteByClienteId(cuentaDto.getClienteId());
        Cliente cliente = clienteOptional.orElseThrow(() ->
                new ClienteNotFoundDomainException("Cliente no encontrado con el ID ESPECIFICADO"));
        if (!esNumero(cuentaDto.getNumeroCuenta()))
            throw new CuentaDomainException("No es un numero de cuenta valida");
        Cuenta cuenta = cuentaDomainRepository.actualizarCuenta(transaccionesDataAccesMapper.cuentaDtoToRequestCuenta(cuentaDto, clienteOptional.get().getId(), cliente));
        return transaccionesDataAccesMapper.cuentaToCuentaDto(cuenta);
    }

    @Override
    public void inactivarCuentas(String idCliente, Boolean activaDesactiva) throws CuentaDomainException, EntityNotFoundException {
        cuentaDomainRepository.inactivarCuentas(idCliente, activaDesactiva);
    }


    @Override
    public Optional<CuentaDto> obtenerCuentaPorNumero(String numeroCuenta) throws CuentaDomainException {
        Optional<Cuenta> cuentaOptional = cuentaDomainRepository.obtenerCuentaPorNumero(numeroCuenta);
        return transaccionesDataAccesMapper.cuentaToCuentaDto(cuentaOptional);
    }

    public boolean esNumero(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return false;
        }
        return cadena.matches("\\d+");
    }
}
