package controller;

import dao.FuncionarioDao;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Funcionario;

@ManagedBean(name = "loginBean")
@RequestScoped
@SessionScoped
public class LoginController {
    
    String login;
    String senha;
    String mensagem;
    
    FuncionarioDao funcionarioDao;
    
    public LoginController() {
        mensagem = "";
        funcionarioDao = new FuncionarioDao();
    }
    
    public String verificaLogin() {
        Funcionario func = funcionarioDao.validaLogin(login.trim(), senha.trim()); 
        if(func != null) {
            mensagem = "Seja Bem Vindo" + func.getNome();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem Vindo!", func.getNome()));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("funcionario", func);
            
            return "/app/index?faces-redirect=true"; 
        }
        else {
            mensagem = "Login ou Senha Incorreto!";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", mensagem));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            
            login = "";
            senha = "";
            
            return null;
        }
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        mensagem = "Deslogado com Sucesso!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Deslogado!", mensagem));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        
        return "/login/login?faces-redirect=true";  
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getNomeFuncionario() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
        Funcionario func = (Funcionario) session.getAttribute("funcionario");
        return func.getNome();
    }
}