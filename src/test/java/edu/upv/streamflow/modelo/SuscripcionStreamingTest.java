package edu.upv.streamflow.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class SuscripcionStreamingTest {

    @Test
    public void deberiaCalcularTarifaSinPerfilesExtra() {
        // Arrange
        SuscripcionStreaming suscripcion = new SuscripcionStreaming("STR-1", 150.0, 0, 50.0);

        // Act & Assert
        assertEquals(150.0, suscripcion.calcularMontoFacturable(), 0.001);
    }

    @Test
    public void deberiaCalcularTarifaConPerfilesExtra() {
        // Arrange
        SuscripcionStreaming suscripcion = new SuscripcionStreaming("STR-2", 150.0, 2, 50.0);

        // Act & Assert (150 + 2 * 50 = 250)
        assertEquals(250.0, suscripcion.calcularMontoFacturable(), 0.001);
    }

    @Test
    public void deberiaIncrementarCostoAlAgregarPerfil() {
        // Arrange
        SuscripcionStreaming suscripcion = new SuscripcionStreaming("STR-3", 100.0, 1, 30.0);
        // Costo inicial: 100 + 1 * 30 = 130
        assertEquals(130.0, suscripcion.calcularMontoFacturable(), 0.001);

        // Act
        suscripcion.agregarPerfil();

        // Assert (Ahora son 2 perfiles: 100 + 2 * 30 = 160)
        assertEquals(160.0, suscripcion.calcularMontoFacturable(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deberiaLanzarExcepcionSiPerfilesInicialesEsNegativo() {
        // Arrange & Act
        new SuscripcionStreaming("STR-4", 150.0, -2, 50.0);
        // Assert esperado: IllegalArgumentException
    }
}