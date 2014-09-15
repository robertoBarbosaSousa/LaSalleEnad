package com.lasalle.perguntasenad.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.provider.BaseColumns;

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.lasalle.perguntasenad.model.db.Progresso;
import com.lasalle.perguntasenad.model.db.DAO.ProgressoDAO;
import com.lasalle.perguntasenad.view.ProgressoView;

/**
 * Regras para monta o grafico.
 * 
 * @author roberto.sousa
 */
public class ProgressoPresenter {

    private ProgressoView view;

    private ProgressoDAO progressoDAO;

    public void setup( ProgressoView view ) {
        this.view = view;
        this.progressoDAO = new ProgressoDAO();
        this.carregaDadosgrafico();
    }

    private void carregaDadosgrafico() {
        Set<Progresso> dados = this.progressoDAO.list( BaseColumns._ID );
        List<GraphViewDataInterface> series = this.contruirListaSeries( dados );
        this.view.controiGrafico( this.converteParaVetor( series ) );
    }

    /**
     * Converte lista para vetor.
     * 
     * @param series
     * @return
     */
    private GraphViewDataInterface[] converteParaVetor( List<GraphViewDataInterface> series ) {
        GraphViewDataInterface[] vetor = new GraphViewDataInterface[series.size()];
        int i = 0;
        for ( GraphViewDataInterface graphViewDataInterface : series ) {
            vetor[i] = graphViewDataInterface;
            i++;
        }
        return vetor;
    }

    /**
     * Monta a lista de series;
     * 
     * @param dados
     * @return
     */
    private List<GraphViewDataInterface> contruirListaSeries( Set<Progresso> dados ) {
        List<GraphViewDataInterface> series = new ArrayList<GraphViewDataInterface>();
        Double soma = Double.valueOf( 0 );
        if ( ( dados != null ) && !dados.isEmpty() ) {
            int count = 1;
            for ( Progresso progresso : dados ) {
                series.add( new GraphViewData( count, progresso.getPercentual() ) );
                soma += progresso.getPercentual();
                count++;
            }
        }
        Double media = Double.valueOf( 0 );
        if ( series.size() > 0 ) {
            media = soma / series.size();
        }
        this.view.preencheTextos( series.size(), media );
        return series;
    }

    public void voltar() {
        this.view.finalizaView();
    }

}
