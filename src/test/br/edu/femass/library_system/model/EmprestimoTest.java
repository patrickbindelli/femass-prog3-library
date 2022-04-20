package br.edu.femass.library_system.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoTest {


    @Test
    void LivroShouldBeReturned() throws Exception {
        Usuario usuario = new Aluno("Alberto", "Alves", "4531321546", "Sistemas de Informação");
        Livro livro = new Livro("Refactoring", 1999, 1, new Autor("Kent", "Beck"), Genero.TECNOLOGIA_E_CIENCIA, 1, 1);
        Emprestimo emprestimo = new Emprestimo(usuario, new Copia(false, livro));

        assertDoesNotThrow(() -> {
            emprestimo.setDataDevolucao(new Date());
        });

        assertNotNull(emprestimo.getDataDevolucao());
    }

    @Test
    void LivroShouldNotBeReturnedTwice() throws Exception {
        Usuario usuario = new Aluno("Alberto", "Alves", "4531321546", "Sistemas de Informação");
        Livro livro = new Livro("Refactoring", 1999, 1, new Autor("Kent", "Beck"), Genero.TECNOLOGIA_E_CIENCIA, 1, 1);
        assertThrows(Exception.class, () -> {
            Emprestimo emprestimo = new Emprestimo(usuario, new Copia(false, livro));

            emprestimo.setDataDevolucao(new Date());
            emprestimo.setDataDevolucao(new Date());
        });
    }

}