package util;

import dao.ClienteDao;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Cliente;

@FacesConverter(forClass = Cliente.class)
public class ClienteConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) { 
        if(value != null && value.trim().length() > 0) {
            Integer id = Integer.parseInt(value);
            
            ClienteDao clienteDao = new ClienteDao();
            return clienteDao.buscarCliente(id);
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object clienteObjeto) {
        if (clienteObjeto != null) {
            Cliente cliente = (Cliente) clienteObjeto;
            return cliente.getIdCliente().toString();
        } 
        return null;
    }
}
