package domain;

import java.time.LocalDateTime;

public class Posting {
    private final long id;
    private String company;
    private String title;
    private String url;
    private String location;
    private final LocalDateTime createdAt;

    public Posting(long id, String company, String title, String url, String location) {
        this.id = id;
        this.company = company;
        this.title = title;
        this.url = url;
        this.location = location;
        this.createdAt = LocalDateTime.now();
    }

    // (옵션) location 없이 만들 때를 대비한 오버로드
    public Posting(long id, String company, String title, String url) {
        this(id, company, title, url, "");
    }

    public long getId() { return id; }
    public String getCompany() { return company; }
    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getLocation() { return location; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCompany(String company) { this.company = company; }
    public void setTitle(String title) { this.title = title; }
    public void setUrl(String url) { this.url = url; }
    public void setLocation(String location) { this.location = location; }
}
