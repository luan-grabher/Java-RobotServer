package Model.Entity;

public class Definicoes {

    //Geral
    private static String pathCfgMySql = "\\\\zac\\robos\\tarefas\\arquivos\\mysql.cfg";
    private static String pathRobos = "\\\\zac\\Robos\\Tarefas\\";

    //Render
    private static String pathArquivoLog = "\\\\zac\\ZAC WEB\\zac\\logRobo.txt";
    private static String pathArquivoUltimaAtualizacao = "\\\\zac\\robos\\Servidor\\ultima_att.txt";

    //Tarefas a Fazer
    private static String pathArquivoParametros = "//zac/Robos/Tarefas/parametros.cfg";
    private static String pathArquivoPublicFucntions = System.getenv("APPDATA") + "\\Microsoft\\Excel\\XLSTART\\PUBLIC_FUNCTIONS.xlsb";

    //Tarefas Feitas
    private static String pathRetornosTarefas = "//zac/Robos/Retornos de Tarefas";
    private static String email_user = "contabil.moresco@gmail.com";
    private static String email_senha = "q1W@e3R$";

    public static String getPathCfgMySql() {
        return pathCfgMySql;
    }

    public static void setPathCfgMySql(String pathCfgMySql) {
        Definicoes.pathCfgMySql = pathCfgMySql;
    }

    public static String getPathArquivoParametros() {
        return pathArquivoParametros;
    }

    public static void setPathArquivoParametros(String pathArquivoParametros) {
        Definicoes.pathArquivoParametros = pathArquivoParametros;
    }

    public static String getPathArquivoPublicFucntions() {
        return pathArquivoPublicFucntions;
    }

    public static void setPathArquivoPublicFucntions(String pathArquivoPublicFucntions) {
        Definicoes.pathArquivoPublicFucntions = pathArquivoPublicFucntions;
    }

    public static String getPathRetornosTarefas() {
        return pathRetornosTarefas;
    }

    public static void setPathRetornosTarefas(String pathRetornosTarefas) {
        Definicoes.pathRetornosTarefas = pathRetornosTarefas;
    }

    public static String getEmail_user() {
        return email_user;
    }

    public static void setEmail_user(String email_user) {
        Definicoes.email_user = email_user;
    }

    public static String getEmail_senha() {
        return email_senha;
    }

    public static void setEmail_senha(String email_senha) {
        Definicoes.email_senha = email_senha;
    }

    public static String getPathRobos() {
        return pathRobos;
    }

    public static void setPathRobos(String pathRobos) {
        Definicoes.pathRobos = pathRobos;
    }

    public static String getPathArquivoLog() {
        return pathArquivoLog;
    }

    public static void setPathArquivoLog(String pathArquivoLog) {
        Definicoes.pathArquivoLog = pathArquivoLog;
    }

    public static String getPathArquivoUltimaAtualizacao() {
        return pathArquivoUltimaAtualizacao;
    }

    public static void setPathArquivoUltimaAtualizacao(String pathArquivoUltimaAtualizacao) {
        Definicoes.pathArquivoUltimaAtualizacao = pathArquivoUltimaAtualizacao;
    }
     
}
