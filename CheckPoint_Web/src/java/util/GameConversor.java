package util;

import dao.GameDao;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Game;

@FacesConverter(forClass = Game.class)
public class GameConversor implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String numeroSerie) {
        if(numeroSerie != null && numeroSerie.trim().length() > 0) {
            GameDao gameDao = new GameDao();
            return gameDao.buscarGame(numeroSerie);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object gameObjeto) {
        if(gameObjeto != null) {
            Game game = (Game) gameObjeto;
            return game.getNumeroSerie();
        }
        
        return null;
    }
    
}
