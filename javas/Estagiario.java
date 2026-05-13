package javas;

public class Estagiario {
    private String nome;
    private String cpf;
    private double salario;
    private double bolsaAuxilio;
    public Estagiario(String nome, String cpf,double salario, double bolsaAuxilio) {
        this.nome = nome;
        this.cpf = cpf;
        this.salario = salario;
        this.bolsaAuxilio = bolsaAuxilio;
    }
    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
    public double getSalario() {
        return salario;
    }
    public double getBolsaAuxilio(){
        return bolsaAuxilio;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }
    public void setBolsaAuxilio(double bolsaAuxilio) {
        this.bolsaAuxilio = bolsaAuxilio;
    }
    public double calcularSalario(){
        return salario + bolsaAuxilio;
    }
    public void exibirDados(){
        System.out.println("Nome:" + nome);
        System.out.println("CPF" + cpf);
        System.out.println("Salário Base:" + salario);
        System.out.println("Bolsa Auxílio:" + bolsaAuxilio);
        System.out.println("Salário a receber:" + calcularSalario());
    }

    
}
