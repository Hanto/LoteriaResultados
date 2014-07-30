package sorteos;

import static datos.CentroDeDatos.listaTiposSorteo;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import UI.GeneraSorteos.tipoGenerador;

import com.badlogic.gdx.utils.Array;

import datos.Constantes;
import datos.SQLQuery;

public class TiposSorteo
{
	private String nombre;
	private String nombreBDD;
	private String nombreFechaBDD;
	private String nombreAPP;
	private int IDSorteo;
	private String [] nombreCamposBDD;					//Nombre que tienen los campos en la BDD de inet (los nombres de las columnas)
	private String [] nombreCamposApp;					//Los nombres que queremos que tengan en nuestra aplicacion
	private int [] tipoDatos;							//el tipo de dato al que pertecene cada campo (Int, String, ...)
	private int posicionExtra;							//Posicion a partir de la cual estan los datos Extra del sorteo: Reintegro, complementario, estrellas, etc...
	private int numSorteosSemanales;					//Numero de sorteos que se realizan cada semana

	private boolean tieneBote = false;
	private boolean boteEnActivo = false;
	private String nombreBoteBDD;
	private Calendar fechaBote = GregorianCalendar.getInstance();
	private String diaBote;

	private String [] resultadoBotes = new String [3];

	private Array<Sorteo>listaSorteos = new Array<Sorteo>();	
	private Boolean isDatosActualizados = false;
	private Boolean isDatosCorrectos = false;

	public tipoGenerador generador;

	//GET
	public String getNombre ()							{ return nombre; }
	public String getNombreBDD ()						{ return nombreBDD; }
	public String getNombreFechaBDD ()					{ return nombreFechaBDD; }
	public String getNombreAPP ()						{ return nombreAPP; }
	public int getIDSorteo ()							{ return IDSorteo; }
	public int getPosicionExtra ()						{ return posicionExtra; }
	public String [] getNombreCamposBDD ()				{ return this.nombreCamposBDD; }
	public String [] getNombreCamposApp ()				{ return this.nombreCamposApp; }
	public int [] getTipoDatos ()						{ return this.tipoDatos; }
	public Array<Sorteo> getListaSorteos ()				{ return listaSorteos; }
	public Boolean isDatosActualizados ()				{ return isDatosActualizados; }
	public Boolean isDatosCorrectos ()					{ return isDatosCorrectos; }
	public int getNumResultados ()						{ return nombreCamposBDD.length; }
	public int getNumSorteosSemanales ()				{ return numSorteosSemanales; }
	public String [] getResultadoBotes ()				{ return resultadoBotes; }
	public String getNombreBoteBDD ()					{ return nombreBoteBDD;}
	public boolean getTieneBote ()						{ return tieneBote; }
	public boolean getBoteEnActivo ()					{ return boteEnActivo; }
	public Calendar getFechaBote ()						{ return fechaBote; }
	public String getDiaBote ()							{ return diaBote; }
	public int getDiaDelMesBote ()						{ return fechaBote.get(Calendar.DAY_OF_MONTH); }
	public int getMesBote ()							{ return (fechaBote.get(Calendar.MONTH)+1); } //se suma 1 al mes ya que los meses en calendar empiezan por 0
	public int getAñoBote ()							{ return fechaBote.get(Calendar.YEAR); }

	//SET
	public void setNombre (String nombre)				{ this.nombre = nombre; }
	public void setNombreBDD (String nombre)			{ nombreBDD = nombre; }
	public void setNombreFechaBDD (String nombre)		{ nombreFechaBDD = nombre; }	
	public void setNombreAPP (String nombre)			{ this.nombreAPP = nombre; }
	public void setPosicionExtra (int i)				{ posicionExtra = i; }
	public void setNumSorteosSemanales (int i)			{ numSorteosSemanales = i; }
	public void isDatosActualizados (Boolean b)			{ isDatosActualizados = b; }
	public void isDatosCorrectos (Boolean b)			{ isDatosCorrectos = b; }
	public void setTieneBote (Boolean b)				{ tieneBote = b; }
	public void setBoteEnActivo (Boolean b)				{ boteEnActivo = b; }
	public void setNombreBoteBDD (String s)				{ nombreBoteBDD = s; }

	public void setNombreCamposBDD (String [] S)
	{
		this.nombreCamposBDD = new String [S.length];
		System.arraycopy(S, 0, this.nombreCamposBDD, 0, S.length);
	}	
	public void setNombreCamposApp (String [] S)
	{
		this.nombreCamposApp = new String [S.length];
		System.arraycopy(S, 0, this.nombreCamposApp, 0, S.length);
	}
	public void setTipoDatos (int [] S)
	{
		this.tipoDatos = new int [S.length];
		System.arraycopy(S, 0, this.tipoDatos, 0, S.length);
	}
	public void setFechaBote (String fecha)
	{	//en el Calendar el mes empieza por el mes 0, por eso hay que restar 1 al mes actual de la base de datos, puesto que ahi el mes empieza en 1
		int primerGuion = fecha.indexOf('-',1);
		int segundoGuion = fecha.indexOf('-',primerGuion+1);

		int año, mes, dia;
		año = Integer.parseInt(fecha.substring(0, primerGuion));
		mes = Integer.parseInt(fecha.substring(primerGuion+1, segundoGuion));
		dia = Integer.parseInt(fecha.substring(segundoGuion+1, fecha.length()));

		this.fechaBote.clear();
		this.fechaBote.set(año, mes-1, dia);
		setDiaBote();
	}
	public void setDiaBote()
	{
	    int numDia = fechaBote.get(Calendar.DAY_OF_WEEK);

	    switch(numDia)
	    {
		    case 1: diaBote = "Domingo"; break;
		    case 2: diaBote = "Lunes"; break;
		    case 3: diaBote = "Martes"; break;
		    case 4: diaBote = "Miércoles"; break;
		    case 5: diaBote = "Jueves"; break;
		    case 6: diaBote = "Viernes"; break;
		    case 7: diaBote = "Sábado"; break;
		    default: diaBote = "";
	    }
	}

	//CONSTRUCTOR: crea un objeto de tipo sorteo, y le asigna el ID siguiente al que tiene el ultimo elemento de la lista
	public TiposSorteo (String nombreSorteo)
	{
		nombre = nombreSorteo;
		if (listaTiposSorteo.size == 0) { IDSorteo =0; }
		else { IDSorteo = listaTiposSorteo.get(listaTiposSorteo.size-1).getIDSorteo()+1; }
	}

	public void getSorteos(int numSorteos)
	{
		String response = null;

		// Llamar al método executeHttpPost pasandole los parametros necesarios
		try 
		{
			final ExecutorService service;
	        Future<String> result;

	        service = Executors.newFixedThreadPool(1);        
	        result = service.submit(new SQLQuery("SELECT * FROM "+nombreBDD+" order by "+nombreFechaBDD+" desc limit "+numSorteos+";", Constantes.BDD_RESULTADOS));

	        try { response = result.get(); }
	        catch (final InterruptedException ex) { /*ex.printStackTrace();*/ getSorteoVacio(); }
	        catch (final ExecutionException ex) { /*ex.printStackTrace();*/ getSorteoVacio(); }

	        service.shutdownNow();

			//parsear la informacion (json)
			try
			{
				listaSorteos.clear();
				JSONArray jArray = new JSONArray(response);
				for(int i=0;i<jArray.length();i++)
				{
	                 JSONObject json_data = jArray.getJSONObject(i);
	                 Sorteo sorteo = new Sorteo();

	                 //FECHA 
	                 String stringFecha = json_data.getString(nombreFechaBDD);
	                 try { sorteo.setFecha(stringFecha); }
	                 catch (Exception ex) { /*System.out.println("ERROR AL PARSEAR LA FECHA: "+ ex.getMessage());*/ getSorteoVacio(); }

	                 //TIPO SORTEO:
	                 sorteo.IDSorteo = IDSorteo;

	                 //RESULTADOS
	                 sorteo.setNumeroCampos(nombreCamposBDD.length);
	                 for (int j=0; j<nombreCamposBDD.length;j++)
	                 {
	                	 switch (getTipoDatos()[j])
	                	 {
	                	 	case Constantes.INT:		sorteo.getResultado()[j] = String.valueOf(json_data.getInt(getNombreCamposBDD()[j])); break;		
	                	 	case Constantes.WORD:		sorteo.getResultado()[j] = json_data.getString(getNombreCamposBDD()[j]); break;
	                	 } 
	                 }

	                 listaSorteos.add(sorteo);
				}
				isDatosActualizados = true;
				isDatosCorrectos = true;
			} 
			catch(JSONException e) 
			{ 
				//System.out.println("Error parseando los datos de "+nombre+" "+e.toString());
				getSorteoVacio();
			}
		} 
		catch (Exception e) 
		{ 
			//System.out.println("ERROR "+nombre+" in http connection!!" + e.getMessage());
			getSorteoVacio();
		}
	}

	public void getSorteosNuevos()
	{
		String response = null;

		// Llamar al método executeHttpPost pasandole los parametros necesarios
		try 
		{
			final ExecutorService service;
	        Future<String> result;

	        service = Executors.newFixedThreadPool(1);

	        String fechaUltimoSorteo = "'"+Integer.toString(listaSorteos.get(0).getAño())+"-"+Integer.toString(listaSorteos.get(0).getMes())+"-"+Integer.toString(listaSorteos.get(0).getDiaDelMes())+"'";
	        result = service.submit(new SQLQuery("SELECT * FROM "+nombreBDD+" WHERE "+nombreFechaBDD+" >+"+fechaUltimoSorteo+" order by "+nombreFechaBDD+" desc limit "+numSorteosSemanales*3*4+";", Constantes.BDD_RESULTADOS));

	        //System.out.println("SELECT * FROM "+nombreBDD+" WHERE "+nombreFechaBDD+" >"+fechaUltimoSorteo+" order by "+nombreFechaBDD+" desc limit "+numSorteosSemanales*3*4+";");

	        try { response = result.get(); }
	        catch (final InterruptedException ex) 
	        	{ isDatosCorrectos = false; /*ex.printStackTrace();*/ /* para que la proxima vez que se actualicen los datos, se machaque todo, por si acaso */ }
	        catch (final ExecutionException ex) 
	        	{ isDatosCorrectos = false; /*ex.printStackTrace();*/ }

	        service.shutdownNow();

			//parsear la informacion (json)
			try
			{
				//listaSorteos.clear();
				JSONArray jArray = new JSONArray(response);
				for(int i=0;i<jArray.length();i++)
				{
	                 JSONObject json_data = jArray.getJSONObject(i);
	                 Sorteo sorteo = new Sorteo();

	                 //FECHA 
	                 String stringFecha = json_data.getString(nombreFechaBDD);
	                 try { sorteo.setFecha(stringFecha); }
	                 catch (Exception ex) 
	                 	{ isDatosCorrectos = false; /*System.out.println("ERROR AL PARSEAR LA FECHA: "+ ex.getMessage());*/  }

	                 //TIPO SORTEO:
	                 sorteo.IDSorteo = IDSorteo;

	                 //RESULTADOS
	                 sorteo.setNumeroCampos(nombreCamposBDD.length);
	                 for (int j=0; j<nombreCamposBDD.length;j++)
	                 {
	                	 switch (getTipoDatos()[j])
	                	 {
	                	 	case Constantes.INT:		sorteo.getResultado()[j] = String.valueOf(json_data.getInt(getNombreCamposBDD()[j])); break;		
	                	 	case Constantes.WORD:		sorteo.getResultado()[j] = json_data.getString(getNombreCamposBDD()[j]); break;
	                	 } 
	                 }

	                 listaSorteos.insert(i, sorteo);
	                 listaSorteos.pop();
				}
			} 
			catch(JSONException e) 
				{ isDatosCorrectos = false; /* System.out.println("Error parseando los datos de "+nombre+" "+e.toString()); */ }
		} 
		catch (Exception e) 
			{ isDatosCorrectos = false; /* System.out.println("ERROR "+nombre+" in http connection!!" + e.getMessage());*/ }
	}

	public void getSorteoVacio ()
	{
		System.out.println("ERROR recogiendo los datos del Tipo Sorteo: "+ nombre);

		Sorteo sorteo = new Sorteo();
		sorteo.setFecha("01-01-2000");
		sorteo.IDSorteo = IDSorteo;
		sorteo.setNumeroCampos(nombreCamposBDD.length);
		sorteo.setNumeroCampos(nombreCamposBDD.length);
        for (int j=0; j<nombreCamposBDD.length;j++)
        {
        	switch (getTipoDatos()[j])
           	{
           		case Constantes.INT:		sorteo.getResultado()[j] = "-"; break;		
           	 	case Constantes.WORD:		sorteo.getResultado()[j] = "-"; break;
           	} 
        }
        
        //Lo añado 4 veces por ahora, por que hay sorteos que tienen 4 resultados semanales como el bonoloto, para que no explote la representacion semanal
        listaSorteos.add(sorteo);
        listaSorteos.add(sorteo);
        listaSorteos.add(sorteo);
        listaSorteos.add(sorteo);
        
        isDatosActualizados = true;
		isDatosCorrectos = false;
	}
}
