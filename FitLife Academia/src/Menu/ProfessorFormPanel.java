package Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class ProfessorFormPanel extends JPanel {
    public ProfessorFormPanel(FitLifeApp app) {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Cadastro de Professores", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 8, 6, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        JTextField nome = new JTextField(20);
        JTextField registro = new JTextField(10);  // matrícula/CREF
        JTextField especialidade = new JTextField(20);
        JComboBox<String> turno = new JComboBox<>(new String[]{"Manhã", "Tarde", "Noite"});
        JTextArea observacoes = new JTextArea(4, 20);
        observacoes.setLineWrap(true);
        observacoes.setWrapStyleWord(true);
        JScrollPane obsScroll = new JScrollPane(observacoes);

        JButton salvar = new JButton("Salvar");
        JButton limpar = new JButton("Limpar");
        JButton voltar = new JButton("Voltar");

        salvar.addActionListener((ActionEvent e) -> {
            if (nome.getText().trim().isEmpty() || registro.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha Nome e Registro.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Professor salvo:\n" +
                    "Nome: " + nome.getText() + "\nRegistro: " + registro.getText() +
                    "\nEspecialidade: " + especialidade.getText() + "\nTurno: " + turno.getSelectedItem());
            // Integre com a persistência aqui
        });

        limpar.addActionListener(e -> {
            nome.setText("");
            registro.setText("");
            especialidade.setText("");
            observacoes.setText("");
            turno.setSelectedIndex(0);
        });

        voltar.addActionListener(e -> app.showScreen("dashboard"));

        int row = 0;
        c.gridx = 0; c.gridy = row; form.add(new JLabel("Nome:"), c);
        c.gridx = 1; c.gridy = row++; form.add(nome, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Registro (CREF):"), c);
        c.gridx = 1; c.gridy = row++; form.add(registro, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Especialidade:"), c);
        c.gridx = 1; c.gridy = row++; form.add(especialidade, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Turno:"), c);
        c.gridx = 1; c.gridy = row++; form.add(turno, c);

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
