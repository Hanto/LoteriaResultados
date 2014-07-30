package datos;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import screen.PantallaResultados;
import sorteos.TiposSorteo;

import com.badlogic.gdx.utils.Array;

public class CentroDeDatos implements Runnable
{
	public static Array<TiposSorteo> listaTiposSorteo = new Array<TiposSorteo>(Constantes.NOMBRE_CAMPOS_BDD1.length);
	public static boolean generarTablasResultados = false;
	public static boolean actualizandoDatos = false;

	//THREAT RUN:
	@Override
	public void run() 			{ actualizarSorteos (); }

	//CONSTRUCTOR:
	public CentroDeDatos ()		{ inicializarTiposSorteo ();}

	public static TiposSorteo getTipoSorteoPorNombre (String nombreTipoSorteo)
	{
		for (int i=0; i<listaTiposSorteo.size; i++)
		{ if (listaTiposSorteo.get(i).getNombre().toLowerCase().equals(nombreTipoSorteo.toLowerCase())) return listaTiposSorteo.get(i); }
		return null;
	}

	public static void inicializarTiposSorteo ()
	{	
		listaTiposSorteo.clear();

		for (int i=0; i< Constantes.NOMBRE_SORTEOS.length; i++)
		{
			//NOMBRE, IDSORTEO: Creamos un Objeto de Tipo TiposSorteo e inicializamos su nombre e ID;
			TiposSorteo TSorteo = new TiposSorteo(Constantes.NOMBRE_SORTEOS[i]);

			//NOMBRE SORTEO EN LA BDD1 y BDD2:
			TSorteo.setNombreBDD(Constantes.NOMBRE_SORTEOS_BDD[i]);
			TSorteo.setNombreFechaBDD(Constantes.NOMBRE_FECHA_BDD[i]);

			//NOMBRE SORTEOS APP:
			TSorteo.setNombreAPP(Constantes.NOMBRE_SORTEOS_APP[i]);

			//NOMBRE CAMPOS CORTO:
			String [] S = new String [Constantes.NOMBRE_CAMPOS_BDD1[i].length];
			for (int j=0; j < S.length; j++) { S[j]=Constantes.NOMBRE_CAMPOS_BDD1[i][j]; }
			TSorteo.setNombreCamposBDD(S);

			//NOMBRE CAMPOS LARGO:
			S = new String [Constantes.NOMBRE_CAMPOS_APP[i].length];
			for (int j=0; j < S.length; j++) { S[j]=Constantes.NOMBRE_CAMPOS_APP[i][j]; }
			TSorteo.setNombreCamposApp(S);

			//TIPO DATOS:
			int [] I = new int [Constantes.TIPODATOS[i].length];
			for (int j=0; j< I.length; j++) { I[j]=Constantes.TIPODATOS[i][j]; }
			TSorteo.setTipoDatos(I);

			//POSICIONEXTRA:
			TSorteo.setPosicionExtra(Constantes.POSICIONEXTRA[i]);

			//SORTEOS SEMANALES:
			TSorteo.setNumSorteosSemanales(Constantes.SORTEOS_SEMANALES[i]);		

			//BOTES:
			if (Constantes.BOTES_NOMBRE_BDD[i] != "")	{ TSorteo.setTieneBote(true); }
			TSorteo.setNombreBoteBDD(Constantes.BOTES_NOMBRE_BDD[i]);

			//GENERADOR (generador que usa para enseñar sus resultados)
			TSorteo.generador = Constantes.GENERADORES[i];

			listaTiposSorteo.add(TSorteo);
		}
	}

	public static void actualizarSorteos ()
	{	
		actualizandoDatos=true;
		getBotes();

		//int datosActualizados = 0;
		//LISTA SORTEOS:
		for (int i=0; i<listaTiposSorteo.size;i++)
		{
			if (!listaTiposSorteo.get(i).isDatosActualizados() || !listaTiposSorteo.get(i).isDatosCorrectos()) 
				listaTiposSorteo.get(i).getSorteos(listaTiposSorteo.get(i).getNumSorteosSemanales()*4*3);
			else listaTiposSorteo.get(i).getSorteosNuevos();

			//if (listaTiposSorteo.get(i).isDatosActualizados()) datosActualizados++;
		}
		//Se activa un booleano para que las tablas se reconstruyan en el Main render thread, puesto que los objetos group, stage, actor, etc, no son threat safe y
		//no pueden modificarse desde aqui
		if (/*datosActualizados == listaTiposSorteo.size && */generarTablasResultados) {generarTablasResultados = false; PantallaResultados.generarTablasResultados = true;};	

		actualizandoDatos=false;
	}

	public static void getBotes()
	{
		String response = null;

		// Llamar al método executeHttpPost pasandole los parametros necesarios
		try 
		{
			final ExecutorService service;
	        Future<String> result;

	        service = Executors.newFixedThreadPool(1);        
	        result = service.submit(new SQLQuery("SELECT * FROM (SELECT * FROM BOTES ORDER BY Fecha DESC) AS T GROUP BY SORTEO", Constantes.BDD_ESCRUTINIOS));

	        try { response = result.get(); }
	        catch(final InterruptedException ex) { ex.printStackTrace(); }
	        catch(final ExecutionException ex) { ex.printStackTrace(); }

	        service.shutdownNow();

			//parsear la informacion (json)
			try
			{
				JSONArray jArray = new JSONArray(response);
				for(int i=0;i<jArray.length();i++)
				{
	                 JSONObject json_data = jArray.getJSONObject(i);

	                 //FECHA
	                 String stringNombreSorteo = json_data.getString(Constantes.BOTES_NOMBRE_CAMPOS_BDD[0]);			//Sorteo
	                 String stringFecha = json_data.getString(Constantes.BOTES_NOMBRE_CAMPOS_BDD[1]);					//Fecha
	                 String stringCantidad = json_data.getString(Constantes.BOTES_NOMBRE_CAMPOS_BDD[2]);				//Cantidad

	                 for (int j=0; j<listaTiposSorteo.size; j++)
	                 {
	                	 if (listaTiposSorteo.get(j).getNombreBoteBDD().equals(stringNombreSorteo))
	                	 {
	                		 listaTiposSorteo.get(j).getResultadoBotes()[0] = stringNombreSorteo;
	                		 listaTiposSorteo.get(j).getResultadoBotes()[1] = stringFecha;
	                		 listaTiposSorteo.get(j).setFechaBote(stringFecha);
	                		 listaTiposSorteo.get(j).getResultadoBotes()[2] = stringCantidad;

	                		 //como en la BDD todos los botes tienen en la fecha la hora 00:00:00, cuando el dia del bote coincide con el dia actual saldria sin bote
	                		 //puesto que el dia actual tiene horas y minutos, para que el bote sea valido durante todo el dia le pondremos la ultima hora del dia
	                		 Calendar diaActual = Calendar.getInstance(Locale.getDefault());
	                		 Calendar diaSorteo = listaTiposSorteo.get(j).getFechaBote();
	                		 diaSorteo.set(Calendar.HOUR, 23);
	                		 diaSorteo.set(Calendar.MINUTE, 59);
	                		 if (diaActual.after(diaSorteo))	
	                		 	{ listaTiposSorteo.get(j).setBoteEnActivo(false); }
		                	 else listaTiposSorteo.get(j).setBoteEnActivo(true);

	                		 break;
	                	 }
	                 }
				}     
			} 
			catch(JSONException e) 
			{ 
				System.out.println("Error parseando los datos de "+e.toString());
				for (int j=0; j<listaTiposSorteo.size; j++)
				{
					listaTiposSorteo.get(j).getResultadoBotes()[0] = "-";
					listaTiposSorteo.get(j).getResultadoBotes()[1] = "01-01-2000";
					listaTiposSorteo.get(j).setFechaBote("01-01-2000");
					listaTiposSorteo.get(j).getResultadoBotes()[2] = "0";
                }
			}
		} 
		catch (Exception e) 
		{ 
			System.out.println("ERROR in http connection!!" + e.getMessage());
			for (int j=0; j<listaTiposSorteo.size; j++)
			{
				listaTiposSorteo.get(j).getResultadoBotes()[0] = "-";
				listaTiposSorteo.get(j).getResultadoBotes()[1] = "01-01-2000";
				listaTiposSorteo.get(j).setFechaBote("01-01-2000");
				listaTiposSorteo.get(j).getResultadoBotes()[2] = "0";
            }
		}
	}
}