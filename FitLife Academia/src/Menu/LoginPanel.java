package Menu;

import Cadastro.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

class LoginPanel extends JPanel {
    public LoginPanel(FitLifeApp app, CadastroAcademico cadastro) {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("FitLife", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 8, 6, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCpf = new JLabel("CPF:");
        JTextField campoCpf = new JTextField(15);

        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField(15);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener((ActionEvent e) -> {

            // Informações de LOGIN
            String cpf = campoCpf.getText().trim();
            String senha = new String(campoSenha.getPassword());

            // Admin shortcut
            if (cpf.equals("academia") && senha.equals("123")) {
                JOptionPane.showMessageDialog(this, "Login de administrador realizado com sucesso!");
                // admin: não seta aluno nem professor; vai para painel administrativo
                app.showScreen("adminDashboard");
                return;
            }

            // Tenta encontrar aluno
            Aluno aluno = cadastro.buscarAlunoPorCpf(cpf);
            if (aluno != null) {
                if (senha.equals(aluno.getSenha())) {
                    app.setCurrentAluno(aluno);
                    JOptionPane.showMessageDialog(this, "Login do aluno realizado com sucesso!");
                    app.showStudentDashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Senha incorreta para o CPF informado.");
                }
                return;
            }

            // Tenta encontrar professor (aqui, sem senha)
            Professor professor = cadastro.buscarProfessorPorCpf(cpf);
            if (professor != null) {
                app.setCurrentProfessor(professor);
                JOptionPane.showMessageDialog(this, "Login do professor realizado com sucesso!");
                app.showProfessorDashboard();
                return;
            }

            JOptionPane.showMessageDialog(this, "CPF não cadastrado.");
        });
        JLabel titulo2 = new JLabel("Ainda não sou membro", JLabel.CENTER);
        titulo2.setFont(new Font("Arial", Font.BOLD, 28));

        JButton btnInscrever = new JButton("Inscrever-se");
        btnInscrever.addActionListener((ActionEvent e) -> {
            app.showScreen("inscrever");
        });
        c.gridx = 0;
        c.gridy = 0;
        form.add(lblCpf, c);
        c.gridx = 1;
        c.gridy = 0;
        form.add(campoCpf, c);
        c.gridx = 0;
        c.gridy = 1;
        form.add(lblSenha, c);
        c.gridx = 1;
        c.gridy = 1;
        form.add(campoSenha, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        form.add(btnConfirmar, c);

        c.gridx = 0;
        c.gridy = 4;
        form.add(new JPanel(), c);

        c.gridy = 5;
        form.add(titulo2, c);

        c.gridy = 6;
        form.add(btnInscrever, c);
        add(form, BorderLayout.CENTER);
    }
}
