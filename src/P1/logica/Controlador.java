/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P1.logica;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Properties;

/**
 *
 * @author marco
 */
public class Controlador implements Runnable{
    
    private Socket conexion;
    
    private DataOutputStream alServidor;
    private BufferedReader delServidor;
    

    public Controlador() throws IOException  {
        /*Properties p = new Properties();
        p.load(new FileReader(new File("./info_servidor.properties")));*/
        
        
    }
    
    //V: Valida sesion
    public boolean iniciaSesion(String nombre, String pass) throws IOException, ClassNotFoundException{
        alServidor.writeBytes("V-" + nombre + "-" + pass+"\n");
        System.out.println("Se ha enviado " + "V-" + nombre + "-" + pass);
        String responce = this.delServidor.readLine();
        
        return responce.equals("pv");
        //pv: petition valid
    }
    
    //I: Invalida sesion
    public boolean cierraSesion()
            throws IOException, ClassNotFoundException{
        alServidor.writeBytes("I\n");
        
        String responce = this.delServidor.readLine();
        
        return responce.equals("pv");
    }
    
    //D: Deposita cantidad
    public synchronized boolean depositar(Double deposito)
            throws IOException, ClassNotFoundException{
        alServidor.writeBytes("D-" + deposito + "\n");
        
        String responce = this.delServidor.readLine();
        System.out.println("Responce received " + responce);
        
        return responce.equals("pv");
    }
    
    //R: Retira cantidad
    public synchronized boolean retirar(Double retiro)
            throws IOException, ClassNotFoundException{
        alServidor.writeBytes("R-" + retiro + "\n");
        
        String responce = this.delServidor.readLine();
        
        return responce.equals("pv");
    }
    
    //C: Consulta saldo
    public synchronized double consultar()
            throws IOException, ClassNotFoundException{
        alServidor.writeBytes("C\n");
        
        String responce = this.delServidor.readLine();
        System.out.println(responce);
        
        return Double.parseDouble(responce);
    }
    
    //T: Transferir cantidad
    public synchronized boolean transferir(BigInteger idCuentaDestino, Double cantidad)
            throws IOException, ClassNotFoundException{
        alServidor.writeBytes("T-" + cantidad + "-" + idCuentaDestino + "\n");
        
        String responce = this.delServidor.readLine();
        
        return responce.equals("pv");
    }

	@Override
	public void run() {
		try {
			conexion = new Socket("localhost", 3035);
			alServidor = new DataOutputStream(this.conexion.getOutputStream());
	        delServidor = new BufferedReader(new InputStreamReader(this.conexion.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}
