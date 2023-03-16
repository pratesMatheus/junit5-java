<h1 align="center">JUnit Java</h1>

Vamos entender o que são testes unitários e sua importância. Vamos usar a linguagem Java.

**NOTA**: Para testes unitários em Java, temos **JUnit**. O projeto de estudo foi feito tendo o maven como gerenciador de projeto! Também vale a observação de que foi usado o **JUnit 5** (versão 5)

## O que são testes unitários

- Também chamado de _testes de unidade_;
- É para testar a menor unidade de código possível;
- Unidade: função, método, classes;
- Testar uma aplicação na sua menor parte;
- Geralmente escrito em tempo de desenvolvimento;

````java
import java.time.LocalDate;

//Exemplo simples para verificar se a idade é maior que 18:
class Pessoa {
    //constructor, atributos e outros métodos

    public boolean ehMaiordeIdade() {
        return idade > 18;
    }
}

class PessoaTeste {
    @Test
    void validarVerificacaoDeMaiorIdade() {
        Pessoa joao = new Pessoa("João", LocalDate.of(2001, 1, 20));
        Assertions.assertTrue(joao.ehMaiordeIdade());
    }
}
````


## Por que escrever testes unitários?

Situações no desenvolvimento de software que mostram a importância de testes unitários:
- Compreender o código fonte;
- Corrigir bugs com segurança;
- Refatorar código sem introduzir bugs;
- Entregar com segurança uma nova feature;

Podemos ainda falar sobre:
- Pirâmide de Testes (inicia com testes unitários, por ser mais barato);
- Testes unitários como métrica de Qualidade (confiabilidade);
- Cobertura de Testes;
- Automação na execução de testes;
- Cultura de agilidade;

## Testes unitários com JUnit

### Assertions: o básico para testar

- Quando se trabalhamos com **JUnit** sempre iniciamosnotação `@Test`,
- e depois criando um método (com retorno `void` + nomeDoMétodo()) e com isso temos nosso método para teste;

````java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
//Importações necessárias acima 🔝
public class PessoaTeste {

    @Test
    void deveCalcularIdadeCorretamente() {
        Pessoa matheus = new Pessoa("Matheus", LocalDateTime.of(2000, 1, 1, 15, 0, 0));
        Assertions.assertEquals(23, matheus.getIdade());
        // é como se estivesse dizendo: espero que a idade de matheus seja 23
    }
    //outros métodos com notação @Test abaixo
}
````
No básico, temos a classe `Assertions` na API do JUnit. É perfeita para iniciar com testes em JUnit, pois trabalha com cenários e executa as validações. 
A classe Assertions trabalha com asserções como o próprio nome diz! Trabalha com:

- assertEquals(): verifica se a "afirmação"/asserção é igual;
- assertTrue(): retorna um boolean;
- assertFalse(): retorna um boolean;
- assertArrayEquals(): verifica se um array é igual ao outro. Percorre e verifica se cada item dentro do array é igual ao outro. Isso também vale para o tamanho do vetor/array;
- assertNUll(): verifica se a "afirmação" é nula;
- assertNotNUll(): verifica se a "afirmação" não é nula;


**Obs.**: é possível ter mais de uma asserção dentro de um mesmo método de teste. O exemplo abaixo mostra como isso se dá:
```java
public class PessoaTeste {
    // outros métodos acima
    @Test
    void deveRetornarSeEhMaiorDeIdade() {
        Pessoa lucas = new Pessoa("Lucas", LocalDateTime.of(2000, 1, 1, 15, 0, 0)); //Criação de cenário
        Assertions.assertTrue(lucas.ehMaiorDeIdade());
        
        Pessoa joao = new Pessoa("João", LocalDateTime.now());
        Assertions.assertFalse(joao.ehMaiorDeIdade());
    }
}
```

**NOTA**: Também é interessante fazer importações estáticas dos métodos de `Assertions` usados. É uma prática muito comum em desenvolvimento de testes. Por exemplo:
```java
import static org.junit.jupiter.api.Assertions.*;
```

### **Notações**: After e Before
Para facilitar todo o entendimento sobre esses dois recursos do JUnit, vamos imaginar que temos uma classe gerenciadora de conexão com o DB (BancoDeDados.java).

**Before** (antes)
- A notação `@BeforeAll` é usada "antes de tudo" como o nome diz. No caso, antes do teste em si (`@Test`), como por exemplo: antes do teste iniciar a conexão com banco de dados para então rodar os testes;
- Já a notação `@BeforeEach` é usada antes do teste começar (`@Test`), como uma forma de fazer uma configuração inicial pré teste, mais precisamente antes de `@AfterEach`. A notação `@BeforeEach` roda antes de cada teste;
**After** (depois)
- A notação `@AfterAll` é usada "depois de tudo" como o nome diz. No caso, antes do teste em si (`@Test`), como por exemplo: antes do teste finalizar a conexão com banco de dados para então rodar os testes;
- Já a notação `@AfterEach` é usada antes do teste começar (`@Test`), como uma forma de fazer uma desmontagem após cada teste, mais precisamente de após `@BeforeEach`. A notação `@AfterEach` roda após de cada teste;

**NOTA**: Tanto `@BeforeEach` quanto `@AfterEach` podem ser usados em chamadas assícronas, como é o caso de um banco de dados!

```JAVA
public class AfterBeforeTeste {

    @BeforeAll
    static void configuraConexao() {
        BancoDeDados.iniciarConexao();
    }

    @BeforeEach
    void insereDadosParaTeste() {
        BancoDeDados.insereDados(new Pessoa("João", LocalDateTime.of(2000, 1, 1, 13, 0, 0)));
    }

    @AfterEach
    void removeDadosDoTeste() {
        BancoDeDados.removeDados(new Pessoa("João", LocalDateTime.of(2000, 1, 1, 13, 0, 0)));
    }

    @Test
    void validarDadosDeRetorno() {
        Assertions.assertTrue(true);
    }

    @Test
    void validarDadosDeRetorno2() {
        Assertions.assertNull(null);
    }

    @AfterAll
    static void finalizarConexao() {
        BancoDeDados.finalizarConexao();
    }
}
```

### Assumptions e testes condicionais

**Assumptions**: Como o próprio nome diz, `Assumptions` se refere a uma suposição de algo, uma hipótese. Em outras palavras, é assumir determinada condição! Sim, é presumir que algo, no teste, só será executado sobre determinada situação. A classe `Assumptions` pertence a API do JUnit e possui os seguintes métodos:

- assumeFalse(): retorna um boolean;
- assumeTrue(): retorna um boolean;
- AssumingThat(): executa algo fornecido, mas somente se a suposição for válida;

**NOTA**: cada um desses métodos possuem variações de sobrescrita;

```JAVA
public class AssumptionsTeste {

    @Test
    void validarAlgoSomenteNoUsuarioMatheus() {
        Assumptions.assumeFalse("root".equals(System.getenv("USER")));
        Assertions.assertEquals(10, 5 + 5);
    }
} 
```

Já quando tratamos de condicionais as possibilidades aumentam e muito.

```JAVA
public class CondicionaisTeste {

    @Test
    @EnabledForJreRange(min = JAVA_11, max = JAVA_17)
    void validarAlgoSomenteNoUsuarioMatheus() {
        Assertions.assertEquals(10, 5 + 5);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "USER", matches = "matheus")
    void validarAlgoSomenteNoUsuarioMatheus1() {
        Assertions.assertEquals(10, 5 + 5);
    }

    @Test
    @DisabledOnOs({OS.LINUX, OS.WINDOWS}) // @DisabledOnOs(OS.LINUX)
    void validarAlgoSomenteNoUsuarioMatheus2() {
        Assertions.assertEquals(10, 5 + 5);
    }
}
```

### Testando Exceptions

Para entender como funciona o teste com exceptions, o único pré-requisito é ter aprendido o conteúdo sobre exceptions e tratamento de erros em Java, e claro, ter conhecimento em Debug acaba ajudando também!

```JAVA
public class ExceptionsTeste {
    @Test
    void validarCenarioDeExcecaoNaTransferencia() {
        Conta contaOrigem = new Conta("123456", 0);
        Conta contaDestino = new Conta("987654", 100);

        TransferenciaEntreContas transferenciaEntreContas = new TransferenciaEntreContas();

        Assertions.assertDoesNotThrow(() -> transferenciaEntreContas.transfere(contaOrigem, contaDestino, 20));
    }
}
```

### Ordenando testes

Sim, é basicamente isso: ordenar a ordem em que os testes serão executados. 
A regra é que: antes de declarar a classe de teste use a seguinte notação:
`@TestMethodOrder(MethodOrderer.<forma de ordernar escolhida>.class)`

Podemos ordenar por: 
- ordem de anotação com `OrderAnnotation.class` e usar a notação `@Order()` e passar a ordem como parâmetro,
  - por exemplo: `@Order(3)` será o 3° teste a ser executado;
- nome do método com `MethodName.class` validar por ordem alfabética baseado no nome do método de teste criado;
- de forma aleatória com `Random.class` e executará aleatoriamente;
- e com `DisplayName.class` que nomeia o teste de alguma forma. É muito usado para indicar o que o método de teste deve fazer,
  - por exemplo: `@DisplayName("Nome que dei ao teste")` 


```JAVA
@TestMethodOrder(MethodOrderer.DisplayName.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EscolhendoAOrdemTeste {

    @DisplayName("Teste que valida se o usuário foi criado")
    //@Order(4)
    @Test
    void validaFluxoA() {
        Assertions.assertTrue(true);
    }

    @DisplayName("B")
    //@Order(1)
    @Test
    void validaFluxoB() {
        Assertions.assertTrue(true);
    }

    @DisplayName("C")
    //@Order(3)
    @Test
    void validaFluxoC() {
        Assertions.assertTrue(true);
    }

    @DisplayName("D")
    //@Order(2)
    @Test
    void validaFluxoD() {
        Assertions.assertTrue(true);
    }
}
```
## Um pouco de história

- Framework open source para criação de testes unitários criado por Erich Gamma e Kent Beck
- Kent Beck (TDD);
- Erich Gamma (Gang of Four, Design Patterns);

## Boas práticas

Aqui vão algumas dicas de boas práticas para usar, não apenas em Java, ou com JUnit 5, mas claro, com qualquer linguagem em qualquer situação!

**Seja simples**
- Preocupe-se com os nomes;
- Preocupe-se com a facilidade de leitura;

**Faça testes desde o início (ou comece cedo!)**
- Procure escrever o código de teste o mais próximo possível do código de execução;
- Testes devem ser determinísticos;
- Veja um pouco sobre TDD;

**Padronização sempre**
- Nomenclaturas;

**Economize seu tempo automatizando**
- Ferramentas de cobertura de código (Jacobo);
- Automatize a execução dos seus testes;

## Referências
- https://imasters.com.br/devsecops/testes-unitarios-qual-a-importancia
- https://dayvsonlima.medium.com/entenda-de-uma-vez-por-todas-o-que-s%C3%A3o-testes-unit%C3%A1rios-para-que-servem-e-como-faz%C3%AA-los-2a6f645bab3
- https://medium.com/cesar-update/aprendendo-a-promover-uma-cultura-de-qualidade-no-desenvolvimento-de-software-%C3%A1gil-f5a3444d88d1
- https://junit.org/junit5/
- https://github.com/junit-team/junit5/
- https://www.baeldung.com/junit-5-gradle
- https://maven.apache.org/surefire/maven-surefire-plugin/examples/junit-platform.html
- https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html
- https://www.tutorialspoint.com/junit/junit_using_assertion.htm
- https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html
- https://www.tutorialspoint.com/junit/junit_using_assertion.htm
- https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall
- https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assumptions.html
- https://www.baeldung.com/junit-5#2-assumptions
- https://mkyong.com/junit5/junit-5-assumptions-examples/
- https://junit.org/junit5/docs/current/user-guide/#writing-tests-conditional-execution
- https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-execution-order
- https://code.visualstudio.com/docs/java/java-testing
- https://wiki.eclipse.org/Eclipse/Testing
- https://nglauber.medium.com/junit-no-eclipse-ebd134fcf6d4
- https://edisciplinas.usp.br/pluginfile.php/5768433/mod_resource/content/0/Utilizando%20JUnit%20no%20Eclipse.pdf
- https://www.jetbrains.com/help/idea/tests-in-ide.html
- https://www.testim.io/blog/unit-testing-best-practices/
- https://devporai.com.br/5-dicas-para-escrever-bons-testes-unitarios/
- https://jeziellago.medium.com/testes-boas-pr%C3%A1ticas-e-patterns-6bfe0925040

## **Obrigado**

Disponibilizado com 💖 por [pratesMatheus](https://github.com/pratesMatheus "pratesMatheus").
