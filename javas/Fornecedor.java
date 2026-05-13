package javas;

public class Fornecedor extends Cliente {
    private String cnpj;
    public Fornecedor(String nome, String cpf, String telefone, String endereco, double pagamento, String cnpj){
        super(nome, cpf, telefone, endereco, pagamento);
        this.cnpj = cnpj;
    }
    public String getCnpj(){
        return cnpj;
    }
    public void setCnpj(String cnpj){
        this.cnpj = cnpj;
    }
    @Override
    public void exibirDados(){
        System.out.println("Nome:" + getNome());
        System.out.println("CPF:" + getCpf());
        System.out.println("CNPJ:" + cnpj);
        System.out.println("Telefone:" + getTelefone());
        System.out.println("Endereço:" + getEndereco());
        System.out.println("Pagamento:" + getPagamento());
    }  
}
