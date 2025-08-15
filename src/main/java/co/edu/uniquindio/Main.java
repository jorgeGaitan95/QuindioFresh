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

        System.out.println("\n--- Procesando Pago ---");
        boolean pagoExitoso = pedido.procesarPago();

        if (pagoExitoso) {
            System.out.println("✅ Pago procesado exitosamente");
            System.out.println("📦 Pedido enviado por: " + pedido.getMetodoEnvio());
        } else {
            System.out.println("❌ Error en el procesamiento del pago");
        }

        System.out.println("\n=== Fin del Sistema ===");
    }
}