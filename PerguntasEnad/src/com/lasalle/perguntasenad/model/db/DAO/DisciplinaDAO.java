package com.lasalle.perguntasenad.model.db.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.lasalle.perguntasenad.model.db.Curso;
import com.lasalle.perguntasenad.model.db.Disciplina;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadContract.CursoData;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadContract.DisciplinaData;

/**
 * Persistence of the Game implementation
 */
public class DisciplinaDAO extends AbstractDAO<Disciplina> {

    /**
     * DAO game
     */
    public DisciplinaDAO() {
        super( DisciplinaData.TABLE_NAME );
    }

    /* (non-Javadoc)
     * @see br.com.tectoy.pensebem.domain.model.dao.AbstractDAO#getContentValues(br.com.tectoy.pensebem.domain.model.AbstractEntity)
     */
    @Override
    protected ContentValues getContentValues( Disciplina entity ) {
        final ContentValues values = new ContentValues();
        values.put( DisciplinaData.COLUMN_DESCRICAO, entity.getDescricao() );
        values.put( DisciplinaData.FK_COLUMN_CURSO, entity.getCurso().getId() );
        return values;
    }

    /* (non-Javadoc)
     * @see br.com.tectoy.pensebem.domain.model.dao.AbstractDAO#getObject(android.database.Cursor)
     */
    @Override
    protected Disciplina getObject( Cursor mCursor ) {
        final Disciplina disc = new Disciplina();
        disc.setId( mCursor.getLong( mCursor.getColumnIndexOrThrow( BaseColumns._ID ) ) );
        disc.setDescricao( mCursor.getString( mCursor.getColumnIndexOrThrow( CursoData.COLUMN_DESCRICAO ) ) );
        disc.setCurso( new Curso(
            mCursor.getLong( mCursor.getColumnIndexOrThrow( DisciplinaData.FK_COLUMN_CURSO ) ) ) );
        return disc;
    }
}
