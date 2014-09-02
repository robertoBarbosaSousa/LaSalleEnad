package com.lasalle.perguntasenad.view;

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
    void carregaListaCursos();

    /**
     * Atualiza o valor no shared.
     */
    void atualizaUltimaVersao( String versao );

}
