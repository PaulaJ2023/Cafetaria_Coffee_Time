# ☕ Coffee Time
Projeto A3 - Site para uma loja fisica e delivery de Cafés com doces e salgados como acompanhamento.
* Site: [Link](https://paulaj2023.github.io/Coffee_Time/)
* Administração: [Link](https://paulaj2023.github.io/Coffee_Time/admin.html) 
---
# 📑 Sobre o Projeto
O **Coffee Time** é uma plataforma completa para uma loja fisica e delivery especializada em cafés especiais com salgados e doces artesanais. A plataforma conecta clientes, funcionários e administradores, centralizando o gerenciamento de pedidos, pagamentos e avaliações em um único lugar.

Professores: Edjane Mikaelly Silva de Azevedo

---
# 👥 Desenvolvedores

* Paula Jordania do Nascimento de Lima -
Curso: Ciência da Computação - UNP Campus Salgado Filho - Turno: Manhã

 * Maria Eduarda Rodrigues Gonçalves -
Curso: ADS - UNP Campus Salgado Filho - Turno: Manhã

---
#  👥 Perfis dos usuários

## Cliente 👤
* Cadastrar e Logar
* Adiciona produtos ao carrinho e simula os valores.
* Realiza pedidos preenchendo informações como nome, forma de pagamento e tipo de entrega (Delivery ou Retirada no Balcão).
* Acompanha o status dos seus pedidos

## Funcionários / Membros Ativos 👥
* Gerente: Perfil com maior nível de acesso na gestão.
* Barista: Responsável pelo preparo dos cafés e produtos.
* Estagiária: Perfil de suporte à equipe.

## Administrador 🛠️
* Gerencia Funcionarios
* Controlar pedidos
* Administrar a plataforma
* Painel de Acessibilidade (UI/IHC)
* Modo Escuro
* Listagem e Filtro de Pedidos
* Controle de Progresso
* Dashboard de Métricas
* Cadastro de Equipe
* Gerador de Token (Exportação)

---
# Funcionalidades Principais ⚙️
* Login Inteligente:
* Cadastro com Validação
* Carrinho de Compras
* Formulário Adaptável

---
# Requísitos Aplicados 📋
* Uso de nove classes
* Criação de objetos
* Atributos e métodos
* Encapsulamento
* Herança
* Polimorfismo
* Cadastro, listagem e consulta de dados
* Organização do código

---
# 🧩 Entidades do Sistema

|Tipo de Entidade             | Classe no Código Java | Justificativa                                                            |
| --------------------------- | --------------------- | ------------------------------------------------------------------------ |
| `/Entidade Forte`           | Funcionario / Cliente | Possuem identificadores próprios (CPF) e existem de forma autónoma       |
| `/Entidade Fraca`           | Gerente / Estagiario  | Dependem logicamente e estruturalmente da classe Funcionario (Herança)   |
| `/Entidade Associativa`     | Pedido                | Une os dados do fluxo de venda (Cliente + Produtos + Valores do momento) |

# 🔗 Relacionamentos

## 🔹 One-to-One

* Pedido ➔ Pagamento

## 🔹 One-to-Many

* Cliente ➔ Pedido

## 🔹 Many-to-Many

* Pedido ➔ Produtos

---

# 🛠️ Tecnologias Utilizadas

## Back-end

* Java SE
* Java Swing
* Java AWT

## Integração de Dados

* Web LocalStorage (API do Navegador)
* Java Collections (ArrayList)
* Clipboard Data Transfer (Mecanismo de Integração)

## Front-end

* HTML
* CSS
* JavaScript
* Font Awesome

---

# 🏗️ Modelo de Dados com localStorage e ArrayList

Nosso código código não possui um banco de dados relacional físico configurado (como MySQL ou PostgreSQL). Em vez disso, usamos o localStorage no navegador e ArrayList na memória do Java para guardar as informações. No entanto, estruturalmente, nosso programa possui sim um Modelo de Dados Implícito. As nossas classes Java e os objetos criados no JavaScript foram desenhados com atributos que se conectam exatamente como tabelas de um banco de dados.

```mermaid
erDiagram
    %% Relacionamento de Herança/Especialização representado logicamente
    FUNCIONARIO ||--o| GERENTE : pode_ser
    FUNCIONARIO ||--o| ESTAGIARIO : pode_ser

    %% Relacionamentos do fluxo de negócios do Coffee Time
    CLIENTE ||--o{ PEDIDO : realiza
    PEDIDO ||--|{ ITEM_PEDIDO : contem
    PRODUTO ||--o{ ITEM_PEDIDO : pertence_a
    PEDIDO ||--|| PAGAMENTO : possui

    CLIENTE {
        string cpf PK
        string nome
        string telefone
        string endereco
    }

    PEDIDO {
        int numeroPedido PK
        string nomeCliente FK
        string endereco
        string retirarPedido
        float totalPagar
    }

    ITEM_PEDIDO {
        int id FK
        int numeroPedido FK
        string nomeProduto
        int quantidade
    }

    PRODUTO {
        string nome PK
        float preco
        string categoria
    }

    PAGAMENTO {
        int numeroPedido PK
        string formaPagamento
        float valorPagamento
    }

    FUNCIONARIO {
        string cpf PK
        string nome
        float salarioBase
        float valorHora
        float horasTrabalhadas
    }

    GERENTE {
        string cpf PK
        float gratificacao
        float participacaoLucros
    }

    ESTAGIARIO {
        string cpf PK
        float bolsaAuxilio
    }
