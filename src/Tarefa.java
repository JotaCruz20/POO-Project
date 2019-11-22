import java.util.Calendar;
import java.util.GregorianCalendar;

public class Tarefa {
    protected GregorianCalendar dataInicio;
    protected double duracao;
    protected GregorianCalendar dataFim;
    protected Pessoa pessoaResponsavel;

    public double getTaxaEsforco(){
        return 0;
    }
}