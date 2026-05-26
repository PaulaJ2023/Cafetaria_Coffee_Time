package POOJava;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor; // Importante para ler a área de transferência
import java.awt.Toolkit; // Importante para acessar o Clipboard do sistema
import java.util.ArrayList;

public class TelaFuncionarios extends JFrame {

    // Campos de texto adaptados para bater com as variáveis do código
    private JTextField txtNome, txtCpf, txtSalario, txtValorHora, txtHorasTrabalhadas, txtGratificacao,
            txtParticipacaoLucros, txtBolsaAuxilio;
    private JComboBox<String> comboTipo;
    private JTextArea areaResultado;
    private JLabel lblTotalFolha; // Label para mostrar o total da folha salarial

    private ArrayList<Funcionario> lista = new ArrayList<>();

    public TelaFuncionarios() {
        setTitle("Sistema de Gerenciamento de Funcionários");
        setSize(580, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Painel de Cadastro (GridLayout expandido para 9 linhas)
        JPanel painelTopo = new JPanel(new GridLayout(9, 2, 5, 5));
        painelTopo.setBorder(BorderFactory.createTitledBorder("Cadastro de Funcionários"));

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtSalario = new JTextField();
        txtValorHora = new JTextField();
        txtHorasTrabalhadas = new JTextField();
        txtGratificacao = new JTextField();
        txtParticipacaoLucros = new JTextField();
        txtBolsaAuxilio = new JTextField();

        String[] tipos = { "Funcionário Comum", "Gerente", "Estagiário" };
        comboTipo = new JComboBox<>(tipos);

        // Adicionando os componentes no painel de cima
        painelTopo.add(new JLabel(" Tipo de Cargo:"));
        painelTopo.add(comboTipo);
        painelTopo.add(new JLabel(" Nome:"));
        painelTopo.add(txtNome);
        painelTopo.add(new JLabel(" CPF:"));
        painelTopo.add(txtCpf);
        painelTopo.add(new JLabel(" Salário Base:"));
        painelTopo.add(txtSalario);
        painelTopo.add(new JLabel(" Valor da Hora:"));
        painelTopo.add(txtValorHora);
        painelTopo.add(new JLabel(" Horas Trabalhadas:"));
        painelTopo.add(txtHorasTrabalhadas);
        painelTopo.add(new JLabel(" Gratificação (Apenas Gerente):"));
        painelTopo.add(txtGratificacao);
        painelTopo.add(new JLabel(" Part. Lucros (Apenas Gerente):"));
        painelTopo.add(txtParticipacaoLucros);
        painelTopo.add(new JLabel(" Bolsa Auxílio (Apenas Estagiário):"));
        painelTopo.add(txtBolsaAuxilio);

        add(painelTopo, BorderLayout.NORTH);

        // Painel Central (Exibição dos resultados cadastrados)
        JPanel painelCentral = new JPanel(new BorderLayout());
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultado);
        painelCentral.add(scroll, BorderLayout.CENTER);

        // Inicializa a label de totalização da folha
        lblTotalFolha = new JLabel("Total da Folha Salarial: R$ 0,00", SwingConstants.RIGHT);
        lblTotalFolha.setFont(new Font("Arial", Font.BOLD, 13));
        lblTotalFolha.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        painelCentral.add(lblTotalFolha, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);

        // --- PAINEL DE BOTÕES TOTALMENTE ARRUMADO ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnCadastrar = new JButton("Cadastrar 💾");
        JButton btnLimpar = new JButton("Limpar 🧹");
        JButton btnFechar = new JButton("Fechar ❌");

        // Botão de Importação customizado estilo Web
        JButton btnImportarWeb = new JButton("Importar da Web 👤☕");
        btnImportarWeb.setBackground(new Color(69, 182, 156)); // Verde igual ao site
        btnImportarWeb.setForeground(Color.WHITE);
        btnImportarWeb.setFont(new Font("Arial", Font.BOLD, 12));
        btnImportarWeb.setFocusPainted(false);

        // Adicionando os botões na ordem correta e organizada
        painelBotoes.add(btnImportarWeb);
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnFechar);

        add(painelBotoes, BorderLayout.SOUTH);

        // Configuração das ações de clique dos botões
        btnCadastrar.addActionListener(e -> cadastrarFuncionario());
        btnLimpar.addActionListener(e -> limparCampos());
        btnFechar.addActionListener(e -> dispose());
        btnImportarWeb.addActionListener(e -> importarFuncionarioDaWeb());
    }

    private void cadastrarFuncionario() {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            double salario = Double.parseDouble(txtSalario.getText());
            double valorHora = Double.parseDouble(txtValorHora.getText());
            double horas = Double.parseDouble(txtHorasTrabalhadas.getText());

            int tipoSelecionado = comboTipo.getSelectedIndex();

            if (tipoSelecionado == 0) { // Funcionário Comum
                Funcionario f = new Funcionario(nome, cpf, salario, valorHora, horas);
                lista.add(f);
            } else if (tipoSelecionado == 1) { // Gerente
                double gratificacao = Double.parseDouble(txtGratificacao.getText());
                double lucros = Double.parseDouble(txtParticipacaoLucros.getText());
                Gerente g = new Gerente(nome, cpf, salario, valorHora, horas, gratificacao, lucros);
                lista.add(g);
            } else if (tipoSelecionado == 2) { // Estagiário
                double bolsa = Double.parseDouble(txtBolsaAuxilio.getText());
                Estagiario est = new Estagiario(nome, cpf, salario, valorHora, horas, bolsa);
                lista.add(est);
            }

            atualizarTela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Certifique-se de preencher os campos numéricos corretamente!",
                    "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    private void atualizarTela() {
        areaResultado.setText("");
        double totalFolha = 0;

        for (Funcionario f : lista) {
            areaResultado.append("--------------------------------------------------\n");
            areaResultado.append("Nome: " + f.getNome() + " | CPF: " + f.getCpf() + "\n");
            areaResultado.append("Salário Calculado: R$ " + String.format("%.2f", f.calcularSalario()) + "\n");

            totalFolha += f.calcularSalario();
        }

        lblTotalFolha.setText("Total da Folha Salarial: R$ " + String.format("%.2f", totalFolha));
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtSalario.setText("");
        txtValorHora.setText("");
        txtHorasTrabalhadas.setText("");
        txtGratificacao.setText("");
        txtParticipacaoLucros.setText("");
        txtBolsaAuxilio.setText("");
    }

    // ====== FUNÇÃO DE IMPORTAÇÃO: APENAS PREENCHE OS CAMPOS DA TELA ======
    private void importarFuncionarioDaWeb() {
        try {
            // 1. Pega o texto da área de transferência (o seu Ctrl+V do botão do site)
            String dadosCopiados = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);

            // Validação básica do formato
            if (dadosCopiados == null || dadosCopiados.trim().isEmpty() || !dadosCopiados.contains(";")) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ Nenhum dado de funcionário válido na área de transferência.\n" +
                                "Vá ao site de Admin e clique em 'Copiar Funcionário para o Java'.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Divide os dados da String: Nome;Email;Cargo
            String[] partes = dadosCopiados.split(";");
            if (partes.length < 3) {
                JOptionPane.showMessageDialog(this, "⚠️ O texto copiado está incompleto ou corrompido.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nome = partes[0].trim();
            String email = partes[1].trim();
            String cargo = partes[2].trim();

            // Limpa antes de injetar os novos valores
            limparCampos();

            // Preenche o nome e sugere uma máscara de CPF provisória
            txtNome.setText(nome);
            txtCpf.setText("000.000.000-00");

            // 3. Identifica o cargo e muda o ComboBox + sugere valores fictícios adequados
            if (cargo.equalsIgnoreCase("Gerente Geral") || cargo.equalsIgnoreCase("Gerente")) {
                comboTipo.setSelectedIndex(1); // Modifica o seletor para "Gerente"
                txtSalario.setText("4500.00");
                txtValorHora.setText("0.0");
                txtHorasTrabalhadas.setText("0.0");
                txtGratificacao.setText("500.00");
                txtParticipacaoLucros.setText("250.00");
                txtBolsaAuxilio.setText("0.0");
            } else if (cargo.equalsIgnoreCase("Estagiário") || cargo.equalsIgnoreCase("Estagiario")) {
                comboTipo.setSelectedIndex(2); // Modifica o seletor para "Estagiário"
                txtSalario.setText("0.0");
                txtValorHora.setText("0.0");
                txtHorasTrabalhadas.setText("0.0");
                txtGratificacao.setText("0.0");
                txtParticipacaoLucros.setText("0.0");
                txtBolsaAuxilio.setText("1200.00");
            } else {
                comboTipo.setSelectedIndex(0); // Modifica o seletor para "Funcionário Comum"
                txtSalario.setText("2200.00");
                txtValorHora.setText("0.0");
                txtHorasTrabalhadas.setText("0.0");
                txtGratificacao.setText("0.0");
                txtParticipacaoLucros.setText("0.0");
                txtBolsaAuxilio.setText("0.0");
            }

            JOptionPane.showMessageDialog(this,
                    "✨ Campos preenchidos com os dados de " + nome + "!\n" +
                            "📝 Altere o CPF/Valores se necessário e clique em 'Cadastrar 💾'.",
                    "Importado com Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao preencher os campos: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaFuncionarios().setVisible(true);
        });
    }
}