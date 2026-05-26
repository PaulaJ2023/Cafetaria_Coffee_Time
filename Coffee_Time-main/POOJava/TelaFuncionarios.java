package POOJava;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class TelaFuncionarios extends JFrame {

    private JTextField txtNome, txtCpf, txtSalario, txtValorHora, txtHorasTrabalhadas, txtGratificacao,
            txtParticipacaoLucros, txtBolsaAuxilio;
    private JComboBox<String> comboTipo;
    private JTextArea areaResultado;
    private JLabel lblTotalFolha;

    private ArrayList<Funcionario> lista = new ArrayList<>();

    public TelaFuncionarios() {
        setTitle("Funcionarios");
        setSize(650, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(248, 250, 252));

        JPanel painelTopo = new JPanel(new GridLayout(9, 2, 8, 8));
        painelTopo.setBackground(Color.WHITE);
        painelTopo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)),
                        "Dados Gerais do Contrato", TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 13), new Color(15, 23, 42))));

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtSalario = new JTextField();
        txtValorHora = new JTextField();
        txtHorasTrabalhadas = new JTextField();
        txtGratificacao = new JTextField();
        txtParticipacaoLucros = new JTextField();
        txtBolsaAuxilio = new JTextField();

        comboTipo = new JComboBox<>(new String[] { "Funcionário Comum", "Gerente", "Estagiário" });

        painelTopo.add(new JLabel(" Categoria Contratual:"));
        painelTopo.add(comboTipo);
        painelTopo.add(new JLabel(" Nome Completo:"));
        painelTopo.add(txtNome);
        painelTopo.add(new JLabel(" CPF/Documento:"));
        painelTopo.add(txtCpf);
        painelTopo.add(new JLabel(" Salário Fixo Base:"));
        painelTopo.add(txtSalario);
        painelTopo.add(new JLabel(" Valor Hora (R$):"));
        painelTopo.add(txtValorHora);
        painelTopo.add(new JLabel(" Horas Computadas:"));
        painelTopo.add(txtHorasTrabalhadas);
        painelTopo.add(new JLabel(" Gratificação por Cargo:"));
        painelTopo.add(txtGratificacao);
        painelTopo.add(new JLabel(" Distribuição de PLR:"));
        painelTopo.add(txtParticipacaoLucros);
        painelTopo.add(new JLabel(" Bolsa de Estágio:"));
        painelTopo.add(txtBolsaAuxilio);

        JPanel painelCentral = new JPanel(new BorderLayout(8, 8));
        painelCentral.setOpaque(false);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        areaResultado = new JTextArea();
        areaResultado.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultado);

        JButton btnSalvar = new JButton("Gravar e Consolidar Contrato");
        btnSalvar.setBackground(new Color(15, 23, 42));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSalvar.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        painelCentral.add(btnSalvar, BorderLayout.NORTH);
        painelCentral.add(scroll, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelInferior.setBackground(Color.WHITE);
        painelInferior.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        lblTotalFolha = new JLabel("Encargos Totais de Folha: R$ 0,00  ");
        lblTotalFolha.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalFolha.setForeground(new Color(15, 23, 42));
        painelInferior.add(lblTotalFolha);

        add(painelTopo, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> cadastrar());
    }

    private void cadastrar() {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            double salario = Double.parseDouble(txtSalario.getText());
            double valorHora = Double.parseDouble(txtValorHora.getText());
            double horas = Double.parseDouble(txtHorasTrabalhadas.getText());

            String tipo = (String) comboTipo.getSelectedItem();

            if (tipo.equals("Gerente")) {
                double grat = Double.parseDouble(txtGratificacao.getText());
                double lucros = Double.parseDouble(txtParticipacaoLucros.getText());
                lista.add(new Gerente(nome, cpf, salario, valorHora, horas, grat, lucros));
            } else if (tipo.equals("Estagiário")) {
                double bolsa = Double.parseDouble(txtBolsaAuxilio.getText());
                lista.add(new Estagiario(nome, cpf, salario, valorHora, horas, bolsa));
            } else {
                lista.add(new Funcionario(nome, cpf, salario, valorHora, horas));
            }

            atualizarTela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Processamento contábil efetuado.", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Inconsistência nos campos de valores.", "Erro de Entrada",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTela() {
        areaResultado.setText("");
        double totalFolha = 0;
        for (Funcionario f : lista) {
            areaResultado.append("==================================================\n");
            areaResultado.append("Ficha: " + f.getNome() + " | Identificação: " + f.getCpf() + "\n");
            areaResultado.append("Salário Contábil Final: R$ " + String.format("%.2f", f.calcularSalario()) + "\n");
            totalFolha += f.calcularSalario();
        }
        lblTotalFolha.setText("Encargos Totais de Folha: R$ " + String.format("%.2f", totalFolha) + "  ");
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