package com.lasalle.perguntasenad.model.db.DAO;

import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.lasalle.perguntasenad.model.db.Opcao;
import com.lasalle.perguntasenad.model.db.Pergunta;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadContract.OpcaoData;

/**
 * Persistence of the Game implementation
 */
public class OpcaoDAO extends AbstractDAO<Opcao> {

    /**
     * DAO game
     */
    public OpcaoDAO() {
        super( OpcaoData.TABLE_NAME );
    }

    /* (non-Javadoc)
     * @see br.com.tectoy.pensebem.domain.model.dao.AbstractDAO#getContentValues(br.com.tectoy.pensebem.domain.model.AbstractEntity)
     */
    @Override
    protected ContentValues getContentValues( Opcao entity ) {
        final ContentValues values = new ContentValues();
        values.put( OpcaoData.COLUMN_DESCRICAO, entity.getDescricao() );
        values.put( OpcaoData.FK_COLUMN_PERGUNTA, entity.getPergunta().getId() );
        values.put( OpcaoData.COLUMN_RESPOSTA_CORRETA, entity.isRespostaCorreta() );
        return values;
    }

    /* (non-Javadoc)
     * @see br.com.tectoy.pensebem.domain.model.dao.AbstractDAO#getObject(android.database.Cursor)
     */
    @Override
    protected Opcao getObject( Cursor mCursor ) {
        final Opcao opcao = new Opcao();
        opcao.setId( mCursor.getLong( mCursor.getColumnIndexOrThrow( BaseColumns._ID ) ) );
        opcao.setDescricao( mCursor.getString( mCursor.getColumnIndexOrThrow( OpcaoData.COLUMN_DESCRICAO ) ) );
        opcao.setRespostaCorreta( mCursor.getInt( mCursor.getColumnIndexOrThrow( OpcaoData.COLUMN_RESPOSTA_CORRETA ) ) == 1 );
        opcao.setPergunta( new Pergunta(
            mCursor.getLong( mCursor.getColumnIndexOrThrow( OpcaoData.FK_COLUMN_PERGUNTA ) ) ) );
        return opcao;
    }

    public Set<Opcao> getOpcoes( long idPergunta ) {
        final String selection = OpcaoData.FK_COLUMN_PERGUNTA + " = ?";
        final String[] selectionArgs = { String.valueOf( idPergunta ) };
        return this.list( null, selection, selectionArgs, null );
    }

}
