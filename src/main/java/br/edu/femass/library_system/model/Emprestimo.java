package br.edu.femass.library_system.model;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
public class Emprestimo {
    private final Date dataEmprestimo = new Date();
    private Date dataDevolucao;
    private final Usuario usuario;
    private final Copia copia;

    public Emprestimo(Usuario usuario, Copia copia){
        this.usuario = usuario;
        this.copia = copia;
        copia.setEmprestimo(this);
    }

    public String getDataEmprestimo() {
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        return dtf.format(dataEmprestimo);
    }

    public String getDataDevolucao() {
        if(dataDevolucao == null) return null;
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        return dtf.format(dataDevolucao);
    }

    public void setDataDevolucao(Date dataDevolucao) throws Exception{
        if(this.dataDevolucao != null) throw new Exception("Livro já devolvido");
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {

        long diff = (this.dataDevolucao == null ? new Date().getTime() : this.dataDevolucao.getTime()) - this.dataEmprestimo.getTime();
        TimeUnit time = TimeUnit.DAYS;
        long direfenca = time.convert(diff, TimeUnit.MILLISECONDS);

        System.out.println(direfenca);

        return  copia.getCodigo()
                + "\t" + getDataEmprestimo()
                + "\t" + (dataDevolucao == null ? "" : getDataDevolucao())
                + " " + (direfenca > this.getUsuario().getPrazoEntrega() ? "(Atraso)" : "");
    }
}
