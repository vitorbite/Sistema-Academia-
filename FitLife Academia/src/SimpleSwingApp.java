import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SimpleSwingApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("FitLife - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 240);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("FitLife", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        frame.add(titulo, BorderLayout.NORTH);

        // Painel central com BoxLayout vertical
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Linha: Nome
        JLabel lblNome = new JLabel("Nome:", JLabel.CENTER);
        JTextField campoNome = new JTextField(15);

        // Linha: Senha
        JLabel lblSenha = new JLabel("Senha:", JLabel.CENTER);
        JPasswordField campoSenha = new JPasswordField(15);

        // Centralizar texto dentro dos componentes
        campoNome.setHorizontalAlignment(JTextField.CENTER);
        campoSenha.setHorizontalAlignment(JTextField.CENTER);

        // Centralizar os próprios componentes no painel
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        campoNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        campoSenha.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Controlar tamanhos
        Dimension campoSize = new Dimension(220, 28);
        campoNome.setMaximumSize(campoSize);
        campoSenha.setMaximumSize(campoSize);

        // Botão confirmar
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                String senha = new String(campoSenha.getPassword());
                if (nome.equals("admin") && senha.equals("123")) {
                    JOptionPane.showMessageDialog(frame, "Login realizado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Nome ou senha incorretos.");
                }
            }
        });

        // Montagem
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(lblNome);
        panel.add(campoNome);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblSenha);
        panel.add(campoSenha);
        panel.add(Box.createRigidArea(new Dimension(0, 16)));
        panel.add(btnConfirmar);

        // Centraliza o painel no centro da janela
        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centro.add(panel);

        frame.add(centro, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}