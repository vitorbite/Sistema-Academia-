package Menu;
import Cadastro.*;
import javax.swing.*;
import java.awt.*;

public class FitLifeApp extends JFrame {
    private CardLayout cards;
    private JPanel container;
    private CadastroAcademico cadastro;
    private Aluno currentAluno;
    private Professor currentProfessor;
    private StudentDashboardPanel studentDashboard;
    private ProfessorDashboardPanel professorDashboard;

    public FitLifeApp() {
        super("FitLife");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        cards = new CardLayout();
        container = new JPanel(cards);

        // Instância única de cadastro
        cadastro = CadastroAcademico.getInstance();
        
        // Telas
        LoginPanel login = new LoginPanel(this, cadastro);
        Inscrever inscrever = new Inscrever(this, cadastro);
        AlunoFormPanel alunoForm = new AlunoFormPanel(this, cadastro);
        ProfessorFormPanel professorForm = new ProfessorFormPanel(this, cadastro);
        PlanoSelectionPanel planoSelection = new PlanoSelectionPanel(this, cadastro);
        studentDashboard = new StudentDashboardPanel(this, cadastro);
        professorDashboard = new ProfessorDashboardPanel(this, cadastro);
        PagamentoPanel pagamentoPanel = new PagamentoPanel(this, cadastro);

        // Adiciona ao container
        container.add(login, "login");
        container.add(inscrever, "inscrever");
        container.add(alunoForm, "alunoForm");
        container.add(professorForm, "professorForm");
        container.add(planoSelection, "planos");
        container.add(studentDashboard, "studentDashboard");
        container.add(professorDashboard, "professorDashboard");
        container.add(pagamentoPanel, "pagamentos");

        add(container);
        showScreen("login");
    }

    public void showScreen(String name) {
        cards.show(container, name);
    }

    public void setCurrentAluno(Aluno aluno) {
        this.currentAluno = aluno;
    }

    public Aluno getCurrentAluno() {
        return currentAluno;
    }

    public void setCurrentProfessor(Professor professor) {
        this.currentProfessor = professor;
    }

    public Professor getCurrentProfessor() {
        return currentProfessor;
    }

    public void refreshStudentDashboard() {
        if (studentDashboard != null) {
            studentDashboard.refresh();
        }
    }

    public void refreshProfessorDashboard() {
        if (professorDashboard != null) {
            professorDashboard.refresh();
        }
    }

    public void showStudentDashboard() {
        refreshStudentDashboard();
        showScreen("studentDashboard");
    }

    public void showProfessorDashboard() {
        refreshProfessorDashboard();
        showScreen("professorDashboard");
    }

    public void start() {
        SwingUtilities.invokeLater(() -> new FitLifeApp().setVisible(true));
    }
}
