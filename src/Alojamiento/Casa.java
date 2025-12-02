package Alojamiento;

public class Casa extends Alojamiento{
    private Integer cantidadAmbientes;
    private String direccion;

    public Casa(Integer cantidadAmbientes,String direccion) {
        this.cantidadAmbientes = cantidadAmbientes;
        this.direccion = direccion;
    }

    public Double calcularCosto() {
        if (cantidadAmbientes == 1) {
            // Monoambiente
            return 15000.00;
        } else if (cantidadAmbientes >= 2 && cantidadAmbientes <= 4) {
            // Entre 2 y 4 ambientes
            return 30000.00;
        } else if (cantidadAmbientes > 4) {
            // Más de 4 ambientes
            return 50000.00;
        } else {
            // Manejo de casos inválidos (0 o ambientes negativos)
            return 0.00;
        }
    }

}
