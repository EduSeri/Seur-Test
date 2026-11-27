# Cambios del Proyecto SeurTest

## Actualización Reciente (Control de Acciones en SeurTopBar y Menú Autenticado)
- **Parámetro `showActions` en `SeurTopBar`**: Se añadió `showActions: Boolean = true` con una altura mínima fija de `64.dp` para evitar saltos en la cabecera. En pantallas sin sesión iniciada (Login, Recovery y Registro), `showActions = false` muestra únicamente el logo de SEUR sin iconos de acción ni menú.
- **Menú Lateral Exclusivo para Usuarios Autenticados**: Se eliminó la versión de invitado en `SeurSideMenu`. El menú lateral ahora requiere sesión iniciada, mostrando siempre el perfil del usuario autenticado y el botón de "Cerrar sesión" en el pie (el cual cierra automáticamente el menú al cerrar sesión).
- **Menú Lateral Desplegable (`SeurSideMenu`)**: Panel lateral deslizante de 324.dp, esquinas de 28.dp, scrim negro alpha 0.55, acordeón en "miSEUR", insets y `BackHandler`.
- **Estructura Edge-to-Edge y Material 3** con temas claro y oscuro completos.
