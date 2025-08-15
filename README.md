# QuindioFresh - Sistema de Gestión de Pedidos

Este proyecto implementa un sistema de gestión de pedidos para una tienda de productos frescos del Quindío, basado en un diagrama UML de clases.

## Estructura del Proyecto

### Clases Implementadas

1. **Producto** - Representa los productos disponibles
   - Atributos: `id`, `nombre`, `precioUnitario`
   - Relación: 1 a muchos con `ItemPedido`

2. **Cliente** - Representa los clientes del sistema
   - Atributos: `id`, `nombre`, `direccion`, `telefono`
   - Relación: 1 a muchos con `Pedido`

3. **Pedido** - Representa las órdenes de compra
   - Atributos: `pedidoId`, `fechaPedido`, `metodoEnvio`
   - Métodos: `agregarProducto()`, `procesarPago()`
   - Relación: Composición con `ItemPedido` (1 a muchos)

4. **ItemPedido** - Representa los items individuales en un pedido
   - Atributos: `cantidad`
   - Relación: Asociación con `Producto` (muchos a 1)

5. **MetodoEnvio** - Enum para los métodos de envío disponibles
   - Valores: `ESTANDAR`, `EXPRESS`, `URGENTE`, `RECOGIDA_EN_TIENDA`

## Relaciones Implementadas

- **Producto ↔ ItemPedido**: Asociación 1 a muchos
- **ItemPedido ↔ Pedido**: Composición (Pedido contiene ItemPedidos)
- **Cliente ↔ Pedido**: Asociación 1 a muchos

## Compilación y Ejecución

```bash
# Compilar el proyecto
javac -d target/classes src/main/java/co/edu/uniquindio/*.java

# Ejecutar el programa
java -cp target/classes co.edu.uniquindio.Main
```

## Funcionalidades

- Creación de productos con precios
- Registro de clientes
- Creación de pedidos con múltiples productos
- Cálculo automático de subtotales y totales
- Simulación de procesamiento de pagos
- Diferentes métodos de envío

## Ejemplo de Uso

El programa de ejemplo crea:
- 3 productos (Fresas Orgánicas, Plátanos Maduros, Aguacates Hass)
- 1 cliente (Juan Pérez)
- 1 pedido con los 3 productos en diferentes cantidades
- Procesa el pago y muestra el resultado

## Características Técnicas

- Implementado en Java puro
- Sin dependencias externas
- Estructura de paquetes estándar
- Manejo de relaciones UML correctamente implementado
- Métodos de negocio funcionales
