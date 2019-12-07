package controller;

import dao.GameDao;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Game;

@ManagedBean(name = "disponibilidadeBean")
@ViewScoped
@SessionScoped
public class DisponibilidadeController {
    
    private String nomeJogo;
    
    private boolean habilitarConsulta;
    
    private final GameDao gameDao;
    
    public DisponibilidadeController() {
        gameDao = new GameDao();
    }
    
    public String getNomeJogo() {
        return nomeJogo;
    }

    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }
    
    public List<Game> getGames() {
        return gameDao.disponibilidade(nomeJogo);
    }
    
    public boolean habilitarConsulta() {
        return habilitarConsulta;
    }
    
    public void acaoBotaoConsulta() {
        habilitarConsulta = true;
    }
}
