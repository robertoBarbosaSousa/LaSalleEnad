package com.lasalle.perguntasenad.view.activity;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.lasalle.perguntasenad.R;
import com.lasalle.perguntasenad.model.db.Curso;
import com.lasalle.perguntasenad.model.db.Disciplina;
import com.lasalle.perguntasenad.presenter.SelecaoDisciplinasPresenter;
import com.lasalle.perguntasenad.view.SelecaoDisciplinasView;

/**
 * COntrola tela de seleção de disciplinas.
 * 
 * @author roberto.sousa
 */
@ContentView( R.layout.activity_selecao_disciplinas )
public class SelecaoDisciplinasActivity extends RoboActivity implements SelecaoDisciplinasView {

    @Inject
    private SelecaoDisciplinasPresenter presenter;

    @InjectView( R.id.disciplinas )
    private LinearLayout disciplinas;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        this.presenter.setup( this );
    }

    @Override
    protected void onPostCreate( Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );
        this.presenter.carregaListaDisciplinas( (Curso) this.getIntent().getSerializableExtra( Curso.class.getName() ) );
    }

    @Override
    public void addCheckBoxDisciplina( Disciplina disciplina ) {
        CheckBox cbx = new CheckBox( this );
        cbx.setChecked( true );
        cbx.setText( disciplina.getDescricao() );
        cbx.setOnCheckedChangeListener( this.mudaSelecao );
        cbx.setTag( disciplina );
        this.disciplinas.addView( cbx );
    }

    private OnCheckedChangeListener mudaSelecao = new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged( CompoundButton buttonView, boolean isChecked ) {
            SelecaoDisciplinasActivity.this.presenter.mudaSelecao( isChecked, (Disciplina) buttonView.getTag() );
        }
    };
}
