package com.lasalle.perguntasenad.model.db.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.lasalle.perguntasenad.model.db.Curso;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadContract.CursoData;

/**
 * Persistence of the Game implementation
 */
public class CursoDAO extends AbstractDAO<Curso> {

    /**
     * DAO game
     */
    public CursoDAO() {
        super( CursoData.TABLE_NAME );
    }

    /* (non-Javadoc)
     * @see br.com.tectoy.pensebem.domain.model.dao.AbstractDAO#getContentValues(br.com.tectoy.pensebem.domain.model.AbstractEntity)
     */
    @Override
    protected ContentValues getContentValues( Curso entity ) {
        final ContentValues values = new ContentValues();
        values.put( CursoData.COLUMN_DESCRICAO, entity.getDescricao() );
        return values;
    }

    /* (non-Javadoc)
     * @see br.com.tectoy.pensebem.domain.model.dao.AbstractDAO#getObject(android.database.Cursor)
     */
    @Override
    protected Curso getObject( Cursor mCursor ) {
        final Curso curso = new Curso();
        curso.setId( mCursor.getLong( mCursor.getColumnIndexOrThrow( BaseColumns._ID ) ) );
        curso.setDescricao( mCursor.getString( mCursor.getColumnIndexOrThrow( CursoData.COLUMN_DESCRICAO ) ) );
        return curso;
    }
}
