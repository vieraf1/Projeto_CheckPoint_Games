package model;

import java.util.Date;
import java.util.Objects;

public class Game {

    private String numeroSerie;
    private String nomeTitulo;
    private float valorAluguel;
    private Date dataLancamento;
    private int faixaEtaria;
    private boolean alugado;
    private EnumGenero genero;
    private EnumConsole console;
    private EnumEstudio estudio;
    
    private Funcionario funcionario;

    public Game() {
    }

    public Game(String numeroSerie, Funcionario funcionario, String nomeTitulo, float valorAluguel, Date dataLancamento, int faixaEtaria, EnumGenero genero, EnumConsole console, EnumEstudio estudio, boolean alugado) {
        this.numeroSerie = numeroSerie;
        this.funcionario = funcionario;
        this.nomeTitulo = nomeTitulo;
        this.valorAluguel = valorAluguel;
        this.dataLancamento = dataLancamento;
        this.faixaEtaria = faixaEtaria;
        this.genero = genero;
        this.console = console;
        this.estudio = estudio;
        this.alugado = alugado;
    }
    
    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNomeTitulo() {
        return nomeTitulo;
    }

    public void setNomeTitulo(String nomeTitulo) {
        this.nomeTitulo = nomeTitulo;
    }

    public float getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(float valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public int getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(int faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public boolean isAlugado() {
        return alugado;
    }

    public void setAlugado(boolean alugado) {
        this.alugado = alugado;
    }

    public EnumGenero getGenero() {
        return genero;
    }

    public void setGenero(EnumGenero genero) {
        this.genero = genero;
    }

    public EnumConsole getConsole() {
        return console;
    }

    public void setConsole(EnumConsole console) {
        this.console = console;
    }

    public EnumEstudio getEstudio() {
        return estudio;
    }

    public void setEstudio(EnumEstudio estudio) {
        this.estudio = estudio;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.numeroSerie);
        hash = 83 * hash + Objects.hashCode(this.nomeTitulo);
        hash = 83 * hash + Float.floatToIntBits(this.valorAluguel);
        hash = 83 * hash + Objects.hashCode(this.dataLancamento);
        hash = 83 * hash + this.faixaEtaria;
        hash = 83 * hash + (this.alugado ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.genero);
        hash = 83 * hash + Objects.hashCode(this.console);
        hash = 83 * hash + Objects.hashCode(this.estudio);
        hash = 83 * hash + Objects.hashCode(this.funcionario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (!Objects.equals(this.numeroSerie, other.numeroSerie)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return numeroSerie + " | " + nomeTitulo;
    }

    
}