package dao;

import model.Funcionario;
import util.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDao {

    // INSTÂNCIA DE CONEXÃO E CONSTRUTOR -------------------------------------------------------------------------------------------------------------
    private final Connection c;

    public FuncionarioDao() {
        this.c = new ConexaoBD().getConnection();
    }

    public void alterarSenha(Funcionario funcionario) {
        String sql = "UPDATE FUNCIONARIO SET SENHA = ? WHERE ID_FUNCIONARIO = ?";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getSenha());
            stmt.setInt(2, funcionario.getIdFuncionario());

            stmt.execute();
            stmt.close();
        } catch (SQLException sqle) {
            System.err.println("\nErro SQL Exception ao editar Funcinário\n");
        } catch (NullPointerException npe) {
            System.err.println("\nErro Null Pointer Exception ao editar Funcinário\n");
        }
    }
    
    public Funcionario validaLogin(String login, String senha) {
        String sql = "SELECT TOP 1 * FROM FUNCIONARIO WHERE LOGIN = ? AND SENHA = ?";

        Funcionario funcionario = null;

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                funcionario = new Funcionario(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }

            stmt.close();
            rs.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQL Exception ao validar login\n");
        } catch (NullPointerException npe) {
            System.err.print("\nErro Null Pointer Exception ao validar login\n");
        }

        return funcionario;
    }
    
    public Funcionario buscarFuncionario(Integer idFuncionario) {
        String sql = "SELECT * FROM FUNCIONARIO WHERE ID_FUNCIONARIO = ?";

        Funcionario funcionario = null;

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                funcionario = new Funcionario(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }

            stmt.close();
            rs.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQL Exception ao buscar funcionário\n");
        } catch (NullPointerException npe) {
            System.err.print("\nErro Null Pointer Exception ao buscar funcionário\n");
        }

        return funcionario;
    }

}