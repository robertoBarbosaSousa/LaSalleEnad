package com.lasalle.perguntasenad.model.db;

import java.io.Serializable;

/**
 * Class that implements a generic entity for application
 */
public abstract class AbstractEntity implements Serializable {

    /**
     * shift
     */
    private static final int SHIFT = 32;

    /**
     * has Code.
     */
    private static final int PRIME = 31;

    /**
     * Retains the id serialUID used by a serializable class
     */
    private static final long serialVersionUID = 1L;

    /**
     * Retains the id used by child entities
     */
    private long entityId;

    /**
     * Constructor
     */
    public AbstractEntity() {
    }

    /**
     * Abstract entity id
     * 
     * @param entityId
     */
    public AbstractEntity( long entityId ) {
        this.entityId = entityId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = ( AbstractEntity.PRIME * result ) + this.getSum();
        return result;
    }

    /**
     * SOma de bits.
     * 
     * @return
     */
    private int getSum() {
        return (int) ( this.entityId ^ ( this.entityId >>> AbstractEntity.SHIFT ) );
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( final Object obj ) {
        boolean result = true;

        if ( this == obj ) {
            result = true;

        } else if ( obj == null ) {
            result = false;

        } else if ( this.getClass() != obj.getClass() ) {
            result = false;

        } else {
            final AbstractEntity other = (AbstractEntity) obj;

            if ( this.entityId != other.entityId ) {
                result = false;
            }
        }

        return result;
    }

    /**
     * Gets the id.
     */
    public long getId() {
        return this.entityId;
    }

    /**
     * Sets the id.
     */
    public void setId( final long entityId ) {
        this.entityId = entityId;
    }

}
