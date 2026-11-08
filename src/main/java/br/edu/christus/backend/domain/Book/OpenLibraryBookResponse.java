package br.edu.christus.backend.domain.Book;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class OpenLibraryBookResponse {

    private String title;
    private List<NamedItem> authors;
    private List<NamedItem> publishers;

    @JsonProperty("publish_date")
    private String publishDate;

    @JsonProperty("number_of_pages")
    private Integer numberOfPages;

    private Cover cover;

    // Getters e Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<NamedItem> getAuthors() { return authors; }
    public void setAuthors(List<NamedItem> authors) { this.authors = authors; }

    public List<NamedItem> getPublishers() { return publishers; }
    public void setPublishers(List<NamedItem> publishers) { this.publishers = publishers; }

    public String getPublishDate() { return publishDate; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

    public Integer getNumberOfPages() { return numberOfPages; }
    public void setNumberOfPages(Integer numberOfPages) { this.numberOfPages = numberOfPages; }

    public Cover getCover() { return cover; }
    public void setCover(Cover cover) { this.cover = cover; }

    public static class NamedItem {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class Cover {
        private String medium;
        private String large;
        public String getMedium() { return medium; }
        public void setMedium(String medium) { this.medium = medium; }
        public String getLarge() { return large; }
        public void setLarge(String large) { this.large = large; }
    }
}
