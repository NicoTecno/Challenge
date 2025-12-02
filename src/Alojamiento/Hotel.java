package Alojamiento;

public class Hotel extends Alojamiento {
    private String nombre;
    private Integer estrellas;

    public Hotel(String nombre, Integer estrellas) {
        this.nombre = nombre;
        this.estrellas = estrellas;
    }

    @Override
    public Double calcularCosto() {
        return estrellas * 10000.00;
    }
}
