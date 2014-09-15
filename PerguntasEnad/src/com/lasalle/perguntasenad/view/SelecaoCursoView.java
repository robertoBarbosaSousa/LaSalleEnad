package com.lasalle.perguntasenad.view;

import com.lasalle.perguntasenad.model.db.Curso;

/**
 * Interface para comunicação entre preenter e view.
 * 
 * @author roberto.sousa
 */
public interface SelecaoCursoView {

    /**
     * Mostra dialogo com progress.
     */
    void exibirDialogoUpdate();

    /**
     * Carrega a lista de cursos.
     */
    void addCurso( Curso curso );

    /**
     * Atualiza o valor no shared.
     */
    void atualizaUltimaVersao( String versao );

    /**
     * Re direciona para tela de escolha de disciplinas passando o curso selecionado.
     * 
     * @param cursoSelecionado
     */
    void linkParaEscolhaDisciplinas( Curso cursoSelecionado );

    /**
     * redireciona.
     */
    void redirecionaTelaDeAcompanahmento();

}
