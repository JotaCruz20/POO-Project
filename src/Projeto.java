import java.util.ArrayList;
import java.util.Scanner;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Projeto {
    private String nome;
    private String acron;
    private GregorianCalendar dataInicio;
    private GregorianCalendar duracao;
    private GregorianCalendar dataFim;
    private int numTarefas;
    private ArrayList<Tarefa> tarefas;
    private Pessoa investigadrPrincipal;
    private ArrayList<Pessoa> Docentes;
    private ArrayList<Pessoa> Bolseiros;

    public Pessoa procuraPessoa(String nome){
        int count=0;
        while(count!=Docentes.size() && !Docentes.get(count).nome.equals(nome)){
            count++;
            if(Docentes.get(count).nome.equals(nome)){
                return Docentes.get(count);
            }
        }
        count=0;
        while(count!=Bolseiros.size() && !Bolseiros.get(count).nome.equals(nome)){
            count++;
            if(Bolseiros.get(count).nome.equals(nome)){
                return Bolseiros.get(count);
            }
        }
        return null;
    }

    public void listarTarefa(){
        for (int i = 0; i <tarefas.size() ; i++) {
            System.out.println(tarefas.get(i));
        }
    }

    /*public void criaTarefa(Tarefa tarefa){
        String datai,nome_tarefa,nome_pessoa;
        String[] keep_data;
        long duracao;
        int dia,mes,ano;
        Pessoa pessoa;
        Tarefa tarefa;
        GregorianCalendar data;
        Scanner obj=new Scanner(System.in);
        System.out.println("Indique o nome do docente/bolseiro que vai fazer a tarefa: ");
        nome_pessoa=obj.nextLine();
        pessoa=procuraPessoa(nome_pessoa);
        if(pessoa==null){
            System.out.println("Pessoa não existe no sistema");
        }
        else {
            System.out.println("Indique a data de inicio da tarefa: ");
            datai = obj.nextLine();
            keep_data=datai.split("/");
            dia=Integer.parseInt(keep_data[0]);
            mes=Integer.parseInt(keep_data[1]);
            ano=Integer.parseInt(keep_data[2]);
            if(verificaData(dia,mes,ano)) {
                data = new GregorianCalendar(ano, mes, dia);
                System.out.println("Indique a duração estimada da tarefa: ");
                duracao = obj.nextLong();
                System.out.println("Indique o tipo de Tarefa que pretende adicionar.");
                nome_tarefa = obj.nextLine();
                if (nome_tarefa.equals("Desenvolvimento")) {
                    tarefa = new Desenvolvimento(data, duracao, pessoa);
                    tarefas.add(tarefa);
                } else if (nome_tarefa.equals("Design")) {
                    tarefa = new Design(data, duracao, pessoa);
                    tarefas.add(tarefa);
                } else if (nome_tarefa.equals("Documentacao")) {
                    tarefa = new Documentacao(data, duracao, pessoa);
                    tarefas.add(tarefa);
                } else {
                    System.out.println("Tipo de tarefa não existe");
                }
            }
            else{
                System.out.println("Data inválida");
            }
        }
    }*/

    public void eliminaTarefa(Tarefa tarefa){

    }

    public void atribuirTarefa(Tarefa tarefa,Pessoa pessoa){

    }

    public void atualizaConclusao(Tarefa tarefa, double perConclusao){

    }

    public void listaTarefasIniciadas(){

    }

    public void listaNaoTarefasIniciadas(){

    }

    /*public double custo(){
    }*/

    public void terminaProjeto(){
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void atualizaTarefa(Tarefa tarefa){

    }
}