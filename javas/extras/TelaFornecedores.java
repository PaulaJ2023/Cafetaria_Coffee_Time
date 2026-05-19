package javas.extras;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaFornecedores extends JFrame {

    // Campos de texto específicos para a classe Fornecedor
    private JTextField txtNome, txtCpf, txtCnpj, txtTelefone, txtEndereco, txtPagamento;
    private JTextArea areaResultado;
    private JLabel lblTotalPagamentos; // Mostra o somatório das despesas com fornecedores

    // Lista exclusiva para armazenar os Fornecedores
    private ArrayList<Fornecedor> listaFornecedores = new ArrayList<>();

    public TelaFornecedores() {
        setTitle("Sistema de Gerenciamento de Fornecedores");
        setSize(500, 580);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Painel de Cadastro (GridLayout configurado para 6 linhas de campos)
        JPanel painelTopo = new JPanel(new GridLayout(6, 2, 5, 5));
        painelTopo.setBorder(BorderFactory.createTitledBorder("Cadastro de Fornecedor"));

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtCnpj = new JTextField();
        txtTelefone = new JTextField();
        txtEndereco = new JTextField();
        txtPagamento = new JTextField();

        painelTopo.add(new JLabel("Nome do Fornecedor:"));
        painelTopo.add(txtNome);
        painelTopo.add(new JLabel("CPF do Fornecedor:"));
        painelTopo.add(txtCpf);
        painelTopo.add(new JLabel("CNPJ do Fornecedor:"));
        painelTopo.add(txtCnpj);
        painelTopo.add(new JLabel("Telefone:"));
        painelTopo.add(txtTelefone);
        painelTopo.add(new JLabel("Endereço:"));
        painelTopo.add(txtEndereco);
        painelTopo.add(new JLabel("Valor do Pagamento:"));
        painelTopo.add(txtPagamento);

        add(painelTopo, BorderLayout.NORTH);

        // Área de resultado com rolagem para listagem de dados
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        // Painel Inferior (Botão de Cadastro + Balanço de Gastos)
        JPanel painelInferior = new JPanel(new BorderLayout());
        
        JButton btnCadastrar = new JButton("Cadastrar Fornecedor");
        btnCadastrar.setBackground(new Color(128, 0, 128)); // Cor roxa/magenta para diferenciar das outras telas
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblTotalPagamentos = new JLabel("Total a Pagar Fornecedores: R$ 0.00", SwingConstants.CENTER);
        lblTotalPagamentos.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalPagamentos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelInferior.add(btnCadastrar, BorderLayout.NORTH);
        painelInferior.add(lblTotalPagamentos, BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);

        // Evento do botão cadastrar
        btnCadastrar.addActionListener(e -> cadastrarFornecedor());
    }

    private void cadastrarFornecedor() {
        try {
            // Capturando os dados de texto
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String cnpj = txtCnpj.getText();
            String telefone = txtTelefone.getText();
            String endereco = txtEndereco.getText();
            
            // Convertendo o valor numérico
            double pagamento = Double.parseDouble(txtPagamento.getText());

            // Instanciando o objeto Fornecedor seguindo o construtor do primeiro código
            Fornecedor f = new Fornecedor(nome, cpf, telefone, endereco, cnpj, pagamento);

            // Adicionando à lista correta
            listaFornecedores.add(f);
            
            atualizarTela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Fornecedor cadastrado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Certifique-se de preencher o valor do pagamento usando apenas números e ponto!", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    private void atualizarTela() {
        areaResultado.setText("");
        double totalGasto = 0;

        // Varre a lista imprimindo os dados formatados
        for (Fornecedor f : listaFornecedores) {
            areaResultado.append("--------------------------------------------------\n");
            areaResultado.append("Fornecedor: " + f.getNome() + "\n");
            areaResultado.append("CPF: " + f.getCpf() + " | CNPJ: " + f.getCnpj() + "\n");
            areaResultado.append("Tel: " + f.getTelefone() + " | End: " + f.getEndereco() + "\n");
            areaResultado.append("Valor do Contrato/Pagamento: R$ " + String.format("%.2f", f.getPagamento()) + "\n");
            
            totalGasto += f.getPagamento();
        }
        
        // Atualiza o somatório na barra inferior
        lblTotalPagamentos.setText("Total a Pagar Fornecedores: R$ " + String.format("%.2f", totalGasto));
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtCnpj.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtPagamento.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaFornecedores().setVisible(true);
        });
    }
}
