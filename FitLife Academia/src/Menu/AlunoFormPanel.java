package Menu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class AlunoFormPanel extends JPanel {
    public AlunoFormPanel(FitLifeApp app) {
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
        JComboBox<String> plano = new JComboBox<>(new String[]{"Mensal", "Trimestral", "Anual"});
        JTextArea observacoes = new JTextArea(4, 20);
        observacoes.setLineWrap(true);
        observacoes.setWrapStyleWord(true);
        JScrollPane obsScroll = new JScrollPane(observacoes);

        JButton salvar = new JButton("Salvar");
        JButton limpar = new JButton("Limpar");
        JButton voltar = new JButton("Voltar");

        salvar.addActionListener((ActionEvent e) -> {
            String n = nome.getText().trim();
            String i = idade.getText().trim();
            String cpfVal = cpf.getText().trim();
            String p = (String) plano.getSelectedItem();

            if (n.isEmpty() || i.isEmpty() || cpfVal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha Nome, Idade e CPF.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Aluno salvo:\n" +
                    "Nome: " + n + "\nIdade: " + i + "\nCPF: " + cpfVal + "\nPlano: " + p);
            // Aqui você pode integrar com sua camada de dados
        });

        limpar.addActionListener(e -> {
            nome.setText("");
            idade.setText("");
            cpf.setText("");
            observacoes.setText("");
            plano.setSelectedIndex(0);
        });

        voltar.addActionListener(e -> app.showScreen("dashboard"));

        // Grid
        int row = 0;
        c.gridx = 0; c.gridy = row; form.add(new JLabel("Nome:"), c);
        c.gridx = 1; c.gridy = row++; form.add(nome, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Idade:"), c);
        c.gridx = 1; c.gridy = row++; form.add(idade, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("CPF:"), c);
        c.gridx = 1; c.gridy = row++; form.add(cpf, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Plano:"), c);
        c.gridx = 1; c.gridy = row++; form.add(plano, c);

        c.gridx = 0; c.gridy = row; c.anchor = GridBagConstraints.NORTHWEST;
        form.add(new JLabel("Observações:"), c);
        c.gridx = 1; c.gridy = row++; c.fill = GridBagConstraints.BOTH; c.weighty = 1.0;
        form.add(obsScroll, c);

        c.gridx = 0; c.gridy = row; c.gridwidth = 2; c.fill = GridBagConstraints.NONE; c.weighty = 0;
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actions.add(salvar);
        actions.add(limpar);
        actions.add(voltar);
        form.add(actions, c);

        add(form, BorderLayout.CENTER);
    }
}
