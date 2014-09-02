package com.lasalle.perguntasenad.model.db.schema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadContract.CursoData;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadContract.DisciplinaData;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadContract.OpcaoData;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadContract.PerguntaData;

/**
 * Access to and creation of the database application.
 */
public final class PerguntasEnadDataBaseHelper extends SQLiteOpenHelper {

    /**
     * Database version
     */
    private static final int DATABASE_VERSION = 4;

    /**
     * Database name
     */
    private static final String DATABASE_NAME = "perguntasEnad.sqlite";

    private static final String SQL_CREATE_CURSO = "CREATE TABLE " + CursoData.TABLE_NAME + " (" + CursoData._ID
            + " INTEGER PRIMARY KEY," + CursoData.COLUMN_ID + " TEXT, " + CursoData.COLUMN_DESCRICAO + ")";

    private static final String SQL_CREATE_DISCIPLINA = "CREATE TABLE " + DisciplinaData.TABLE_NAME + " ("
            + DisciplinaData._ID + " INTEGER PRIMARY KEY," + DisciplinaData.COLUMN_DESCRICAO + " TEXT, "
            + DisciplinaData.FK_COLUMN_CURSO + " LONG, FOREIGN KEY (" + DisciplinaData.FK_COLUMN_CURSO
            + ") REFERENCES " + CursoData.TABLE_NAME + " (" + CursoData._ID + ") )";

    private static final String SQL_CREATE_PERGUNTA = "CREATE TABLE " + PerguntaData.TABLE_NAME + " ("
            + PerguntaData._ID + " INTEGER PRIMARY KEY," + PerguntaData.COLUMN_ENUNCIADO + " TEXT, "
            + PerguntaData.COLUMN_NIVEL_DIFICULDADE + " INTEGER, " + PerguntaData.FK_COLUMN_DISCIPLINA
            + " LONG, FOREIGN KEY (" + PerguntaData.FK_COLUMN_DISCIPLINA + ") REFERENCES " + DisciplinaData.TABLE_NAME
            + " (" + DisciplinaData._ID + ") )";

    private static final String SQL_CREATE_OPCAO = "CREATE TABLE " + OpcaoData.TABLE_NAME + " (" + OpcaoData._ID
            + " INTEGER PRIMARY KEY," + OpcaoData.COLUMN_DESCRICAO + " TEXT, " + OpcaoData.COLUMN_RESPOSTA_CORRETA
            + " TEXT, " + OpcaoData.FK_COLUMN_PERGUNTA + " LONG, FOREIGN KEY (" + OpcaoData.FK_COLUMN_PERGUNTA
            + ") REFERENCES " + PerguntaData.TABLE_NAME + " (" + PerguntaData._ID + ") )";

    /**
     * Database access (singleton)
     */
    private static PerguntasEnadDataBaseHelper dbHelper;

    private PerguntasEnadDataBaseHelper( Context context ) {
        super( context, PerguntasEnadDataBaseHelper.DATABASE_NAME, null, PerguntasEnadDataBaseHelper.DATABASE_VERSION );
    }

    public static PerguntasEnadDataBaseHelper getInstance() {
        return PerguntasEnadDataBaseHelper.dbHelper;
    }

    //
    // /**
    // * Database instance
    // *
    // * @param context
    // */
    // public static void resetDatabase() {
    // db.execSQL( "TRUNCATE TABLE " + OpcaoData.TABLE_NAME );
    // db.execSQL( "TRUNCATE TABLE " + PerguntaData.TABLE_NAME );
    // db.execSQL( "TRUNCATE TABLE " + DisciplinaData.TABLE_NAME );
    // db.execSQL( "TRUNCATE TABLE " + CursoData.TABLE_NAME );
    // }

    /**
     * Database instance
     * 
     * @param context
     */
    public static void loadDB( final Context context ) {
        if ( PerguntasEnadDataBaseHelper.dbHelper == null ) {
            PerguntasEnadDataBaseHelper.dbHelper = new PerguntasEnadDataBaseHelper( context );
        }
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( PerguntasEnadDataBaseHelper.SQL_CREATE_CURSO );
        db.execSQL( PerguntasEnadDataBaseHelper.SQL_CREATE_DISCIPLINA );
        db.execSQL( PerguntasEnadDataBaseHelper.SQL_CREATE_PERGUNTA );
        db.execSQL( PerguntasEnadDataBaseHelper.SQL_CREATE_OPCAO );
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
    }

}
