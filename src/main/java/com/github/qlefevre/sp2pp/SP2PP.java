package com.github.qlefevre.sp2pp;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.qlefevre.sp2pp.model.Account;
import com.github.qlefevre.sp2pp.model.AccountTransaction;
import com.github.qlefevre.sp2pp.model.BuySellEntry;
import com.github.qlefevre.sp2pp.model.Classification;
import com.github.qlefevre.sp2pp.model.Client;
import com.github.qlefevre.sp2pp.model.Portfolio;
import com.github.qlefevre.sp2pp.model.PortfolioTransaction;
import com.github.qlefevre.sp2pp.model.Security;
import com.github.qlefevre.sp2pp.model.Taxonomy;
import com.github.qlefevre.sp2pp.model.Watchlist;
import com.github.qlefevre.sp2pp.settings.AttributeTypes;
import com.github.qlefevre.sp2pp.settings.Bookmarks;
import com.github.qlefevre.sp2pp.settings.Config;
import com.github.qlefevre.sp2pp.settings.ConfigEntry;
import com.github.qlefevre.sp2pp.settings.ConfigSet;
import com.github.qlefevre.sp2pp.settings.ConfigurationSets;
import com.github.qlefevre.sp2pp.settings.Configurations;
import com.github.qlefevre.sp2pp.settings.Settings;

public class SP2PP {
    public static void main(String[] args) {
        try (FileInputStream excelFile = new FileInputStream(args[0])) {
            try (Workbook workbook = new XSSFWorkbook(excelFile)) {

                Client client = new Client("1");

                Map<String, Portfolio> portfoliosMap = new HashMap<>();
                Map<String, Account> accountsMap = new HashMap<>();
                createPortfolios(client, portfoliosMap, accountsMap);

                Sheet sheet = workbook.getSheetAt(1); // Deuxième onglet Produits structurés
                Map<String, Security> securitiesMap = addSecurities(sheet, client);

                sheet = workbook.getSheetAt(0); // Premier onglet Portefeuille
                addBuyTransactions(sheet, client, securitiesMap, portfoliosMap, accountsMap);
                addRiskTaxonomy(sheet,client, securitiesMap);

                sheet = workbook.getSheetAt(3); // Quatrième onglet PS remboursés
                addSellTransactions(sheet, client, securitiesMap, portfoliosMap, accountsMap);

                client.setSettings(createSettings(portfoliosMap));

                XmlGenerator.generateXml(client, "output.xml");
                System.out.println("XML file created successfully.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing Excel file", e);
        }
    }

    private static void addRiskTaxonomy(Sheet sheet, Client client, Map<String,Security> securitiesMap) {
        String title = "Rémunération & risque";
        Taxonomy taxonomy = new Taxonomy(title);
        client.getTaxonomies().add(taxonomy);
        Classification root = new Classification();
        root.setColor("#AAAAAA");
        root.setName(title);
        taxonomy.setRootNode(root);

        Map<Security,String> riskMap = new HashMap<>();

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0 || row.getCell(0) == null || row.getCell(0).getStringCellValue().isEmpty())
                continue;

            String isin = row.getCell(0).getStringCellValue();
            String risk = row.getCell(10).getStringCellValue();
            riskMap.put(securitiesMap.get(isin), risk);
        }

        Map<String,String> riskColorMap = new LinkedHashMap<>();
        riskColorMap.put("Faible", "#b6d7a8");
        riskColorMap.put("Moyen", "#b7e1cd");
        riskColorMap.put("Fort", "#fff2cc");
        
        //Ajout des classifications de risque
        riskColorMap.keySet().stream().distinct().forEach  (risk0 -> {
            Classification classification = new Classification();
            classification.setName(risk0);
            classification.setColor(riskColorMap.get(risk0));
            root.addChild(classification);
            riskMap.entrySet().stream().filter(entry -> entry.getValue().equals(risk0)).forEach(entry -> {
                classification.addAssignment(new Classification.Assignment(entry.getKey()));
            });
        });
    }

    private static void createPortfolios(Client client, Map<String, Portfolio> portfoliosMap,
            Map<String, Account> accountsMap) {

        String[] brokers = { "Hedios", "Linxea", "Cashbee", "MeilleurTaux" };
        for (String broker : brokers) {
            Account account = new Account(broker, broker);
            Portfolio portfolio = new Portfolio(broker, broker);
            client.addAccount(account);
            portfolio.setReferenceAccount(account);
            client.addPortfolio(portfolio);
            portfoliosMap.put(portfolio.getName(), portfolio);
            accountsMap.put(account.getName(), account);
        }
        ;
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

            Security security = new Security(isin, name, isin, issuer, strikeDate);
            client.addSecurity(security);
            securitiesMap.put(isin, security);
        }
        return securitiesMap;
    }

    private static void addBuyTransactions(Sheet sheet, Client client, Map<String, Security> securitiesMap,
            Map<String, Portfolio> portfoliosMap, Map<String, Account> accountsMap) {

        Watchlist watchlist = new Watchlist("Produits actifs");
        client.addWatchlist(watchlist);

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0 || row.getCell(0) == null || row.getCell(0).getStringCellValue().isEmpty())
                continue;

            String isin = row.getCell(0).getStringCellValue();
            String broker = row.getCell(2).getStringCellValue();
            BigDecimal shares = BigDecimal.valueOf(row.getCell(4).getNumericCellValue());

            // Mise au format Portfolio Performance
            long computedShared = shares.multiply(BigDecimal.valueOf(100000000)).longValue(); // Convertir les actions
                                                                                              // en "parts" (en
                                                                                              // multipliant par 100
                                                                                              // millions)
            long computedAmount = shares.multiply(BigDecimal.valueOf(100000)).longValue(); // Montant standard produit
                                                                                           // structuré = 1000 euros

            // Date de constation intiale
            LocalDate transactionDate = row.getCell(9).getDateCellValue().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            Security security = securitiesMap.get(isin);
            PortfolioTransaction transaction = new PortfolioTransaction(transactionDate, "EUR",
                    computedAmount, security, computedShared, PortfolioTransaction.Type.BUY);
            portfoliosMap.get(broker).addTransaction(transaction);

            AccountTransaction accountTransaction = new AccountTransaction(transactionDate, "EUR",
                    computedAmount, security, AccountTransaction.Type.BUY);
            accountsMap.get(broker).addTransaction(accountTransaction);

            new BuySellEntry(portfoliosMap.get(broker), transaction, accountsMap.get(broker), accountTransaction);

            // On ajoute la liste des produits actifs
            watchlist.addSecurity(security);

        }

    }

    private static void addSellTransactions(Sheet sheet, Client client, Map<String, Security> securitiesMap,
            Map<String, Portfolio> portfoliosMap, Map<String, Account> accountsMap) {

        Watchlist watchlist = new Watchlist("Produits remboursés");
        client.addWatchlist(watchlist);

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0 || row.getCell(0) == null || row.getCell(0).getStringCellValue().isEmpty())
                continue;

            String isin = row.getCell(0).getStringCellValue();
            String broker = row.getCell(2).getStringCellValue();
            BigDecimal shares = BigDecimal.valueOf(row.getCell(4).getNumericCellValue());

            // Mise au format Portfolio Performance
            long computedShared = shares.multiply(BigDecimal.valueOf(100000000)).longValue(); // Convertir les actions
                                                                                              // en "parts" (en
                                                                                              // multipliant par 100
                                                                                              // millions)
            long computedAmount = shares.multiply(BigDecimal.valueOf(100000)).longValue(); // Montant standard produit
                                                                                           // structuré = 1000 euros

            Security security = securitiesMap.get(isin);

            // Date de constation intiale
            LocalDate transactionDate = LocalDate.parse(security.getAttributes().get("strike date"),
                    DateTimeFormatter.ISO_LOCAL_DATE);

            // Transaction d'achat à la date de constatation initiale
            PortfolioTransaction transaction = new PortfolioTransaction(transactionDate, "EUR",
                    computedAmount, security, computedShared, PortfolioTransaction.Type.BUY);
            portfoliosMap.get(broker).addTransaction(transaction);

            AccountTransaction accountTransaction = new AccountTransaction(transactionDate, "EUR",
                    computedAmount, security, AccountTransaction.Type.BUY);
            accountsMap.get(broker).addTransaction(accountTransaction);

            new BuySellEntry(portfoliosMap.get(broker), transaction, accountsMap.get(broker), accountTransaction);

            // Transaction de vente à la date de remboursement
            BigDecimal gain = BigDecimal.valueOf(row.getCell(10).getNumericCellValue());
            long totalAmount = shares.multiply(BigDecimal.valueOf(1000)).add(gain).multiply(BigDecimal.valueOf(100))
                    .longValue(); // Montant standard produit structuré + gain
            LocalDate sellTransactionDate = row.getCell(9).getDateCellValue().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            transaction = new PortfolioTransaction(sellTransactionDate, "EUR",
                    totalAmount, security, computedShared, PortfolioTransaction.Type.SELL);
            portfoliosMap.get(broker).addTransaction(transaction);

            accountTransaction = new AccountTransaction(sellTransactionDate, "EUR",
                    totalAmount, security, AccountTransaction.Type.SELL);
            accountsMap.get(broker).addTransaction(accountTransaction);

            new BuySellEntry(portfoliosMap.get(broker), transaction, accountsMap.get(broker), accountTransaction);

             // On ajoute la liste des produits remboursés
            watchlist.addSecurity(security);
        }

    }

    private static Settings createSettings(Map<String, Portfolio> portfoliosMap) {
        Settings settings = new Settings();

        // Empty bookmarks
        settings.setBookmarks(new Bookmarks());

        // Empty attributeTypes
        settings.setAttributeTypes(new AttributeTypes());

        // ConfigurationSets with one entry
        ConfigurationSets configSets = new ConfigurationSets();
        ConfigEntry entry = new ConfigEntry();
        entry.setString("client-filter-definitions");

        ConfigSet configSet = new ConfigSet();
        Configurations configurations = new Configurations();
        Config config = new Config();
        config.setUuid(UUID.randomUUID().toString());
        config.setName(portfoliosMap.keySet().stream().sorted().collect(Collectors.joining(", "))); 
        config.setData(portfoliosMap.values().stream().map(Portfolio::getUuid).map(Object::toString)
                .collect(Collectors.joining(","))); 
        configurations.getConfig().add(config);
        configSet.setConfigurations(configurations);
        entry.setConfigSet(configSet);
        configSets.getEntry().add(entry);

        settings.setConfigurationSets(configSets);

        return settings;
    }

}
