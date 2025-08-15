package co.edu.uniquindio;

public class ItemPedido {
    private int cantidad;
    private Producto producto;

    // Constructor
    public ItemPedido(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    // Getters and Setters
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // Method to calculate the total price for this item
    public float calcularSubtotal() {
        return cantidad * producto.getPrecioUnitario();
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "cantidad=" + cantidad +
                ", producto=" + producto.getNombre() +
                ", subtotal=" + calcularSubtotal() +
                '}';
    }
}
