package Menu;
import Cadastro.*;
import javax.swing.*;
import java.awt.*;

public class FitLifeApp extends JFrame {
    private CardLayout cards;
    private JPanel container;
    private CadastroAcademico cadastro;
    private Cadastro.Aluno currentAluno;

    public FitLifeApp() {
        super("FitLife");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        cards = new CardLayout();
        container = new JPanel(cards);

        // Telas
        cadastro = CadastroAcademico.getInstance();
        LoginPanel login = new LoginPanel(this, cadastro);
        Inscrever inscrever = new Inscrever(this, cadastro);
        DashboardPanel dashboard = new DashboardPanel(this);
        AlunoFormPanel alunoForm = new AlunoFormPanel(this, cadastro);
        ProfessorFormPanel professorForm = new ProfessorFormPanel(this, cadastro);
        AlunoLogin alunoLogin = new AlunoLogin(this);
        PlanoSelectionPanel planoPanel = new PlanoSelectionPanel(this, cadastro);
         

        // Adiciona ao container
        container.add(login, "login");
        container.add(inscrever, "inscrever");
        container.add(dashboard, "dashboard");
        container.add(alunoForm, "alunoForm");
        container.add(professorForm, "professorForm");
        container.add(alunoLogin, "alunoLogin");
        container.add(planoPanel, "planos");

        add(container);
        showScreen("login");
    }

    public void showScreen(String name) {
        cards.show(container, name);
    }

    public void setCurrentAluno(Cadastro.Aluno aluno) {
        this.currentAluno = aluno;
    }

    public Cadastro.Aluno getCurrentAluno() {
        return this.currentAluno;
    }

    public void start() {
        SwingUtilities.invokeLater(() -> new FitLifeApp().setVisible(true));
    }
}
