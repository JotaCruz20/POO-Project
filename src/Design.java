import java.util.Calendar;
import java.util.GregorianCalendar;

public class Design extends Tarefa {
    public Design(GregorianCalendar dataInico, long duracao, Pessoa pessoaResponsavel){
        this.dataInicio=dataInico;
        this.duracao=duracao;
        this.pessoaResponsavel=pessoaResponsavel;
    }
    public double getTaxaEsforco(){

    }
}