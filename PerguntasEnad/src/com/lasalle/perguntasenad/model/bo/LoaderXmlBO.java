package com.lasalle.perguntasenad.model.bo;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.lasalle.perguntasenad.model.db.Curso;
import com.lasalle.perguntasenad.model.db.Disciplina;
import com.lasalle.perguntasenad.model.db.Opcao;
import com.lasalle.perguntasenad.model.db.Pergunta;
import com.lasalle.perguntasenad.model.db.DAO.CursoDAO;
import com.lasalle.perguntasenad.model.db.DAO.DisciplinaDAO;
import com.lasalle.perguntasenad.model.db.DAO.OpcaoDAO;
import com.lasalle.perguntasenad.model.db.DAO.PerguntaDAO;
import com.lasalle.perguntasenad.model.db.contants.NivelDificuldade;

/**
 * Loader xml BO implementation
 */
public class LoaderXmlBO {

    private CursoDAO cursoDAO;

    private DisciplinaDAO disciplinaDAO;

    private OpcaoDAO opcaoDAO;

    private PerguntaDAO perguntaDAO;

    /**
     * Method load xml
     */
    public LoaderXmlBO() {
        this.cursoDAO = new CursoDAO();
        this.disciplinaDAO = new DisciplinaDAO();
        this.opcaoDAO = new OpcaoDAO();
        this.perguntaDAO = new PerguntaDAO();
    }

    /**
     * Retorna o id do xml atual.
     * 
     * @param file
     * @return
     */
    public String getVersionId( Document file ) {
        if ( ( file.getElementsByTagName( "questoesEnad" ).item( 0 ) != null )
                && ( file.getElementsByTagName( "questoesEnad" ).item( 0 ).getAttributes().getNamedItem( "versao" ) != null ) ) {
            return file.getElementsByTagName( "questoesEnad" ).item( 0 ).getAttributes().getNamedItem( "versao" )
                    .getNodeValue();
        }
        return "";
    }

    /**
     * Method load from xml
     */
    public void load( Document rootXml ) {
        this.limparDataBase();
        Node penseBemNode = rootXml.getElementsByTagName( "questoesEnad" ).item( 0 );
        for ( int i = 0; i < penseBemNode.getChildNodes().getLength(); i++ ) {
            Node node = penseBemNode.getChildNodes().item( i );
            if ( node.getNodeName().equalsIgnoreCase( "curso" ) ) {
                this.atualizaCurso( node );
            }
        }
    }

    /**
     * Atualiza as informações do curso.
     * 
     * @param node
     */
    private void atualizaCurso( Node node ) {
        String desc = this.getValue( node, "descricao" );
        if ( desc != null ) {
            Curso curso = new Curso();
            curso.setDescricao( desc );
            this.cursoDAO.insert( curso );
            for ( int i = 0; i < node.getChildNodes().getLength(); i++ ) {
                Node filho = node.getChildNodes().item( i );
                if ( filho.getNodeName().equalsIgnoreCase( "disciplina" ) ) {
                    this.atualizaDisciplina( filho, curso );
                }
            }
        }
    }

    /**
     * Cria Disciplina
     * 
     * @param curso
     * @param filho
     */
    private void atualizaDisciplina( Node node, Curso curso ) {
        String desc = this.getValue( node, "descricao" );
        if ( desc != null ) {
            Disciplina disc = new Disciplina();
            disc.setCurso( curso );
            disc.setDescricao( desc );
            this.disciplinaDAO.insert( disc );
            for ( int i = 0; i < node.getChildNodes().getLength(); i++ ) {
                Node filho = node.getChildNodes().item( i );
                if ( filho.getNodeName().equalsIgnoreCase( "pergunta" ) ) {
                    this.atualizaPergunta( filho, disc );
                }
            }
        }
    }

    /**
     * Cria pergunta
     * 
     * @param filho
     * @param disc
     */
    private void atualizaPergunta( Node node, Disciplina disc ) {
        String enunciado = this.getValue( node, "enunciado" );
        String dificuldade = this.getValue( node, "dificuldade" );

        if ( enunciado != null ) {
            Pergunta perg = new Pergunta();
            perg.setDisciplina( disc );
            perg.setEnunciado( enunciado );
            perg.setNivelDificuldade( NivelDificuldade.getNivelFromDesc( dificuldade ) );

            this.perguntaDAO.insert( perg );

            for ( int i = 0; i < node.getChildNodes().getLength(); i++ ) {
                Node filho = node.getChildNodes().item( i );
                if ( filho.getNodeName().equalsIgnoreCase( "opcao" ) ) {
                    this.atualizaOpcao( filho, perg );
                }
            }
        }
    }

    /**
     * Atualiza opção.
     * 
     * @param filho
     * @param perg
     */
    private void atualizaOpcao( Node node, Pergunta perg ) {
        String desc = this.getValue( node, "descricao" );
        String respostaCorreta = this.getValue( node, "respostaCorreta" );
        boolean resposta = respostaCorreta != null ? Boolean.valueOf( respostaCorreta ) : false;
        if ( desc != null ) {
            Opcao op = new Opcao();
            op.setPergunta( perg );
            op.setDescricao( desc );
            op.setRespostaCorreta( resposta );
            this.opcaoDAO.insert( op );
        }
    }

    private void limparDataBase() {
        this.opcaoDAO.truncate();
        this.perguntaDAO.truncate();
        this.disciplinaDAO.truncate();
        this.cursoDAO.truncate();
    }

    /**
     * Retorna o valor do atributo. Retorna null caso o atributo nao exista.
     * 
     * @param node
     * @param atributo
     * @return
     */
    private String getValue( Node node, String atributo ) {
        if ( ( node != null ) && ( node.getAttributes() != null )
                && ( node.getAttributes().getNamedItem( atributo ) != null ) ) {
            return node.getAttributes().getNamedItem( atributo ).getNodeValue();
        }
        return null;
    }
}
