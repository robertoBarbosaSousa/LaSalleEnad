package com.lasalle.perguntasenad.model.db;

/**
 * Mapeamento da tabela Curso.
 */
public class Curso extends AbstractEntity {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -7729555475184985837L;

    private String descricao;

    /**
     * Constructor
     */
    public Curso() {
    }

    /**
     * Entity game
     * 
     * @param entityId
     */
    public Curso( long entityId ) {
        super( entityId );
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao( String descricao ) {
        this.descricao = descricao;
    }

}
