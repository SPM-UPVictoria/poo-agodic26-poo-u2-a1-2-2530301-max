package edu.upv.streamflow.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class SuscripcionStreamingFamiliarTest {

    @Test
    public void deberiaBonificarUnicoPerfilAdicional() {
        // Arrange
        SuscripcionStreamingFamiliar suscripcion = new SuscripcionStreamingFamiliar("FAM-1", 200.0, 1, 60.0);

        // Act & Assert (1 perfil extra bonificado, solo paga tarifa base = 200.0)
        assertEquals(200.0, suscripcion.calcularMontoFacturable(), 0.001);
    }

    @Test
    public void deberiaCobrarPerfilesExcedentesDespuesDeBonificar() {
        // Arrange
        SuscripcionStreamingFamiliar suscripcion = new SuscripcionStreamingFamiliar("FAM-2", 200.0, 3, 60.0);

        // Act & Assert (3 perfiles extra, se bonifica 1, se cobran 2: 200 + 2 * 60 = 320)
        assertEquals(320.0, suscripcion.calcularMontoFacturable(), 0.001);
    }

    @Test
    public void deberiaMantenerTarifaBaseSiNoHayPerfilesExtra() {
        // Arrange
        SuscripcionStreamingFamiliar suscripcion = new SuscripcionStreamingFamiliar("FAM-3", 200.0, 0, 60.0);

        // Act & Assert
        assertEquals(200.0, suscripcion.calcularMontoFacturable(), 0.001);
    }
}