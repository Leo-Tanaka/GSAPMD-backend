# 🛰️ Space Mission Control - API Rest de Telemetria

##  Descrição do Projeto
Este repositório contém a API Rest responsável pelo processamento, armazenamento e gerenciamento de dados de missões espaciais. O sistema gerencia os módulos físicos da nave, registra eventos operacionais de rotina enviados por sensores e gera alertas críticos isolados automaticamente em caso de anomalias no sistema.

A arquitetura foi projetada com foco em alta coesão e separação de responsabilidades utilizando o ecossistema Spring.

---

##  Integrantes da Equipe
* **Enzo Raddatz** - RM: 556312
* **Francisco Ferrara Neto** - RM: 557209
* **Leonardo Tanaka Cortez** - RM: 556781

---

##  Tecnologias Utilizadas
* **Linguagem Principal:** Java 17
* **Framework:** Spring Boot 3.5.14
* **Persistência:** Spring Data JPA
* **Banco de Dados:** H2 Database configurado em Modo Arquivo (`file`)
* **Produtividade:** Project Lombok

---

###  Endpoints da API

A API segue os padrões RESTful, utilizando os verbos HTTP correspondentes para cada operação logística do centro de controle.

| Método | Rota | Descrição | Parâmetros / Corpo | Status de Sucesso |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/api/telemetry` | Recupera o histórico de telemetria de forma paginada e ordenada da mais recente para a mais antiga. | `page` (número da página, padrão: 0)<br>`size` (quantidade por página, padrão: 20) | `200 OK` |
| **POST** | `/api/telemetry` | Recebe a telemetria, vincula ou cria o módulo correspondente e gera alertas se o status for `CRITICO`. | Corpo em JSON estruturado via `TelemetryDTO`. | `201 Created` |
| **DELETE**| `/api/telemetry/{id}` | Remove permanentemente um registro de evento do banco de dados pelo seu ID numérico. | `id` enviado diretamente como variável na URL. | `204 No Content` |

#### 📋 Exemplo de Payload para o POST
```json
{
  "moduleName": "Propulsão Principal",
  "sensorType": "Temperatura da Câmara",
  "readingValue": 1250.8,
  "status": "ALERTA"
}
```

####  Exemplo de Retorno do GET (Estrutura Paginada do Spring)
```json
{
  "content": [
    {
      "id": 1,
      "module": {
        "id": 1,
        "name": "Propulsão Principal"
      },
      "sensorType": "Temperatura da Câmara",
      "readingValue": 1250.8,
      "status": "ALERTA",
      "timestamp": "2026-06-06T18:00:00"
    }
  ],
  "pageable": { ... },
  "totalElements": 1,
  "totalPages": 1,
  "size": 20,
  "number": 0
}
```

---

### Como Executar o Backend

Siga as instruções abaixo para compilar e rodar o servidor de controle de missão na sua máquina local.

#### 1. Pré-requisitos
Antes de iniciar, certifique-se de ter instalado em sua máquina:
* **Java Development Kit (JDK) 17** ou superior.
* Variáveis de ambiente `JAVA_HOME` devidamente configuradas.
* Uma IDE com suporte a projetos Maven (Ex: IntelliJ IDEA, Eclipse ou VS Code com a extensão *Spring Boot Extension Pack*).

#### 2. Configuração do Banco de Dados
A aplicação utiliza o banco de dados **H2 em modo arquivo**, eliminando a necessidade de instalar servidores de banco de dados externos (como MySQL ou Oracle) para a avaliação. 
* Os dados serão salvos automaticamente na pasta `./data/` na raiz do projeto.
* Se precisar resetar o banco de dados por completo, basta parar a aplicação e deletar a pasta `./data/`.

#### 3. Inicialização via Terminal
Abra o terminal na pasta raiz do projeto backend (onde se encontra o arquivo `pom.xml`) e execute o comando do Maven Wrapper:

```bash
# No Linux/macOS:
./mvnw spring-boot:run

# No Windows (Prompt de Comando / PowerShell):
mvnw.cmd spring-boot:run
```

O servidor iniciará e estará pronto para receber conexões na porta **8080** (`http://localhost:8080`).

#### 4. Acesso ao Console do Banco de Dados
Para inspecionar as tabelas (`tb_modules`, `tb_operational_events`, `tb_critical_alerts`) e validar a persistência física dos dados:
1. Abra o navegador e acesse: `http://localhost:8080/h2-console`
2. Certifique-se de preencher os campos exatamente como abaixo:
   * **Driver Class:** `org.h2.Driver`
   * **JDBC URL:** `jdbc:h2:file:./data/mission_control_db`
   * **User Name:** `sa`
   * **Password:** *(deixe o campo completamente em branco)*
3. Clique em **Connect**.