package controller;

import dao.AluguelDao;
import dao.ClienteDao;
import dao.GameDao;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import model.Aluguel;
import model.Cliente;
import model.EnumTipoPagamento;
import model.Funcionario;
import model.Game;

@ManagedBean(name = "aluguelBean")
@ViewScoped
@SessionScoped
public class AluguelController {

    private Game gameBusca;
    private Cliente clienteBusca;
    private String mensagem;
    private List<SelectItem> games;
    private List<SelectItem> clientes;

    private boolean habilitarConsulta;
    private boolean habilitarPainel;
    private boolean aluguelNovo;

    private final AluguelDao aluguelDao;
    private Aluguel aluguelCRUD;

    public AluguelController() {
        mensagem = "";
        aluguelDao = new AluguelDao();
    }

    public Game getGameBusca() {
        return gameBusca;
    }

    public void setGameBusca(Game gameBusca) {
        this.gameBusca = gameBusca;
    }

    public Cliente getClienteBusca() {
        return clienteBusca;
    }

    public void setClienteBusca(Cliente clienteBusca) {
        this.clienteBusca = clienteBusca;
    }

    public List<Aluguel> getAlugueis() {
        Integer idCliente = null;
        String numeroSerie = null;

        if (clienteBusca != null) {
            idCliente = clienteBusca.getIdCliente();
        }

        if (gameBusca != null) {
            numeroSerie = gameBusca.getNumeroSerie();
        }

        return aluguelDao.listarAlugueis(idCliente, numeroSerie);
    }

    public List<SelectItem> getGames() {

        if (games == null) {

            games = new ArrayList<SelectItem>();
            GameDao gameDao = new GameDao();
            List<Game> listaGames = gameDao.listarGames(null, null);

            if (listaGames != null && !listaGames.isEmpty()) {
                SelectItem item;
                for (Game gameLista : listaGames) {
                    item = new SelectItem(gameLista, gameLista.getNumeroSerie() + " | " + gameLista.getNomeTitulo());
                    games.add(item);
                }
            }
        }

        return games;
    }

    public List<SelectItem> getClientes() {
        if (clientes == null) {

            clientes = new ArrayList<SelectItem>();
            ClienteDao clienteDao = new ClienteDao();
            List<Cliente> listaClientes = clienteDao.listarClientes(null, null);

            if (listaClientes != null && !listaClientes.isEmpty()) {
                SelectItem item;
                for (Cliente clienteLista : listaClientes) {
                    item = new SelectItem(clienteLista, clienteLista.getNome() + " " + clienteLista.getSobrenome());
                    clientes.add(item);
                }
            }
        }
        return clientes;
    }

    public boolean habilitarConsulta() {
        return habilitarConsulta;
    }

    public boolean habilitarPainel() {
        return habilitarPainel;
    }

    public void deletarAluguel(Aluguel aluguel) {
        aluguelDao.excluirAluguel(aluguel);

        mensagem = "O Cliente foi Deletado com Sucesso!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", mensagem));
    }

    public Aluguel getAluguelCRUD() {
        return aluguelCRUD;
    }

    public void acaoBotaoConsulta() {
        habilitarConsulta = true;
        habilitarPainel = false;
    }

    public void acaoBotaoSalvar() {
        habilitarConsulta = true;
        habilitarPainel = false;
        prePersist();
    }

    public void acaoBotaoNovo() {
        habilitarConsulta = false;
        habilitarPainel = true;
        aluguelCRUD = new Aluguel();
        aluguelNovo = true;
    }

    public void acaoBotaoAlterar(Aluguel aluguel) {
        aluguelCRUD = aluguel;
        aluguelNovo = false;

        habilitarConsulta = true;
        habilitarPainel = true;
    }

    public void acaoBotaoCancelar() {
        habilitarConsulta = true;
        habilitarPainel = false;
        aluguelCRUD = null;
    }

    public void prePersist() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Funcionario func = (Funcionario) session.getAttribute("funcionario");

        if (aluguelNovo) {
            aluguelCRUD.setFuncionario(func);
            aluguelCRUD.setTipoPagamento(EnumTipoPagamento.Dinheiro);
            
            aluguelCRUD.getGame().setAlugado(true);
            GameDao gameDao = new GameDao();
            gameDao.editarGame(aluguelCRUD.getGame());
            
            Calendar c = Calendar.getInstance();
            c.setTime(aluguelCRUD.getDataRetirada());
            c.add(Calendar.DATE, 8);
            aluguelCRUD.setDataDevolucao(c.getTime());
            
            aluguelCRUD.setValorPagar(aluguelCRUD.getGame().getValorAluguel());
        }
        else {
            LocalDateTime dt1 = aluguelCRUD.getDataDevolucao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime dt2 = aluguelCRUD.getDataEntrega().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            int dias = (int) dt1.until(dt2, ChronoUnit.DAYS);
            aluguelCRUD.setDiasAtrasados(dias);
            
            Double valorFinal = aluguelCRUD.getValorPagar() + ((aluguelCRUD.getValorPagar() * 0.1) * aluguelCRUD.getDiasAtrasados());
            aluguelCRUD.setValorPago(valorFinal);
            
            if(aluguelCRUD.getDataEntrega() != null) {
                aluguelCRUD.getGame().setAlugado(false);
                GameDao gameDao = new GameDao();
                gameDao.editarGame(aluguelCRUD.getGame());
            }
        }

        salvarAlterarAluguel();
    }

    public void salvarAlterarAluguel() {
        if (aluguelNovo) {
            if (validaGame() && validaCliente() && validaFaixaEtaria()) {
                aluguelDao.criarAluguel(aluguelCRUD);

                mensagem = "O Aluguel foi Cadastrado com Sucesso!";
                FacesContext context = FacesContext.getCurrentInstance();

                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", mensagem));
            }
        } else {
                System.out.println("Dias atraso: " + aluguelCRUD.getDiasAtrasados());
                aluguelDao.editarAluguel(aluguelCRUD);

                mensagem = "O Aluguel foi Alterado com Sucesso!";
                FacesContext context = FacesContext.getCurrentInstance();

                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", mensagem));
        }
        aluguelCRUD = null;
    }

    public boolean getAluguelNovo() {
        return aluguelNovo;
    }

    public boolean validaCliente() {
        Cliente clienteValidar = aluguelCRUD.getCliente();
        List<Aluguel> lista = aluguelDao.listarAlugueis(null, null);
        int quantidade = 0;
        
        for(Aluguel aluguel : lista) {
            if (clienteValidar.equals(aluguel.getCliente())) {
                Date hoje;
                Calendar c = Calendar.getInstance();
                hoje = c.getTime();
                
                if (aluguel.getDataEntrega() == null || hoje.before(aluguel.getDataEntrega())) {
                    quantidade += 1;
                }
            }
        }
        
        if(quantidade >= 2) {
            mensagem = "O Cliente " + clienteValidar.toString() + " Não pode alugar mais jogos!";
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));
            return false;
        }
        
        return true;
    }

    public boolean validaGame() {
        Game gameValidar = aluguelCRUD.getGame();
        List<Aluguel> lista = aluguelDao.listarAlugueis(null, null);

        for (Aluguel aluguel : lista) {
            if (gameValidar.equals(aluguel.getGame())) {
                Date hoje;
                Calendar c = Calendar.getInstance();
                hoje = c.getTime();
                
                if (aluguel.getDataEntrega() == null || hoje.before(aluguel.getDataEntrega())) {
                    mensagem = "O Jogo " + gameValidar.toString() + " Não está Disponível!";
                    FacesContext context = FacesContext.getCurrentInstance();

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));
                    return false;
                }
            }
        }

        return true;
    }
    
    public boolean validaFaixaEtaria() {
        if(aluguelCRUD.getCliente().getIdade() < aluguelCRUD.getGame().getFaixaEtaria()) {
            mensagem = "O Cliente " + aluguelCRUD.getCliente().toString() + " Não tem Idade Suficiente para o Jogo " + aluguelCRUD.getGame().getNomeTitulo() + "!";
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));
            return false;
        }
        
        return true;
    }

}
