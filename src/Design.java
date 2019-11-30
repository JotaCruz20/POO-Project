import java.util.GregorianCalendar;

public class Design extends Tarefa {

    public Design(GregorianCalendar dataInicio, double duracao, Pessoa pessoaResponsavel){
        this.dataInicio=dataInicio;
        this.duracao=duracao;
        this.pessoaResponsavel=pessoaResponsavel;
    }

    public double getTaxaEsforco(){
        return 0;
    }

    @Override
    public String toString() {
        return super.toString()+" Tipo Tarefa: Design";
    }
}