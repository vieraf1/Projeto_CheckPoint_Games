package dao;

import model.Cliente;
import util.ConexaoBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    private final Connection c;

    public ClienteDao() {
        this.c = new ConexaoBD().getConnection();
    }

    public void criarCliente(Cliente cliente) {
        String sql = "INSERT INTO CLIENTE"
                + "(ID_FUNCIONARIO, NOME, SOBRENOME, IDADE, DATA_NASCIMENTO, CPF, RG, ENDERECO, COMPLEMENTO, EMAIL, TELEFONE) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getFuncionario().getIdFuncionario());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getSobrenome());
            stmt.setInt(4, cliente.getIdade());
            stmt.setDate(5, new java.sql.Date(cliente.getDataNascimento().getTime()));
            stmt.setString(6, cliente.getCpf());
            stmt.setString(7, cliente.getRg());
            stmt.setString(8, cliente.getEndereco());
            stmt.setString(9, cliente.getComplemento());
            stmt.setString(10, cliente.getEmail());
            stmt.setString(11, cliente.getTelefone());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao criar Cliente\n");
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao criar Cliente\n");
        }
    }

    public Cliente buscarCliente(Integer idCliente) {
        String sql = "SELECT * FROM CLIENTE WHERE ID_CLIENTE = ?";

        Cliente cliente = null;

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer idFuncionario = rs.getInt(2);
                FuncionarioDao funcionarioDao = new FuncionarioDao();

                cliente = new Cliente(
                        rs.getInt(1),
                        funcionarioDao.buscarFuncionario(idFuncionario),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getDate(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12)
                );
            }

            rs.close();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQL Exception ao buscar Cliente\n");
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao buscar Cliente\n");
        }

        return cliente;
    }

    public List<Cliente> listarClientes(String nome, String sobrenome) {
        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM CLIENTE WHERE NOME LIKE ? AND SOBRENOME LIKE ? ORDER BY NOME";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {
            if (nome != null) {
                stmt.setString(1, "%" + nome + "%");
            } else {
                stmt.setString(1, "%%");
            }
            if (sobrenome != null) {
                stmt.setString(2, "%" + sobrenome + "%");
            } else {
                stmt.setString(2, "%%");
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer idFuncionario = rs.getInt(2);
                FuncionarioDao funcionarioDao = new FuncionarioDao();

                Cliente cliente = new Cliente(
                        rs.getInt(1),
                        funcionarioDao.buscarFuncionario(idFuncionario),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getDate(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12)
                );

                clientes.add(cliente);
            }
            rs.close();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao listar Clientes\n" + sqle.getMessage());
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao listar Clientes\n");
        }

        return clientes;
    }

    public void editarCliente(Cliente cliente) {
        String sql = "UPDATE CLIENTE SET "
                + "ID_FUNCIONARIO = ?, "
                + "NOME = ?, "
                + "SOBRENOME = ?, "
                + "IDADE = ?, "
                + "DATA_NASCIMENTO = ?, "
                + "CPF = ?, "
                + "RG = ?, "
                + "ENDERECO = ?, "
                + "COMPLEMENTO = ?, "
                + "EMAIL = ?, "
                + "TELEFONE = ? "
                + "WHERE ID_CLIENTE = ?";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getFuncionario().getIdFuncionario());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getSobrenome());
            stmt.setInt(4, cliente.getIdade());
            stmt.setDate(5, new java.sql.Date(cliente.getDataNascimento().getTime()));
            stmt.setString(6, cliente.getCpf());
            stmt.setString(7, cliente.getRg());
            stmt.setString(8, cliente.getEndereco());
            stmt.setString(9, cliente.getComplemento());
            stmt.setString(10, cliente.getEmail());
            stmt.setString(11, cliente.getTelefone());
            stmt.setInt(12, cliente.getIdCliente());

            stmt.execute();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao editar Cliente\n");
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao editar Cliente\n");
        }
    }

    public void excluirCliente(Cliente cliente) {
        String sql = "DELETE FROM CLIENTE WHERE ID_CLIENTE = ?";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getIdCliente());

            stmt.execute();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao excluir Cliente\n");
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao excluir Cliente\n");
        }
    }

}
