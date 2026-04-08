# Sistema de Gestão de Benefícios

## Visão Geral

Gestão de benefícios desenvolvido com arquitetura em camadas: 
- Utilizado **EJB**, **Spring Boot** e **Angular**.
- O projeto implementa operações CRUD completas e uma funcionalidade crítica de transferência. 
- Correção de bugs importantes de concorrência e validação.

## Arquitetura

### **Camadas da Aplicação**
```
 - Frontend (Angular)
 - Backend (Spring Boot)
 - EJB (Business Logic)
 - Database (H2)
```

### **Módulos do Projeto**
- `ejb-module/` - Lógica de negócio com EJB
- `backend-module/` - API REST com Spring Boot
- `frontend/` - Interface web com Angular


## Como Executar a Aplicação

### **Pré-requisitos**
- **Java 8+**
- **WildFly 39+**
- **Maven 3.6+**
- **Node.js 25+**
- **npm**

#### **Baixar o Wildfly (versao 39 +**
 - Instalar e inicialiar o Wildfly de forma padrão

#### **Subindo o Modulo EJB**
```bash
cd ejb-module
mvn clean install -pl ejb-module && cp ejb-module/target/ejb-module-1.0-SNAPSHOT.jar {PASTA_DO_WILDFLY}/standalone/deployments/
```

#### **Executar Backend com String Boot**
```bash
cd backend-module
mvn spring-boot:run
```

 - A aplicação estará disponível em: **http://localhost:8081** 
 - URL do Swagger em : **http://localhost:8081/swagger-ui/index.html**


### **Executar Todos os Testes**
```bash
cd ejb-module && mvn test
cd backend-module && mvn test
```

## API Endpoints
| Método | Endpoint | Descrição                         |
|--------|----------|-----------------------------------|
| `GET` | `/api/v1/beneficios` | Listar todos os benefícios|
| `GET` | `/api/v1/beneficios/{id}` | Buscar benefício por ID|
| `POST` | `/api/v1/beneficios` | Criar novo benefício|
| `PUT` | `/api/v1/beneficios/{id}` | Atualizar benefício|
| `DELETE` | `/api/v1/beneficios/{id}` | Excluir benefício|
| `POST` | `/api/v1/beneficios/transfer` | Transferir valor entre benefícios|

#### **Frontend**
```bash
cd frontend
npm install
npm start
```

Frontend disponível em: **http://localhost:4200**

## Tecnologias Usadas

### **Backend**
- **Java 17**
- **Jakarta EJB 4.0.1**
- **Spring Boot 4.0.0**
- **H2 Database** - Banco em memória
- **Maven** - Gerenciamento de dependências

### **Frontend**
- **Angular**
- **TypeScript**
- **RxJS**

<br>

---
> Documento de Requisitos
---
# 🏗️ Desafio Fullstack Integrado
🚨 Instrução Importante (LEIA ANTES DE COMEÇAR)
❌ NÃO faça fork deste repositório.

Este repositório é fornecido como modelo/base. Para realizar o desafio, você deve:
✅ Opção correta (obrigatória)
  Clique em “Use this template” (se este repositório estiver marcado como Template)
OU
  Clone este repositório e crie um NOVO repositório público em sua conta GitHub.
📌 O resultado deve ser um repositório próprio, independente deste.

## 🎯 Objetivo
Criar solução completa em camadas (DB, EJB, Backend, Frontend), corrigindo bug em EJB e entregando aplicação funcional.

## 📦 Estrutura
- db/: scripts schema e seed
- ejb-module/: serviço EJB com bug a ser corrigido
- backend-module/: backend Java 8+
- frontend/: app Angular
- docs/: instruções e critérios
- .github/workflows/: CI

## ✅ Tarefas do candidato
1. Executar db/schema.sql e db/seed.sql
2. Corrigir bug no BeneficioEjbService
3. Implementar backend CRUD + integração com EJB
4. Desenvolver frontend Angular consumindo backend
5. Implementar testes
6. Documentar (Swagger, README)
7. Enviar link para recrutadora com seu repositório para análise

## 🐞 Bug no EJB
- Transferência não verifica saldo, não usa locking, pode gerar inconsistência
- Espera-se correção com validações, rollback, locking/optimistic locking

## 📊 Critérios de avaliação
- Arquitetura em camadas (20%)
- Correção EJB (20%)
- CRUD + Transferência (15%)
- Qualidade de código (10%)
- Testes (15%)
- Documentação (10%)
- Frontend (10%)
