package com.lasalle.perguntasenad.view;

import java.util.List;

import com.lasalle.perguntasenad.model.db.Disciplina;
import com.lasalle.perguntasenad.model.db.contants.NivelDificuldade;

/**
 * Interface para comunicação entre preenter e view.
 * 
 * @author roberto.sousa
 */
public interface SelecaoDisciplinasView {

    /**
     * Adiciona a disciplina do cursos selecionado.
     * 
     * @param disciplina
     */
    void addCheckBoxDisciplina( Disciplina disciplina );

    /**
     * Exibe mensagem pedindo para usuários selecionar item.
     */
    void mostrarMensagemSelecao();

    /**
     * Atualiza a qtde de perguntas
     * 
     * @param i
     */
    void setaQtdePerguntas( int qtde );

    /**
     * Redireciona para a tela do jogo.
     * 
     * @param nivelEscolhido
     * @param qtdeperguntas
     * @param disciplinasSelecionadas
     */
    void redirecionaParaTelaJogo( NivelDificuldade nivelEscolhido, int qtdeperguntas,
                                  List<Disciplina> disciplinasSelecionadas );

}
