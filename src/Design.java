import java.util.Calendar;
import java.util.GregorianCalendar;

public class Design extends Tarefa {

    public Design(GregorianCalendar dataInicio, long duracao, Pessoa pessoaResponsavel){
        this.dataInicio=dataInicio;
        this.duracao=duracao;
        this.pessoaResponsavel=pessoaResponsavel;
    }

    public double getTaxaEsforco(){
    }
}