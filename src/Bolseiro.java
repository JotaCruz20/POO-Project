import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

abstract class Bolseiro extends Pessoa implements Serializable {
    protected GregorianCalendar dataInicio;
    protected GregorianCalendar dataFim;
    protected Projeto projeto;

    public Bolseiro(String nome, String email, int diaFim, int mesFim, int anoFim){super(nome,email);}
       /* super(nome,email);
        boolean invalido;
        Scanner sc = new Scanner(System.in);
        GregorianCalendar dataInicioLocal = new GregorianCalendar();
        GregorianCalendar dataFimLocal = new GregorianCalendar(anoFim,mesFim,diaFim);
        while(dataFimLocal.compareTo(dataInicioLocal)<0) {
            System.out.println("Data inválida.Insira novos valores");
            diaFim = sc.nextByte();
            mesFim = sc.nextByte();
            anoFim = sc.nextByte();
            dataFimLocal = new GregorianCalendar(anoFim,mesFim,diaFim);
        }
        this.dataInicio = dataInicioLocal;
        this.dataFim = dataFimLocal;
        this.projeto = null;

    }*/

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

    /*@Override
    public String toString() {
        return super.toString()+""
    }*/
}