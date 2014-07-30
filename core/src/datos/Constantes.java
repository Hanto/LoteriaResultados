package datos;

import UI.GeneraSorteos;
import UI.GeneraSorteos.tipoGenerador;

import com.badlogic.gdx.graphics.Color;

public class Constantes 
{
	public static int ALTURA_FILAS = 70;
	public static int ALTURA_FILAS_RESULTADOS = 70;
	public static int ALTURA_FILAS_BOTES = 40;
	public static int ALTURA_FILAS_ESCRUTINIOS = 40;
	public static int ALTURA_FILAS_PADING = 2;

	//Tipos de datos que capturamos: (TABLA BDDTIPODATOS)
    public final static int DATE    = 0;
    public final static int INT     = 1;
    public final static int WORD    = 2;

    //Script PHP de conexion con la BDD SQL
    public final static String DIRECCION_SCRIPT_CONEXION_SQL = "http://www.lotogestion.com/android/jsonscript.php";
    
    //Direccion de la tienda online
    public final static String TIENDA_ASG = "http://m.loteriasreunidas.com";
    
    //nombre del sorteo
	public final static String NOMBRE_SORTEOS [] = 			{"Primitiva", "Lototurf", "Quiniela", "Nacional", "Bonoloto", "Euromillon", "Gordo", "Quintuple", "Quinigol"};
	//nombre que tiene cada sorteo en la base de datos 1 (la del LotoCapturador programado por Ivan)
	public final static String NOMBRE_SORTEOS_BDD [] = 		{"PJ", "LT", "q", "n", "BL", "EURO", "GORDO", "QP", "QG"};
	//nombre que tiene cada sorteo en la base de datos 2 (la que contiene los datos capturados por ASG)
	public final static String NOMBRE_FECHA_BDD [] = 		{"FECHAPJ", "FECHALT", "FECHAQ", "FECHAN", "FECHABL", "FECHAEURO", "FECHAG", "FECHAQP", "FECHAQG"};
	//Nombre que tiene cada sorteo en la propia aplicacion, se diferencia de los primeros en que estan correctamente capitalizados y puntuados
	public final static String NOMBRE_SORTEOS_APP [] =		{"La Primitiva", "Lototurf", "La Quiniela", "Lotería Nacional", "BonoLoto", "Euromillones", "El Gordo", "Quíntuple Plus", "Quinigol"};
	//Nombre de los campos en la base de dasos 1 (la del LotoCapturador programado por Ivan)
	public final static String NOMBRE_CAMPOS_BDD1 [][]=     {{"PJ1","PJ2","PJ3","PJ4","PJ5","PJ6","PJC","PJR","JOKER"},                         // PRIMITIVA
												        	{"LT1","LT2","LT3","LT4","LT5","LT6","LTC","LTR"},                                	// LOTOTURF
												        	{"Q1","Q2","Q3","Q4","Q5","Q6","Q7","Q8","Q9","Q10","Q11","Q12","Q13","Q14","Q15"}, // QUINIELA
												        	{"NAC1","NAC2","ESP","NR"},        													// NACIONAL
												        	{"BL1","BL2","BL3","BL4","BL5","BL6","BLC","BLR"},                                  // BONOLOTO
												        	{"E1","E2","E3","E4","E5","EE1","EE2"},                                       		// EUROMILLON
												        	{"G1","G2","G3","G4","G5","GR"},                                         			// GORDO
												        	{"QP1","QP2","QP3","QP4","QP5","QP6"},                                         		// QUINTUPLE
												        	{"QG1","QG2","QG3","QG4","QG5","QG6"}			                                    // QUINIGOL
												        	};
	//Nombre de los campos en en la propia aplicacion
	public final static String NOMBRE_CAMPOS_APP [][]=     {{"Resultado 1","Resultado 2","Resultado 3","Resultado 4","Resultado 5","Resultado 6","C","R", "Jóker"},
											        		{"Resultado 1","Resultado 2","Resultado 3","Resultado 4","Resultado 5","Resultado 6","C","R"},
											        		{"Resultado 1","Resultado 2","Resultado 3","Resultado 4","Resultado 5","Resultado 6","Resultado 7","Resultado 8","Resultado 9","Resultado 10","Resultado 11","Resultado 12","Resultado 13","Resultado 14","Resultado 15"},
											        		{"1ºPremio","2ºPremio","Fraccion-Serie","Reintegro"},
											        		{"Resultado 1","Resultado 2","Resultado 3","Resultado 4","Resultado 5","Resultado 6", "C", "R"},
											        		{"Resultado 1","Resultado 2","Resultado 3","Resultado 4","Resultado 5", "E1", "E2"},
											        		{"Resultado 1","Resultado 2","Resultado 3","Resultado 4","Resultado 5", "Clave"},
											        		{"Carrera 1","Carrera 2","Carrera 3","Carrera 4","Carrera 5","Carrera 5, 2º clasificado"},
											        		{"Resultado 1","Resultado 2","Resultado 3","Resultado 4","Resultado 5","Resultado 6"}
															};

	public final static tipoGenerador GENERADORES[]=		{new GeneraSorteos.generadorPrimitiva(), new GeneraSorteos.generadorBonolotoLototurf(), new GeneraSorteos.generadorQuiniela(), new GeneraSorteos.generadorLoteriaNacional(), 
															 new GeneraSorteos.generadorBonolotoLototurf(), new GeneraSorteos.generadorEuromillones(), new GeneraSorteos.generadorGordo(), new GeneraSorteos.generadorQuintuple(), 
															 new GeneraSorteos.generadorQuinigol()};

	public final static int TIPODATOS [][] =        	   {{INT,INT,INT,INT,INT,INT,INT,INT,INT},
        													{INT,INT,INT,INT,INT,INT,INT,INT},
        													{WORD,WORD,WORD,WORD,WORD,WORD,WORD,WORD,WORD,WORD,WORD,WORD,WORD,WORD,WORD},
        													{WORD,WORD,WORD,WORD},
        													{INT,INT,INT,INT,INT,INT,INT,INT},
        													{INT,INT,INT,INT,INT,INT,INT},
        													{INT,INT,INT,INT,INT,INT},
        													{INT,INT,INT,INT,INT,INT},
        													{WORD,WORD,WORD,WORD,WORD,WORD}
															};

	public final static int POSICIONEXTRA [] =				{6, 6, 15, 3, 6, 5, 5, 6, 6};

	public final static int SORTEOS_SEMANALES [] =			{2, 1, 1, 2, 4, 2, 1, 1, 2};

	public final static String ORDEN_RENDER [] =			{ "Primitiva", "Gordo", "Bonoloto", "Euromillon", "Nacional", "Lototurf", "Quintuple", "Quinigol", "Quiniela"};

	//Colores (Antes tenian color de reborde, pero para acelerar el framerate se incluyo en la pre-generacion de las fuentes bitmap)
	public final static Color COLOR_RESULTADOS_BASE = Color.WHITE;
	//public final static Color COLOR_RESULTADOS_REBORDE = Color.DARK_GRAY;
	public final static Color COLOR_RESULTADOS_SOMBRA = Color.BLACK;

	public final static Color COLOR_EXTRA_BASE = Color.ORANGE;
	//public final static Color COLOR_EXTRA_REBORDE = Color.DARK_GRAY;
	public final static Color COLOR_EXTRA_SOMBRA = Color.BLACK;

	public final static Color COLOR_DIA_BASE = Color.ORANGE;
	//public final static Color COLOR_DIA_REBORDE = Color.DARK_GRAY;
	public final static Color COLOR_DIA_SOMBRA = Color.BLACK;

	public final static Color COLOR_CATEGORIA_BASE = Color.ORANGE;
	//public final static Color COLOR_CATEGORIA_REBORDE = Color.DARK_GRAY;
	public final static Color COLOR_CATEGORIA_SOMBRA = Color.BLACK;

	public final static Color COLOR_BOTES_BASE = Color.WHITE;
	//public final static Color COLOR_BOTES_REBORDE = Color.DARK_GRAY;
	public final static Color COLOR_BOTES_SOMBRA = Color.BLACK;

	public final static Color COLOR_BOTESFECHA_BASE = Color.ORANGE;
	//public final static Color COLOR_BOTESFECHA_REBORDE = Color.DARK_GRAY;
	public final static Color COLOR_BOTESFECHA_SOMBRA = Color.BLACK;

	public static String BDD_RESULTADOS = "PremiosCarteleria";
	public static String BDD_ESCRUTINIOS = "PremiosCarteleria";
	public static String BDD_BOTES = "PremiosCarteleria";

	public static String BDD_ESCRUTINIO_NOMBRE_CAMPO_TIPOSORTEO = "TipoSorteo";
	public static String BDD_ESCRUTINIO_NOMBRE_CAMPO_CATEGORIA =  "Categoria";
	public static String BDD_ESCRUTINIO_NOMBRE_CAMPO_PREMIO =  "Premio";
	public static String BDD_ESCRUTINIO_NOMBRE_CAMPO_ACERTANTES =  "Acertantes";

	public final static int MENU_RESULTADOS_SEMANALES = 0;
	public final static int MENU_RESULTADOS_TRIMESTRALES = 1;
	public final static int MENU_ESCRUTINIO = 2;
	public final static int MENU_BOTES = 3;

	//BOTES
	public final static String BOTES_NOMBRE_BDD [] =		{ "La Primitiva Jueves", "Lototurf", "Quiniela", "", "Bonoloto", "Euromillones", "El Gordo de la Primi", "QuintuplePlus", "Quinigol"};
	public final static String BOTES_NOMBRE_CAMPOS_BDD [] =	{ "SORTEO", "FECHA", "CANTIDAD" };
}
