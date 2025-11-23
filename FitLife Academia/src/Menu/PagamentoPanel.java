package Menu;

import Cadastro.Aluno;
import Cadastro.CadastroAcademico;
import Financeiro.Fatura;
import Financeiro.GestorFinanceiro;
import Financeiro.StatusPagamento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PagamentoPanel extends JPanel {
    
    private DefaultListModel<Fatura> faturaListModel;
    private JList<Fatura> faturaJList;
    private GestorFinanceiro gestorFinanceiro = GestorFinanceiro.getInstance();
    private FitLifeApp app;

    public PagamentoPanel(FitLifeApp app, CadastroAcademico cadastro) {
        this.app = app;
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gestão de Pagamentos", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        // Lista de Faturas (modelo com objetos Fatura para renderizar status)
        faturaListModel = new DefaultListModel<>();
        faturaJList = new JList<>(faturaListModel);
        // Renderer para mostrar ícone de status colorido ao lado da descrição
        faturaJList.setCellRenderer(new ListCellRenderer<Fatura>() {
            private final Icon iconPago = new StatusIcon(Color.GREEN.darker());
            private final Icon iconPendente = new StatusIcon(Color.ORANGE.darker());
            private final Icon iconAtrasado = new StatusIcon(Color.RED.darker());

            @Override
            public Component getListCellRendererComponent(JList<? extends Fatura> list, Fatura value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                if (value != null) {
                    // Texto com data de vencimento explícita
                    String texto = String.format("%s (CPF: %s) | R$ %.2f | Venc: %s | Status: %s",
                            value.getAluno().getNome(), value.getAluno().getCpf(), value.getValor(),
                            value.getDataVencimento(), value.getStatus());
                    label.setText(texto);
                    label.setToolTipText(texto);
                    StatusPagamento s = value.getStatus();
                    if (s == StatusPagamento.PAGO) label.setIcon(iconPago);
                    else if (s == StatusPagamento.PENDENTE) label.setIcon(iconPendente);
                    else label.setIcon(iconAtrasado);
                }
                if (isSelected) {
                    label.setBackground(list.getSelectionBackground());
                    label.setForeground(list.getSelectionForeground());
                } else {
                    label.setBackground(list.getBackground());
                    label.setForeground(list.getForeground());
                }
                return label;
            }
        });
        faturaJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(faturaJList);
        add(scrollPane, BorderLayout.CENTER);

        // Ações (Botões)
        JPanel actions = new JPanel(new FlowLayout());
        JButton btnCarregar = new JButton("Atualizar Faturas");
        JButton btnGerarFatura = new JButton("Gerar Fatura");
        JButton btnPagar = new JButton("Registrar Pagamento");
        JButton btnVoltar = new JButton("Voltar");

        btnCarregar.addActionListener(e -> carregarTodasFaturas());

        btnGerarFatura.addActionListener(e -> {
            // Apenas administração pode gerar faturas manualmente
            if (app.getCurrentAluno() != null || app.getCurrentProfessor() != null) {
                JOptionPane.showMessageDialog(this, "Apenas a administração pode gerar faturas manualmente.");
                return;
            }
            List<Aluno> alunos = cadastro.getAlunos();
            if (alunos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum aluno cadastrado.");
                return;
            }
            String[] options = new String[alunos.size()];
            for (int i = 0; i < alunos.size(); i++) {
                options[i] = alunos.get(i).getNome() + " (" + alunos.get(i).getCpf() + ")";
            }
            String sel = (String) JOptionPane.showInputDialog(this, "Selecione o aluno:", "Gerar Fatura", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (sel == null) return; // cancelou
            int idx = -1;
            for (int i = 0; i < options.length; i++) if (options[i].equals(sel)) { idx = i; break; }
            if (idx >= 0) {
                Aluno alvo = alunos.get(idx);
                gestorFinanceiro.gerarNovaFatura(alvo, true); // admin manual generation -> 1 mês de diferença
                carregarTodasFaturas();
                JOptionPane.showMessageDialog(this, "Fatura gerada para " + alvo.getNome());
            }
        });

        btnPagar.addActionListener(e -> {
            int selectedIndex = faturaJList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma fatura para registrar o pagamento.");
                return;
            }
            // Mapeia o índice visível para a fatura correspondente
            java.util.List<Fatura> vis = new java.util.ArrayList<>();
            boolean alunoLogado = (app.getCurrentAluno() != null && app.getCurrentProfessor() == null);
            for (Fatura f : gestorFinanceiro.getFaturas()) {
                if (alunoLogado) {
                    // Para aluno: incluir todas as faturas pertencentes a ele (PENDENTE, PAGO, ATRASADO)
                    if (f.getAluno() != null && app.getCurrentAluno() != null &&
                            f.getAluno().getCpf().equals(app.getCurrentAluno().getCpf())) {
                        vis.add(f);
                    }
                } else {
                    vis.add(f);
                }
            }
            if (selectedIndex < 0 || selectedIndex >= vis.size()) {
                JOptionPane.showMessageDialog(this, "Seleção inválida.");
                return;
            }
            Fatura faturaSelecionada = vis.get(selectedIndex);

            if (faturaSelecionada.getStatus() != Financeiro.StatusPagamento.PENDENTE) {
                JOptionPane.showMessageDialog(this, "Somente faturas no status PENDENTE podem ser pagas.");
                return;
            }

            if (gestorFinanceiro.registrarPagamento(faturaSelecionada)) {
                JOptionPane.showMessageDialog(this, "Pagamento registrado com sucesso para: " + faturaSelecionada.getAluno().getNome());
                carregarTodasFaturas();
            }
        });

        btnVoltar.addActionListener(e -> {
            // Volta para o dashboard apropriado conforme tipo de usuário logado
            if (app.getCurrentAluno() != null) {
                app.showScreen("studentDashboard");
            } else if (app.getCurrentProfessor() != null) {
                app.showScreen("professorDashboard");
            } else {
                app.showScreen("login");
            }
        });

        actions.add(btnCarregar);
        // Mostrar o botão de gerar fatura somente para a administração (nenhum aluno/professor logado)
        if (app.getCurrentAluno() == null && app.getCurrentProfessor() == null) {
            actions.add(btnGerarFatura);
        }
        actions.add(btnPagar);
        actions.add(btnVoltar);

        add(actions, BorderLayout.SOUTH);
        carregarTodasFaturas(); // Carrega ao iniciar
    }
    
    private void carregarTodasFaturas() {
        faturaListModel.clear();
        // Se um aluno estiver logado, mostra todas as faturas dele (PENDENTE, PAGO, ATRASADO); senão, mostra todas
        boolean alunoLogado = (app.getCurrentAluno() != null && app.getCurrentProfessor() == null);
        java.util.List<Fatura> todas = gestorFinanceiro.getFaturas();
        for (Fatura f : todas) {
            if (alunoLogado) {
                    if (f.getAluno() != null && app.getCurrentAluno() != null &&
                        f.getAluno().getCpf().equals(app.getCurrentAluno().getCpf())) {
                    faturaListModel.addElement(f);
                }
            } else {
                faturaListModel.addElement(f);
            }
        }
    }

    // Permite que FitLifeApp invoque um refresh ao mostrar o painel
    public void refresh() {
        carregarTodasFaturas();
    }

    // Pequeno ícone circular colorido para representar o status
    private static class StatusIcon implements Icon {
        private final Color color;
        private final int size = 12;

        public StatusIcon(Color color) { this.color = color; }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillOval(x, y, size, size);
            g.setColor(Color.DARK_GRAY);
            g.drawOval(x, y, size, size);
        }

        @Override
        public int getIconWidth() { return size; }

        @Override
        public int getIconHeight() { return size; }
    }
}