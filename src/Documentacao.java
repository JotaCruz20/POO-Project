import java.util.Calendar;
import java.util.GregorianCalendar;


public class Documentacao extends Tarefa {
    public Documentacao(GregorianCalendar dataInico, long duracao, Pessoa pessoaResponsavel){
        this.dataInicio=dataInico;
        this.duracao=duracao;
        this.pessoaResponsavel=pessoaResponsavel;
    }
    public double getTaxaEsforco(){

    }
}