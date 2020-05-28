package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;


public class EmpleadoTest {

	// IMPORTANTE
	// ESTA CLASE ESTA ANOTADA COMO @IGNORE por lo que no ejecutará ningun test
	// hasta que no borre esa anotación.
	
		Empleado e1 = new Empleado();
		Empleado e2 = new Empleado();
		
		Tarea t1 = new Tarea();
		Tarea t2 = new Tarea();
		Tarea t3 = new Tarea();
		Tarea t4 = new Tarea();
		Tarea t5 = new Tarea();
		Tarea t6 = new Tarea();
		
		
	
	@Test
	public void testSalarioEfectivo() {
		//tareas no facturadas, y con fecha de fin
		//	public Tarea(int id, String d, int duracion) {
		
		//Con empleado de tipo CONTRATADO: OK
	
		e1.setTipo(Tipo.EFECTIVO);
		e1.setCostoHora(10.5);
		
		t1.setId(100);
		t2.setId(101);
		t3.setId(102);
		t1.setFacturada(false);
		t2.setFacturada(false);
		t3.setFacturada(false);
		t1.setDuracionEstimada(10);
		t2.setDuracionEstimada(12);
		t3.setDuracionEstimada(8);
		
		e1.asignarTarea(t1);
		e1.asignarTarea(t2);
		//e1.asignarTarea(t3);
		
		
		e1.comenzar(100);
		e1.comenzar(101);
		//e1.comenzar(102);
		
		e1.finalizar(100);
		e1.finalizar(101);
		//e1.finalizar(102);
		
		System.out.println("Salario del empleado efectivo: "+e1.salario());
		
		
		 

		
		//fail("Not yet implemented");
	}
	
	@Test
	public void testSalarioContratado() {
		e1.setTipo(Tipo.CONTRATADO);
		e1.setCostoHora(10.5);
		
		t1.setId(100);
		t2.setId(101);
		t3.setId(102);
		t1.setFacturada(false);
		t2.setFacturada(false);
		t3.setFacturada(false);
		t1.setDuracionEstimada(10);
		t2.setDuracionEstimada(12);
		t3.setDuracionEstimada(8);
		
		e1.asignarTarea(t1);
		e1.asignarTarea(t2);
		e1.asignarTarea(t3);
		
		
		e1.comenzar(100);
		e1.comenzar(101);
		e1.comenzar(102);
		
		e1.finalizar(100);
		e1.finalizar(101);
		e1.finalizar(102);
		
		System.out.println("Salario del empleado contratado: "+e1.salario());
	}

	
	@Test
	public void testAsignarTarea() {
		//fail("Not yet implemented");

		e1.setTipo(Tipo.CONTRATADO);
		Boolean resultado = e1.asignarTarea(t1);
		assertTrue(resultado);
		e1.asignarTarea(t2);
		e1.asignarTarea(t3);
		e1.asignarTarea(t4);
		e1.asignarTarea(t5);
		
		Tarea t6 = new Tarea();
		resultado = e1.asignarTarea(t6);
		assertFalse(resultado);
		
		
		
	}

	@Test(expected = ExcepcionEmpleado.class)
	public void testAsignarTareaException() {
		e1.asignarTarea(t1);
		e2.asignarTarea(t1);
		
	}
	
	@Test
	public void testComenzarInteger() {
		//	public Tarea(int id, String d, int duracion) {

		Tarea tarea = new Tarea(100, "Tarea nro 100", 15);
		e1.asignarTarea(tarea);
		e1.comenzar(100);
		ArrayList<Tarea> aux = (ArrayList<Tarea>) e1.getTareasAsignadas();
		System.out.println("Test ComenzarInteger:");
		for (Tarea t : aux) {
			System.out.println("ID: "+ t.getId() +" - Fecha inicio: "+ t.getFechaInicio().toString());
			
			
		}
		//fail("Not yet implemented");
	}
	
	@Test(expected = ExcepcionEmpleado.class)
	public void testComenzarIntegerEx() {
		
		e1.comenzar(100);
		//fail("Not yet implemented");
	}

	@Test
	public void testFinalizarInteger() {
		
		Tarea tarea = new Tarea(100, "Tarea nro 100", 15);
		e1.asignarTarea(tarea);
		e1.finalizar(100);
		ArrayList<Tarea> aux = (ArrayList<Tarea>) e1.getTareasAsignadas();
		System.out.println("Test FinalizarInteger:");

		for (Tarea t : aux) {
			System.out.println("ID: "+ t.getId() +" - Fecha fin: "+ t.getFechaFin().toString());
			
			
		}
		//fail("Not yet implemented");
	}

	@Test
	public void testComenzarIntegerString() {
				
		Tarea tarea = new Tarea(100, "Tarea nro 100", 15);
		e1.asignarTarea(tarea);
		e1.comenzar(100, "02-02-2020 09:00");
		ArrayList<Tarea> aux = (ArrayList<Tarea>) e1.getTareasAsignadas();
		for (Tarea t : aux) {
			System.out.println("Test ComenzarIntegerString:\nID: "+ t.getId() +" - Fecha inicio: "+ t.getFechaInicio().toString());
			
			
		}
		
		//fail("Not yet implemented");
	}

	@Test
	public void testFinalizarIntegerString() {

		Tarea tarea = new Tarea(100, "Tarea nro 100", 15);
		e1.asignarTarea(tarea);
		e1.finalizar(100, "02-05-2020 09:00");
		ArrayList<Tarea> aux = (ArrayList<Tarea>) e1.getTareasAsignadas();
		for (Tarea t : aux) {
			System.out.println("Test FinalizarIntegerString:\nID: "+ t.getId() +" - Fecha fin: "+ t.getFechaFin().toString());
			
			
		}
		
		//fail("Not yet implemented");
	}

}
