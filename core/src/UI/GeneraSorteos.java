package UI;

import static datos.Constantes.ALTURA_FILAS_RESULTADOS;
import static datos.Constantes.COLOR_DIA_BASE;
import static datos.Constantes.COLOR_DIA_SOMBRA;
import static datos.Constantes.COLOR_EXTRA_BASE;
import static datos.Constantes.COLOR_EXTRA_SOMBRA;
import static datos.Constantes.COLOR_RESULTADOS_BASE;
import static datos.Constantes.COLOR_RESULTADOS_SOMBRA;
import static datos.Constantes.POSICIONEXTRA;
import static utils.Text.printText;
import screen.PantallaResultados;
import sorteos.Sorteo;
import utils.Text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import datos.Constantes;
import datos.Recursos;
           
public class GeneraSorteos 
{
	public static interface tipoGenerador
	{
		public Group generarLineaSorteo (Sorteo sorteo, boolean mostrarDiaFecha);
	}

	public static class generadorPrimitiva implements tipoGenerador
	{
		@Override
		public Group generarLineaSorteo(Sorteo sorteo, boolean mostrarDiaFecha) 
		{
			Group group = new Group();
			group.setTransform(false);
			group.setHeight(1.5f*ALTURA_FILAS_RESULTADOS);
			generarDiaFecha (sorteo, mostrarDiaFecha, 1.5f*Constantes.ALTURA_FILAS_RESULTADOS, group);

			//Formateamos el Joker:
			String Joker = sorteo.getResultado()[8];
			if (Joker.length()<7) 
			{	
				for (int j=Joker.length(); j<7; j++) { Joker = "0"+Joker; }
				sorteo.getResultado()[8] = Joker; //ya que lo hemos formateado correctamente salvamos el nuevo formato para no tener que recomputarlo
			}

			String resultado = "";
			String resultadoExtra = "";

			//para optimizar el rendimiento de la version Android, en lugar de dibujar cada resultado como un objeto, juntamos todos los resultados en un mismo String
			//y dibujamos un unico objeto que contiene todos los resultados, esto deberemos hacerlo para los resultados y para los campos extra (reintegro, estrellas, complementario...)
			//puesto que tienen caracteristicas (tamaño, color) diferentes. Cada String lo formatearemos de la siguiente manera:
			//los valores separados por un espacio y en el que contengan un digito o dos siempre ocupen el espacio de dos, para que la alineacion de resultados sea perfecta.

			for (int i=0; i<sorteo.getResultado().length; i++)
			{	//el vector PosicionExtra contiene para cada sorteo a partir de que resultado estan los valores considerados "Extra" 
				if (i<POSICIONEXTRA[0])
				{
					if (sorteo.getResultado()[i].length()<2) sorteo.getResultado()[i] = "  "+sorteo.getResultado()[i];
					resultado = resultado + "  "+ sorteo.getResultado()[i];
				}
				else  if (i<8)
				{
					if (sorteo.getResultado()[i].length()<2) sorteo.getResultado()[i] = "  "+sorteo.getResultado()[i];
					resultadoExtra = resultadoExtra + "   "+ sorteo.getResultado()[i];
				}
			}

			//Primera Linea:
			printText(resultado, PantallaResultados.font30, COLOR_RESULTADOS_BASE, COLOR_RESULTADOS_SOMBRA, 60, (int)(ALTURA_FILAS_RESULTADOS*0.5)+5, Align.left, Align.bottom, 2, group);
			printText(resultadoExtra, PantallaResultados.font30, COLOR_EXTRA_BASE, COLOR_RESULTADOS_SOMBRA, 345, 4+(int)(ALTURA_FILAS_RESULTADOS*0.5), Align.left, Align.bottom, 1, group);
			printText(sorteo.getNombreCamposApp(6)+":          "+sorteo.getNombreCamposApp(7)+":", PantallaResultados.font20, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 340, 6+(int)(ALTURA_FILAS_RESULTADOS*0.5), Align.left, Align.bottom, 1, group);

			//Segunda Linea:
			printText("joker:", PantallaResultados.font20, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 158, 10, Align.left, Align.bottom, 1, group);
			printText(sorteo.getResultado()[8], PantallaResultados.font30, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 218, 8, Align.left, Align.bottom, 1, group);

			//Añadimos el listener de click para este resultado:
			Listeners.listenerLineaSorteo (sorteo, group);

			return group;
		}
	}

	public static class generadorBonolotoLototurf implements tipoGenerador
	{
		@Override
		public Group generarLineaSorteo(Sorteo sorteo, boolean mostrarDiaFecha) 
		{
			Group group = new Group();
			group.setTransform(false);
			group.setHeight(ALTURA_FILAS_RESULTADOS);

			//Dia, Fecha o Fecha, dia en el caso del resumen trimestral
			generarDiaFecha (sorteo, mostrarDiaFecha, Constantes.ALTURA_FILAS_RESULTADOS, group);

			//Resultados del Sorteo
			String resultado = "";
			String resultadoExtra = "";

			for (int i=0; i<sorteo.getResultado().length; i++)
			{ 
				if (i<POSICIONEXTRA[4])
				{
					if (sorteo.getResultado()[i].length()<2) sorteo.getResultado()[i] = "  "+sorteo.getResultado()[i];
					resultado = resultado + "  "+ sorteo.getResultado()[i];
				}
				else 
				{
					if (sorteo.getResultado()[i].length()<2) sorteo.getResultado()[i] = "  "+sorteo.getResultado()[i];
					resultadoExtra = resultadoExtra + "   "+ sorteo.getResultado()[i];
				}
			}
			printText(resultado, PantallaResultados.font30, COLOR_RESULTADOS_BASE, COLOR_RESULTADOS_SOMBRA, 60, 7, Align.left, Align.bottom, 2, group);
			printText(resultadoExtra, PantallaResultados.font30, COLOR_EXTRA_BASE, COLOR_RESULTADOS_SOMBRA, 345, 6, Align.left, Align.bottom, 1, group);
			printText(sorteo.getNombreCamposApp(6)+":          "+sorteo.getNombreCamposApp(7)+":", PantallaResultados.font20, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 340, 8, Align.left, Align.bottom, 1, group);

			//Añadimos el listener de click para este resultado:
			Listeners.listenerLineaSorteo (sorteo, group);

			return group;
		}
	}

	public static class generadorQuiniela implements tipoGenerador
	{
		@Override
		public Group generarLineaSorteo(Sorteo sorteo, boolean mostrarDiaFecha) 
		{
			Group group = new Group();
			group.setTransform(false);
			group.setHeight(ALTURA_FILAS_RESULTADOS);

			//Fecha del Sorteo
			generarDiaFecha (sorteo, mostrarDiaFecha, Constantes.ALTURA_FILAS_RESULTADOS, group);

			//Resultados del Sorteo
			String resultado = "";

			for (int i=0; i<sorteo.getResultado().length; i++)	{ resultado = resultado + "  "+ sorteo.getResultado()[i]; }
			printText(resultado, PantallaResultados.font30, COLOR_RESULTADOS_BASE, COLOR_RESULTADOS_SOMBRA, 25, 7, Align.left, Align.bottom, 2, group);

			Listeners.listenerLineaSorteo (sorteo, group);

			return group;
		}	
	}

	public static class generadorLoteriaNacional implements tipoGenerador
	{
		@Override
		public Group generarLineaSorteo(Sorteo sorteo, boolean mostrarDiaFecha) 
		{
			Group group = new Group();
			group.setTransform(false);
			group.setHeight(1.5f*ALTURA_FILAS_RESULTADOS);

			//Fecha del Sorteo
			generarDiaFecha (sorteo, mostrarDiaFecha, 1.5f*Constantes.ALTURA_FILAS_RESULTADOS, group);

			//Primera Linea
			printText(sorteo.getResultado()[0], PantallaResultados.font30, COLOR_RESULTADOS_BASE, COLOR_RESULTADOS_SOMBRA, 180, (int)(ALTURA_FILAS_RESULTADOS*0.5)+5, Align.right, 2, group);
			printText("Reintegro:", PantallaResultados.font20, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 200, 6+(int)(ALTURA_FILAS_RESULTADOS*0.5), Align.left, 1, group);
			printText(sorteo.getResultado()[3], PantallaResultados.font30, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 200+90, 4+(int)(ALTURA_FILAS_RESULTADOS*0.5), Align.left, 1, group);

			//SegundaLinea
			printText(sorteo.getResultado()[1], PantallaResultados.font30, COLOR_RESULTADOS_BASE, COLOR_RESULTADOS_SOMBRA, 105, 7, Align.left, Align.bottom, 2, group);

			String fraccionSerie = sorteo.getResultado()[2];
			if (fraccionSerie.length()>6)
			{
				if (fraccionSerie.equals("Navidad")) { printText("Navidad", PantallaResultados.font20, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 200, 10, Align.left, Align.bottom, 1, group); }
				else 
				{
					String fraccion = fraccionSerie.substring(2, fraccionSerie.indexOf(" "));
					String serie = fraccionSerie.substring(fraccionSerie.indexOf(":",2 )+1, fraccionSerie.length());

					printText("Fracción: ", PantallaResultados.font20, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 200, 10, Align.left, Align.bottom, 1, group);
					printText(fraccion, PantallaResultados.font30, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 305, 8, Align.right, Align.bottom, 1, group);

					printText("Serie: ", PantallaResultados.font20, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 315, 10, Align.left, Align.bottom, 1, group);
					printText(serie, PantallaResultados.font30, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 381, 8, Align.right, Align.bottom, 1, group);
				}
			}
			Listeners.listenerLineaSorteoNacional (sorteo, group);

			return group;
		}
	}

	public static class generadorEuromillones implements tipoGenerador
	{
		@Override
		public Group generarLineaSorteo(Sorteo sorteo, boolean mostrarDiaFecha) 
		{
			Group group = new Group();
			group.setTransform(false);
			group.setHeight(ALTURA_FILAS_RESULTADOS);

			generarDiaFecha (sorteo, mostrarDiaFecha, Constantes.ALTURA_FILAS_RESULTADOS, group);

			//Resultados del Sorteo
			String resultado = "";
			String resultadoExtra = "";

			for (int i=0; i<sorteo.getResultado().length; i++)
			{ 
				if (i<POSICIONEXTRA[5])
				{
					if (sorteo.getResultado()[i].length()<2) sorteo.getResultado()[i] = "  "+sorteo.getResultado()[i];
					resultado = resultado + "  "+ sorteo.getResultado()[i];
				}
				else 
				{
					if (sorteo.getResultado()[i].length()<2) sorteo.getResultado()[i] = "  "+sorteo.getResultado()[i];
					resultadoExtra = resultadoExtra + sorteo.getResultado()[i] + "       ";
				}
			}
			printText(resultado, PantallaResultados.font30, COLOR_RESULTADOS_BASE, COLOR_RESULTADOS_SOMBRA, 60, 7, Align.left, Align.bottom, 2, group);
			printText(resultadoExtra, PantallaResultados.font30, COLOR_EXTRA_BASE, COLOR_RESULTADOS_SOMBRA, 325, 6, Align.left, Align.bottom, 1, group);
			printText(sorteo.getNombreCamposApp(5)+":          "+sorteo.getNombreCamposApp(6)+":", PantallaResultados.font20, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 295, 8, Align.left, Align.bottom, 1, group);

			Listeners.listenerLineaSorteo (sorteo, group);

			return group;
		}
	}

	public static class generadorGordo implements tipoGenerador
	{
		@Override
		public Group generarLineaSorteo(Sorteo sorteo, boolean mostrarDiaFecha) 
		{
			Group group = new Group();
			group.setTransform(false);
			group.setHeight(ALTURA_FILAS_RESULTADOS);

			generarDiaFecha (sorteo, mostrarDiaFecha, Constantes.ALTURA_FILAS_RESULTADOS, group);

			//Resultados del Sorteo
			String resultado = "";

			for (int i=0; i<sorteo.getResultado().length; i++)
			{ 
				if (i<POSICIONEXTRA[6])
				{
					if (sorteo.getResultado()[i].length()<2) sorteo.getResultado()[i] = "  "+sorteo.getResultado()[i];
					resultado = resultado + "  "+ sorteo.getResultado()[i];
				}
			}
			printText(resultado, PantallaResultados.font30, COLOR_RESULTADOS_BASE, COLOR_RESULTADOS_SOMBRA, 60, 7, Align.left, Align.bottom, 2, group);
			printText(sorteo.getResultado()[5], PantallaResultados.font30, COLOR_EXTRA_BASE, COLOR_RESULTADOS_SOMBRA, 365, 6, Align.left, Align.bottom, 1, group);
			printText(sorteo.getNombreCamposApp(5)+":", PantallaResultados.font20, COLOR_EXTRA_BASE, COLOR_EXTRA_SOMBRA, 300, 8, Align.left, Align.bottom, 1, group);

			Listeners.listenerLineaSorteo (sorteo, group);

			return group;
		}
	}

	public static class generadorQuintuple implements tipoGenerador
	{
		@Override
		public Group generarLineaSorteo(Sorteo sorteo, boolean mostrarDiaFecha) 
		{
			Group group = new Group();
			group.setTransform(false);
			group.setHeight(ALTURA_FILAS_RESULTADOS);

			generarDiaFecha (sorteo, mostrarDiaFecha, Constantes.ALTURA_FILAS_RESULTADOS, group);

			//Resultados del Sorteo
			String resultado = "";

			for (int i=0; i<sorteo.getResultado().length; i++)
			{ 
				if (sorteo.getResultado()[i].length()<2) sorteo.getResultado()[i] = "  "+sorteo.getResultado()[i];
				resultado = resultado + "  "+ sorteo.getResultado()[i];
			}
			printText(resultado, PantallaResultados.font30, COLOR_RESULTADOS_BASE, COLOR_RESULTADOS_SOMBRA, 60, 7, Align.left, Align.bottom, 2, group);

			Listeners.listenerLineaSorteo (sorteo, group);

			return group;
		}
	}

	public static class generadorQuinigol implements tipoGenerador
	{
		@Override
		public Group generarLineaSorteo(Sorteo sorteo, boolean mostrarDiaFecha) 
		{
			Group group = new Group();
			group.setTransform(false);
			group.setHeight(ALTURA_FILAS_RESULTADOS);

			generarDiaFecha (sorteo, mostrarDiaFecha, Constantes.ALTURA_FILAS_RESULTADOS, group);

			//Resultados del Sorteo
			for (int i=0; i<sorteo.getResultado().length; i++)
			{ 
				printText(sorteo.getResultado()[i], PantallaResultados.font30, COLOR_RESULTADOS_BASE, COLOR_RESULTADOS_SOMBRA, 100+i*(40+32), 7, Align.right, Align.bottom, 2, group);
			}

			Listeners.listenerLineaSorteo (sorteo, group);

			return group;
		}
	}

	public static void generarDiaFecha (Sorteo sorteo, boolean mostrarDiaFecha, float alturaFila, Group group)
	{
		Image barra = Recursos.getBarraAzulClaro();
		group.addActor(barra);
		barra.setBounds(0, 0, PantallaResultados.anchoPantalla, (int)alturaFila);

		//el booleano determina el orden de la fecha, si es cierto pondremos el dia de la semana y luego la fecha, si es falso al reves
		if (mostrarDiaFecha)
		{
			Label Dia = Text.printText(sorteo.getDia(), PantallaResultados.font28, Constantes.COLOR_DIA_BASE, Constantes.COLOR_DIA_SOMBRA, 5, (int)alturaFila, Align.left, Align.top, 1, group);
			String fecha = sorteo.getDiaDelMes()+"-"+sorteo.getMes()+"-"+sorteo.getAño();
			printText(fecha, PantallaResultados.font20, Color.WHITE, Color.BLACK, (int)Dia.getWidth()+10, (int)alturaFila-6, Align.left, Align.top, 1, group);
		}
		else
		{
			String fecha = sorteo.getDiaDelMes()+"-"+sorteo.getMes()+"-"+sorteo.getAño();
			Label lFecha = printText(fecha, PantallaResultados.font24, COLOR_DIA_BASE, COLOR_DIA_SOMBRA, 5, (int)alturaFila, Align.left, Align.top, 1, group);
			printText(sorteo.getDia(), PantallaResultados.font20, Color.WHITE, Color.BLACK, (int)lFecha.getWidth()+10, (int)alturaFila-3, Align.left, Align.top, 1, group);
		}
	}
}