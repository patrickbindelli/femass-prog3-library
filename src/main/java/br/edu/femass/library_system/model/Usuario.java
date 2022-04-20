package br.edu.femass.library_system.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Usuario {

    protected String nome;
    protected String cpf;
    protected String matricula;
    protected Integer prazoEntrega;
    protected transient List<Emprestimo> emprestimos = new ArrayList<>();

    public Usuario(String nome, String cpf, String matricula, Integer prazoEntrega) {
        this.nome = nome;
        this.cpf = cpf;
        this.matricula = matricula;
        this.prazoEntrega = prazoEntrega;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void addEmprestimo(Emprestimo emprestimo) throws Exception{
        List<Emprestimo> emprestimosAtivos = new ArrayList<>();
        this.emprestimos.forEach(i -> {
            if (i.getDataDevolucao() == null) emprestimosAtivos.add(i);
        });
        if(emprestimosAtivos.size() == 5) throw new Exception("Limite de empréstimos atingidos para esse usuario");
        this.emprestimos.add(emprestimo);
    }

    @Override
    public String toString() {
        return nome + " (" + this.getClass().getSimpleName() + ")";
    }
}
