package Control;

import Auxiliar.Parametros;
import Auxiliar.Valor;
import Model.DAO.Tarefa_DAO;
import Model.Entity.Definicoes;
import java.util.ArrayList;
import java.util.List;
import sql.Banco;
import View.Log_View;
import java.io.File;
import java.util.Calendar;
import main.Arquivo;

public class TarefasFazer_Control implements Runnable {

    private List<Tarefa_DAO> tarefas = new ArrayList<>();

    @Override
    public void run() {
        definirListaDeTarefas();

        if (tarefas.size() > 0) {
            for (Tarefa_DAO tarefa : tarefas) {
                Parametros parametros = tarefa.getParametros();

                if (pronto(tarefa)) {
                    //Adiciona id tarefa nos parametros
                    parametros.get("idTarefa").set(new Valor(tarefa.getId()));
                    tarefa.alterarDescricao(parametros.getListAsString());

                    //Coloca inicio tarefa
                    if (definirInicioExecucaoRobo(tarefa)) {
                        //Salva no arquivo de parametros
                        if (Arquivo.salvar(Definicoes.getPathArquivoParametros(), parametros.getListAsString())) {
                            //Abre o robô
                            if (abrirRobo(parametros.get("tarefa").getString())) {
                                //Espera resposta no arquivo parametros.cfg
                                if (esperaRespostaRobo(parametros.getListAsString())) {
                                    //Muda status para fazendo
                                    tarefa.mudarStatus(Tarefa_DAO.STATUS_FAZENDO);

                                    Log_View.render("Executando o robô '" + tarefa.getNome() + "'.");
                                } else {
                                    Log_View.render("O robô '" + tarefa.getNome() + "' não está respondendo às ordens do servidor.");
                                }
                            }
                        } else {
                            Log_View.render("Erro ao criar conexão com o robô.");
                        }
                    } else {
                        Log_View.render("Erro ao definir incio da execução do robô '"
                                + parametros.get("tarefa").getString() + "'");
                    }
                } else {
                    Log_View.render("O robô '" + tarefa.getNome() + "' não está pronto");
                }
            }
        } else {
            Log_View.render("Nenhum robô para executar.");
        }
    }

    private boolean esperaRespostaRobo(String parametrosOriginais) {
        int maxExec = 120; //2 minutos
        int count = 0;
        String retornoRobo = parametrosOriginais;
        while (parametrosOriginais.equals(retornoRobo) && count < maxExec) {
            count++;
            retornoRobo = Arquivo.ler(Definicoes.getPathArquivoParametros());
            App_Control.esperar(1);
        }

        return count <= maxExec;
    }

    private boolean definirInicioExecucaoRobo(Tarefa_DAO tarefa) {
        try {
            Calendar now = Calendar.getInstance();
            tarefa.getParametros().get("inicio_execucao").set(new Valor(now));
            tarefa.alterarDescricao(tarefa.getParametros().getListAsString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean abrirRobo(String pathRobo) {
        String caminhoArquivo = Definicoes.getPathRobos() + pathRobo.replace("/", "\\");
        String caminhoArquivoLc = caminhoArquivo.toLowerCase();

        //VERIFICA SE EXISTE E SE É DE XLSX OU JAR
        if (Selector.Arquivo.verifica(caminhoArquivo, "xlsm", false)
                || Selector.Arquivo.verifica(caminhoArquivo, "jar", false)) {

            //Verifica de é um arquivo XLSM
            if (caminhoArquivoLc.contains(".xlsm")) {
                //Define arquivos
                File roboFile = new File(caminhoArquivo);

                //Verifica se estão abertos
                /*
                *Não adianta colocar Arquivo.aberto, quando compila gera um erro de cannot find symbol
                 */
                if (Arquivo.aberto(roboFile) || Arquivo.aberto(new File(Definicoes.getPathArquivoPublicFucntions()))) {
                    Log_View.render("As funções do robô '" + pathRobo + "' já estão sendo utilizadas, essa tarefa será executada depois.");
                    return false;
                }
            }

            return startRobotFile(caminhoArquivo);
        } else {
            Log_View.render("Robô '" + pathRobo + "' inválido.");
            return false;
        }
    }

    private boolean startRobotFile(String caminhoArquivo) {
        /*try {
            List<String> command = new ArrayList<>();

            if(caminhoArquivo.toLowerCase().contains(".jar")){
                command.add("java");
                command.add("-jar");
            }else if(caminhoArquivo.toLowerCase().contains(".xlsm")){
                command.add("start");
                command.add("excel");
            }
            
            command.add(caminhoArquivo);

            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.start();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }*/
        String pathWithQuot = "\"" + caminhoArquivo + "\"";
        String tipo_app = pathWithQuot.toLowerCase().contains(".jar") ? "java -jar" : "excel";

        String comando = "start " + tipo_app + " " + pathWithQuot;
        //Tenta abrir aplicativo e ao mesmo tempo testa se foi aberto
        if (cmd.cmd.execute_script(comando, cmd.cmd.CMD_PATH_USER) == false) {
            Log_View.render("Problema ao abrir o robô " + pathWithQuot + " por comando.");
            return false;
        } else {
            //Retorna que ocorreu tudo OK
            return true;
        }
    }

    private void definirListaDeTarefas() {
        //PEGA LISTA DE PROGRAMAS A FAZER
        String sql;
        sql = "Select tarefas.id from tarefas "
                + "Inner join membros "
                + "on membros.id = tarefas.membro_id "
                + "where membros.nome = 'Zac' and tarefas.status =1";

        Banco banco = new Banco(App_Control.mysqlPath);
        if (banco.testConnection()) {
            ArrayList<String[]> recordSet = banco.select(sql);

            for (int i = 0; i < recordSet.size(); i++) {
                Object[] rec = recordSet.get(i);
                tarefas.add(new Tarefa_DAO(Integer.valueOf(rec[0].toString())));
            }
        }
    }

    private boolean pronto(Tarefa_DAO tarefa) {
        //Função redundante para posteriormente se precisar adicionar verificação de "Quando" executar ou outras coisas
        return true;
    }
}
