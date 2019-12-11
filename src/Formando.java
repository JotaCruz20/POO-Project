import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;

public class Formando extends Bolseiro implements Serializable {
    Projeto projeto;

    public Formando(String nome, String email, int diaFim, int mesFim, int anoFim){
        super(nome,email,diaFim,mesFim,anoFim);
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    @Override
    public double getCusto() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
