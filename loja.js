//ANOTAÇÕES DA PAULA: Esse arquivo cuida da parte da site do Coffee Time
//ANOTAÇÕES DA PAULA: Cuida do login, do cadastro e também soma os valores do carrinho.

//VARIÁVEIS GERAIS
const nomeUsuarioSidebar = document.getElementById('nome-usuario-sidebar');
const fecharPopupTodos = document.getElementById('popup-fechar-todos');

//SISTEMA DE LOGIN
const formularioLogin = document.getElementById('formulario-login');
const campoUsuario = document.getElementById('campo-usuario');

if (formularioLogin) {
    formularioLogin.addEventListener('submit', function (event) {
        //Evita que a página recarregue
        event.preventDefault();

        //Pega o valor digitado pelo usuário
        const nomeDigitado = campoUsuario.value.trim();

        if (nomeDigitado !== "") {
            // AJUSTE ANTERIOR: Pega apenas o primeiro nome (ou antes do @ se for um e-mail)
            const primeiroNome = nomeDigitado.split(" ")[0].split("@")[0];

            // Atualiza o texto do menu lateral com o primeiro nome
            if (nomeUsuarioSidebar) {
                nomeUsuarioSidebar.innerText = `Olá, ${primeiroNome}! ✨`;
            }

            // Alerta mostrado em cima com o primeiro nome
            alert(`🌸 Bem-vindo(a) de volta, ${primeiroNome}!`);

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
// NOVOS CAMPOS: Seleciona os campos de senha do formulário
const campoSenhaRegistro = document.getElementById('campo-senha-registro');
const campoConfirmarSenhaRegistro = document.getElementById('campo-confirmar-senha-registro');

if (formularioRegistro) {
    formularioRegistro.addEventListener('submit', function (event) {
        //Evita o recarregamento da página
        event.preventDefault();

        // Pega os valores digitados e remove espaços extras
        const nomeCompleto = campoNomeRegistro.value.trim();
        const senha = campoSenhaRegistro ? campoSenhaRegistro.value : "";
        const confirmarSenha = campoConfirmarSenhaRegistro ? campoConfirmarSenhaRegistro.value : "";

        // NOVA VALIDAÇÃO: Verifica se as duas senhas são diferentes
        if (senha !== confirmarSenha) {
            alert("⚠️ Atenção: As senhas digitadas não são iguais! Por favor, verifique.");
            return; // O 'return' para a execução aqui e não deixa o cadastro continuar
        }

        if (nomeCompleto !== "") {
            //Pegar apenas o primeiro nome digitado
            const primeiroNome = nomeCompleto.split(" ")[0];

            //Atualiza o menu lateral
            if (nomeUsuarioSidebar) {
                nomeUsuarioSidebar.innerText = `Olá, ${primeiroNome}! ✨`;
            }

            //Alerta em cima
            alert(`🌱 Conta criada com sucesso! Seja bem-vindo(a), ${primeiroNome}!`);

            // Limpa o formulário de registro após o sucesso
            formularioRegistro.reset();

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
    formularioRecuperar.addEventListener('submit', function (event) {
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

//SISTEMA DE CONTATO
const formularioContato = document.getElementById('formulario-contato');
const campoNomeContato = document.getElementById('campo-nome-contato');

if (formularioContato) {
    formularioContato.addEventListener('submit', function (event) {
        //Evita que a página recarregue ao enviar o formulário
        event.preventDefault();

        //Pega o nome da pessoa e limpa os espaços sobrando
        const nomeContato = campoNomeContato.value.trim();

        if (nomeContato !== "") {
            //Mostra o alerta de confirmação
            alert(`💌 Obrigado pelo contato, ${nomeContato}!\n✨ Sua mensagem foi enviada com sucesso para a equipe do Coffee Time.`);

            //Limpa o formulário todo após o envio
            formularioContato.reset();

            //Fecha o popup
            if (fecharPopupTodos) {
                fecharPopupTodos.checked = true;
            }
        }
    });
}

//SISTEMA DE ACESSIBILIDADE
//Elementos do Popup
const botaoAcessibilidade = document.getElementById('btn-acessibilidade');
const popupAcessibilidade = document.getElementById('popup-acessibilidade');
const fecharAcessibilidade = document.getElementById('fechar-acessibilidade');

// Ações para abrir e fechar o popup na tela
if (botaoAcessibilidade && popupAcessibilidade) {
    botaoAcessibilidade.addEventListener('click', function (event) {
        event.preventDefault(); // Evita que o link '#' recarregue ou suba a página
        popupAcessibilidade.classList.add('ativo');
    });
}

if (fecharAcessibilidade && popupAcessibilidade) {
    fecharAcessibilidade.addEventListener('click', function () {
        popupAcessibilidade.classList.remove('ativo');
    });
}

// Elementos de ativação das opções
const toggleContraste = document.getElementById('acc-contraste');
const btnAumentarTexto = document.getElementById('acc-aumentar');
const btnDiminuirTexto = document.getElementById('acc-diminuir');
const toggleSublinhado = document.getElementById('acc-sublinhado');
const toggleInverter = document.getElementById('acc-inverter');

let tamanhoFonteAtual = 100; // Começa em 100%

// 1. Ativar Alto Contraste
if (toggleContraste) {
    toggleContraste.addEventListener('change', function () {
        document.body.classList.toggle('acc-alto-contraste', this.checked);
    });
}

// 2. Aumentar Tamanho das Fontes
if (btnAumentarTexto) {
    btnAumentarTexto.addEventListener('click', function () {
        if (tamanhoFonteAtual < 140) { // Define um limite máximo seguro
            tamanhoFonteAtual += 10;
            document.documentElement.style.fontSize = `${tamanhoFonteAtual}%`;
        }
    });
}

// 3. Diminuir/Resetar Tamanho das Fontes
if (btnDiminuirTexto) {
    btnDiminuirTexto.addEventListener('click', function () {
        if (tamanhoFonteAtual > 100) { // Não deixa ficar menor que o padrão da loja
            tamanhoFonteAtual -= 10;
            document.documentElement.style.fontSize = `${tamanhoFonteAtual}%`;
        }
    });
}

// 4. Sublinhar Elementos Clicáveis
if (toggleSublinhado) {
    toggleSublinhado.addEventListener('change', function () {
        document.body.classList.toggle('acc-links-sublinhados', this.checked);
    });
}

//Inverter Cores Globais
if (toggleInverter) {
    toggleInverter.addEventListener('change', function() {
        // Altera para documentElement para aplicar a classe na tag <html>
        document.documentElement.classList.toggle('acc-inverter-cores', this.checked);
    });
}

//PARA ACESSIBILIDADE DIGITAL
document.addEventListener('DOMContentLoaded', () => {
    const toggles = document.querySelectorAll('.popup-toggle, .menu-check');

    function atualizarVisibilidadeAria() {
        // Controla o estado dos popups/overlays
        document.querySelectorAll('.popup-overlay').forEach(overlay => {
            const estiloComputado = window.getComputedStyle(overlay).display;
            if (estiloComputado === 'flex') {
                overlay.removeAttribute('aria-hidden');
            } else {
                overlay.setAttribute('aria-hidden', 'true');
            }
        });

        // Controla o estado do menu lateral
        const sidebar = document.querySelector('.sidebar');
        const menuCheck = document.getElementById('menu');
        if (sidebar && menuCheck) {
            if (menuCheck.checked) {
                sidebar.removeAttribute('aria-hidden');
            } else {
                sidebar.setAttribute('aria-hidden', 'true');
            }
        }
    }

    // Escuta as alterações nos inputs seletores
    toggles.forEach(toggle => {
        toggle.addEventListener('change', atualizarVisibilidadeAria);
    });

    // Corre uma primeira vez para definir os estados iniciais corretos
    atualizarVisibilidadeAria();
});

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