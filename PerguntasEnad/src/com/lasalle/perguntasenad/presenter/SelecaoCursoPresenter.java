package com.lasalle.perguntasenad.presenter;

import java.io.InputStream;

import org.w3c.dom.Document;

import android.util.Log;

import com.google.inject.Inject;
import com.lasalle.perguntasenad.model.bo.LoaderXmlBO;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadDataBaseHelper;
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

    private LoaderXmlBO loaderBO;

    public void setup( SelecaoCursoView view ) {
        this.view = view;
        this.loaderBO = new LoaderXmlBO();
    }

    /**
     * Verifica se as questões estão atualizadas.
     */
    public void verificaBancoDeQuestoes( String myVersion, InputStream file ) {
        try {
            Document doc = XMLUtil.getDocumentByInputStream( file );
            if ( this.updateValues( myVersion, doc ) ) {
                this.view.exibirDialogoUpdate();
                this.view.atualizaUltimaVersao( this.versionId );
            } else {
                this.view.carregaListaCursos();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
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
            this.versionId = this.loaderBO.getVersionId( file );
            return ( ( myVersion == null ) || !this.versionId.equals( myVersion ) );
        } catch ( Exception e ) {
            return true;
        }
    }

    /**
     * Atualiza base de questões;
     * 
     * @param file
     */
    public void atualizaQuestoes( InputStream file ) {
        try {
            Document doc = XMLUtil.getDocumentByInputStream( file );
            this.loaderBO.load( doc );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
