import java.util.GregorianCalendar;
import java.util.Calendar;

public class Desenvolvimento extends Tarefa {

    public Desenvolvimento(GregorianCalendar dataInicio, long duracao, Pessoa pessoaResponsavel){
        this.dataInicio=dataInicio;
        this.duracao=duracao;
        this.pessoaResponsavel=pessoaResponsavel;
    }

    public double getTaxaEsforco(){
        return 0;
    }
}