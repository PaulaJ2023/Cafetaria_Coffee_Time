//ANOTAÇÕES DA PAULA: Esse arquivo cuida da parte da site do Coffee Time
//ANOTAÇÕES DA PAULA: Cuida do login, do cadastro e também soma os valores do carrinho.

//VARIÁVEIS GERAIS
const nomeUsuarioSidebar = document.getElementById('nome-usuario-sidebar');
const fecharPopupTodos = document.getElementById('popup-fechar-todos');
//SISTEMA DE LOGIN
const formularioLogin = document.getElementById('formulario-login');
const campoUsuario = document.getElementById('campo-usuario');

if (formularioLogin) {
    formularioLogin.addEventListener('submit', function(event) {
        //Evita que a página recarregue
        event.preventDefault(); 

        //Pega o valor digitado pelo usuário
        const nomeDigitado = campoUsuario.value.trim();

        if (nomeDigitado !== "") {
            // Atualiza o texto do menu lateral com o nome da pessoa
            if (nomeUsuarioSidebar) {
                nomeUsuarioSidebar.innerText = `Olá, ${nomeDigitado}! ✨`;
            }

            // Alerta mostrado em cima
            alert(`🌸 Bem-vindo(a) de volta, ${nomeDigitado}!`);

            // Fecha o popup
            if (fecharPopupTodos) {
                fecharPopupTodos.checked = true;
            }
        }
    });
}


//SISTEMA DE CADASTRO
const formularioRegistro = document.getElementById('formulario-registro');
const campoNomeRegistro = document.getElementById('campo-nome-registro');

if (formularioRegistro) {
    formularioRegistro.addEventListener('submit', function(event) {
        //Evita o recarregamento da página
        event.preventDefault();

        //Pega o nome completo digitado e remove espaços extras
        const nomeCompleto = campoNomeRegistro.value.trim();

        if (nomeCompleto !== "") {
            //Pegar apenas o primeiro nome digitado
            const primeiroNome = nomeCompleto.split(" ")[0];

            //Atualiza o menu lateral
            if (nomeUsuarioSidebar) {
                nomeUsuarioSidebar.innerText = `Olá, ${primeiroNome}! ✨`;
            }

            //Alerta em cima
            alert(`🌱 Conta criada com sucesso! Seja bem-vindo(a), ${primeiroNome}!`);

            //Fecha o popup e limpa as seleções
            if (fecharPopupTodos) {
                fecharPopupTodos.checked = true;
            }
        }
    });
}

//SISTEMA DE RECUPERAÇÃO DE SENHA
const formularioRecuperar = document.getElementById('formulario-recuperar');
const campoEmailRecuperar = document.getElementById('campo-email-recuperar');

if (formularioRecuperar) {
    formularioRecuperar.addEventListener('submit', function(event) {
        //Evita que a página recarregue
        event.preventDefault();

        //Pega o e-mail digitado
        const emailDigitado = campoEmailRecuperar.value.trim();

        if (emailDigitado !== "") {
            //Mostra o alerta fofo confirmando o envio para o e-mail digitado
            alert(`🌸 Sucesso! As instruções de recuperação foram enviadas para:\n💌 ${emailDigitado}`);

            //Limpa o campo de texto depois de enviar
            campoEmailRecuperar.value = "";

            //Fecha o popup simulando o clique no botão de fechar todos
            if (fecharPopupTodos) {
                fecharPopupTodos.checked = true;
            }
        }
    });
}

//SISTEMA DO CARRINHO DE COMPRAS
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