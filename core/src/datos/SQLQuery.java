package datos;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;



public class SQLQuery implements Callable<String>
{
	public static final int HTTP_TIMEOUT = 20 * 1000; // The time it takes for our client to timeout (milliseconds)
	private static String resultado;
	private static String consultaSQL;
	private static String nombreBBDD;
	
	
	//Constructor que recibe el string de la consultaSQL
	public SQLQuery (String ConsultaSQL, String nBD)
	{
		consultaSQL = ConsultaSQL;
		nombreBBDD = nBD;
	}

	//El metodo principal que se ejecuta cuando se crea el Objeto
	public String call() 
	{
		try { executeHttpPost(); } 
		catch (Exception e) { System.out.println("ERROR INESPERADO: "+ e.getMessage());	e.printStackTrace();}
		return resultado;
	}

	//Realiza una petición POST a la url especificada, con los parámetros especificados.
	//Recibe la url y los parámetros
	//Devuelve el resultado de la petición.
	public void executeHttpPost() throws Exception 
	{
		BufferedReader in = null;
		try 
		{
			String direccion = Constantes.DIRECCION_SCRIPT_CONEXION_SQL;
			URL url = new URL(direccion);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("POST");
			String urlParametros = "bd="+nombreBBDD+"&consulta="+consultaSQL;
			
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParametros);
			wr.flush();
			wr.close();
			
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) 
			{	response.append(inputLine); }
			
			in.close();
			
			resultado = response.toString();
		} 
		finally 
		{
			if (in != null) 
			{
				try { in.close(); } 
				catch (IOException e) { System.out.println("Error converting result "+e.toString()); e.printStackTrace(); }
			}
		}
	}
}