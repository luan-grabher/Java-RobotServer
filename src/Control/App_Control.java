package Control;

import Model.Entity.Definicoes;
import View.Log_View;
import java.io.PrintWriter;
import java.io.StringWriter;
import sql.Banco;

public class App_Control {

    private final int tempoEspera = 60;
    private final int tempoEsperaBanco = 5;
    public static String mysqlPath = Definicoes.getPathCfgMySql();

    public void run() {
        try {
            //Verifica Banco de dados
            Banco banco = new Banco(App_Control.mysqlPath);
            if (banco.testConnection()) {
                //Controla Tarefas Feitas
                Thread feitas = new Thread(new TarefasFeitas_Control());
                feitas.run();
                //Controla Tarefas A Fazer
                Thread fazer = new Thread(new TarefasFazer_Control());
                fazer.run();
                //Controla Tarefas Fazendo
                Thread fazendo = new Thread(new TarefasFazendo_Control());
                fazendo.run();

                //Repete execução depois do tempo de espera
                //Mata processos do usuário zac
                banco.query("kill user zac");
                esperar(tempoEspera);
                //run();
            } else {
                Log_View.render("Erro ao conectar ao banco de dados!");

                //Mata processos do usuário zac
                banco.query("kill user zac");
                esperar(tempoEsperaBanco);
                //run();
            }

        } catch (Exception e) {
            Log_View.render("Erro no servidor: " + e + "\n" + getStackTraceString(e));
        }
    }

    public static String getStackTraceString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (Exception e) {
        }
    }
}
