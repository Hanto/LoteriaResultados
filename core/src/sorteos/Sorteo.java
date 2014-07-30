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

import screen.PantallaResultados;
import utils.GroupText;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.me.Game.Loto;

import datos.Constantes;
import datos.SQLQuery;

public class Sorteo implements Runnable
{
	public static class Escrutinio
	{
		public String tipoSorteo;
		public String Categoria;
		public String Premio;
		public String Acertantes;
	};

	protected Calendar fecha = GregorianCalendar.getInstance();
	protected String dia;
	protected String[] resultado;
	protected Array <Escrutinio> listaEscrutinios = new Array <Escrutinio>();
	protected int IDSorteo;

	//GET
	public Calendar getFecha() 						{ return fecha; }
	public int getDiaDelMes ()						{ return fecha.get(Calendar.DAY_OF_MONTH); }
	public int getMes ()							{ return (fecha.get(Calendar.MONTH)+1); } //se suma 1 al mes ya que los meses en calendar empiezan por 0
	public int getAño ()							{ return fecha.get(Calendar.YEAR); }
	public String getDia() 							{ return dia; }
	public String[] getResultado ()					{ return resultado; }
	public Array<Escrutinio> getListaEscrutinio ()	{ return listaEscrutinios; }
	public int getIDSorteo ()						{ return IDSorteo; }

	@Override
	public void run() 
	{
		setListaEscrutinio(true);
	}

	public TiposSorteo getTipoSorteo()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo()==IDSorteo) {return listaTiposSorteo.get(i);} }
		return null;
	}

	public String getFechaFormateada ()
	{	//Hay que sumar 1 al mes, puesto que los meses de calendar empiezan en 0
		String fechaFormateada;
		int Mes = fecha.get(Calendar.MONTH) +1;
		fechaFormateada = fecha.get(Calendar.YEAR)+"-"+Mes+"-"+fecha.get(Calendar.DAY_OF_MONTH);
		return fechaFormateada;
	}

	public String getNombreSorteo ()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo()==IDSorteo) {return listaTiposSorteo.get(i).getNombre();} }
		return "ID de Sorteo no encontrado";
	}
	public String getNombreSorteoBDD ()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo()==IDSorteo) {return listaTiposSorteo.get(i).getNombreBDD();} }
		return "ID de Sorteo no encontrado";
	}
	public String getNombreFechaBDD ()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo()==IDSorteo) {return listaTiposSorteo.get(i).getNombreFechaBDD();} }
		return "ID de Sorteo no encontrado";
	}
	public String getNombreSorteoAPP ()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo()==IDSorteo) {return listaTiposSorteo.get(i).getNombreAPP();} }
		return "ID de Sorteo no encontrado";
	}
	public String getNombreCamposBDD (int numColumna)
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo() == IDSorteo) {return listaTiposSorteo.get(i).getNombreCamposBDD()[numColumna];} }
		return "Campo no existe";
	}
	public String getNombreCamposApp (int numColumna)
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo() == IDSorteo) {return listaTiposSorteo.get(i).getNombreCamposApp()[numColumna];} }
		return "Campo no existe";
	}
	public int getTipoDatos (int numColumna)
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo() == IDSorteo) {return listaTiposSorteo.get(i).getTipoDatos()[numColumna];} }
		return -1;
	}
	public int getNumeroCampos ()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo() == IDSorteo) {return listaTiposSorteo.get(i).getNombreCamposBDD().length;} }
		return -1;
	}
	public int getPosicionExtra ()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo() == IDSorteo) {return listaTiposSorteo.get(i).getPosicionExtra();} }
		return -1;
	}

	public boolean getTieneBote ()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo() == IDSorteo) {return listaTiposSorteo.get(i).getTieneBote();} }
		return false;
	}

	public boolean getBoteActivo ()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo() == IDSorteo) {return listaTiposSorteo.get(i).getBoteEnActivo();} }
		return false;
	}

	public String getResultadoBotes (int pos)
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo() == IDSorteo) {return listaTiposSorteo.get(i).getResultadoBotes()[pos];} }
		return null;
	}

	public int getDiaDelMesBote ()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo() == IDSorteo) {return listaTiposSorteo.get(i).getDiaDelMesBote();} }
		return -1;
	}

	public int getMesBote ()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo() == IDSorteo) {return listaTiposSorteo.get(i).getMesBote();} }
		return -1;
	}

	public int getAñoBote ()
	{
		for (int i=0; i<listaTiposSorteo.size;i++) 
		{ if (listaTiposSorteo.get(i).getIDSorteo() == IDSorteo) {return listaTiposSorteo.get(i).getAñoBote();} }
		return -1;

	}

	//SET
	public void setFecha(Calendar fecha)
	{
		this.fecha = fecha;
		setDia();
	}
	public void setFecha (int dia, int mes, int año)
	{	//en el Calendar el mes empieza por el mes 0, por eso hay que restar 1 al mes actual de la base de datos, puesto que ahi el mes empieza en 1
		fecha.set(año, mes-1, dia);
		setDia();
	}
	public void setFecha (String fecha)
	{	//en el Calendar el mes empieza por el mes 0, por eso hay que restar 1 al mes actual de la base de datos, puesto que ahi el mes empieza en 1
		int primerGuion = fecha.indexOf('-',1);
		int segundoGuion = fecha.indexOf('-',primerGuion+1);

		int año, mes, dia;
		año = Integer.parseInt(fecha.substring(0, primerGuion));
		mes = Integer.parseInt(fecha.substring(primerGuion+1, segundoGuion));
		dia = Integer.parseInt(fecha.substring(segundoGuion+1, fecha.length()));

		this.fecha.clear();
		this.fecha.set(año, mes-1, dia);
		setDia();
	}

	public void setDia()
	{
	    int numDia = fecha.get(Calendar.DAY_OF_WEEK);

	    switch(numDia)
	    {
		    case 1: dia = "Domingo"; break;
		    case 2: dia = "Lunes"; break;
		    case 3: dia = "Martes"; break;
		    case 4: dia = "Miércoles"; break;
		    case 5: dia = "Jueves"; break;
		    case 6: dia = "Viernes"; break;
		    case 7: dia = "Sábado"; break;
		    default: dia = "";
	    }
	}
	public void setNumeroCampos (int numCampos)
	{
		resultado = new String [numCampos];
	}

	public void setListaEscrutinio(boolean mostrarEnPantalla)
	{

		PantallaResultados.isLoadingEscrutinio = true;

		Group cargandoGroup = new Group();
		GroupText cargando = new utils.GroupText("Cargando Escrutinio", PantallaResultados.font45, Color.ORANGE, Color.DARK_GRAY, Color.BLACK, 0, 0, Align.center, Align.center, 3, true, cargandoGroup);

		if (mostrarEnPantalla)
		{
			cargandoGroup.setPosition(PantallaResultados.anchoPantalla/2, PantallaResultados.altoPantalla/2);
			((PantallaResultados)Loto.screen).stage.addActor(cargandoGroup);
			cargandoGroup.toFront();
			cargandoGroup.setTouchable(Touchable.disabled);
		}

		String response = null;

		// Llamar al método executeHttpPost pasandole los parametros necesarios
		try 
		{
			final ExecutorService service;
			Future<String> result;

			service = Executors.newFixedThreadPool(1);
			String consultaSQL = "SELECT Categoria, Premio, Acertantes, TipoSorteo " +
					" FROM Escrutinio" +
					" WHERE TipoSorteo LIKE '%"+getNombreSorteo()+"%'" +
					" AND Fecha='"+getFechaFormateada()+"'";
			result = service.submit(new SQLQuery(consultaSQL, Constantes.BDD_ESCRUTINIOS));

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
					Escrutinio escrutinio = new Escrutinio ();

					escrutinio.tipoSorteo = json_data.getString(Constantes.BDD_ESCRUTINIO_NOMBRE_CAMPO_TIPOSORTEO);
					escrutinio.Categoria = json_data.getString(Constantes.BDD_ESCRUTINIO_NOMBRE_CAMPO_CATEGORIA);
					escrutinio.Premio = json_data.getString(Constantes.BDD_ESCRUTINIO_NOMBRE_CAMPO_PREMIO);
					escrutinio.Acertantes = String.valueOf(json_data.getInt(Constantes.BDD_ESCRUTINIO_NOMBRE_CAMPO_ACERTANTES));

					escrutinio.Premio = escrutinio.Premio.trim(); //para quitar el espacio que tienen al final los PremiosJoker, por algun tipo de bug en la captura
					escrutinio.Acertantes = escrutinio.Acertantes.trim(); //por si acaso quitamos los espacios de los acertantes tambien

					listaEscrutinios.add(escrutinio);
				}
			} catch(JSONException e) { System.out.println("Error parseando los datos:"+e.toString());  cargando.setTexto("Error de conexión"); }
		} catch (Exception e) { System.out.println("ERROR in http connection!!" + e.getMessage()); cargando.setTexto("Error de conexíon"); }

		if (mostrarEnPantalla)
		{
			((PantallaResultados)Loto.screen).stage.getRoot().removeActor(cargandoGroup);
			PantallaResultados.menu.cambiarSubMenu(Constantes.MENU_ESCRUTINIO, this);
		}
		PantallaResultados.isLoadingEscrutinio = false;
	}
}
