package co.edu.uniquindio;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Sistema de Gestión de Pedidos - QuindioFresh ===");

        // Crear productos
        Producto producto1 = new Producto("P001", "Fresas Orgánicas", 15000.0f);
        Producto producto2 = new Producto("P002", "Plátanos Maduros", 8000.0f);
        Producto producto3 = new Producto("P003", "Aguacates Hass", 12000.0f);

        // Crear cliente
        Cliente cliente = new Cliente("C001", "Juan Pérez", "Calle 15 #23-45, Armenia", "3001234567");

        // Crear pedido
        Pedido pedido = new Pedido("ORD001", cliente, MetodoEnvio.EXPRESS);

        // Agregar productos al pedido
        pedido.agregarProducto(producto1, 2);
        pedido.agregarProducto(producto2, 3);
        pedido.agregarProducto(producto3, 1);

        // Mostrar información del pedido
        System.out.println("\n--- Información del Pedido ---");
        System.out.println(pedido);

        System.out.println("\n--- Detalles de los Items ---");
        for (ItemPedido item : pedido.getItems()) {
            System.out.println(item);
        }

        // Datos de tarjeta de crédito
        String numeroTarjeta = "1234567890123456";
        String titular = "Juan Pérez";
        String fechaVencimiento = "12/25";
        String cvv = "123";

        System.out.println("\n--- Información de Pago ---");
        System.out.println("Tarjeta: " + numeroTarjeta.substring(0, 4) + "****" + numeroTarjeta.substring(12));
        System.out.println("Titular: " + titular);

        // Ejecutar el proceso central completo
        System.out.println("\n--- INICIANDO PROCESO DE CONFIRMACIÓN ---");
        boolean confirmacionExitosa = pedido.confirmarPedido(numeroTarjeta, titular, fechaVencimiento, cvv);

        if (confirmacionExitosa) {
            System.out.println("\n🎉 ¡Pedido confirmado exitosamente!");
            System.out.println("📱 Notificación SMS enviada al cliente");
            System.out.println("📦 Pedido en preparación para envío " + pedido.getMetodoEnvio());
        } else {
            System.out.println("\n❌ Error en la confirmación del pedido");
            System.out.println("El proceso se detuvo debido a un error");
        }

        // Mostrar estado final del pedido
        System.out.println("\n--- Estado Final del Pedido ---");
        System.out.println("Pago procesado: " + (pedido.isPagoProcesado() ? "✓ Sí" : "✗ No"));
        System.out.println("Notificación enviada: " + (pedido.isNotificacionEnviada() ? "✓ Sí" : "✗ No"));

        System.out.println("\n=== Fin del Sistema ===");
    }
}