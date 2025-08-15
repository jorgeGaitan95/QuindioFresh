package co.edu.uniquindio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {
    private String pedidoId;
    private Date fechaPedido;
    private MetodoEnvio metodoEnvio;
    private Cliente cliente;
    private List<ItemPedido> items;

    // Constructor
    public Pedido(String pedidoId, Cliente cliente, MetodoEnvio metodoEnvio) {
        this.pedidoId = pedidoId;
        this.cliente = cliente;
        this.metodoEnvio = metodoEnvio;
        this.fechaPedido = new Date();
        this.items = new ArrayList<>();
    }

    // Getters and Setters
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

    // Method to add a product with quantity to the order
    public void agregarProducto(Producto producto, int cantidad) {
        ItemPedido item = new ItemPedido(cantidad, producto);
        items.add(item);
    }

    // Method to process payment
    public boolean procesarPago() {
        // Simulate payment processing
        // In a real application, this would integrate with a payment gateway
        float total = calcularTotal();
        System.out.println("Procesando pago por: $" + total);

        // Simulate successful payment (90% success rate)
        return Math.random() > 0.1;
    }

    // Method to calculate total order amount
    public float calcularTotal() {
        float total = 0;
        for (ItemPedido item : items) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "pedidoId='" + pedidoId + '\'' +
                ", fechaPedido=" + fechaPedido +
                ", metodoEnvio=" + metodoEnvio +
                ", cliente=" + cliente.getNombre() +
                ", total=" + calcularTotal() +
                ", numeroItems=" + items.size() +
                '}';
    }
}
