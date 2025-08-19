package co.edu.uniquindio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase Pedido refactorizada aplicando el principio de Responsabilidad Única
 * Ahora usa objetos: Pago, Envio, NotificacionSMS
 */
public class Pedido {
    // ========================================
    // ATRIBUTOS
    // ========================================
    private String pedidoId;
    private Date fechaPedido;
    private Cliente cliente;
    private List<ItemPedido> items;
    private boolean confirmado;

    // ========================================
    // OBJETOS RELACIONADOS (Composición)
    // ========================================
    private Envio envio;
    private Pago pago;
    private NotificacionSMS notificacion;

    // ========================================
    // CONSTRUCTOR
    // ========================================
    public Pedido(String pedidoId, Cliente cliente, MetodoEnvio metodoEnvio) {
        this.pedidoId = pedidoId;
        this.cliente = cliente;
        this.fechaPedido = new Date();
        this.items = new ArrayList<>();
        this.confirmado = false;
        this.envio = new Envio(metodoEnvio, cliente.getDireccion());
    }

    // ========================================
    // GETTERS Y SETTERS
    // ========================================
    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public MetodoEnvio getMetodoEnvio() {
        return envio.getMetodoEnvio();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public Envio getEnvio() {
        return envio;
    }

    public Pago getPago() {
        return pago;
    }

    public NotificacionSMS getNotificacion() {
        return notificacion;
    }

    // ========================================
    // MÉTODOS DE GESTIÓN DE PRODUCTOS
    // ========================================
    public void agregarProducto(Producto producto, int cantidad) {
        ItemPedido item = new ItemPedido(cantidad, producto);
        items.add(item);
    }

    // ========================================
    // MÉTODOS DE CÁLCULO
    // ========================================
    public float calcularSubtotalProductos() {
        float subtotal = 0;
        for (ItemPedido item : items) {
            subtotal += item.calcularSubtotal();
        }
        return subtotal;
    }

    public float calcularCostoEnvio() {
        return envio.getCosto();
    }

    public float calcularTotal() {
        return calcularSubtotalProductos() + calcularCostoEnvio();
    }

    public String obtenerResumenCostos() {
        float subtotal = calcularSubtotalProductos();
        float envío = calcularCostoEnvio();
        float total = calcularTotal();
        
        StringBuilder resumen = new StringBuilder();
        resumen.append("Subtotal productos: $").append(String.format("%.0f", subtotal)).append(" COP\n");
        resumen.append("Costo envío (").append(envio.getMetodoEnvio()).append("): $").append(String.format("%.0f", envío)).append(" COP\n");
        resumen.append("TOTAL: $").append(String.format("%.0f", total)).append(" COP");
        
        return resumen.toString();
    }

    // ========================================
    // MÉTODOS DE PROCESAMIENTO DE PAGO
    // ========================================
    public boolean procesarPago(String numeroTarjeta, String titular, String fechaVencimiento, String cvv) {
        float total = calcularTotal();
        
        // Crear objeto TarjetaCredito
        TarjetaCredito tarjeta = new TarjetaCredito(numeroTarjeta, titular, fechaVencimiento, cvv);
        
        // Crear objeto Pago
        this.pago = new Pago(tarjeta, total);
        
        // Procesar el pago
        return pago.procesar();
    }

    public boolean enviarNotificacion() {
        if (pago == null || !pago.isProcesado()) {
            System.out.println("ERROR: No se puede enviar notificación sin pago procesado");
            return false;
        }
        
        // Crear objeto NotificacionSMS
        this.notificacion = new NotificacionSMS(cliente.getTelefono(), this);
        
        // Enviar la notificación
        return notificacion.enviar();
    }

    // ========================================
    // MÉTODO PRINCIPAL: PROCESO CENTRAL
    // ========================================
    public boolean confirmarPedido(String numeroTarjeta, String titular, String fechaVencimiento, String cvv) {
        System.out.println("=== PROCESO DE CONFIRMACIÓN DE PEDIDO ===");
        System.out.println("Pedido ID: " + pedidoId);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Fecha: " + fechaPedido);
        System.out.println("Número de productos: " + items.size());
        System.out.println("\n--- RESUMEN DE COSTOS ---");
        System.out.println(obtenerResumenCostos());
        System.out.println("=========================================");
        
        // PASO 1: Registro del Pedido (ya completado)
        System.out.println("✓ PASO 1: Pedido registrado");
        
        // PASO 2: Procesamiento del Pago
        System.out.println("\n--- PASO 2: PROCESAMIENTO DE PAGO ---");
        if (!procesarPago(numeroTarjeta, titular, fechaVencimiento, cvv)) {
            System.out.println("❌ FALLO: No se pudo procesar el pago");
            return false;
        }
        
        // PASO 3: Cálculo de Envío (ya incluido en el pago)
        System.out.println("\n--- PASO 3: CÁLCULO DE ENVÍO ---");
        System.out.println(envio.obtenerResumen());
        
        // PASO 4: Notificación al Cliente
        System.out.println("\n--- PASO 4: NOTIFICACIÓN AL CLIENTE ---");
        if (!enviarNotificacion()) {
            System.out.println("❌ FALLO: No se pudo enviar la notificación");
            return false;
        }
        
        // Marcar pedido como confirmado
        this.confirmado = true;
        
        System.out.println("\n=== ✅ PEDIDO CONFIRMADO EXITOSAMENTE ===");
        System.out.println("Todos los pasos del proceso se completaron correctamente.");
        System.out.println("=========================================");
        
        return true;
    }

    // ========================================
    // MÉTODOS DE REPRESENTACIÓN
    // ========================================
    @Override
    public String toString() {
        return "Pedido{" +
                "pedidoId='" + pedidoId + '\'' +
                ", fechaPedido=" + fechaPedido +
                ", cliente=" + cliente.getNombre() +
                ", subtotal=" + calcularSubtotalProductos() +
                ", costoEnvio=" + calcularCostoEnvio() +
                ", total=" + calcularTotal() +
                ", numeroItems=" + items.size() +
                ", confirmado=" + confirmado +
                ", pago=" + (pago != null ? (pago.isProcesado() ? "Procesado" : "No procesado") : "No creado") +
                ", notificacion=" + (notificacion != null ? "Creada" : "No creada") +
                '}';
    }
}
