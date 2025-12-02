package Alojamiento;

import java.util.List;
import java.util.Map;

public class Complejo extends Alojamiento{
    private String direccion;
    private List<Casa> complejo;

    public Complejo(String direccion, List<Casa> casas) {
        this.direccion = direccion;
        this.complejo = casas;

    }

    private Double calcularDescuento(){
        Integer cantidadCasas = complejo.size();
        if(cantidadCasas > 5) return 0.50;
        Map<Integer, Double> tablaDescuentos = Map.of(
                1, 0.1,
                2, 0.2,
                3, 0.3,
                4, 0.4,
                5, 0.5
        );
        return tablaDescuentos.get(cantidadCasas);


    }

    public Double calcularCosto(){
        Double valorTotal = complejo.stream().mapToDouble(Casa::calcularCosto).sum();
        Double descuento = calcularDescuento();
        Double montoDescuento = valorTotal * descuento;
        return valorTotal - montoDescuento;
    }
}
