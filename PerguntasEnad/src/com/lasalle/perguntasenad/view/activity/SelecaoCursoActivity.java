package com.lasalle.perguntasenad.view.activity;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lasalle.perguntasenad.R;
import com.lasalle.perguntasenad.presenter.SelecaoCursoPresenter;
import com.lasalle.perguntasenad.view.SelecaoCursoView;

/**
 * COntrola tela de seleçã de curso.
 * 
 * @author roberto.sousa
 */
@ContentView( R.layout.activity_selecao_curso )
public class SelecaoCursoActivity extends RoboActivity implements SelecaoCursoView {

    private static final String NOME_SHARED_PREFERENCE = "QuestionsVersion";

    @Inject
    private SelecaoCursoPresenter presenter;

    SharedPreferences prefs;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        this.presenter.setup( this );
        this.prefs = this.getSharedPreferences( SelecaoCursoActivity.NOME_SHARED_PREFERENCE, Context.MODE_PRIVATE );
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
        Toast.makeText( this, "atualiza", Toast.LENGTH_LONG ).show();
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