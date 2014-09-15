package com.lasalle.perguntasenad.view;

import com.lasalle.perguntasenad.model.db.Opcao;

/**
 * Interface para comunicação entre preenter e view.
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
     * Mostra as informações de fim do jogo.
     * 
     * @param qtdeRespostarCorretas
     * @param qtdePergutnas
     */
    void fimJogo( int qtdePergutnas, int qtdeRespostarCorretas, double percentual );

    /**
     * Adiciona opção na tela.
     * 
     * @param opcao
     */
    void addOpcao( Opcao opcao );

    /**
     * Remove as opções da pergunta anterior.
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
