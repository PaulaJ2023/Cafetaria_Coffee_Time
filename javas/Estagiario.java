package javas;

public class Estagiario extends Funcionario{
    private double bolsaAuxilio;
    public Estagiario(String nome, String cpf, double salario,double valorHora,double horasTrabalhadas, double bolsaAuxilio){
        super(nome, cpf, salario, valorHora, horasTrabalhadas);
        this.bolsaAuxilio = bolsaAuxilio;
    }
    public double getBolsaAuxilio() {
        return bolsaAuxilio;
    }
    public void setBolsaAuxilio(double bolsaAuxilio) {
        this.bolsaAuxilio = bolsaAuxilio;
    }
    @Override
    public double calcularSalario() {
        return super.calcularSalario() + bolsaAuxilio;
    }
    @Override
    public void exibirDados() {
        System.out.println("Nome:" + getNome());
        System.out.println("CPF:" + getCpf());
        System.out.println("Salário Base:" + getSalario());
        System.out.println("Salário a receber:" + calcularSalario());
        System.out.println("Bolsa de Auxílio:" + bolsaAuxilio);
}
}
