# Cambios del Proyecto SeurTest

## Actualización Reciente (Nuevo Color Azul Web y Optimizaciones de Layout)
- **Nuevo Color Azul Web (`SeurWebBlue` / `#0062B3`)**: Se ha añadido el nuevo color azul corporativo `#0062B3` tanto en `Color.kt` (para Jetpack Compose) como en `colors.xml` (para recursos XML).
- **Corrección Definitiva del Hueco Naranja en Home y Mis Envios**: En `SeurScreenLayout`, el contenedor de la tarjeta `surface` se estructuró con `Modifier.weight(1f)` para que la tarjeta se expanda verticalmente rellenando todo el espacio hasta el borde inferior físico.
- **Componente de Layout Común (`SeurScreenLayout`)**: Layout reutilizable que unifica cabecera naranja y tarjeta `surface` con esquinas redondeadas.
- **Lista Mundial Completa de Países (~250 países)** con `CountryRepository`, `libphonenumber`, emojis de bandera y ordenación con `Collator`.
- **Selector en `ModalBottomSheet` con Búsqueda**.
- **Logotipo Oficial en PNG** en `SeurTopBar`.
- **Tema Claro y Oscuro Completo (Material 3)**.
- **Estructura Edge-to-Edge y Scroll Unificado**.
