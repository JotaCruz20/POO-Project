import java.util.GregorianCalendar;


public class Documentacao extends Tarefa {
    public Documentacao(GregorianCalendar dataInicio, double duracao, Pessoa pessoaResponsavel){
        this.dataInicio=dataInicio;
        this.duracao=duracao;
        this.pessoaResponsavel=pessoaResponsavel;
        this.perConclusao=0;
    }

    public Documentacao(GregorianCalendar dataInicio, double duracao){
        this.dataInicio=dataInicio;
        this.duracao=duracao;
        this.perConclusao=0;
    }

    public double getTaxaEsforco(){
        return 0.25;
    }

    @Override
    public String toString() {
        return super.toString()+" Tipo Tarefa: Documentação"+" Percentagem de Conclusão: "+getPerConclusao();
    }
}