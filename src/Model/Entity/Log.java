package Model.Entity;

public class Log {
    
    private String hora;
    private String descricao;

    public Log(String hora, String descricao) {
        this.hora = hora;
        this.descricao = descricao;
    }

    public String getHora() {
        return hora;
    }

    public String getDescricao() {
        return descricao;
    }
}
