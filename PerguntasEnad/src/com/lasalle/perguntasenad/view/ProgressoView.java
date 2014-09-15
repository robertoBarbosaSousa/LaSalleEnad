package com.lasalle.perguntasenad.view;

import com.jjoe64.graphview.GraphViewDataInterface;

/**
 * Comunicação entre as camadas.
 * 
 * @author roberto.sousa
 */
public interface ProgressoView {

    /**
     * Finaliza.
     */
    void finalizaView();

    /**
     * Manda dados pro grafico.
     * 
     * @param series
     */
    void controiGrafico( GraphViewDataInterface[] series );

    /**
     * Preenche os textos.
     * 
     * @param size
     * @param media
     */
    void preencheTextos( int size, Double media );

}
