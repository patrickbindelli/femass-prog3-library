package br.edu.femass.library_system.model;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class AutorTest {

    @Test
    void AutorShouldNotHaveEmptyName(){
        assertThrows(InvalidParameterException.class, () -> {
           Autor autor = new Autor("", "Silva");
        });

        assertThrows(InvalidParameterException.class, () -> {
            Autor autor = new Autor(null, "Silva");
        });

        assertThrows(InvalidParameterException.class, () -> {
            Autor autor = new Autor("          ", "Silva");
        });
    }

    @Test
    void AutorShouldNotHaveEmptySurname(){
        assertThrows(InvalidParameterException.class, () -> {
            Autor autor = new Autor("Pedro", "");
        });

        assertThrows(InvalidParameterException.class, () -> {
            Autor autor = new Autor("Pedro", null);
        });

        assertThrows(InvalidParameterException.class, () -> {
            Autor autor = new Autor("Pedro", "                ");
        });
    }

}