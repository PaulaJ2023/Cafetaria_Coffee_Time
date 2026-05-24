// ANOTAÇÕES DA PAULA: Esse arquivo cuida do Painel de Admin! 🛠️
// Ele pega os dados do formulário de funcionário e joga na lista da tela.

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

    document.getElementById('btn-gerar-token').addEventListener('click', function() {
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