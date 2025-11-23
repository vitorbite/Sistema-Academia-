package Menu;

import Cadastro.CadastroAcademico;
import Cadastro.Aluno;
import Financeiro.GestorFinanceiro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminDashboardPanel extends JPanel {
    private FitLifeApp app;
    private CadastroAcademico cadastro;
    private DefaultListModel<String> alunosModel;
    private JList<String> alunosList;

    public AdminDashboardPanel(FitLifeApp app, CadastroAcademico cadastro) {
        this.app = app;
        this.cadastro = cadastro;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Painel Administrativo", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        alunosModel = new DefaultListModel<>();
        alunosList = new JList<>(alunosModel);
        alunosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(alunosList);
        add(sp, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout());
        JButton btnGerar = new JButton("Gerar Boleto");
        JButton btnRefresh = new JButton("Atualizar Lista");
        JButton btnVoltar = new JButton("Sair (Voltar ao Login)");

        btnRefresh.addActionListener(e -> carregarAlunos());
            btnGerar.addActionListener(e -> {
            int idx = alunosList.getSelectedIndex();
            if (idx == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um aluno para gerar o boleto.");
                return;
            }
            List<Aluno> alunos = cadastro.getAlunos();
            Aluno alvo = alunos.get(idx);
            GestorFinanceiro.getInstance().gerarNovaFatura(alvo, true);
            JOptionPane.showMessageDialog(this, "Boleto gerado para " + alvo.getNome());
        });

        btnVoltar.addActionListener(e -> {
            // Logout admin: volta para tela de login
            app.showScreen("login");
        });

        actions.add(btnRefresh);
        actions.add(btnGerar);
        actions.add(btnVoltar);
        add(actions, BorderLayout.SOUTH);

        carregarAlunos();
    }

    private void carregarAlunos() {
        alunosModel.clear();
        for (Aluno a : cadastro.getAlunos()) {
            String plano = (a.getPlano() != null) ? a.getPlano().getClass().getSimpleName() : "(nenhum)";
            alunosModel.addElement(String.format("%s | CPF: %s | Plano: %s", a.getNome(), a.getCpf(), plano));
        }
    }
}
