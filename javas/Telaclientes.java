package javas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Telaclientes extends JFrame {

    // Campos de texto adaptados exclusivamente para a classe Cliente
    private JTextField txtNome, txtCpf, txtTelefone, txtEndereco, txtPagamento;
    private JTextArea areaResultado;
    private JLabel lblTotalPagamentos; // Mostra o acumulado recebido dos clientes

    // Lista exclusiva para armazenar os Clientes
    private ArrayList<Cliente> listaClientes = new ArrayList<>();

    public Telaclientes() {
        setTitle("Sistema de Gerenciamento de Clientes");
        setSize(500, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Painel de Cadastro (GridLayout ajustado para 5 linhas de campos de dados)
        JPanel painelTopo = new JPanel(new GridLayout(5, 2, 5, 5));
        painelTopo.setBorder(BorderFactory.createTitledBorder("Cadastro de Cliente"));

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtTelefone = new JTextField();
        txtEndereco = new JTextField();
        txtPagamento = new JTextField();

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

        add(painelTopo, BorderLayout.NORTH);

        // Área de resultado central com Scroll para listagem
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        // Painel Inferior (Botão de Cadastrar + Somatório de Recebimentos)
        JPanel painelInferior = new JPanel(new BorderLayout());
        
        JButton btnCadastrar = new JButton("Cadastrar Cliente");
        btnCadastrar.setBackground(new Color(46, 139, 87)); // Cor verde para diferenciar de funcionários
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblTotalPagamentos = new JLabel("Total de Pagamentos Recebidos: R$ 0.00", SwingConstants.CENTER);
        lblTotalPagamentos.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalPagamentos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelInferior.add(btnCadastrar, BorderLayout.NORTH);
        painelInferior.add(lblTotalPagamentos, BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);

        // Evento do botão cadastrar
        btnCadastrar.addActionListener(e -> cadastrarCliente());
    }

    private void cadastrarCliente() {
        try {
            // Captura das Strings do formulário
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String telefone = txtTelefone.getText();
            String endereco = txtEndereco.getText();
            
            // Conversão do valor monetário do pagamento
            double pagamento = Double.parseDouble(txtPagamento.getText());

            // Criação do objeto Cliente utilizando o construtor do seu código console
            Cliente c = new Cliente(nome, cpf, telefone, endereco, pagamento);

            // Salvando na lista correta
            listaClientes.add(c);
            
            atualizarTela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Insira um valor numérico válido para o pagamento!", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    private void atualizarTela() {
        areaResultado.setText("");
        double totalRecebido = 0;

        // Varre a lista de clientes imprimindo os dados na tela
        for (Cliente c : listaClientes) {
            areaResultado.append("--------------------------------------------------\n");
            areaResultado.append("Cliente: " + c.getNome() + " | CPF: " + c.getCpf() + "\n");
            areaResultado.append("Telefone: " + c.getTelefone() + " | Endereço: " + c.getEndereco() + "\n");
            // Supondo que sua classe Cliente tenha o método getPagamento() implementado
            areaResultado.append("Valor Pago: R$ " + String.format("%.2f", c.getPagamento()) + "\n");
            
            totalRecebido += c.getPagamento();
        }
        
        // Atualiza o painel inferior com a soma total dos pagamentos dos clientes
        lblTotalPagamentos.setText("Total de Pagamentos Recebidos: R$ " + String.format("%.2f", totalRecebido));
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
            new Telaclientes().setVisible(true);
        });
    }
}