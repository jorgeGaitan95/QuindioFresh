package co.edu.uniquindio;

/**
 * Clase Pago - Representa un objeto de pago del mundo real
 * Responsabilidad: Gestionar el proceso de pago usando una tarjeta de crédito
 */
public class Pago {
    // ========================================
    // ATRIBUTOS DEL OBJETO PAGO
    // ========================================
    private TarjetaCredito tarjeta;
    private float monto;
    private boolean procesado;

    // ========================================
    // CONSTRUCTOR
    // ========================================
    public Pago(TarjetaCredito tarjeta, float monto) {
        this.tarjeta = tarjeta;
        this.monto = monto;
        this.procesado = false;
    }

    // ========================================
    // GETTERS Y SETTERS
    // ========================================
    public TarjetaCredito getTarjeta() {
        return tarjeta;
    }

    public float getMonto() {
        return monto;
    }

    public boolean isProcesado() {
        return procesado;
    }

    // ========================================
    // MÉTODOS DEL OBJETO PAGO
    // ========================================
    
    /**
     * Procesa el pago
     * @return true si el pago fue exitoso
     */
    public boolean procesar() {
        System.out.println("=== PROCESAMIENTO DE PAGO ===");
        
        // Validar datos de la tarjeta
        if (!tarjeta.esValida()) {
            System.out.println("ERROR: Datos de tarjeta de crédito inválidos");
            return false;
        }
        
        System.out.println("Procesando pago por: $" + String.format("%.0f", monto) + " COP");
        System.out.println("Tarjeta: " + tarjeta.getNumeroEnmascarado());
        System.out.println("Titular: " + tarjeta.getTitular());
        
        // Simular procesamiento de pago (90% éxito)
        boolean pagoExitoso = Math.random() > 0.1;
        
        if (pagoExitoso) {
            System.out.println("✓ Pago procesado exitosamente");
            this.procesado = true;
        } else {
            System.out.println("✗ Error en el procesamiento del pago");
        }
        
        System.out.println("=============================");
        return procesado;
    }

    /**
     * Obtiene un resumen del pago
     * @return Resumen formateado del pago
     */
    public String obtenerResumen() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("PAGO:\n");
        resumen.append("  - Monto: $").append(String.format("%.0f", monto)).append(" COP\n");
        resumen.append("  - Tarjeta: ").append(tarjeta.getNumeroEnmascarado()).append("\n");
        resumen.append("  - Titular: ").append(tarjeta.getTitular()).append("\n");
        resumen.append("  - Procesado: ").append(procesado ? "Sí" : "No");
        
        return resumen.toString();
    }

    @Override
    public String toString() {
        return "Pago{" +
                "monto=" + monto +
                ", tarjeta=" + tarjeta.getNumeroEnmascarado() +
                ", procesado=" + procesado +
                '}';
    }
}
