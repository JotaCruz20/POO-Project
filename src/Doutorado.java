import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Doutorado extends Bolseiro implements Serializable {

    public Doutorado(String nome, String email, GregorianCalendar dataInicio, GregorianCalendar dataFim){
        super(nome,email,dataInicio,dataFim);
        this.tarefas=new ArrayList<>();
    }

    public void addTarefa(Tarefa tarefa){
        this.tarefas.add(tarefa);
    }

    public double getCusto(){
        //Doutorados: 1000 mês
        return (dataFim.get(Calendar.MONTH)+ 12*(dataFim.get(Calendar.YEAR)- dataFim.get(Calendar.YEAR)-1)+(12-dataInicio.get(Calendar.MONTH)+1))*1000;
    }

    public void adicionaProjeto (Projeto projeto){
        this.projeto = projeto;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}