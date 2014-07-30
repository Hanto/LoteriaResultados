package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.Game.Loto;

public class AbstractScreen implements Screen
{
    protected final Loto game;
    protected final SpriteBatch batch;
    protected final ShapeRenderer shape;
    protected final OrthographicCamera camara;
    public final Stage stage;
    
    public static int anchoPantalla;
    public static int altoPantalla;
    
    protected String getName()          { return getClass().getSimpleName(); }
    
    public AbstractScreen (Loto game)
    {
        this.game = game;
        this.batch = new SpriteBatch();
        this.shape = new ShapeRenderer();
        this.camara = new OrthographicCamera();
        this.stage = new Stage();
        
        if (Gdx.graphics.getWidth() < 480) {anchoPantalla = 480; altoPantalla = Gdx.graphics.getHeight();}
    	else {anchoPantalla = Gdx.graphics.getWidth(); altoPantalla = Gdx.graphics.getHeight();}
        
        if (altoPantalla >= 1500)
        {
        	altoPantalla = altoPantalla/2;
        	anchoPantalla = anchoPantalla/2;
        }
        
        //para que el boton de back de android no salga de la aplicacion
        Gdx.input.setCatchBackKey(true);
    }
    
    @Override
    public void show() 
    { 
    	Gdx.app.log( Loto.LOG, "SHOW (Inicializando Pantalla): " + getName()); 
    	camara.setToOrtho(false, anchoPantalla, altoPantalla);
    }
    
    @Override
    public void render(float delta) 
    {
        Gdx.gl.glClearColor(2.55f/2.55f, 2.55f/2.55f, 2.55f/2.55f, 1f);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int anchura, int altura) 
    { 
        Gdx.app.log( Loto.LOG, "RESIZE (Redimensionando pantalla): "+ getName() +" a: "+anchura+" x "+altura);
        stage.getViewport().update(anchoPantalla, altoPantalla, false);
    }

    @Override
    public void hide() 
    {   Gdx.app.log( Loto.LOG, "HIDE (Cerrando pantalla): " + getName()); 
        dispose();
    }

    @Override
    public void pause() 
    { Gdx.app.log( Loto.LOG, "PAUSE (Pausando pantalla): " + getName()); }

    @Override
    public void resume() 
    { Gdx.app.log( Loto.LOG, "RESUME (Pantalla reanudada): " + getName()); }

    @Override
    public void dispose() 
    {
        Gdx.app.log( Loto.LOG, "DISPOSE (Liberando recursos): " + getName());
        
        if (batch != null) batch.dispose();
        if (shape != null) shape.dispose();
        if (stage != null) stage.dispose();
    }   
}