package screen;

import utils.GroupText;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.me.Game.Loto;
import com.me.Game.Loto.TipoPantalla;

import datos.CentroDeDatos;
import datos.Recursos;

public class PantallaLoading extends AbstractScreen
{

	public static BitmapFont fuenteCabecera;
	public static BitmapFont fuenteFecha;

	public GroupText gtPorcentaje;

	private int datosActualizados = 0;

	public PantallaLoading(Loto game) 
	{
		super(game);

		FileHandle fontFile = Gdx.files.internal("fonts/RawengulkSans.ttf");
  		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator (fontFile);
  		fuenteCabecera=fontGen.generateFont(35);
  		fuenteFecha=fontGen.generateFont(12);
  		fontGen.dispose();
	}

	@Override
	public void show()
	{
		super.show();
		camara.update();

		try 
		{ 
			Thread thread = new Thread (Loto.centroDatos);
			thread.start();
		}
		catch (Exception e) { System.out.println( "ERROR: al actualizar datos" ); }

		Group group = new Group();

		Image fondoLoadingScreen = Recursos.getFondoLoadingScreen();
		stage.addActor(fondoLoadingScreen);
		fondoLoadingScreen.setBounds(0, 0, anchoPantalla, altoPantalla);

		//new GroupText("Cargando Datos", fuenteCabecera, Color.WHITE, Color.WHITE, Color.BLACK, 0, 0, Align.center, Align.bottom, 4, false, group);
		gtPorcentaje = new utils.GroupText("Cargando Datos: 00.0 %", fuenteCabecera, Color.WHITE, Color.WHITE, Color.BLACK, 0, 0, Align.center, Align.center, 2, false, group);

		stage.addActor(group);
		group.setPosition(anchoPantalla/2, altoPantalla-altoPantalla/16*2.56f);
		group.toFront();
	}

	@Override
	public void render(float delta)
	{
		camara.update();
		batch.setProjectionMatrix(camara.combined);

		super.render(delta);

		for (int i=0; i<CentroDeDatos.listaTiposSorteo.size;i++)
		{ 
			if (CentroDeDatos.listaTiposSorteo.get(i).isDatosActualizados()) datosActualizados++; 
		}

		double porcentaje = Math.round(((double)datosActualizados/(double)CentroDeDatos.listaTiposSorteo.size)*100);
		gtPorcentaje.setTexto("Cargando Datos: "+Double.toString(porcentaje)+" %");

		batch.begin();
		batch.end();

		if (datosActualizados == CentroDeDatos.listaTiposSorteo.size) game.navegarA(TipoPantalla.Resultados);
		datosActualizados = 0;	
    }

	@Override
	public void dispose()
	{ 
		super.dispose(); 

        if (fuenteCabecera != null) fuenteCabecera.dispose();
        if (fuenteFecha != null) fuenteFecha.dispose();
	}	
}