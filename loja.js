// ANOTAÇÕES DA PAULA: Esse arquivo cuida da lojinha do Coffee Time! ☕
// Ele soma os valores e finge que envia o pedido para o nosso sistema.

// Criamos uma lista vazia para colocar os produtos que o cliente escolher
let carrinho = [];
let totalPagar = 0;

// Selecionamos todos os botões de "adicionar" da nossa página
const botoesAdicionar = document.querySelectorAll('.btn-adicionar');

// Para cada botão encontrado, vamos "ouvir" quando ele for clicado
botoesAdicionar.forEach(botao => {
    botao.addEventListener('click', function () {
        // Pegamos o nome do produto e o preço que estão no HTML perto do botão
        const card = botao.parentElement;
        const nomeProduto = card.querySelector('h3').innerText;
        const precoTexto = card.querySelector('.preco').innerText; // Ex: "R$ 5,00"

        // Limpamos o texto do preço para transformar em um número de verdade
        const precoNumero = parseFloat(precoTexto.replace('R$', '').replace(',', '.').trim());

        // Colocamos o produto dentro do nosso carrinho
        carrinho.push(nomeProduto);
        totalPagar += precoNumero;

        // Mostramos um aviso fofo na tela!
        alert(`✨ ${nomeProduto} foi adicionado ao seu carrinho com sucesso! \nTotal atual: R$ ${totalPagar.toFixed(2)}`);

        // Salvamos temporariamente o total e os itens para o Admin conseguir ler depois
        localStorage.setItem('ultimoTotal', totalPagar.toFixed(2));
        localStorage.setItem('ultimosProdutos', carrinho.join(', '));
    });
});