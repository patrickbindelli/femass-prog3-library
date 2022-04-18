package br.edu.femass.library_system.model;

public class Aluno extends Usuario{
    private String curso;

    public Aluno(String nome, String cpf, String matricula, String curso){
        super(nome, cpf, matricula, 5);
        this.curso = curso;
    }
}
