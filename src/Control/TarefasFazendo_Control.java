package Control;

import Model.DAO.Tarefa_DAO;
import Model.DAO.TarefasDAO_Model;
import Model.TarefasFazendo_Model;
import java.util.List;

public class TarefasFazendo_Control implements Runnable{
    
    @Override
    public void run() {
        //Pega Lista de tarefas que estão fazendo
         List<Tarefa_DAO> tarefas = TarefasDAO_Model.getTarefasComStatus(Tarefa_DAO.STATUS_FAZENDO);
         
         TarefasFazendo_Model.arrastarTarefasTerminadas(tarefas);
    }
    
}
