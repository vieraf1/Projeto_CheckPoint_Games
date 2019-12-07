package controller;

import dao.FuncionarioDao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Funcionario;

@ManagedBean(name = "alterarSenhaBean")
@RequestScoped
@SessionScoped
public class AlterarSenhaController {
    
    private String senhaAntiga;
    private String senhaAntigaConfirmar;
    private String senhaNova;
    private String senhaNovaConfirmar;
    private String mensagem;
    
    FuncionarioDao funcionarioDao;
    
    public AlterarSenhaController() {
        mensagem = "";
        funcionarioDao = new FuncionarioDao();
    }

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public String getSenhaAntigaConfirmar() {
        return senhaAntigaConfirmar;
    }

    public void setSenhaAntigaConfirmar(String senhaAntigaConfirmar) {
        this.senhaAntigaConfirmar = senhaAntigaConfirmar;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    public String getSenhaNovaConfirmar() {
        return senhaNovaConfirmar;
    }

    public void setSenhaNovaConfirmar(String senhaNovaConfirmar) {
        this.senhaNovaConfirmar = senhaNovaConfirmar;
    }
    
    public String alterarSenha() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        
        Funcionario func = (Funcionario) session.getAttribute("funcionario");
        
        if(!senhaAntiga.equals(senhaAntigaConfirmar)) {
            mensagem = "Os Campos da Senha Atual Devem Ser Iguais!";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", mensagem));
            resetarCampos();
            return null;
        } 
        
        if(!senhaNova.equals(senhaNovaConfirmar)){
            mensagem = "Os Campos da Senha Nova Devem Ser Iguais!";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", mensagem));
            resetarCampos();
            return null;
        } 
        
        if(!func.getSenha().equals(senhaAntiga)) {
            mensagem = "Senha Atual está Incorreta!";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", mensagem));
            resetarCampos();
            return null;
        }
        
        if(senhaNova.length() < 8) {
            mensagem = "A Nova Senha deve ter pelo menos 8 caracteres";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", mensagem));
            resetarCampos();
            return null;
        }
        
        if(senhaNova.equals(senhaAntiga)) {
            mensagem = "A Nova Senha deve ser diferente da Senha Atual";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", mensagem));
            resetarCampos();
            return null;
        }
        
        func.setSenha(senhaNova);
        funcionarioDao.alterarSenha(func);
        mensagem = "Senha Alterado com sucesso!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", mensagem));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        
        return "/app/alterarSenha?faces-redirect=true"; 
    }
    
    public void resetarCampos() {       
        senhaAntiga = "";
        senhaAntigaConfirmar = "";
        senhaNova = "";
        senhaNovaConfirmar = ""; 
    }
}