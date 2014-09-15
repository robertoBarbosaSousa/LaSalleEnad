package com.lasalle.perguntasenad.presenter.DTO;

import com.lasalle.perguntasenad.model.db.Opcao;
import com.lasalle.perguntasenad.model.db.Pergunta;

/**
 * Utilitario para auxiliar no controle das perguntas respondidas.
 * 
 * @author roberto.sousa
 */
public class PerguntaDTO {

    private Pergunta pergunta;

    private Opcao resposta;

    public PerguntaDTO( Pergunta pergunta ) {
        super();
        this.pergunta = pergunta;
    }

    public boolean isRespondido() {
        return this.resposta != null;
    }

    public Opcao getResposta() {
        return this.resposta;
    }

    public void setResposta( Opcao resposta ) {
        this.resposta = resposta;
    }

    public Pergunta getPergunta() {
        return this.pergunta;
    }

    public boolean isCorreto() {
        if ( this.resposta != null ) {
            return this.resposta.isRespostaCorreta();
        }
        return false;
    }

}
