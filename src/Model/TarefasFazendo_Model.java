package Model;

import Model.DAO.Tarefa_DAO;
import Model.Entity.Definicoes;
import java.io.File;
import java.util.List;
import main.Arquivo;

public class TarefasFazendo_Model {

    public static void arrastarTarefasTerminadas(List<Tarefa_DAO> tarefas) {
        //Percorre tarefas
        for (Tarefa_DAO tarefa : tarefas) {
            //Se robo não estiver aberto
            if (!roboEstaAberto(tarefa)) {
                //Se não existir um arquivo de retorno com o id da tarefa
                if(!existeArquivoRetorno(tarefa)){
                    //Cria arquivo de retorno com ID da tarefa e mensagem que o robô não retornou nada
                    Arquivo.salvar(Definicoes.getPathRetornosTarefas(), tarefa.getId() + ".html", "Sem retorno para: " +  tarefa.getNome() + "§O robô "+ tarefa.getNome() + " terminou sua execução e não retornou nada.");
                }
            }
        }
    }
    
    public static boolean existeArquivoRetorno(Tarefa_DAO tarefa){
        File arquivo = new File(Definicoes.getPathRetornosTarefas() + "/" + tarefa.getId() + ".html");
        return arquivo.exists();
    }

    public static boolean roboEstaAberto(Tarefa_DAO tarefa) {
        String pathRobo = tarefa.getParametros().get("tarefa").getString();
        String caminhoArquivo = Definicoes.getPathRobos() + pathRobo.replace("/", "\\");

        return Arquivo.aberto(new File(caminhoArquivo));
    }
}
