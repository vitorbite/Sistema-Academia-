package Menu;

import Cadastro.Aluno;
import Cadastro.CadastroAcademico;
import Menu.Financeiro.Fatura;
import Menu.Financeiro.GestorFinanceiro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PagamentoPanel extends JPanel {
    
    private DefaultListModel<String> faturaListModel;
    private JList<String> faturaJList;
    private GestorFinanceiro gestorFinanceiro = GestorFinanceiro.getInstance();

    public PagamentoPanel(FitLifeApp app, CadastroAcademico cadastro) {
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

        btnVoltar.addActionListener(e -> app.showScreen("dashboard"));

        actions.add(btnCarregar);
        actions.add(btnGerarFatura);
        actions.add(btnPagar);
        actions.add(btnVoltar);

        add(actions, BorderLayout.SOUTH);
        carregarTodasFaturas(); // Carrega ao iniciar
    }
    
    private void carregarTodasFaturas() {
        faturaListModel.clear();
        for (Fatura f : gestorFinanceiro.getFaturas()) {
            faturaListModel.addElement(f.toString());
        }
    }
}