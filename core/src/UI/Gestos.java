package UI;

import screen.PantallaResultados;
import utils.Text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.me.Game.Loto;

import datos.CentroDeDatos;

public class Gestos implements GestureListener
{

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) 
	{
		//System.out.println(PantallaResultados.menu.listaSubMenus.get(PantallaResultados.menu.getMenuActual()).scrollMenu.getScrollY());

		if (PantallaResultados.menu.listaSubMenus.get(PantallaResultados.menu.getMenuActual()).scrollMenu.getScrollY() <= -69) 
		{
			if (CentroDeDatos.actualizandoDatos == false)
			{	//Para que solo ejecute la actualizacion 1 vez, y no varias si el scroll es muy fuerte, si se ejecuta mas de una vez peta todo, por que no se pueden establecer conexiones simultaneas
				CentroDeDatos.actualizandoDatos = true;

				Group cargandoGroup = new Group();
				Text.printText("Descargando Nuevos Sorteos", PantallaResultados.font30, Color.ORANGE, Color.BLACK, 0, 0, Align.center, Align.center, 3, cargandoGroup);

				cargandoGroup.setPosition(PantallaResultados.anchoPantalla/2, PantallaResultados.altoPantalla/2);
				((PantallaResultados)Loto.screen).stage.addActor(cargandoGroup);
				cargandoGroup.toFront();
				cargandoGroup.setTouchable(Touchable.disabled);
				cargandoGroup.addAction(Actions.sequence(Actions.alpha(1f, 3f), Actions.alpha(0f, 2f), Actions.removeActor(cargandoGroup)));

				CentroDeDatos.generarTablasResultados = true;
				Thread thread = new Thread (Loto.centroDatos);
				thread.start();
			}
		}
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}	
}