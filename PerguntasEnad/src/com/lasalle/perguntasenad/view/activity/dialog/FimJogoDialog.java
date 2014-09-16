package com.lasalle.perguntasenad.view.activity.dialog;

import java.text.NumberFormat;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lasalle.perguntasenad.R;
import com.lasalle.perguntasenad.view.activity.dialog.listener.FimJogoListener;

/**
 * Dialogo de fim do jogo.
 * 
 * @author roberto.sousa
 */
public class FimJogoDialog extends Dialog {

    private Button btnOk;

    private Button btnProgresso;

    private TextView txtQtdeQuestoes;

    private TextView txtAcertos;

    private TextView txtTempo;

    private TextView txtMedia;

    private FimJogoListener listener;

    private TextView txtPercentual;

    public FimJogoDialog( Context context, FimJogoListener listener ) {
        super( context, R.style.Dialog );
        this.listener = listener;
        this.setContentView( R.layout.dialogo_fim_jogo );
        this.btnOk = (Button) this.findViewById( R.id.btnOK );
        this.btnProgresso = (Button) this.findViewById( R.id.btnVerProgresso );

        this.btnOk.setOnClickListener( this.clickOK );
        this.btnProgresso.setOnClickListener( this.clickProgresso );

        this.txtTempo = (TextView) this.findViewById( R.id.txtTempo );
        this.txtQtdeQuestoes = (TextView) this.findViewById( R.id.txtQtdeQuestoes );
        this.txtMedia = (TextView) this.findViewById( R.id.txtMedia );
        this.txtAcertos = (TextView) this.findViewById( R.id.txtAcertos );
        this.txtPercentual = (TextView) this.findViewById( R.id.txtPercentual );

    }

    android.view.View.OnClickListener clickOK = new android.view.View.OnClickListener() {

        @Override
        public void onClick( View v ) {
            FimJogoDialog.this.listener.finalizaActivity();
        }
    };
    
    android.view.View.OnClickListener clickProgresso = new android.view.View.OnClickListener() {

        @Override
        public void onClick( View v ) {
            FimJogoDialog.this.listener.verProgresso();
        }
    };

    public void show( int qtdePerguntas, int qtdeRespostasCorretas, double percentual, long tempo ) {
        super.show();
        long segundos = tempo / 1000;
        Double media = ( (double) segundos / (double) qtdePerguntas );
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits( 2 );

        this.txtTempo.setText( "Tempo Total: " + segundos + " segundos" );
        this.txtQtdeQuestoes.setText( "Total de Questões: " + qtdePerguntas );
        this.txtAcertos.setText( "Acertos: " + qtdeRespostasCorretas );
        this.txtPercentual.setText( "PercentualAcerto: " + nf.format( percentual ) + "%" );
        this.txtMedia.setText( "Média de tempo por questão: " + nf.format( media ) + " segundos" );
    }
}
