package POOJava;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaAdmin extends JFrame {

    // Listas em memória (Substituem o Banco de Dados)
    private ArrayList<Funcionario> listaFuncionarios = new ArrayList<>();
    private ArrayList<Pedido> listaPedidos = new ArrayList<>();
    private int contadorPedidos = 1;

    // Componentes da Aba de Funcionários
    private JTextField txtNomeFunc, txtCpfFunc, txtSalario, txtValorHora, txtHoras, txtGratificacao, txtLucros,
            txtBolsa;
    private JComboBox<String> comboCargo;
    private JTextArea areaFuncionarios;
    private JLabel lblTotalFolha;

    // Componentes da Aba de Pedidos
    private JTextField txtNomeCliente, txtEndereco, txtTotalPedido;
    private JTextArea areaProdutos, areaPedido;
    private JComboBox<String> comboPagamento, retirarPedido;

    public TelaAdmin() {
        setTitle("Café Time ☕ - Painel Administrativo");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criando o sistema de abas
        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Gestão de Funcionários", criarAbaFuncionarios());
        abas.addTab("Anotação de Pedidos", criarAbaPedidos());

        add(abas);
    }

    // --- ABA DE FUNCIONÁRIOS ---
    private JPanel criarAbaFuncionarios() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        // Formulário de Cadastro
        JPanel form = new JPanel(new GridLayout(10, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Cadastrar Novo Funcionário"));

        comboCargo = new JComboBox<>(new String[] { "Funcionário Comum", "Gerente", "Estagiário" });
        txtNomeFunc = new JTextField();
        txtCpfFunc = new JTextField();
        txtSalario = new JTextField();
        txtValorHora = new JTextField();
        txtHoras = new JTextField();
        txtGratificacao = new JTextField("0");
        txtLucros = new JTextField("0");
        txtBolsa = new JTextField("0");
        JButton btnCadastrarFunc = new JButton("Cadastrar Funcionário");

        form.add(new JLabel("Cargo:"));
        form.add(comboCargo);
        form.add(new JLabel("Nome:"));
        form.add(txtNomeFunc);
        form.add(new JLabel("CPF:"));
        form.add(txtCpfFunc);
        form.add(new JLabel("Salário Base:"));
        form.add(txtSalario);
        form.add(new JLabel("Valor por Hora:"));
        form.add(txtValorHora);
        form.add(new JLabel("Horas Trabalhadas:"));
        form.add(txtHoras);
        form.add(new JLabel("Gratificação (Gerente):"));
        form.add(txtGratificacao);
        form.add(new JLabel("Part. Lucros (Gerente):"));
        form.add(txtLucros);
        form.add(new JLabel("Bolsa Auxílio (Estagiário):"));
        form.add(txtBolsa);
        form.add(new JLabel(""));
        form.add(btnCadastrarFunc);

        // Área de Exibição
        areaFuncionarios = new JTextArea();
        areaFuncionarios.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaFuncionarios);

        lblTotalFolha = new JLabel("Total da Folha Salarial: R$ 0,00");
        lblTotalFolha.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel painelDireita = new JPanel(new BorderLayout());
        painelDireita.add(scroll, BorderLayout.CENTER);
        painelDireita.add(lblTotalFolha, BorderLayout.SOUTH);

        painel.add(form, BorderLayout.WEST);
        painel.add(painelDireita, BorderLayout.CENTER);

        // Ação do Botão
        btnCadastrarFunc.addActionListener(e -> cadastrarFuncionario());

        return painel;
    }

    // --- ABA DE PEDIDOS ---
    private JPanel criarAbaPedidos() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        // Formulário de Pedidos
        JPanel form = new JPanel(new GridLayout(7, 2, 8, 12));
        form.setBorder(BorderFactory.createTitledBorder("Anotar Novo Pedido"));

        txtNomeCliente = new JTextField();
        txtEndereco = new JTextField();
        areaProdutos = new JTextArea(3, 20);
        comboPagamento = new JComboBox<>(new String[] { "Pix", "Cartão de Crédito", "Cartão de Débito", "Dinheiro" });
        retirarPedido = new JComboBox<>(new String[] { "Retirar da Loja", "Delivery", "Comer na Loja" });
        txtTotalPedido = new JTextField();
        JButton btnCadastrarPedido = new JButton("Anotar Pedido");

        form.add(new JLabel("Nome do Cliente:"));
        form.add(txtNomeCliente);
        form.add(new JLabel("Endereço:"));
        form.add(txtEndereco);
        form.add(new JLabel("Produtos Selecionados:"));
        form.add(new JScrollPane(areaProdutos));
        form.add(new JLabel("Forma de Pagamento:"));
        form.add(comboPagamento);
        form.add(new JLabel("Escolha uma retirada do seu pedido:"));
        form.add(retirarPedido);
        form.add(new JLabel("Total a pagar"));
        form.add(txtTotalPedido);
        form.add(new JLabel(""));
        form.add(btnCadastrarPedido);

        // Área de Exibição dos Pedidos
        areaPedido = new JTextArea();
        areaPedido.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaPedido);

        painel.add(form, BorderLayout.WEST);
        painel.add(scroll, BorderLayout.CENTER);

        // Ação do Botão
        btnCadastrarPedido.addActionListener(e -> cadastrarPedido());

        return painel;
    }

    // --- LÓGICA POO EM MEMÓRIA ---
    private void cadastrarFuncionario() {
        try {
            int cargo = comboCargo.getSelectedIndex();
            String nome = txtNomeFunc.getText() == null ? txtNomeFunc.getText() : txtNomeFunc.getText();
            String cpf = txtCpfFunc.getText();
            double salario = Double.parseDouble(txtSalario.getText());
            double valorHora = Double.parseDouble(txtValorHora.getText());
            double horas = Double.parseDouble(txtHoras.getText());

            Funcionario f;

            if (cargo == 1) { // Gerente
                double grat = Double.parseDouble(txtGratificacao.getText());
                double lucros = Double.parseDouble(txtLucros.getText());
                f = new Gerente(nome, cpf, salario, valorHora, horas, grat, lucros);
            } else if (cargo == 2) { // Estagiário
                double bolsa = Double.parseDouble(txtBolsa.getText());
                f = new Estagiario(nome, cpf, salario, valorHora, horas, bolsa);
            } else { // Funcionário Normal
                f = new Funcionario(nome, cpf, salario, valorHora, horas);
            }

            listaFuncionarios.add(f);
            atualizarListaFuncionarios();
            limparCamposFuncionario();
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário. Verifique os valores numéricos.");
        }
    }

    private void cadastrarPedido() {
        try {
            String cliente = txtNomeCliente.getText();
            String endereco = txtEndereco.getText();
            String produtos = areaProdutos.getText();
            String pagamento = (String) comboPagamento.getSelectedItem();
            String retirar = (String) retirarPedido.getSelectedItem();
            double total = Double.parseDouble(txtTotalPedido.getText());

            Pedido p = new Pedido(contadorPedidos++, cliente, endereco, produtos, pagamento, retirar, total);
            listaPedidos.add(p);

            atualizarListaPedidos();
            limparCamposPedido();
            JOptionPane.showMessageDialog(this, "Pedido anotado com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar pedido. Verifique o valor total.");
        }
    }

    private void atualizarListaFuncionarios() {
        areaFuncionarios.setText("");
        double totalFolha = 0;

        for (Funcionario f : listaFuncionarios) {
            areaFuncionarios.append("Nome: " + f.getNome() + " | CPF: " + f.getCpf() + "\n");
            // O polimorfismo acontece aqui ao calcular o salário específico de cada cargo
            // automaticamente
            areaFuncionarios.append("Salário Líquido: R$ " + String.format("%.2f", f.calcularSalario()) + "\n");
            areaFuncionarios.append("----------------------------------------------------------------------\n");
            totalFolha += f.calcularSalario();
        }
        lblTotalFolha.setText("Total da Folha Salarial: R$ " + String.format("%.2f", totalFolha));
    }

    private void atualizarListaPedidos() {
        areaPedido.setText("");
        for (Pedido p : listaPedidos) {
            areaPedido.append("Pedido Nº: #" + p.getNumeroPedido() + "\n");
            areaPedido.append("Cliente: " + p.getNomeCliente() + " | Endereço: " + p.getEndereco() + "\n");
            areaPedido.append("Produtos:\n" + p.getProdutosSelecionados() + "\n");
            areaPedido.append("Forma de Pagamento: " + p.getFormaPagamento() + "\n");
            areaPedido.append("Total: R$ " + String.format("%.2f", p.getTotalPagar()) + "\n");
            areaPedido.append("----------------------------------------------------------------------\n");
        }
    }

    private void limparCamposFuncionario() {
        txtNomeFunc.setText("");
        txtCpfFunc.setText("");
        txtSalario.setText("");
        txtValorHora.setText("");
        txtHoras.setText("");
        txtGratificacao.setText("0");
        txtLucros.setText("0");
        txtBolsa.setText("0");
    }

    private void limparCamposPedido() {
        txtNomeCliente.setText("");
        txtEndereco.setText("");
        areaProdutos.setText("");
        txtTotalPedido.setText("");
        comboPagamento.setSelectedIndex(0);
        retirarPedido.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaAdmin().setVisible(true));
    }
}