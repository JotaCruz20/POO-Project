import java.util.ArrayList;
import java.util.Calendar;

public class Mestre extends Formando {

    public Mestre(String nome, String email, int diaFim, int mesFim, int anoFim){
        super(nome,email,diaFim,mesFim,anoFim);
        docentes = null;
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