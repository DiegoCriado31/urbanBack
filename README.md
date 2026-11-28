# UrbanStyle API (`urbanBack`)

Backend REST desarrollado con **Spring Boot** y conectado a **MongoDB Atlas** para el proyecto **UrbanStyle**, correspondiente a la **Evaluación Parcial 3 — Desarrollo Fullstack II (`DSY1104`) — Duoc UC**.

---

## 1. Descripción General

`urbanBack` expone los servicios REST consumidos por el frontend en React (`urban`) en el puerto `8081`, gestionando autenticación basada en tokens **JWT**, control de acceso por roles, persistencia documental en **MongoDB Atlas** y documentación interactiva con **SpringDoc OpenAPI (Swagger)**.

---

## 2. Seguridad, Roles y Autenticación JWT

- **Política *stateless*:** se gestiona mediante filtro JWT (`JwtFilter`) y reglas de autorización en `SecurityConfig`.
- **Rutas públicas y documentación:** registro (`/api/usuarios/registro`), inicio de sesión (`/api/usuarios/login`), Swagger UI (`/swagger-ui/index.html`) y consultas públicas (`GET /api/productos/**` y `GET /api/blogs/**`).
- **Control por roles:**
  - **`SUPERVISOR`:** acceso total, incluyendo gestión de usuarios (`/api/usuarios/**`), productos y blogs.
  - **`COMERCIAL`:** gestión de catálogo (productos) y edición de blogs, sin acceso a gestión de usuarios.
  - **`CLIENTE`:** acceso de tienda/consulta según reglas de negocio y endpoints públicos.
- **Datos iniciales:** `DataInitializer` asegura la carga de cuentas iniciales al arrancar la aplicación.

---

## 3. Documentación y Pruebas en Swagger UI

Con la aplicación levantada en `http://localhost:8081`, la documentación interactiva está disponible en:

`http://localhost:8081/swagger-ui/index.html`

- Las peticiones a endpoints protegidos sin token o sin rol suficiente retornan `403`.
- Tras autenticar en `POST /api/usuarios/login` y configurar el token en **Authorize** (`bearerAuth`), las peticiones autorizadas envían `Authorization: Bearer <token>` y responden `200 OK`.

---

## 4. Ejecución Local

Requiere **Java (JDK)** y **Maven** (o `./mvnw` / `.\mvnw.cmd`), además de acceso a **MongoDB Atlas** configurado en la aplicación.

```bash
git clone [https://github.com/DiegoCriado31/urbanBack.git](https://github.com/DiegoCriado31/urbanBack.git)
cd urbanBack
./mvnw spring-boot:run
