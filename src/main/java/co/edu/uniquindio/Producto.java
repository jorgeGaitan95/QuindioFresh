package co.edu.uniquindio;

public class Producto {
    private String sku;
    private String nombre;
    private float precioUnitario;

    // Constructor
    public Producto(String sku, String nombre, float precioUnitario) {
        this.sku = sku;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
    }

    // Getters and Setters
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "sku='" + sku + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precioUnitario=" + precioUnitario +
                '}';
    }
}
