import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private Double presupuestoInicial;
    private List<Producto> historialCompras;

    public Usuario(String nombre, Double presupuestoInicial) {
        this.nombre = nombre;
        this.presupuestoInicial = presupuestoInicial;
        this.historialCompras = new ArrayList<>();
    }

    public boolean intentarContratar(Producto producto, Integer cantidadNoches) {
        Double costo = producto.calcularCosto(cantidadNoches);

        if (this.presupuestoInicial >= costo) {
            this.historialCompras.add(producto);
            this.presupuestoInicial -= costo;
            System.out.println(nombre + " ha comprado un producto por $" +
                    String.format("%.2f", costo) +
                    ". Presupuesto restante: $" +
                    String.format("%.2f", presupuestoInicial));

            return true;
        } else {
            double falta = costo - this.presupuestoInicial;
            System.out.println("Notificación de falta de fondos para " + nombre +
                    ". El costo es $" + String.format("%.2f", costo) +
                    " y solo quedan $" + String.format("%.2f", presupuestoInicial) +
                    ". Faltan $" + String.format("%.2f", falta) + ".");
            return false;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPresupuestoInicial() {
        return presupuestoInicial;
    }

    public List<Producto> getHistorialCompras() {
        return historialCompras;
    }
}
