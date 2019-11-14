import java.util.ArrayList;

abstract class Pessoa {
    protected String nome;
    protected String email;
    protected double esforco;
    protected ArrayList<Projeto> projetos;
    protected ArrayList<Tarefa> tarefas;

    public double getCusto(){

    }

    @Override
    public String toString() {
        return super.toString();
    }
}