package com.lasalle.perguntasenad.model.db;

import com.lasalle.perguntasenad.model.db.contants.NivelDificuldade;

/**
 * Mapeamento das perguntas.
 * 
 * @author roberto.sousa
 */
public class Pergunta extends AbstractEntity {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -1943521894914879399L;

    private String enunciado;

    private Disciplina disciplina;

    private NivelDificuldade nivelDificuldade;

    /**
     * Contrutor.
     */
    public Pergunta() {
    }

    /**
     * Entity game
     * 
     * @param entityId
     */
    public Pergunta( long entityId ) {
        super( entityId );
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado( String enunciado ) {
        this.enunciado = enunciado;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina( Disciplina disciplina ) {
        this.disciplina = disciplina;
    }

    public NivelDificuldade getNivelDificuldade() {
        return nivelDificuldade;
    }

    public void setNivelDificuldade( NivelDificuldade nivelDificuldade ) {
        this.nivelDificuldade = nivelDificuldade;
    }

}
