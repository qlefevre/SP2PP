package com.github.qlefevre.sp2pp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;


public class Classification 
{

    public static final int ONE_HUNDRED_PERCENT = 10000;
  
    public static class Assignment {
        private InvestmentVehicle investmentVehicle;
        private int weight;
        private int rank;
        private Map<String, Object> data;

        public Assignment()
        {
            // needed for xstream de-serialization
        }

        public Assignment(InvestmentVehicle vehicle)
        {
            this(vehicle, ONE_HUNDRED_PERCENT);
        }

        public Assignment(InvestmentVehicle vehicle, int weight)
        {
            this.weight = weight;
            this.investmentVehicle = vehicle;
        }

        public int getWeight()
        {
            return weight;
        }

        public void setWeight(int weight)
        {
            this.weight = weight;
        }

        public InvestmentVehicle getInvestmentVehicle()
        {
            return investmentVehicle;
        }

        public int getRank()
        {
            return rank;
        }

        public void setRank(int rank)
        {
            this.rank = rank;
        }

        public Object setData(String key, Object object)
        {
            if (data == null)
                data = new HashMap<>();

            return object == null ? data.remove(key) : data.put(key, object);
        }

        public Object getData(String key)
        {
            if (data == null)
                return null;

            return data.get(key);
        }

    }

    private String id;
    private String name;
    private String color;

    private Classification parent;
    private List<Classification> children = new ArrayList<>();
    private List<Assignment> assignments = new ArrayList<>();

    private int weight;
    private int rank;

    private Map<String, Object> data;

    public Classification()
    {
        // needed for xstream de-serialization
    }

    public Classification(String name)
    {
        this( UUID.randomUUID().toString(), name);
    }

    public Classification(String id, String name)
    {
        this(null, id, name);
    }

    public Classification(Classification parent, String id, String name, String color)
    {
        this.parent = parent;
        this.id = id;
        this.name = name;
        this.color = color;

        if (color == null)
        {
            Random r = new Random();
            this.color = '#' + Integer.toHexString(((r.nextInt(128) + 127) << 16) //
                            | ((r.nextInt(128) + 127) << 8) //
                            | (r.nextInt(128) + 127));
        }

        this.weight = ONE_HUNDRED_PERCENT;
    }

    public Classification(Classification parent, String id, String name)
    {
        this(parent, id, name, null);
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


    public void setName(String name)
    {
        this.name = name;
    }


    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public Classification getParent()
    {
        return parent;
    }

    public void setParent(Classification parent)
    {
        this.parent = parent;
    }

    public List<Classification> getChildren()
    {
        return children;
    }

    public void addChild(Classification classification)
    {
        children.add(classification);
    }

    public List<Assignment> getAssignments()
    {
        return assignments;
    }

    public void addAssignment(Assignment assignment)
    {
        assignments.add(assignment);
    }

    public void removeAssignment(Assignment assignment)
    {
        assignments.remove(assignment);
    }

    public void clearAssignments()
    {
        assignments.clear();
    }

    public int getWeight()
    {
        return weight;
    }

    public int getChildrenWeight()
    {
        int sum = 0;
        for (Classification child : children)
            sum += child.getWeight();
        return sum;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public int getRank()
    {
        return rank;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    public Object setData(String key, Object object)
    {
        if (data == null)
            data = new HashMap<>();

        return object == null ? data.remove(key) : data.put(key, object);
    }

    public Object getData(String key)
    {
        if (data == null)
            return null;

        return data.get(key);
    }

    public Stream<Map.Entry<String, Object>> getData()
    {
        if (data == null)
            return Stream.empty();
        else
            return data.entrySet().stream();
    }

}