package datos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Recursos 
{
	public static TextureAtlas atlas;

	private static TextureRegion barraAzulOscuro;
	private static TextureRegion barraAzulClaro;
	private static TextureRegion barraRoja;
	private static TextureRegion botonRojoOscuro;
	private static TextureRegion botonRojoClaro;
	private static TextureRegion flecha;

	private static TextureRegion logoPrimitiva;
	private static TextureRegion logoElGordo;
	private static TextureRegion logoBonoloto;
	private static TextureRegion logoEuromillones;
	private static TextureRegion logoLoteriaNacional;
	private static TextureRegion logoLototurf;
	private static TextureRegion logoQuintuplePlus;
	private static TextureRegion logoQuinigol;
	private static TextureRegion logoQuiniela;

	private static TextureRegion fondoLoadingScreen;
	private static TextureRegion fondoPantallaResultados;

	public static Image getBarraAzulOscuro()		{ return new Image(barraAzulOscuro); }
	public static Image getBarraAzulClaro()			{ return new Image (barraAzulClaro); }
	public static Image getBarraRoja()				{ return new Image(barraRoja); }
	public static Image getBotonRojoOscuro()		{ return new Image(botonRojoOscuro); }
	public static Image getBotonRojoClaro()			{ return new Image(botonRojoClaro); }
	public static Image getFlecha()					{ return new Image(flecha); }
	public static Image getFondoLoadingScreen()		{ return new Image(fondoLoadingScreen); }
	public static Image getFondoPantallaResultados(){ return new Image(fondoPantallaResultados); }

	public static Image getLogo (String nombreLogo)
	{
		if (nombreLogo.equals("primitiva")) return new Image(logoPrimitiva);
		if (nombreLogo.equals("gordo")) return new Image(logoElGordo);
		if (nombreLogo.equals("bonoloto")) return new Image(logoBonoloto);
		if (nombreLogo.equals("euromillon")) return new Image(logoEuromillones);
		if (nombreLogo.equals("nacional")) return new Image(logoLoteriaNacional);
		if (nombreLogo.equals("lototurf")) return new Image(logoLototurf);
		if (nombreLogo.equals("quintuple")) return new Image(logoQuintuplePlus);
		if (nombreLogo.equals("quinigol")) return new Image(logoQuinigol);
		if (nombreLogo.equals("quiniela")) return new Image(logoQuiniela);

		return new Image(logoPrimitiva);
	}

	public static void inicializarRecursos ()
	{
		atlas = new TextureAtlas(Gdx.files.internal("data/atlas.txt"));

		barraAzulOscuro = new TextureRegion (atlas.findRegion("barra_titulo"));
		barraAzulClaro = new TextureRegion (atlas.findRegion("barra"));
		flecha = new TextureRegion (atlas.findRegion("arrow"));
		barraRoja = new TextureRegion (atlas.findRegion("barra_asg"));
		botonRojoOscuro = new TextureRegion (atlas.findRegion("boton2"));
		botonRojoClaro = new TextureRegion (atlas.findRegion("boton1"));

		logoPrimitiva = new TextureRegion (atlas.findRegion("primitiva"));
		logoElGordo = new TextureRegion (atlas.findRegion("gordo"));
		logoBonoloto = new TextureRegion (atlas.findRegion("bonoloto"));
		logoEuromillones = new TextureRegion (atlas.findRegion("euromillon"));
		logoLoteriaNacional = new TextureRegion (atlas.findRegion("nacional"));
		logoLototurf = new TextureRegion (atlas.findRegion("lototurf"));
		logoQuintuplePlus = new TextureRegion (atlas.findRegion("quintuple"));
		logoQuinigol = new TextureRegion (atlas.findRegion("quinigol"));
		logoQuiniela = new TextureRegion (atlas.findRegion("quiniela"));

		fondoLoadingScreen = new TextureRegion (atlas.findRegion("LoadingScreen"));
		fondoPantallaResultados = new TextureRegion (atlas.findRegion("fondo"));
	}

	public static void dispose ()
	{
		if (atlas != null) atlas.dispose();
	}
}