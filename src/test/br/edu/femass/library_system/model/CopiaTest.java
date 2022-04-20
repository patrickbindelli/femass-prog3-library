package br.edu.femass.library_system.model;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class CopiaTest {
    String titulo = "Refactoring";
    Integer ano = 1999;
    Integer edicao = 1;
    Genero genero = Genero.TECNOLOGIA_E_CIENCIA;
    Integer nrCopias = 1;
    Integer nrCopiasFixas = 1;
    Autor autor = new Autor("Kent", "Beck");
    Livro livro = new Livro(titulo, ano, edicao, autor, genero, nrCopias, nrCopiasFixas);
    CopiaTest() throws Exception {}
    @Test
    void CopiaCannotExistWithoutLivro(){
        assertThrows(InvalidParameterException.class, () -> {
            Copia copia = new Copia(true, null);
        });
    }
}