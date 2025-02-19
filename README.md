# **Banca  App**

Este proyecto es una aplicación Java con una arquitectura hexagonal, diseñada para la gestión de movimientos y cuentas. A continuación, se describen los pasos para la descarga, configuración y ejecución del proyecto, así como las pruebas disponibles.

---

## **Requisitos Previos**

1. **Java**: JDK 21 instalado.
2. **Maven**: Instalado y configurado en el `PATH`.
3. **Docker**: Instalado para la ejecución de contenedores.
4. **Postman**: Para realizar pruebas de integración con los endpoints disponibles.

---

## **Paso a Paso para la Ejecución**

### **1. Clonar el Repositorio**
Descarga el código fuente desde el repositorio remoto:

```bash
git clone https://github.com/esyacelga/banca-app.git
cd banca-app
```

### **2. Construir el Proyecto**
Ejecuta el siguiente comando para compilar y construir el proyecto utilizando Maven:

```bash
mvn clean install
```

Este comando:
- Limpia los artefactos anteriores.
- Compila el proyecto.
- Genera los artefactos `.jar` necesarios en la carpeta `target`.

### **3. Levantar los Contenedores con Docker**
Ejecuta el siguiente comando para construir y levantar los contenedores necesarios (base de datos y aplicación):

```bash
docker-compose up --build
```

Esto:
- Crea y levanta los contenedores definidos en el archivo `docker-compose.yml`.
- Construye las imágenes de los microservicios.
- **Ejecuta automáticamente el script de la base de datos al iniciar la aplicación.**

### **4. Probar los Endpoints con Postman**
El archivo `banca endpoint.postman_collection.json` contiene una colección de pruebas para Postman.

1. Importa este archivo en Postman:
    - Ve a **File > Import** en Postman.
    - Selecciona el archivo `banca endpoint.postman_collection.json`.

2. Ejecuta las solicitudes desde Postman:
    - Todos los endpoints están configurados y listos para ser probados.

---

## **Arquitectura del Proyecto**

El proyecto utiliza una arquitectura **hexagonal** con los siguientes módulos principales:

1. **Core**:
    - Contiene las reglas de negocio y la lógica del dominio.
    - Es independiente de las capas externas.

2. **Application**:
    - Define casos de uso específicos y la interacción con el dominio.

3. **Infrastructure**:
    - Maneja la interacción con la base de datos, REST controllers y configuraciones.

4. **Ports & Adapters**:
    - Define interfaces para la interacción entre los módulos.

---

## **Endpoints Disponibles**

Los endpoints están documentados en la colección de Postman, pero aquí tienes un resumen:

1. **Crear Movimiento**
    - **POST** `/movimientos`
    - Crea un nuevo movimiento en el sistema.

2. **Actualizar Movimiento**
    - **PUT** `/movimientos`
    - Actualiza un movimiento existente.

3. **Buscar Movimiento por ID**
    - **GET** `/movimientos/{uuidMovimiento}`
    - Obtiene un movimiento específico por su UUID.

---

## **Notas Adicionales**

- **Base de Datos**: El proyecto utiliza PostgreSQL, configurado para ejecutarse dentro de un contenedor Docker.
- **Ejecución Automática del Script**: Al iniciar la aplicación, el script de la base de datos se ejecuta automáticamente.
- **Arquitectura**: El diseño hexagonal permite mantener un desacoplamiento entre la lógica de negocio y las dependencias externas.
- **Pruebas de Integración**: Se pueden realizar con Postman utilizando el archivo mencionado o implementando más pruebas en `src/test`.

---

## **Comandos Rápidos**

1. Descargar y compilar:
   ```bash
   git clone https://github.com/esyacelga/banca-microservicios.git
   cd banca-microservicios
   mvn clean install
   ```

2. Levantar la aplicación:
   ```bash
   docker-compose up --build
   ```

3. Eliminar contenedores y volúmenes Docker:
   ```bash
   docker rm $(docker ps -aq)
   docker rmi $(docker images -q)
   docker system prune -a --volumes -f
   docker volume rm $(docker volume ls -q)
   mvn clean install -T 1C
   ```

