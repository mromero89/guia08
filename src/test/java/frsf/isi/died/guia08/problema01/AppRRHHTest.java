package frsf.isi.died.guia08.problema01;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Tarea;

public class AppRRHHTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		
		AppRRHH app = new AppRRHH();
		
		app.agregarEmpleadoContratado(1004,"Ezequiel Rodriguez",10.0);
		app.agregarEmpleadoEfectivo(1005, "Juan Noguera", 12.5);
		app.asignarTarea(1004,100,"tarea 100",12);
		
		app.empezarTarea(1004, 100);
		app.terminarTarea(1004, 100);
		app.cargarEmpleadosContratadosCSV("contratados.csv");
		app.cargarEmpleadosEfectivosCSV("efectivos.csv");
		app.asignarTarea(120, 140, "Tarea 140", 19);
		app.cargarTareasCSV("tareas.csv");
		
		ArrayList<Empleado> empleados = (ArrayList<Empleado>) app.getEmpleados();

		for (Empleado e : empleados) {
						
			List<Tarea> tareasAux = e.getTareasAsignadas();
			
			for(Tarea t : tareasAux) {
				t.setFacturada(false);
				t.setFechaInicio(LocalDateTime.of(2020, 05, 27, 10, 00));
				t.setFechaFin(LocalDateTime.now());

			}
		}
		
		

		for (Empleado e : empleados) {
			System.out.println("Nombre empleado: "+e.getNombre()+"- CUIL: "+e.getCuil()+" -Tipo: "+e.getTipo());
			
			List<Tarea> tareasAux = e.getTareasAsignadas();
			
			for(Tarea t : tareasAux) {
				

				System.out.println("Id de la tarea:"+t.getId()+" nombre de la tarea: "+ t.getDescripcion());
				System.out.println("Fecha de inicio: "+t.getFechaInicio()+"Fecha de fin: "+t.getFechaFin());
				System.out.println("Facturada: "+t.getFacturada());
			}
		}
		System.out.println("Total a facturar: "+app.facturar());
		
		//fail("Not yet implemented");
	}

}
