package Alojamiento;

public abstract class Alojamiento implements Prducto {
    private String direccion;

    public abstract Double calcularCosto();

    public String getDireccion(){
        return direccion;
    };
}

