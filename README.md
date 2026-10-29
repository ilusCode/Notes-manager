# 🗒️ Notes Manager

API REST construida con **Spring Boot** y **WebFlux** que genera, en un solo PDF, un lote de **notas
numeradas** (folios) a partir de una plantilla de **JasperReports** y una imagen de fondo. El PDF se
devuelve codificado en **base64**.

![Java](https://img.shields.io/badge/Java-8-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.6-6DB33F?logo=springboot&logoColor=white)
![JasperReports](https://img.shields.io/badge/JasperReports-6.9-0A66C2)
![Maven](https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white)

> 🚧 **Proyecto en desarrollo.** Revisa [Estado y pendientes](#-estado-y-pendientes) antes de usarlo en producción.

---

## 🏗️ Cómo funciona

```
Cliente                                Notes Manager
  │ POST /notes/create (multipart)        │
  │ image + template + parámetros ──────▶ │ guarda image y template en directorios temporales
  │                                       │ calcula la numeración de cada hoja
  │                                       │ compila la plantilla y llena una página por hoja
  │                                       │ une todas las páginas en un solo PDF
  │ ◀──── { doc: <PDF en base64> } ────── │ borra los archivos temporales
```

### Numeración para cortar y apilar

Cada hoja lleva `notesForSheet` notas. La numeración está pensada para **imprimir, apilar y cortar**:
al separar las notas de cada posición, cada pila queda en orden consecutivo.

Con `totalNotes = 8`, `notesForSheet = 2` y `noteInit = 1` se generan 4 hojas:

| Hoja | Nota 1 (`p1`) | Nota 2 (`p2`) |
|------|---------------|---------------|
| 1 | 0001 | 0005 |
| 2 | 0002 | 0006 |
| 3 | 0003 | 0007 |
| 4 | 0004 | 0008 |

Los folios se formatean con cuatro dígitos (`%04d`). `noteInit` desplaza el primer folio: con
`noteInit = 101` el mismo lote va del `0101` al `0108`.

## 📡 API

### `POST /notes/create`

Petición `multipart/form-data`:

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `image` | archivo | Imagen de fondo de la nota. Su ruta llega a la plantilla en el parámetro `Image` |
| `template` | archivo | Plantilla de JasperReports (`.jrxml`) |
| `notesForSheet` | entero | Notas por hoja, de `1` a `4` |
| `totalNotes` | entero | Total de notas del lote |
| `noteInit` | entero | Folio inicial (`1` para empezar en `0001`) |

```bash
curl -X POST http://127.0.0.1:8090/notes/create \
  -F image=@fondo.png \
  -F template=@nota.jrxml \
  -F notesForSheet=2 \
  -F totalNotes=8 \
  -F noteInit=1
```

**`200 OK`**

```json
{
  "doc": "JVBERi0xLjQKJeLjz9MK...",
  "notifications": {
    "time": "2021-12-12T03:20:54.123",
    "mesagge": "Se genero el documento de forma correcta",
    "httpStatus": "OK",
    "code": "200"
  }
}
```

Para guardar el PDF:

```bash
curl ... | python3 -c 'import sys,json,base64; sys.stdout.buffer.write(base64.b64decode(json.load(sys.stdin)["doc"]))' > notas.pdf
```

| Error | Código | Cuándo |
|-------|--------|--------|
| `400 Bad Request` | `DEKI_ERROR_002` | `notesForSheet` no está entre 1 y 4 |
| `406 Not Acceptable` | `DEKI_ERROR_001` | No se pudo compilar o llenar la plantilla |

### La plantilla

La plantilla `.jrxml` recibe:

| Nombre | Tipo | Contenido |
|--------|------|-----------|
| `Image` | parámetro (`String`) | Ruta absoluta de la imagen subida |
| `p1` … `p4` | campos (`String`) | Folio de cada nota de la hoja, según `notesForSheet` |

## ⚙️ Configuración

`src/main/resources/application.yml`:

```yaml
server:
  port: 8090
  address: 127.0.0.1
```

## 🚀 Ejecución local

Requiere **JDK 8** (o superior compatible con Spring Boot 2.6) y **Maven**:

```bash
mvn spring-boot:run
```

La API queda en `http://127.0.0.1:8090`.

## 📁 Estructura

```
src/main/java/mx/iluscode/deki/notes/manager
├── DekiApplication.java              # Punto de entrada de Spring Boot
├── controllers/                      # NotasController: POST /notes/create
├── model/                            # CreateNoteResponse, DataNumbersDTO, NotificationWrapper
├── services/
│   ├── FilesService.java             # Guarda los archivos subidos en directorios temporales
│   ├── NotesService.java             # Llena la plantilla y exporta el PDF en base64
│   └── TypeNotesService.java         # Calcula los folios de cada hoja
├── transform/                        # Construye el objeto de notificación de la respuesta
└── utils/                            # Constantes, códigos de error y formato de folios
src/main/resources
├── application.yml
└── fonts/                            # Arial Rounded MT Bold y su fontfamily.xml
```

## 🚧 Estado y pendientes

- [ ] **Sin pruebas automatizadas.**
- [ ] **Notas sobrantes:** si `totalNotes` no es múltiplo de `notesForSheet`, las notas que no completan una hoja no se generan (por ejemplo, 9 notas con 2 por hoja generan 8).
- [ ] **Fuentes:** `fonts/fontfamily.xml` no está registrado (falta `jasperreports_extension.properties`), así que JasperReports no carga Arial Rounded MT Bold desde el proyecto.
- [ ] `TypeNotesService` soporta 5 y 6 notas por hoja, pero el controlador solo acepta de 1 a 4.
- [ ] La plantilla se recompila en cada hoja; compilarla una sola vez reduciría el tiempo con lotes grandes.

## 👤 Autor

**Eduardo Fernández Morales** — [@ilusCode](https://github.com/ilusCode) · [LinkedIn](https://www.linkedin.com/in/eduardo-fernandez-morales/)
