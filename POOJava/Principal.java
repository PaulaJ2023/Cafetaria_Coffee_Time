package POOJava;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor; // Importante para ler a área de transferência
import java.awt.Toolkit; // Importante para acessar o Clipboard do sistema
import java.util.ArrayList;

public class Principal {

    // --- JANELA GERENCIADA POR COMPOSIÇÃO ---
    private JFrame janela;

    // --- LISTAS EM MEMÓRIA (Atuam como o Banco de Dados do Sistema) ---
    private ArrayList<Funcionario> listaFuncionarios = new ArrayList<>();
    private ArrayList<Pedido> listaPedidos = new ArrayList<>();
    private int contadorPedidos = 1;

    // --- COMPONENTES DA ABA DE FUNCIONÁRIOS ---
    private JTextField txtNomeFunc, txtCpfFunc, txtSalario, txtValorHora, txtHorasFunc, txtGratificacao, txtLucros,
            txtBolsa;
    private JComboBox<String> comboCargo;
    private JTextArea areaFuncionarios;
    private JLabel lblTotalFolha;

    // --- COMPONENTES DA ABA DE PEDIDOS ---
    private JTextField txtNomeCliente, txtEndereco, txtTotalPedido;
    private JTextArea areaProdutos, areaPedido;
    private JComboBox<String> comboPagamento, retirarPedido;

    public Principal() {
        // Inicializa a janela interna de forma limpa e organizada
        janela = new JFrame("Coffee Time ☕ - Sistema Integrado de Gestão");
        janela.setSize(850, 700);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(new BorderLayout());

        // Criando o sistema de abas do Painel
        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Gestão de Funcionários 👥", criarAbaFuncionarios());
        abas.addTab("Anotação de Pedidos 📜", criarAbaPedidos());

        // Título Superior Charmoso
        JLabel lblTituloGeral = new JLabel("Coffee Time ☕ - Painel de Controle", SwingConstants.CENTER);
        lblTituloGeral.setFont(new Font("Arial", Font.BOLD, 18));
        lblTituloGeral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adicionando os componentes estruturais à janela
        janela.add(lblTituloGeral, BorderLayout.NORTH);
        janela.add(abas, BorderLayout.CENTER);
    }

    // Método para tornar a janela visível
    public void exibir() {
        janela.setVisible(true);
    }

    // =========================================================================
    // 1. CONSTRUÇÃO DA ABA DE FUNCIONÁRIOS
    // =========================================================================
    private JPanel criarAbaFuncionarios() {
        JPanel painelGeral = new JPanel(new BorderLayout(10, 10));
        painelGeral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelForm = new JPanel(new GridLayout(9, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createTitledBorder("Informações do Colaborador"));

        txtNomeFunc = new JTextField();
        txtCpfFunc = new JTextField();
        txtSalario = new JTextField("0.0");
        txtValorHora = new JTextField("0.0");
        txtHorasFunc = new JTextField("0.0");
        txtGratificacao = new JTextField("0.0");
        txtLucros = new JTextField("0.0");
        txtBolsa = new JTextField("0.0");

        String[] cargos = { "Funcionário Comum", "Gerente", "Estagiário" };
        comboCargo = new JComboBox<>(cargos);

        painelForm.add(new JLabel(" Tipo de Cargo:"));
        painelForm.add(comboCargo);
        painelForm.add(new JLabel(" Nome:"));
        painelForm.add(txtNomeFunc);
        painelForm.add(new JLabel(" CPF:"));
        painelForm.add(txtCpfFunc);
        painelForm.add(new JLabel(" Salário Base:"));
        painelForm.add(txtSalario);
        painelForm.add(new JLabel(" Valor da Hora:"));
        painelForm.add(txtValorHora);
        painelForm.add(new JLabel(" Horas Trabalhadas:"));
        painelForm.add(txtHorasFunc);
        painelForm.add(new JLabel(" Gratificação (Gerente):"));
        painelForm.add(txtGratificacao);
        painelForm.add(new JLabel(" Part. Lucros (Gerente):"));
        painelForm.add(txtLucros);
        painelForm.add(new JLabel(" Bolsa Auxílio (Estagiário):"));
        painelForm.add(txtBolsa);

        painelGeral.add(painelForm, BorderLayout.NORTH);

        JPanel painelCentro = new JPanel(new BorderLayout());
        areaFuncionarios = new JTextArea();
        areaFuncionarios.setEditable(false);
        painelCentro.add(new JScrollPane(areaFuncionarios), BorderLayout.CENTER);

        lblTotalFolha = new JLabel("Total da Folha Salarial: R$ 0,00", SwingConstants.RIGHT);
        lblTotalFolha.setFont(new Font("Arial", Font.BOLD, 13));
        lblTotalFolha.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelCentro.add(lblTotalFolha, BorderLayout.SOUTH);

        painelGeral.add(painelCentro, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnImportarFunc = new JButton("Importar da Web 👤☕");
        JButton btnCadastrar = new JButton("Cadastrar 💾");
        JButton btnLimpar = new JButton("Limpar 🧹");

        btnImportarFunc.setBackground(new Color(69, 182, 156));
        btnImportarFunc.setForeground(Color.WHITE);
        btnImportarFunc.setFont(new Font("Arial", Font.BOLD, 12));
        btnImportarFunc.setFocusPainted(false);

        painelBotoes.add(btnImportarFunc);
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnLimpar);
        painelGeral.add(painelBotoes, BorderLayout.SOUTH);

        btnImportarFunc.addActionListener(e -> importarFuncionarioDaWeb());
        btnCadastrar.addActionListener(e -> cadastrarFuncionario());
        btnLimpar.addActionListener(e -> limparCamposFuncionario());

        return painelGeral;
    }

    // =========================================================================
    // 2. CONSTRUÇÃO DA ABA DE PEDIDOS (COM IMPORTAR INTEGRADO)
    // =========================================================================
    private JPanel criarAbaPedidos() {
        JPanel painelGeral = new JPanel(new BorderLayout(10, 10));
        painelGeral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulário de Entrada de Dados do Pedido
        JPanel painelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createTitledBorder("Anotação do Pedido"));

        txtNomeCliente = new JTextField();
        txtEndereco = new JTextField();
        txtTotalPedido = new JTextField();

        String[] pagamentos = { "Pix", "Cartão de Crédito", "Cartão de Débito", "Dinheiro" };
        comboPagamento = new JComboBox<>(pagamentos);

        String[] formasEntrega = { "Entrega (Delivery)", "Retirada no Balcão" };
        retirarPedido = new JComboBox<>(formasEntrega);

        painelForm.add(new JLabel(" Nome do Cliente:"));
        painelForm.add(txtNomeCliente);
        painelForm.add(new JLabel(" Endereço de Entrega:"));
        painelForm.add(txtEndereco);
        painelForm.add(new JLabel(" Forma de Pagamento:"));
        painelForm.add(comboPagamento);
        painelForm.add(new JLabel(" Modo de Retirada:"));
        painelForm.add(retirarPedido);
        painelForm.add(new JLabel(" Total a Pagar (R$):"));
        painelForm.add(txtTotalPedido);

        // Painel Dividido do Meio (Esquerda: Itens / Direita: Histórico de Pedidos)
        JPanel painelMeio = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel painelProdutosInput = new JPanel(new BorderLayout());
        painelProdutosInput
                .setBorder(BorderFactory.createTitledBorder("Produtos Selecionados (Ex: 1x Café, 2x Cookie)"));
        areaProdutos = new JTextArea();
        painelProdutosInput.add(new JScrollPane(areaProdutos), BorderLayout.CENTER);

        JPanel painelHistoricoPedidos = new JPanel(new BorderLayout());
        painelHistoricoPedidos.setBorder(BorderFactory.createTitledBorder("Histórico de Pedidos Emitidos"));
        areaPedido = new JTextArea();
        areaPedido.setEditable(false);
        painelHistoricoPedidos.add(new JScrollPane(areaPedido), BorderLayout.CENTER);

        painelMeio.add(painelProdutosInput);
        painelMeio.add(painelHistoricoPedidos);

        // Agrupando o formulário e as caixas de texto textuais no centro
        JPanel painelSuperiorEPedidos = new JPanel(new BorderLayout(5, 5));
        painelSuperiorEPedidos.add(painelForm, BorderLayout.NORTH);
        painelSuperiorEPedidos.add(painelMeio, BorderLayout.CENTER);
        painelGeral.add(painelSuperiorEPedidos, BorderLayout.CENTER);

        // Painel de Ações e Botões de Pedido no Rodapé
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnImportarPedido = new JButton("Importar Pedido da Web 🛒☕");
        JButton btnLancarPedido = new JButton("Lançar Pedido 💾");
        JButton btnLimparPedido = new JButton("Limpar Campos 🧹");

        // Estilização charmosa do botão de importar para combinar com a interface do
        // Coffee Time
        btnImportarPedido.setBackground(new Color(192, 108, 132)); // Cor que combina com o CSS do Admin da Web
        btnImportarPedido.setForeground(Color.WHITE);
        btnImportarPedido.setFont(new Font("Arial", Font.BOLD, 12));
        btnImportarPedido.setFocusPainted(false);

        painelBotoes.add(btnImportarPedido);
        painelBotoes.add(btnLancarPedido);
        painelBotoes.add(btnLimparPedido);
        painelGeral.add(painelBotoes, BorderLayout.SOUTH);

        // Conexão dos Listeners
        btnImportarPedido.addActionListener(e -> importarPedidoDaWeb());
        btnLancarPedido.addActionListener(e -> lancarPedido());
        btnLimparPedido.addActionListener(e -> limparCamposPedido());

        return painelGeral;
    }

    // =========================================================================
    // 3. LOGICA DE NEGÓCIO - IMPORTAÇÃO DE PEDIDO DA WEB
    // =========================================================================
    private void importarPedidoDaWeb() {
        try {
            // Captura os dados brutos salvos na área de transferência (Ctrl+C / Ctrl+V)
            String dadosCopiados = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);

            // Validação de segurança básica se o dado está vazio ou mal estruturado
            if (dadosCopiados == null || dadosCopiados.trim().isEmpty() || !dadosCopiados.contains(";")) {
                JOptionPane.showMessageDialog(janela,
                        "⚠️ Nenhum dado de pedido válido na área de transferência.\n" +
                                "Acesse o painel web, localize 'Último Pedido' e clique em 'Copiar Dados para o Java ☕'.",
                        "Aviso de Sincronização", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // O separador criado no arquivo admin.js é o ";"
            String[] partes = dadosCopiados.split(";");
            if (partes.length < 7) {
                JOptionPane.showMessageDialog(janela,
                        "⚠️ Os dados estruturados do pedido parecem corrompidos ou incompletos.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Atribuição de variáveis baseadas na ordem exata definida no JavaScript
            String idWeb = partes[0].trim();
            String cliente = partes[1].trim();
            String endereco = partes[2].trim();
            String itens = partes[3].trim();
            String pagamento = partes[4].trim();
            String tipoEntrega = partes[5].trim();
            String total = partes[6].trim();

            limparCamposPedido();

            // Insere os valores copiados direto nas caixas de texto correspondentes da tela
            txtNomeCliente.setText(cliente);
            txtEndereco.setText(endereco);
            areaProdutos.setText(itens.replace(", ", "\n")); // Separa cada item por linha para melhor legibilidade
            txtTotalPedido.setText(total);

            // Sincroniza o JComboBox de Forma de Pagamento
            for (int i = 0; i < comboPagamento.getItemCount(); i++) {
                if (comboPagamento.getItemAt(i).equalsIgnoreCase(pagamento)) {
                    comboPagamento.setSelectedIndex(i);
                    break;
                }
            }

            // Sincroniza o JComboBox de Modo de Retirada / Tipo de Entrega
            if (tipoEntrega.equalsIgnoreCase("Delivery")) {
                retirarPedido.setSelectedIndex(0); // Entrega (Delivery)
            } else {
                retirarPedido.setSelectedIndex(1); // Retirada no Balcão
            }

            JOptionPane.showMessageDialog(janela,
                    "🎉 Último Pedido " + idWeb + " de " + cliente + " importado com sucesso!\n" +
                            "Verifique os dados e clique em 'Lançar Pedido 💾' para arquivar.",
                    "Integração Concluída", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(janela, "Falha na leitura do clipboard do sistema: " + ex.getMessage(),
                    "Erro de Integração", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // 4. MÉTODOS ADICIONAIS DE FUNCIONÁRIOS
    // =========================================================================
    private void importarFuncionarioDaWeb() {
        try {
            String dadosCopiados = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);

            if (dadosCopiados == null || dadosCopiados.trim().isEmpty() || !dadosCopiados.contains(";")) {
                JOptionPane.showMessageDialog(janela,
                        "⚠️ Nenhum dado de funcionário válido na área de transferência.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] partes = dadosCopiados.split(";");
            String nome = partes[0].trim();
            String cargo = partes[2].trim();

            limparCamposFuncionario();
            txtNomeFunc.setText(nome);
            txtCpfFunc.setText("000.000.000-00");

            if (cargo.equalsIgnoreCase("Gerente Geral") || cargo.equalsIgnoreCase("Gerente")) {
                comboCargo.setSelectedIndex(1);
                txtSalario.setText("4500.00");
                txtGratificacao.setText("500.00");
                txtLucros.setText("250.00");
            } else if (cargo.equalsIgnoreCase("Estagiário") || cargo.equalsIgnoreCase("Estagiario")) {
                comboCargo.setSelectedIndex(2);
                txtSalario.setText("0.0");
                txtBolsa.setText("1200.00");
            } else {
                comboCargo.setSelectedIndex(0);
                txtSalario.setText("2200.00");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(janela, "Erro na importação: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarFuncionario() {
        try {
            String nome = txtNomeFunc.getText();
            String cpf = txtCpfFunc.getText();
            double salario = Double.parseDouble(txtSalario.getText());
            double valorHora = Double.parseDouble(txtValorHora.getText());
            double horas = Double.parseDouble(txtHorasFunc.getText());

            int cargoIndex = comboCargo.getSelectedIndex();

            if (cargoIndex == 0) {
                listaFuncionarios.add(new Funcionario(nome, cpf, salario, valorHora, horas));
            } else if (cargoIndex == 1) {
                double gratificacao = Double.parseDouble(txtGratificacao.getText());
                double lucros = Double.parseDouble(txtLucros.getText());
                listaFuncionarios.add(new Gerente(nome, cpf, salario, valorHora, horas, gratificacao, lucros));
            } else if (cargoIndex == 2) {
                double bolsaAuxilio = Double.parseDouble(txtBolsa.getText());
                listaFuncionarios.add(new Estagiario(nome, cpf, salario, valorHora, horas, bolsaAuxilio));
            }

            atualizarListaFuncionarios();
            limparCamposFuncionario();
            JOptionPane.showMessageDialog(janela, "Funcionário cadastrado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(janela, "Erro: Verifique os dados numéricos!", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarListaFuncionarios() {
        areaFuncionarios.setText("");
        double totalFolha = 0;
        for (Funcionario f : listaFuncionarios) {
            areaFuncionarios.append("--------------------------------------------------\n");
            areaFuncionarios.append("Nome: " + f.getNome() + " | CPF: " + f.getCpf() + "\n");
            areaFuncionarios.append("Salário Calculado: R$ " + String.format("%.2f", f.calcularSalario()) + "\n");
            totalFolha += f.calcularSalario();
        }
        lblTotalFolha.setText("Total da Folha Salarial: R$ " + String.format("%.2f", totalFolha));
    }

    private void limparCamposFuncionario() {
        txtNomeFunc.setText("");
        txtCpfFunc.setText("");
        txtSalario.setText("0.0");
        txtValorHora.setText("0.0");
        txtHorasFunc.setText("0.0");
        txtGratificacao.setText("0.0");
        txtLucros.setText("0.0");
        txtBolsa.setText("0.0");
        comboCargo.setSelectedIndex(0);
    }

    // =========================================================================
    // 5. REGRA DE LANÇAMENTO E ATUALIZAÇÃO DE PEDIDOS
    // =========================================================================
    private void lancarPedido() {
        try {
            String nomeCliente = txtNomeCliente.getText();
            String endereco = txtEndereco.getText();
            String produtos = areaProdutos.getText();
            String pagamento = (String) comboPagamento.getSelectedItem();
            String modoRetirada = (String) retirarPedido.getSelectedItem();
            double total = Double.parseDouble(txtTotalPedido.getText());

            if (nomeCliente.trim().isEmpty() || produtos.trim().isEmpty()) {
                JOptionPane.showMessageDialog(janela, "Por favor, preencha o nome do cliente e os produtos!",
                        "Campos Vazios", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Pedido novoPedido = new Pedido(contadorPedidos++, nomeCliente, endereco, produtos, pagamento, modoRetirada,
                    total);
            listaPedidos.add(novoPedido);

            atualizarListaPedidos();
            limparCamposPedido();
            JOptionPane.showMessageDialog(janela, "Pedido lançado no sistema com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(janela, "Insira um valor numérico válido para o total do pedido!",
                    "Erro no Total", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarListaPedidos() {
        areaPedido.setText("");
        for (Pedido p : listaPedidos) {
            areaPedido.append("Pedido Nº: #" + p.getNumeroPedido() + "\n");
            areaPedido.append("Cliente: " + p.getNomeCliente() + "\n");
            areaPedido.append("Produtos:\n" + p.getProdutosSelecionados() + "\n");
            areaPedido.append("Pagamento: " + p.getFormaPagamento() + " | " + p.retirarPedido() + "\n");
            areaPedido.append("Total: R$ " + String.format("%.2f", p.getTotalPagar()) + "\n");
            areaPedido.append("--------------------------------------------------\n");
        }
    }

    private void limparCamposPedido() {
        txtNomeCliente.setText("");
        txtEndereco.setText("");
        txtTotalPedido.setText("");
        areaProdutos.setText("");
        comboPagamento.setSelectedIndex(0);
        retirarPedido.setSelectedIndex(0);
    }

    // --- MÉTODO MAIN ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Principal app = new Principal();
            app.exibir();
        });
    }
}

/*
 * public class Principal {
 * public static void main(String[] args) {
 * Scanner leitor = new Scanner(System.in);
 * ArrayList<Funcionario> lista = new ArrayList<>();
 * ArrayList<Cliente> listaClientes = new ArrayList<>();
 * // ArrayList<Fornecedor> listaFornecedores = new ArrayList<>();
 * int opcao;
 * do {
 * System.out.println("-menu-");
 * System.out.println("1- Cadastrar funcionario");
 * System.out.println("2- Listar funcionario");
 * System.out.println("3- Calcular folha salarial");
 * System.out.println("4 - Cadastrar cliente");
 * // System.out.println("5 - Cadastrar fornecedor");
 * System.out.println("0- Sair");
 * System.out.println("escolha uma opçâo");
 * opcao = leitor.nextInt();
 * leitor.nextLine();
 * if (opcao == 1) {
 * System.out.println("\nTipo(1- funcionario | 2-Gerente | 3-Estagiario): ");
 * int tipo_funci = leitor.nextInt();
 * leitor.nextLine();
 * System.out.println("nome");
 * String nome = leitor.nextLine();
 * System.out.println("cpf");
 * String cpf = leitor.nextLine();
 * Funcionario f;
 * if (tipo_funci == 2) {
 * System.out.println("salario");
 * double salario = leitor.nextDouble();
 * System.out.println("valor hora");
 * double valorHora = leitor.nextDouble();
 * System.out.println("horas trabalhadas");
 * double horasTrabalhadas = leitor.nextDouble();
 * System.out.println("gratificação");
 * double gratificacao = leitor.nextDouble();
 * System.out.println("participação nos lucros");
 * double participacaoLucros = leitor.nextDouble();
 * f = new Gerente(nome, cpf, salario, valorHora, horasTrabalhadas,
 * gratificacao, participacaoLucros);
 * } else if (tipo_funci == 3) {
 * System.out.println("salario");
 * double salario = leitor.nextDouble();
 * System.out.println("valor hora");
 * double valorHora = leitor.nextDouble();
 * System.out.println("horas trabalhadas");
 * double horasTrabalhadas = leitor.nextDouble();
 * System.out.println("bolsa auxilio");
 * double bolsaAuxilio = leitor.nextDouble();
 * f = new Estagiario(nome, cpf, salario, valorHora, horasTrabalhadas,
 * bolsaAuxilio);
 * } else {
 * System.out.println("salario");
 * double salario = leitor.nextDouble();
 * System.out.println("valor hora");
 * double valorHora = leitor.nextDouble();
 * System.out.println("horas trabalhadas");
 * double horasTrabalhadas = leitor.nextDouble();
 * f = new Funcionario(nome, cpf, salario, valorHora, horasTrabalhadas);
 * }
 * lista.add(f);
 * System.out.println("funcionario cadastrado com sucesso");
 * } else if (opcao == 2) {
 * System.out.println("--listando funcionario--");
 * for (int i = 0; i < lista.size(); i++) {
 * lista.get(i).exibirDados();
 * }
 * } else if (opcao == 3) {
 * // calcular valor total
 * double total = 0;
 * int i = 0;
 * while (i < lista.size()) {
 * total += lista.get(i).calcularSalario();
 * i++;
 * }
 * System.out.println("total da folha salarial" + total);
 * } else if (opcao == 4) {
 * System.out.println("nome do cliente");
 * String nome = leitor.nextLine();
 * System.out.println("cpf do cliente");
 * String cpf = leitor.nextLine();
 * System.out.println("Telefone do cliente");
 * String telefone = leitor.nextLine();
 * System.out.println("endereço do cliente");
 * String endereco = leitor.nextLine();
 * System.out.println("pagamento do cliente");
 * double pagamento = leitor.nextDouble();
 * leitor.nextLine();// limpar buffer
 * Cliente c = new Cliente(nome, cpf, telefone, endereco, pagamento);
 * listaClientes.add(c);
 * System.out.println("cliente cadastrado com sucesso");
 * }
 * } while (opcao != 0);
 * leitor.close();
 * }
 * }
 */

/*
 * Parte do gerente desativado
 * else if (opcao == 5){
 * System.out.println("nome do fornecedor");
 * String nome = leitor.nextLine();
 * System.out.println("cpf do fornecedor");
 * String cpf = leitor.nextLine();
 * System.out.println("cnpj do fornecedor");
 * String cnpj = leitor.nextLine();
 * System.out.println("Telefone do fornecedor");
 * String telefone = leitor.nextLine();
 * System.out.println("endereço do fornecedor");
 * String endereco = leitor.nextLine();
 * System.out.println("pagamento do fornecedor");
 * double pagamento = leitor.nextDouble();
 * leitor.nextLine();//limpar buffer
 * Fornecedor f = new Fornecedor(nome, cpf, telefone, endereco, cnpj,
 * pagamento);
 * listaFornecedores.add(f);
 * System.out.println("fornecedor cadastrado com sucesso");
 * }
 */