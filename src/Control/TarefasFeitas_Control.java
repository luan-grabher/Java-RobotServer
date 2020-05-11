package Control;

import Model.DAO.Tarefa_DAO;
import Model.Entity.Definicoes;
import java.io.File;
import View.Log_View;
import main.Arquivo;
import main.Gmail;

public class TarefasFeitas_Control implements Runnable {

    private String[] tarefas;

    @Override
    public void run() {
        //Pega lista de feitas
        File caminho = new File(Definicoes.getPathRetornosTarefas());
        tarefas = caminho.list();

        if (tarefas.length > 0) {
            //Percorre todas tarefas
            for (String nomeArquivoTarefa : tarefas) {
                String tarefaIdString = nomeArquivoTarefa.replaceAll(".html", "");
                try {
                    int tarefaId = Integer.parseInt(tarefaIdString);
                    if (tarefaId > 0) {
                        Tarefa_DAO tarefa = new Tarefa_DAO(tarefaId);
                        //Verifica parametros
                        if (tarefa.getParametros() != null) {
                            //enviar Email
                            if (enviarEmail(tarefa.getParametros().get("tarefa").getString(), tarefa.getParametros().get("email").getString(), nomeArquivoTarefa)) {
                                //Arrastar no sistema
                                tarefa.mudarStatus(Tarefa_DAO.STATUS_FEITO);

                                /*Exclui Arquivo*/
                                if ((new File(Definicoes.getPathRetornosTarefas() + "/" + nomeArquivoTarefa).delete())) {
                                    Log_View.render("Tarefa '" + tarefa.getNome() + "' concluída!");
                                } else {
                                    Log_View.render("Erro ao excluir o arquivo de retorno da tarefa '" + tarefa.getNome() + "'");
                                }
                            }
                        } else {
                            Log_View.render("Tarefa feita '" + tarefaId + "' inválida no banco de dados!");
                        }
                    } else {
                        Log_View.render("Nome do arquivo de retorno '" + tarefaIdString + "' inválido!");
                    }
                } catch (Exception e) {
                }
            }
        } else {
            Log_View.render("Nenhuma tarefa concluída.");
        }
    }

    private boolean enviarEmail(String nomeTarefa, String enderecoEmail, String nomeArquivoTarefa) {
        boolean b = false;
        if (!enderecoEmail.equals("")) {
            //Pegar Assunto e mensagem e-mail
            String[] emailAssuntoMensagem = Arquivo.ler(Definicoes.getPathRetornosTarefas() + "/" + nomeArquivoTarefa).split("§", -1);

            if (emailAssuntoMensagem.length == 2) {
                if (!"".equals(emailAssuntoMensagem[0]) && !"".equals(emailAssuntoMensagem[1])) {
                    Gmail gmail = new Gmail(Definicoes.getEmail_user(), Definicoes.getEmail_senha());
                    if (!gmail.enviaZAC(enderecoEmail, emailAssuntoMensagem[0], emailAssuntoMensagem[1])) {
                        Log_View.render("Erro ao enviar e-mail da tarefa '" + nomeTarefa + "' para " + enderecoEmail);
                    } else {
                        b = true;
                    }
                } else {
                    Log_View.render("No retorno da tarefa '" + nomeTarefa + "' o assunto ou a mensagem está em branco!");
                }
            } else {
                Log_View.render("No retorno da tarefa '" + nomeTarefa + "' deve existir Assunto e Mensagem!");
            }
        } else {
            Log_View.render("No retorno da tarefa '" + nomeTarefa + "' não possui destinatário de e-mail!");
        }

        return b;
    }
}
