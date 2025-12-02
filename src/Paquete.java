import java.util.ArrayList;
import java.util.List;

public class Paquete implements Producto {
    private String nombre;
    // Colección de productos Vendibles
    private List<Producto> productos;

    public Paquete(String nombre) {
        this.nombre = nombre;
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }

    @Override
    public Double calcularCosto(Integer cantidadNoches) {
        return productos.stream()
                // Llama a calcularCosto(cantidadNoches) en cada elemento
                .mapToDouble(p -> p.calcularCosto(cantidadNoches))
                .sum();
    }
}
