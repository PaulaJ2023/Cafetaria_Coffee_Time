package POOJava;

public class Cliente {
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String pagamento; // Alterado para String para guardar os detalhes da forma de pagamento
    private double valorPagamento; // Campo interno para manter o valor numérico para as somas

    public Cliente(String nome, String cpf, String telefone, String endereco, double valorPagamento, String detalhesPagamento) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.valorPagamento = valorPagamento;
        this.pagamento = detalhesPagamento;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getPagamento() {
        return pagamento;
    }

    public double getValorNumericoPagamento() {
        return valorPagamento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public void setValorPagamento(double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public void exibirDados() {
        System.out.println("Nome:" + nome);
        System.out.println("CPF:" + cpf);
        System.out.println("Telefone:" + telefone);
        System.out.println("Endereço:" + endereco);
        System.out.println("Pagamento:" + pagamento);
    }
}