package javas;

public class Cliente {
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private double pagamento;
    public Cliente(String nome, String cpf, String telefone, String endereco, double pagamento){
        this.nome = nome; 
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.pagamento = pagamento;
    }
    public String getNome(){
        return nome;
    }
    public String getCpf(){
        return cpf;
    }
    public String getTelefone(){
        return telefone;
    }
    public String getEndereco(){
        return endereco;
    }
    public double getPagamento(){
        return pagamento;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    public void setPagamento(double pagamento){
        this.pagamento = pagamento;
    }
    public void exibirDados(){
        System.out.println("Nome:" + nome);
        System.out.println("CPF:" + cpf);
        System.out.println("Telefone:" + telefone);
        System.out.println("Endereço:" + endereco);
        System.out.println("Pagamento:" + pagamento);
    }
}
