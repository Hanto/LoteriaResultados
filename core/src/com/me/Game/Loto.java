package com.me.Game;

import interfaces.ActivityLauncher;
import screen.PantallaLoading;
import screen.PantallaResultados;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import datos.CentroDeDatos;
import datos.Recursos;

public class Loto extends Game
{
	public static String LOG = Loto.class.getSimpleName();
	public enum TipoPantalla { Resultados, Loading}
	public ActivityLauncher activityLauncher;
	public static Screen screen = null;
	public static CentroDeDatos centroDatos = new CentroDeDatos ();

	protected String getName()          	{ return getClass().getSimpleName(); }

	public Loto (ActivityLauncher lector)
	{	this.activityLauncher = lector; }

	@Override
	public void create() 
	{ 
		//super.create();
		Recursos.inicializarRecursos();
		this.activityLauncher.mostrarPublicidad(true);
		navegarA(TipoPantalla.Loading);
	}

	public void navegarA(TipoPantalla tipoPantalla)
	{
		switch (tipoPantalla)
		{
			case Resultados:		screen = new PantallaResultados(this); break;
			case Loading:			screen = new PantallaLoading(this); break;
			default:				screen = new PantallaResultados(this); break;
		}
		setScreen(screen);	
	}

	@Override
	public void dispose ()
	{
		Gdx.app.log( Loto.LOG, "DISPOSE (Liberando recursos): " + getName());
		super.dispose();

		Recursos.dispose();
	}

	public void abrirLector() 		{ activityLauncher.abrirLectorCodigoBarras(); }
	public void abrirNavegador () 	{ activityLauncher.abrirNavegador(); }
	public void mostrarPublicidad()	{ activityLauncher.mostrarPublicidad(true); }
	public void ocultarPublicidad() { activityLauncher.mostrarPublicidad(false); }
	

}
