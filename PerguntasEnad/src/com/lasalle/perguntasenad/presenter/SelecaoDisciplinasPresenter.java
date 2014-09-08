package com.lasalle.perguntasenad.presenter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.lasalle.perguntasenad.model.db.Curso;
import com.lasalle.perguntasenad.model.db.Disciplina;
import com.lasalle.perguntasenad.model.db.DAO.DisciplinaDAO;
import com.lasalle.perguntasenad.view.SelecaoDisciplinasView;

/**
 * Regras para tela de seleção de disciplinas.
 * 
 * @author roberto.sousa
 */
public class SelecaoDisciplinasPresenter {

    private SelecaoDisciplinasView view;

    private Map<Disciplina, Boolean> mapDisciplinas = new HashMap<Disciplina, Boolean>();

    private DisciplinaDAO disciplinaDAO;

    public void setup( SelecaoDisciplinasView view ) {
        this.view = view;
        this.disciplinaDAO = new DisciplinaDAO();
    }

    public void carregaListaDisciplinas( Curso curso ) {
        Set<Disciplina> disciplinas = this.disciplinaDAO.listDisciplinasDoCurso( curso.getId() );
        for ( Disciplina disciplina : disciplinas ) {
            this.view.addCheckBoxDisciplina( disciplina );
            this.mapDisciplinas.put( disciplina, true );
        }
    }

    public void mudaSelecao( boolean isChecked, Disciplina tag ) {
        this.mapDisciplinas.put( tag, isChecked );
    }
}
