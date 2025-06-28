package com.projetosd.meta2;
import java.io.Serializable;
import java.util.ArrayList;

public class URL implements Serializable {

    public String url;
    public String title;
    public String quote;
    public ArrayList<String> words = new ArrayList<>();
    public ArrayList<String> links = new ArrayList<>();

    public URL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public void addWords(String words) {
        (this.words).add(words);
    } 

    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }

    public ArrayList<String> getLinks() {
        return links;
    }
    
    public void addLinks(String url) {
        (this.links).add(url);
    }
    
}