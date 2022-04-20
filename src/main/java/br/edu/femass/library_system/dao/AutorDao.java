package br.edu.femass.library_system.dao;

import br.edu.femass.library_system.model.Autor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AutorDao implements Dao<Autor> {
    private static List<Autor> autores = new ArrayList<>();

    @Override
    public void create(Autor objeto) throws Exception {
        autores.add(objeto);
    }

    @Override
    public List<Autor> read() throws Exception {
        return autores;
    }

    @Override
    public void delete(Autor objeto) throws Exception {

    }

    @Override
    public void update(Integer index, Autor objeto) throws Exception {
        autores.set(index, objeto);
    }
}
