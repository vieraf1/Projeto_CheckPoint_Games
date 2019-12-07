package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Relatorio;
import util.ConexaoBD;

public class RelatorioDao {

    public final Connection c;

    public RelatorioDao() {
        this.c = new ConexaoBD().getConnection();
    }

    public List<Relatorio> relatorioGames(Date dataDe, Date dataAte) {

        List<Relatorio> relatorios = new ArrayList<>();

        String sql = "DECLARE @DATADE DATE, @DATAATE DATE \n"
                + "SET @DATADE = ?\n"
                + "SET @DATAATE = ?\n"
                + "\n"
                + "SELECT G.NOME_TITULO AS JOGO, COUNT(*) AS QUANTIDADE\n"
                + "FROM ALUGUEL A\n"
                + "INNER JOIN GAME G ON(A.NUMERO_SERIE = G.NUMERO_SERIE)\n"
                + "WHERE (@DATADE IS NULL OR @DATAATE IS NULL) OR A.DATA_RETIRADA BETWEEN @DATADE AND @DATAATE\n"
                + "GROUP BY G.NOME_TITULO\n"
                + "ORDER BY COUNT(*) DESC";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            if (dataDe == null) {
                stmt.setDate(1, null);
            } else {
                stmt.setDate(1, new java.sql.Date(dataDe.getTime()));
            }

            if (dataAte == null) {
                stmt.setDate(2, null);
            } else {
                stmt.setDate(2, new java.sql.Date(dataAte.getTime()));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Relatorio relatorio = new Relatorio(
                        rs.getString(1),
                        rs.getInt(2)
                );

                relatorios.add(relatorio);
            }
            rs.close();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao gerar relatório por Game\n" + sqle.getMessage());
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao gerar relatório por Game\n" + npe.getMessage());
        }

        return relatorios;
    }

    public List<Relatorio> relatorioGenero(Date dataDe, Date dataAte) {

        List<Relatorio> relatorios = new ArrayList<>();

        String sql = "DECLARE @DATADE DATE, @DATAATE DATE \n"
                + "\n"
                + "SET @DATADE = ?\n"
                + "SET @DATAATE= ?\n"
                + "\n"
                + "SELECT G.GENERO AS GÊNERO, COUNT(*) AS QUANTIDADE\n"
                + "FROM ALUGUEL A\n"
                + "INNER JOIN GAME G ON(A.NUMERO_SERIE = G.NUMERO_SERIE)\n"
                + "WHERE (@DATADE IS NULL OR @DATAATE IS NULL) OR A.DATA_RETIRADA BETWEEN @DATADE AND @DATAATE\n"
                + "GROUP BY G.GENERO\n"
                + "ORDER BY COUNT(*) DESC";

        try (PreparedStatement stmt = this.c.prepareStatement(sql)) {

            if (dataDe == null) {
                stmt.setDate(1, null);
            } else {
                stmt.setDate(1, new java.sql.Date(dataDe.getTime()));
            }

            if (dataAte == null) {
                stmt.setDate(2, null);
            } else {
                stmt.setDate(2, new java.sql.Date(dataAte.getTime()));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Relatorio relatorio = new Relatorio(
                        rs.getString(1),
                        rs.getInt(2)
                );

                relatorios.add(relatorio);
            }
            rs.close();
            stmt.close();

        } catch (SQLException sqle) {
            System.err.print("\nErro SQLE ao gerar relatório por Gênero\n" + sqle.getMessage());
        } catch (NullPointerException npe) {
            System.err.print("\nErro NPE ao gerar relatório por Gênero\n" + npe.getMessage());
        }

        return relatorios;
    }

}
