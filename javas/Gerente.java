package javas;

public class Gerente extends Funcionario {
    private double gratificacao;
    private double participacaoLucros;
    public Gerente(String nome, String cpf, double salario, double valorHora, double horasTrabalhadas, double gratificacao,double participacaoLucros){
        super(nome, cpf, salario, valorHora, horasTrabalhadas);
        this.gratificacao = gratificacao;
        this.participacaoLucros = participacaoLucros;
    }
    public double getGratificacao(){
        return gratificacao;
    }
    public double getPartipacaoLucros(){
        return participacaoLucros;
    }
    public void setGratificaca(double gratificacao){
        this.gratificacao = gratificacao;
    }
    public void setParticipacaoLucros(double participacaoLucros){
        this.participacaoLucros = participacaoLucros;
    }
    @Override
    public double calcularSalario(){
        return super.calcularSalario() + gratificacao + participacaoLucros;
    }
    @Override
    public void exibirDados(){
        System.out.println("Nome:" + getNome());
        System.out.println("CPF" + getCpf());
        System.out.println("Salário Base:" + getSalario());
        System.out.println("Gratificação:" + gratificacao);
        System.out.println("Participação nos lucros:" + participacaoLucros);
        System.out.println("Salário a receber:" + calcularSalario());
    }
    
}
