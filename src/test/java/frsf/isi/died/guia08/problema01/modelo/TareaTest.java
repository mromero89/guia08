package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import org.junit.Test;

public class TareaTest {

	@Test(expected = TareaException.class)
	public void asignarEmpleadoTestEx() {
		Empleado e1 = new Empleado();
		Empleado e2 = new Empleado();
		Tarea t1 = new Tarea();
		t1.setId(100);
		e1.asignarTarea(t1);
		e1.finalizar(100);
		
		t1.asignarEmpleado(e2);
		//fail("Not yet implemented");
	}
	
	@Test
	public void asignarEmpleadoTest() {
		Empleado e1 = new Empleado();
		Tarea t1 = new Tarea();
		t1.setId(100);
		
		
		t1.asignarEmpleado(e1);
		//fail("Not yet implemented");
	}

}
