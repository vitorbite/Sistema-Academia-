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
    
    private DefaultListModel<String> faturaListModel;
    private JList<String> faturaJList;
    private GestorFinanceiro gestorFinanceiro = GestorFinanceiro.getInstance();
    private FitLifeApp app;

    public PagamentoPanel(FitLifeApp app, CadastroAcademico cadastro) {
        this.app = app;
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gestão de Pagamentos", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        // Lista de Faturas
        faturaListModel = new DefaultListModel<>();
        faturaJList = new JList<>(faturaListModel);
        faturaJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(faturaJList);
        add(scrollPane, BorderLayout.CENTER);

        // Ações (Botões)
        JPanel actions = new JPanel(new FlowLayout());
        JButton btnCarregar = new JButton("Atualizar Faturas");
        JButton btnGerarFatura = new JButton("Gerar Fatura (Primeiro Aluno)");
        JButton btnPagar = new JButton("Registrar Pagamento");
        JButton btnVoltar = new JButton("Voltar");

        btnCarregar.addActionListener(e -> carregarTodasFaturas());

        btnGerarFatura.addActionListener(e -> {
            List<Aluno> alunos = cadastro.getAlunos();
            if (!alunos.isEmpty()) {
                gestorFinanceiro.gerarNovaFatura(alunos.get(0));
                carregarTodasFaturas();
                JOptionPane.showMessageDialog(this, "Fatura gerada para " + alunos.get(0).getNome());
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum aluno cadastrado.");
            }
        });

        btnPagar.addActionListener(e -> {
            int selectedIndex = faturaJList.getSelectedIndex();
            if (selectedIndex != -1) {
                Fatura faturaSelecionada = gestorFinanceiro.getFaturas().get(selectedIndex); 
                
                if (faturaSelecionada.getStatus() == Financeiro.StatusPagamento.PAGO) {
                    JOptionPane.showMessageDialog(this, "Esta fatura já está paga!");
                    return;
                }
                
                if (gestorFinanceiro.registrarPagamento(faturaSelecionada)) {
                     JOptionPane.showMessageDialog(this, "Pagamento registrado com sucesso para: " + faturaSelecionada.getAluno().getNome());
                     carregarTodasFaturas(); 
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma fatura para registrar o pagamento.");
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
        actions.add(btnGerarFatura);
        actions.add(btnPagar);
        actions.add(btnVoltar);

        add(actions, BorderLayout.SOUTH);
        carregarTodasFaturas(); // Carrega ao iniciar
    }
    
    private void carregarTodasFaturas() {
        faturaListModel.clear();
        // Se um aluno estiver logado, mostra apenas as faturas dele; senão, mostra todas
        boolean mostrarApenasAlunoAtual = (app.getCurrentAluno() != null && app.getCurrentProfessor() == null);
        java.util.List<Fatura> todas = gestorFinanceiro.getFaturas();
        for (Fatura f : todas) {
            if (mostrarApenasAlunoAtual) {
                if (f.getAluno() != null && app.getCurrentAluno() != null &&
                        f.getAluno().getCpf().equals(app.getCurrentAluno().getCpf())) {
                    faturaListModel.addElement(f.toString());
                }
            } else {
                faturaListModel.addElement(f.toString());
            }
        }
    }
}