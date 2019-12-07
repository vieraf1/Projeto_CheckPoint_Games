package model;

public class Funcionario {
    
    private Integer idFuncionario;
    private String login;
    private String senha;
    private String nome;
    private String empresa;
    private String cargo;

    public Funcionario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Funcionario(Integer idFuncionario, String login, String senha, String nome, String empresa, String cargo) {
        this.idFuncionario = idFuncionario;
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.empresa = empresa;
        this.cargo = cargo;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
}