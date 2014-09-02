package com.lasalle.perguntasenad.view.activity;

import java.io.IOException;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lasalle.perguntasenad.R;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadDataBaseHelper;
import com.lasalle.perguntasenad.presenter.SelecaoCursoPresenter;
import com.lasalle.perguntasenad.view.SelecaoCursoView;

/**
 * COntrola tela de seleção de curso.
 * 
 * @author roberto.sousa
 */
@ContentView( R.layout.activity_selecao_curso )
public class SelecaoCursoActivity extends RoboActivity implements SelecaoCursoView {

    private static final String NOME_SHARED_PREFERENCE = "QuestionsVersion";

    @Inject
    private SelecaoCursoPresenter presenter;

    private SharedPreferences prefs;

    private ProgressDialog dialogoAtualizando;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        PerguntasEnadDataBaseHelper.loadDB( this );
        this.presenter.setup( this );
        this.prefs = this.getSharedPreferences( SelecaoCursoActivity.NOME_SHARED_PREFERENCE, Context.MODE_PRIVATE );
        this.dialogoAtualizando = new ProgressDialog( this );
        this.dialogoAtualizando.setTitle( this.getString( R.string.atualizando ) );
        this.dialogoAtualizando.setMessage( this.getString( R.string.text_progressBar ) );
    }

    @Override
    protected void onPostCreate( Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );
        this.verificaAtualizacaoQuestoes();
    }

    public void verificaAtualizacaoQuestoes() {
        try {
            this.presenter.verificaBancoDeQuestoes(
                this.prefs.getString( SelecaoCursoActivity.NOME_SHARED_PREFERENCE, null ),
                this.getAssets().open( "questoes.xml" ) );
        } catch ( Exception e ) {
            Log.e( "Error Reading assets", e.getClass().getName() );
        }
    }

    @Override
    public void exibirDialogoUpdate() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                SelecaoCursoActivity.this.dialogoAtualizando.show();
            };

            @Override
            protected void onPostExecute( Void result ) {
                SelecaoCursoActivity.this.dialogoAtualizando.hide();
            };

            @Override
            protected Void doInBackground( Void... params ) {
                try {
                    SelecaoCursoActivity.this.presenter.atualizaQuestoes( SelecaoCursoActivity.this.getAssets().open(
                        "questoes.xml" ) );
                } catch ( IOException e ) {
                    Log.e( SelecaoCursoActivity.this.getClass().getName(), "Erro ao atualizar questões" );
                }
                return null;
            };

        }.execute();

    }

    @Override
    public void carregaListaCursos() {
        Toast.makeText( this, "caga", Toast.LENGTH_LONG ).show();

    }

    @Override
    public void atualizaUltimaVersao( String versao ) {
        this.prefs.edit().putString( SelecaoCursoActivity.NOME_SHARED_PREFERENCE, versao ).commit();
    }

}