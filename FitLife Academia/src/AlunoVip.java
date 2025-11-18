import Planos.Plano;

public class AlunoVip extends Aluno {

    public AlunoVip(String nome, String cpf, Plano plano) {
        super(nome, cpf, plano);

        if (!plano.eVIP()) {
            throw new IllegalArgumentException("O plano informado não é VIP para o AlunoVip.");
        }
    }

    @Override
    public String getTipo() {
        return "ALUNO VIP";
    }

    @Override
    public boolean isVip() {
        return true;
    }

}


