package Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AlunoLogin extends JPanel{
    public AlunoLogin(FitLifeApp app) {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("FitLife - Dashboard", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        JPanel menu = new JPanel(new GridLayout(2, 2, 12, 12));

        JButton btnAlunos = new JButton("Cadastro de Alunos");
        btnAlunos.addActionListener((ActionEvent e) -> app.showScreen("alunoForm"));

        JButton btnProfessores = new JButton("Cadastro de Professores");
        btnProfessores.addActionListener((ActionEvent e) -> app.showScreen("professorForm"));

        JButton btnPlanos = new JButton("Planos"); // Removida a marcação "(em breve)"
        btnPlanos.addActionListener((ActionEvent e) -> app.showScreen("planos")); // Adicionada a ação de navegação
        
        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> app.showScreen("login"));

        // menu.add(btnAlunos);
        // menu.add(btnProfessores);
        menu.add(btnPlanos); // Botão funcional
        menu.add(btnSair);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        center.add(menu);
        add(center, BorderLayout.CENTER);
    }
}
