package UI;

import screen.PantallaResultados;
import sorteos.Sorteo;
import UI.Menu.SubMenu.tipoSubMenu;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.me.Game.Loto;

import datos.CentroDeDatos;
import datos.Constantes;

public class Menu 
{
	public Array<SubMenu> listaSubMenus = new Array<SubMenu>();
	protected int subMenuActual = 0;
	protected int subMenuAnterior = 0;

	//clase que define los submenus que contiene la clase menu
	public static class SubMenu
	{
		public Table tablaMenu;
		public ScrollPane scrollMenu;
		public tipoSubMenu tipoMenu;
		public enum tipoSubMenu { SORTEOS_SEMANA, SORTEOS_TRIMESTRE, ESCRUTINIOS, BOTES };
	};

	public static class SubMenuExtra extends SubMenu
	{
		public Sorteo sorteo;
		public Group cabecera;
	};

	public int getMenuAnterior()	{ return subMenuAnterior; }
	public int getMenuActual()		{ return subMenuActual; }




	protected void esconderSubMenu (int numSubMenu)		
	{	
		SubMenu submenu = listaSubMenus.get(numSubMenu);

		submenu.scrollMenu.addAction(Actions.sequence(Actions.moveTo(-PantallaResultados.anchoPantalla, 0, 0.6f, Interpolation.sine)));
		submenu.scrollMenu.addAction(Actions.sequence(Actions.fadeOut(0.6f), Actions.removeActor()));

		if (submenu.tipoMenu == tipoSubMenu.SORTEOS_TRIMESTRE || submenu.tipoMenu == tipoSubMenu.ESCRUTINIOS) esconderCabecera (numSubMenu);
	}

	protected void mostrarSubMenu (int numSubMenu)		
	{	
		SubMenu submenu = listaSubMenus.get(numSubMenu);

		submenu.scrollMenu.clearActions();		//si no se borran las acciones pendientes, podia quedar la del removeActor de arriba pendiente, y eliminarse el actor
		((PantallaResultados)Loto.screen).stage.addActor(submenu.scrollMenu);
		submenu.scrollMenu.addAction(Actions.sequence(Actions.moveTo(0, 0, 0.6f, Interpolation.sine)));
		submenu.scrollMenu.addAction(Actions.sequence(Actions.fadeIn(0.6f)));

		if (submenu.tipoMenu == tipoSubMenu.SORTEOS_TRIMESTRE || submenu.tipoMenu == tipoSubMenu.ESCRUTINIOS) mostrarCabecera (numSubMenu);
	}

	protected void esconderCabecera (int numSubMenu)
	{
		Group cabecera = ((SubMenuExtra)listaSubMenus.get(numSubMenu)).cabecera;

		cabecera.addAction(Actions.sequence(Actions.moveTo(0, PantallaResultados.altoPantalla, 0.6f, Interpolation.sine)));
		cabecera.addAction(Actions.sequence(Actions.fadeOut(0.6f),Actions.removeActor(cabecera)));
	}

	protected void mostrarCabecera (int numSubMenu)
	{
		Group cabecera = ((SubMenuExtra)listaSubMenus.get(numSubMenu)).cabecera;

		((PantallaResultados)Loto.screen).stage.addActor(cabecera);
		cabecera.addAction(Actions.sequence(Actions.moveTo(0, PantallaResultados.altoPantalla-Constantes.ALTURA_FILAS*2-Constantes.ALTURA_FILAS_RESULTADOS+Constantes.ALTURA_FILAS_PADING+4, 0.6f, Interpolation.sine)));
	}

	public void cambiarSubMenu (int numSubMenu)
	{	
		if (listaSubMenus.get(numSubMenu).tipoMenu == tipoSubMenu.SORTEOS_TRIMESTRE || listaSubMenus.get(numSubMenu).tipoMenu == tipoSubMenu.ESCRUTINIOS)
		{ 
			actualizarMenu (numSubMenu, ((SubMenuExtra)listaSubMenus.get(numSubMenu)).sorteo); 
		}

		if (subMenuActual != numSubMenu ) esconderSubMenu (subMenuActual);
		mostrarSubMenu (numSubMenu);
		subMenuAnterior = subMenuActual;
		subMenuActual = numSubMenu;
	}

	public void cambiarSubMenu (int numSubMenu, Sorteo sorteo)
	{
		actualizarMenu (numSubMenu, sorteo);
		cambiarSubMenu (numSubMenu);
	}

	public void añadirSubMenu (Table tabla, ScrollPane scroll, tipoSubMenu tipo)
	{
		if (tipo == tipoSubMenu.SORTEOS_SEMANA || tipo == tipoSubMenu.BOTES)
		{
			SubMenu submenu = new SubMenu ();
			submenu.tablaMenu = tabla;
			submenu.scrollMenu = scroll;
			submenu.tipoMenu = tipo;

			listaSubMenus.add(submenu);
		}
		if (tipo == tipoSubMenu.SORTEOS_TRIMESTRE || tipo == tipoSubMenu.ESCRUTINIOS)
		{
			SubMenuExtra submenu = new SubMenuExtra ();
			submenu.tablaMenu = tabla;
			submenu.scrollMenu = scroll;
			submenu.tipoMenu = tipo;

			submenu.sorteo = CentroDeDatos.listaTiposSorteo.first().getListaSorteos().first();
			submenu.cabecera = GeneraTablas.generarCabeceraSorteo(submenu.sorteo, true, false);

			listaSubMenus.add(submenu);
		}
	}

	public void actualizarMenu (int numSubMenu)
	{
		SubMenu subMenu = listaSubMenus.get(numSubMenu);

		if (subMenu.tipoMenu == tipoSubMenu.SORTEOS_SEMANA)
		{	//Se Tienen que limpiar los actores y añadirlos de nuevo puesto que una asignacion directa cambiaria la direccion de memoria del actor y ya no se dibujaria
			subMenu.tablaMenu.clear();
			GeneraTablas.generarResultadosSemana(subMenu.tablaMenu);
		}
		if (subMenu.tipoMenu == tipoSubMenu.BOTES)
		{
			subMenu.tablaMenu.clear();
			GeneraTablas.generarBotes(subMenu.tablaMenu);
		}
	}

	public void actualizarMenu (int numSubMenu, Sorteo sorteo)
	{
		SubMenu subMenu = listaSubMenus.get(numSubMenu);

		if (subMenu.tipoMenu == tipoSubMenu.SORTEOS_TRIMESTRE)
		{
			((SubMenuExtra)subMenu).sorteo = sorteo;
			((SubMenuExtra)subMenu).cabecera = GeneraTablas.generarCabeceraSorteo(sorteo, true, false);
			((SubMenuExtra)subMenu).cabecera.setPosition(0, PantallaResultados.altoPantalla);

			subMenu.tablaMenu.clear();
			GeneraTablas.generarResultadosTrimestrales(sorteo, subMenu.tablaMenu);
		}
		if (subMenu.tipoMenu == tipoSubMenu.ESCRUTINIOS)
		{
			((SubMenuExtra)subMenu).sorteo = sorteo;
			((SubMenuExtra)subMenu).cabecera = GeneraTablas.generarCabeceraSorteo(sorteo, true, false);
			((SubMenuExtra)subMenu).cabecera.setPosition(0, PantallaResultados.altoPantalla);
			//((SubMenuExtra)subMenu).cabecera.setPosition(-PantallaResultados.anchoPantalla, PantallaResultados.altoPantalla-DATA.ALTURA_FILAS*2-DATA.ALTURA_FILAS_RESULTADOS+DATA.ALTURA_FILAS_PADING+2);

			subMenu.tablaMenu.clear();
			GeneraTablas.generarEscrutinio(sorteo, subMenu.tablaMenu);
		}
	}
}