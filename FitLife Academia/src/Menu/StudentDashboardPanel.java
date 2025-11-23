package Menu;

import Cadastro.Aluno;
import Cadastro.RegistroTreino;
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

        // painel de informações (esquerda)
        lblInfo = new JLabel("", JLabel.LEFT);
        lblInfo.setVerticalAlignment(SwingConstants.TOP);
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        infoPanel.add(lblInfo, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);

        // painel de ações (direita) com botões na vertical
        JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
        actions.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        actions.add(Box.createVerticalGlue());

        JButton btnVerPlano = new JButton("Ver Plano");
        JButton btnVerModalidades = new JButton("Ver Modalidades");
        JButton btnHistorico = new JButton("Histórico de Treinos");
        JButton btnPresenca = new JButton("Registrar Presença");
        JButton btnCancelar = new JButton("Cancelar Matrícula");
        JButton btnSair = new JButton("Sair");

        Dimension btnSize = new Dimension(160, 28);
        for (JButton b : new JButton[]{btnVerPlano, btnVerModalidades, btnHistorico, btnPresenca, btnCancelar, btnSair}) {
            b.setMaximumSize(btnSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            actions.add(b);
            actions.add(Box.createVerticalStrut(8));
        }

        actions.add(Box.createVerticalGlue());
        add(actions, BorderLayout.EAST);

        // listeners
        btnVerPlano.addActionListener(e -> showPlano());
        btnVerModalidades.addActionListener(e -> showModalidades());
        btnHistorico.addActionListener(e -> showHistorico());
        btnPresenca.addActionListener(e -> registrarPresenca());
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

    private void registrarPresenca() {
        Aluno a = app.getCurrentAluno();
        if (a == null) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno logado.");
            return;
        }
        // Busca aulas disponíveis (professor + modalidade + horário) correspondentes às modalidades do aluno
        java.util.List<Modalidades.Modalidade> mods = a.getModalidades();
        java.util.List<Cadastro.Professor> profs = cadastro.getProfessores();

        class Sessao {
            Modalidades.Modalidade modalidade;
            Cadastro.Professor professor;
            String horario;
            String label() { return professor.getNome() + " | " + modalidade.getClass().getSimpleName() + " | " + horario; }
        }

        java.util.List<Sessao> sessoes = new java.util.ArrayList<>();
        if (mods != null && profs != null) {
            for (Modalidades.Modalidade m : mods) {
                String mName = m.getClass().getSimpleName();
                for (Cadastro.Professor p : profs) {
                    // compara os tipos cadastrados no professor com o nome da modalidade
                    if (p.getTiposExercicio() != null) {
                        for (String tipo : p.getTiposExercicio()) {
                            if (tipo != null && tipo.equalsIgnoreCase(mName)) {
                                if (p.getHorarios() != null && !p.getHorarios().isEmpty()) {
                                    for (String h : p.getHorarios()) {
                                        Sessao s = new Sessao();
                                        s.modalidade = m;
                                        s.professor = p;
                                        s.horario = h;
                                        sessoes.add(s);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Modalidades.Modalidade escolhaModalidade = null;
        Cadastro.Professor escolhaProfessor = null;
        String escolhaHorario = null;

        if (!sessoes.isEmpty()) {
            String[] options = new String[sessoes.size()];
            for (int i=0;i<sessoes.size();i++) options[i] = sessoes.get(i).label();
            String sel = (String) JOptionPane.showInputDialog(this, "Escolha a sessão para registrar presença:", "Registrar Presença", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (sel == null) return; // cancelou
            for (Sessao s : sessoes) {
                if (s.label().equals(sel)) {
                    escolhaModalidade = s.modalidade;
                    escolhaProfessor = s.professor;
                    escolhaHorario = s.horario;
                    break;
                }
            }
        } else {
            // fallback: escolher apenas modalidade do aluno (como antes)
            if (mods == null || mods.isEmpty()) {
                int resp = JOptionPane.showConfirmDialog(this, "Deseja registrar presença sem modalidade?", "Registrar Presença", JOptionPane.YES_NO_OPTION);
                if (resp != JOptionPane.YES_OPTION) return;
            } else {
                String[] options = new String[mods.size()];
                for (int i=0;i<mods.size();i++) options[i] = mods.get(i).getClass().getSimpleName();
                String sel = (String) JOptionPane.showInputDialog(this, "Escolha a modalidade para registrar presença:", "Registrar Presença", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (sel == null) return;
                for (Modalidades.Modalidade m : mods) {
                    if (m.getClass().getSimpleName().equals(sel)) { escolhaModalidade = m; break; }
                }
            }
        }

        // Criar um registro indicando presença e incluindo professor/horário nos detalhes
        String detalhes = "Presença na aula" + (escolhaHorario != null ? " (" + escolhaHorario + ")" : "");
        if (escolhaProfessor != null) detalhes += " - Professor: " + escolhaProfessor.getNome();
        RegistroTreino reg = new RegistroTreino(escolhaModalidade, detalhes, "Presença registrada");
        a.adicionarRegistroTreino(reg);
        JOptionPane.showMessageDialog(this, "Presença registrada para " + a.getNome() + ".");
        refresh();
    }
}
