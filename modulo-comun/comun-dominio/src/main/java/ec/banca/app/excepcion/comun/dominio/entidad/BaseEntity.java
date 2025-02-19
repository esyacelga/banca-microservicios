package ec.banca.app.excepcion.comun.dominio.entidad;

import java.util.Objects;

public abstract class BaseEntity<ID> {


    private ID id;


    public ID getId() {
        return id;
    }


    public void setId(ID id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;

        // Asegúrate de manejar el caso cuando los IDs son null
        if (this.id == null || that.id == null) {
            return false;  // O devuelve una excepción, dependiendo del contexto
        }
        return id.equals(that.id);
    }


    @Override
    public int hashCode() {
        int code = Objects.hash(id);
        return Objects.hash(code);
    }
}
