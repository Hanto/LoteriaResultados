package utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class Text 
{
	public static Label printText (String texto, BitmapFont font, Color ColorTexto, Color ColorSombra, int posX, int posY, int centradoHorizontal, int centradoVertical, int relieve, Group group)
	{
		LabelStyle sombra = new LabelStyle();
		sombra.font = font;
		sombra.fontColor = ColorSombra;
		Label textoSombra = new Label (texto, sombra);

		LabelStyle normal = new LabelStyle(sombra);
		normal.fontColor = ColorTexto;
		Label textoNormal = new Label (texto, normal);

		switch (centradoHorizontal)
		{
			case Align.right:	{ posX = posX - (int)textoSombra.getWidth(); break; }
			case Align.center:	{ posX = posX - (int)textoSombra.getWidth()/2; break; }
			case Align.left:	{ break; }
		}

		switch (centradoVertical)
		{
			case Align.top:		{ posY = posY - (int)textoSombra.getHeight(); break; }
			case Align.center:	{ posY = posY - (int)textoSombra.getHeight()/2; break; }
			case Align.bottom:	{ break; }
		}

		textoSombra.setPosition(posX+relieve, posY-relieve);
		group.addActor(textoSombra);

		textoNormal.setPosition(posX, posY);
		group.addActor(textoNormal);

		return textoNormal;
	}

	public static Label printText (String texto, BitmapFont font, Color ColorTexto, Color ColorSombra, int posX, int posY, int centradoHorizontal, int relieve, Group group)
		{ return printText (texto, font, ColorTexto, ColorSombra, posX, posY, centradoHorizontal, Align.bottom, relieve, group); }

}