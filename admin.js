// =========================================================================
// 📝 ANOTAÇÕES DA PAULA: Esse arquivo cuida do Painel de Admin! 
// 🛠️ Ele pega os dados do formulário de funcionário e puxa os pedidos da loja.
// =========================================================================

document.addEventListener('DOMContentLoaded', function () {

    // Inicialização: Carrega os pedidos assim que a página inicia
    carregarPedidosDaLoja();

    // ---------------------------------------------------------------------
    // 1. SISTEMA DE FILTROS DOS PEDIDOS
    // ---------------------------------------------------------------------
    const botoesFiltro = document.querySelectorAll('.filtros-fofos .filtro-btn');

    if (botoesFiltro.length > 0) {
        botoesFiltro.forEach(botao => {
            botao.addEventListener('click', function () {
                // Remove a classe 'ativo' de todos os botões de filtro
                botoesFiltro.forEach(btn => btn.classList.remove('ativo'));

                // Adiciona a classe 'ativo' apenas no botão clicado
                this.classList.add('ativo');

                // Pega o tipo de filtro (todos, Pendente, Preparo, etc.) e recarrega a tabela
                const filtro = this.getAttribute('data-filtro');
                carregarPedidosDaLoja(filtro);
            });
        });
    }

    // ---------------------------------------------------------------------
    //  2. SISTEMA DE CADASTRO DE FUNCIONÁRIOS
    // ---------------------------------------------------------------------
    const formFuncionario = document.getElementById('form-cadastro-colaborador');
    const listaFuncionariosHtml = document.querySelector('.lista-funcionarios');

    if (formFuncionario && listaFuncionariosHtml) {
        formFuncionario.addEventListener('submit', function (evento) {
            evento.preventDefault();

            // Captura os valores dos campos do formulário
            const nome = document.getElementById('nome-func').value;
            const email = document.getElementById('email-func').value;
            const cargo = document.getElementById('cargo-func').value;

            // --- INTEGRAÇÃO JAVA: Salva o funcionário recém-criado no LocalStorage ---
            const dadosFuncionario = { nome, email, cargo };
            localStorage.setItem('ultimoFuncionarioAdmin', JSON.stringify(dadosFuncionario));

            // Criação do elemento de interface para o novo funcionário
            const novoItem = document.createElement('div');
            novoItem.classList.add('item-funcionario-fofo');

            // Definições visuais padrão (Card Rosa / Ícone de Café / Cargo Normal)
            let icone = 'fa-mug-hot';
            let classeCor = 'rosa';
            let classeBadge = 'cargo-normal';

            // Customização baseada no cargo selecionado (Compatível com o select do HTML)
            if (cargo === 'Gerente Geral') {
                classeBadge = 'cargo-gerente';
            }
            if (cargo === 'Barista') {
                icone = 'fa-cookie-bite';
                classeCor = 'marrom';
            }
            if (cargo === 'Estagiário') {
                icone = 'fa-seedling';
                classeCor = 'amarelo';
                classeBadge = 'cargo-estagiario';
            }

            // Injeta o HTML com as classes e variáveis definidas acima
            novoItem.innerHTML = `
                <div class="avatar-func ${classeCor}"><i class="fas ${icone}"></i></div>
                <div class="func-info">
                    <strong>${nome}</strong>
                    <span>${email}</span>
                </div>
                <span class="badge-cargo ${classeBadge}">${cargo}</span>    
            `;

            listaFuncionariosHtml.appendChild(novoItem);

            // Atualiza dinamicamente o card de Equipe Ativa e o painel de integração Java
            atualizarCardEquipe();
            inicializarPainelIntegracaoFuncionario();

            alert(`✨ Colaborador(a) ${nome} cadastrado(a) com sucesso no sistema!`);
            formFuncionario.reset();
        });
    }

    // Inicializa as caixinhas de integração após renderizar a página
    inicializarPainelIntegracao();
    inicializarPainelIntegracaoFuncionario();
});

// =========================================================================
// SEÇÃO: MANIPULAÇÃO DE PEDIDOS & MÉTRICAS
// =========================================================================

/**
 * Puxa os pedidos salvos no LocalStorage e renderiza na Tabela Administrativa
 * @param {string} filtroSelecionado - Filtro atual ('todos', 'Pendente', 'Preparo', etc)
 */
function carregarPedidosDaLoja(filtroSelecionado = 'todos') {
    const tabelaCorpo = document.querySelector('.tabela-admin tbody');
    if (!tabelaCorpo) return;

    tabelaCorpo.innerHTML = '';

    // Busca os dados locais ou inicia um array vazio caso não exista nada salvo
    let listaPedidos = JSON.parse(localStorage.getItem('pedidosAdmin')) || [];

    // Variáveis auxiliares para recalcular as métricas do topo do painel
    let faturamentoExtra = 0;
    let novosPedidosContagem = listaPedidos.length;
    let pendentesExtra = 0;

    listaPedidos.forEach(pedido => {
        const statusAtual = pedido.status || 'Pendente';

        // LÓGICA DE FILTRAGEM
        if (filtroSelecionado !== 'todos' && statusAtual !== filtroSelecionado) {
            return;
        }

        const novaLinha = document.createElement('tr');
        novaLinha.style.background = "#FFF9F3";

        // Identificação visual do tipo de atendimento (Delivery vs Balcão)
        let badgeTipo = '';
        if (pedido.tipo === 'Delivery') {
            badgeTipo = `<span class="badge-fofo tipo-delivery" title="Endereço: ${pedido.endereco}"><i class="fas fa-motorcycle"></i> Delivery</span>`;
            if (statusAtual === 'Pendente') {
                pendentesExtra++;
            }
        } else {
            badgeTipo = `<span class="badge-fofo tipo-balcao"><i class="fas fa-store"></i> Retirada</span>`;
        }

        // Soma o valor do pedido ao faturamento global acumulado
        faturamentoExtra += parseFloat(pedido.total || 0);

        // Define as cores e estados com base na etapa atual do pedido
        let badgeStatus = '';
        let acaoBotao = '';

        if (statusAtual === 'Pendente') {
            badgeStatus = `<span class="badge-fofo status-pendente">Pendente</span>`;
            acaoBotao = `<button class="btn-acao avancar" title="Aceitar Pedido" onclick="atualizarStatusPedido('${pedido.id}', this)"><i class="fas fa-check"></i></button>`;
        } else if (statusAtual === 'Preparo') {
            badgeStatus = `<span class="badge-fofo status-preparo"><i class="fas fa-spinner fa-spin"></i> Em Preparo</span>`;
            acaoBotao = `<button class="btn-acao avancar" title="Despachar / Avisar Pronto" onclick="atualizarStatusPedido('${pedido.id}', this)"><i class="fas fa-check"></i></button>`;
        } else if (statusAtual === 'A Caminho') {
            badgeStatus = `<span class="badge-fofo status-caminho"><i class="fas fa-shipping-fast"></i> A Caminho</span>`;
            acaoBotao = `<button class="btn-acao avancar" title="Finalizar Pedido" onclick="atualizarStatusPedido('${pedido.id}', this)"><i class="fas fa-check"></i></button>`;
        } else if (statusAtual === 'Finalizado') {
            badgeStatus = `<span class="badge-fofo status-entregue">Finalizado 🎉</span>`;
            acaoBotao = `<button class="btn-acao excluir" title="Excluir Pedido" onclick="excluirPedido('${pedido.id}')" style="background: #e74c3c; color: white; border: none; padding: 5px 10px; border-radius: 5px; cursor: pointer;"><i class="fas fa-trash"></i></button>`;
        }

        // Constrói a estrutura HTML da linha da tabela
        novaLinha.innerHTML = `
            <td><strong>${pedido.id}</strong></td>
            <td>${pedido.cliente}</td>
            <td>${pedido.itens}</td>
            <td>${badgeTipo}</td>
            <td>${pedido.pagamento}</td>
            <td>${badgeStatus}</td>
            <td>${acaoBotao}</td>
        `;

        // Insere sempre no topo para que os pedidos mais novos apareçam primeiro
        tabelaCorpo.insertBefore(novaLinha, tabelaCorpo.firstChild);
    });

    // Atualiza os painéis numéricos de resumo
    atualizarCardsMetricas(novosPedidosContagem, faturamentoExtra, pendentesExtra);
    atualizarCardEquipe();
}

/**
 * Atualiza os contadores numéricos informativos exibidos no cabeçalho
 */
function atualizarCardsMetricas(novosPedidos, valorExtra, novosPendentes) {
    const cards = document.querySelectorAll('.card-metrica-admin .numero-metrica');
    if (cards.length >= 4) {
        // Card [0]: Total Geral de Pedidos
        let totalPedidosHoje = novosPedidos;
        cards[0].innerText = totalPedidosHoje;

        // Card [1]: Pedidos Pendentes (Garante o plural correto caso não seja 1)
        let totalPendentesEntrega = novosPendentes;
        cards[1].innerText = `${totalPendentesEntrega} Pendente${totalPendentesEntrega !== 1 ? 's' : ''}`;

        // Card [3]: Faturamento Total formatado em Moeda Real (R$)
        let faturamentoTotal = valorExtra;
        cards[3].innerText = `R$ ${faturamentoTotal.toFixed(2).replace('.', ',')}`;
    }
}

/**
 * Conta os elementos da lista de funcionários na tela e atualiza o respectivo Card [2]
 */
function atualizarCardEquipe() {
    const totalMembros = document.querySelectorAll('.lista-funcionarios .item-funcionario-fofo').length;
    const cardEquipe = document.querySelectorAll('.numero-metrica')[2];
    if (cardEquipe) {
        cardEquipe.textContent = totalMembros;
    }
}

/**
 * Avança o status do ciclo de vida do pedido (Pendente -> Preparo -> A Caminho -> Finalizado)
 */
window.atualizarStatusPedido = function (idPedido, botao) {
    let listaPedidos = JSON.parse(localStorage.getItem('pedidosAdmin')) || [];
    let pedido = listaPedidos.find(p => p.id == idPedido);

    if (pedido) {
        const statusAtual = pedido.status || 'Pendente';
        if (statusAtual === 'Pendente') pedido.status = 'Preparo';
        else if (statusAtual === 'Preparo') pedido.status = 'A Caminho';
        else if (statusAtual === 'A Caminho') pedido.status = 'Finalizado';

        localStorage.setItem('pedidosAdmin', JSON.stringify(listaPedidos));

        // Preserva o filtro que estava ativo ao recarregar a visualização
        const filtroAtivo = document.querySelector('.filtro-btn.ativo');
        const filtro = filtroAtivo ? filtroAtivo.getAttribute('data-filtro') : 'todos';
        carregarPedidosDaLoja(filtro);
    }
};

/**
 * Remove o registro de forma permanente do LocalStorage
 */
window.excluirPedido = function (idPedido) {
    if (confirm("Deseja realmente apagar o histórico deste pedido?")) {
        let listaPedidos = JSON.parse(localStorage.getItem('pedidosAdmin')) || [];

        // Filtra mantendo todos menos o ID que foi excluído
        listaPedidos = listaPedidos.filter(p => p.id != idPedido);
        localStorage.setItem('pedidosAdmin', JSON.stringify(listaPedidos));

        // Recarrega e atualiza os painéis integrados
        const filtroAtivo = document.querySelector('.filtro-btn.ativo');
        const filtro = filtroAtivo ? filtroAtivo.getAttribute('data-filtro') : 'todos';
        carregarPedidosDaLoja(filtro);
        inicializarPainelIntegracao();
    }
};


// =========================================================================
// ☕ SEÇÃO: INTEGRAÇÃO COM SISTEMA DESKTOP (JAVA FOUNDATIONS)
// =========================================================================

/**
 * Painel que estrutura e gera a String tokenizada do ÚLTIMO PEDIDO para área de transferência
 */
function inicializarPainelIntegracao() {
    let painelExistente = document.getElementById('painel-integracao-java');
    if (painelExistente) painelExistente.remove();

    let listaPedidos = JSON.parse(localStorage.getItem('pedidosAdmin')) || [];
    let ultimoPedido = listaPedidos[0];

    const produtosPedido = ultimoPedido ? ultimoPedido.itens : 'Nenhum pedido detectado';
    const totalPedido = ultimoPedido ? ultimoPedido.total : '0.00';

    const painelPedidosWeb = document.createElement('section');
    painelPedidosWeb.id = 'painel-integracao-java';
    painelPedidosWeb.classList.add('painel-secao');
    painelPedidosWeb.innerHTML = `
        <h2 style="text-align: center;"><i class="fas fa-coffee"></i> Último Pedido da Web (Integração)</h2>
        <div style="background: #fff; padding: 15px; border-radius: 15px; border: 2px dashed #C06C84; text-align: center;">
            <p><strong>Produtos:</strong> ${produtosPedido}</p>
            <p><strong>Total:</strong> R$ ${parseFloat(totalPedido).toFixed(2).replace('.', ',')}</p>
            <button id="btn-gerar-token" style="background: #C06C84; color: white; border: none; padding: 8px 12px; border-radius: 8px; cursor: pointer; font-family: 'Itim'; margin-top: 10px;">
                Copiar Dados para o Java ☕
            </button>
        </div>
    `;

    const container = document.querySelector('.admin-container') || document.body;
    container.appendChild(painelPedidosWeb);

    document.getElementById('btn-gerar-token').addEventListener('click', function () {
        if (!ultimoPedido) {
            alert("Não há pedidos no sistema para exportar!");
            return;
        }

        // Formato exportação: idApenas;cliente;endereco;itens;pagamento;tipo;total
        const numeroApenas = ultimoPedido.id.replace('#', '');
        const token = `${numeroApenas};${ultimoPedido.cliente};${ultimoPedido.endereco};${ultimoPedido.itens};${ultimoPedido.pagamento};${ultimoPedido.tipo};${ultimoPedido.total}`;

        navigator.clipboard.writeText(token);
        alert("✨ Dados estruturados copiados com sucesso!\n☕ Vá ao sistema Java e clique em 'Importar Pedido da Web'.");
    });
}

/**
 * Painel que estrutura e gera a String tokenizada do ÚLTIMO COLABORADOR cadastrado para área de transferência
 */
function inicializarPainelIntegracaoFuncionario() {
    let painelExistente = document.getElementById('painel-integracao-java-func');
    if (painelExistente) painelExistente.remove();

    let ultimoFunc = JSON.parse(localStorage.getItem('ultimoFuncionarioAdmin'));

    const nomeFunc = ultimoFunc ? ultimoFunc.nome : 'Nenhum cadastro recente';
    const emailFunc = ultimoFunc ? ultimoFunc.email : 'corporativo@coffeetime.com';
    const cargoFunc = ultimoFunc ? ultimoFunc.cargo : 'Nenhum';

    const painelFuncWeb = document.createElement('section');
    painelFuncWeb.id = 'painel-integracao-java-func';
    painelFuncWeb.classList.add('painel-secao');
    painelFuncWeb.innerHTML = `
        <h2 style="text-align: center;"><i class="fas fa-user-tie"></i> Último Colaborador (Integração)</h2>
        <div style="background: #fff; padding: 15px; border-radius: 15px; border: 2px dashed #45B69C; text-align: center;">
            <p><strong>Nome:</strong> ${nomeFunc}</p>
            <p><strong>Cargo:</strong> ${cargoFunc}</p>
            <button id="btn-gerar-token-func" style="background: #45B69C; color: white; border: none; padding: 8px 12px; border-radius: 8px; cursor: pointer; font-family: 'Itim'; margin-top: 10px;">
                Copiar Funcionário para o Java 👤☕
            </button>
        </div>
    `;

    const container = document.querySelector('.admin-container') || document.body;
    container.appendChild(painelFuncWeb);

    document.getElementById('btn-gerar-token-func').addEventListener('click', function () {
        if (!ultimoFunc) {
            alert("Não há funcionários cadastrados nesta sessão para exportar!");
            return;
        }

        // Formato string de transferência para tratamento com String.split(";") no Java: Nome;Email;Cargo
        const tokenFunc = `${ultimoFunc.nome};${ultimoFunc.email};${ultimoFunc.cargo}`;

        navigator.clipboard.writeText(tokenFunc);
        alert("✨ Dados do colaborador copiados com sucesso!\n☕ Vá ao sistema Java e clique em 'Importar Funcionário'.");
    });
}


// =========================================================================
// 🔄 SEÇÃO: ESCUTA DE SINCRONIZAÇÃO DE STORAGE (MULTI-ABA)
// =========================================================================

// Sincroniza em tempo real caso o LocalStorage mude em outra aba aberta no mesmo navegador
window.addEventListener('storage', function (evento) {
    if (evento.key === 'pedidosAdmin') {
        const filtroAtivo = document.querySelector('.filtro-btn.ativo');
        const filtro = filtroAtivo ? filtroAtivo.getAttribute('data-filtro') : 'todos';
        carregarPedidosDaLoja(filtro);
        inicializarPainelIntegracao();
    }
    if (evento.key === 'ultimoFuncionarioAdmin') {
        inicializarPainelIntegracaoFuncionario();
    }
});