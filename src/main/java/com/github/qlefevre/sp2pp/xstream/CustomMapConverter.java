package com.github.qlefevre.sp2pp.xstream;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class CustomMapConverter extends MapConverter {

    public CustomMapConverter(Mapper mapper) {
        super(mapper);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.startNode("map");
        super.marshal(source, writer, context);
        writer.endNode();
    }

}
