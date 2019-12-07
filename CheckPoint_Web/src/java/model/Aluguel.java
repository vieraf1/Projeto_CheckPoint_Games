package model;

import java.util.Date;

public class Aluguel {

    private Integer idAluguel;
    private Date dataRetirada;
    private Date dataDevolucao;
    private double valorPagar;
    private Date dataEntrega;
    private int diasAtrasados;
    private double valorPago;
    private EnumTipoPagamento tipoPagamento;
    
    private Funcionario funcionario;
    private Cliente cliente;
    private Game game;

    public Aluguel() {
    }

    public Aluguel(Integer idAluguel, Funcionario funcionario, Cliente cliente, Game game, Date dataRetirada, Date dataDevolucao, double valorPagar, Date dataEntrega, int diasAtrasados, double valorPago, EnumTipoPagamento tipoPagamento) {
        this.idAluguel = idAluguel;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.game = game;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorPagar = valorPagar;
        this.dataEntrega = dataEntrega;
        this.diasAtrasados = diasAtrasados;
        this.valorPago = valorPago;
        this.tipoPagamento = tipoPagamento;
    }
        
    public Integer getIdAluguel() {
        return idAluguel;
    }

    public void setIdAluguel(Integer idAluguel) {
        this.idAluguel = idAluguel;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(Date dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public double getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(double valorPagar) {
        this.valorPagar = valorPagar;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public int getDiasAtrasados() {
        return diasAtrasados;
    }

    public void setDiasAtrasados(int diasAtrasados) {
        this.diasAtrasados = diasAtrasados;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public EnumTipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(EnumTipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
