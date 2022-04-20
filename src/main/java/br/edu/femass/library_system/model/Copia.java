package br.edu.femass.library_system.model;

import lombok.Data;

import java.security.InvalidParameterException;
import java.util.UUID;

@Data
public class Copia {
    private String codigo = UUID.randomUUID().toString().substring(0, 6);
    private Boolean fixo;
    private transient Livro livro;
    private transient Emprestimo emprestimo;

    public Copia(Boolean fixo, Livro livro) {
        if (livro == null) throw new InvalidParameterException();
        this.fixo = fixo;
        this.livro = livro;
    }

    public void rmEmprestimo() {
        this.emprestimo = null;
    }

    @Override
    public String toString() {
        return "Copia " + codigo
                + "\n Codigo: " + codigo.substring(0, 8)
                + " - livro: " + livro
                + " - Fixa: " + fixo;
    }
}
