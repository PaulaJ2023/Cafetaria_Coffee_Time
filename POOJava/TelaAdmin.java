package POOJava;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
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

        // Formulário de Pedidos (Grid reajustado para comportar o botão de importação)
        JPanel form = new JPanel(new GridLayout(8, 2, 8, 12));
        form.setBorder(BorderFactory.createTitledBorder("Anotar Novo Pedido"));

        txtNomeCliente = new JTextField();
        txtEndereco = new JTextField();
        areaProdutos = new JTextArea(3, 20);
        comboPagamento = new JComboBox<>(new String[] { "Pix", "Cartão de Crédito", "Cartão de Débito", "Dinheiro" });
        retirarPedido = new JComboBox<>(new String[] { "Retirar da Loja", "Delivery", "Comer na Loja" });
        txtTotalPedido = new JTextField();

        JButton btnImportarWeb = new JButton("Importar Pedido da Web ☕");
        btnImportarWeb.setBackground(new Color(192, 108, 132)); // Cor combinando com a identidade visual do site
        btnImportarWeb.setForeground(Color.WHITE);

        JButton btnCadastrarPedido = new JButton("Anotar Pedido");

        form.add(new JLabel("Nome do Cliente:"));
        form.add(txtNomeCliente);
        form.add(new JLabel("Endereço:"));
        form.add(txtEndereco);
        form.add(new JLabel("Produtos Selecionados:"));
        form.add(new JScrollPane(areaProdutos));
        form.add(new JLabel("Forma de Pagamento:"));
        form.add(comboPagamento);
        form.add(new JLabel("Tipo de Retirada:"));
        form.add(retirarPedido);
        form.add(new JLabel("Total a Pagar (R$):"));
        form.add(txtTotalPedido);
        form.add(btnImportarWeb);
        form.add(btnCadastrarPedido);

        // Área de Exibição dos Pedidos
        areaPedido = new JTextArea();
        areaPedido.setEditable(false);
        JScrollPane scrollPedidos = new JScrollPane(areaPedido);

        painel.add(form, BorderLayout.WEST);
        painel.add(scrollPedidos, BorderLayout.CENTER);

        // Ações dos Botões
        btnCadastrarPedido.addActionListener(e -> cadastrarPedido());
        btnImportarWeb.addActionListener(e -> importarPedidoDoClipboard());

        return painel;
    }

    // Método responsável por capturar o Clipboard e fazer o Parse dos dados
    private void importarPedidoDoClipboard() {
        try {
            // Pega o texto armazenado na Área de Transferência (Ctrl+C) do computador
            String dadosCopiados = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);

            if (dadosCopiados != null && dadosCopiados.contains(";")) {
                String[] partes = dadosCopiados.split(";");

                if (partes.length >= 7) {
                    // 1. Preenche os campos textuais simples
                    txtNomeCliente.setText(partes[1].trim());
                    txtEndereco.setText(partes[2].trim());

                    // Melhora a visualização dos produtos quebrando as linhas por vírgula
                    areaProdutos.setText(partes[3].trim().replace(", ", "\n"));

                    // 2. Trata e seleciona a Forma de Pagamento no ComboBox
                    String pagamentoWeb = partes[4].trim();
                    selecionarItemCombo(comboPagamento, pagamentoWeb);

                    // 3. Tratamento especial para o tipo de retirada ("Retirada" -> "Retirar da
                    // Loja")
                    String tipoWeb = partes[5].trim();
                    if (tipoWeb.equalsIgnoreCase("Retirada")) {
                        retirarPedido.setSelectedItem("Retirar da Loja");
                    } else {
                        selecionarItemCombo(retirarPedido, tipoWeb);
                    }

                    // 4. Insere o valor total
                    txtTotalPedido.setText(partes[6].trim());

                    JOptionPane.showMessageDialog(this, "✨ Pedido da Web importado com sucesso!", "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "O texto copiado não possui a quantidade de dados necessária.",
                            "Formato Inválido", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nenhum dado válido de pedido encontrado na Área de Transferência.\nCertifique-se de clicar em 'Copiar Dados para o Java' no site.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar a Área de Transferência: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método utilitário para varrer e selecionar dinamicamente itens de JComboBox
    private void selecionarItemCombo(JComboBox<String> combo, String valor) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).equalsIgnoreCase(valor)
                    || combo.getItemAt(i).toLowerCase().contains(valor.toLowerCase())) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void cadastrarFuncionario() {
        try {
            String nome = txtNomeFunc.getText();
            String cpf = txtCpfFunc.getText();
            double salario = Double.parseDouble(txtSalario.getText());
            double valorHora = Double.parseDouble(txtValorHora.getText());
            double horas = Double.parseDouble(txtHoras.getText());

            Funcionario f;
            String cargo = (String) comboCargo.getSelectedItem();

            if (cargo.equals("Gerente")) {
                double grat = Double.parseDouble(txtGratificacao.getText());
                double lucros = Double.parseDouble(txtLucros.getText());
                f = new Gerente(nome, cpf, salario, valorHora, horas, grat, lucros);
            } else if (cargo.equals("Estagiário")) {
                double bolsa = Double.parseDouble(txtBolsa.getText());
                f = new Estagiario(nome, cpf, salario, valorHora, horas, bolsa);
            } else {
                f = new Funcionario(nome, cpf, salario, valorHora, horas);
            }

            listaFuncionarios.add(f);
            atualizarListaFuncionarios();
            limparCamposFuncionario();
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarPedido() {
        try {
            String nome = txtNomeCliente.getText();
            String endereco = txtEndereco.getText();
            String produtos = areaProdutos.getText();
            String pagamento = (String) comboPagamento.getSelectedItem();
            String retirada = (String) retirarPedido.getSelectedItem();
            double total = Double.parseDouble(txtTotalPedido.getText());

            Pedido p = new Pedido(contadorPedidos++, nome, endereco, produtos, pagamento, retirada, total);
            listaPedidos.add(p);

            atualizarListaPedidos();
            limparCamposPedido();
            JOptionPane.showMessageDialog(this, "Pedido registado com sucesso!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registar pedido: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarListaFuncionarios() {
        areaFuncionarios.setText("");
        double totalFolha = 0;
        for (Funcionario f : listaFuncionarios) {
            f.exibirDados();
            areaFuncionarios.append("Nome: " + f.getNome() + " | CPF: " + f.getCpf() + "\n");
            areaFuncionarios.append("Salário a receber: R$ " + String.format("%.2f", f.calcularSalario()) + "\n");
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
