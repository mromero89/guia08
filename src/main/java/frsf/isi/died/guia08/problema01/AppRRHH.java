package frsf.isi.died.guia08.problema01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.ExcepcionEmpleado;
import frsf.isi.died.guia08.problema01.modelo.Tarea;

public class AppRRHH {
	


	private List<Empleado> empleados = new ArrayList<Empleado>();
	
	//constructor: public Empleado(int cuil, String nombre, Tipo tipo, double costoHora) 
	
	//PROVISORIO
	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista
		Empleado aux = new Empleado(cuil, nombre, Tipo.CONTRATADO, costoHora);
		empleados.add(aux);
	}
	
	public void agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista	
		Empleado aux = new Empleado(cuil, nombre, Tipo.EFECTIVO, costoHora);
		empleados.add(aux);
	}
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) {
		// busca un empleado
		// con el método buscarEmpleado() de esta clase
		// agregarlo a la lista		
		
		//Constructor 	public Tarea(int id, String d, int duracion) {
		Tarea aux = new Tarea(idTarea, descripcion, duracionEstimada);
		Optional<Empleado> resultado = this.buscarEmpleado(e->e.getCuil().intValue()==cuil);
		if (resultado.isPresent()) {
			resultado.get().asignarTarea(aux);
		}
		
		
	}
	
	public void empezarTarea(Integer cuil,Integer idTarea) {
		// busca el empleado por cuil en la lista de empleados
		// con el método buscarEmpleado() actual de esta clase
		// e invoca al método comenzar tarea
		Optional<Empleado> e1 = buscarEmpleado(e -> e.getCuil().intValue() == cuil);
		Empleado aux2 = null;
		if (e1.isPresent()) {
			aux2 = e1.get();
		}	
		try {
			if (aux2 != null) {
				aux2.comenzar(idTarea);
			}
		}
		catch(ExcepcionEmpleado e) {
			e.getMessage();
		}
		
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) {
		// crear un empleado
		// agregarlo a la lista		
		
		Empleado aux = buscarEmpleado(e -> e.getCuil().intValue() == cuil).get();
		try {
		aux.finalizar(idTarea);
		}
		catch(ExcepcionEmpleado e) {
			e.getMessage();
		}
		
	}

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) throws FileNotFoundException, IOException {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado
		
		//FileInputStream fis;
		try(Reader fileReader = new FileReader(nombreArchivo)) {
		try(BufferedReader in = new BufferedReader(fileReader)){
		String linea = null;
		while((linea = in.readLine())!=null) {
			
		String[] fila = linea.split(";");
		
		int c = Integer.valueOf(fila[0]);
		String nomb = fila[1];
		double costoH = Double.valueOf(fila[2]);
		agregarEmpleadoContratado(c, nomb, costoH);
		
		}
		}
		}
		
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) throws FileNotFoundException, IOException{
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado
		
		try(Reader fileReader = new FileReader(nombreArchivo)) {
		try(BufferedReader in = new BufferedReader(fileReader)){
		String linea = null;
		while((linea = in.readLine())!=null) {
			
		String[] fila = linea.split(";");
		
		int c = Integer.valueOf(fila[0]);
		String nomb = fila[1];
		double costoH = Double.valueOf(fila[2]);
		agregarEmpleadoEfectivo(c, nomb, costoH);
		
		}
		}
		}
		
	}

	public void cargarTareasCSV(String nombreArchivo) throws FileNotFoundException, IOException {
		// leer datos del archivo
		// cada fila del archivo tendrá:
		// cuil del empleado asignado, numero de la taera, descripcion y duración estimada en horas.
		
		//Formato de archivo: ID, DESCRIPCION, DURACIONESTIMADA, CUILEMPLEADOASIGNAR
		
		try(Reader fileReader = new FileReader(nombreArchivo)) {
			try(BufferedReader in = new BufferedReader(fileReader)){
			String linea = null;
			while((linea = in.readLine())!=null) {
				
			String[] fila = linea.split(";");
			
			int id = Integer.valueOf(fila[0]);
			String desc = fila[1];
			int duracion = Integer.valueOf(fila[2]);
			int cuil = Integer.valueOf(fila[3]);
			
			//Constructor: public Tarea(int id, String d, int duracion)
			Tarea aux = new Tarea(id, desc, duracion);
			//aux.asignarEmpleado(buscarEmpleado(e->e.getCuil() == cuil).get());
			for (Empleado e : empleados) {
				if (e.getCuil() == cuil) {
					e.asignarTarea(aux);
					aux.asignarEmpleado(e);

				}
			}
			//tareas.add(aux);
			}
			}
			}
		
	}
	
	private void guardarTareasTerminadasCSV() throws IOException {
		// guarda una lista con los datos de la tarea que fueron terminadas
		// y todavía no fueron facturadas
		// y el nombre y cuil del empleado que la finalizó en formato CSV 
		for (Empleado e : empleados) {
			if (e.getTareasAsignadas().stream().filter(t->(t.getFacturada() != null && t.getFacturada() == false && t.getFechaFin()!=null)).collect(Collectors.toList()) != null) {
				List<Tarea> tareas = e.getTareasAsignadas().stream().filter(t->(t.getFacturada() != null && t.getFacturada() == false && t.getFechaFin()!=null)).collect(Collectors.toList());
			
			//escribir archivo - tiene que escribir: info de la tarea, cuil_empleado y nombre_empleado
			try(Writer fileWriter= new FileWriter("tareasTerminadas.csv",true)) {
				try(BufferedWriter out = new BufferedWriter(fileWriter)){
					//un for con las tareas a escribir
					for (Tarea t : tareas) {						
						out.write(t.asCSV()+ System.getProperty("line.separator"));

					}
				}
				}
			}
		}
	}
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}

	public Double facturar() throws IOException {
		this.guardarTareasTerminadasCSV();
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}
}
