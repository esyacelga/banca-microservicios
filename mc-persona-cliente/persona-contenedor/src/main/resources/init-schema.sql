CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS public.persona
(
    id UUID PRIMARY KEY, -- Ahora la aplicación genera el UUID
    nombre VARCHAR(255) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    edad INT NOT NULL,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

ALTER TABLE IF EXISTS public.persona
    OWNER TO postgres;

-- Tabla Cliente
CREATE TABLE IF NOT EXISTS public.cliente
(
    id UUID PRIMARY KEY, -- Hereda la PK de Persona
    cliente_id VARCHAR(50) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL,
    FOREIGN KEY (id) REFERENCES persona(id) ON DELETE CASCADE
);

ALTER TABLE IF EXISTS public.cliente
    OWNER TO postgres;


CREATE TABLE IF NOT EXISTS public.cuenta
(
    id            UUID        NOT NULL,
    id_cliente    UUID        NOT NULL,
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,
    tipo_cuenta   VARCHAR(50) NOT NULL,
    saldo_inicial NUMERIC(15, 2)       DEFAULT 0,
    estado        BOOLEAN     NOT NULL DEFAULT true,
    CONSTRAINT cuenta_pkey PRIMARY KEY (id),
    CONSTRAINT fk_cuenta_cliente FOREIGN KEY (id_cliente)
    REFERENCES public.cliente (id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    );

ALTER TABLE IF EXISTS public.cuenta
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.movimientos
(
    id               UUID           NOT NULL,
    id_cuenta        UUID           NOT NULL REFERENCES cuenta (id),
    fecha_movimiento TIMESTAMP      NOT NULL DEFAULT NOW(),
    tipo_movimiento  VARCHAR(50)    NOT NULL,
    valor            NUMERIC(15, 2) NOT NULL,
    saldo            NUMERIC(15, 2) NOT NULL,
    CONSTRAINT movimientos_pkey PRIMARY KEY (id),
    CHECK (valor <> 0)
    );

CREATE TABLE IF NOT EXISTS public.auditoria_transacciones
(
    id                UUID        NOT NULL DEFAULT uuid_generate_v4(),
    id_movimiento     UUID        NOT NULL,
    accion            VARCHAR(50) NOT NULL,
    usuario_auditoria VARCHAR(50),
    fecha_auditoria   TIMESTAMP            DEFAULT NOW(),
    detalles          JSONB,
    CONSTRAINT auditoria_transacciones_pkey PRIMARY KEY (id)
    );

CREATE OR REPLACE FUNCTION registrar_auditoria()
    RETURNS TRIGGER AS '
    BEGIN
        IF (TG_OP = ''DELETE'') THEN
            INSERT INTO auditoria_transacciones (id_movimiento, accion, usuario_auditoria, detalles)
            VALUES (OLD.id, TG_OP, current_user, row_to_json(OLD));
        ELSE
            INSERT INTO auditoria_transacciones (id_movimiento, accion, usuario_auditoria, detalles)
            VALUES (NEW.id, TG_OP, current_user, row_to_json(NEW));
        END IF;
        RETURN NEW;
    END;
' LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER trigger_auditoria_movimientos
    AFTER INSERT OR UPDATE OR DELETE
                    ON movimientos
                        FOR EACH ROW
                        EXECUTE FUNCTION registrar_auditoria();
