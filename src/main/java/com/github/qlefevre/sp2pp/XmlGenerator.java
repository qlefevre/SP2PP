package com.github.qlefevre.sp2pp;

import com.github.qlefevre.sp2pp.model.*;
import com.github.qlefevre.sp2pp.xstream.CustomReferenceByIdMarshallingStrategy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.FileWriter;
import java.util.Map;

public class XmlGenerator {
    public static void generateXml(Client client, String outputPath) throws Exception {
        XStream xstream = new XStream(new StaxDriver());

        // Active les références circulaires (pour éviter la duplication des objets)
        xstream.setMarshallingStrategy(new CustomReferenceByIdMarshallingStrategy());

         // Alias pour les classes
        xstream.alias("client", Client.class);
        xstream.alias("security", Security.class);
        xstream.alias("price", Price.class);
        xstream.alias("portfolio", Portfolio.class);
        xstream.alias("account", Account.class);

        // Configuration pour les attributs
        xstream.omitField(Client.class, "id");
        xstream.omitField(Security.class, "id");
        xstream.omitField(Account.class, "id");

        xstream.useAttributeFor(Price.class,  "t");
        xstream.useAttributeFor(Price.class, "v");


        // Configuration pour les Map
        xstream.alias("map", Map.class);
        xstream.alias("entry", Map.Entry.class);
        //xstream.useAttributeFor(Map.Entry.class, "key");
     
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xstream.marshal(client, new PrettyPrintWriter(writer));
        }
    }
}
