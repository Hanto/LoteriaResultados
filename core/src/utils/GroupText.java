package utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class GroupText
{
    private Color colorNormal;
    private Color colorSombra;
    private Color colorReborde;
    private LabelStyle estiloNormal;
    private LabelStyle estiloSombra;
    private LabelStyle estiloReborde;
    private Label textoNormal;
    private Label textoSombra;
    
    //private int centradoHorizontal;
    //private int centradoVertical;
    private int relieveSombra;
    
    private int X;
    private int Y;
   
    public float getWidth()			{ return textoNormal.getWidth(); }
    
    //CONSTRUCTOR Metodo para crear texto con un formato determinado y encapsularlo todo en un grupo:
    public GroupText (String texto, BitmapFont fuente, Color colorNormal, Color colorReborde, Color colorSombra, int posX, int posY, int centradoHorizontal, int centradoVertical, int relieve, boolean reborde, Group grupo)
    {   
        this.colorNormal = colorNormal;
        this.colorSombra = colorSombra;
        this.colorReborde = colorReborde;
        this.X = posX;
        this.Y = posY;
        //this.centradoHorizontal = centradoHorizontal;
        //this.centradoVertical = centradoVertical;
        this.relieveSombra = relieve;
        
        //Creamos el estilo para los dos tipos de texto, el de la sombra y el del texto normal:
        estiloNormal = new LabelStyle (fuente, this.colorNormal);
        estiloSombra = new LabelStyle (fuente, this.colorSombra);
        estiloReborde = new LabelStyle (fuente, this.colorReborde);
        //Creamos el texto segun segun los dos estilo (el normal y el sombra):
        textoNormal = new Label (texto, estiloNormal);
        textoSombra = new Label (texto, estiloSombra);
        //Segun el tipo de centradoHorizontal ajustamos el eje de coordenadas X:
        switch (centradoHorizontal)
        {
            case Align.right:   { posX = posX - (int)textoNormal.getWidth(); break; }
            case Align.center:  { posX = posX - (int)textoNormal.getWidth()/2; break; }
            case Align.left:    { break; }
            default:            { break; }
        }
        //Segun el tipo de centradoVertical ajustamos el eje de coordenadas Y:
        switch (centradoVertical)
        {
            case Align.top:     { posY = posY -(int)textoNormal.getHeight(); break; }
            case Align.center:  { posY = posY -(int)textoNormal.getHeight()/2; break; }
            case Align.bottom:  { break; }
            default:            { break; }
        }
        //Situamos el texto normal y el texto sombra en las coordenadas generadas segun el tipo de centrado, y añadimos ambos textos al grupo grupoTexto:
        if (relieve >0)
        {
        	textoSombra.setPosition(posX+relieveSombra, posY-relieveSombra);
        	grupo.addActor(textoSombra);
        }
        if (reborde)
        {	
        	Label textoSombra1 = new Label (texto, estiloReborde);
        	Label textoSombra2 = new Label (texto, estiloReborde);
        	Label textoSombra3 = new Label (texto, estiloReborde);
        	Label textoSombra4 = new Label (texto, estiloReborde);
        	
        	textoSombra1.setPosition(posX-1, posY);
        	textoSombra2.setPosition(posX+1, posY);
        	textoSombra3.setPosition(posX, posY-1);
        	textoSombra4.setPosition(posX, posY+1);
        	grupo.addActor(textoSombra1);
        	grupo.addActor(textoSombra2);
        	grupo.addActor(textoSombra3);
        	grupo.addActor(textoSombra4);
        }
        textoNormal.setPosition(posX, posY);
        grupo.addActor(textoNormal);
    }
    

    public void setFuente ( BitmapFont fuente )
    {   //no se puede hacer un estiloNormal = new LabelStyle () con los nuevos parametros, hay que acceder a los campos y modificarlos. Si se hace un new, se crean nuevas referencias y los actores
        //no se actualizan.
        estiloNormal.font = fuente;
        estiloSombra.font = fuente;
        
        textoNormal.setStyle(estiloNormal);
        textoSombra.setStyle(estiloSombra);
    }
    
    public void setColorNormal ( Color color )
    {
        estiloNormal.fontColor = color;
        textoNormal.setStyle(estiloNormal);
    }
    
    public void setColorSombra ( Color color)
    {
        estiloSombra.fontColor = color;
        textoSombra.setStyle(estiloSombra);
    }
    
    public void setTexto ( String texto )
    {
        textoNormal.setText(texto);
        //FALTA ESO!!!:
        //Al cambiar el texto, los metodos de calculo de tamaño devuelven el tamaño antiguo, no se por que.
        textoSombra.setText(texto);
        //setCentrado ( centradoHorizontal, centradoVertical);
    }
    
    public void setCentrado ( int centradoHorizontal, int centradoVertical)
    {
        System.out.println(X);
        int posX = X;
        int posY = Y;
        int offsetSombra = 1;
        switch (centradoHorizontal)
        {
            case Align.right:   { posX = posX - (int)textoNormal.getWidth(); break; }
            case Align.center:  { posX = posX - (int)textoNormal.getWidth()/2; break; }
            case Align.left:    { break; }
            default:            { break; }
        }
        //Segun el tipo de centradoVertical ajustamos el eje de coordenadas Y:
        switch (centradoVertical)
        {
            case Align.top:     { posY = posY -(int)textoNormal.getHeight(); break; }
            case Align.center:  { posY = posY -(int)textoNormal.getHeight()/2; break; }
            case Align.bottom:  { break; }
            default:            { break; }
        }
        textoSombra.setPosition(posX+offsetSombra, posY-offsetSombra);
        textoNormal.setPosition(posX, posY);
    }
    
}