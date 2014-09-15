package com.lasalle.perguntasenad.view.activity;

import java.text.NumberFormat;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.lasalle.perguntasenad.R;
import com.lasalle.perguntasenad.presenter.ProgressoPresenter;
import com.lasalle.perguntasenad.view.ProgressoView;

/**
 * COntrola tela de seleção de curso.
 * 
 * @author roberto.sousa
 */
@ContentView( R.layout.activity_progresso )
public class ProgressoActivity extends RoboActivity implements ProgressoView {

    @Inject
    private ProgressoPresenter presenter;

    @InjectView( R.id.btnVoltar )
    private Button btnVoltar;

    @InjectView( R.id.grafico )
    private RelativeLayout rlGrafico;

    @InjectView( R.id.txTotalPartidas )
    private TextView txTotalPartidas;

    @InjectView( R.id.txMedia )
    private TextView txMedia;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        this.presenter.setup( this );
        this.btnVoltar.setOnClickListener( this.clickVoltar );
    }

    @Override
    public void controiGrafico( GraphViewDataInterface[] series ) {
        GraphViewSeries exampleSeries = new GraphViewSeries( series );
        GraphView graphView = new LineGraphView( this, "Progresso" );
        graphView.setManualYAxisBounds( 100, 0 );
        graphView.addSeries( exampleSeries );
        graphView.setScrollable( true );
        graphView.setShowHorizontalLabels( false );
        this.rlGrafico.addView( graphView );
    }

    private OnClickListener clickVoltar = new OnClickListener() {

        @Override
        public void onClick( View v ) {
            ProgressoActivity.this.presenter.voltar();
        }
    };

    @Override
    public void finalizaView() {
        this.finish();
    }

    @Override
    public void preencheTextos( int totalPartidas, Double media ) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits( 2 );
        this.txTotalPartidas.setText( "Total de Partidas: " + totalPartidas );
        this.txMedia.setText( "Média de acertos: " + nf.format( media ) + "%" );
    }

}