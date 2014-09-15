package com.lasalle.perguntasenad.model.db;

/**
 * Mapeamento da tabela PROGRESSO.
 */
public class Progresso extends AbstractEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1294100691894474869L;

    private Double percentual;

    /**
     * Constructor
     */
    public Progresso() {
    }

    /**
     * Entity game
     * 
     * @param percentual2
     */
    public Progresso( Double percentual ) {
        this.percentual = percentual;
    }

    public Double getPercentual() {
        return this.percentual;
    }

    public void setPercentual( Double percentual ) {
        this.percentual = percentual;
    }

}
