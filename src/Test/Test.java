package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

// Importaciones de clases de tus paquetes
import Alojamiento.Casa;
import Alojamiento.Complejo;
import Alojamiento.Hotel;

// Importaciones de clases en el paquete por defecto (o paquete base)
import Usuario;
import Vuelo;
import Paquete;
import Producto;



public class AgenciaDeViajesTest {

    private Usuario usuarioConFondos;
    private Usuario usuarioSinFondos;
    private Hotel hotelDeLujo;
    private Vuelo vueloBarato;
    private Casa monoambiente;

    @BeforeEach
    void setUp() {
        // Inicialización antes de cada test

        // Usuarios
        usuarioConFondos = new Usuario("Richie Rich", 100000.0);
        usuarioSinFondos = new Usuario("Poor Man", 5000.0);

        // Productos individuales (Asegúrate de que los constructores coinciden con tus clases)
        hotelDeLujo = new Hotel("The Star", 5, "Miami Beach"); // $50.000 por noche
        vueloBarato = new Vuelo("2025-01-01", "2025-01-05", "LowCost", 8000.0);
        monoambiente = new Casa(1, "Centro"); // $15.000 por noche
    }

    // ====================================================================
    // 1. TESTS DE CÁLCULO DE COSTO INDIVIDUAL (POLIMORFISMO)
    // ====================================================================

    @Test
    void testCostoVuelo() {
        // El costo del vuelo es fijo, ignora las noches.
        assertEquals(8000.0, vueloBarato.calcularCosto(5), 0.01,
                "El costo del Vuelo debe ser el precio base.");
    }

    @Test
    void testCostoHotel() {
        // 5 estrellas * $10.000 * 3 noches = $150.000
        assertEquals(150000.0, hotelDeLujo.calcularCosto(3), 0.01,
                "El costo del Hotel debe ser (estrellas * 10k * noches).");
    }

    @Test
    void testCostoCasa() {
        // 1 ambiente: $15.000 * 4 noches = $60.000
        assertEquals(60000.0, monoambiente.calcularCosto(4), 0.01,
                "El costo del Monoambiente (1 amb) debe ser $15k * noches.");

        // 3 ambientes: $30.000 * 2 noches = $60.000
        Casa casaMedia = new Casa(3, "Suburbio");
        assertEquals(60000.0, casaMedia.calcularCosto(2), 0.01,
                "El costo de Casa (2-4 amb) debe ser $30k * noches.");
    }

    // ====================================================================
    // 2. TESTS DE DESCUENTO EN COMPLEJO
    // ====================================================================

    @Test
    void testCostoComplejoConDescuento() {
        // Tres casas de 3 ambientes: $30.000/noche * 3 = $90.000 por noche
        Casa c1 = new Casa(3, "C1");
        Casa c2 = new Casa(3, "C2");
        Casa c3 = new Casa(3, "C3");
        List<Casa> tresCasas = Arrays.asList(c1, c2, c3);

        Complejo complejoCompleto = new Complejo("Zona Sur", tresCasas);

        // Costo Base (1 noche): $90.000
        // Descuento: 30% ($27.000). Costo Final: $90.000 * 0.70 = $63.000
        assertEquals(63000.0, complejoCompleto.calcularCosto(1), 0.01,
                "Debe aplicar el 30% de descuento al alquilar 3 casas.");

        // Costo Final (2 noches): $180.000 * 0.70 = $126.000
        assertEquals(126000.0, complejoCompleto.calcularCosto(2), 0.01,
                "El descuento debe aplicarse sobre el total de las noches.");
    }

    @Test
    void testCostoComplejoSinDescuento() {
        // Una sola casa (no aplica descuento)
        List<Casa> unaCasa = Arrays.asList(new Casa(3, "C1"));
        Complejo complejoParcial = new Complejo("Zona Norte", unaCasa);

        // Costo Base (1 noche): $30.000. Sin descuento.
        assertEquals(30000.0, complejoParcial.calcularCosto(1), 0.01,
                "No debe aplicar descuento si no se alquilan las 3 unidades.");
    }

    // ====================================================================
    // 3. TESTS DE PAQUETE (COMPOSITE)
    // ====================================================================

    @Test
    void testCostoPaqueteSimple() {
        Paquete paquete = new Paquete("Básico");
        paquete.agregarProducto(vueloBarato); // $8.000
        paquete.agregarProducto(monoambiente); // $15.000 * 1 noche

        // Costo Total (1 noche): 8.000 + 15.000 = $23.000
        assertEquals(23000.0, paquete.calcularCosto(1), 0.01,
                "El paquete debe sumar los costos de sus componentes.");
    }

    @Test
    void testCostoPaqueteCompuesto() {
        // SubPaquete: Vuelo ($8.000) + Monoambiente ($15.000 * 2 noches = $30.000). Total: $38.000
        Paquete subPaquete = new Paquete("SubPaquete");
        subPaquete.agregarProducto(vueloBarato);
        subPaquete.agregarProducto(monoambiente);

        // Paquete Principal: Hotel ($50.000 * 2 noches = $100.000) + SubPaquete ($38.000)
        Paquete paquetePrincipal = new Paquete("MegaPromo");
        paquetePrincipal.agregarProducto(hotelDeLujo);
        paquetePrincipal.agregarProducto(subPaquete);

        // Costo Total (2 noches): 100.000 + 38.000 = $138.000
        assertEquals(138000.0, paquetePrincipal.calcularCosto(2), 0.01,
                "El paquete debe manejar la recursividad (paquetes anidados).");
    }

    // ====================================================================
    // 4. TESTS DE USUARIO Y OPERACIONES
    // ====================================================================

    @Test
    void testUsuarioContratarExitoso() {
        // Costo: 50.000 (Hotel 1 noche)
        double costo = hotelDeLujo.calcularCosto(1);
        assertTrue(usuarioConFondos.intentarContratar(hotelDeLujo, 1),
                "La compra debe ser exitosa con fondos suficientes.");

        // Presupuesto final: 100.000 - 50.000 = 50.000
        assertEquals(100000.0 - costo, usuarioConFondos.getPresupuestoInicial(), 0.01,
                "El presupuesto debe reducirse correctamente.");
        assertEquals(1, usuarioConFondos.getHistorialCompras().size(),
                "El producto debe agregarse al historial.");
    }

    @Test
    void testUsuarioContratarFallo() {
        // Costo: 50.000 (Hotel 1 noche). Presupuesto: 5.000
        assertFalse(usuarioSinFondos.intentarContratar(hotelDeLujo, 1),
                "La compra debe fallar si no hay fondos.");

        // El presupuesto y el historial no deben cambiar
        assertEquals(5000.0, usuarioSinFondos.getPresupuestoInicial(), 0.01,
                "El presupuesto no debe cambiar tras un fallo.");
        assertEquals(0, usuarioSinFondos.getHistorialCompras().size(),
                "El historial no debe registrar compras fallidas.");
    }
}