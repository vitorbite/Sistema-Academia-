package Menu;
import javax.swing.*;

import Cadastro.CadastroAcademico;

import java.awt.*;
import java.awt.event.ActionEvent;

public class Inscrever extends JPanel{
    public Inscrever(FitLifeApp app, CadastroAcademico cadastroAcademico) {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Seja Membro e Inscreva-se!!!", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        JPanel menu = new JPanel(new GridLayout(3, 1, 12, 32));

        JButton btnAlunos = new JButton("Quero ser Aluno");
        btnAlunos.addActionListener((ActionEvent e) -> app.showScreen("alunoForm"));

        JButton btnProfessores = new JButton("Quero ser Professor");
        btnProfessores.addActionListener((ActionEvent e) -> app.showScreen("professorForm"));

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> app.showScreen("login"));

        menu.add(btnAlunos);
        menu.add(btnProfessores);
        menu.add(btnSair);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        center.add(menu);
        add(center, BorderLayout.CENTER);
        
    }
}
