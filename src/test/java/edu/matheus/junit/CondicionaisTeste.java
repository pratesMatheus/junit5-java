package edu.matheus.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.junit.jupiter.api.condition.JRE.JAVA_11;
import static org.junit.jupiter.api.condition.JRE.JAVA_17;

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
