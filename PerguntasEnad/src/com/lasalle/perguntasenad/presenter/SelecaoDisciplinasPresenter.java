package com.lasalle.perguntasenad.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lasalle.perguntasenad.model.db.Curso;
import com.lasalle.perguntasenad.model.db.Disciplina;
import com.lasalle.perguntasenad.model.db.DAO.DisciplinaDAO;
import com.lasalle.perguntasenad.model.db.contants.NivelDificuldade;
import com.lasalle.perguntasenad.view.SelecaoDisciplinasView;

/**
 * Regras para tela de seleção de disciplinas.
 * 
 * @author roberto.sousa
 */
public class SelecaoDisciplinasPresenter {

    private static final Integer QTDE_INICIAL_PERGUNTAS = 5;

    private SelecaoDisciplinasView view;

    private Map<Disciplina, Boolean> mapDisciplinas = new HashMap<Disciplina, Boolean>();

    private DisciplinaDAO disciplinaDAO;

    public void setup( SelecaoDisciplinasView view ) {
        this.view = view;
        this.disciplinaDAO = new DisciplinaDAO();
    }

    public void carregaListaDisciplinas( Curso curso ) {
        Set<Disciplina> disciplinas = this.disciplinaDAO.listDisciplinasDoCurso( curso.getId() );
        this.view.setaQtdePerguntas( SelecaoDisciplinasPresenter.QTDE_INICIAL_PERGUNTAS );
        for ( Disciplina disciplina : disciplinas ) {
            this.view.addCheckBoxDisciplina( disciplina );
            this.mapDisciplinas.put( disciplina, true );
        }
    }

    public void mudaSelecao( boolean isChecked, Disciplina tag ) {
        this.mapDisciplinas.put( tag, isChecked );
    }

    public void clickJogar( int qtdeperguntas, boolean radioFacil, boolean radioMedio, boolean radioDificil ) {
        List<Disciplina> disciplinasSelecionadas = this.getDisicplinasSelecionadas();
        if ( disciplinasSelecionadas.size() > 0 ) {
            NivelDificuldade nivelEscolhido = NivelDificuldade.TODOS;
            if ( radioFacil ) {
                nivelEscolhido = NivelDificuldade.FACIL;
            } else if ( radioMedio ) {
                nivelEscolhido = NivelDificuldade.MEDIO;
            } else if ( radioDificil ) {
                nivelEscolhido = NivelDificuldade.DIFICIL;
            }
            this.view.redirecionaParaTelaJogo( nivelEscolhido, qtdeperguntas, disciplinasSelecionadas );
        } else {
            this.view.mostrarMensagemSelecao();
        }
    }

    private List<Disciplina> getDisicplinasSelecionadas() {
        List<Disciplina> disciplinasSelecionadas = new ArrayList<Disciplina>();
        for ( Disciplina item : this.mapDisciplinas.keySet() ) {
            if ( this.mapDisciplinas.get( item ) ) {
                disciplinasSelecionadas.add( item );
            }
        }
        return disciplinasSelecionadas;
    }

    public void mudouQtdePerguntas( int progress ) {
        if ( progress == 0 ) {
            this.view.setaQtdePerguntas( 1 );
        }
    }
}
