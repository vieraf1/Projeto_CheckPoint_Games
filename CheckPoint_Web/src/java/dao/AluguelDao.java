package dao;

import model.Aluguel;
import util.ConexaoBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.EnumTipoPagamento;

public class AluguelDao {

    public final Connection c;

    public AluguelDao() {
        this.c = new ConexaoBD().getConnection();
    }

    public void criarAluguel(Aluguel aluguel) {
        String sql = "INSERT INTO ALUGUEL" + "(ID_FUNCIONARIO, ID_CLIENTE, NUMERO_SERIE, DATA_RETIRADA, DATA_DEVOLUCAO, VALOR_PAGAR, DATA_ENTREGA, DIAS_ATRASO, VALOR_PAGO, TIPO_PAGAMENTO) VALUES(?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setInt(1, aluguel.getFuncionario().getIdFuncionario());
            stmt.setInt(2, aluguel.getCliente().getIdCliente());
            stmt.setString(3, aluguel.getGame().getNumeroSerie());
            stmt.setDate(4, new java.sql.Date(aluguel.getDataRetirada().getTime()));
            stmt.setDate(5, new java.sql.Date(aluguel.getDataDevolucao().getTime()));
            stmt.setDouble(6, aluguel.getValorPagar());
            stmt.setDate(7, aluguel.getDataEntrega() == null ? null : new java.sql.Date(aluguel.getDataEntrega().getTime()));
            stmt.setInt(8, aluguel.getDiasAtrasados());
            stmt.setDouble(9, aluguel.getValorPago());
            stmt.setString(10, aluguel.getTipoPagamento().toString());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao criar Aluguel\n");
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao criar Aluguel\n");
        }
    }

    public Aluguel buscarAluguel(Integer idAluguel) {

        String sql = "SELECT * FROM ALUGUEL WHERE ID_ALUGUEL = ?";

        Aluguel aluguel = null;

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setInt(1, idAluguel);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer idFuncionario = rs.getInt(2);
                FuncionarioDao funcionarioDao = new FuncionarioDao();
                
                Integer idCliente = rs.getInt(3);
                ClienteDao clienteDao = new ClienteDao();
                
                String numeroSerie = rs.getString(4);
                GameDao gameDao = new GameDao();
                
                aluguel = new Aluguel(
                        rs.getInt(1),
                        funcionarioDao.buscarFuncionario(idFuncionario),
                        clienteDao.buscarCliente(idCliente),
                        gameDao.buscarGame(numeroSerie),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getDouble(7),
                        rs.getDate(8),
                        rs.getInt(9),
                        rs.getDouble(10),
                        EnumTipoPagamento.valueOf(rs.getString(11))
                );
            }

            rs.close();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao consultar Aluguel\n");
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao consultar Aluguel\n");
        }

        return aluguel;
    }

    public List<Aluguel> listarAlugueis(Integer id, String numero) {

        List<Aluguel> alugueis = new ArrayList<>();

        String sql = "DECLARE @NUMERO_SERIE VARCHAR(100), @ID_CLIENTE INT " +
                     "SET @NUMERO_SERIE = ? " +
                     "SET @ID_CLIENTE = ? " +
                     "SELECT * FROM ALUGUEL " +
                     "WHERE (@NUMERO_SERIE IS NULL OR NUMERO_SERIE = @NUMERO_SERIE) " +
                     "AND (@ID_CLIENTE IS NULL OR ID_CLIENTE = @ID_CLIENTE)" +
                     "ORDER BY ID_ALUGUEL DESC" ;

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {
            
            if(numero == null) {
                stmt.setString(1, null);
            }
            else {
                stmt.setString(1, numero);
            }
            
            if(id == null) {
                stmt.setString(2, null);
            }
            else {
                stmt.setInt(2, id);   
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Integer idFuncionario = rs.getInt(2);
                FuncionarioDao funcionarioDao = new FuncionarioDao();
                
                Integer idCliente = rs.getInt(3);
                ClienteDao clienteDao = new ClienteDao();
                
                String numeroSerie = rs.getString(4);
                GameDao gameDao = new GameDao();
                
                Aluguel aluguel = new Aluguel(
                        rs.getInt(1),
                        funcionarioDao.buscarFuncionario(idFuncionario),
                        clienteDao.buscarCliente(idCliente),
                        gameDao.buscarGame(numeroSerie),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getDouble(7),
                        rs.getDate(8),
                        rs.getInt(9),
                        rs.getDouble(10),
                        EnumTipoPagamento.valueOf(rs.getString(11))
                );

                alugueis.add(aluguel);
            }
            rs.close();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao listar de Aluguel\n" + sqle.getMessage());
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao listar Aluguel\n" + npe.getMessage());
        }

        return alugueis;
    }

    public void editarAluguel(Aluguel aluguel) {

        String sql = "UPDATE ALUGUEL SET ID_FUNCIONARIO = ?, ID_CLIENTE = ?, NUMERO_SERIE = ?, DATA_RETIRADA = ?, DATA_DEVOLUCAO = ?, VALOR_PAGAR = ?, DATA_ENTREGA = ?, DIAS_ATRASO = ?, VALOR_PAGO = ?, TIPO_PAGAMENTO = ?  WHERE ID_ALUGUEL = ?";
        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            stmt.setInt(1, aluguel.getFuncionario().getIdFuncionario());
            stmt.setInt(2, aluguel.getCliente().getIdCliente());
            stmt.setString(3, aluguel.getGame().getNumeroSerie());
            stmt.setDate(4, new java.sql.Date(aluguel.getDataRetirada().getTime()));
            stmt.setDate(5, new java.sql.Date(aluguel.getDataDevolucao().getTime()));
            stmt.setDouble(6, aluguel.getValorPagar());
            stmt.setDate(7, new java.sql.Date(aluguel.getDataEntrega().getTime()));
            stmt.setInt(8, aluguel.getDiasAtrasados());
            stmt.setDouble(9, aluguel.getValorPago());
            stmt.setString(10, aluguel.getTipoPagamento().toString());
            stmt.setInt(11, aluguel.getIdAluguel());

            stmt.execute();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao editar Aluguel\n");
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao editar Aluguel\n");
        }
    }

    public void excluirAluguel(Aluguel aluguel) {

        String sql = "DELETE FROM ALUGUEL WHERE ID_ALUGUEL = ?";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {
            stmt.setInt(1, aluguel.getIdAluguel());

            stmt.execute();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nAluguel SQLE ao excluir Game\n");
        } catch (NullPointerException npe) {
            System.err.print("\nAluguel NPE ao excluir Game\n");
        }
    }
}