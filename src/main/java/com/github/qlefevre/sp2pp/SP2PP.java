package com.github.qlefevre.sp2pp;

import com.github.qlefevre.sp2pp.model.Account;
import com.github.qlefevre.sp2pp.model.AccountTransaction;
import com.github.qlefevre.sp2pp.model.Client;
import com.github.qlefevre.sp2pp.model.Portfolio;
import com.github.qlefevre.sp2pp.model.PortfolioTransaction;
import com.github.qlefevre.sp2pp.model.Security;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SP2PP {
    public static void main(String[] args) {
        try(FileInputStream excelFile = new FileInputStream(args[0])) {
            try(Workbook workbook = new XSSFWorkbook(excelFile)){
            
            Client client = new Client("1");

            Map<String, Portfolio> portfoliosMap = new HashMap<>();
            Map<String, Account> accountsMap = new HashMap<>();
            createPortfolios(client, portfoliosMap, accountsMap);

            Sheet sheet = workbook.getSheetAt(1); // Deuxième onglet Produits structurés
            Map<String,Security> securitiesMap = addSecurities(sheet, client);

            sheet = workbook.getSheetAt(0); // Premier onglet Portefeuille
            addBuyTransactions(sheet, client, securitiesMap, portfoliosMap,accountsMap);

            XmlGenerator.generateXml(client, "output.xml");
            System.out.println("XML file created successfully.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing Excel file", e);
        }
    }

    private static void createPortfolios(Client client, Map<String, Portfolio> portfoliosMap, Map<String, Account> accountsMap) {
        
        String[] brokers = {"Hedios","Linxea","Cashbee","MeilleurTaux"};
        for(String broker : brokers){
            Account account = new Account(broker, broker);
            Portfolio portfolio = new Portfolio(broker, broker);
            client.addAccount(account);
            portfolio.setReferenceAccount(account);
            client.addPortfolio(portfolio);
            portfoliosMap.put(portfolio.getName(), portfolio);
            accountsMap.put(account.getName(), account);
        };
    }

    private static Map<String, Security> addSecurities(Sheet sheet, Client client) {
        Map<String, Security> securitiesMap = new HashMap<>();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0 || row.getCell(0) == null || row.getCell(0).getStringCellValue().isEmpty())
                continue;

            String isin = row.getCell(0).getStringCellValue();
            String name = row.getCell(1).getStringCellValue();
            String issuer = row.getCell(10).getStringCellValue();
            // initial observation date / strike date
            String strikeDate = dateFormat.format(row.getCell(11).getDateCellValue());

            Security security = new Security(isin, name, isin, issuer,strikeDate);
            client.addSecurity(security);
            securitiesMap.put(isin, security);
        }
        return securitiesMap;
    }

    private static void addBuyTransactions(Sheet sheet, Client client, Map<String, Security> securitiesMap,
        Map<String, Portfolio>  portfoliosMap, Map<String, Account> accountsMap) {
        
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0 || row.getCell(0) == null || row.getCell(0).getStringCellValue().isEmpty())
                continue;

            String isin = row.getCell(0).getStringCellValue();
            String broker = row.getCell(2).getStringCellValue();
            BigDecimal shares = BigDecimal.valueOf(row.getCell(4).getNumericCellValue());

            // Mise au format Portfolio Performance
            long computedShared = shares.multiply(BigDecimal.valueOf(100000000)).longValue(); // Convertir les actions en "parts" (en multipliant par 100 millions)
            long computedAmount = shares.multiply(BigDecimal.valueOf(100000)).longValue(); // Montant standard produit structuré = 1000 euros

            // Date de constation intiale
            LocalDate transactionDate = row.getCell(9).getDateCellValue().toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate();

  
            Security security = securitiesMap.get(isin);
            PortfolioTransaction transaction = 
            new PortfolioTransaction(transactionDate, "EUR", 
            computedAmount, security, computedShared, PortfolioTransaction.Type.BUY);
            portfoliosMap.get(broker).addTransaction(transaction);

            AccountTransaction accountTransaction = new AccountTransaction(transactionDate, "EUR", 
            computedAmount, security,  AccountTransaction.Type.BUY);
            accountsMap.get(broker).addTransaction(accountTransaction);

        }
        
    }
}
