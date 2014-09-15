package com.lasalle.perguntasenad.model.db.schema;

import android.provider.BaseColumns;

/**
 * Database fields.
 */
public final class PerguntasEnadContract {

    /**
     * Constructor.
     */
    private PerguntasEnadContract() {
    }

    public static final class CursoData implements BaseColumns {

        public static final String TABLE_NAME = "curso";


        public static final String COLUMN_DESCRICAO = "descricao";

    }

    public static final class DisciplinaData implements BaseColumns {

        public static final String TABLE_NAME = "disciplina";

        public static final String FK_COLUMN_CURSO = "curso_fk";

        public static final String COLUMN_DESCRICAO = "descricao";

    }

    public static final class PerguntaData implements BaseColumns {

        public static final String TABLE_NAME = "pergunta";

        public static final String FK_COLUMN_DISCIPLINA = "disciplina_fk";

        public static final String COLUMN_ENUNCIADO = "enunciado";

        public static final String COLUMN_NIVEL_DIFICULDADE = "nivelDificuldade";

    }

    public static final class OpcaoData implements BaseColumns {

        public static final String TABLE_NAME = "opcao";

        public static final String FK_COLUMN_PERGUNTA = "pergunta_fk";

        public static final String COLUMN_DESCRICAO = "descricao";

        public static final String COLUMN_RESPOSTA_CORRETA = "resposta_correta";

    }

    public static final class ProgressoData implements BaseColumns {

        public static final String TABLE_NAME = "progresso";

        public static final String COLUMN_PERCENTUAL = "percentual";

    }
}
