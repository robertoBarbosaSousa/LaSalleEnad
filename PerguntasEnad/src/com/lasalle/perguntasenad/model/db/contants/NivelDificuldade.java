package com.lasalle.perguntasenad.model.db.contants;

/**
 * Mapeamento do nivel de dificuldade.
 * 
 * @author roberto.sousa
 */
public enum NivelDificuldade {
    FACIL( "facil" ),
    MEDIO( "medio" ),
    DIFICIL( "dificil" ),
    TODOS( "todos" );

    private String desc;

    private NivelDificuldade( String desc ) {
        this.desc = desc;
    }

    public static NivelDificuldade getNivelFromDesc( String dificuldade ) {
        if ( ( dificuldade != null ) && !dificuldade.equals( "" ) ) {
            for ( NivelDificuldade nivel : NivelDificuldade.values() ) {
                if ( nivel.desc.equalsIgnoreCase( dificuldade ) ) {
                    return nivel;
                }
            }
        }
        return FACIL;
    }
}
