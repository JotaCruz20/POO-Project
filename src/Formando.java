import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Formando extends Bolseiro implements Serializable {
    Projeto projeto;
    ArrayList<Pessoa> docentes=new ArrayList<>();

    public Formando(String nome, String email, GregorianCalendar dataInicio, GregorianCalendar dataFim){
        super(nome,email,dataInicio,dataFim);
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void addTarefa(Tarefa tarefa){
        this.tarefas.add(tarefa);
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    @Override
    public double getCusto() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
