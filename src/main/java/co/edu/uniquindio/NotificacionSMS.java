package co.edu.uniquindio;

/**
 * Clase NotificacionSMS - Representa un objeto de notificación SMS
 * Responsabilidad: Gestionar toda la información y lógica relacionada con una notificación SMS
 */
public class NotificacionSMS {
    // ========================================
    // ATRIBUTOS DEL OBJETO NOTIFICACIÓN SMS
    // ========================================
    private String numeroCelular;
    private String mensaje;

    // ========================================
    // CONSTRUCTORES
    // ========================================
    public NotificacionSMS(String numeroCelular, String mensaje) {
        this.numeroCelular = numeroCelular;
        this.mensaje = mensaje;
    }

    /**
     * Constructor sobrecargado que genera automáticamente el mensaje de confirmación
     * @param numeroCelular Número de teléfono del cliente
     * @param pedido Pedido para generar el mensaje de confirmación
     */
    public NotificacionSMS(String numeroCelular, Pedido pedido) {
        this.numeroCelular = numeroCelular;
        this.mensaje = generarMensajeConfirmacion(pedido);
    }

    // ========================================
    // GETTERS Y SETTERS
    // ========================================
    public String getNumeroCelular() {
        return numeroCelular;
    }

    public String getMensaje() {
        return mensaje;
    }

    // ========================================
    // MÉTODOS DEL OBJETO NOTIFICACIÓN SMS
    // ========================================
    
    /**
     * Envía la notificación SMS
     * @return true si el envío fue exitoso
     */
    public boolean enviar() {
        System.out.println("=== ENVÍO DE NOTIFICACIÓN SMS ===");
        System.out.println("Enviando SMS al número: " + numeroCelular);
        System.out.println("Mensaje: " + mensaje);
        
        // Simular envío de SMS (95% de éxito)
        boolean envioExitoso = Math.random() > 0.05;
        
        if (envioExitoso) {
            System.out.println("✓ SMS enviado exitosamente");
        } else {
            System.out.println("✗ Error al enviar SMS");
        }
        
        System.out.println("================================");
        return envioExitoso;
    }

    /**
     * Genera un mensaje de confirmación de pedido
     * @param pedido El pedido para generar el mensaje
     * @return Mensaje formateado para SMS
     */
    private String generarMensajeConfirmacion(Pedido pedido) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("¡Hola ").append(pedido.getCliente().getNombre()).append("! ");
        mensaje.append("Tu pedido #").append(pedido.getPedidoId()).append(" ha sido confirmado. ");
        mensaje.append("Total: $").append(String.format("%.0f", pedido.calcularTotal())).append(" COP. ");
        mensaje.append("Método de envío: ").append(pedido.getMetodoEnvio().toString()).append(". ");
        mensaje.append("Tu pedido está en preparación. ¡Gracias por tu compra!");
        
        return mensaje.toString();
    }

    @Override
    public String toString() {
        return "NotificacionSMS{" +
                "numeroCelular='" + numeroCelular + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
