package dao;

import model.Game;
import util.ConexaoBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.EnumConsole;
import model.EnumEstudio;
import model.EnumGenero;

public class GameDao {

    private final Connection c;

    public GameDao() {
        this.c = new ConexaoBD().getConnection();
    }

    public void criarGame(Game game) {
        String sql = "INSERT INTO GAME" + "(NUMERO_SERIE, ID_FUNCIONARIO, NOME_TITULO, VALOR_ALUGUEL, DATA_LANCAMENTO, FAIXA_ETARIA, GENERO, CONSOLE, ESTUDIO, IC_ALUGADO) VALUES " + "(?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setString(1, game.getNumeroSerie());
            stmt.setInt(2, game.getFuncionario().getIdFuncionario());
            stmt.setString(3, game.getNomeTitulo());
            stmt.setFloat(4, game.getValorAluguel());
            stmt.setDate(5, new java.sql.Date(game.getDataLancamento().getTime()));
            stmt.setInt(6, game.getFaixaEtaria());
            stmt.setString(7, game.getGenero().toString());
            stmt.setString(8, game.getConsole().toString());
            stmt.setString(9, game.getEstudio().name());
            stmt.setBoolean(10, game.isAlugado());

            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao criar Game\n" + sqle.getMessage());
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao criar Game\n" + npe.getMessage());
        }
    }

    public Game buscarGame(String numeroSerie) {
        String sql = "SELECT TOP 1 * FROM GAME WHERE NUMERO_SERIE = ?";

        Game game = null;

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setString(1, numeroSerie);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer idFuncionario = rs.getInt(2);
                FuncionarioDao funcionarioDao = new FuncionarioDao();

                game = new Game(
                        rs.getString(1),
                        funcionarioDao.buscarFuncionario(idFuncionario),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getDate(5),
                        rs.getInt(6),
                        EnumGenero.valueOf(rs.getString(7)),
                        EnumConsole.valueOf(rs.getString(8)),
                        EnumEstudio.valueOf(rs.getString(9)),
                        rs.getBoolean(10)
                );
            }

            rs.close();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao encontrar Game\n");
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao encontrar Game\n");
        }

        return game;
    }

    public List<Game> listarGames(String numeroSerie, String nomeTitulo) {
        List<Game> games = new ArrayList<>();

        String sql = "SELECT * FROM GAME WHERE NUMERO_SERIE LIKE ? AND NOME_TITULO LIKE ? ORDER BY NOME_TITULO";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            if (numeroSerie != null) {
                stmt.setString(1, "%" + numeroSerie + "%");
            } else {
                stmt.setString(1, "%%");
            }
            if (nomeTitulo != null) {
                stmt.setString(2, "%" + nomeTitulo + "%");
            } else {
                stmt.setString(2, "%%");
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer idFuncionario = rs.getInt(2);
                FuncionarioDao funcionarioDao = new FuncionarioDao();

                Game game = new Game(
                        rs.getString(1),
                        funcionarioDao.buscarFuncionario(idFuncionario),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getDate(5),
                        rs.getInt(6),
                        EnumGenero.valueOf(rs.getString(7)),
                        EnumConsole.valueOf(rs.getString(8)),
                        EnumEstudio.valueOf(rs.getString(9)),
                        rs.getBoolean(10)
                );

                games.add(game);
            }
            rs.close();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao criar lista de Games\n");
            System.err.println(sqle);
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao listar Game\n");
        }

        return games;
    }
    
    public List<Game> disponibilidade(String nomeTitulo) {
        List<Game> games = new ArrayList<>();

        String sql = "SELECT * FROM GAME WHERE NOME_TITULO LIKE ? AND IC_ALUGADO = 0 ORDER BY NOME_TITULO";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            if (nomeTitulo != null) {
                stmt.setString(1, "%" + nomeTitulo + "%");
            } else {
                stmt.setString(1, "%%");
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer idFuncionario = rs.getInt(2);
                FuncionarioDao funcionarioDao = new FuncionarioDao();

                Game game = new Game(
                        rs.getString(1),
                        funcionarioDao.buscarFuncionario(idFuncionario),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getDate(5),
                        rs.getInt(6),
                        EnumGenero.valueOf(rs.getString(7)),
                        EnumConsole.valueOf(rs.getString(8)),
                        EnumEstudio.valueOf(rs.getString(9)),
                        rs.getBoolean(10)
                );

                games.add(game);
            }
            rs.close();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao criar lista de Games disponíveis\n");
            System.err.println(sqle);
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao listar Game disponíveis\n");
        }

        return games;
    }

    public void editarGame(Game game) {

        String sql = "UPDATE GAME SET NOME_TITULO = ?, VALOR_ALUGUEL = ?, DATA_LANCAMENTO = ?, FAIXA_ETARIA = ?, GENERO = ?, CONSOLE = ?, ESTUDIO = ?, IC_ALUGADO = ? WHERE NUMERO_SERIE = ?";
        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setString(1, game.getNomeTitulo());
            stmt.setFloat(2, game.getValorAluguel());
            stmt.setDate(3, new java.sql.Date(game.getDataLancamento().getTime()));
            stmt.setInt(4, game.getFaixaEtaria());
            stmt.setString(5, game.getGenero().toString());
            stmt.setString(6, game.getConsole().toString());
            stmt.setString(7, game.getEstudio().toString());
            stmt.setBoolean(8, game.isAlugado());
            stmt.setString(9, game.getNumeroSerie());

            stmt.execute();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao editar Game\n" + sqle.getMessage());
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao editar Game\n");
        }
    }

    public void excluirGame(Game game) {
        String sql = "DELETE FROM GAME WHERE NUMERO_SERIE = ?";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {
            stmt.setString(1, game.getNumeroSerie());

            stmt.execute();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao excluir Game\n");
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao excluir Game\n");
        }
    }
}
