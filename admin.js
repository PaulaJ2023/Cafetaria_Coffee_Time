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

    // Escolhemos um ícone bonitinho dependendo do cargo selecionado
    let icone = 'fa-mug-hot';
    let classeCor = 'rosa';
    if (cargo === 'Barista') { icone = 'fa-cookie-bite'; classeCor = 'marrom'; }
    if (cargo === 'Estagiário') { icone = 'fa-seedling'; classeCor = 'amarelo'; }

    // Colocamos o código HTML de dentro do funcionário de forma simples
    novoItem.innerHTML = `
        <div class="avatar-func ${classeCor}"><i class="fas ${icone}"></i></div>
        <div class="func-info">
            <strong>${nome}</strong>
            <span>${email}</span>
        </div>
        <span class="badge-cargo cargo-normal">${cargo}</span>
    `;

    // Adicionamos esse novo funcionário na nossa lista visual!
    listaFuncionariosHtml.appendChild(novoItem);

    // Mostramos uma mensagem fofa de sucesso
    alert(`✨ Colaborador(a) ${nome} cadastrado(a) com sucesso no sistema!`);

    // Limpamos o formulário para o próximo cadastro
    formFuncionario.reset();
});

// FUNÇÃO EXTRA BÔNUS: Recuperar o pedido feito na página inicial!
window.addEventListener('load', function () {
    const totalPedido = localStorage.getItem('ultimoTotal');
    const produtosPedido = localStorage.getItem('ultimosProdutos');

    // Se existir algum pedido vindo da lojinha, avisa o Administrador
    if (totalPedido) {
        console.log(`☕ Alerta de Novo Pedido: Itens [${produtosPedido}] - Total: R$ ${totalPedido}`);
    }
});