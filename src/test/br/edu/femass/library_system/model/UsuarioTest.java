package br.edu.femass.library_system.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void UsuarioCanHaveUpTo5EmprestimosAtivos() throws Exception {
        Usuario usuario = new Aluno("Alberto", "Alves", "4531321546", "Sistemas de Informação");
        Usuario usuario1 = new Professor("Roberto", "Alberto", "23456324", "Matematica");
        Livro livro = new Livro("Refactoring", 1999, 1, new Autor("Kent", "Beck"), Genero.TECNOLOGIA_E_CIENCIA, 1, 1);

        assertDoesNotThrow(() -> {
            for(int i = 0; i < 5; i++){
                usuario.addEmprestimo(new Emprestimo(usuario, new Copia(false, livro)));
            }
        });

        assertDoesNotThrow(() -> {
            for(int i = 0; i < 5; i++){
                usuario1.addEmprestimo(new Emprestimo(usuario, new Copia(false, livro)));
            }
        });


        assertThrows(Exception.class, () -> {
           for(int i = 0; i < 6; i++){
               usuario.addEmprestimo(new Emprestimo(usuario, new Copia(false, livro)));
           }
        });

        assertThrows(Exception.class, () -> {
            for(int i = 0; i < 6; i++){
                usuario1.addEmprestimo(new Emprestimo(usuario, new Copia(false, livro)));
            }
        });
    }

}