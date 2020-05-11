package Model.DAO;

import Model.Entity.Definicoes;
import java.util.ArrayList;
import java.util.List;
import sql.Banco;

public class TarefasDAO_Model {

    private static String nomeRobo = "Zac";
    
    public static List<Tarefa_DAO> getTarefasComStatus(int status){
        List<Tarefa_DAO> tarefas = new ArrayList<>();
        
        String sql;
        sql = "Select tarefas.id from tarefas "
                + "Inner join membros "
                + "on membros.id = tarefas.membro_id "
                + "where membros.nome = '" + nomeRobo + "' and tarefas.status = " + status;

        Banco banco = new Banco(Definicoes.getPathCfgMySql());
        if(banco.testConnection()){
            ArrayList<String[]> recordSet = banco.select(sql);
            for (String[] rec : recordSet) {
                tarefas.add(new Tarefa_DAO(Integer.valueOf(rec[0])));
            }
        }
        
        return tarefas;
    }
}
