package br.edu.femass.library_system.model;

import lombok.Data;

import java.security.InvalidParameterException;

@Data
public class Autor {
    private String nome;
    private String sobrenome;

    public Autor(String nome, String sobrenome)throws InvalidParameterException {
        if(nome == null || nome.isBlank()) throw new InvalidParameterException("Autor não pode ter nome vazio");
        if(sobrenome == null || sobrenome.isBlank()) throw new InvalidParameterException("Autor não pode ter sobrenome vazio");
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    @Override
    public String toString() {
        return nome + " " + sobrenome;
    }
}
