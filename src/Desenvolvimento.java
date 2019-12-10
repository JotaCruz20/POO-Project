import java.util.GregorianCalendar;

public class Desenvolvimento extends Tarefa {

    public Desenvolvimento(GregorianCalendar dataInicio, double duracao, Pessoa pessoaResponsavel){
        this.dataInicio=dataInicio;
        this.duracao=duracao;
        this.pessoaResponsavel=pessoaResponsavel;
        this.perConclusao=0;
    }

    public Desenvolvimento(GregorianCalendar dataInicio, double duracao){
        this.dataInicio=dataInicio;
        this.duracao=duracao;
        this.perConclusao=0;
    }

    public double getTaxaEsforco(){
        return 1;
    }

    @Override
    public String toString() {
        return super.toString()+" Tipo Tarefa: Desenvolvimento"+" Percentagem de Conclusão: "+getPerConclusao();
    }

}