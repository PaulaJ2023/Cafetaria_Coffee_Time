package POOJava;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.util.ArrayList;

public class TelaAdmin extends JFrame {

    private ArrayList<Funcionario> listaFuncionarios = new ArrayList<>();
    private ArrayList<Pedido> listaPedidos = new ArrayList<>();
    private int contadorPedidos = 1;

    private JTextField txtNomeFunc, txtCpfFunc, txtSalario, txtValorHora, txtHoras, txtGratificacao, txtLucros,
            txtBolsa;
    private JComboBox<String> comboCargo;
    private JTextArea areaFuncionarios;
    private JLabel lblTotalFolha;

    private JTextField txtNomeCliente, txtEndereco, txtTotalPedido;
    private JTextArea areaProdutos, areaPedido;
    private JComboBox<String> comboPagamento, retirarPedido;

    public TelaAdmin() {
        setTitle("Administrativa Geral");
        setSize(1000, 680);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        abas.addTab("Gestão de Funcionários", criarAbaFuncionarios());
        abas.addTab("Anotação de Pedidos Web", criarAbaPedidos());

        add(abas);
    }

    private JPanel criarAbaFuncionarios() {
        JPanel painel = new JPanel(new BorderLayout(15, 15));
        painel.setBackground(new Color(248, 250, 252));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(10, 2, 8, 8));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)),
                "Registo de Colaboradores", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 13),
                new Color(15, 23, 42)));

        comboCargo = new JComboBox<>(new String[] { "Funcionário Comum", "Gerente", "Estagiário" });
        txtNomeFunc = new JTextField();
        txtCpfFunc = new JTextField();
        txtSalario = new JTextField();
        txtValorHora = new JTextField();
        txtHoras = new JTextField();
        txtGratificacao = new JTextField("0");
        txtLucros = new JTextField("0");
        txtBolsa = new JTextField("0");

        JButton btnCadastrarFunc = new JButton("Registar Funcionário");
        btnCadastrarFunc.setBackground(new Color(71, 85, 105));
        btnCadastrarFunc.setForeground(Color.WHITE);
        btnCadastrarFunc.setFont(new Font("Segoe UI", Font.BOLD, 12));

        form.add(new JLabel(" Cargo:"));
        form.add(comboCargo);
        form.add(new JLabel(" Nome Completo:"));
        form.add(txtNomeFunc);
        form.add(new JLabel(" CPF:"));
        form.add(txtCpfFunc);
        form.add(new JLabel(" Salário Base (R$):"));
        form.add(txtSalario);
        form.add(new JLabel(" Valor por Hora (R$):"));
        form.add(txtValorHora);
        form.add(new JLabel(" Horas Trabalhadas:"));
        form.add(txtHoras);
        form.add(new JLabel(" Gratificação:"));
        form.add(txtGratificacao);
        form.add(new JLabel(" Part. Lucros:"));
        form.add(txtLucros);
        form.add(new JLabel(" Bolsa Auxílio:"));
        form.add(txtBolsa);
        form.add(new JLabel(""));
        form.add(btnCadastrarFunc);

        areaFuncionarios = new JTextArea();
        areaFuncionarios.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaFuncionarios.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaFuncionarios);

        lblTotalFolha = new JLabel("Total da Folha Salarial: R$ 0,00");
        lblTotalFolha.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalFolha.setForeground(new Color(15, 23, 42));

        JPanel painelDireita = new JPanel(new BorderLayout(10, 10));
        painelDireita.setOpaque(false);
        painelDireita.add(scroll, BorderLayout.CENTER);
        painelDireita.add(lblTotalFolha, BorderLayout.SOUTH);

        painel.add(form, BorderLayout.WEST);
        painel.add(painelDireita, BorderLayout.CENTER);

        btnCadastrarFunc.addActionListener(e -> cadastrarFuncionario());

        return painel;
    }

    private JPanel criarAbaPedidos() {
        JPanel painel = new JPanel(new BorderLayout(15, 15));
        painel.setBackground(new Color(248, 250, 252));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(8, 2, 8, 12));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)),
                "Ficha de Pedido", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 13),
                new Color(15, 23, 42)));

        txtNomeCliente = new JTextField();
        txtEndereco = new JTextField();
        areaProdutos = new JTextArea(3, 20);
        areaProdutos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboPagamento = new JComboBox<>(new String[] { "Pix", "Cartão de Crédito", "Cartão de Débito", "Dinheiro" });
        retirarPedido = new JComboBox<>(new String[] { "Retirar da Loja", "Delivery", "Comer na Loja" });
        txtTotalPedido = new JTextField();

        JButton btnImportarWeb = new JButton("Importar Dados da Web");
        btnImportarWeb.setBackground(new Color(30, 41, 59));
        btnImportarWeb.setForeground(Color.WHITE);
        btnImportarWeb.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JButton btnCadastrarPedido = new JButton("Confirmar Pedido");
        btnCadastrarPedido.setBackground(new Color(13, 148, 136));
        btnCadastrarPedido.setForeground(Color.WHITE);
        btnCadastrarPedido.setFont(new Font("Segoe UI", Font.BOLD, 12));

        form.add(new JLabel(" Cliente:"));
        form.add(txtNomeCliente);
        form.add(new JLabel(" Endereço de Entrega:"));
        form.add(txtEndereco);
        form.add(new JLabel(" Relação de Itens:"));
        form.add(new JScrollPane(areaProdutos));
        form.add(new JLabel(" Forma de Pagamento:"));
        form.add(comboPagamento);
        form.add(new JLabel(" Logística de Entrega:"));
        form.add(retirarPedido);
        form.add(new JLabel(" Total Geral (R$):"));
        form.add(txtTotalPedido);
        form.add(btnImportarWeb);
        form.add(btnCadastrarPedido);

        areaPedido = new JTextArea();
        areaPedido.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaPedido.setEditable(false);
        JScrollPane scrollPedidos = new JScrollPane(areaPedido);

        painel.add(form, BorderLayout.WEST);
        painel.add(scrollPedidos, BorderLayout.CENTER);

        btnCadastrarPedido.addActionListener(e -> cadastrarPedido());
        btnImportarWeb.addActionListener(e -> importarPedidoDoClipboard());

        return painel;
    }

    private void importarPedidoDoClipboard() {
        try {
            String dadosCopiados = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);

            if (dadosCopiados != null && dadosCopiados.contains(";")) {
                String[] partes = dadosCopiados.split(";");

                if (partes.length >= 7) {
                    txtNomeCliente.setText(partes[1].trim());
                    txtEndereco.setText(partes[2].trim());
                    areaProdutos.setText(partes[3].trim().replace(", ", "\n"));

                    selecionarItemCombo(comboPagamento, partes[4].trim());

                    String tipoWeb = partes[5].trim();
                    if (tipoWeb.equalsIgnoreCase("Retirada")) {
                        retirarPedido.setSelectedItem("Retirar da Loja");
                    } else {
                        selecionarItemCombo(retirarPedido, tipoWeb);
                    }

                    txtTotalPedido.setText(partes[6].trim());
                    JOptionPane.showMessageDialog(this, "Dados sincronizados com sucesso.", "Integração Concluída",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nenhum dado estruturado válido encontrado na Área de Transferência.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao ler a Área de Transferência: " + ex.getMessage(),
                    "Erro Estrutural", JOptionPane.ERROR_MESSAGE);
        }
    }

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
            JOptionPane.showMessageDialog(this, "Funcionário registado com sucesso.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados numéricos: " + ex.getMessage(), "Erro de Validação",
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
            JOptionPane.showMessageDialog(this, "Pedido validado e guardado no sistema.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao processar pedido: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarListaFuncionarios() {
        areaFuncionarios.setText("");
        double totalFolha = 0;
        for (Funcionario f : listaFuncionarios) {
            areaFuncionarios.append("Colaborador: " + f.getNome() + " | NIF/CPF: " + f.getCpf() + "\n");
            areaFuncionarios.append("Vencimento de Folha: R$ " + String.format("%.2f", f.calcularSalario()) + "\n");
            areaFuncionarios.append("----------------------------------------------------------------------\n");
            totalFolha += f.calcularSalario();
        }
        lblTotalFolha.setText("Total da Folha Salarial: R$ " + String.format("%.2f", totalFolha));
    }

    private void atualizarListaPedidos() {
        areaPedido.setText("");
        for (Pedido p : listaPedidos) {
            areaPedido.append("ID Pedido: #" + p.getNumeroPedido() + "\n");
            areaPedido.append("Cliente: " + p.getNomeCliente() + " | Logística: " + p.retirarPedido() + "\n");
            areaPedido.append("Itens Solicitados:\n" + p.getProdutosSelecionados() + "\n");
            areaPedido.append("Liquidação via: " + p.getFormaPagamento() + " | Valor: R$ "
                    + String.format("%.2f", p.getTotalPagar()) + "\n");
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