package POOJava;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class TelaClientes extends JFrame {

    private JTextField txtNome, txtCpf, txtTelefone, txtEndereco, txtPagamento;
    private JTextArea areaResultado;
    private JLabel lblTotalPagamentos;

    private ArrayList<Cliente> listaClientes = new ArrayList<>();

    public TelaClientes() {
        setTitle("Cliente");
        setSize(580, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(12, 12));
        getContentPane().setBackground(new Color(248, 250, 252));

        JPanel painelTopo = new JPanel(new GridLayout(5, 2, 6, 6));
        painelTopo.setBackground(Color.WHITE);
        painelTopo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)),
                        "Perfil Cadastral do Cliente", TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 13), new Color(15, 23, 42))));

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtTelefone = new JTextField();
        txtEndereco = new JTextField();
        txtPagamento = new JTextField();

        painelTopo.add(new JLabel(" Nome do Cliente:"));
        painelTopo.add(txtNome);
        painelTopo.add(new JLabel(" CPF/NIF:"));
        painelTopo.add(txtCpf);
        painelTopo.add(new JLabel(" Contacto Telefónico:"));
        painelTopo.add(txtTelefone);
        painelTopo.add(new JLabel(" Morada/Endereço:"));
        painelTopo.add(txtEndereco);
        painelTopo.add(new JLabel(" Crédito/Valor Líquido Pago:"));
        painelTopo.add(txtPagamento);

        JPanel painelCentral = new JPanel(new BorderLayout(8, 8));
        painelCentral.setOpaque(false);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        areaResultado = new JTextArea();
        areaResultado.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultado);

        JButton btnSalvar = new JButton("Confirmar Registo e Lançamento");
        btnSalvar.setBackground(new Color(15, 118, 110));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSalvar.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        painelCentral.add(btnSalvar, BorderLayout.NORTH);
        painelCentral.add(scroll, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelInferior.setBackground(Color.WHITE);
        painelInferior.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        lblTotalPagamentos = new JLabel("Balanço Total Recebido: R$ 0,00  ");
        lblTotalPagamentos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalPagamentos.setForeground(new Color(15, 23, 42));
        painelInferior.add(lblTotalPagamentos);

        add(painelTopo, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> cadastrarCliente());
    }

    private void cadastrarCliente() {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String telefone = txtTelefone.getText();
            String endereco = txtEndereco.getText();
            double pagamento = Double.parseDouble(txtPagamento.getText());

            Cliente c = new Cliente(nome, cpf, telefone, endereco, pagamento);
            listaClientes.add(c);

            atualizarTela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Cliente arquivado no sistema corporativo com sucesso.", "Confirmação",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Entrada inválida no campo de pagamento monetário.", "Erro de Entrada",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTela() {
        areaResultado.setText("");
        double totalRecebido = 0;

        for (Cliente c : listaClientes) {
            areaResultado.append("--------------------------------------------------\n");
            areaResultado.append("Cliente: " + c.getNome() + " | Documento: " + c.getCpf() + "\n");
            areaResultado.append("Tel: " + c.getTelefone() + " | Destino: " + c.getEndereco() + "\n");
            areaResultado.append("Faturamento Atribuído: R$ " + String.format("%.2f", c.getPagamento()) + "\n");
            totalRecebido += c.getPagamento();
        }

        lblTotalPagamentos.setText("Balanço Total Recebido: R$ " + String.format("%.2f", totalRecebido) + "  ");
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtPagamento.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaClientes().setVisible(true);
        });
    }
}