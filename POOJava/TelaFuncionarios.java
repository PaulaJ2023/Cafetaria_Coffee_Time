package POOJava;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaFuncionarios extends JFrame {

    // Campos de texto adaptados para bater com as variáveis do código anterior
    private JTextField txtNome, txtCpf, txtSalario, txtValorHora, txtHorasTrabalhadas, txtGratificacao,
            txtParticipacaoLucros, txtBolsaAuxilio;
    private JComboBox<String> comboTipo;
    private JTextArea areaResultado;
    private JLabel lblTotalFolha; // Label para mostrar o total da folha salarial

    private ArrayList<Funcionario> lista = new ArrayList<>();

    public TelaFuncionarios() {
        setTitle("Sistema de Gerenciamento de Funcionários");
        setSize(550, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Painel de Cadastro (GridLayout expandido para 9 linhas para caber todos os
        // novos campos)
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

        comboTipo = new JComboBox<>(new String[] { "Funcionario", "Gerente", "Estagiario" });

        painelTopo.add(new JLabel("Tipo:"));
        painelTopo.add(comboTipo);
        painelTopo.add(new JLabel("Nome:"));
        painelTopo.add(txtNome);
        painelTopo.add(new JLabel("CPF:"));
        painelTopo.add(txtCpf);
        painelTopo.add(new JLabel("Salário Base:"));
        painelTopo.add(txtSalario);
        painelTopo.add(new JLabel("Valor Hora:"));
        painelTopo.add(txtValorHora);
        painelTopo.add(new JLabel("Horas Trabalhadas:"));
        painelTopo.add(txtHorasTrabalhadas);
        painelTopo.add(new JLabel("Gratificação (Apenas Gerente):"));
        painelTopo.add(txtGratificacao);
        painelTopo.add(new JLabel("Part. Lucros (Apenas Gerente):"));
        painelTopo.add(txtParticipacaoLucros);
        painelTopo.add(new JLabel("Bolsa Auxílio (Apenas Estagiário):"));
        painelTopo.add(txtBolsaAuxilio);

        add(painelTopo, BorderLayout.NORTH);

        // Área de resultado central com Scroll
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        // Painel Inferior (Botão de Cadastrar + Totalizador de Folha Salarial)
        JPanel painelInferior = new JPanel(new BorderLayout());

        JButton btnCadastrar = new JButton("Cadastrar Funcionário");
        btnCadastrar.setBackground(new Color(70, 130, 180));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));

        lblTotalFolha = new JLabel("Total da Folha Salarial: R$ 0.00", SwingConstants.CENTER);
        lblTotalFolha.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalFolha.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelInferior.add(btnCadastrar, BorderLayout.NORTH);
        painelInferior.add(lblTotalFolha, BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);

        // Evento do botão cadastrar
        btnCadastrar.addActionListener(e -> cadastrarFuncionario());
    }

    private void cadastrarFuncionario() {
        try {
            // Campos comuns a todos os tipos
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            double salario = Double.parseDouble(txtSalario.getText());
            double valorHora = Double.parseDouble(txtValorHora.getText());
            double horasTrabalhadas = Double.parseDouble(txtHorasTrabalhadas.getText());

            String tipo = comboTipo.getSelectedItem().toString();
            Funcionario f;

            // Polimorfismo aplicado na criação dos objetos seguindo seus construtores
            // anteriores
            if (tipo.equals("Gerente")) {
                double gratificacao = Double.parseDouble(txtGratificacao.getText());
                double participacaoLucros = Double.parseDouble(txtParticipacaoLucros.getText());

                f = new Gerente(nome, cpf, salario, valorHora, horasTrabalhadas, gratificacao, participacaoLucros);

            } else if (tipo.equals("Estagiario")) {
                double bolsaAuxilio = Double.parseDouble(txtBolsaAuxilio.getText());

                f = new Estagiario(nome, cpf, salario, valorHora, horasTrabalhadas, bolsaAuxilio);

            } else {
                f = new Funcionario(nome, cpf, salario, valorHora, horasTrabalhadas);
            }

            lista.add(f);

            atualizarTela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique se preencheu os campos numéricos corretamente!",
                    "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    private void atualizarTela() {
        areaResultado.setText("");
        double totalFolha = 0;

        // O polimorfismo acontece aqui: f.calcularSalario() executa a regra da classe
        // específica
        for (Funcionario f : lista) {
            areaResultado.append("--------------------------------------------------\n");
            areaResultado.append("Nome: " + f.getNome() + " | CPF: " + f.getCpf() + "\n");
            areaResultado.append("Salário Calculado: R$ " + String.format("%.2f", f.calcularSalario()) + "\n");

            totalFolha += f.calcularSalario();
        }

        // Atualiza a label com o valor acumulado da folha (Substitui a opção 3 do seu
        // menu antigo)
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaFuncionarios().setVisible(true);
        });
    }
}