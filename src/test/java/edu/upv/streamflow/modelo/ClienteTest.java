package edu.upv.streamflow.modelo;

import edu.upv.streamflow.excepciones.TransaccionRechazadaException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClienteTest {

    @Test
    public void deberiaInstanciarClienteValido() {
        // Arrange & Act
        Cliente cliente = new Cliente("CLI-1", "Ana Gomez", 1500.0);

        // Assert
        assertEquals("CLI-1", cliente.getIdCliente());
        assertEquals("Ana Gomez", cliente.getNombre());
        assertEquals(1500.0, cliente.getSaldoDisponible(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deberiaLanzarExcepcionPorNombreInvalido() {
        // Arrange & Act
        new Cliente("CLI-2", "   ", 500.0);
        // Assert esperado: IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void deberiaLanzarExcepcionPorSaldoInicialNegativo() {
        // Arrange & Act
        new Cliente("CLI-3", "Carlos Ruiz", -1.0);
        // Assert esperado: IllegalArgumentException
    }

    @Test
    public void deberiaAbonarSaldoCorrectamente() {
        // Arrange
        Cliente cliente = new Cliente("CLI-4", "Maria Lopez", 1000.0);

        // Act
        cliente.abonarSaldo(500.0);

        // Assert
        assertEquals(1500.0, cliente.getSaldoDisponible(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deberiaLanzarExcepcionAlAbonarMontoNegativo() {
        // Arrange
        Cliente cliente = new Cliente("CLI-5", "Pedro Perez", 1000.0);

        // Act
        cliente.abonarSaldo(-50.0);
    }

    @Test
    public void deberiaDebitarSaldoExitosamente() throws TransaccionRechazadaException {
        // Arrange
        Cliente cliente = new Cliente("CLI-6", "Lucia Torres", 800.0);

        // Act
        cliente.debitarSaldo(300.0);

        // Assert
        assertEquals(500.0, cliente.getSaldoDisponible(), 0.001);
    }

    @Test(expected = TransaccionRechazadaException.class)
    public void deberiaLanzarExcepcionPropiaAlDebitarMontoMayorAlSaldo() throws TransaccionRechazadaException {
        // Arrange
        Cliente cliente = new Cliente("CLI-7", "Jorge Diaz", 200.0);

        // Act (Debe lanzar TransaccionRechazadaException porque 500 > 200)
        cliente.debitarSaldo(500.0);
    }
}