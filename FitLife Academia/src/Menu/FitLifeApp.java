package Menu;
import Cadastro.*;
import javax.swing.*;
import java.awt.*;

public class FitLifeApp extends JFrame {
    private CardLayout cards;
    private JPanel container;
    private CadastroAcademico cadastro;
    private Cadastro.Aluno currentAluno;
    private Cadastro.Professor currentProfessor;
    private StudentDashboardPanel studentDashboard;
    private ProfessorDashboardPanel professorDashboard;

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
        studentDashboard = new StudentDashboardPanel(this, cadastro);
        professorDashboard = new ProfessorDashboardPanel(this, cadastro);
         

        // Adiciona ao container
        container.add(login, "login");
        container.add(inscrever, "inscrever");
        container.add(dashboard, "dashboard");
        container.add(alunoForm, "alunoForm");
        container.add(professorForm, "professorForm");
        container.add(alunoLogin, "alunoLogin");
        container.add(planoPanel, "planos");
        container.add(studentDashboard, "studentDashboard");
        container.add(professorDashboard, "professorDashboard");

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

    public void setCurrentProfessor(Cadastro.Professor professor) {
        this.currentProfessor = professor;
    }

    public Cadastro.Professor getCurrentProfessor() {
        return this.currentProfessor;
    }

    public void showStudentDashboard() {
        if (studentDashboard != null) studentDashboard.refresh();
        cards.show(container, "studentDashboard");
    }

    public void showProfessorDashboard() {
        if (professorDashboard != null) professorDashboard.refresh();
        cards.show(container, "professorDashboard");
    }

    public void start() {
        SwingUtilities.invokeLater(() -> new FitLifeApp().setVisible(true));
    }
}
