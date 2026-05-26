// ANOTAÇÕES DA PAULA: Esse arquivo cuida do Painel de Admin! 🛠️
// Ele pega os dados do formulário de funcionário e também puxa os pedidos da loja!
document.addEventListener('DOMContentLoaded', function () {

    // Carrega os pedidos assim que a página inicia
    carregarPedidosDaLoja();

    // 1. SISTEMA DE FILTROS DOS PEDIDOS
    const botoesFiltro = document.querySelectorAll('.filtros-fofos .filtro-btn');
    if (botoesFiltro.length > 0) {
        botoesFiltro.forEach(botao => {
            botao.addEventListener('click', function () {
                // Remove a classe 'ativo' de todos os botões de filtro
                botoesFiltro.forEach(btn => btn.classList.remove('ativo'));
                // Adiciona a classe 'ativo' apenas no botão clicado
                this.classList.add('ativo');

                // Pega o tipo de filtro (todos, Pendente, Preparo, etc.)
                const filtro = this.getAttribute('data-filtro');
                // Recarrega a tabela aplicando o filtro escolher
                carregarPedidosDaLoja(filtro);
            });
        });
    }

    // 2. SISTEMA DE CADASTRO DE FUNCIONÁRIOS
    const formFuncionario = document.getElementById('form-cadastro-colaborador');
    const listaFuncionariosHtml = document.querySelector('.lista-funcionarios');

    if (formFuncionario && listaFuncionariosHtml) {
        formFuncionario.addEventListener('submit', function (evento) {
            evento.preventDefault();

            const nome = document.getElementById('nome-func').value;
            const email = document.getElementById('email-func').value;
            const cargo = document.getElementById('cargo-func').value;

            // --- INTEGRAÇÃO JAVA: Salva o funcionário recém-criado no LocalStorage ---
            const dadosFuncionario = { nome, email, cargo };
            localStorage.setItem('ultimoFuncionarioAdmin', JSON.stringify(dadosFuncionario));

            const novoItem = document.createElement('div');
            novoItem.classList.add('item-funcionario-fofo');

            let icone = 'fa-mug-hot';
            let classeCor = 'rosa';
            let classeBadge = 'cargo-normal';

            // Correção: "Gerente Geral" para bater com o select do HTML
            if (cargo === 'Gerente Geral') classeBadge = 'cargo-gerente';
            if (cargo === 'Barista') { icone = 'fa-cookie-bite'; classeCor = 'marrom'; }
            if (cargo === 'Estagiário') { icone = 'fa-seedling'; classeCor = 'amarelo'; classeBadge = 'cargo-estagiario'; }

            novoItem.innerHTML = `
                <div class="avatar-func ${classeCor}"><i class="fas ${icone}"></i></div>
                <div class="func-info">
                    <strong>${nome}</strong>
                    <span>${email}</span>
                </div>
                <span class="badge-cargo ${classeBadge}">${cargo}</span>    
            `;

            listaFuncionariosHtml.appendChild(novoItem);

            // Atualiza dinamicamente o card de Equipe Ativa
            atualizarCardEquipe();

            // Recarrega o painel de exportação do funcionário para o Java
            inicializarPainelIntegracaoFuncionario();

            alert(`✨ Colaborador(a) ${nome} cadastrado(a) com sucesso no sistema!`);
            formFuncionario.reset();
        });
    }

    // Inicializa as caixinhas de integração após renderizar a página
    inicializarPainelIntegracao();
    inicializarPainelIntegracaoFuncionario(); // <--- Nova inicialização
});

// Função que integra os pedidos salvos no LocalStorage dentro da Tabela Administrativa
function carregarPedidosDaLoja(filtroSelecionado = 'todos') {
    const tabelaCorpo = document.querySelector('.tabela-admin tbody');
    if (!tabelaCorpo) return;

    tabelaCorpo.innerHTML = '';

    let listaPedidos = JSON.parse(localStorage.getItem('pedidosAdmin')) || [];

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

        let badgeTipo = '';
        if (pedido.tipo === 'Delivery') {
            badgeTipo = `<span class="badge-fofo tipo-delivery" title="Endereço: ${pedido.endereco}"><i class="fas fa-motorcycle"></i> Delivery</span>`;
            if (statusAtual === 'Pendente') {
                pendentesExtra++;
            }
        } else {
            badgeTipo = `<span class="badge-fofo tipo-balcao"><i class="fas fa-store"></i> Retirada</span>`;
        }

        faturamentoExtra += parseFloat(pedido.total || 0);

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

        novaLinha.innerHTML = `
            <td><strong>${pedido.id}</strong></td>
            <td>${pedido.cliente}</td>
            <td>${pedido.itens}</td>
            <td>${badgeTipo}</td>
            <td>${pedido.pagamento}</td>
            <td>${badgeStatus}</td>
            <td>${acaoBotao}</td>
        `;

        tabelaCorpo.insertBefore(novaLinha, tabelaCorpo.firstChild);
    });

    atualizarCardsMetricas(novosPedidosContagem, faturamentoExtra, pendentesExtra);
    atualizarCardEquipe();
}

// Atualiza os contadores numéricos do cabeçalho do painel administrativo
function atualizarCardsMetricas(novosPedidos, valorExtra, novosPendentes) {
    const cards = document.querySelectorAll('.card-metrica-admin .numero-metrica');
    if (cards.length >= 4) {
        let totalPedidosHoje = novosPedidos;
        cards[0].innerText = totalPedidosHoje;

        let totalPendentesEntrega = novosPendentes;
        cards[1].innerText = `${totalPendentesEntrega} Pendente${totalPendentesEntrega !== 1 ? 's' : ''}`;

        let faturamentoTotal = valorExtra;
        cards[3].innerText = `R$ ${faturamentoTotal.toFixed(2).replace('.', ',')}`;
    }
}

// Atualiza o contador de equipe baseado nos itens visíveis na lista
function atualizarCardEquipe() {
    const totalMembros = document.querySelectorAll('.lista-funcionarios .item-funcionario-fofo').length;
    const cardEquipe = document.querySelectorAll('.numero-metrica')[2];
    if (cardEquipe) {
        cardEquipe.textContent = totalMembros;
    }
}

// Função interativa para avançar o status e salvar no localStorage
window.atualizarStatusPedido = function (idPedido, botao) {
    let listaPedidos = JSON.parse(localStorage.getItem('pedidosAdmin')) || [];
    let pedido = listaPedidos.find(p => p.id == idPedido);

    if (pedido) {
        const statusAtual = pedido.status || 'Pendente';
        if (statusAtual === 'Pendente') {
            pedido.status = 'Preparo';
        } else if (statusAtual === 'Preparo') {
            pedido.status = 'A Caminho';
        } else if (statusAtual === 'A Caminho') {
            pedido.status = 'Finalizado';
        }

        localStorage.setItem('pedidosAdmin', JSON.stringify(listaPedidos));

        const filtroAtivo = document.querySelector('.filtro-btn.ativo');
        const filtro = filtroAtivo ? filtroAtivo.getAttribute('data-filtro') : 'todos';
        carregarPedidosDaLoja(filtro);
    }
};

// Nova função para excluir o pedido permanentemente do LocalStorage
window.excluirPedido = function (idPedido) {
    if (confirm("Deseja realmente apagar o histórico deste pedido?")) {
        let listaPedidos = JSON.parse(localStorage.getItem('pedidosAdmin')) || [];

        listaPedidos = listaPedidos.filter(p => p.id != idPedido);
        localStorage.setItem('pedidosAdmin', JSON.stringify(listaPedidos));

        const filtroAtivo = document.querySelector('.filtro-btn.ativo');
        const filtro = filtroAtivo ? filtroAtivo.getAttribute('data-filtro') : 'todos';
        carregarPedidosDaLoja(filtro);
        inicializarPainelIntegracao();
    }
};

// Seção de Integração de logs da Web atualizada e corrigida
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
    // Insere o painel de pedidos no início ou fim do container
    container.appendChild(painelPedidosWeb);

    document.getElementById('btn-gerar-token').addEventListener('click', function () {
        if (!ultimoPedido) {
            alert("Não há pedidos no sistema para exportar!");
            return;
        }

        const numeroApenas = ultimoPedido.id.replace('#', '');
        const token = `${numeroApenas};${ultimoPedido.cliente};${ultimoPedido.endereco};${ultimoPedido.itens};${ultimoPedido.pagamento};${ultimoPedido.tipo};${ultimoPedido.total}`;

        navigator.clipboard.writeText(token);
        alert("✨ Dados estruturados copiados com sucesso!\n☕ Vá ao sistema Java e clique em 'Importar Pedido da Web'.");
    });
}

// --- NOVA FUNÇÃO: Integração de Funcionários para o Java ---
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

        // Formato da string de transferência para o Java: Nome;Email;Cargo
        const tokenFunc = `${ultimoFunc.nome};${ultimoFunc.email};${ultimoFunc.cargo}`;

        navigator.clipboard.writeText(tokenFunc);
        alert("✨ Dados do colaborador copiados com sucesso!\n☕ Vá ao sistema Java e clique em 'Importar Funcionário'.");
    });
}

// Sincronização entre abas abertas caso o LocalStorage mude
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