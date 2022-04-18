package br.edu.femass.library_system.model;

import lombok.Data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Data
public class Livro {
    private String titulo;
    private Integer ano;
    private Integer edicao;
    private Autor autor;
    private Genero genero;
    private List<Copia> copias = new ArrayList<>();

    public Livro(String titulo, Integer ano, Integer edicao, Autor autor, Genero genero, int nrCopias, int nrCopiasFixas) {

        if(titulo == null || titulo.isBlank()
                || ano == null || ano < 1
                || edicao == null || edicao < 1
                || autor == null || genero == null
        ) throw new InvalidParameterException();

        this.titulo = titulo;
        this.ano = ano;
        this.edicao = edicao;
        this.autor = autor;
        this.genero = genero;

        int finalNrCopiasFixas = Math.min(Math.max(nrCopiasFixas, 1), nrCopias);
        IntStream.rangeClosed(1, Math.max(nrCopias, 1))
                .forEach(i -> this.addCopia(new Copia(i <= (Math.max(finalNrCopiasFixas, 1)), this)));
    }

    public void addCopia(Copia copia){
        copias.add(copia);
    }

    public List<Copia> getCopiasDisponiveis(){
        List<Copia> disp = new ArrayList<>();
        for(Copia copia : this.copias){
            if(!copia.getFixo() && copia.getEmprestimo() == null) disp.add(copia);
        }
        return disp;
    }

    public List<Copia> getCopiasFixas() {
        List<Copia> fixas = new ArrayList<>();
        for(Copia copia : this.copias){
            if (copia.getFixo()) fixas.add(copia);
        }
        return fixas;
    }

    @Override
    public String toString() {
        return titulo + " (" + ano + ") - " + autor;
    }
}
