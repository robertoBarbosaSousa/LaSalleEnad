package com.lasalle.perguntasenad.model.db;

/**
 * Mapeamento das opçõpes.
 * 
 * @author roberto.sousa
 */
public class Opcao extends AbstractEntity {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -1311430221205370956L;

    private String descricao;

    private Pergunta pergunta;

    private boolean respostaCorreta;

    /**
     * Contrutor.
     */
    public Opcao() {
    }

    /**
     * Entity game
     * 
     * @param entityId
     */
    public Opcao( long entityId ) {
        super( entityId );
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao( String descricao ) {
        this.descricao = descricao;
    }

    public Pergunta getPergunta() {
        return this.pergunta;
    }

    public void setPergunta( Pergunta pergunta ) {
        this.pergunta = pergunta;
    }

    public boolean isRespostaCorreta() {
        return this.respostaCorreta;
    }

    public void setRespostaCorreta( boolean respostaCorreta ) {
        this.respostaCorreta = respostaCorreta;
    }

}
