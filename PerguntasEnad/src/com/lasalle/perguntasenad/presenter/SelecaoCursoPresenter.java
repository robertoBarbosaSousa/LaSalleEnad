package com.lasalle.perguntasenad.presenter;

import java.io.InputStream;

import org.w3c.dom.Document;

import android.util.Log;

import com.lasalle.perguntasenad.presenter.util.XMLUtil;
import com.lasalle.perguntasenad.view.SelecaoCursoView;

/**
 * Controla regras da tela de seleção de curso.
 * 
 * @author roberto.sousa
 */
public class SelecaoCursoPresenter {

    private SelecaoCursoView view;

    private String versionId;

    public void setup( SelecaoCursoView view ) {
        this.view = view;
    }

    /**
     * Verifica se as questões estão atualizadas.
     */
    public void verificaBancoDeQuestoes( String myVersion, InputStream file ) {
        try {
            Document doc = XMLUtil.getDocumentByInputStream( file );
            if ( this.updateValues( myVersion, doc ) ) {
                this.view.exibirDialogoUpdate();
                // TODO: update dos dados
                this.view.atualizaUltimaVersao(versionId);
            } else {
                this.view.carregaListaCursos();
            }
        } catch ( Exception e ) {
            Log.e( "ERROR READIND XML FILE", e.getMessage() );
        }
    }

    /**
     * Verifica se os valores das perguntas serão atualizados.
     * 
     * @return
     */
    private boolean updateValues( String myVersion, Document file ) {
        try {
            this.getVersionId( file );
            return ( ( myVersion == null ) || !this.versionId.equals( myVersion ) );
        } catch ( Exception e ) {
            return true;
        }
    }

    /**
     * Retorna o id do xml atual.
     * 
     * @param file
     * @return
     */
    public void getVersionId( Document file ) {
        if ( ( file.getElementsByTagName( "questoesEnad" ).item( 0 ) != null )
                && ( file.getElementsByTagName( "questoesEnad" ).item( 0 ).getAttributes().getNamedItem( "versao" ) != null ) ) {
            this.versionId = file.getElementsByTagName( "questoesEnad" ).item( 0 ).getAttributes()
                    .getNamedItem( "versao" ).getNodeValue();
        }
    }

}
