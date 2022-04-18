package br.edu.femass.library_system.model;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class LivroTest {

    String titulo = "Refactoring";
    Integer ano = 1999;
    Integer edicao = 1;
    Genero genero = Genero.TECNOLOGIA_E_CIENCIA;
    Integer nrCopias = 1;
    Integer nrCopiasFixas = 1;
    Autor autor = new Autor("Kent", "Beck");

    LivroTest() throws Exception{}

    @Test
    void LivroShouldHaveAtLeastOneCopy() throws Exception {
        Livro livro = new Livro(titulo, ano, edicao, autor, genero, 0, 0);

        assertEquals(1, livro.getCopias().size());
        assertEquals(0, livro.getCopiasDisponiveis().size());
    }

    @Test
    void LivroNeedsValidParameters(){
        assertThrows(InvalidParameterException.class, () -> {
            Livro livro = new Livro("", 1999, 1,
                    autor,
                    Genero.TECNOLOGIA_E_CIENCIA, 0, 0);
        });

        assertThrows(InvalidParameterException.class, () -> {
            Livro livro = new Livro("Refactoring", -1, 1,
                    autor,
                    Genero.TECNOLOGIA_E_CIENCIA, 0, 0);
        });

        assertThrows(InvalidParameterException.class, () -> {
            Livro livro = new Livro("Refactoring", 1999, -1,
                    autor,
                    Genero.TECNOLOGIA_E_CIENCIA, 0, 0);
        });


        assertThrows(InvalidParameterException.class, () -> {
            Livro livro = new Livro("Refactoring", 1999, 1,
                    null,
                    Genero.TECNOLOGIA_E_CIENCIA, 0, 0);
        });

        assertThrows(InvalidParameterException.class, () -> {
            Livro livro = new Livro("Refactoring", 1999, 1,
                    null,
                    Genero.TECNOLOGIA_E_CIENCIA, -1, 0);
        });


        assertThrows(InvalidParameterException.class, () -> {
            Livro livro = new Livro("Refactoring", 1999, 1,
                    null,
                    Genero.TECNOLOGIA_E_CIENCIA, 1, -1);
        });
    }

    @Test
    void GetDisponiveisShouldReturnOnlyNotFixed() throws Exception {
        Livro livro = new Livro("Refactoring", 1999, 1,
                new Autor("Kent", "Beck"),
                Genero.TECNOLOGIA_E_CIENCIA, 12, 2);

        assertEquals(12, livro.getCopias().size());
        assertEquals(10, livro.getCopiasDisponiveis().size());

        livro.getCopiasDisponiveis().forEach(i-> {
            assertFalse(i.getFixo());
        });
    }

    @Test
    void LivroShouldNotHaveMoreFixedCopiesThanCopies(){
        Livro livro = new Livro(titulo, ano, edicao, autor, genero, 50, 100);

        assertEquals(livro.getCopias(), livro.getCopiasFixas());
        assertEquals(0, livro.getCopiasDisponiveis().size());
    }
}