# Cambios del Proyecto SeurTest

## Actualización Reciente (Ubicación del Carrusel, Animación a 2s y Limpieza de Formulario)
- **Carrusel en Sección Naranja**: Se reubicó `HomeCarouselSection` fuera de la tarjeta `surface` (negra/blanca), situándose en la sección superior naranja entre el `SeurTopBar` y la tarjeta de contenido (`headerContent`).
- **Movimiento Automático a 2 Segundos**: Se reestructuró la corrutina de auto-avance con un bucle `while(true)` y `delay(2000)` para garantizar que el `HorizontalPager` avance fluidamente cada 2 segundos.
- **Limpieza de Origen y Destino**: Se eliminaron los círculos indicadores ("radio buttons") y los campos de localidad en los bloques de "Haz tu envío".
- **Botón "Continuar" Dinámico**: Color gris si falta información y naranja corporativo si el formulario es válido.
- **Estructura Edge-to-Edge y Material 3** con temas claro y oscuro completos.
