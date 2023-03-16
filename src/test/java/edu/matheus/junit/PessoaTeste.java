package edu.matheus.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

/*
* Dada a classe Pessoa,
* vamos verificar se os métodos internos da mesma funcionam
* */
public class PessoaTeste {

    @Test
    void deveCalcularIdadeCorretamente() {
        Pessoa matheus = new Pessoa("Matheus", LocalDateTime.of(2000, 1, 1, 15, 0, 0));
        Assertions.assertEquals(23, matheus.getIdade());
    } //Testando a idade de uma pessoa

    @Test //Anotações também são essencias em Testes
    void deveRetornarSeEhMaiorDeIdade() {
        Pessoa lucas = new Pessoa("Lucas", LocalDateTime.of(2000, 1, 1, 15, 0, 0)); //Criação de cenário
        Assertions.assertTrue(lucas.ehMaiorDeIdade()); //Execução das validações

        Pessoa joao = new Pessoa("João", LocalDateTime.now());
        Assertions.assertFalse(joao.ehMaiorDeIdade());
    }

    @Test
    void validaIgualdade() {
        Pessoa pessoa = new Pessoa("Pessoa 1", LocalDateTime.now());
        Assertions.assertSame(pessoa, pessoa);
    }
}
