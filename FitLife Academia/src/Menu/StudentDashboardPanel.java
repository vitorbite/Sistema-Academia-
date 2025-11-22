package Menu;

import Cadastro.Aluno;
import javax.swing.*;
import java.awt.*;

public class StudentDashboardPanel extends JPanel {
    private FitLifeApp app;
    private JLabel lblInfo;
    private Cadastro.CadastroAcademico cadastro;
    public StudentDashboardPanel(FitLifeApp app, Cadastro.CadastroAcademico cadastro) {
        this.app = app;
        setLayout(new BorderLayout());
        this.cadastro = cadastro;

        JLabel titulo = new JLabel("Área do Aluno", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        lblInfo = new JLabel("", JLabel.CENTER);
        lblInfo.setVerticalAlignment(SwingConstants.TOP);
        add(lblInfo, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));

        JButton btnVerPlano = new JButton("Ver Plano");
        JButton btnVerModalidades = new JButton("Ver Modalidades");
        JButton btnHistorico = new JButton("Histórico de Treinos");
        JButton btnCancelar = new JButton("Cancelar Matrícula");
        JButton btnSair = new JButton("Sair");

        actions.add(btnVerPlano);
        actions.add(btnVerModalidades);
        actions.add(btnHistorico);
        actions.add(btnCancelar);
        actions.add(btnSair);

        add(actions, BorderLayout.SOUTH);

        btnVerPlano.addActionListener(e -> showPlano());
        btnVerModalidades.addActionListener(e -> showModalidades());
        btnHistorico.addActionListener(e -> showHistorico());
        btnCancelar.addActionListener(e -> cancelarMatricula());
        btnSair.addActionListener(e -> app.showScreen("login"));
    }

    public void refresh() {
        Aluno a = app.getCurrentAluno();
        if (a == null) {
            lblInfo.setText("Nenhum aluno logado.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(a.getNome()).append("<br>");
        sb.append("CPF: ").append(a.getCpf()).append("<br>");
        sb.append("Plano: ").append(a.getPlano() != null ? a.getPlano().getClass().getSimpleName() : "(nenhum)").append("<br>");
        sb.append("Modalidades: ");
        if (a.getModalidades() != null && !a.getModalidades().isEmpty()) {
            for (int i=0;i<a.getModalidades().size();i++) {
                sb.append(a.getModalidades().get(i).getClass().getSimpleName());
                if (i < a.getModalidades().size()-1) sb.append(", ");
            }
        } else sb.append("(nenhuma)");
        lblInfo.setText("<html>" + sb.toString() + "</html>");
    }

    private void showPlano() {
        Aluno a = app.getCurrentAluno();
        if (a == null) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno logado.");
            return;
        }
        if (a.getPlano() == null) {
            JOptionPane.showMessageDialog(this, "Aluno não possui plano cadastrado.");
            return;
        }
        String info = "Plano: " + a.getPlano().getClass().getSimpleName() + "\nValor: R$ " + a.getPlano().getValor() + "\nDuração (dias): " + a.getPlano().getDuracao_em_dias();
        JOptionPane.showMessageDialog(this, info, "Detalhes do Plano", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showModalidades() {
        Aluno a = app.getCurrentAluno();
        if (a == null) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno logado.");
            return;
        }
        if (a.getModalidades() == null || a.getModalidades().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma modalidade inscrita.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<a.getModalidades().size();i++) {
            sb.append((i+1) + ". " + a.getModalidades().get(i).getClass().getSimpleName() + "\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Modalidades", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHistorico() {
        Aluno a = app.getCurrentAluno();
        if (a == null) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno logado.");
            return;
        }
        if (a.getHistoricoTreinos() == null || a.getHistoricoTreinos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum registro de treinos encontrado.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<a.getHistoricoTreinos().size();i++) {
            sb.append((i+1) + ". " + a.getHistoricoTreinos().get(i).toString() + "\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Histórico de Treinos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelarMatricula() {
        Aluno a = app.getCurrentAluno();
        if (a == null) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno logado.");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this, "Confirma cancelamento da matrícula de " + a.getNome() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            // remove o aluno do cadastro
            cadastro.getAlunos().remove(a);
            app.setCurrentAluno(null);
            JOptionPane.showMessageDialog(this, "Matrícula cancelada.");
            app.showScreen("login");
        }
    }
}
