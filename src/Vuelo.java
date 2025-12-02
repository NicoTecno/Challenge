public class Vuelo {
    private String fechaSalida;
    private String fechaRegreso;
    private String aerolínea;
    private Double precio;

    public Vuelo(String fechaRegreso, String fechaSalida, String aerolínea, Double precio) {
        this.fechaRegreso = fechaRegreso;
        this.fechaSalida = fechaSalida;
        this.aerolínea = aerolínea;
        this.precio = precio;
    }
    public String getFechaSalida() {
        return fechaSalida;
    }
    public String getFechaRegreso() {
        return fechaRegreso;
    }
    public String getAerolínea(){
        return aerolínea;
    }

    public Double calcularCosto(Integer cantidadNoches) {
        // El precio es fijo y no depende de las noches
        return this.precio;
    }
}
