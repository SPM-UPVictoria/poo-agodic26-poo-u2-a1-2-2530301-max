package edu.upv.streamflow.modelo;

import edu.upv.streamflow.excepciones.SuscripcionInactivaException;
import edu.upv.streamflow.excepciones.TransaccionRechazadaException;
import edu.upv.streamflow.interfaces.Facturable;
import org.junit.Test;
import static org.junit.Assert.*;

public class CicloCobroPolimorficoTest {

    @Test
    public void deberiaProcesarCobroPolimorficoEnSuscripcionStreaming() throws TransaccionRechazadaException {
        // Arrange
        Cliente cliente = new Cliente("CLI-10", "Sofía Pérez", 1000.0);
        Facturable suscripcion = new SuscripcionStreaming("STR-POLY", 200.0, 1, 50.0); // Costo: 250.0

        // Act
        suscripcion.procesarCobro(cliente);

        // Assert (1000.0 - 250.0 = 750.0)
        assertEquals(750.0, cliente.getSaldoDisponible(), 0.001);
    }

    @Test
    public void deberiaFallarCobroPorSaldoInsuficiente() {
        // Arrange
        Cliente cliente = new Cliente("CLI-11", "Esteban Quito", 50.0);
        Facturable suscripcion = new SuscripcionStreaming("STR-FAIL", 200.0, 1, 50.0); // Costo: 250.0

        // Act & Assert
        try {
            suscripcion.procesarCobro(cliente);
            fail("Se esperaba TransaccionRechazadaException");
        } catch (TransaccionRechazadaException e) {
            // Comprobar que el saldo del cliente quedó intacto
            assertEquals(50.0, cliente.getSaldoDisponible(), 0.001);
        }
    }

    @Test(expected = SuscripcionInactivaException.class)
    public void deberiaLanzarExcepcionNoMarcadaAlCobrarServicioCancelado() throws TransaccionRechazadaException {
        // Arrange
        Cliente cliente = new Cliente("CLI-12", "Valeria Gómez", 1000.0);
        SuscripcionStreaming suscripcion = new SuscripcionStreaming("STR-CANC", 150.0, 0, 50.0);

        // Cancelar el servicio antes de cobrar
        suscripcion.cancelarServicio();

        // Act (Debe lanzar SuscripcionInactivaException)
        suscripcion.procesarCobro(cliente);
    }

    @Test
    public void deberiaPermitirCancelarServicioActivo() {
        // Arrange
        SuscripcionStreaming suscripcion = new SuscripcionStreaming("STR-ACT", 150.0, 0, 50.0);
        assertTrue(suscripcion.estaActivo());

        // Act
        suscripcion.cancelarServicio();

        // Assert
        assertFalse(suscripcion.estaActivo());
    }
}