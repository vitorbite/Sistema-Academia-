package Menu;

import Cadastro.Aluno;
import Cadastro.CadastroAcademico;
import Planos.Mensal;
import Planos.VIP;
import Planos.Anual;

import javax.swing.*;
import java.awt.*;

public class PlanoSelectionPanel extends JPanel {
    public PlanoSelectionPanel(FitLifeApp app, CadastroAcademico cadastro) {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Escolha seu Plano", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 3, 12, 12));
        center.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Mensal
        JPanel mensalPanel = createCard("Mensal", "R$ 129.99\n30 dias");
        JButton btnMensal = new JButton("Selecionar");
        mensalPanel.add(btnMensal, BorderLayout.SOUTH);

        // VIP
        JPanel vipPanel = createCard("VIP", "R$ 179.90\n30 dias\nBenefícios VIP");
        JButton btnVIP = new JButton("Selecionar");
        vipPanel.add(btnVIP, BorderLayout.SOUTH);

        // Anual
        JPanel anualPanel = createCard("Anual", "R$ 95.90 Mensalmente\n365 dias");
        JButton btnAnual = new JButton("Selecionar");
        anualPanel.add(btnAnual, BorderLayout.SOUTH);

        center.add(mensalPanel);
        center.add(vipPanel);
        center.add(anualPanel);

        add(center, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnVoltar = new JButton("Voltar");
        actions.add(btnVoltar);
        add(actions, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> app.showScreen("alunoForm"));

        btnMensal.addActionListener(e -> {
            Aluno a = app.getCurrentAluno();
            if (a != null) {
                a.setPlano(new Mensal());
                // Gerar fatura automaticamente quando o aluno escolhe o plano
                try {
                    Financeiro.GestorFinanceiro.getInstance().gerarNovaFatura(a);
                } catch (Exception ex) {
                    System.out.println("Erro ao gerar fatura após seleção de plano: " + ex.getMessage());
                }
                JOptionPane.showMessageDialog(this, "Plano Mensal aplicado para " + a.getNome());
                app.refreshStudentDashboard();
                app.showScreen("studentDashboard");
            }
        });

        btnVIP.addActionListener(e -> {
            Aluno a = app.getCurrentAluno();
            if (a != null) {
                a.setPlano(new VIP());
                try {
                    Financeiro.GestorFinanceiro.getInstance().gerarNovaFatura(a);
                } catch (Exception ex) {
                    System.out.println("Erro ao gerar fatura após seleção de plano: " + ex.getMessage());
                }
                JOptionPane.showMessageDialog(this, "Plano VIP aplicado para " + a.getNome());
                app.refreshStudentDashboard();
                app.showScreen("studentDashboard");
            }
        });

        btnAnual.addActionListener(e -> {
            Aluno a = app.getCurrentAluno();
            if (a != null) {
                a.setPlano(new Anual());
                try {
                    Financeiro.GestorFinanceiro.getInstance().gerarNovaFatura(a);
                } catch (Exception ex) {
                    System.out.println("Erro ao gerar fatura após seleção de plano: " + ex.getMessage());
                }
                JOptionPane.showMessageDialog(this, "Plano Anual aplicado para " + a.getNome());
                app.refreshStudentDashboard();
                app.showScreen("studentDashboard");
            }
        });
    }

    private JPanel createCard(String title, String desc) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8,8,8,8)));
        JLabel t = new JLabel(title, JLabel.CENTER);
        t.setFont(new Font("Arial", Font.BOLD, 16));
        JTextArea d = new JTextArea(desc);
        d.setEditable(false);
        d.setBackground(p.getBackground());
        d.setFont(new Font("Arial", Font.PLAIN, 12));
        d.setBorder(null);
        p.add(t, BorderLayout.NORTH);
        p.add(d, BorderLayout.CENTER);
        return p;
    }
}
