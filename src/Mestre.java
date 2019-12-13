import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Mestre extends Formando implements Serializable {

    public Mestre(String nome, String email, GregorianCalendar dataInicio, GregorianCalendar dataFim){
        super(nome,email,dataInicio,dataFim);
        this.docentes = new ArrayList<>();
        this.tarefas=new ArrayList<>();
    }

    public double getCusto(){
        //Mestre: 800/mês
        return (dataFim.get(Calendar.MONTH)+ 12*(dataFim.get(Calendar.YEAR)- dataFim.get(Calendar.YEAR)-1)+(12-dataInicio.get(Calendar.MONTH)+1))*800;
    }

    public void adicionaDocente(Docente docente){
        docentes.add(docente);
    }

    public void adicionaProjeto (Projeto projeto){
        this.projeto = projeto;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}