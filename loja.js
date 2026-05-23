//ANOTAÇÕES DA PAULA: Esse arquivo cuida da lojinha do Coffee Time

//SISTEMA DE LOGIN DINÂMICO
const formularioLogin = document.getElementById('formulario-login');
const campoUsuario = document.getElementById('campo-usuario');
const nomeUsuarioSidebar = document.getElementById('nome-usuario-sidebar');
const fecharPopupTodos = document.getElementById('popup-fechar-todos');

formularioLogin.addEventListener('submit', function(event) {
    //Evita que a página recarregue
    event.preventDefault(); 

    //Pega o valor digitado pelo usuário
    const nomeDigitado = campoUsuario.value.trim();

    if (nomeDigitado !== "") {
        //Atualiza o texto do menu lateral com o nome da pessoa
        nomeUsuarioSidebar.innerText = `Olá, ${nomeDigitado}! ✨`;

        //Alerta mostrado em cima
        alert(`🌸 Bem-vindo(a) de volta, ${nomeDigitado}!`);

        //Fecha o popup
        if (fecharPopupTodos) {
            fecharPopupTodos.checked = true;
        }
    }
});

//SISTEMA DE CADASTRO DINÂMICO
const formularioRegistro = document.getElementById('formulario-registro');
const campoNomeRegistro = document.getElementById('campo-nome-registro');

formularioRegistro.addEventListener('submit', function(event) {
    //Evita o recarregamento da página
    event.preventDefault();

    //Pega o nome completo digitado e remove espaços extras
    const nomeCompleto = campoNomeRegistro.value.trim();

    if (nomeCompleto !== "") {
        //Pegar apenas o primeiro nome digitado
        const primeiroNome = nomeCompleto.split(" ")[0];

        //Atualiza o menu lateral
            nomeUsuarioSidebar.innerText = `Olá, ${primeiroNome}! ✨`;
        }

        //Alerta em cima
        alert(`🌱 Conta criada com sucesso! Seja bem-vindo(a), ${primeiroNome}!`);

        //Fecha o popup e limpa as seleções
        if (fecharPopupTodos) {
            fecharPopupTodos.checked = true;
        }
    });

//Soma os valores e finge que envou o pedido para o sistema
//Cria uma lista vazia para colocar os produtos que o cliente escolher
let carrinho = [];
let totalPagar = 0;

//Seleciona todos os botões de "adicionar" da página
const botoesAdicionar = document.querySelectorAll('.btn-adicionar');

botoesAdicionar.forEach(botao => {
    botao.addEventListener('click', function () {
        //Pega o nome do produto e o preço que estão no HTML perto do botão
        const card = botao.parentElement;
        const nomeProduto = card.querySelector('h3').innerText;
        const precoTexto = card.querySelector('.preco').innerText;

        //Limpa o texto do preço para transformar em um número de verdade
        const precoNumero = parseFloat(precoTexto.replace('R$', '').replace(',', '.').trim());

        //Coloca o produto dentro do carrinho
        carrinho.push(nomeProduto);
        totalPagar += precoNumero;

        //Mostra um aviso!
        alert(`✨ ${nomeProduto} foi adicionado ao seu carrinho com sucesso! \nTotal atual: R$ ${totalPagar.toFixed(2)}`);

        //Salva temporariamente o total e os itens para o Admin conseguir ler depois
        localStorage.setItem('ultimoTotal', totalPagar.toFixed(2));
        localStorage.setItem('ultimosProdutos', carrinho.join(', '));
    });
});