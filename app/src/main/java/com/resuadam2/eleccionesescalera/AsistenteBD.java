package com.resuadam2.eleccionesescalera;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;

/**
 * Clase que gestiona la creación y actualización de la BD
 */
public class AsistenteBD extends SQLiteOpenHelper {
    final static String NOMBRE_BD = "elecciones.sqlite";
    final static int VERSION_BD = 1;
    SQLiteStatement sqlInsertPartido, sqlInsertCandidato;

    /**
     * Constructor
     * @param context Contexto de la aplicación
     */
    public AsistenteBD(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    /**
     * Método que crea la BD
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (NIF TEXT PRIMARY KEY, password TEXT, haVotado INTEGER)");
        db.execSQL("CREATE TABLE partidos (codPartido INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, color INTEGER)");
        db.execSQL("CREATE TABLE candidatos (codCandidato INTEGER PRIMARY KEY AUTOINCREMENT, codPartido INTEGER, nombre TEXT, votos INTEGER DEFAULT 0)");
        // INSERTAR USUARIOS
        db.execSQL("INSERT INTO usuarios VALUES('12345678Z','" + Utiles.generateHash("abc123.")+"',0)");
        db.execSQL("INSERT INTO usuarios VALUES('87654321X','" + Utiles.generateHash("sesamo")+"',0)");
        // INSERTAR PARTIDOS Y CANDIDATOS
        sqlInsertPartido = db.compileStatement("INSERT INTO partidos (nombre,color) VALUES (?,?)");
        sqlInsertCandidato = db.compileStatement("INSERT INTO candidatos(codPartido,nombre) VALUES (?,?)");
        insertaDatosPartido("Esta, nuestra comunidad", Color.BLUE, new String[]
                {"Juan \"Chorizo\" Cuesta", "Isabel \"Yerbas\"", "Mauri"});
        insertaDatosPartido("Un poquito de pofavoh", Color.RED, new String[]
                {"Emilio Delgado", "Mariano Delgado", "Jose María"});
        insertaDatosPartido("Los pibes del videoclub", Color.YELLOW ,new String[]
                {"Paco", "Josemi Cuesta", "Roberto"});
        insertaDatosPartido("La pija y amigas", Color.rgb(255,128,0), new String[]
                {"Lucia", "Belen", "Bea"});
        insertaDatosPartido("Radiopactos", Color.rgb(255,0,128), new String[]
                {"Concha", "Marisa", "Vicenta"});
    }

    /**
     * Método que inserta los datos de los partidos y sus candidatos
     * @param nombrePartido Nombre del partido
     * @param color Color del partido
     * @param listaCandidatos Lista de candidatos del partido
     */
    private void insertaDatosPartido(String nombrePartido, int color, String[] listaCandidatos){
        sqlInsertPartido.bindString(1, nombrePartido);
        sqlInsertPartido.bindLong(2, color);
        long codPartido=sqlInsertPartido.executeInsert();
        for(String nombreCandidato:listaCandidatos) {
            sqlInsertCandidato.bindLong(1, codPartido);
            sqlInsertCandidato.bindString(2, nombreCandidato);
            sqlInsertCandidato.execute();
        }
    }

    /**
     * Método que actualiza la BD
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//Gestionar la actualización del esquema y de los datos de la BD en el cambio de versión de la app
    }
}