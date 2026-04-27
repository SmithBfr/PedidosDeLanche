# 🍔 Pedidos de Lanche com RabbitMQ

## 📌 Descrição

API REST desenvolvida com **Spring Boot** para gerenciamento de pedidos de lanche, com processamento assíncrono utilizando **RabbitMQ**.

O sistema permite:

* Criar pedidos a partir de uma string posicional (estilo CNAB)
* Calcular o valor total automaticamente
* Consultar pedidos
* Enviar pedidos para uma fila
* Atualizar o status para **ENTREGUE** via consumo de mensagens

---

## 🧠 Arquitetura

O projeto segue uma arquitetura em camadas:

```text
Controller → Service → Producer → RabbitMQ → Consumer → Service → Banco de Dados
```

---

## 🔄 Fluxo de funcionamento

1. O cliente envia uma string com os dados do pedido
2. O sistema faz o parsing dessa string (`CnabParser`)
3. O pedido é salvo com status **RECEBIDO**
4. Ao solicitar atualização, o pedido é enviado para a fila
5. O Consumer consome a mensagem
6. O pedido é atualizado para **ENTREGUE**

---

## 🛠️ Tecnologias utilizadas

* Java 17+
* Spring Boot
* Spring Data JPA
* Spring AMQP
* H2 Database
* Angular (frontend)
* RabbitMQ

---

## 📥 Entrada de dados (string posicional)

A criação do pedido utiliza uma string com até **40 caracteres**, processada pelo `CnabParser`.

### 📌 Estrutura:

```text
0–10   → tipo do lanche
10–20  → proteína
20–30  → acompanhamento
30–32  → quantidade
32–40  → bebida
```

### Exemplo:

```text
hamburguercarne     salada    02coca
pastel    frango    bacon     01suco    
```

---

## 💰 Regras de cálculo

* Hambúrguer → R$ 20,00
* Pastel → R$ 15,00
* Outros → R$ 12,00

### 🎯 Desconto:

* 10% de desconto se:

  * tipo = hamburguer
  * proteína = carne
  * acompanhamento = salada

### 📌 Observações:

* Uso de `BigDecimal` para precisão financeira
* Valor final com 2 casas decimais

---

## 🚀 Endpoints

### 📌 Criar Pedido

```http
POST /pedidos
Content-Type: text/plain
```

#### Regras:

* Máximo de 40 caracteres

#### Retorno:

```json
{
  "pedidoID": 1,
  "status": "RECEBIDO",
  ...
}
```

---

### 📌 Buscar por ID

```http
GET /pedidos/{id}
```

---

### 📌 Listar todos

```http
GET /pedidos
```

---

### 📌 Enviar para fila (atualizar status)

```http
PUT /pedidos/{id}
```

👉 Este endpoint:

* NÃO atualiza diretamente o banco
* Envia o `pedidoID` para a fila

---

## 📨 Mensageria com RabbitMQ

### 📌 Configuração

* Exchange: `pedidos.exchange`
* Fila: `pedidos.fila`
* Routing Key: `pedidos.recebidos`

---

### 📤 Producer

Responsável por enviar o `pedidoID` para a fila.

---

### 📥 Consumer

* Escuta a fila `pedidos.fila`
* Ao receber a mensagem:

  * Busca o pedido no banco
  * Atualiza o status para **ENTREGUE**

---

## 🧪 Exemplo de fluxo

1. Criar pedido
2. Listar pedidos
3. Clicar em **Atualizar** no frontend
4. Pedido enviado para a fila
5. Consumer processa
6. Status atualizado para **ENTREGUE**

---

## 🗄️ Banco de dados (H2)

Acesse:

```text
http://localhost:8080/h2-console
```

---

## 📊 RabbitMQ Management

Acesse:

```text
http://localhost:15672
```

Usuário: `guest`
Senha: `guest`

---

## 📁 Estrutura do projeto

```text
controller   → endpoints REST
service      → regras de negócio
entity       → modelo de dados
repository   → acesso ao banco
messaging    → producer e consumer
parser       → interpretação da string (CnabParser)
config       → configuração do RabbitMQ
```

---

## ⚠️ Observações importantes

* O Consumer NÃO deve ser chamado manualmente
* O processamento é assíncrono via fila
* O frontend deve enviar corretamente o ID
* O método Angular precisa usar `.subscribe()`
* O parser interpreta string fixa de 40 caracteres

---

## ✅ Status do projeto

✔ CRUD funcionando
✔ Parser CNAB implementado
✔ Cálculo com BigDecimal
✔ Integração com RabbitMQ
✔ Processamento assíncrono
✔ Atualização automática de status

---

## 👨‍💻 Autor

Fellipe Vieira
