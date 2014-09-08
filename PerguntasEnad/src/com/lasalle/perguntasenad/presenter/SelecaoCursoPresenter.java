package com.lasalle.perguntasenad.presenter;

import java.io.InputStream;
import java.util.Set;

import javax.inject.Inject;

import org.w3c.dom.Document;

import android.util.Log;

import com.lasalle.perguntasenad.model.bo.LoaderXmlBO;
import com.lasalle.perguntasenad.model.db.Curso;
import com.lasalle.perguntasenad.model.db.DAO.CursoDAO;
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

    @Inject
    private CursoDAO cursoDAO;

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
                this.carregaListaCursos();
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

    public void carregaListaCursos() {
        Set<Curso> cursos = this.cursoDAO.list( "" );
        for ( Curso curso : cursos ) {
            this.view.addCurso( curso );
        }
    }

    public void cursoSelecionado( Curso cursoSelecionado ) {
        this.view.linkParaEscolhaDisciplinas( cursoSelecionado );
    }
}
