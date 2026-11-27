# Cambios del Proyecto SeurTest

## Actualización Reciente (Menú Lateral Desplegable `SeurSideMenu`)
- **Menú Lateral Desplegable (`SeurSideMenu`)**: Implementación de un panel de navegación lateral deslizante desde la derecha de 324.dp (máx 85% de pantalla), esquinas redondeadas de 28.dp, scrim negro con alpha 0.55, cabecera de usuario con avatar con iniciales o vista de invitado, acordeón expandible para secciones con submenús (como "miSEUR"), botón de pie fijo adaptado (Cerrar sesión o Acceder), insets de sistema (`statusBarsPadding` / `navigationBarsPadding`), manejo de `BackHandler` y soporte para modo claro y oscuro con roles `primaryContainer` y `secondaryContainer`.
- **Nuevo Color Azul Web (`SeurWebBlue` / `#0062B3`)** añadido en `Color.kt` y `colors.xml`.
- **Corrección Definitiva del Hueco Naranja en Home y Mis Envios** mediante `Modifier.weight(1f)` en `SeurScreenLayout`.
- **Lista Mundial Completa de Países (~250 países)** con `CountryRepository` y `libphonenumber`.
- **Selector en `ModalBottomSheet` con Búsqueda**.
- **Logotipo Oficial en PNG** en `SeurTopBar`.
- **Tema Claro y Oscuro Completo (Material 3)** y **Estructura Edge-to-Edge**.
