package br.edu.femass.library_system.dao;

import br.edu.femass.library_system.adapters.UsuarioAdapter;
import br.edu.femass.library_system.model.Livro;
import br.edu.femass.library_system.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao implements Dao<Usuario> {

    private static List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void create(Usuario objeto) throws Exception {
        usuarios.add(objeto);
    }

    @Override
    public List<Usuario> read() throws Exception {
        return usuarios;
    }

    @Override
    public void delete(Usuario objeto) throws Exception {
        usuarios.remove(objeto);
    }

    @Override
    public void update(Integer index, Usuario objeto) throws Exception {
        usuarios.set(index, objeto);
    }
}
