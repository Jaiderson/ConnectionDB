package util;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Utilidades {

	/**
	 * 
	 * @param esImagen Indica si es archivo de imagen o de log.
	 * @param nomArchivo nombre del archivo al cual se le extrera la ruta.
	 * 
	 * @return Ruta absoluta del archivo.
	 */
	public static String getRutaEvidencias(boolean esImagen, String nomArchivo){
		if(nomArchivo == null || nomArchivo.trim().isEmpty()) {
			return "";
		}
		
		String archivo = esImagen ? "\\evidencia\\img\\"+nomArchivo : "\\evidencia\\"+nomArchivo;
		File file = new File(archivo);
		return file.getAbsolutePath();		
	}
	/**
	 * 
	 * @param fecha A formatear.
	 * @return Fecha en formato dd-MM-yyyy sin hora.
	 */
	public static String getFechaLocal(Date fecha){
		if(fecha == null){
			return "";
		}
		LocalDateTime fec = LocalDateTime.ofInstant(new Date(fecha.getTime()).toInstant(), ZoneId.systemDefault());
		return fec.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}
	/**
	 * 
	 * @param fecha a formatear
	 * @return Fecha en formato dd/MM/yyyy HH:mm:ss
	 */
	public static String getFecha(Date fecha){
		if(fecha == null){
			return "";
		}
		
		LocalDateTime fec = LocalDateTime.ofInstant(new Date(fecha.getTime()).toInstant(), ZoneId.systemDefault());
		String result = fec.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		int hora = Integer.parseInt(result.substring(11,13));
		if(hora < 10){
			result = result.substring(0, 11)+result.substring(12); 
		}
		
		return result;
	}
	/**
	 * 
	 * @param fecha
	 * @return
	 */
	public static Date getFecha(LocalDateTime fecha){
		if(fecha == null){
			return null;
		}
		
	    Instant instant = fecha.atZone(ZoneId.systemDefault()).toInstant();
	    return Date.from(instant);		
	}
	/**
	 * 
	 * @param lista Lista de cadenas a extraer in item aleatorio.
	 * @return Elemento extraido aleatoriamente.
	 */
	public static String getItemRandom(List<String> lista) {
		if(lista == null || lista.isEmpty()) {
			return "";
		}
	
		int index = -1;
		
		do {
			index = (int) (Math.random() * lista.size());
		}
		while(index < 0 || index >= lista.size());
		
		return lista.get(index);
	}
	/**
	 * 
	 * @return Numero aletorio entre 0 y 5.
	 */
	public static int getCantidadRandom() {
		int index = -1;
		
		do {
			index = (int) (Math.random() * 5);
		}
		while(index <= 0 || index > 5);
		
		return index;
	}
	
	
	
}
