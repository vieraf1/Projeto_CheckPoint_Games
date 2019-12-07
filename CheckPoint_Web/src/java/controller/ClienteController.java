package controller;

import dao.ClienteDao;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Cliente;
import model.Funcionario;

@ManagedBean(name = "clienteBean")
@ViewScoped
@SessionScoped
public class ClienteController {

    private String nome;
    private String sobrenome;
    private String mensagem;

    private boolean habilitarConsulta;
    private boolean habilitarPainel;
    private boolean clienteNovo;

    private final ClienteDao clienteDao;
    private Cliente clienteCRUD;

    public ClienteController() {
        mensagem = "";
        clienteDao = new ClienteDao();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public List<Cliente> getClientes() {
        return clienteDao.listarClientes(nome, sobrenome);
    }

    public boolean habilitarConsulta() {
        return habilitarConsulta;
    }

    public boolean habilitarPainel() {
        return habilitarPainel;
    }

    public void deletarCliente(Cliente cliente) {
        clienteDao.excluirCliente(cliente);

        mensagem = "O Cliente foi Deletado com Sucesso!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", mensagem));
    }

    public Cliente getClienteCRUD() {
        return clienteCRUD;
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
        clienteCRUD = new Cliente();
        clienteNovo = true;
    }

    public void acaoBotaoAlterar(Cliente cliente) {
        clienteCRUD = cliente;
        clienteNovo = false;

        habilitarConsulta = true;
        habilitarPainel = true;
    }

    public void acaoBotaoCancelar() {
        habilitarConsulta = true;
        habilitarPainel = false;
        clienteCRUD = null;
    }

    public void prePersist() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Funcionario func = (Funcionario) session.getAttribute("funcionario");

        clienteCRUD.setFuncionario(func);

        LocalDateTime dt1 = clienteCRUD.getDataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime dt2 = LocalDateTime.now();

        int anos = (int) dt1.until(dt2, ChronoUnit.YEARS);
        clienteCRUD.setIdade(anos);

        salvarAlterarCliente();
    }

    public void salvarAlterarCliente() {
        if (clienteNovo) {
            clienteDao.criarCliente(clienteCRUD);

            mensagem = "O Cliente foi Cadastrado com Sucesso!";
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", mensagem));
        } else {
            clienteDao.editarCliente(clienteCRUD);

            mensagem = "O Cliente foi Alterado com Sucesso!";
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", mensagem));
        }
        clienteCRUD = null;
    }

}
