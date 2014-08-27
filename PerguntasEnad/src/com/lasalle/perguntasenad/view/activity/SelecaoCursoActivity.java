package com.lasalle.perguntasenad.view.activity;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.os.Bundle;

import com.lasalle.perguntasenad.R;
import com.lasalle.perguntasenad.model.db.schema.PerguntasEnadDataBaseHelper;
import com.lasalle.perguntasenad.presenter.SelecaoCursoPresenter;
import com.lasalle.perguntasenad.view.SelecaoCursoView;

/**
 * COntrola tela de seleçã de curso.
 * 
 * @author roberto.sousa
 */
@ContentView( R.layout.activity_selecao_curso )
public class SelecaoCursoActivity extends RoboActivity implements SelecaoCursoView {

    @Inject
    private SelecaoCursoPresenter presenter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        this.presenter.setup( this );
        PerguntasEnadDataBaseHelper.loadDB( this );
    }

}