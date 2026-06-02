/* ==========================================================================
   ☕ COFFEE TIME - SCRIPT DE INTERATIVIDADE E FLUXOS DA PLATAFORMA
   ========================================================================== 
   ANOTAÇÕES DA PAULA: Esse arquivo cuida da parte do site do Coffee Time.
   Cuida do login, do cadastro e também soma os valores do carrinho.
   ========================================================================== */

/* --------------------------------------------------------------------------
   1. MAPEAMENTO DE ELEMENTOS DO DOM (VARIÁVEIS GERAIS)
   -------------------------------------------------------------------------- 
   Dica: Mapear todas as constantes no topo do arquivo evita redundâncias 
   de buscas repetidas no documento através do 'document.getElementById'. */

// Elementos de Controle de Fluxo e Interface Geral
const nomeUsuarioSidebar = document.getElementById('nome-usuario-sidebar');
const fecharPopupTodos = document.getElementById('popup-fechar-todos');
const menuCheck = document.getElementById('menu');

// Elementos do Sistema de Login e Autenticação
const formularioLogin = document.getElementById('formulario-login');
const campoUsuario = document.getElementById('campo-usuario');
const campoSenhaLogin = document.querySelector('#formulario-login input[type="password"]');

// Elementos do Sistema de Cadastro e Registro de Contas
const formularioRegistro = document.getElementById('formulario-registro');
const campoNomeRegistro = document.getElementById('campo-nome-registro');
const campoSenhaRegistro = document.getElementById('campo-senha-registro');
const campoConfirmarSenhaRegistro = document.getElementById('campo-confirmar-senha-registro');

// Elementos das Telas Auxiliares (Recuperação e Contato)
const formularioRecuperar = document.getElementById('formulario-recover');
const formRecuperarReal = document.getElementById('formulario-recuperar') || formularioRecuperar;
const formularioContato = document.getElementById('formulario-contato');
const campoNomeContato = document.getElementById('campo-nome-contato');

// Elementos do Painel de Configurações do Sistema
const formularioConfig = document.getElementById('formulario-config');
const popupConfigControle = document.getElementById('popup-config-controle');
const popupSobreControle = document.getElementById('popup-sobre-controle');

// Elementos de Controle de Acessibilidade
const botaoAcessibilidade = document.getElementById('btn-acessibilidade');
const popupAcessibilidade = document.getElementById('popup-acessibilidade');
const fecharAcessibilidade = document.getElementById('fechar-acessibilidade');
const toggleContraste = document.getElementById('acc-contraste');
const btnAumentarTexto = document.getElementById('acc-aumentar');
const btnDiminuirTexto = document.getElementById('acc-diminuir');
const toggleSublinhado = document.getElementById('acc-sublinhado');
const toggleInverter = document.getElementById('acc-inverter');

// Elementos do Ecossistema do Carrinho de Compras
const btnCarrinhoSidebar = document.getElementById('btn-carrinho');
const modalCarrinho = document.getElementById('modal-carrinho');
const btnFecharCarrinho = document.getElementById('btn-fechar-carrinho');
const conteinerItensCarrinho = document.getElementById('itens-do-carrinho');
const elementoValorTotal = document.getElementById('valor-total-carrinho');
const btnFinalizarPedido = document.getElementById('btn-finalizar-pedido');
const botoesAdicionar = document.querySelectorAll('.btn-adicionar');
const cartTipoEntrega = document.getElementById('cart-tipo-entrega');
const blocoEnderecoEntrega = document.getElementById('bloco-endereco-entrega');

/* --------------------------------------------------------------------------
   2. VARIÁVEIS DE ESTADO DO SISTEMA
   -------------------------------------------------------------------------- */
let carrinho = [];           // Array dinâmico que armazenará os objetos dos produtos inseridos
let tamanhoFonteAtual = 100; // Estado inicial do tamanho da fonte em porcentagem (%) para acessibilidade


/* --------------------------------------------------------------------------
   3. SISTEMA DE LOGIN E CONTROLE ADMINISTRATIVO
   -------------------------------------------------------------------------- */
if (formularioLogin) {
    formularioLogin.addEventListener('submit', function (event) {
        event.preventDefault(); // Evita o recarregamento nativo da página

        const nomeDigitado = campoUsuario.value.trim();
        const senhaDigitada = campoSenhaLogin ? campoSenhaLogin.value : "";

        // Verificação para o login de Administrador (Duda)
        if ((nomeDigitado === "eduarda@coffeetime.com" || nomeDigitado === "duda123") && senhaDigitada === "duda123") {
            alert("☕ Acesso administrativo detectado! Bem-vinda, Duda. Redirecionando...");
            window.location.href = "admin.html"; // Direciona para o painel de administração de pedidos
            return;
        }

        // Fluxo normal para clientes comuns
        if (nomeDigitado !== "") {
            // Separa o e-mail ou nome completo para pegar apenas o primeiro nome para exibição fofa
            const primeiroNome = nomeDigitado.split(" ")[0].split("@")[0];

            if (nomeUsuarioSidebar) {
                nomeUsuarioSidebar.innerText = `Olá, ${primeiroNome}! ✨`;
            }
            alert(`🌸 Bem-vindo(a) de volta, ${primeiroNome}!`);
            fecharTodosPopups();
        }
    });
}


/* --------------------------------------------------------------------------
   4. SISTEMA DE CADASTRO / REGISTRO
   -------------------------------------------------------------------------- */
if (formularioRegistro) {
    formularioRegistro.addEventListener('submit', function (event) {
        event.preventDefault();

        const nomeCompleto = campoNomeRegistro.value.trim();
        const senha = campoSenhaRegistro ? campoSenhaRegistro.value : "";
        const confirmarSenha = campoConfirmarSenhaRegistro ? campoConfirmarSenhaRegistro.value : "";

        // Validação de segurança básica: as duas senhas devem bater
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
            fecharTodosPopups();
        }
    });
}


/* --------------------------------------------------------------------------
   5. FORMULÁRIOS AUXILIARES (RECUPERAÇÃO DE SENHA E CONTATO)
   -------------------------------------------------------------------------- */
if (formRecuperarReal) {
    formRecuperarReal.addEventListener('submit', function (event) {
        event.preventDefault();
        const campoEmailRecuperar = document.getElementById('campo-email-recuperar');
        const emailDigitado = campoEmailRecuperar.value.trim();

        if (emailDigitado !== "") {
            alert(`🌸 Sucesso! As instruções de recuperação foram enviadas para:\n💌 ${emailDigitado}`);
            campoEmailRecuperar.value = "";
            fecharTodosPopups();
        }
    });
}

if (formularioContato) {
    formularioContato.addEventListener('submit', function (event) {
        event.preventDefault();
        const nomeContato = campoNomeContato.value.trim();

        if (nomeContato !== "") {
            alert(`💌 Obrigado pelo contato, ${nomeContato}!\n✨ Sua mensagem foi enviada com sucesso para a equipe do Coffee Time.`);
            formularioContato.reset();
            fecharTodosPopups();
        }
    });
}


/* --------------------------------------------------------------------------
   6. CONFIGURAÇÕES INTERNAS E CUSTOMIZAÇÃO DE TEMA
   -------------------------------------------------------------------------- */

// Função auxiliar centralizada para aplicar ou remover a classe do tema escuro no body
function aplicarTemaEscuro(ativar) {
    if (ativar) {
        document.body.classList.add('tema-escuro');
    } else {
        document.body.classList.remove('tema-escuro');
    }
}

// Escuta a ativação dos rádios para fechar a sidebar automaticamente quando abrir o popup "Sobre" ou "Config"
if (popupSobreControle) {
    popupSobreControle.addEventListener('change', function () {
        if (this.checked && menuCheck) menuCheck.checked = false;
    });
}

if (popupConfigControle) {
    popupConfigControle.addEventListener('change', function () {
        if (this.checked && menuCheck) menuCheck.checked = false;
    });
}

// Event Listener para ler as configurações salvas no navegador assim que a página carregar
document.addEventListener('DOMContentLoaded', () => {
    const configsSalvas = JSON.parse(localStorage.getItem('coffeeTimeConfigs'));
    if (configsSalvas) {
        if (document.getElementById('cfg-tema-escuro')) {
            document.getElementById('cfg-tema-escuro').checked = configsSalvas.temaEscuro || false;
            aplicarTemaEscuro(configsSalvas.temaEscuro);
        }
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

// Manipulador do formulário de salvamento de configurações de preferência
if (formularioConfig) {
    formularioConfig.addEventListener('submit', function (event) {
        event.preventDefault();

        const temaEscuro = document.getElementById('cfg-tema-escuro').checked;
        const notificacoes = document.getElementById('cfg-notificacoes').checked;
        const salvarDados = document.getElementById('cfg-salvar-dados').checked;
        const moeda = document.getElementById('cfg-moeda').value;

        aplicarTemaEscuro(temaEscuro);

        // Agrupa as configurações em um objeto único e converte para string para salvar no LocalStorage
        const objetoConfig = { temaEscuro, notificacoes, salvarDados, moeda };
        localStorage.setItem('coffeeTimeConfigs', JSON.stringify(objetoConfig));

        alert("⚙️ Preferências atualizadas com sucesso e aplicadas!");
        fecharTodosPopups();
    });
}


/* --------------------------------------------------------------------------
   7. ARQUITETURA DE ACESSIBILIDADE AVANÇADA
   -------------------------------------------------------------------------- 
   Lembrete: Esta seção injeta classes específicas de acessibilidade no body 
   e altera dinamicamente o root font-size da aplicação de forma amigável. */

if (botaoAcessibilidade && popupAcessibilidade) {
    botaoAcessibilidade.addEventListener('click', function (event) {
        event.preventDefault();
        popupAcessibilidade.classList.add('ativo'); // Ativa o modal de acessibilidade
    });
}

if (fecharAcessibilidade && popupAcessibilidade) {
    fecharAcessibilidade.addEventListener('click', function () {
        popupAcessibilidade.classList.remove('ativo');
    });
}

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

// Sincronização dos estados com as Tags ARIA para leitores de tela deficientes visuais
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


/* --------------------------------------------------------------------------
   8. ECOSSISTEMA DO CARRINHO DE COMPRAS E PEDIDOS
   -------------------------------------------------------------------------- */

// Alterna a exibição do bloco de endereço condicionado ao tipo de entrega (Delivery x Balcão)
if (cartTipoEntrega && blocoEnderecoEntrega) {
    cartTipoEntrega.addEventListener('change', function () {
        const inputEndereco = document.getElementById('cart-endereco-cliente');
        if (this.value === 'Delivery') {
            blocoEnderecoEntrega.style.display = 'block';
            if (inputEndereco) inputEndereco.setAttribute('required', 'true');
        } else {
            blocoEnderecoEntrega.style.display = 'none';
            if (inputEndereco) inputEndereco.removeAttribute('required');
        }
    });
}

// Controle de abertura e fechamento da interface modal do carrinho fofo
if (btnCarrinhoSidebar && modalCarrinho) {
    btnCarrinhoSidebar.addEventListener('click', function (e) {
        e.preventDefault();
        if (menuCheck) menuCheck.checked = false; // recolhe sidebar

        modalCarrinho.style.display = 'flex';
        renderizarCarrinhoVisual();
    });
}

if (btnFecharCarrinho && modalCarrinho) {
    btnFecharCarrinho.addEventListener('click', function () {
        modalCarrinho.style.display = 'none';
    });
}

// Adicionando escutas de clique em toda a matriz de botões de produtos na vitrine
botoesAdicionar.forEach(botao => {
    botao.addEventListener('click', function () {
        const card = botao.parentElement;
        const nomeProduto = card.querySelector('h3').innerText;
        const precoTexto = card.querySelector('.preco').innerText;

        // Limpa string monetária e converte de 'R$ 15,90' para o formato numérico flutuante do JS (15.90)
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

// Renderização dinâmica do conteúdo injetado internamente no HTML do carrinho
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
        divItem.style.borderBottom = '1px dashed var(--borda-card)';

        divItem.innerHTML = `
            <div>
                <span style="font-weight: bold; color: var(--cor-texto);">${item.quantidade}x</span> ${item.nome}
                <div style="font-size: 13px; color: #C06C84;">R$ ${item.preco.toFixed(2).replace('.', ',')} cada</div>
            </div>
            <div style="display: flex; align-items: center; gap: 10px;">
                <span style="font-weight: bold; color: var(--cor-texto);">R$ ${custoItem.toFixed(2).replace('.', ',')}</span>
                <button class="btn-remover-item" data-index="${index}" style="background: none; border: none; color: #C06C84; cursor: pointer; font-size: 16px;"><i class="fas fa-trash-alt"></i></button>
            </div>
        `;

        conteinerItensCarrinho.appendChild(divItem);
    });

    elementoValorTotal.innerText = `R$ ${totalAcumulado.toFixed(2).replace('.', ',')}`;

    // Re-atribui listeners de remoção individual pós renderização dos elementos filhos
    const botoesRemover = document.querySelectorAll('.btn-remover-item');
    botoesRemover.forEach(btn => {
        btn.addEventListener('click', function () {
            const idx = parseInt(this.getAttribute('data-index'));
            carrinho.splice(idx, 1); // Corta o elemento do array baseado no índice dele
            renderizarCarrinhoVisual();
        });
    });
}

// Gatilho finalizador do Checkout e envio das informações agrupadas para a Duda (Painel Admin)
if (btnFinalizarPedido) {
    btnFinalizarPedido.addEventListener('click', function () {
        if (carrinho.length === 0) {
            alert("🛒 Seu carrinho está vazio! Escolha uma delícia primeiro.");
            return;
        }

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

        // Criação da estrutura de dados do novo pedido simulando banco de dados no LocalStorage
        const novoPedido = {
            id: "#" + Math.floor(2000 + Math.random() * 9000), // Gera código único fofinho aleatório
            cliente: nomeCliente,
            itens: itensTexto,
            tipo: tipoEntrega,
            endereco: tipoEntrega === 'Delivery' ? enderecoCliente : 'Retirada no Balcão',
            pagamento: formaPagamento,
            total: total.toFixed(2)
        };

        // Empurra o objeto estruturado para o array contido na memória persistente LocalStorage
        let listaPedidos = JSON.parse(localStorage.getItem('pedidosAdmin')) || [];
        listaPedidos.unshift(novoPedido); // Adiciona no início da lista
        localStorage.setItem('pedidosAdmin', JSON.stringify(listaPedidos));

        alert(`🎉 Pedido Finalizado com Sucesso!\n✨ Obrigado por comprar no Coffee Time, ${nomeCliente}!\n💰 Total: R$ ${total.toFixed(2).replace('.', ',')}`);

        // Reset completo pós venda do carrinho e formulário associado
        carrinho = [];
        document.getElementById('cart-nome-cliente').value = '';
        document.getElementById('cart-endereco-cliente').value = '';
        if (blocoEnderecoEntrega) blocoEnderecoEntrega.style.display = 'none';

        renderizarCarrinhoVisual();
        if (modalCarrinho) modalCarrinho.style.display = 'none';
    });
}


/* --------------------------------------------------------------------------
   9. FUNÇÕES DE SUPORTE GLOBAL
   -------------------------------------------------------------------------- */
function fecharTodosPopups() {
    if (fecharPopupTodos) {
        fecharPopupTodos.checked = true; // Força a ativação do rádio controlador oculto do reset
    }
}