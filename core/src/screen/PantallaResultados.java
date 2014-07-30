package screen;

import utils.GroupText;
import utils.Text;
import UI.Gestos;
import UI.Menu;
import UI.Menu.SubMenu.tipoSubMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.me.Game.Loto;

import datos.Constantes;
import datos.Recursos;

public class PantallaResultados extends AbstractScreen
{
	public static BitmapFont fuenteCabecera;
	public static BitmapFont fuenteFecha;
	public static BitmapFont fuente14;
	public static BitmapFont fuente20;
	public static BitmapFont fuente24;
	public static BitmapFont fuente28;
	public static BitmapFont fuente30;
	public static BitmapFont fuente37;
	public static BitmapFont fuente45;
	public static Skin skin;

	public static BitmapFont font30;
	public static BitmapFont font28;
	public static BitmapFont font20;
	public static BitmapFont font24;
	public static BitmapFont font14;
	public static BitmapFont font45;

	//Variable que contiene los distintos menus (los resultados semana, trimestre, y escrutinios):
	public static Menu menu = new Menu();
	public static boolean isLoadingEscrutinio = false;
	public static boolean generarTablasResultados = false;

	public static GroupText FPS;

	protected InputMultiplexer inputMultiplexer = new InputMultiplexer();
    protected Gestos gestos = new Gestos();
    protected GestureDetector gestureDetector = new GestureDetector(gestos);

	public PantallaResultados (Loto game) 
	{ 
		super (game); 

		InputProcessor backProcessor = new InputAdapter()
		{
			@Override
			public boolean keyDown(int keycode) 
			{
				if (keycode == Keys.BACK || keycode == Keys.BACKSPACE ) 
				{
					switch (menu.getMenuActual())
					{
						case Constantes.MENU_BOTES: menu.cambiarSubMenu(menu.getMenuAnterior()); break;
						case Constantes.MENU_ESCRUTINIO: menu.cambiarSubMenu(Constantes.MENU_RESULTADOS_TRIMESTRALES); break;
						case Constantes.MENU_RESULTADOS_TRIMESTRALES: menu.cambiarSubMenu(Constantes.MENU_RESULTADOS_SEMANALES); break;
						case Constantes.MENU_RESULTADOS_SEMANALES: Gdx.app.exit();
					}
				}
				return false;
			}
		};

		inputMultiplexer.addProcessor(backProcessor);
		inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(stage);

		font30 = new BitmapFont (Gdx.files.internal("fonts/Resultados.fnt"), false);
		font28 = new BitmapFont (Gdx.files.internal("fonts/28.fnt"), false);
		font20 = new BitmapFont (Gdx.files.internal("fonts/20.fnt"), false);
		font24 = new BitmapFont (Gdx.files.internal("fonts/24.fnt"), false);
		font14 = new BitmapFont (Gdx.files.internal("fonts/14.fnt"), false);
		font45 = new BitmapFont (Gdx.files.internal("fonts/45.fnt"), false);

		//skin = new Skin (Gdx.files.internal("ui/defaultskin.json"));

        //Generar fuentes con el tipo de fuente RAWENGULKSANS:
  		FileHandle fontFile = Gdx.files.internal("fonts/RawengulkSans.ttf");
  		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator (fontFile);
  		fuenteCabecera=fontGen.generateFont(35);
  		fuenteFecha=fontGen.generateFont(14);
  		
  		//Generar fuentes con el tipo de fuente CALIBRI:
  		fontFile = Gdx.files.internal("fonts/calibrib.ttf");
  		fontGen = new FreeTypeFontGenerator (fontFile);
  		fuente14=fontGen.generateFont(14);
  		fuente20=fontGen.generateFont(20, "qwertyuiopasdfghjklñzxcvbnmQWERTYUIOPASDFGHJKLÑZXCVBNMáéíóú,.-1234567890€ºª()/:", false);
  		fuente24=fontGen.generateFont(24, "qwertyuiopasdfghjklñzxcvbnmQWERTYUIOPASDFGHJKLÑZXCVBNMáéíóú,.-1234567890€ºª()/:", false);
  		fuente28=fontGen.generateFont(28);
  		fuente30=fontGen.generateFont(30, "qwertyuiopasdfghjklñzxcvbnmQWERTYUIOPASDFGHJKLÑZXCVBNMáéíóú,.-1234567890€ºª()/:", false);
  		fuente37=fontGen.generateFont(37);
  		fuente45=fontGen.generateFont(45, "qwertyuiopasdfghjklñzxcvbnmQWERTYUIOPASDFGHJKLÑZXCVBNMáéíóú,.-1234567890€ºª()/:", false);
  		//Una vez generadas todas las fuentes necesarias, liberar el recurso;
  		fontGen.dispose();
	}

	
	@Override
	public void show()
	{
		super.show();

		Gdx.input.setInputProcessor(inputMultiplexer);

		Image fondo = Recursos.getFondoPantallaResultados();
		fondo.setBounds(0, 0, anchoPantalla, altoPantalla);
		stage.addActor(fondo);

  		final Table tablaResultadosSemana = new Table().top().left();
		final ScrollPane scrollResultadosSemana = new ScrollPane(tablaResultadosSemana);
		scrollResultadosSemana.setBounds(-anchoPantalla, 0, anchoPantalla, altoPantalla-Constantes.ALTURA_FILAS*2+5);
		scrollResultadosSemana.setOverscroll(false, true);
		scrollResultadosSemana.setupOverscroll(70, 100, 200);

		Table tablaResultadosTrimestre = new Table().top().left();
		ScrollPane scrollResultadosTrimestre = new ScrollPane(tablaResultadosTrimestre);
		scrollResultadosTrimestre.setBounds(-anchoPantalla, 0, anchoPantalla, altoPantalla-Constantes.ALTURA_FILAS*2+5);
		scrollResultadosTrimestre.setOverscroll(false, true);
		scrollResultadosTrimestre.setupOverscroll(70, 100, 200);

		Table tablaEscrutinios = new Table().top().left();
		ScrollPane scrollEscrutinios = new ScrollPane(tablaEscrutinios);
		scrollEscrutinios.setBounds(-anchoPantalla, 0, anchoPantalla, altoPantalla-Constantes.ALTURA_FILAS*2+5);
		scrollEscrutinios.setOverscroll(false, true);
		scrollEscrutinios.setupOverscroll(70, 100, 200);

		Table tablaBotes = new Table().top().left();
		ScrollPane scrollBotes = new ScrollPane(tablaBotes);
		scrollBotes.setBounds(-anchoPantalla, 0, anchoPantalla, altoPantalla-Constantes.ALTURA_FILAS*2+5);
		scrollBotes.setOverscroll(false, true);
		scrollBotes.setupOverscroll(70, 100, 200);


		Group gCabecera = new Group();

		Image welcomeBarra = Recursos.getBarraRoja();
		gCabecera.addActor(welcomeBarra);
		welcomeBarra.setBounds(0, 2, anchoPantalla, Constantes.ALTURA_FILAS-2);
		Text.printText("Loterías Reunidas ASG", font45, Color.ORANGE, Color.BLACK, anchoPantalla/2, Constantes.ALTURA_FILAS/2, Align.center, Align.center, 3, gCabecera);

    	Image barraOpciones = Recursos.getBotonRojoOscuro();
    	gCabecera.addActor(barraOpciones);
    	barraOpciones.setBounds(0, -Constantes.ALTURA_FILAS+4, anchoPantalla, Constantes.ALTURA_FILAS);
    	gCabecera.setPosition(0, altoPantalla-Constantes.ALTURA_FILAS);
    	stage.addActor(gCabecera);
    	
    	
    	final Image glow = Recursos.getBotonRojoClaro();
    	glow.setBounds(anchoPantalla/3*2, altoPantalla-Constantes.ALTURA_FILAS*2+4, anchoPantalla/3, Constantes.ALTURA_FILAS);
    	stage.addActor(glow);
    	glow.setOrigin(glow.getWidth()/2, glow.getHeight()/2);
    	glow.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(1.10f, 1f, 1f),Actions.scaleTo(1.0f, 1f, 1f))));
    	//glow.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.5f, 1f),Actions.alpha(1f, 1f))));
    	
    	//El texto se centra de forma relativa respecto a la coordenada 0,0. Eso si hay que hacer las divisiones de los botones y luego dividir entre dos, puesto que centramos el boton en el medio
    	//Por eso si tenemos 3 botones, el resultado es anchoPantalla/6
    	
    	Group gBotonScanner = new Group();
    	Text.printText(" Escanear ", font24, Color.LIGHT_GRAY, Color.BLACK, anchoPantalla/6, Constantes.ALTURA_FILAS/2, Align.center, Align.bottom, 2, gBotonScanner);
    	Text.printText("  Décimo  ", font24, Color.LIGHT_GRAY, Color.BLACK, anchoPantalla/6, Constantes.ALTURA_FILAS/2-20, Align.center, Align.bottom, 2, gBotonScanner);
    	gBotonScanner.setPosition(0, altoPantalla-Constantes.ALTURA_FILAS*2);
    	stage.addActor(gBotonScanner);
    	
    	Group gBotonBotes = new Group();
    	Text.printText("   Botes   ", font24, Color.WHITE, Color.BLACK, anchoPantalla/6, Constantes.ALTURA_FILAS/2, Align.center, Align.bottom, 2, gBotonBotes);
    	Text.printText(" en Juego ", font24, Color.WHITE, Color.BLACK, anchoPantalla/6, Constantes.ALTURA_FILAS/2-20, Align.center, Align.bottom, 2, gBotonBotes);
    	gBotonBotes.setPosition(anchoPantalla/3*1,altoPantalla-Constantes.ALTURA_FILAS*2);
    	stage.addActor(gBotonBotes);
    	    
    	Group gBotonResultados = new Group();
    	Text.printText("  Últimos  ", font24, Color.WHITE, Color.BLACK, anchoPantalla/6, Constantes.ALTURA_FILAS/2, Align.center, Align.bottom, 2, gBotonResultados);
    	Text.printText("Resultados", font24, Color.WHITE, Color.BLACK, anchoPantalla/6, Constantes.ALTURA_FILAS/2-20, Align.center, Align.bottom, 2, gBotonResultados);
    	gBotonResultados.setPosition(anchoPantalla/3*2, altoPantalla-Constantes.ALTURA_FILAS*2);
    	stage.addActor(gBotonResultados);
   
    	//Group gBotonComprar = new Group();
    	//Text.printText(" Comprobar ", font24, Color.LIGHT_GRAY, Color.BLACK, anchoPantalla/8, Constantes.ALTURA_FILAS/2, Align.center, Align.bottom, 2, gBotonComprar);
    	//Text.printText("  Décimo   ", font24, Color.LIGHT_GRAY, Color.BLACK, anchoPantalla/8, Constantes.ALTURA_FILAS/2-20, Align.center, Align.bottom, 2, gBotonComprar);
    	//gBotonComprar.setPosition(anchoPantalla/4*3, altoPantalla-Constantes.ALTURA_FILAS*2);
    	//stage.addActor(gBotonComprar);
    	
    	/*gCabecera.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
            {
				if (CentroDeDatos.actualizandoDatos == false)
				{
					CentroDeDatos.generarTablasResultados = true;
					Thread thread = new Thread (Loto.centroDatos);
					thread.start();
				}
				return true;
            }
		});*/
    	
    	gBotonScanner.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
            {
				game.abrirLector();
				return true;
            }
		});	
    	
    	gBotonBotes.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
            {
				glow.setPosition(anchoPantalla/3*1, glow.getY());
				menu.cambiarSubMenu(3);
				return true;
            }
		});
    	
    	gBotonResultados.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
            {
				glow.setPosition(anchoPantalla/3*2, glow.getY());
				menu.cambiarSubMenu(0);
				return true;
            }
		});
    	
    	/*gBotonComprar.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
            {
				game.abrirNavegador();
				return true;
            }
		});*/
    	
    	Group version = new Group ();
    	FPS = new utils.GroupText("0", font20, Color.WHITE, Color.DARK_GRAY, Color.BLACK, 3, altoPantalla+3, Align.left, Align.top, 2, false, version);
    	//Text.printText("Versión no comercial v0.52 alpha", font20, Color.WHITE, Color.BLACK, anchoPantalla-3, altoPantalla+3, Align.right, Align.top, 2, version);
    	stage.addActor(version);
    	
    	menu.añadirSubMenu(tablaResultadosSemana, scrollResultadosSemana, tipoSubMenu.SORTEOS_SEMANA);
		menu.añadirSubMenu(tablaResultadosTrimestre, scrollResultadosTrimestre, tipoSubMenu.SORTEOS_TRIMESTRE);
		menu.añadirSubMenu(tablaEscrutinios, scrollEscrutinios, tipoSubMenu.ESCRUTINIOS);
		menu.añadirSubMenu(tablaBotes, scrollBotes, tipoSubMenu.BOTES);

		menu.actualizarMenu(Constantes.MENU_RESULTADOS_SEMANALES);
		menu.actualizarMenu(Constantes.MENU_BOTES);

		//entra en scroll para hacerlo mas espectacular:
		menu.cambiarSubMenu(Constantes.MENU_RESULTADOS_SEMANALES);
		//scrollResultadosSemana.addAction(Actions.sequence(Actions.moveTo(0f, 0, 2f, Interpolation.swing)));
	}


	@Override
	public void render(float delta)
	{
		if (generarTablasResultados == true) { generarTablasResultados = false; menu.actualizarMenu(Constantes.MENU_RESULTADOS_SEMANALES); menu.actualizarMenu(Constantes.MENU_BOTES); }

		camara.update();
		batch.setProjectionMatrix(camara.combined);

		super.render(delta);

		FPS.setTexto(Integer.toString(Gdx.graphics.getFramesPerSecond())+"fps");

		batch.begin();
		batch.end();
	}

	@Override
	public void dispose()
	{ 
		super.dispose(); 

		Recursos.dispose();

        if (skin != null) skin.dispose();
        
        if (fuenteCabecera != null) fuenteCabecera.dispose();
        if (fuenteFecha != null) fuenteFecha.dispose();
        if (fuente14 != null) fuente14.dispose();
        if (fuente20 != null) fuente20.dispose();
        if (fuente24 != null) fuente24.dispose();
        if (fuente28 != null) fuente28.dispose();
        if (fuente30 != null) fuente30.dispose();
		if (fuente37 != null) fuente37.dispose();
		if (fuente45 != null) fuente45.dispose();

		font30.dispose();
		font28.dispose();
		font20.dispose();
		font24.dispose();
		font14.dispose();
		font45.dispose();
	}

}