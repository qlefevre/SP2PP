package com.github.qlefevre.sp2pp.model;

import java.util.UUID;

public class Taxonomy
{

    private String id;
    private String name;
    private Classification root;

    public Taxonomy()
    {
        // needed for xstream de-serialization
    }

    public Taxonomy(String name)
    {
        this(UUID.randomUUID().toString(), name);
    }

    Taxonomy(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    /* package */void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public Classification getRoot()
    {
        return root;
    }

    public void setRootNode(Classification node)
    {
        this.root = node;
    }

    
}