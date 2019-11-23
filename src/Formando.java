import java.text.Normalizer;
import java.util.ArrayList;

public class Formando extends Bolseiro{
    protected ArrayList<Docente> docentes;

    public Formando(String nome, String email, int diaFim, int mesFim, int anoFim){
        super(nome,email,diaFim,mesFim,anoFim);
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
