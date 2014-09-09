package com.lasalle.perguntasenad.view.activity;

import java.util.List;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lasalle.perguntasenad.R;
import com.lasalle.perguntasenad.model.db.Curso;
import com.lasalle.perguntasenad.model.db.Disciplina;
import com.lasalle.perguntasenad.model.db.contants.NivelDificuldade;
import com.lasalle.perguntasenad.presenter.SelecaoDisciplinasPresenter;
import com.lasalle.perguntasenad.view.SelecaoDisciplinasView;

/**
 * COntrola tela de seleção de disciplinas.
 * 
 * @author roberto.sousa
 */
@ContentView( R.layout.activity_selecao_disciplinas )
public class SelecaoDisciplinasActivity extends RoboActivity implements SelecaoDisciplinasView {

    private static final Integer QTDE_MAXIMA_PERGUNTAS = 20;

    @Inject
    private SelecaoDisciplinasPresenter presenter;

    @InjectView( R.id.disciplinas )
    private LinearLayout disciplinas;

    @InjectView( R.id.btnVoltar )
    private Button btnVoltar;

    @InjectView( R.id.btnJogar )
    private Button btnJogar;

    @InjectView( R.id.seekQtdePerguntas )
    private SeekBar seekQtdePerguntas;

    @InjectView( R.id.txtQtdePerguntas )
    private TextView txtQtdePerguntas;

    @InjectView( R.id.radioFacil )
    private RadioButton radioFacil;

    @InjectView( R.id.radioMedio )
    private RadioButton radioMedio;

    @InjectView( R.id.radioDificil )
    private RadioButton radioDificil;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        this.presenter.setup( this );
        this.btnVoltar.setOnClickListener( this.clickVoltar );
        this.btnJogar.setOnClickListener( this.clickJogar );
        this.seekQtdePerguntas.setMax( SelecaoDisciplinasActivity.QTDE_MAXIMA_PERGUNTAS );
        this.seekQtdePerguntas.setOnSeekBarChangeListener( this.seekBar );
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

    private OnClickListener clickVoltar = new OnClickListener() {

        @Override
        public void onClick( View v ) {
            SelecaoDisciplinasActivity.this.finish();
        }
    };

    private OnClickListener clickJogar = new OnClickListener() {

        @Override
        public void onClick( View v ) {
            SelecaoDisciplinasActivity.this.presenter.clickJogar(
                SelecaoDisciplinasActivity.this.seekQtdePerguntas.getProgress(),
                SelecaoDisciplinasActivity.this.radioFacil.isSelected(),
                SelecaoDisciplinasActivity.this.radioMedio.isSelected(),
                SelecaoDisciplinasActivity.this.radioDificil.isSelected() );
        }
    };

    private OnSeekBarChangeListener seekBar = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch( SeekBar seekBar ) {
        }

        @Override
        public void onStartTrackingTouch( SeekBar seekBar ) {
        }

        @Override
        public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {
            SelecaoDisciplinasActivity.this.txtQtdePerguntas.setText( progress + "" );
            SelecaoDisciplinasActivity.this.presenter.mudouQtdePerguntas( progress );
        }
    };

    @Override
    public void mostrarMensagemSelecao() {
        Toast.makeText( this, "Selecione pelo menos uma disciplina.", Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void setaQtdePerguntas( int qtde ) {
        this.txtQtdePerguntas.setText( qtde + "" );
        this.seekQtdePerguntas.setProgress( qtde );
    }

    @Override
    public void redirecionaParaTelaJogo( NivelDificuldade nivelEscolhido, int qtdeperguntas,
                                         List<Disciplina> disciplinasSelecionadas ) {
        Intent intent = new Intent( this, JogoActivity.class );
        intent.putExtra( NivelDificuldade.class.getName(), nivelEscolhido );
        intent.putExtra( "QTDE_PERGUNTAS", qtdeperguntas );
        intent.putExtra( Disciplina.class.getName(), disciplinasSelecionadas.toArray() );
        this.startActivity( intent );
    }

}
