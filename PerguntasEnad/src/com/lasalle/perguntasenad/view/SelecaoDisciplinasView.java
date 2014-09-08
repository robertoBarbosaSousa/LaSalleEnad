package com.lasalle.perguntasenad.view;

import com.lasalle.perguntasenad.model.db.Disciplina;

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
    void addCheckBoxDisciplina (Disciplina disciplina );

}
