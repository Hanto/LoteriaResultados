package UI;

import static screen.PantallaResultados.menu;
import screen.PantallaResultados;
import sorteos.Sorteo;
import utils.Text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.me.Game.Loto;

import datos.Constantes;

public class Listeners 
{

	public static void listenerLineaSorteo (final Sorteo sorteo, Group group)
	{
		group.addListener(new ClickListener()
		{
			 @Override
			 public void clicked (InputEvent event, float x, float y)
			 {

				 if (PantallaResultados.menu.subMenuActual != Constantes.MENU_ESCRUTINIO && sorteo.getIDSorteo() != 3)
				 {
					 if (sorteo.getListaEscrutinio().size == 0)
					 {
						 if (PantallaResultados.isLoadingEscrutinio == false)
						 {
							 Thread thread = new Thread (sorteo);
							 thread.start();
						 }
					 }
					 else menu.cambiarSubMenu(Constantes.MENU_ESCRUTINIO, sorteo);
				 }
				 else if (!(PantallaResultados.menu.subMenuActual == Constantes.MENU_RESULTADOS_SEMANALES && sorteo.getIDSorteo() == 3))
				 {
					menu.cambiarSubMenu(menu.subMenuAnterior);
				 }
			 }
		});
	}

	public static void listenerLineaSorteoNacional (final Sorteo sorteo, Group group)
	{
		group.addListener(new ClickListener()
		{
			 @Override
			 public void clicked (InputEvent event, float x, float y)
			 {
				Group cargandoGroup = new Group();
				Text.printText("Sorteo sin Escrutinios", PantallaResultados.font45, Color.ORANGE, Color.BLACK, 0, 0, Align.center, Align.center, 3, cargandoGroup);

				cargandoGroup.setPosition(PantallaResultados.anchoPantalla/2, PantallaResultados.altoPantalla/2);
				((PantallaResultados)Loto.screen).stage.addActor(cargandoGroup);
				cargandoGroup.toFront();
				cargandoGroup.setTouchable(Touchable.disabled);
				cargandoGroup.addAction(Actions.sequence(Actions.alpha(1f, 1f), Actions.alpha(0f, 1f), Actions.removeActor(cargandoGroup)));
			 }
		});
	}

	public static void listenerCabeceraSorteo (final Sorteo sorteo, Group group)
	{
		group.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) 
            {
				if (menu.subMenuActual == Constantes.MENU_RESULTADOS_SEMANALES)			{ menu.cambiarSubMenu(Constantes.MENU_RESULTADOS_TRIMESTRALES, sorteo);}
				else if (menu.subMenuActual == Constantes.MENU_RESULTADOS_TRIMESTRALES)	{ menu.cambiarSubMenu(Constantes.MENU_RESULTADOS_SEMANALES); }
				else if (menu.subMenuActual == Constantes.MENU_ESCRUTINIO)				{ menu.cambiarSubMenu(menu.subMenuAnterior); }
            }
		});
	}

	public static void listenerCabeceraLineaEscrutinio (Group group)
	{
		group.addListener(new ClickListener ()
		{
			 @Override
			 public void clicked (InputEvent event, float x, float y)
			 {
				 menu.cambiarSubMenu(menu.subMenuAnterior);
			 }
		});
	}
}
