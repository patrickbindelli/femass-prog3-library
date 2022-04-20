package br.edu.femass.library_system.model;

public class Professor extends Usuario{
    private String departamento;

    public Professor(String nome, String cpf, String matricula, String departamento) {
        super(nome, cpf, matricula, 5);
        this.departamento = departamento;
    }


    @Override
    public String toString() {
        return super.toString() + " - " + this.departamento;
    }
}
