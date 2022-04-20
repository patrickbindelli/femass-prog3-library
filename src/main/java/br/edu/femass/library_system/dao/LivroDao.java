package br.edu.femass.library_system.dao;

import br.edu.femass.library_system.model.Autor;
import br.edu.femass.library_system.model.Emprestimo;
import br.edu.femass.library_system.model.Livro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LivroDao implements Dao<Livro>{

    private static ArrayList<Livro> livros = new ArrayList<>();


    @Override
    public void create(Livro objeto) throws Exception {
        livros.add(objeto);
    }

    @Override
    public List<Livro> read() throws Exception {
        return livros;
    }

    @Override
    public void delete(Livro objeto) throws Exception {
        livros.remove(objeto);
    }

    @Override
    public void update(Integer index, Livro objeto) throws Exception {
    }
}
