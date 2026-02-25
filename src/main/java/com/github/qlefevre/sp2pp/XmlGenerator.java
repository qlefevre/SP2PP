package com.github.qlefevre.sp2pp;

import com.github.qlefevre.sp2pp.model.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.FileWriter;
import java.util.Map;

public class XmlGenerator {
    public static void generateXml(Client client, String outputPath) throws Exception {
        XStream xstream = new XStream(new StaxDriver());

         // Alias pour les classes
        xstream.alias("client", Client.class);
        xstream.alias("security", Security.class);
        xstream.alias("price", Price.class);

        // Configuration pour les attributs
        xstream.useAttributeFor(Client.class, "id");
        xstream.useAttributeFor(Security.class, "id");

        xstream.useAttributeFor(Price.class,  "t");
        xstream.useAttributeFor(Price.class, "v");

        // Gestion des collections implicites
        xstream.addImplicitCollection(Client.class, "securities");

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
