# Cambios del Proyecto SeurTest

## Actualización Reciente (Corrección Definitiva del Hueco Naranja Inferior)
- **Corrección Definitiva del Hueco Naranja en Home y Mis Envios**: En `SeurScreenLayout`, el contenedor de la tarjeta `surface` se estructuró con `Modifier.weight(1f)` en lugar de altura adaptativa por contenido. Esto garantiza que, incluso en pantallas con contenido corto (como Home o Mis Envios), la tarjeta blanca/oscura se expanda verticalmente para llenar todo el espacio restante hasta el borde inferior físico de la pantalla, eliminando por completo cualquier hueco o franja naranja.
- **Corrección de Franja Naranja en Navegación del Sistema**: Se ajustó el color de fondo raíz de las pantallas mediante `Scaffold(containerColor = MaterialTheme.colorScheme.surface)` y se añadió un `Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))` al final del contenido con scroll.
- **Componente de Layout Común (`SeurScreenLayout`)**: Layout reutilizable que unifica cabecera naranja y tarjeta `surface` con esquinas redondeadas.
- **Lista Mundial Completa de Países (~250 países)** con `CountryRepository`, `libphonenumber`, emojis de bandera y ordenación con `Collator`.
- **Selector en `ModalBottomSheet` con Búsqueda**.
- **Logotipo Oficial en PNG** en `SeurTopBar`.
- **Tema Claro y Oscuro Completo (Material 3)**.
- **Estructura Edge-to-Edge y Scroll Unificado**.
