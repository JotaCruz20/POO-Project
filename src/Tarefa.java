import java.util.GregorianCalendar;
import java.util.Calendar;

public class Tarefa {
    protected GregorianCalendar dataInicio;
    protected double duracao;
    protected GregorianCalendar dataFim;
    protected Pessoa pessoaResponsavel;

    public double getTaxaEsforco(){ return 0; }

}