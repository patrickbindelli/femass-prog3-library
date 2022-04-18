package br.edu.femass.library_system.dao;

import br.edu.femass.library_system.model.Emprestimo;
import br.edu.femass.library_system.model.Livro;
import br.edu.femass.library_system.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EmprestimoDao implements Dao<Emprestimo> {

    private static List<Emprestimo> emprestimos = new ArrayList<>();

    @Override
    public void create(Emprestimo objeto) throws Exception {
        emprestimos.add(objeto);
        objeto.getCopia().setEmprestimo(objeto);
    }

    @Override
    public List<Emprestimo> read() throws Exception {
        return emprestimos;
    }

    public List<Emprestimo> getEmprestimosByLivro(Livro livro) throws Exception{
        List<Emprestimo> emprestimoList = new ArrayList<>();
        for(Emprestimo emprestimo : emprestimos){
            if(emprestimo.getCopia().getLivro() == livro) emprestimoList.add(emprestimo);
        }
        return emprestimoList;
    }

    public List<Emprestimo> getEmprestimosByUser(Usuario usuario) throws Exception {
        List<Emprestimo> emprestimoList = new ArrayList<>();
        for(Emprestimo emprestimo : emprestimos){
            if(emprestimo.getUsuario() == usuario) emprestimoList.add(emprestimo);
        }
        return emprestimoList;
    }

    @Override
    public void delete(Emprestimo objeto) throws Exception {
        emprestimos.remove(objeto);
    }

    @Override
    public void update(Integer index, Emprestimo objeto) throws Exception {

    }
}
