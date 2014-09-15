package com.lasalle.perguntasenad.view;

import com.lasalle.perguntasenad.model.db.Opcao;

/**
 * Interface para comunica��o entre preenter e view.
 * 
 * @author roberto.sousa
 */
public interface JogoView {

    /**
     * Para o cronometro.
     */
    void pararTempo();

    /**
     * Inicia o cronometro.
     */
    void iniciarTempo();

    /**
     * Mostra o enunciado da pergunta.
     * 
     * @param enunciado
     */
    void mostraEnunciado( String enunciado );

    /**
     * Mostra as informa��es de fim do jogo.
     * 
     * @param qtdeRespostarCorretas
     * @param qtdePergutnas
     */
    void fimJogo( int qtdePergutnas, int qtdeRespostarCorretas, double percentual );

    /**
     * Adiciona op��o na tela.
     * 
     * @param opcao
     */
    void addOpcao( Opcao opcao );

    /**
     * Remove as op��es da pergunta anterior.
     */
    void limparOpcoes();

    /**
     * Monstra mensagem correto.
     */
    void mostraFeedbackCorreto();

    /**
     * Monstra mensagem errado.
     */
    void mostraFeedbackErrado();

    /**
     * Mostra mensagem de selecionar resposta.
     */
    void selecioneResposta();

}
