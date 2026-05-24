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
                // Recarrega a tabela aplicando o filtro escolhido
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

            alert(`✨ Colaborador(a) ${nome} cadastrado(a) com sucesso no sistema!`);
            formFuncionario.reset();
        });
    }
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
        // Define o status atual do pedido (se não houver, assume 'Pendente')
        const statusAtual = pedido.status || 'Pendente';

        // LÓGICA DE FILTRAGEM: Se o filtro não for 'todos' e for diferente do status do pedido, ignora este pedido
        if (filtroSelecionado !== 'todos' && statusAtual !== filtroSelecionado) {
            return; // Salta para o próximo pedido sem colocar na tabela
        }

        const novaLinha = document.createElement('tr');
        novaLinha.style.background = "#FFF9F3";

        // Configura ícone e badge de tipo de entrega
        let badgeTipo = '';
        if (pedido.tipo === 'Delivery') {
            badgeTipo = `<span class="badge-fofo tipo-delivery" title="Endereço: ${pedido.endereco}"><i class="fas fa-motorcycle"></i> Delivery</span>`;
            if (pedido.status !== 'Finalizado') {
                pendentesExtra++;
            }
        } else {
            badgeTipo = `<span class="badge-fofo tipo-balcao"><i class="fas fa-store"></i> Retirada</span>`;
        }

        // Calcula faturamento total dos novos itens
        faturamentoExtra += parseFloat(pedido.total || 0);

        // Define a estrutura do Badge de Status baseado no que está salvo
        let badgeStatus = '';
        let acaoBotao = '';

        if (!pedido.status || pedido.status === 'Pendente') {
            badgeStatus = `<span class="badge-fofo status-pendente">Pendente</span>`;
            acaoBotao = `<button class="btn-acao avancar" title="Aceitar Pedido" onclick="atualizarStatusPedido('${pedido.id}', this)"><i class="fas fa-check"></i></button>`;
        } else if (pedido.status === 'Preparo') {
            badgeStatus = `<span class="badge-fofo status-preparo"><i class="fas fa-spinner fa-spin"></i> Em Preparo</span>`;
            acaoBotao = `<button class="btn-acao avancar" title="Despachar / Avisar Pronto" onclick="atualizarStatusPedido('${pedido.id}', this)"><i class="fas fa-check"></i></button>`;
        } else if (pedido.status === 'A Caminho') {
            badgeStatus = `<span class="badge-fofo status-caminho"><i class="fas fa-shipping-fast"></i> A Caminho</span>`;
            acaoBotao = `<button class="btn-acao avancar" title="Finalizar Pedido" onclick="atualizarStatusPedido('${pedido.id}', this)"><i class="fas fa-check"></i></button>`;
        } else if (pedido.status === 'Finalizado') {
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

        // Coloca o novo pedido no topo da tabela de controle
        tabelaCorpo.insertBefore(novaLinha, tabelaCorpo.firstChild);
    });

    // Atualiza dinamicamente os Cards de Métricas do topo da página!
    atualizarCardsMetricas(novosPedidosContagem, faturamentoExtra, pendentesExtra);
    atualizarCardEquipe();
}

// Atualiza os contadores numéricos do cabeçalho do painel administrativo
function atualizarCardsMetricas(novosPedidos, valorExtra, novosPendentes) {
    const cards = document.querySelectorAll('.card-metrica-admin .numero-metrica');
    if (cards.length >= 4) {
        // Pedidos de hoje
        let totalPedidosHoje = 4 + novosPedidos;
        cards[0].innerText = totalPedidosHoje;

        // Para Entrega
        let totalPendentesEntrega = 1 + novosPendentes;
        cards[1].innerText = `${totalPendentesEntrega} Pendentes`;

        // Faturamento
        let faturamentoBase = 342.80;
        let faturamentoTotal = faturamentoBase + valorExtra;
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
        if (!pedido.status || pedido.status === 'Pendente') {
            pedido.status = 'Preparo';
        } else if (pedido.status === 'Preparo') {
            pedido.status = 'A Caminho';
        } else if (pedido.status === 'A Caminho') {
            pedido.status = 'Finalizado';
        }

        localStorage.setItem('pedidosAdmin', JSON.stringify(listaPedidos));

        // Mantém o filtro atual ao recarregar a lista após a mudança de status
        const filtroAtivo = document.querySelector('.filtro-btn.ativo');
        const filtro = filtroAtivo ? filtroAtivo.getAttribute('data-filtro') : 'todos';
        carregarPedidosDaLoja(filtro);
    }
};

// Nova função para excluir o pedido permanentemente do LocalStorage
window.excluirPedido = function (idPedido) {
    if (confirm("Deseja realmente apagar o histórico deste pedido?")) {
        let listaPedidos = JSON.parse(localStorage.getItem('pedidosAdmin')) || [];

        // Filtra para remover apenas o item selecionado
        listaPedidos = listaPedidos.filter(p => p.id != idPedido);

        localStorage.setItem('pedidosAdmin', JSON.stringify(listaPedidos));

        const filtroAtivo = document.querySelector('.filtro-btn.ativo');
        const filtro = filtroAtivo ? filtroAtivo.getAttribute('data-filtro') : 'todos';
        carregarPedidosDaLoja(filtro);
    }
};

// Seção de Integração de logs da Web (Exibição da caixa pontilhada de cópia)
window.addEventListener('load', function () {
    const totalPedido = localStorage.getItem('ultimoTotal');
    const produtosPedido = localStorage.getItem('ultimosProdutos');

    const painelPedidosWeb = document.createElement('section');
    painelPedidosWeb.classList.add('painel-secao');
    painelPedidosWeb.innerHTML = `
        <h2 style="text-align: center;"><i class="fas fa-coffee"></i> Último Pedido da Web (Integração)</h2>
        <div style="background: #fff; padding: 15px; border-radius: 15px; border: 2px dashed #C06C84; text-align: center;">
            <p><strong>Produtos:</strong> ${produtosPedido || 'Nenhum pedido detectado'}</p>
            <p><strong>Total:</strong> R$ ${totalPedido || '0.00'}</p>
            <button id="btn-gerar-token" style="background: #C06C84; color: white; border: none; padding: 8px 12px; border-radius: 8px; cursor: pointer; font-family: 'Itim'; margin-top: 10px;">
                Copiar Dados para o Java ☕
            </button>
        </div>
    `;

    const container = document.querySelector('.admin-container') || document.body;
    container.appendChild(painelPedidosWeb);

    document.getElementById('btn-gerar-token').addEventListener('click', function () {
        if (!totalPedido) {
            alert("Não há pedidos no carrinho para exportar!");
            return;
        }
        const token = `CLIENTE: WebCliente | ENDERECO: Retirada Balcão | PRODUTOS: ${produtosPedido} | TOTAL: ${totalPedido}`;
        navigator.clipboard.writeText(token);
        alert("✨ Dados do pedido copiados! Vá à Tela do Java e use a Aba de Anotação de Pedidos.");
    });
});

// Sincronização entre abas abertas caso o LocalStorage mude
window.addEventListener('storage', function (evento) {
    if (evento.key === 'pedidosAdmin') {
        const filtroAtivo = document.querySelector('.filtro-btn.ativo');
        const filtro = filtroAtivo ? filtroAtivo.getAttribute('data-filtro') : 'todos';
        carregarPedidosDaLoja(filtro);
    }
});