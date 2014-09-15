package com.lasalle.perguntasenad.model.db.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.lasalle.perguntasenad.model.db.Disciplina;
import com.lasalle.perguntasenad.model.db.Pergunta;
import com.lasalle.perguntasenad.model.db.contants.NivelDificuldade;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadContract.PerguntaData;

/**
 * Persistence of the Game implementation
 */
public class PerguntaDAO extends AbstractDAO<Pergunta> {

    /**
     * DAO game
     */
    public PerguntaDAO() {
        super( PerguntaData.TABLE_NAME );
    }

    /* (non-Javadoc)
     * @see br.com.tectoy.pensebem.domain.model.dao.AbstractDAO#getContentValues(br.com.tectoy.pensebem.domain.model.AbstractEntity)
     */
    @Override
    protected ContentValues getContentValues( Pergunta entity ) {
        final ContentValues values = new ContentValues();
        values.put( PerguntaData.COLUMN_ENUNCIADO, entity.getEnunciado() );
        values.put( PerguntaData.FK_COLUMN_DISCIPLINA, entity.getDisciplina().getId() );
        values.put( PerguntaData.COLUMN_NIVEL_DIFICULDADE, entity.getNivelDificuldade().ordinal() );
        return values;
    }

    /* (non-Javadoc)
     * @see br.com.tectoy.pensebem.domain.model.dao.AbstractDAO#getObject(android.database.Cursor)
     */
    @Override
    protected Pergunta getObject( Cursor mCursor ) {
        final Pergunta perg = new Pergunta();
        perg.setId( mCursor.getLong( mCursor.getColumnIndexOrThrow( BaseColumns._ID ) ) );
        perg.setEnunciado( mCursor.getString( mCursor.getColumnIndexOrThrow( PerguntaData.COLUMN_ENUNCIADO ) ) );
        perg.setNivelDificuldade( NivelDificuldade.values()[mCursor.getInt( mCursor
                .getColumnIndexOrThrow( PerguntaData.COLUMN_NIVEL_DIFICULDADE ) )] );
        perg.setDisciplina( new Disciplina( mCursor.getLong( mCursor
                .getColumnIndexOrThrow( PerguntaData.FK_COLUMN_DISCIPLINA ) ) ) );
        return perg;
    }

    public Set<Pergunta> listPerguntas( NivelDificuldade nivel, String disciplinasSelecionadas ) {

        String selection = "";
        List<String> selectionArgs = new ArrayList<String>();
        if ( ( nivel != null ) && !nivel.equals( NivelDificuldade.TODOS ) ) {
            selection += PerguntaData.COLUMN_NIVEL_DIFICULDADE + " = ? and ";
            selectionArgs.add( nivel.ordinal() + "" );
        }
        if ( disciplinasSelecionadas != null && !disciplinasSelecionadas.equals( "" ) ) {
            selection += PerguntaData.FK_COLUMN_DISCIPLINA + " in (" + disciplinasSelecionadas + ") and ";
        }
        selection = selection.substring( 0, selection.length() - 5 );

        return this.list( null, selection, selectionArgs.toArray( new String[selectionArgs.size()] ), null );
    }
}
