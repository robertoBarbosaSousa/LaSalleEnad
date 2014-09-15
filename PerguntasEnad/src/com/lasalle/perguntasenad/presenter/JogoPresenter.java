package com.lasalle.perguntasenad.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.lasalle.perguntasenad.model.db.Disciplina;
import com.lasalle.perguntasenad.model.db.Opcao;
import com.lasalle.perguntasenad.model.db.Pergunta;
import com.lasalle.perguntasenad.model.db.Progresso;
import com.lasalle.perguntasenad.model.db.DAO.OpcaoDAO;
import com.lasalle.perguntasenad.model.db.DAO.PerguntaDAO;
import com.lasalle.perguntasenad.model.db.DAO.ProgressoDAO;
import com.lasalle.perguntasenad.model.db.contants.NivelDificuldade;
import com.lasalle.perguntasenad.presenter.DTO.PerguntaDTO;
import com.lasalle.perguntasenad.view.JogoView;

/**
 * Controla as regras de negócio da tela.
 * 
 * @author roberto.sousa
 */
public class JogoPresenter {

    private static final int PERCENTUAL_QUESTIONS = 100;

    private JogoView view;

    private List<PerguntaDTO> perguntas;

    private PerguntaDAO perguntaDAO;

    private OpcaoDAO opcaoDAO;

    private Integer perguntaAtual;

    private Opcao ultimaOpcaoSelecionada;

    private ProgressoDAO progressoDAO;

    public void setup( JogoView view ) {
        this.view = view;
        this.perguntaDAO = new PerguntaDAO();
        this.opcaoDAO = new OpcaoDAO();
        this.progressoDAO = new ProgressoDAO();
    }

    public void iniciaJogo( NivelDificuldade nivelEscolhido, int qtdeperguntas, Object[] disciplinasSelecionadas ) {
        this.carregaListaPergntas( nivelEscolhido, qtdeperguntas, disciplinasSelecionadas );
        this.mostrarProximaPergunta();
        this.view.iniciarTempo();
    }

    /**
     * Mostra na tela a proxima pergunta.
     */
    public void mostrarProximaPergunta() {
        this.ultimaOpcaoSelecionada = null;
        this.perguntaAtual++;
        if ( !this.acabouJogo() ) {
            Pergunta perguntaAtual = this.perguntas.get( this.perguntaAtual ).getPergunta();
            this.view.mostraEnunciado( perguntaAtual.getEnunciado() );
            this.view.limparOpcoes();
            this.monstrarOpcoes( perguntaAtual.getId() );
        } else {
            this.finalizaJogo();
        }
    }

    /**
     * Monta informações do fim do jogo.
     */
    private void finalizaJogo() {
        int corretas = 0;
        for ( PerguntaDTO perg : this.perguntas ) {
            if ( perg.isCorreto() ) {
                corretas++;
            }
        }
        Double percentual = this.getPercentualAcerto( corretas );
        this.progressoDAO.insert( new Progresso( percentual ) );

        this.view.fimJogo( this.perguntas.size(), corretas, percentual );
    }

    /**
     * Calcula o percentual de acerto.
     * 
     * @return
     */
    private double getPercentualAcerto( int corretas ) {
        Double percentual = ( ( (double) corretas * (double) JogoPresenter.PERCENTUAL_QUESTIONS ) / this.perguntas
                .size() );
        return percentual;
    }

    /**
     * Mostra as opções das perguntas.
     * 
     * @param idPergunta
     */
    private void monstrarOpcoes( long idPergunta ) {
        Set<Opcao> fromDB = this.opcaoDAO.getOpcoes( idPergunta );
        List<Opcao> result = new ArrayList<Opcao>( fromDB );
        Collections.shuffle( result );
        for ( Opcao opcao : result ) {
            this.view.addOpcao( opcao );
        }
    }

    /**
     * Verifica se acabou o jogo.
     * 
     * @return
     */
    private boolean acabouJogo() {
        if ( this.temPerguntasPendentes() ) {
            // Tenta recuperar a proxima.
            if ( this.perguntaAtual < this.perguntas.size() ) {
                for ( int i = this.perguntaAtual; i <= ( this.perguntas.size() - 1 ); i++ ) {
                    if ( !this.perguntas.get( i ).isRespondido() ) {
                        this.perguntaAtual = i;
                        return false;
                    }
                }
            }

            // Recupera as puladas.
            int count = 0;
            this.perguntaAtual = null;
            for ( PerguntaDTO pergunta : this.perguntas ) {
                if ( !pergunta.isRespondido() ) {
                    this.perguntaAtual = count;
                    return false;
                }
                count++;
            }
        }
        return true;
    }

    private boolean temPerguntasPendentes() {
        for ( PerguntaDTO perg : this.perguntas ) {
            if ( !perg.isRespondido() ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Carrega a lista de perguntas.
     * 
     * @param disciplinasSelecionadas
     * @para qtdeperguntas
     * @param nivelEscolhid
     */
    private void carregaListaPergntas( NivelDificuldade nivelEscolhido, int qtdeperguntas,
                                       Object[] disciplinasSelecionadas ) {
        this.perguntaAtual = -1;
        String res = "";
        for ( Object obj : disciplinasSelecionadas ) {
            if ( obj instanceof Disciplina ) {
                res += "'" + ( (Disciplina) obj ).getId() + "',";
            }
        }
        Set<Pergunta> fromDb = this.perguntaDAO.listPerguntas( nivelEscolhido, res.substring( 0, res.length() - 1 ) );
        this.montaListaDasPerguntas( qtdeperguntas, fromDb );
    }

    /**
     * Monta a lista de perguntas do jogo.
     * 
     * @param qtdeperguntas
     * @param fromDb
     */
    private void montaListaDasPerguntas( int qtdeperguntas, Set<Pergunta> fromDb ) {
        this.perguntas = new ArrayList<PerguntaDTO>();
        for ( Pergunta pergunta : fromDb ) {
            this.perguntas.add( new PerguntaDTO( pergunta ) );
        }

        Collections.shuffle( this.perguntas );

        if ( this.perguntas.size() > qtdeperguntas ) {
            this.perguntas = this.perguntas.subList( 0, qtdeperguntas );
        }
    }

    /**
     * Guarda a ultima opção selecionada.
     * 
     * @param opcao
     */
    public void selecionou( Opcao opcao ) {
        this.ultimaOpcaoSelecionada = opcao;
    }

    /**
     * Passa para a proxima questão.
     */
    public void pular() {
        this.mostrarProximaPergunta();
    }

    public void confirma() {
        if ( this.ultimaOpcaoSelecionada != null ) {
            if ( this.ultimaOpcaoSelecionada.isRespostaCorreta() ) {
                this.view.mostraFeedbackCorreto();
            } else {
                this.view.mostraFeedbackErrado();
            }
            this.perguntas.get( this.perguntaAtual ).setResposta( this.ultimaOpcaoSelecionada );
            this.mostrarProximaPergunta();
        } else {
            this.view.selecioneResposta();
        }
    }

}
