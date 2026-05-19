package POOJava;

import java.util.ArrayList;
import java.util.Scanner;

public class Principal {
  public static void main(String[] args) {
    Scanner leitor = new Scanner(System.in);
    ArrayList<Funcionario> lista = new ArrayList<>();
    ArrayList<Cliente> listaClientes = new ArrayList<>();
    // ArrayList<Fornecedor> listaFornecedores = new ArrayList<>();
    int opcao;
    do {
      System.out.println("-menu-");
      System.out.println("1- Cadastrar funcionario");
      System.out.println("2- Listar funcionario");
      System.out.println("3- Calcular folha salarial");
      System.out.println("4 - Cadastrar cliente");
      // System.out.println("5 - Cadastrar fornecedor");
      System.out.println("0- Sair");
      System.out.println("escolha uma opçâo");
      opcao = leitor.nextInt();
      leitor.nextLine();
      if (opcao == 1) {
        System.out.println("\nTipo(1- funcionario | 2-Gerente | 3-Estagiario): ");
        int tipo_funci = leitor.nextInt();
        leitor.nextLine();
        System.out.println("nome");
        String nome = leitor.nextLine();
        System.out.println("cpf");
        String cpf = leitor.nextLine();
        Funcionario f;
        if (tipo_funci == 2) {
          System.out.println("salario");
          double salario = leitor.nextDouble();
          System.out.println("valor hora");
          double valorHora = leitor.nextDouble();
          System.out.println("horas trabalhadas");
          double horasTrabalhadas = leitor.nextDouble();
          System.out.println("gratificação");
          double gratificacao = leitor.nextDouble();
          System.out.println("participação nos lucros");
          double participacaoLucros = leitor.nextDouble();
          f = new Gerente(nome, cpf, salario, valorHora, horasTrabalhadas, gratificacao, participacaoLucros);
        } else if (tipo_funci == 3) {
          System.out.println("salario");
          double salario = leitor.nextDouble();
          System.out.println("valor hora");
          double valorHora = leitor.nextDouble();
          System.out.println("horas trabalhadas");
          double horasTrabalhadas = leitor.nextDouble();
          System.out.println("bolsa auxilio");
          double bolsaAuxilio = leitor.nextDouble();
          f = new Estagiario(nome, cpf, salario, valorHora, horasTrabalhadas, bolsaAuxilio);
        } else {
          System.out.println("salario");
          double salario = leitor.nextDouble();
          System.out.println("valor hora");
          double valorHora = leitor.nextDouble();
          System.out.println("horas trabalhadas");
          double horasTrabalhadas = leitor.nextDouble();
          f = new Funcionario(nome, cpf, salario, valorHora, horasTrabalhadas);
        }
        lista.add(f);
        System.out.println("funcionario cadastrado com sucesso");
      } else if (opcao == 2) {
        System.out.println("--listando funcionario--");
        for (int i = 0; i < lista.size(); i++) {
          lista.get(i).exibirDados();
        }
      } else if (opcao == 3) {
        // calcular valor total
        double total = 0;
        int i = 0;
        while (i < lista.size()) {
          total += lista.get(i).calcularSalario();
          i++;
        }
        System.out.println("total da folha salarial" + total);
      } else if (opcao == 4) {
        System.out.println("nome do cliente");
        String nome = leitor.nextLine();
        System.out.println("cpf do cliente");
        String cpf = leitor.nextLine();
        System.out.println("Telefone do cliente");
        String telefone = leitor.nextLine();
        System.out.println("endereço do cliente");
        String endereco = leitor.nextLine();
        System.out.println("pagamento do cliente");
        double pagamento = leitor.nextDouble();
        leitor.nextLine();// limpar buffer
        Cliente c = new Cliente(nome, cpf, telefone, endereco, pagamento);
        listaClientes.add(c);
        System.out.println("cliente cadastrado com sucesso");
      } /*
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
    } while (opcao != 0);
    leitor.close();
  }
}