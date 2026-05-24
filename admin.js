// ANOTAÇÕES DA PAULA: Esse arquivo cuida do Painel de Admin! 🛠️
// Ele pega os dados do formulário de funcionário e também puxa os pedidos da loja!

document.addEventListener('DOMContentLoaded', function () {
    // 1. CARREGAR E RENDERIZAR PEDIDOS VINDOS DA WEB
    carregarPedidosDaLoja();

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
            if (cargo === 'Barista') { icone = 'fa-cookie-bite'; classeCor = 'marrom'; }
            if (cargo === 'Estagiário') { icone = 'fa-seedling'; classeCor = 'amarelo'; }

            novoItem.innerHTML = `
                <div class="avatar-func ${classeCor}"><i class="fas ${icone}"></i></div>
                <div class="func-info">
                    <strong>${nome}</strong>
                    <span>${email}</span>
                </div>
                <span class="badge-cargo cargo-normal">${cargo}</span>
            `;

            listaFuncionariosHtml.appendChild(novoItem);
            alert(`✨ Colaborador(a) ${nome} cadastrado(a) com sucesso no sistema!`);
            formFuncionario.reset();
        });
    }
});

// Função que integra os pedidos salvos no LocalStorage dentro da Tabela Administrativa
function carregarPedidosDaLoja() {
    const tabelaCorpo = document.querySelector('.tabela-admin tbody');
    if (!tabelaCorpo) return;

    // Pega a lista de pedidos integrados da loja
    let listaPedidos = JSON.parse(localStorage.getItem('pedidosAdmin')) || [];

    // Variáveis para somar nas métricas estáticas da página
    let faturamentoExtra = 0;
    let novosPedidosContagem = listaPedidos.length;
    let pendentesExtra = 0;

    listaPedidos.forEach(pedido => {
        const novaLinha = document.createElement('tr');
        novaLinha.style.background = "#FFF9F3"; // Destaque fofo para pedidos reais vindo do site

        // Configura ícone e badge de tipo de entrega
        let badgeTipo = '';
        if (pedido.tipo === 'Delivery') {
            badgeTipo = `<span class="badge-fofo tipo-delivery" title="Endereço: ${pedido.endereco}"><i class="fas fa-motorcycle"></i> Delivery</span>`;
            pendentesExtra++;
        } else {
            badgeTipo = `<span class="badge-fofo tipo-balcao"><i class="fas fa-store"></i> Retirada</span>`;
        }

        // Calcula faturamento total dos novos itens
        faturamentoExtra += parseFloat(pedido.total);

        novaLinha.innerHTML = `
            <td><strong>${pedido.id}</strong></td>
            <td>${pedido.cliente}</td>
            <td>${pedido.itens}</td>
            <td>${badgeTipo}</td>
            <td>${pedido.pagamento}</td>
            <td><span class="badge-fofo status-pendente">Pendente</span></td>
            <td>
              <button class="btn-acao avancar" title="Aceitar Pedido" onclick="atualizarStatusPedido(this)"><i class="fas fa-check"></i></button>
            </td>
        `;

        // Coloca o novo pedido no topo da tabela de controle
        tabelaCorpo.insertBefore(novaLinha, tabelaCorpo.firstChild);
    });

    // Atualiza dinamicamente os Cards de Métricas do topo da página!
    atualizarCardsMetricas(novosPedidosContagem, faturamentoExtra, pendentesExtra);
}

// Atualiza os contadores numéricos do cabeçalho do painel administrativo
function atualizarCardsMetricas(novosPedidos, valorExtra, novosPendentes) {
    const cards = document.querySelectorAll('.card-metrica-admin .numero-metrica');
    if (cards.length >= 4) {
        // Pedidos de hoje (4 estáticos do HTML base + novos do site)
        let totalPedidosHoje = 4 + novosPedidos;
        cards[0].innerText = totalPedidosHoje;

        // Para Entrega (1 estático + novos pendentes de delivery)
        let totalPendentesEntrega = 1 + novosPendentes;
        cards[1].innerText = `${totalPendentesEntrega} Pendentes`;

        // Faturamento (R$ 342,80 estáticos + soma dos valores enviados pela loja)
        let faturamentoBase = 342.80;
        let faturamentoTotal = faturamentoBase + valorExtra;
        cards[3].innerText = `R$ ${faturamentoTotal.toFixed(2).replace('.', ',')}`;
    }
}

// Função interativa fofa para simular o avanço de status ao clicar no check (v) do painel admin
window.atualizarStatusPedido = function (botao) {
    const celulaStatus = botao.parentElement.previousElementSibling;
    const badge = celulaStatus.querySelector('.badge-fofo');

    if (badge.classList.contains('status-pendente')) {
        badge.className = "badge-fofo status-preparo";
        badge.innerHTML = `<i class="fas fa-spinner fa-spin"></i> Em Preparo`;
        botao.title = "Despachar / Avisar Pronto";
    } else if (badge.classList.contains('status-preparo')) {
        badge.className = "badge-fofo status-caminho";
        badge.innerHTML = `<i class="fas fa-shipping-fast"></i> A Caminho`;
        botao.title = "Finalizar Pedido";
    } else {
        badge.className = "badge-fofo status-entregue";
        badge.innerHTML = `Finalizado 🎉`;
        botao.disabled = true;
        botao.style.opacity = "0.5";
    }
};

// 1. Procurar o formulário de cadastro lá no HTML
const formFuncionario = document.getElementById('form-cadastro-colaborador');
const listaFuncionariosHtml = document.querySelector('.lista-funcionarios');

// 2. Quando o usuário clicar no botão de "Salvar" (enviar o formulário):
formFuncionario.addEventListener('submit', function (evento) {
    evento.preventDefault(); // Impede a página de atualizar e sumir com os dados

    // Pegamos os valores que a pessoa digitou nos campos de texto
    const nome = document.getElementById('nome-func').value;
    const email = document.getElementById('email-func').value;
    const cargo = document.getElementById('cargo-func').value;

    // Criamos um novo elemento visual (uma caixinha fofa) com os dados novos
    const novoItem = document.createElement('div');
    novoItem.classList.add('item-funcionario-fofo');

    //Escolhe um ícone dependendo do cargo selecionado
    let icone = 'fa-mug-hot';
    let classeCor = 'rosa';
    if (cargo === 'Barista') { icone = 'fa-cookie-bite'; classeCor = 'marrom'; }
    if (cargo === 'Estagiário') { icone = 'fa-seedling'; classeCor = 'amarelo'; }

    //Coloca o código HTML de dentro do funcionário
    novoItem.innerHTML = `
        <div class="avatar-func ${classeCor}"><i class="fas ${icone}"></i></div>
        <div class="func-info">
            <strong>${nome}</strong>
            <span>${email}</span>
        </div>
        <span class="badge-cargo cargo-normal">${cargo}</span>
    `;

    //Adiciona novo funcionário na lista
    listaFuncionariosHtml.appendChild(novoItem);

    //Mostra uma mensagem de sucesso
    alert(`✨ Colaborador(a) ${nome} cadastrado(a) com sucesso no sistema!`);

    //Limpa o formulário para o próximo cadastro
    formFuncionario.reset();
});

//Recuperar o pedido feito na página inicial!
window.addEventListener('load', function () {
    const totalPedido = localStorage.getItem('ultimoTotal');
    const produtosPedido = localStorage.getItem('ultimosProdutos');

    //Se existir algum pedido vindo da lojinha, avisa o Administrador
    if (totalPedido) {
        console.log(`☕ Alerta de Novo Pedido: Itens [${produtosPedido}] - Total: R$ ${totalPedido}`);
    }
});

window.addEventListener('load', function () {
    const totalPedido = localStorage.getItem('ultimoTotal');
    const produtosPedido = localStorage.getItem('ultimosProdutos');

    const painelPedidosWeb = document.createElement('section');
    painelPedidosWeb.classList.add('painel-secao');
    painelPedidosWeb.innerHTML = `
        <h2><i class="fas fa-coffee"></i> Último Pedido da Web (Integração)</h2>
        <div style="background: #fff; padding: 15px; border-radius: 15px; border: 2px dashed #C06C84;">
            <p><strong>Produtos:</strong> ${produtosPedido || 'Nenhum pedido detetado'}</p>
            <p><strong>Total:</strong> R$ ${totalPedido || '0.00'}</p>
            <button id="btn-gerar-token" style="background: #C06C84; color: white; border: none; padding: 8px 12px; border-radius: 8px; cursor: pointer; font-family: 'Itim';">
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
        // Formato legível para partilha rápida ou parse
        const token = `CLIENTE: WebCliente | ENDERECO: Retirada Balcão | PRODUTOS: ${produtosPedido} | TOTAL: ${totalPedido}`;
        navigator.clipboard.writeText(token);
        alert("✨ Dados do pedido copiados! Vá à Tela do Java e use a Aba de Anotação de Pedidos.");
    });
});