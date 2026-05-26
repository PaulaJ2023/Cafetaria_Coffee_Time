package POOJava;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaClientes extends JFrame {

    private JTextField txtNome, txtCpf, txtTelefone, txtEndereco, txtPagamento;
    private JComboBox<String> cbFormaPagamento; // Novo menu seletor para as formas de pagamento
    private JTextArea areaResultado;
    private JLabel lblTotalPagamentos; 

    private ArrayList<Cliente> listaClientes = new ArrayList<>();

    public TelaClientes() {
        setTitle("Sistema de Gerenciamento de Clientes");
        setSize(500, 600); // Aumentado um pouco o tamanho para acomodar a nova linha
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Ajustado para 6 linhas de campos de dados
        JPanel painelTopo = new JPanel(new GridLayout(6, 2, 5, 5));
        painelTopo.setBorder(BorderFactory.createTitledBorder("Cadastro de Cliente"));

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtTelefone = new JTextField();
        txtEndereco = new JTextField();
        txtPagamento = new JTextField();
        
        // Inicializando o seletor de formas de pagamento
        String[] formas = {"Pix", "Cartão de Débito", "Cartão de Crédito", "Dinheiro"};
        cbFormaPagamento = new JComboBox<>(formas);

        painelTopo.add(new JLabel("Nome do Cliente:"));
        painelTopo.add(txtNome);
        painelTopo.add(new JLabel("CPF do Cliente:"));
        painelTopo.add(txtCpf);
        painelTopo.add(new JLabel("Telefone:"));
        painelTopo.add(txtTelefone);
        painelTopo.add(new JLabel("Endereço:"));
        painelTopo.add(txtEndereco);
        painelTopo.add(new JLabel("Valor do Pagamento:"));
        painelTopo.add(txtPagamento);
        painelTopo.add(new JLabel("Forma de Pagamento:"));
        painelTopo.add(cbFormaPagamento);

        add(painelTopo, BorderLayout.NORTH);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout());

        JButton btnCadastrar = new JButton("Cadastrar Cliente");
        btnCadastrar.setBackground(new Color(46, 139, 87)); 
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));

        lblTotalPagamentos = new JLabel("Total de Pagamentos Recebidos: R$ 0.00", SwingConstants.CENTER);
        lblTotalPagamentos.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalPagamentos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelInferior.add(btnCadastrar, BorderLayout.NORTH);
        painelInferior.add(lblTotalPagamentos, BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> cadastrarCliente());
    }

    private void cadastrarCliente() {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String telefone = txtTelefone.getText();
            String endereco = txtEndereco.getText();

            // Validação inicial do valor numérico
            double valor = Double.parseDouble(txtPagamento.getText());
            
            // Captura a forma selecionada no JComboBox
            String formaSelecionada = (String) cbFormaPagamento.getSelectedItem();
            String detalhesPagamento = "";

            // Lógica para obter as informações adicionais pedidas no enunciado
            if (formaSelecionada.equals("Pix")) {
                String chavePix = JOptionPane.showInputDialog(this, "Digite a chave PIX:");
                if (chavePix == null || chavePix.trim().isEmpty()) chavePix = "Não informada";
                detalhesPagamento = "Pix (Chave: " + chavePix + ") - R$ " + String.format("%.2f", valor);
                
            } else if (formaSelecionada.equals("Cartão de Débito") || formaSelecionada.equals("Cartão de Crédito")) {
                String numCartao = JOptionPane.showInputDialog(this, "Digite o número do cartão:");
                if (numCartao == null || numCartao.trim().isEmpty()) numCartao = "Não informado";
                detalhesPagamento = formaSelecionada + " (Nº: " + numCartao + ") - R$ " + String.format("%.2f", valor);
                
            } else if (formaSelecionada.equals("Dinheiro")) {
                String[] moedas = {"Real (BRL)", "Dólar (USD)"};
                int resposta = JOptionPane.showOptionDialog(this, "O pagamento é em Real ou Dólar?", 
                        "Tipo de Moeda", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                        null, moedas, moedas[0]);
                
                String moedaEscolhida = (resposta == 1) ? "Dólar (USD)" : "Real (BRL)";
                detalhesPagamento = "Dinheiro em " + moedaEscolhida + " - R$ " + String.format("%.2f", valor);
            }

            // Criação do objeto passando o valor puro e a String detalhada
            Cliente c = new Cliente(nome, cpf, telefone, endereco, valor, detalhesPagamento);

            listaClientes.add(c);

            atualizarTela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Insira um valor numérico válido para o pagamento!",
                    "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    private void atualizarTela() {
        areaResultado.setText("");
        double totalRecebido = 0;

        for (Cliente c : listaClientes) {
            areaResultado.append("--------------------------------------------------\n");
            areaResultado.append("Cliente: " + c.getNome() + " | CPF: " + c.getCpf() + "\n");
            areaResultado.append("Telefone: " + c.getTelefone() + " | Endereço: " + c.getEndereco() + "\n");
            // Exibe a string completa detalhada que criamos
            areaResultado.append("Forma de Pagto: " + c.getPagamento() + "\n");

            totalRecebido += c.getValorNumericoPagamento();
        }

        lblTotalPagamentos.setText("Total de Pagamentos Recebidos: R$ " + String.format("%.2f", totalRecebido));
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtPagamento.setText("");
        cbFormaPagamento.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaClientes().setVisible(true);
        });
    }
}