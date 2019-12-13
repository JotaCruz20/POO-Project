import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

abstract class Bolseiro extends Pessoa implements Serializable {
    protected GregorianCalendar dataInicio;
    protected GregorianCalendar dataFim;
    protected Projeto projeto;

    public Bolseiro(String nome, String email,GregorianCalendar dataInicio,GregorianCalendar dataFim){
        super(nome,email);
        this.dataInicio=dataInicio;
        this.dataFim=dataFim;
        this.tarefas=new ArrayList<>();
    }

    public abstract double getCusto();

    public void adicionaProjeto(Projeto projeto){
        this.projeto = projeto;
    }

    public String getDataFim() {
        return dataInicio.get(Calendar.DAY_OF_MONTH)+" "+ dataInicio.get(Calendar.MONTH)+" "+dataInicio.get(Calendar.YEAR);
    }

    public double duracaoTarefas() {
        double total = 0;
        for (int i = 0; i < tarefas.size(); i++) {
            total += tarefas.get(i).getDuracao();
        }
        return total;
    }
}