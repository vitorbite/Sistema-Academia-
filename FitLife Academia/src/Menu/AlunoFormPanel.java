package Menu;

import Planos.*;
import Cadastro.*;
import javax.swing.text.PlainDocument;
import Planos.Plano;
import Planos.VIP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class AlunoFormPanel extends JPanel {
    CadastroAcademico cadastro = new CadastroAcademico();

    public AlunoFormPanel(FitLifeApp app, CadastroAcademico cadastro) {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Cadastro de Alunos", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 8, 6, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        JTextField nome = new JTextField(20);
        JTextField idade = new JTextField(5);
        JTextField cpf = new JTextField(14);
        JTextField senha = new JTextField(20);
        JTextField confirmarSenha = new JTextField(20);
        JComboBox<String> plano = new JComboBox<>(new String[] { "Mensal", "VIP", "Anual" });
        // JTextArea observacoes = new JTextArea(4, 20);
        // observacoes.setLineWrap(true);
        // observacoes.setWrapStyleWord(true);
        // JScrollPane obsScroll = new JScrollPane(observacoes);

        JButton salvar = new JButton("Salvar");
        JButton limpar = new JButton("Limpar");
        JButton voltar = new JButton("Voltar");

        salvar.addActionListener((ActionEvent e) -> {
            String n = nome.getText().trim();
            String i = idade.getText().trim();
            String cpfVal = cpf.getText().trim();
            String s = senha.getText().trim();
            String cS = confirmarSenha.getText().trim();
            String p = (String) plano.getSelectedItem();
            if (n.isEmpty() || i.isEmpty() || cpfVal.isEmpty() || !s.equals(cS)) {
                JOptionPane.showMessageDialog(this, "Preencha Nome, Idade, CPF e Senha corretamente.");
                return;
            }
            Aluno aluno = cadastro.cadastrarAluno(new Aluno(n, Integer.parseInt(i), cpfVal, s, new Plano()));
            JOptionPane.showMessageDialog(this, "Aluno salvo:\n" +
                    "Nome: " + n + "\nIdade: " + i + "\nCPF: " + cpfVal + "\nPlano: " + p);
            // Aqui você pode integrar com sua camada de dados
            if (p.equalsIgnoreCase("Mensal")) {
                aluno.setPlano(new Mensal());
            }
            if (p.equalsIgnoreCase("VIP")) {
                aluno.setPlano(new VIP());
            }
            if (p.equalsIgnoreCase("Anual")) {
                aluno.setPlano(new Anual());
            }
        });

        limpar.addActionListener(e -> {
            nome.setText("");
            idade.setText("");
            cpf.setText("");
            senha.setText("");
            confirmarSenha.setText("");
            // observacoes.setText("");
            plano.setSelectedIndex(0);
        });

        voltar.addActionListener(e -> app.showScreen("dashboard"));

        // Grid
        int row = 0;
        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Nome:"), c);
        c.gridx = 1;
        c.gridy = row++;
        form.add(nome, c);

        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Idade:"), c);
        c.gridx = 1;
        c.gridy = row++;
        form.add(idade, c);

        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("CPF:"), c);
        c.gridx = 1;
        c.gridy = row++;
        form.add(cpf, c);

        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Plano:"), c);
        c.gridx = 1;
        c.gridy = row++;
        form.add(plano, c);

        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Senha:"), c);
        c.gridx = 1;
        c.gridy = row++;
        form.add(senha, c);

        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Confirmar Senha:"), c);
        c.gridx = 1;
        c.gridy = row++;
        form.add(confirmarSenha, c);

        // c.gridx = 0;
        // c.gridy = row;
        // c.anchor = GridBagConstraints.NORTHWEST;
        // form.add(new JLabel("Observações:"), c);
        // c.gridx = 1;
        // c.gridy = row++;
        // c.fill = GridBagConstraints.BOTH;
        // c.weighty = 1.0;
        // form.add(obsScroll, c);

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.weighty = 0;
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actions.add(salvar);
        actions.add(limpar);
        actions.add(voltar);
        form.add(actions, c);

        add(form, BorderLayout.CENTER);
    }
}
