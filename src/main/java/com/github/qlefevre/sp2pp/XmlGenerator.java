package com.github.qlefevre.sp2pp;

import java.io.FileWriter;

import com.github.qlefevre.sp2pp.model.Account;
import com.github.qlefevre.sp2pp.model.AccountTransaction;
import com.github.qlefevre.sp2pp.model.BuySellEntry;
import com.github.qlefevre.sp2pp.model.Classification;
import com.github.qlefevre.sp2pp.model.Classification.Assignment;
import com.github.qlefevre.sp2pp.model.Client;
import com.github.qlefevre.sp2pp.model.CrossEntry;
import com.github.qlefevre.sp2pp.model.Portfolio;
import com.github.qlefevre.sp2pp.model.PortfolioTransaction;
import com.github.qlefevre.sp2pp.model.Price;
import com.github.qlefevre.sp2pp.model.Security;
import com.github.qlefevre.sp2pp.model.Taxonomy;
import com.github.qlefevre.sp2pp.model.Watchlist;
import com.github.qlefevre.sp2pp.settings.AttributeType;
import com.github.qlefevre.sp2pp.settings.AttributeTypes;
import com.github.qlefevre.sp2pp.settings.Bookmark;
import com.github.qlefevre.sp2pp.settings.Bookmarks;
import com.github.qlefevre.sp2pp.settings.Config;
import com.github.qlefevre.sp2pp.settings.ConfigEntry;
import com.github.qlefevre.sp2pp.settings.ConfigSet;
import com.github.qlefevre.sp2pp.settings.ConfigurationSets;
import com.github.qlefevre.sp2pp.settings.Configurations;
import com.github.qlefevre.sp2pp.settings.Settings;
import com.github.qlefevre.sp2pp.xstream.CustomMapConverter;
import com.github.qlefevre.sp2pp.xstream.CustomReferenceByIdMarshallingStrategy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class XmlGenerator {
    public static void generateXml(Client client, String outputPath) throws Exception {
        XStream xstream = new XStream(new StaxDriver());

        // Active les références circulaires (pour éviter la duplication des objets)
        xstream.setMarshallingStrategy(new CustomReferenceByIdMarshallingStrategy());
        // Map <map> <entry>
        xstream.registerConverter(new CustomMapConverter(xstream.getMapper()));

         // Alias pour les classes
        xstream.alias("client", Client.class);
        xstream.alias("security", Security.class);
        xstream.alias("price", Price.class);
        xstream.alias("portfolio", Portfolio.class);
        xstream.alias("account", Account.class);
        xstream.alias("portfolio-transaction", PortfolioTransaction.class);
        xstream.alias("account-transaction", AccountTransaction.class);
        xstream.alias("buysell", BuySellEntry.class);
        xstream.alias("crossEntry", CrossEntry.class);
        xstream.alias("watchlist", Watchlist.class);
        xstream.alias("taxonomy", Taxonomy.class);
        xstream.alias("classification", Classification.class);
             xstream.alias("assignment", Assignment.class);

        // entries used in <configurationSets>
        // settings subtree
        xstream.alias("config-set", ConfigSet.class);
        xstream.aliasField("config-set", ConfigEntry.class, "configSet");
        xstream.alias("entry", ConfigEntry.class);
        xstream.alias("configurations", Configurations.class);
        xstream.alias("config", Config.class);
        xstream.alias("configurationSets", ConfigurationSets.class);
        xstream.alias("settings", Settings.class);
        xstream.alias("bookmarks", Bookmarks.class);
        xstream.alias("bookmark", Bookmark.class);
        xstream.alias("attributeTypes", AttributeTypes.class);
        xstream.alias("attribute-type", AttributeType.class);
        xstream.addImplicitCollection(ConfigurationSets.class, "entry");
        xstream.addImplicitCollection(Configurations.class, "config");
        xstream.addImplicitCollection(AttributeTypes.class, "attributeType");

        // Configuration pour les attributs
        xstream.omitField(Client.class, "id");
        xstream.omitField(Security.class, "id");
        xstream.omitField(Account.class, "id");
        xstream.omitField(Portfolio.class, "id");

        xstream.useAttributeFor(Price.class,  "t");
        xstream.useAttributeFor(Price.class, "v");
     
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xstream.marshal(client, new PrettyPrintWriter(writer));
        }
    }
}
