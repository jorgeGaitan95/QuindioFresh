package co.edu.uniquindio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {
    // ========================================
    // ATRIBUTOS
    // ========================================
    private String pedidoId;
    private Date fechaPedido;
    private MetodoEnvio metodoEnvio;
    private Cliente cliente;
    private List<ItemPedido> items;
    private boolean pagoProcesado;
    private boolean notificacionEnviada;

    // ========================================
    // CONSTRUCTOR
    // ========================================
    public Pedido(String pedidoId, Cliente cliente, MetodoEnvio metodoEnvio) {
        this.pedidoId = pedidoId;
        this.cliente = cliente;
        this.metodoEnvio = metodoEnvio;
        this.fechaPedido = new Date();
        this.items = new ArrayList<>();
        this.pagoProcesado = false;
        this.notificacionEnviada = false;
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
        return metodoEnvio;
    }

    public void setMetodoEnvio(MetodoEnvio metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
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

    public boolean isPagoProcesado() {
        return pagoProcesado;
    }

    public boolean isNotificacionEnviada() {
        return notificacionEnviada;
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
    public float calcularTotalProductos() {
        float total = 0;
        for (ItemPedido item : items) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public float calcularCostoEnvio() {
        switch (metodoEnvio) {
            case ESTANDAR:
                return 7000.0f; // $7,000 COP
            case EXPRESS:
                return 15000.0f; // $15,000 COP
            default:
                return 0.0f;
        }
    }

    public float calcularTotal() {
        return calcularTotalProductos() + calcularCostoEnvio();
    }

    // ========================================
    // MÉTODOS DE VALIDACIÓN
    // ========================================
    private boolean validarTarjetaCredito(String numero, String titular, String fechaVencimiento, String cvv) {
        // Validaciones básicas
        if (numero == null || numero.length() != 16) {
            return false;
        }
        
        if (titular == null || titular.trim().isEmpty()) {
            return false;
        }
        
        if (fechaVencimiento == null || fechaVencimiento.length() != 5) {
            return false;
        }
        
        if (cvv == null || cvv.length() != 3) {
            return false;
        }
        
        // Validar que el número de tarjeta solo contenga dígitos
        if (!numero.matches("\\d{16}")) {
            return false;
        }
        
        // Validar que el CVV solo contenga dígitos
        if (!cvv.matches("\\d{3}")) {
            return false;
        }
        
        return true;
    }

    // ========================================
    // MÉTODOS DE PROCESAMIENTO DE PAGO
    // ========================================
    public boolean procesarPago(String numeroTarjeta, String titular, String fechaVencimiento, String cvv) {
        System.out.println("=== PROCESAMIENTO DE PAGO ===");
        
        // Validar datos de la tarjeta
        if (!validarTarjetaCredito(numeroTarjeta, titular, fechaVencimiento, cvv)) {
            System.out.println("ERROR: Datos de tarjeta de crédito inválidos");
            return false;
        }
        
        float total = calcularTotal(); // Incluye el costo de envío
        System.out.println("Procesando pago por: $" + String.format("%.0f", total) + " COP");
        System.out.println("  - Subtotal productos: $" + String.format("%.0f", calcularTotalProductos()) + " COP");
        System.out.println("  - Costo envío: $" + String.format("%.0f", calcularCostoEnvio()) + " COP");
        System.out.println("Tarjeta: " + numeroTarjeta.substring(0, 4) + "****" + numeroTarjeta.substring(12));
        
        // Simular procesamiento de pago (90% éxito)
        boolean pagoExitoso = Math.random() > 0.1;
        
        if (pagoExitoso) {
            System.out.println("✓ Pago procesado exitosamente");
            this.pagoProcesado = true;
        } else {
            System.out.println("✗ Error en el procesamiento del pago");
        }
        
        System.out.println("=============================");
        return pagoProcesado;
    }

    // ========================================
    // MÉTODOS DE NOTIFICACIÓN
    // ========================================
    public boolean enviarNotificacion() {
        if (!pagoProcesado) {
            System.out.println("ERROR: No se puede enviar notificación sin pago procesado");
            return false;
        }
        
        System.out.println("=== ENVÍO DE NOTIFICACIÓN ===");
        
        String mensaje = generarMensajeConfirmacion();
        boolean notificacionExitoso = enviarSMS(cliente.getTelefono(), mensaje);
        
        if (notificacionExitoso) {
            System.out.println("✓ Notificación enviada exitosamente");
            this.notificacionEnviada = true;
        } else {
            System.out.println("✗ Error al enviar notificación");
        }
        
        System.out.println("=============================");
        return notificacionEnviada;
    }

    private String generarMensajeConfirmacion() {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("¡Hola ").append(cliente.getNombre()).append("! ");
        mensaje.append("Tu pedido #").append(pedidoId).append(" ha sido confirmado. ");
        mensaje.append("Total: $").append(String.format("%.0f", calcularTotal())).append(" COP. ");
        mensaje.append("Método de envío: ").append(metodoEnvio.toString()).append(". ");
        mensaje.append("Tu pedido está en preparación. ¡Gracias por tu compra!");
        
        return mensaje.toString();
    }

    private boolean enviarSMS(String numeroTelefono, String mensaje) {
        // Simulación del envío de SMS
        // En una aplicación real, aquí se integraría con un servicio de SMS
        System.out.println("=== NOTIFICACIÓN SMS ===");
        System.out.println("Enviando SMS al número: " + numeroTelefono);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("=========================");
        
        // Simular éxito en el envío (95% de éxito)
        return Math.random() > 0.05;
    }

    // ========================================
    // MÉTODO PRINCIPAL: PROCESO CENTRAL
    // ========================================
    public boolean confirmarPedido(String numeroTarjeta, String titular, String fechaVencimiento, String cvv) {
        System.out.println("=== PROCESO DE CONFIRMACIÓN DE PEDIDO ===");
        System.out.println("Pedido ID: " + pedidoId);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Fecha: " + fechaPedido);
        System.out.println("Método de envío: " + metodoEnvio);
        System.out.println("Número de productos: " + items.size());
        System.out.println("Subtotal productos: $" + String.format("%.0f", calcularTotalProductos()) + " COP");
        System.out.println("Costo envío: $" + String.format("%.0f", calcularCostoEnvio()) + " COP");
        System.out.println("TOTAL: $" + String.format("%.0f", calcularTotal()) + " COP");
        System.out.println("=========================================");
        
        // PASO 1: Registro del Pedido (ya completado)
        System.out.println("✓ PASO 1: Pedido registrado");
        
        // PASO 2: Procesamiento del Pago (incluye costo de envío)
        System.out.println("\n--- PASO 2: PROCESAMIENTO DE PAGO ---");
        if (!procesarPago(numeroTarjeta, titular, fechaVencimiento, cvv)) {
            System.out.println("❌ FALLO: No se pudo procesar el pago");
            return false;
        }
        
        // PASO 3: Cálculo de Envío (ya incluido en el pago)
        System.out.println("\n--- PASO 3: CÁLCULO DE ENVÍO ---");
        System.out.println("✓ Costo de envío ya incluido en el pago: $" + String.format("%.0f", calcularCostoEnvio()) + " COP");
        
        // PASO 4: Notificación al Cliente
        System.out.println("\n--- PASO 4: NOTIFICACIÓN AL CLIENTE ---");
        if (!enviarNotificacion()) {
            System.out.println("❌ FALLO: No se pudo enviar la notificación");
            return false;
        }
        
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
                ", metodoEnvio=" + metodoEnvio +
                ", cliente=" + cliente.getNombre() +
                ", subtotal=" + calcularTotalProductos() +
                ", costoEnvio=" + calcularCostoEnvio() +
                ", total=" + calcularTotal() +
                ", numeroItems=" + items.size() +
                ", pagoProcesado=" + pagoProcesado +
                ", notificacionEnviada=" + notificacionEnviada +
                '}';
    }
}
