package com.lasalle.perguntasenad.model.db;

/**
 * Mapeamento da disciplina.
 * 
 * @author roberto.sousa
 */
public class Disciplina extends AbstractEntity {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -5976790608959443061L;

    private String descricao;

    private Curso curso;

    /**
     * Contrutor.
     */
    public Disciplina() {
    }

    /**
     * Entity game
     * 
     * @param entityId
     */
    public Disciplina( long entityId ) {
        super( entityId );
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao( String descricao ) {
        this.descricao = descricao;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso( Curso curso ) {
        this.curso = curso;
    }

}
