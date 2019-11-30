import java.util.Calendar;
import java.util.GregorianCalendar;


public class Documentacao extends Tarefa {
    public Documentacao(GregorianCalendar dataInicio, double duracao, Pessoa pessoaResponsavel){
        this.dataInicio=dataInicio;
        this.duracao=duracao;
        this.pessoaResponsavel=pessoaResponsavel;
    }

    public double getTaxaEsforco(){
        return 0;
    }

    @Override
    public String toString() {
        return super.toString()+" Tipo Tarefa: Documentação";
    }
}