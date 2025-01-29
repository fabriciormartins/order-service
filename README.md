# Serviço de Pedidos (Order Service)

Este serviço é responsável por receber solicitações de criação 
de pedidos e atendê-los em menor tempo possível, realizando o cálculo do seu total e sensibilizando 
o serviço de pagamento (checkout).

## 🚀 Começando

O projeto foi desenvolvido com Java na versão 21 e Spring Boot na versão 3.4.2, 
utilizando o [Maven](https://maven.apache.org/) como gerenciador de dependências. A persistência é feita utilizando
o [MongoDB](https://www.mongodb.com/pt-br/) para maior escalabilidade e resiliência. Internamente, faz se uso de eventos
do Spring ([Application Events](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/application-events.html))
para disparo de procedimentos internos.

### 📋 Pré-requisitos

Para executá-lo localmente precisará do [Docker](https://docs.docker.com/) em sua versão mais recente.
Você poderá ir até a pasta na qual realizou o checkout e exectuar o seguinte comando:
```
docker compose up
```
ou caso prefira não visualizar os logs de execução, pode seguir este outro comando:
```
docker compose up -d
```
## ⚙️ Executando os testes unitários

Os testes unitários foram escritos utilizando [Mockito](https://site.mockito.org/) 
e [JUnit](https://junit.org/junit5/) e para execução dos mesmos, poderá ir até a pasta na 
qual realizou o checkout e executar o seguinte comando:
```
mvn test
```

## ⚙️ Executando os testes de carga

Os testes de carga foram escritos utilizando [Gatling](https://gatling.io/), 
e poderá ser executado apenas com a aplicação em execução utilizando o comando:

```
mvn gatling:test
```

---
⌨️ com ❤️ por [Fabrício Rocha](https://github.com/fabriciormartins/) 😊