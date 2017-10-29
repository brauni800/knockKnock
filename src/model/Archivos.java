package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Archivos {

	private String ruta;
	
	public Archivos(String ruta) {
		super();
		this.ruta = ruta + ".txt";
	}

	public void insertarFecha() {
		try {
			File archivo = new File(ruta);
	        BufferedWriter bw;
	        if(archivo.exists()) {
	            bw = new BufferedWriter(new FileWriter(archivo, true));
	            bw.newLine();
	            bw.write(new Date().toString());
	        } else {
	            bw = new BufferedWriter(new FileWriter(archivo));
	            bw.write(new Date().toString());
	        }
	        bw.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	public long contarLineas() {
		long numLines = 0;
		try (
				BufferedReader bf = new BufferedReader(new FileReader(ruta));
				){
			
			numLines = bf.lines().count();
		} catch (IOException e) {
			e.getStackTrace();
			numLines = 0;
		}
		return numLines;
	}
	
	public String[] getButtonsNames() {
		String[] result = new String[3];
		try (
				BufferedReader bf = new BufferedReader(new FileReader(ruta));
				){
			result[0] = bf.readLine();
			result[1] = bf.readLine();
			result[2] = bf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
