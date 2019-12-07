package controller;

import dao.RelatorioDao;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Relatorio;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "relatorioBean")
@ViewScoped
@SessionScoped
public class RelatorioController {
    
    private String mensagem;
    private Date dataDe;
    private Date dataAte;
    
    private List<Relatorio> relatorios;
    
    private static String[] colunasGames = {"Nome do Título", "Quantidade de Alugueis"};
    private static String[] colunasGenero = {"Gênero", "Quantidade de Alugueis"};
    
    private StreamedContent file;

    public Date getDataDe() {
        return dataDe;
    }

    public void setDataDe(Date dataDe) {
        this.dataDe = dataDe;
    }

    public Date getDataAte() {
        return dataAte;
    }

    public void setDataAte(Date dataAte) {
        this.dataAte = dataAte;
    }
    
    public StreamedContent getRelatorioGame() throws FileNotFoundException {
        carregarDadosRelatorioGames();
        InputStream stream = new FileInputStream("C:\\Arquivos\\relatorioGames.xlsx");
        file = new DefaultStreamedContent(stream, "application/xlsx", "relatorioGames.xlsx");
        return file;
    }
    
    public StreamedContent getRelatorioGenero() throws FileNotFoundException {
        carregarDadosRelatorioGenero();
        InputStream stream = new FileInputStream("C:\\Arquivos\\relatorioGenero.xlsx");
        file = new DefaultStreamedContent(stream, "application/xlsx", "relatorioGenero.xlsx");
        return file;
    }
    
    public void carregarDadosRelatorioGames() {
        RelatorioDao relatorioDao = new RelatorioDao();
        relatorios = relatorioDao.relatorioGames(dataDe, dataAte);
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório Games");
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 17);
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        
        Row headerRow = sheet.createRow(0);
        
        for(int i = 0; i < colunasGames.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(colunasGames[i]);
            cell.setCellStyle(headerCellStyle);
        }
        
        int rowNum = 1;
        
        for(Relatorio relatorio: relatorios) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(relatorio.getNome());
            row.createCell(1).setCellValue(relatorio.getQuantidade().toString());
        }
        
        for(int i = 0; i < colunasGames.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        try {
            FileOutputStream arquivo = new FileOutputStream("C:\\Arquivos\\relatorioGames.xlsx");
            
            workbook.write(arquivo);
            arquivo.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RelatorioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void carregarDadosRelatorioGenero() {
        RelatorioDao relatorioDao = new RelatorioDao();
        relatorios = relatorioDao.relatorioGenero(dataDe, dataAte);
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório Gênero");
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 17);
        
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        
        Row headerRow = sheet.createRow(0);
        
        for(int i = 0; i < colunasGenero.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(colunasGenero[i]);
            cell.setCellStyle(headerCellStyle);
        }
        
        int rowNum = 1;
        
        for(Relatorio relatorio: relatorios) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(relatorio.getNome());
            row.createCell(1).setCellValue(relatorio.getQuantidade().toString());
        }
        
        for(int i = 0; i < colunasGenero.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        try {
            FileOutputStream arquivo = new FileOutputStream("C:\\Arquivos\\relatorioGenero.xlsx");
            
            workbook.write(arquivo);
            arquivo.close();
            workbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RelatorioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}