import java.io.Serializable;
import java.util.ArrayList;

abstract class Pessoa implements Serializable {
    protected String nome;
    protected String email;
    protected double esforco;
    protected ArrayList<Tarefa> tarefas;

    public Pessoa(String nome, String email){
        this.nome = nome;
        this.email = email;
        this.esforco = 0;
        this.tarefas =null;
    }

    public void toStringTarefas(){
        for (int i=0;i<tarefas.size();i++){
            System.out.println(tarefas.get(i));
        }
    }

    public String getNome() {
        return nome;
    }

    public abstract double duracaoTarefas();

    public double getEsforco() {
        return esforco;
    }

    public abstract double getCusto();

    @Override //CORRIGIR!
    public String toString() {
        return "--"+nome.toUpperCase()+"--\nemail: "+email+"\nTarefas: "+tarefas+"\n";
    }
}