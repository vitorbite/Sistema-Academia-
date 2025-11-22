package Menu;

import Planos.*;
import Cadastro.*;
import Modalidades.*;

import javax.swing.text.PlainDocument;
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
        JPanel modalidadesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modalidadesPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JCheckBox cbYoga = new JCheckBox("Yoga");
        JCheckBox cbMusculacao = new JCheckBox("Musculação");
        JCheckBox cbPilates = new JCheckBox("Pilates");

        modalidadesPanel.add(cbYoga);
        modalidadesPanel.add(cbMusculacao);
        modalidadesPanel.add(cbPilates);

        JButton salvar = new JButton("Salvar");
        JButton limpar = new JButton("Limpar");
        JButton voltar = new JButton("Voltar");

        salvar.addActionListener((ActionEvent e) -> {
            String n = nome.getText().trim();
            String i = idade.getText().trim();
            String cpfVal = cpf.getText().trim();
            String s = senha.getText().trim();
            String cS = confirmarSenha.getText().trim();

            if (n.isEmpty() || i.isEmpty() || cpfVal.isEmpty() || !s.equals(cS)) {
                JOptionPane.showMessageDialog(this, "Preencha Nome, Idade, CPF e Senha corretamente.");
                return;
            }
            if (!cbYoga.isSelected() && !cbMusculacao.isSelected() && !cbPilates.isSelected()) {
                JOptionPane.showMessageDialog(this, "Selecione pelo menos uma modalidade!");
                return;
            }
            Aluno aluno = cadastro.cadastrarAluno(new Aluno(n, Integer.parseInt(i), cpfVal, s, new Plano()));
            // Aqui você pode integrar com sua camada de dados
            if (cbYoga.isSelected()) {
                aluno.inscreverEmModalidade(new Yoga());
            }
            if (cbMusculacao.isSelected()) {
                aluno.inscreverEmModalidade(new Musculacao());
            }
            if (cbPilates.isSelected()) {
                aluno.inscreverEmModalidade(new Pilates());
            }

            JOptionPane.showMessageDialog(this, "Aluno salvo:\n" +
                    "Nome: " + n + "\nIdade: " + i + "\nCPF: " + cpfVal);
        });

        limpar.addActionListener(e -> {
            nome.setText("");
            idade.setText("");
            cpf.setText("");
            senha.setText("");
            confirmarSenha.setText("");
            cbYoga.setSelected(false);
            cbMusculacao.setSelected(false);
            cbPilates.setSelected(false);
        });

        voltar.addActionListener(e -> app.showScreen("inscrever"));

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

        // Adicionar no grid:
        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Modalidades:"), c);
        c.gridx = 1;
        c.gridy = row++;
        form.add(modalidadesPanel, c);

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
