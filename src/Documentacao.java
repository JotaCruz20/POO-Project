import java.util.Calendar;
import java.util.GregorianCalendar;


public class Documentacao extends Tarefa {
    public Documentacao(GregorianCalendar dataInicio, long duracao, Pessoa pessoaResponsavel){
        this.dataInicio=dataInicio;
        this.duracao=duracao;
        this.pessoaResponsavel=pessoaResponsavel;
    }

    public double getTaxaEsforco(){
    }
}