package edu.upv.streamflow.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class SuscripcionAlmacenamientoTest {

    @Test
    public void deberiaCobrarTarifaBaseDentroDelLimiteGratuito() {
        // Arrange
        SuscripcionAlmacenamiento suscripcion = new SuscripcionAlmacenamiento("ALM-1", 100.0, 50, 5.0);
        suscripcion.registrarConsumo(30);

        // Act & Assert (30 GB <= 50 GB límite, paga solo base)
        assertEquals(100.0, suscripcion.calcularMontoFacturable(), 0.001);
    }

    @Test
    public void deberiaCobrarTarifaBaseEnElLimiteExacto() {
        // Arrange
        SuscripcionAlmacenamiento suscripcion = new SuscripcionAlmacenamiento("ALM-2", 100.0, 50, 5.0);
        suscripcion.registrarConsumo(50);

        // Act & Assert (50 GB == 50 GB límite, paga solo base)
        assertEquals(100.0, suscripcion.calcularMontoFacturable(), 0.001);
    }

    @Test
    public void deberiaCalcularExcedenteDeAlmacenamiento() {
        // Arrange
        SuscripcionAlmacenamiento suscripcion = new SuscripcionAlmacenamiento("ALM-3", 100.0, 50, 5.0);
        suscripcion.registrarConsumo(70); // 20 GB extra sobre el límite de 50

        // Act & Assert (100 + 20 * 5.0 = 200.0)
        assertEquals(200.0, suscripcion.calcularMontoFacturable(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deberiaLanzarExcepcionAlRegistrarConsumoNegativo() {
        // Arrange
        SuscripcionAlmacenamiento suscripcion = new SuscripcionAlmacenamiento("ALM-4", 100.0, 50, 5.0);

        // Act
        suscripcion.registrarConsumo(-15);
    }
}