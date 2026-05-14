# La Luz - Asistencia Integral Exequial

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C00.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![Bootstrap](https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white)

## 📄 Descripción del Proyecto
**La Luz Asistencia Integral** es una plataforma web de alto nivel diseñada para la gestión y prestación de servicios exequiales y planes de previsión en Colombia. El sistema combina una arquitectura de backend robusta con una interfaz de usuario (UI) diseñada bajo principios de serenidad y profesionalismo, facilitando procesos críticos como la afiliación digital y la consulta de homenajes.

## 🚀 Características Principales

### 🛡️ Previsión y Afiliación Digital
*   **Gestión de Planes**: Presentación detallada de planes (Ejecutivo, Excelencia y Preferencial) con coberturas parametrizadas.
*   **Transaccionalidad**: Módulos integrados para la afiliación 100% virtual y pasarela de consulta de pagos.
*   **Transparencia**: Tabla técnica de tiempos de carencia según el tipo de fallecimiento.

### 🕯️ Módulo In Memoriam (Obituarios)
*   **Listado Dinámico**: Sistema que renderiza servicios activos en tiempo real mediante lógica de servidor (Spring Boot + Thymeleaf).
*   **Interacción Social**: Funcionalidad para envío de condolencias y homenajes digitales.

### 🐾 Huella de Luz (Mascotas)
*   Sección especializada para el último adiós de mascotas, ofreciendo planes de cremación individual y colectiva con un enfoque empático.

### 📍 Red de Sedes
*   Integración de geolocalización para las sedes principales en Bogotá y La Calera, facilitando la ubicación física en momentos de necesidad.

## 📐 Arquitectura y Estructura del Proyecto
El proyecto sigue el estándar de **Maven** y una arquitectura **MVC (Modelo-Vista-Controlador)**, organizando el código fuente y los recursos de la siguiente manera:

```text
[funeraleslaluz.com/](https://funeraleslaluz.com/)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── [funeraleslaluz.com/](https://funeraleslaluz.com/)
│   │   │       ├── controlador/       # Manejo de rutas (Contacto, Web)
│   │   │       ├── modelo/            # Entidades (Obituario, PlanExequial, EmailContacto)
│   │   │       ├── repositorio/       # Interfaces de persistencia (Spring Data JPA)
│   │   │       ├── servicio/          # Lógica de negocio (EmailService, AsistenciaService)
│   │   │       └── Application.java   # Clase principal de Spring Boot
│   │   └── resources/
│   │       ├── static/                # Archivos públicos
│   │       │   ├── css/               # Hojas de estilo segmentadas (mascotas, contacto)
│   │       │   └── img/               # Recursos gráficos, logos y material visual
│   │       ├── templates/             # Vistas dinámicas con Thymeleaf
│   │       │   ├── fragmentos/        # Componentes reutilizables (layout.html)
│   │       │   ├── servicios/         # Vistas de servicios especializados
│   │       │   │   ├── atencion-inmediata.html
│   │       │   │   ├── conmemoraciones.html
│   │       │   │   ├── internacionales.html
│   │       │   │   ├── repatriacion.html
│   │       │   │   └── tradicionales.html
│   │       │   ├── contacto.html          # Formulario de contacto
│   │       │   ├── index.html             # Landing page principal
│   │       │   ├── mascotas.html          # Sección "Huella de Luz"
│   │       │   ├── obituarios.html        # Consulta de servicios fúnebres
│   │       │   ├── planes.html            # Información de planes exequiales
│   │       │   ├── prevision-empresarial.html
│   │       │   ├── proteccion-datos.html  # Políticas de privacidad
│   │       │   └── sedes.html             # Ubicación de instalaciones
│   │       └── application.properties     # Configuración global del sistema
└── pom.xml                                # Gestión de dependencias de Maven