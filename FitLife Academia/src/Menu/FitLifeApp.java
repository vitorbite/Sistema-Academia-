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
    private PagamentoPanel pagamentoPanel;
    private AdminDashboardPanel adminDashboard;

    public FitLifeApp() {
        super("FitLife");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        cards = new CardLayout();
        container = new JPanel(cards);

        cadastro = CadastroAcademico.getInstance();
    
        LoginPanel login = new LoginPanel(this, cadastro);
        Inscrever inscrever = new Inscrever(this, cadastro);
        AlunoFormPanel alunoForm = new AlunoFormPanel(this, cadastro);
        ProfessorFormPanel professorForm = new ProfessorFormPanel(this, cadastro);
        PlanoSelectionPanel planoSelection = new PlanoSelectionPanel(this, cadastro);
        studentDashboard = new StudentDashboardPanel(this, cadastro);
        professorDashboard = new ProfessorDashboardPanel(this, cadastro);
        pagamentoPanel = new PagamentoPanel(this, cadastro);
        adminDashboard = new AdminDashboardPanel(this, cadastro);

        // Gera faturas iniciais automaticamente para alunos existentes somente se já possuem plano
        for (Aluno a : cadastro.getAlunos()) {
            try {
                if ((a.getHistoricoFaturas() == null || a.getHistoricoFaturas().isEmpty()) && a.getPlano() != null && a.getPlano().getValor() > 0) {
                    Financeiro.GestorFinanceiro.getInstance().gerarNovaFatura(a);
                }
            } catch (Exception ex) {
                System.out.println("Erro ao gerar fatura inicial para " + a.getNome() + ": " + ex.getMessage());
            }
        }

        container.add(login, "login");
        container.add(inscrever, "inscrever");
        container.add(alunoForm, "alunoForm");
        container.add(professorForm, "professorForm");
        container.add(planoSelection, "planos");
        container.add(studentDashboard, "studentDashboard");
        container.add(professorDashboard, "professorDashboard");
        container.add(pagamentoPanel, "pagamentos");
        container.add(adminDashboard, "adminDashboard");

        add(container);
        showScreen("login");
    }

    public void showScreen(String name) {
        // refresh specific panels before showing
        if ("studentDashboard".equals(name)) refreshStudentDashboard();
        if ("professorDashboard".equals(name)) refreshProfessorDashboard();
        if ("pagamentos".equals(name) && pagamentoPanel != null) pagamentoPanel.refresh();
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
