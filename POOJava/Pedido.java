package POOJava;

public class Pedido {
    private int numeroPedido;
    private String nomeCliente;
    private String endereco;
    private String produtosSelecionados;
    private String formaPagamento;
    private double totalPagar;

    public Pedido(int numeroPedido, String nomeCliente, String endereco, String produtosSelecionados,
            String formaPagamento, double totalPagar) {
        this.numeroPedido = numeroPedido;
        this.nomeCliente = nomeCliente;
        this.endereco = endereco;
        this.produtosSelecionados = produtosSelecionados;
        this.formaPagamento = formaPagamento;
        this.totalPagar = totalPagar;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getProdutosSelecionados() {
        return produtosSelecionados;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public double getTotalPagar() {
        return totalPagar;
    }
}