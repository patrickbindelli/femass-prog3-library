package br.edu.femass.library_system.dao;

import br.edu.femass.library_system.model.Copia;

import java.util.ArrayList;
import java.util.List;

public class CopiaDao implements Dao<Copia>{

    public static List<Copia> copias = new ArrayList<>();

    @Override
    public void create(Copia objeto) throws Exception {
        copias.add(objeto);
    }

    @Override
    public List<Copia> read() throws Exception {
        return copias;
    }

    @Override
    public void delete(Copia objeto) throws Exception {
        copias.remove(objeto);
    }

    @Override
    public void update(Integer index, Copia objeto) throws Exception {

    }
}
