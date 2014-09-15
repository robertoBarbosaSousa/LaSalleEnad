package com.lasalle.perguntasenad.view.activity;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lasalle.perguntasenad.R;
import com.lasalle.perguntasenad.model.db.Disciplina;
import com.lasalle.perguntasenad.model.db.Opcao;
import com.lasalle.perguntasenad.model.db.contants.NivelDificuldade;
import com.lasalle.perguntasenad.presenter.JogoPresenter;
import com.lasalle.perguntasenad.view.JogoView;
import com.lasalle.perguntasenad.view.activity.dialog.FimJogoDialog;
import com.lasalle.perguntasenad.view.activity.dialog.listener.FimJogoListener;

@ContentView( R.layout.activity_jogo )
public class JogoActivity extends RoboActivity implements JogoView, FimJogoListener {

    private static final int PADDING_OPCOES = 10;

    @Inject
    private JogoPresenter presenter;

    @InjectView( R.id.cronometro )
    private Chronometer cronometro;

    @InjectView( R.id.enunciado )
    private TextView enunciado;

    @InjectView( R.id.respostas )
    private LinearLayout opcoes;

    @InjectView( R.id.btnConfirma )
    private Button btnConfirmar;

    @InjectView( R.id.btnPular )
    private Button btnPular;

    @InjectView( R.id.btnDesistir )
    private Button btnDesistir;

    private FimJogoDialog dialogFimJogo;

    private static final int COR_SELECIONADO = Color.YELLOW;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        this.presenter.setup( this );
        this.btnPular.setOnClickListener( this.clickPular );
        this.btnConfirmar.setOnClickListener( this.clickConfirmar );
        this.btnDesistir.setOnClickListener( this.clickDesistir );
        this.dialogFimJogo = new FimJogoDialog( this, this );
    }

    @Override
    protected void onPostCreate( Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );
        this.presenter.iniciaJogo(
            (NivelDificuldade) this.getIntent().getSerializableExtra( NivelDificuldade.class.getName() ),
            (Integer) this.getIntent().getSerializableExtra( "QTDE_PERGUNTAS" ), (Object[]) this.getIntent()
                    .getSerializableExtra( Disciplina.class.getName() ) );
    }

    @Override
    public void pararTempo() {
        this.cronometro.stop();
    }

    @Override
    public void iniciarTempo() {
        this.cronometro.setBase( SystemClock.elapsedRealtime() );
        this.cronometro.start();
    }

    @Override
    public void mostraEnunciado( String enunciado ) {
        this.enunciado.setText( enunciado );
    }

    @Override
    public void fimJogo( int qtdePergutnas, int qtdeRespostarCorretas, double percentual ) {
        this.pararTempo();
        this.btnConfirmar.setClickable( false );
        this.btnPular.setClickable( false );
        this.dialogFimJogo.show( qtdePergutnas, qtdeRespostarCorretas, percentual, SystemClock.elapsedRealtime()
                - this.cronometro.getBase() );
    }

    @Override
    public void addOpcao( Opcao opcao ) {
        RelativeLayout relative = new RelativeLayout( this );
        relative.setLayoutParams( new LayoutParams( android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT ) );
        relative.setTag( opcao );
        TextView tv = new TextView( this );
        tv.setText( opcao.getDescricao() );
        relative.addView( tv );
        relative.setBackgroundColor( Color.TRANSPARENT );
        relative.setOnClickListener( this.clickRelative );
        relative.setPadding( 0, JogoActivity.PADDING_OPCOES, 0, JogoActivity.PADDING_OPCOES );
        this.opcoes.addView( relative );
    }

    @Override
    public void limparOpcoes() {
        this.opcoes.removeAllViews();
    }

    @Override
    public void mostraFeedbackCorreto() {
        Toast.makeText( this, "Ta 's'erto!!!", Toast.LENGTH_SHORT ).show();

    }

    @Override
    public void mostraFeedbackErrado() {
        Toast.makeText( this, "Q burro!!!", Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void selecioneResposta() {
        Toast.makeText( this, "Selecione uma opção!!!", Toast.LENGTH_SHORT ).show();
    }

    private OnClickListener clickRelative = new OnClickListener() {

        @Override
        public void onClick( View v ) {
            for ( int i = 0; i < JogoActivity.this.opcoes.getChildCount(); i++ ) {
                JogoActivity.this.opcoes.getChildAt( i ).setBackgroundColor( Color.TRANSPARENT );
            }
            v.setBackgroundColor( JogoActivity.COR_SELECIONADO );
            JogoActivity.this.presenter.selecionou( (Opcao) v.getTag() );
        }
    };

    private OnClickListener clickPular = new OnClickListener() {

        @Override
        public void onClick( View v ) {
            JogoActivity.this.presenter.pular();
        }
    };

    private OnClickListener clickDesistir = new OnClickListener() {

        @Override
        public void onClick( View v ) {
            JogoActivity.this.finish();
        }
    };

    private OnClickListener clickConfirmar = new OnClickListener() {

        @Override
        public void onClick( View v ) {
            JogoActivity.this.presenter.confirma();
        }
    };

    @Override
    public void finalizaActivity() {
        this.dialogFimJogo.dismiss();
        this.finish();
    }

}
