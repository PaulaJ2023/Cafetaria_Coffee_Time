package javas;

public class Funcionario {
    private String nome;
    private String cpf;
    private double salario;
    private double valorHora;
    private double horasTrabalhadas;

    public Funcionario(String nome, String cpf, double salario,double valorHora,double horasTrabalhadas){
        this .nome = nome;
        this .cpf = cpf;
        this .salario = salario;
        this .valorHora = valorHora;
        this .horasTrabalhadas = horasTrabalhadas;
    }
    public String getNome(){
        return nome;
    }
    public String getCpf(){
        return cpf;
    }
    public double getSalario(){
        return salario;
    }
    public double getValorHora(){
        return valorHora;
    }
    public double getHorasTrabalhadas(){
        return horasTrabalhadas;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public void setSalario(double salario){
        this.salario = salario;
    }
    public void setValorHora(double valorHora){
        this.valorHora = valorHora;
    }
    public void setHorasTrabalhadas(double horasTrabalhadas){
        this.horasTrabalhadas = horasTrabalhadas;
    }
    public double calcularSalario(){
        return valorHora * horasTrabalhadas;
    }
    public void exibirDados(){
        System.out.println("Nome:" + nome);
        System.out.println("CPF" + cpf);
        System.out.println("Salário Base:" + salario);
        System.out.println("Salário a receber:" + calcularSalario());
    }
}
