import java.util.GregorianCalendar;

public class Design extends Tarefa {

    public Design(GregorianCalendar dataInicio, double duracao, Pessoa pessoaResponsavel){
        this.dataInicio=dataInicio;
        this.duracao=duracao;
        this.pessoaResponsavel=pessoaResponsavel;
        this.perConclusao=0;
    }

    public Design(GregorianCalendar dataInicio, double duracao){
        this.dataInicio=dataInicio;
        this.duracao=duracao;
        this.perConclusao=0;
    }

    public double getTaxaEsforco(){
        return 0.5;
    }

    @Override
    public String toString() {
        return super.toString()+" Tipo Tarefa: Design"+" Percentagem de Conclusão: "+getPerConclusao();
    }
}