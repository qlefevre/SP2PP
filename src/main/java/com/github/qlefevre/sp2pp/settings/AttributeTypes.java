package com.github.qlefevre.sp2pp.settings;

import java.util.ArrayList;
import java.util.List;

public class AttributeTypes {
    private List<AttributeType> attributeType = new ArrayList<>();

    public AttributeTypes() {
    }

    public List<AttributeType> getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(List<AttributeType> attributeType) {
        this.attributeType = attributeType;
    }
}