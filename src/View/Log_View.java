package View;

import Model.Entity.Definicoes;
import Model.Entity.Log;
import Windows.Home;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import main.Arquivo;

public abstract class Log_View implements Runnable{
    
    private static List<Log> logs = new ArrayList<>();
    
    public static void render(String new_log_text){
        //Pega horario
        Calendar hora = Calendar.getInstance();
        //hora.add(Calendar.HOUR_OF_DAY, -1); //remove horario de verão
        
        String horaStr = 
                zeroNaFrente(hora.get(Calendar.HOUR_OF_DAY)) 
                + ":" 
                + zeroNaFrente(hora.get(Calendar.MINUTE))
                + ":"
                + zeroNaFrente(hora.get(Calendar.SECOND))
        ;
        
        //Adiciona na lista
        logs.add(new Log(horaStr, new_log_text));
        
        //Remove linhas além de 20
        while(logs.size() > 20){
            logs.remove(0);
        }
        
        //Montar texto log
        StringBuilder text_log  = new StringBuilder();
        for (int i = logs.size() -1 ; i >= 0; i--) {
            Log log = logs.get(i);
            text_log.append(log.getHora());
            text_log.append(" - ");
            text_log.append(log.getDescricao());
            text_log.append("\n");
        }
        
        //Altera log na janela
        Home.log.setText(text_log.toString());
        
        //Grava no arquivo
        Arquivo.salvar(Definicoes.getPathArquivoLog(),text_log.toString().replaceAll("\n", "<br>"));

        //salva ultimo horario de verificação
        Arquivo.salvar(Definicoes.getPathArquivoUltimaAtualizacao(), horaStr);
    }
    
    private static String zeroNaFrente(int number){
        return (number<10?"0":"") + number;
    }
}
