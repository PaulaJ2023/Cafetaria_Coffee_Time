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
        event.preventDefault();
        const nomeDigitado = campoUsuario.value.trim();

        if (nomeDigitado !== "") {
            const primeiroNome = nomeDigitado.split(" ")[0].split("@")[0];
            if (nomeUsuarioSidebar) {
                nomeUsuarioSidebar.innerText = `Olá, ${primeiroNome}! ✨`;
            }
            alert(`🌸 Bem-vindo(a) de volta, ${primeiroNome}!`);
            if (fecharPopupTodos) {
                fecharPopupTodos.checked = true;
            }
        }
    });
}

//SISTEMA DE CADASTRO
const formularioRegistro = document.getElementById('formulario-registro');
const campoNomeRegistro = document.getElementById('campo-nome-registro');
const campoSenhaRegistro = document.getElementById('campo-senha-registro');
const campoConfirmarSenhaRegistro = document.getElementById('campo-confirmar-senha-registro');

if (formularioRegistro) {
    formularioRegistro.addEventListener('submit', function (event) {
        event.preventDefault();

        const nomeCompleto = campoNomeRegistro.value.trim();
        const senha = campoSenhaRegistro ? campoSenhaRegistro.value : "";
        const confirmarSenha = campoConfirmarSenhaRegistro ? campoConfirmarSenhaRegistro.value : "";

        if (senha !== confirmarSenha) {
            alert("⚠️ Atenção: As senhas digitadas não são iguais! Por favor, verifique.");
            return;
        }

        if (nomeCompleto !== "") {
            const primeiroNome = nomeCompleto.split(" ")[0];
            if (nomeUsuarioSidebar) {
                nomeUsuarioSidebar.innerText = `Olá, ${primeiroNome}! ✨`;
            }
            alert(`🌱 Conta criada com sucesso! Seja bem-vindo(a), ${primeiroNome}!`);
            formularioRegistro.reset();
            if (fecharPopupTodos) {
                fecharPopupTodos.checked = true;
            }
        }
    });
}

//SISTEMA DE RECUPERAÇÃO DE SENHA
const formularioRecuperar = document.getElementById('formulario-recuperar');

if (formularioRecuperar) {
    formularioRecuperar.addEventListener('submit', function (event) {
        event.preventDefault();
        const campoEmailRecuperar = document.getElementById('campo-email-recuperar');
        const emailDigitado = campoEmailRecuperar.value.trim();

        if (emailDigitado !== "") {
            alert(`🌸 Sucesso! As instruções de recuperação foram enviadas para:\n💌 ${emailDigitado}`);
            campoEmailRecuperar.value = "";
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
        event.preventDefault();
        const nomeContato = campoNomeContato.value.trim();

        if (nomeContato !== "") {
            alert(`💌 Obrigado pelo contato, ${nomeContato}!\n✨ Sua mensagem foi enviada com sucesso para a equipe do Coffee Time.`);
            formularioContato.reset();
            if (fecharPopupTodos) {
                fecharPopupTodos.checked = true;
            }
        }
    });
}

//SISTEMA DE CONFIGURAÇÕES
const formularioConfig = document.getElementById('formulario-config');
const popupConfigControle = document.getElementById('popup-config-controle');

// Carregar configurações salvas ao iniciar a página
document.addEventListener('DOMContentLoaded', () => {
    const configsSalvas = JSON.parse(localStorage.getItem('coffeeTimeConfigs'));
    if (configsSalvas) {
        if (document.getElementById('cfg-notificacoes')) {
            document.getElementById('cfg-notificacoes').checked = configsSalvas.notificacoes;
        }
        if (document.getElementById('cfg-salvar-dados')) {
            document.getElementById('cfg-salvar-dados').checked = configsSalvas.salvarDados;
        }
        if (document.getElementById('cfg-moeda')) {
            document.getElementById('cfg-moeda').value = configsSalvas.moeda;
        }
    }
});

// Fechar a sidebar quando clicar em Configurações (comportamento nativo das labels)
if (popupConfigControle) {
    popupConfigControle.addEventListener('change', function() {
        if (this.checked) {
            const menuCheck = document.getElementById('menu');
            if (menuCheck) menuCheck.checked = false;
        }
    });
}

// Evento de salvamento das Configurações
if (formularioConfig) {
    formularioConfig.addEventListener('submit', function (event) {
        event.preventDefault();
        
        const notificacoes = document.getElementById('cfg-notificacoes').checked;
        const salvarDados = document.getElementById('cfg-salvar-dados').checked;
        const moeda = document.getElementById('cfg-moeda').value;

        const objetoConfig = { notificacoes, salvarDados, moeda };
        localStorage.setItem('coffeeTimeConfigs', JSON.stringify(objetoConfig));

        alert("⚙️ Preferências atualizadas com sucesso e aplicadas!");
        
        if (fecharPopupTodos) {
            fecharPopupTodos.checked = true;
        }
    });
}

//SISTEMA DE ACESSIBILIDADE
const botaoAcessibilidade = document.getElementById('btn-acessibilidade');
const popupAcessibilidade = document.getElementById('popup-acessibilidade');
const fecharAcessibilidade = document.getElementById('fechar-acessibilidade');

if (botaoAcessibilidade && popupAcessibilidade) {
    botaoAcessibilidade.addEventListener('click', function (event) {
        event.preventDefault();
        popupAcessibilidade.classList.add('ativo');
    });
}

if (fecharAcessibilidade && popupAcessibilidade) {
    fecharAcessibilidade.addEventListener('click', function () {
        popupAcessibilidade.classList.remove('ativo');
    });
}

const toggleContraste = document.getElementById('acc-contraste');
const btnAumentarTexto = document.getElementById('acc-aumentar');
const btnDiminuirTexto = document.getElementById('acc-diminuir');
const toggleSublinhado = document.getElementById('acc-sublinhado');
const toggleInverter = document.getElementById('acc-inverter');

let tamanhoFonteAtual = 100;

if (toggleContraste) {
    toggleContraste.addEventListener('change', function () {
        document.body.classList.toggle('acc-alto-contraste', this.checked);
    });
}

if (btnAumentarTexto) {
    btnAumentarTexto.addEventListener('click', function () {
        if (tamanhoFonteAtual < 140) {
            tamanhoFonteAtual += 10;
            document.documentElement.style.fontSize = `${tamanhoFonteAtual}%`;
        }
    });
}

if (btnDiminuirTexto) {
    btnDiminuirTexto.addEventListener('click', function () {
        if (tamanhoFonteAtual > 100) {
            tamanhoFonteAtual -= 10;
            document.documentElement.style.fontSize = `${tamanhoFonteAtual}%`;
        }
    });
}

if (toggleSublinhado) {
    toggleSublinhado.addEventListener('change', function () {
        document.body.classList.toggle('acc-links-sublinhados', this.checked);
    });
}

if (toggleInverter) {
    toggleInverter.addEventListener('change', function () {
        document.documentElement.classList.toggle('acc-inverter-cores', this.checked);
    });
}

//PARA ACESSIBILIDADE DIGITAL
document.addEventListener('DOMContentLoaded', () => {
    const toggles = document.querySelectorAll('.popup-toggle, .menu-check');

    function atualizarVisibilidadeAria() {
        document.querySelectorAll('.popup-overlay').forEach(overlay => {
            const estiloComputado = window.getComputedStyle(overlay).display;
            if (estiloComputado === 'flex') {
                overlay.removeAttribute('aria-hidden');
            } else {
                overlay.setAttribute('aria-hidden', 'true');
            }
        });

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

    toggles.forEach(toggle => {
        toggle.addEventListener('change', atualizarVisibilidadeAria);
    });

    atualizarVisibilidadeAria();
});


//SISTEMA DO CARRINHO

let carrinho = [];

// Elementos da interface mapeados
const btnCarrinhoSidebar = document.getElementById('btn-carrinho');
const modalCarrinho = document.getElementById('modal-carrinho');
const btnFecharCarrinho = document.getElementById('btn-fechar-carrinho');
const conteinerItensCarrinho = document.getElementById('itens-do-carrinho');
const elementoValorTotal = document.getElementById('valor-total-carrinho');
const btnFinalizarPedido = document.getElementById('btn-finalizar-pedido');
const botoesAdicionar = document.querySelectorAll('.btn-adicionar');

// Novos elementos do formulário do carrinho
const cartTipoEntrega = document.getElementById('cart-tipo-entrega');
const blocoEnderecoEntrega = document.getElementById('bloco-endereco-entrega');

// Monitora mudança no tipo de entrega para mostrar/esconder endereço
if (cartTipoEntrega && blocoEnderecoEntrega) {
    cartTipoEntrega.addEventListener('change', function () {
        if (this.value === 'Delivery') {
            blocoEnderecoEntrega.style.display = 'block';
            document.getElementById('cart-endereco-cliente').setAttribute('required', 'true');
        } else {
            blocoEnderecoEntrega.style.display = 'none';
            document.getElementById('cart-endereco-cliente').removeAttribute('required');
        }   
    });
}

// 1. Abrir e Fechar o Carrinho Visual
if (btnCarrinhoSidebar && modalCarrinho) {
    btnCarrinhoSidebar.addEventListener('click', function (e) {
        e.preventDefault();
        const menuCheck = document.getElementById('menu');
        if (menuCheck) menuCheck.checked = false;

        modalCarrinho.style.display = 'flex';
        renderizarCarrinhoVisual();
    });
}

if (btnFecharCarrinho && modalCarrinho) {
    btnFecharCarrinho.addEventListener('click', function () {
        modalCarrinho.style.display = 'none';
    });
}

// 2. Adicionar Itens ao Carrinho
botoesAdicionar.forEach(botao => {
    botao.addEventListener('click', function () {
        const card = botao.parentElement;
        const nomeProduto = card.querySelector('h3').innerText;
        const precoTexto = card.querySelector('.preco').innerText;
        const precoNumero = parseFloat(precoTexto.replace('R$', '').replace(',', '.').trim());

        const itemExistente = carrinho.find(item => item.nome === nomeProduto);

        if (itemExistente) {
            itemExistente.quantidade += 1;
        } else {
            carrinho.push({
                nome: nomeProduto,
                preco: precoNumero,
                quantidade: 1
            });
        }

        alert(`✨ ${nomeProduto} foi adicionado ao seu carrinho!`);
        renderizarCarrinhoVisual();
    });
});

// 3. Atualizar a visualização interna do carrinho
function renderizarCarrinhoVisual() {
    if (!conteinerItensCarrinho) return;

    if (carrinho.length === 0) {
        conteinerItensCarrinho.innerHTML = '<p class="carrinho-vazio-txt">Seu carrinho está vazio... 🥞</p>';
        elementoValorTotal.innerText = 'R$ 0,00';
        return;
    }

    conteinerItensCarrinho.innerHTML = '';
    let totalAcumulado = 0;

    carrinho.forEach((item, index) => {
        const custoItem = item.preco * item.quantidade;
        totalAcumulado += custoItem;

        const divItem = document.createElement('div');
        divItem.style.display = 'flex';
        divItem.style.justifyContent = 'space-between';
        divItem.style.alignItems = 'center';
        divItem.style.padding = '8px 0';
        divItem.style.borderBottom = '1px dashed #FCE1B6';

        divItem.innerHTML = `
            <div>
                <span style="font-weight: bold; color: #5A2323;">${item.quantidade}x</span> ${item.nome}
                <div style="font-size: 13px; color: #C06C84;">R$ ${item.preco.toFixed(2).replace('.', ',')} cada</div>
            </div>
            <div style="display: flex; align-items: center; gap: 10px;">
                <span style="font-weight: bold; color: #5A2323;">R$ ${custoItem.toFixed(2).replace('.', ',')}</span>
                <button class="btn-remover-item" data-index="${index}" style="background: none; border: none; color: #C06C84; cursor: pointer; font-size: 16px;"><i class="fas fa-trash-alt"></i></button>
            </div>
        `;

        conteinerItensCarrinho.appendChild(divItem);
    });

    elementoValorTotal.innerText = `R$ ${totalAcumulado.toFixed(2).replace('.', ',')}`;

    const botoesRemover = document.querySelectorAll('.btn-remover-item');
    botoesRemover.forEach(btn => {
        btn.addEventListener('click', function () {
            const idx = parseInt(this.getAttribute('data-index'));
            carrinho.splice(idx, 1);
            renderizarCarrinhoVisual();
        });
    });
}

// 4. Finalizar Compra e Salvar no Banco de Dados Local do Admin
if (btnFinalizarPedido) {
    btnFinalizarPedido.addEventListener('click', function () {
        if (carrinho.length === 0) {
            alert("🛒 Seu carrinho está vazio! Escolha uma delícia primeiro.");
            return;
        }

        // Validações dos novos inputs
        const nomeCliente = document.getElementById('cart-nome-cliente').value.trim();
        const tipoEntrega = document.getElementById('cart-tipo-entrega').value;
        const formaPagamento = document.getElementById('cart-forma-pagamento').value;
        const enderecoCliente = document.getElementById('cart-endereco-cliente').value.trim();

        if (!nomeCliente) {
            alert("⚠️ Por favor, insira o seu nome antes de finalizar!");
            return;
        }

        if (tipoEntrega === 'Delivery' && !enderecoCliente) {
            alert("⚠️ Por favor, insira o endereço para a entrega!");
            return;
        }

        let total = carrinho.reduce((acc, item) => acc + (item.preco * item.quantidade), 0);
        let itensTexto = carrinho.map(item => `${item.quantidade}x ${item.nome}`).join(', ');

        // Criando a estrutura do pedido para a tabela do Admin
        const novoPedido = {
            id: "#" + Math.floor(2000 + Math.random() * 9000),
            cliente: nomeCliente,
            itens: itensTexto,
            tipo: tipoEntrega,
            endereco: tipoEntrega === 'Delivery' ? enderecoCliente : 'Retirada no Balcão',
            pagamento: formaPagamento,
            total: total.toFixed(2) // Certifique-se de manter o .toFixed(2) aqui!
        };

        // Pegar pedidos já existentes ou começar uma lista nova
        let listaPedidos = JSON.parse(localStorage.getItem('pedidosAdmin')) || [];
        listaPedidos.unshift(novoPedido); // Adiciona no início da fila
        localStorage.setItem('pedidosAdmin', JSON.stringify(listaPedidos));

        alert(`🎉 Pedido Finalizado com Sucesso!\n✨ Obrigado por comprar no Coffee Time, ${nomeCliente}!\n💰 Total: R$ ${total.toFixed(2).replace('.', ',')}`);

        // Limpa o carrinho e reseta os inputs
        carrinho = [];
        document.getElementById('cart-nome-cliente').value = '';
        document.getElementById('cart-endereco-cliente').value = '';
        if (blocoEnderecoEntrega) blocoEnderecoEntrega.style.display = 'none';

        renderizarCarrinhoVisual();
        if (modalCarrinho) modalCarrinho.style.display = 'none';
    });
}