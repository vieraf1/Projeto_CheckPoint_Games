package controller;

import dao.GameDao;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Funcionario;
import model.Game;

@ManagedBean(name = "gameBean")
@ViewScoped
@SessionScoped
public class GameController {
    
    private String nomeJogo;
    private String numeroSerie;
    private String mensagem;
    
    private boolean habilitarConsulta;
    private boolean habilitarPainel;
    private boolean gameNovo;
    private String alugadoPanel;
    
    private final GameDao gameDao;
    private Game gameCRUD;
    
    public GameController() {
        mensagem = "";
        gameDao = new GameDao();
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getAlugadoPanel() {
        return alugadoPanel;
    }

    public void setAlugadoPanel(String alugadoPanel) {
        this.alugadoPanel = alugadoPanel;
    }
    
    public List<Game> getGames() {
        return gameDao.listarGames(numeroSerie, nomeJogo);
    }

    public boolean habilitarConsulta() {
        return habilitarConsulta;
    }

    public boolean habilitarPainel() {
        return habilitarPainel;
    }

    public void deletarGame(Game game) {
        gameDao.excluirGame(game);
        
        mensagem = "O Jogo foi Deletado com Sucesso!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", mensagem));
    }
    
    public String getAlugado(boolean alugado) {
        if(alugado) {
            return "Sim";
        } else {
            return "Não";   
        }
    }
    
    public Game getGameCRUD() {
        return gameCRUD;
    }
    
    public void painelEditar(Game game) {
        gameCRUD = game;
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
        gameCRUD = new Game();
        gameNovo = true;
    }
    
    public void acaoBotaoAlterar(Game game) {
        gameCRUD = game;
        gameNovo = false;
        
        if(gameCRUD.isAlugado()) {
            alugadoPanel = "Sim";
        }
        else {
            alugadoPanel = "Não";
        }
        
        habilitarConsulta = true;
        habilitarPainel = true;
    }
    
    public void acaoBotaoCancelar() {
        habilitarConsulta = true;
        habilitarPainel = false;
        gameCRUD = null;
        alugadoPanel = null;
    }
    
    public void prePersist() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
        Funcionario func = (Funcionario) session.getAttribute("funcionario");
        
        if(gameNovo) {
            gameCRUD.setFuncionario(func);
        }
        
        if(alugadoPanel.equals("Sim")) {
            gameCRUD.setAlugado(true);
        }
        else {
            gameCRUD.setAlugado(false);
        }
        salvarAlterarGame();
    }
    
    public void salvarAlterarGame() {
        if(gameNovo) {
            gameDao.criarGame(gameCRUD);
            
            mensagem = "O Jogo foi Cadastrado com Sucesso!";
            FacesContext context = FacesContext.getCurrentInstance();
            
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!",mensagem));
        }
        else {
            gameDao.editarGame(gameCRUD);
            
            mensagem = "O Jogo foi Alterado com Sucesso!";
            FacesContext context = FacesContext.getCurrentInstance();
         
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!",mensagem));
        }
        gameCRUD = null;
    }
}