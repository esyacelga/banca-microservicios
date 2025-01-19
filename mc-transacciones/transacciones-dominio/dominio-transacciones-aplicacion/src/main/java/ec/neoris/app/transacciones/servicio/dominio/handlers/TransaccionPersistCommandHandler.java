package ec.neoris.app.transacciones.servicio.dominio.handlers;

import ec.neoris.app.transacciones.servicio.dominio.dto.MovimientoRegistroDto;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestCuenta;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestCuentaActualizacion;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestMovimiento;
import ec.neoris.app.transacciones.servicio.dominio.dto.request.RequestMovimientoActualizacion;
import ec.neoris.app.transacciones.servicio.dominio.dto.response.ResponseCuenta;
import ec.neoris.app.transacciones.servicio.dominio.dto.response.ResponseMovimiento;
import ec.neoris.app.transacciones.servicio.dominio.exception.*;
import ec.neoris.app.transacciones.servicio.dominio.helpers.CuentaQueryHelper;
import ec.neoris.app.transacciones.servicio.dominio.helpers.TransaccionPersistHelper;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransaccionPersistCommandHandler {
    public final TransaccionPersistHelper transaccionPersistHelper;
    public final CuentaQueryHelper cuentaQueryHelper;

    public TransaccionPersistCommandHandler(TransaccionPersistHelper transaccionPersistHelper, CuentaQueryHelper cuentaQueryHelper) {
        this.transaccionPersistHelper = transaccionPersistHelper;
        this.cuentaQueryHelper = cuentaQueryHelper;
    }


    public ResponseMovimiento insertarMovimiento(RequestMovimiento requestMovimiento) throws TransaccionDomainException, CuentaNotFoundDomainException {
        try {
            return transaccionPersistHelper.insertarMovimiento(requestMovimiento);
        } catch (CuentaNotFoundDomainException ex) {
            throw ex;
        } catch (TransaccionDomainException ex) {
            throw ex;
        } catch (ConstraintViolationException ex) {
            throw new TransaccionesConstrainViolationException("Violación de restricciones al insertar el movimiento con No {" + requestMovimiento.getNumeroCuenta() + "}", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new TransaccionesConstrainViolationException("Error de integridad en la base de datos al insertar el movimiento con No {" + requestMovimiento.getNumeroCuenta() + "}", ex);
        } catch (Exception ex) {
            throw new TransaccionDomainException("Error al registrar el movimiento", ex);
        }

    }

    public ResponseCuenta insertarCuentaPersona(RequestCuenta requestCuenta) throws CuentaDomainException, ClienteNotFoundDomainException {
        try {
            return transaccionPersistHelper.insertarCuentaPersona(requestCuenta);
        } catch (ClienteNotFoundDomainException ex) {
            throw ex;
        } catch (CuentaDomainException ex) {
            throw ex;
        } catch (ConstraintViolationException ex) {
            throw new TransaccionesConstrainViolationException("Violación de restricciones al actualizar el cuenta con No {" + requestCuenta.getNumeroCuenta() + "}", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new TransaccionesConstrainViolationException("Error de integridad en la base de datos al actualizar el cuenta con No {" + requestCuenta.getNumeroCuenta() + "}", ex);
        } catch (Exception ex) {
            throw new CuentaDomainException("Error al registrar la cuenta", ex);
        }
    }

    public ResponseCuenta actualizarCuentaPersona(RequestCuentaActualizacion cuentaActualizacion) throws CuentaDomainException {
        return transaccionPersistHelper.actualizarCuentaPersona(cuentaActualizacion);
    }

    public MovimientoRegistroDto actualizarMovimiento(RequestMovimientoActualizacion requestMovimiento) throws TransaccionDomainException {
        return transaccionPersistHelper.actualizarMovimiento(requestMovimiento);
    }

}
