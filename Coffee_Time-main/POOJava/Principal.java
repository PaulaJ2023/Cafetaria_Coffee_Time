package POOJava;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Principal {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame menuPrincipal = new JFrame("Dashboard Corporativo - Coffee Time");
      menuPrincipal.setSize(480, 420);
      menuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      menuPrincipal.setLocationRelativeTo(null);

      JPanel painelConteudo = new JPanel(new GridLayout(4, 1, 15, 15));
      painelConteudo.setBackground(new Color(248, 250, 252)); // Fundo cinza ultra-claro
      painelConteudo.setBorder(new EmptyBorder(35, 45, 35, 45));

      JLabel titulo = new JLabel("Painel de Controlo Integrado", SwingConstants.CENTER);
      titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
      titulo.setForeground(new Color(15, 23, 42)); // Azul quase preto

      // Botões estilizados com a nova paleta profissional
      JButton btnAdmin = criarBotaoModerno("Painel Administrativo & Pedidos Web", new Color(30, 41, 59));
      JButton btnFuncionarios = criarBotaoModerno("Funcionarios", new Color(71, 85, 105));
      JButton btnClientes = criarBotaoModerno("Clientes", new Color(15, 118, 110));

      btnAdmin.addActionListener(e -> new TelaAdmin().setVisible(true));
      btnFuncionarios.addActionListener(e -> new TelaFuncionarios().setVisible(true));
      btnClientes.addActionListener(e -> new TelaClientes().setVisible(true));

      painelConteudo.add(titulo);
      painelConteudo.add(btnAdmin);
      painelConteudo.add(btnFuncionarios);
      painelConteudo.add(btnClientes);

      menuPrincipal.add(painelConteudo);
      menuPrincipal.setVisible(true);
    });
  }

  private static JButton criarBotaoModerno(String texto, Color corBase) {
    JButton botao = new JButton(texto);
    botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
    botao.setBackground(corBase);
    botao.setForeground(Color.WHITE);
    botao.setFocusPainted(false);
    botao.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(corBase.darker(), 1),
        BorderFactory.createEmptyBorder(12, 15, 12, 15)));
    botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

    botao.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        botao.setBackground(corBase.brighter());
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        botao.setBackground(corBase);
      }
    });
    return botao;
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