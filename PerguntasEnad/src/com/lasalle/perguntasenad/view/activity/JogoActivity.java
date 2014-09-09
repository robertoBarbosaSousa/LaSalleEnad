package com.lasalle.perguntasenad.view.activity;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.os.Bundle;

import com.lasalle.perguntasenad.R;
import com.lasalle.perguntasenad.presenter.JogoPresenter;
import com.lasalle.perguntasenad.view.JogoView;

@ContentView( R.layout.activity_jogo )
public class JogoActivity extends RoboActivity implements JogoView {

    @Inject
    private JogoPresenter presenter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        this.presenter.setup( this );
    }

}
