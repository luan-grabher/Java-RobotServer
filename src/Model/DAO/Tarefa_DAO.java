package Model.DAO;

import Control.App_Control;
import Auxiliar.Parametros;
import java.util.ArrayList;
import sql.Banco;

public class Tarefa_DAO {
    
    public static int STATUS_AFAZER = 1;
    public static int STATUS_FAZENDO = 2;
    public static int STATUS_FEITO = 3;

    private final int id;
    private String nome;
    private Parametros parametros = null;

    //INICIO
    public Tarefa_DAO(int idTarefa) {
        this.id = idTarefa;
        copiaDoBanco();
    }

    private void copiaDoBanco() {
        //conecta no banco
        Banco banco = new Banco(App_Control.mysqlPath);
        if (banco.testConnection()) {
            //Pega tarefa
            String sql = "SELECT descricao, nome FROM banco.tarefas where id = " + id;
            ArrayList<String[]> tarefaDb = banco.select(sql);
            String parametrosString = tarefaDb.get(0)[0];

            parametros = new Parametros(parametrosString);
            this.nome = tarefaDb.get(0)[1];
        }
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public Parametros getParametros() {
        return parametros;
    }

    /**
    * Altera status no banco de dados
    * @param status Use as variaveis estáticas de STATUS
    */
    public void mudarStatus(int status) {
        switch (status) {
            case 1:
                dataFinalNula();
                finalizaHoras();
                break;
            case 2:
                dataFinalNula();
                finalizaHoras();
                novaHora();
                break;
            case 3:
                finalizaHoras();
                terminaTarefa();
                break;
            default:
                break;
        }

        alterarStatus(status);

        updateServerValue();
    }
    
    private void updateServerValue(){
        String sql;
        sql = "update random set numero = (FLOOR(RAND()*(99999-1+1))+1) where id= 1;";
        Banco banco = new Banco(App_Control.mysqlPath);
        banco.query(sql);
    }

    //UPDATES
    private void dataFinalNula() {
        String sql;
        sql = "update tarefas set enddate = Null where id= " + id;

        Banco banco = new Banco(App_Control.mysqlPath);
        banco.query(sql);
    }

    private void finalizaHoras() {
        String sql;
        sql = "update horas set endd = CURRENT_TIMESTAMP() where tarefa_id= " + id + " and endd is null";

        Banco banco = new Banco(App_Control.mysqlPath);
        banco.query(sql);
    }

    private void terminaTarefa() {
        String sql;
        sql = "update tarefas set enddate = CURRENT_TIMESTAMP() where id= " + id;

        Banco banco = new Banco(App_Control.mysqlPath);
        banco.query(sql);
    }

    private void alterarStatus(long status) {
        String sql;
        sql = "update tarefas set status = " + status + " where id = " + id;

        Banco banco = new Banco(App_Control.mysqlPath);
        banco.query(sql);
    }

    public void alterarDescricao(String desc) {
        String sql;
        sql = "update tarefas set descricao = '" + desc + "' where id = " + id;

        Banco banco = new Banco(App_Control.mysqlPath);
        banco.query(sql);
    }

    //INSERT
    private void novaHora() {
        finalizaHoras();

        String sql;
        sql = "insert into horas values(null,26," + id + ",CURRENT_TIMESTAMP(),null);";

        Banco banco = new Banco(App_Control.mysqlPath);
        banco.query(sql);
    }

    //SELECTS
    public String getDescricao() {
        //PEGA LISTA DE PROGRAMAS A FAZER
        String sql;
        sql = "Select descricao from tarefas where tarefas.id = " + id;

        Banco banco = new Banco(App_Control.mysqlPath);
        return banco.select(sql).get(0)[0];
    }

}
