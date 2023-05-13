package com.example.agriclutureassistant.ui.pojo;

import java.util.ArrayList;

public class PlantsTypes {
    public class Root{
        public Query query;
        public String language;
        public String preferedReferential;
        public String bestMatch;
        public ArrayList<Result> results;
        public String version;
        public int remainingIdentificationRequests;

    }
    public class Query{
        public String project;
        public ArrayList<String> images;
        public ArrayList<String> organs;
        public boolean includeRelatedImages;

    }
    public class Result{
        public double score;
        public Species species;
        public Gbif gbif;

    }
    public class Species {
        public String scientificNameWithoutAuthor;
        public String scientificNameAuthorship;
        public Genus genus;
        public Family family;
        public ArrayList<String> commonNames;
        public String scientificName;
    }
        public class Genus{

            public String scientificNameWithoutAuthor;
            public String scientificNameAuthorship;
            public String scientificName;

        }

    public class Family{

        public String scientificNameWithoutAuthor;
        public String scientificNameAuthorship;
        public String scientificName;
    }
    public class Gbif{

        public String id;
    }
}
