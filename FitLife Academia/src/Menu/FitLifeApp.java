package Menu;

import javax.swing.*;
import java.awt.*;

public class FitLifeApp extends JFrame {
    private CardLayout cards;
    private JPanel container;

    public FitLifeApp() {
        super("FitLife");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        cards = new CardLayout();
        container = new JPanel(cards);

        // Telas
        LoginPanel login = new LoginPanel(this);
        DashboardPanel dashboard = new DashboardPanel(this);
        AlunoFormPanel alunoForm = new AlunoFormPanel(this);
        ProfessorFormPanel professorForm = new ProfessorFormPanel(this);

        // Adiciona ao container
        container.add(login, "login");
        container.add(dashboard, "dashboard");
        container.add(alunoForm, "alunoForm");
        container.add(professorForm, "professorForm");

        add(container);
        showScreen("login");
    }

    public void showScreen(String name) {
        cards.show(container, name);
    }

    public void start() {
        SwingUtilities.invokeLater(() -> new FitLifeApp().setVisible(true));
    }
}
