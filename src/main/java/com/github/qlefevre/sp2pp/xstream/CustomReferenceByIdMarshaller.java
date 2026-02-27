package com.github.qlefevre.sp2pp.xstream;

import java.util.List;
import java.util.Map;

import com.github.qlefevre.sp2pp.model.Account;
import com.github.qlefevre.sp2pp.model.Security;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.core.ReferenceByIdMarshaller;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.path.Path;
import com.thoughtworks.xstream.mapper.Mapper;

public class CustomReferenceByIdMarshaller extends ReferenceByIdMarshaller {

    public CustomReferenceByIdMarshaller(HierarchicalStreamWriter writer, ConverterLookup converterLookup,
            Mapper mapper) {
        super(writer, converterLookup, mapper);
    }

    public void convert(Object item, Converter converter) {
         if (item instanceof List || item instanceof Map) {
            // List, Map, etc... don't bother using references.
            converter.marshal(item, writer, this);
        } else {
            super.convert(item, converter);
        }

    }

    @Override
    protected Object createReferenceKey(Path currentPath, Object item) {
        if (item instanceof Security) {
            return ((Security) item).getIsin();
        } else if (item instanceof Account) {
            return ((Account) item).getId();
        }

        return super.createReferenceKey(currentPath, item);
    }

}
