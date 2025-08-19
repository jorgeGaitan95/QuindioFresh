package co.edu.uniquindio;

/**
 * Clase Envio - Representa un objeto de envío
 * Responsabilidad: Gestionar toda la información y lógica relacionada con un envío
 */
public class Envio {
    // ========================================
    // ATRIBUTOS DEL OBJETO ENVÍO
    // ========================================
    private MetodoEnvio metodoEnvio;
    private float costo;
    private String direccionDestino;
    private String numeroSeguimiento;

    // ========================================
    // CONSTRUCTOR
    // ========================================
    public Envio(MetodoEnvio metodoEnvio, String direccionDestino) {
        this.metodoEnvio = metodoEnvio;
        this.direccionDestino = direccionDestino;
        this.numeroSeguimiento = generarNumeroSeguimiento();
        calcularCosto();
    }

    // ========================================
    // GETTERS Y SETTERS
    // ========================================
    public MetodoEnvio getMetodoEnvio() {
        return metodoEnvio;
    }

    public float getCosto() {
        return costo;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public String getNumeroSeguimiento() {
        return numeroSeguimiento;
    }

    // ========================================
    // MÉTODOS DEL OBJETO ENVÍO
    // ========================================
    
    /**
     * Calcula el costo del envío según el método seleccionado
     */
    private void calcularCosto() {
        switch (metodoEnvio) {
            case ESTANDAR:
                this.costo = 7000.0f; // $7,000 COP
                break;
            case EXPRESS:
                this.costo = 15000.0f; // $15,000 COP
                break;
            default:
                this.costo = 0.0f;
        }
    }

    /**
     * Genera un número de seguimiento único
     * @return Número de seguimiento
     */
    private String generarNumeroSeguimiento() {
        // Simular generación de número de seguimiento
        return "ENV-" + System.currentTimeMillis() % 1000000;
    }



    /**
     * Procesa el envío
     */
    public void procesarEnvio() {
        System.out.println("=== ENVÍO PROCESADO ===");
        System.out.println("✓ Envío procesado");
        System.out.println("Número de seguimiento: " + numeroSeguimiento);
        System.out.println("Método: " + metodoEnvio);
        System.out.println("=========================");
    }

    /**
     * Obtiene un resumen del envío
     * @return Resumen formateado del envío
     */
    public String obtenerResumen() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("ENVÍO:\n");
        resumen.append("  - Método: ").append(metodoEnvio).append("\n");
        resumen.append("  - Costo: $").append(String.format("%.0f", costo)).append(" COP\n");
        resumen.append("  - Dirección: ").append(direccionDestino).append("\n");
        resumen.append("  - Número seguimiento: ").append(numeroSeguimiento);
        
        return resumen.toString();
    }

    @Override
    public String toString() {
        return "Envio{" +
                "metodo=" + metodoEnvio +
                ", costo=" + costo +
                ", numeroSeguimiento='" + numeroSeguimiento + '\'' +
                '}';
    }
}
