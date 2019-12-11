import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * @param "Classe Tarefa"
 */

public class Tarefa implements Serializable {
    protected GregorianCalendar dataInicio;
    protected double duracao;
    protected GregorianCalendar dataFim;
    protected Pessoa pessoaResponsavel;
    protected double perConclusao;

    public double getTaxaEsforco(){ return 0; }

    public void atribuirPessoa(Pessoa pessoa){
        this.pessoaResponsavel=pessoa;
    }

    public void setPerConclusao(double perConclusao){
        this.perConclusao=perConclusao;
    }

    public double getPerConclusao() {
        return perConclusao;
    }

    public double getDuracao() {
        return duracao;
    }

    public GregorianCalendar getDataInicio() {
        return dataInicio;
    }

    @Override
    public String toString() {
        if(pessoaResponsavel!=null) {
            return "Pessoa Responsavel: " + pessoaResponsavel.nome;
        }
        else{
            return "Sem Pessoa Responsavel";
        }
    }
}