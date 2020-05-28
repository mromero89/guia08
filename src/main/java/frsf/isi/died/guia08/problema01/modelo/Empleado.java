package frsf.isi.died.guia08.problema01.modelo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Empleado {

	public enum Tipo { CONTRATADO,EFECTIVO}; 
	
	private Integer cuil;
	
	public Integer getCuil() {
		return cuil;
	}

	private String nombre;
	private Tipo tipo;
	private Double costoHora;
	private List<Tarea> tareasAsignadas;
	
	private Function<Tarea, Double> calculoPagoPorTarea;		
	private Predicate<Tarea> puedeAsignarTarea;
	
	public Empleado() {
		tareasAsignadas = new ArrayList<Tarea>();

	}
	
	public Empleado(int c, String n, Tipo t, double costo) {
		tareasAsignadas = new ArrayList<Tarea>();
		cuil = c;
		nombre = n;
		tipo = t;
		costoHora = costo;
		
	}

	
	public List<Tarea> getTareasAsignadas() {
		return tareasAsignadas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setCuil(Integer cuil) {
		this.cuil = cuil;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCostoHora(Double costoHora) {
		this.costoHora = costoHora;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Double salario() {
		// cargar todas las tareas no facturadas
		// calcular el costo
		// marcarlas como facturadas.
				
		double suma = 0.0;
		for (Tarea t : tareasAsignadas) {
			if (t.getFacturada() != null && t.getFacturada() == false && t.getFechaFin() !=null) {
				System.out.println("Tarea a facturar: "+t.getId()+" con duracion de: "+t.getDuracionEstimada());
				

				Long demoraEnTerminar = 4*(Duration.between(t.getFechaInicio(), t.getFechaFin()).toDays());
				
				if (t.getDuracionEstimada() > demoraEnTerminar) {
					if (this.tipo == Tipo.EFECTIVO)
						suma += ((double) t.getDuracionEstimada())*this.costoHora*1.2;
					else
						suma += ((double) t.getDuracionEstimada())*this.costoHora*1.3;
				}
				else
					if (this.tipo == Tipo.EFECTIVO) {
						suma += ((double) t.getDuracionEstimada())*this.costoHora;

					}
					else
						if (demoraEnTerminar-t.getDuracionEstimada()>=2 && tipo == Tipo.CONTRATADO) {
							suma += ((double) t.getDuracionEstimada())*this.costoHora*0.75;
						}
						else
							suma += ((double) t.getDuracionEstimada())*this.costoHora;
				
				t.setFacturada(true);
			}
				
		}
		
		return suma;
		
	}
	
	/**
	 * Si la tarea ya fue terminada nos indica cuaal es el monto según el algoritmo de calculoPagoPorTarea
	 * Si la tarea no fue terminada simplemente calcula el costo en base a lo estimado.
	 * @param t
	 * @return
	 */
	public Double costoTarea(Tarea t) {
		return 0.0;
	}
		
	public Boolean asignarTarea(Tarea t) {
		if (t.getEmpleadoAsignado() != null || t.getFechaFin() != null) {
			throw new ExcepcionEmpleado("Sucedió un error al asignar la tarea");

		}
			
		if(tipo == Tipo.CONTRATADO && tareasAsignadas.size() >= 5)
			return false;
		if (tipo == Tipo.EFECTIVO && cantHsPendientes() >= 15)
			return false;
		
		tareasAsignadas.add(t);
		t.asignarEmpleado(this);
		return true;
		
	}
	
	private int cantHsPendientes() {
		int suma = 0;
		for (Tarea t : tareasAsignadas) {
			if (t.getFechaFin() == null)
				suma += t.getDuracionEstimada();
		}
		return suma;
	}
	
	public void comenzar(Integer idTarea) {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de inicio la fecha y hora actual
		boolean encontrado = false;
		for (Tarea t : tareasAsignadas) {
			if (t.getId() == idTarea) {
				
				t.setFechaInicio(LocalDateTime.now());
				encontrado = true;
			}
				
		}
		if (encontrado == false) {
			
			throw new ExcepcionEmpleado("No se encontro la tarea");

		}
	}
	
	public void finalizar(Integer idTarea) {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		
		boolean encontrado = false;
		for (Tarea t : tareasAsignadas) {
			if (t.getId() == idTarea) {
				t.setFechaFin(LocalDateTime.now());
				encontrado = true;
			}
				
		}
		if (encontrado == false)
			throw new ExcepcionEmpleado("No se encontro la tarea");
		
	}

	public void comenzar(Integer idTarea,String fecha) {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		
		boolean encontrado = false;
		for (Tarea t : tareasAsignadas) {
			if (t.getId() == idTarea) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

				t.setFechaInicio(LocalDateTime.parse(fecha, formatter));
				encontrado = true;
			}
				
		}
		if (encontrado == false)
			throw new ExcepcionEmpleado("No se encontro la tarea");
		
	}
	
	public void finalizar(Integer idTarea,String fecha) {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		
		boolean encontrado = false;
		for (Tarea t : tareasAsignadas) {
			if (t.getId() == idTarea) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

				t.setFechaFin(LocalDateTime.parse(fecha, formatter));
				encontrado = true;
			}
				
		}
		if (encontrado == false)
			throw new ExcepcionEmpleado("No se encontro la tarea");
		
	}
}
