package com.github.qlefevre.sp2pp;

import com.github.qlefevre.sp2pp.model.Account;
import com.github.qlefevre.sp2pp.model.Client;
import com.github.qlefevre.sp2pp.model.Portfolio;
import com.github.qlefevre.sp2pp.model.Security;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.util.Iterator;

public class SP2PP {
    public static void main(String[] args) {
        try(FileInputStream excelFile = new FileInputStream(args[0])) {
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(1); // Deuxième onglet

            Client client = new Client("1");

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0 || row.getCell(0) == null || row.getCell(0).getStringCellValue().isEmpty())
                    continue;

                String isin = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                String issuer = row.getCell(10).getStringCellValue();

                Security security = new Security(isin, name, isin, issuer);
                client.addSecurity(security);
            }

            Account account = new Account("Hedios","Hedios");
            Portfolio portfolio = new Portfolio("Hedios","Hedios");
            client.addAccount(account);
            portfolio.setReferenceAccount(account);
            client.addPortfolio(portfolio);

            XmlGenerator.generateXml(client, "output.xml");
            System.out.println("XML file created successfully.");

        } catch (Exception e) {
            throw new RuntimeException("Error processing Excel file", e);
        }
    }
}
