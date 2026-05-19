package POOJava;

public class Pedido {
    private int numeroPedido;
    private String nomeCliente;
    private String endereco;
    private String produtosSelecionados;
    private String formaPagamento;
    private String retirarPedido;
    private double totalPagar;

    public Pedido(int numeroPedido, String nomeCliente, String endereco, String produtosSelecionados,
            String formaPagamento, String retirarPedido, double totalPagar) {
        this.numeroPedido = numeroPedido;
        this.nomeCliente = nomeCliente;
        this.endereco = endereco;
        this.produtosSelecionados = produtosSelecionados;
        this.formaPagamento = formaPagamento;
        this.retirarPedido = retirarPedido;
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

    public String retirarPedido() {
        return retirarPedido;
    }

    public double getTotalPagar() {
        return totalPagar;
    }
}