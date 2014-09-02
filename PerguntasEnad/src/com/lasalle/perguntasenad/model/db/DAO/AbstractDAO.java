package com.lasalle.perguntasenad.model.db.DAO;

import java.util.LinkedHashSet;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.lasalle.perguntasenad.model.db.AbstractEntity;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadDataBaseHelper;

/**
 * Base DAO.
 */
public abstract class AbstractDAO<T extends AbstractEntity> {

    /**
     * Database manager
     */
    private final PerguntasEnadDataBaseHelper dbHelper;

    /**
     * Database table name
     */
    private final String tableName;

    /**
     * Abstract DAO
     * 
     * @param tableName
     */
    public AbstractDAO( final String tableName ) {
        this.dbHelper = PerguntasEnadDataBaseHelper.getInstance();
        this.tableName = tableName;
    }

    /**
     * Insert register
     */
    public void insert( T entity ) {
        entity.setId( this.dbHelper.getWritableDatabase()
                .insert( this.tableName, null, this.getContentValues( entity ) ) );
    }

    /**
     * Update register
     * 
     * @param entity
     * @param selection
     * @param selectionArgs
     */
    public void update( T entity, final String selection, final String[] selectionArgs ) {
        this.dbHelper.getWritableDatabase().update( this.tableName, this.getContentValues( entity ), selection,
            selectionArgs );
    }

    /**
     * Update register
     * 
     * @param entity
     */
    public void update( T entity ) {
        final String selection = BaseColumns._ID + " = ?";
        final String[] selectionArgs = { String.valueOf( entity.getId() ) };
        this.dbHelper.getWritableDatabase().update( this.tableName, this.getContentValues( entity ), selection,
            selectionArgs );
    }

    /**
     * Delete register
     * 
     * @param id
     */
    public void deleteById( long id ) {
        final String selection = BaseColumns._ID + " = ?";
        final String[] selectionArgs = { String.valueOf( id ) };
        this.dbHelper.getWritableDatabase().delete( this.tableName, selection, selectionArgs );
    }

    /**
     * Delete database Delete register
     * 
     * @param selection
     * @param selectionArgs
     */
    public void delete( final String selection, final String[] selectionArgs ) {
        this.dbHelper.getWritableDatabase().delete( this.tableName, selection, selectionArgs );
    }

    /**
     * List all registers
     * 
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    protected Set<T> list( final String[] projection, final String selection, final String[] selectionArgs,
                           final String sortOrder ) {

        return this.fillList( this.dbHelper.getWritableDatabase().query( this.tableName, projection, selection,
            selectionArgs, null, null, sortOrder ) );
    }

    /**
     * List all registers
     * 
     * @param sortOrder
     * @return
     */
    public Set<T> list( final String sortOrder ) {
        return this.fillList( this.dbHelper.getWritableDatabase().query( this.tableName, null, null, null, null, null,
            sortOrder ) );
    }

    /**
     * Fill list items
     */
    private Set<T> fillList( final Cursor mCursor ) {
        try {
            final Set<T> items = new LinkedHashSet<T>();
            while ( mCursor.moveToNext() ) {
                items.add( this.getObject( mCursor ) );
            }
            return items;
        } finally {
            this.closeCursor( mCursor );
        }
    }

    /**
     * Retrieves item by id
     */
    public T findById( final long id ) {
        final String selection = BaseColumns._ID + " = ?";
        final String[] selectionArgs = { String.valueOf( id ) };
        final Cursor mCursor = this.dbHelper.getWritableDatabase().query( this.tableName, null, selection,
            selectionArgs, null, null, null );
        try {
            if ( mCursor.moveToNext() ) {
                return this.getObject( mCursor );
            }
        } finally {
            this.closeCursor( mCursor );
        }
        return null;
    }

    /**
     * Method close cursor
     */
    private void closeCursor( final Cursor mCursor ) {
        if ( mCursor != null ) {
            mCursor.close();
        }
    }

    /**
     * Retrieves data from the entity contentValues​​.
     */
    protected abstract ContentValues getContentValues( T entity );

    /**
     * Retrieves the entity from a cursor.
     */
    protected abstract T getObject( Cursor mCursor );

    public void truncate() {
        this.dbHelper.getWritableDatabase().delete( tableName, "", new String[] {} );
    }
}
