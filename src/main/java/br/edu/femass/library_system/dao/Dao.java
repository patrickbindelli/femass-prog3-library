package br.edu.femass.library_system.dao;

import br.edu.femass.library_system.model.Autor;

import java.util.List;

public interface Dao<T> {
    public void create(T objeto) throws Exception;
    public List<T> read() throws Exception;
    public void delete(T objeto) throws Exception;
    public void update(Integer index, T objeto) throws Exception;

}
