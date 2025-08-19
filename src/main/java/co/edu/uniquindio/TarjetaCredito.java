package co.edu.uniquindio;

/**
 * Clase TarjetaCredito - Representa un objeto de tarjeta de crédito
 * Responsabilidad: Gestionar la información y validación de una tarjeta de crédito
 */
public class TarjetaCredito {
    // ========================================
    // ATRIBUTOS DEL OBJETO TARJETA DE CRÉDITO
    // ========================================
    private String numero;
    private String titular;
    private String fechaVencimiento;
    private String cvv;

    // ========================================
    // CONSTRUCTOR
    // ========================================
    public TarjetaCredito(String numero, String titular, String fechaVencimiento, String cvv) {
        this.numero = numero;
        this.titular = titular;
        this.fechaVencimiento = fechaVencimiento;
        this.cvv = cvv;
    }

    // ========================================
    // GETTERS Y SETTERS
    // ========================================
    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getCvv() {
        return cvv;
    }

    // ========================================
    // MÉTODOS DEL OBJETO TARJETA DE CRÉDITO
    // ========================================
    
    /**
     * Valida si los datos de la tarjeta son correctos
     * @return true si la tarjeta es válida
     */
    public boolean esValida() {
        // Validar que el número tenga 16 dígitos
        if (numero == null || numero.length() != 16) {
            return false;
        }
        
        // Validar que el titular no esté vacío
        if (titular == null || titular.trim().isEmpty()) {
            return false;
        }
        
        // Validar formato de fecha (MM/YY)
        if (fechaVencimiento == null || fechaVencimiento.length() != 5) {
            return false;
        }
        
        // Validar que el CVV tenga 3 dígitos
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

    /**
     * Obtiene el número de tarjeta enmascarado para mostrar
     * @return Número enmascarado (ej: 1234****5678)
     */
    public String getNumeroEnmascarado() {
        if (numero == null || numero.length() != 16) {
            return "****";
        }
        return numero.substring(0, 4) + "****" + numero.substring(12);
    }

    @Override
    public String toString() {
        return "TarjetaCredito{" +
                "numero='" + getNumeroEnmascarado() + '\'' +
                ", titular='" + titular + '\'' +
                ", fechaVencimiento='" + fechaVencimiento + '\'' +
                '}';
    }
}
