import java.io.Serializable;
import java.util.ArrayList;

public class Docente extends Pessoa implements Serializable {
    private int numMecanografico;
    private String areaInvestigacao;
    private ArrayList<Formando> formandos;
    private ArrayList<Projeto> projetos;

    public Docente(String nome, String email,int numMecanografico, String areaInvestigacao){
        super(nome,email);
        this.tarefas=new ArrayList<>();
        this.numMecanografico = numMecanografico;
        this.areaInvestigacao = areaInvestigacao;
        formandos = new ArrayList<>();
        projetos = new ArrayList<>();

    }

    public void addTarefa(Tarefa tarefa){
        this.tarefas.add(tarefa);
    }

    public void adicionaFormando(Formando formando){
        this.formandos.add(formando);
    }

    public void imprimeFormandos(){
        for(int i=0;i<formandos.size();i++){
            System.out.println(formandos.get(i));
        }
    }

    @Override
    public double duracaoTarefas() {
        return 0;
    }

    public void adicionaProjeto(Projeto projeto){
        projetos.add(projeto);
    }

    public double getCusto(){
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() + "\nNumero Mecanográfico: "+ numMecanografico+"\nÁrea de Investigação: "+areaInvestigacao;
    }
}