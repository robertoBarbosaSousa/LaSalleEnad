package com.lasalle.perguntasenad.model.db.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.lasalle.perguntasenad.model.db.Progresso;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadContract.ProgressoData;

/**
 * Persistence of the Game implementation
 */
public class ProgressoDAO extends AbstractDAO<Progresso> {

    /**
     * DAO game
     */
    public ProgressoDAO() {
        super( ProgressoData.TABLE_NAME );
    }

    /* (non-Javadoc)
     * @see br.com.tectoy.pensebem.domain.model.dao.AbstractDAO#getContentValues(br.com.tectoy.pensebem.domain.model.AbstractEntity)
     */
    @Override
    protected ContentValues getContentValues( Progresso entity ) {
        final ContentValues values = new ContentValues();
        values.put( ProgressoData.COLUMN_PERCENTUAL, entity.getPercentual() );
        return values;
    }

    /* (non-Javadoc)
     * @see br.com.tectoy.pensebem.domain.model.dao.AbstractDAO#getObject(android.database.Cursor)
     */
    @Override
    protected Progresso getObject( Cursor mCursor ) {
        final Progresso perg = new Progresso();
        perg.setId( mCursor.getLong( mCursor.getColumnIndexOrThrow( BaseColumns._ID ) ) );
        perg.setPercentual( mCursor.getDouble( mCursor.getColumnIndexOrThrow( ProgressoData.COLUMN_PERCENTUAL ) ) );
        return perg;
    }
}
