package Menu;

import Cadastro.CadastroAcademico;
import Cadastro.Professor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class ProfessorFormPanel extends JPanel {
    public ProfessorFormPanel(FitLifeApp app, CadastroAcademico cadastro) {
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
        JTextField idade = new JTextField(5);
        JTextField cpf = new JTextField(14);
        JTextField senha = new JTextField(20);
        JTextField confirmarSenha = new JTextField(20);

        JTextField registro = new JTextField(10); // matrícula/CREF (apenas para exibição)
        JTextField especialidade = new JTextField(20);
        // Substitui o turno por horário de início e fim no formato HH:mm
        JTextField horarioInicio = new JTextField(5);
        JTextField horarioFim = new JTextField(5);

        JButton salvar = new JButton("Salvar");
        JButton limpar = new JButton("Limpar");
        JButton voltar = new JButton("Voltar");

        salvar.addActionListener((ActionEvent e) -> {
            String n = nome.getText().trim();
            String i = idade.getText().trim();
            String reg = registro.getText().trim();
            String esp = especialidade.getText().trim();

            if (n.isEmpty() || reg.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha Nome e Registro (CREF).");
                return;
            }
            // validação de horários (opcionais)
            String hi = horarioInicio.getText().trim();
            String hf = horarioFim.getText().trim();
            String horario = null;
            if (!hi.isEmpty() || !hf.isEmpty()) {
                if (!TimeUtils.isValidTime(hi) || !TimeUtils.isValidTime(hf)) {
                    JOptionPane.showMessageDialog(this, "Horário inválido. Use o formato HH:mm (ex: 08:30).");
                    return;
                }
                horario = hi + " - " + hf;
            }

            Professor p = new Professor(n, Integer.parseInt(i.isEmpty()? "30": i), reg);
            if (!esp.isEmpty()) {
                p.adicionarTipoExercicio(esp);
            }

            if (horario != null) {
                p.adicionarHorario(horario);
            }

            cadastro.cadastrarProfessor(p);
                // Atualiza dashboard do professor
                app.setCurrentProfessor(p);
                app.refreshProfessorDashboard();

                JOptionPane.showMessageDialog(this, "Professor salvo:\n" +
                    "Nome: " + n + "\nRegistro: " + reg + "\nEspecialidade: " + esp + "\nHorário: "
                    + (horario != null ? horario : "(não informado)"));

                // Limpa campos após salvar
                nome.setText("");
                registro.setText("");
                especialidade.setText("");
                horarioInicio.setText("");
                horarioFim.setText("");
        });

        limpar.addActionListener(e -> {
            nome.setText("");
            registro.setText("");
            especialidade.setText("");
            horarioInicio.setText("");
            horarioFim.setText("");
        });

        voltar.addActionListener(e -> app.showScreen("dashboard"));

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
        form.add(new JLabel("Registro (CREF):"), c);
        c.gridx = 1;
        c.gridy = row++;
        form.add(registro, c);

        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Especialidade:"), c);
        c.gridx = 1;
        c.gridy = row++;
        form.add(especialidade, c);

        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Horário Início (HH:mm):"), c);
        c.gridx = 1;
        c.gridy = row++;
        form.add(horarioInicio, c);

        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel("Horário Fim (HH:mm):"), c);
        c.gridx = 1;
        c.gridy = row++;
        form.add(horarioFim, c);

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

// Helper para validar horário no formato HH:mm
class TimeUtils {
    public static boolean isValidTime(String t) {
        if (t == null) return false;
        return t.matches("^([01]\\d|2[0-3]):[0-5]\\d$");
    }
}
