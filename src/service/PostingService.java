package service;

import domain.Posting;
import store.PostingRepository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class PostingService {

    private final PostingRepository repo;
    private long seq = 1;

    public PostingService(PostingRepository repo) {
        this.repo = repo;
    }

    public Posting addPosting(String company, String title, String url, String location) {
        company = requireNonBlank(company, "회사명");
        title = requireNonBlank(title, "제목");
        url = requireNonBlank(url, "URL");
        location = requireNonBlank(location, "지역");

        Posting posting = new Posting(seq++, company, title, url, location);
        repo.save(posting);
        return posting;
    }

    public List<Posting> listPostingsLatestFirst() {
        return repo.findAll().stream()
                .sorted(Comparator.comparing(Posting::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public Posting getPosting(long id) {
        return repo.findById(id).orElseThrow(() ->
                new NoSuchElementException("해당 id의 공고가 없습니다."));
    }

    // null 또는 blank면 기존 값 유지
    public Posting updatePosting(long id, String company, String title, String url, String location) {
        Posting p = getPosting(id);

        if (company != null && !company.isBlank()) p.setCompany(company.trim());
        if (title != null && !title.isBlank()) p.setTitle(title.trim());
        if (url != null && !url.isBlank()) p.setUrl(url.trim());
        if (location != null && !location.isBlank()) p.setLocation(location.trim());

        // InMemory라 따로 저장 호출 안 해도 되지만, 형태를 맞추려고 save 호출
        repo.save(p);
        return p;
    }

    public void deletePosting(long id) {
        boolean ok = repo.deleteById(id);
        if (!ok) throw new NoSuchElementException("해당 id의 공고가 없습니다.");
    }

    public List<Posting> searchByCompany(String keyword) {
        String k = requireNonBlank(keyword, "회사명 키워드").toLowerCase();

        return repo.findAll().stream()
                .filter(p -> safeLower(p.getCompany()).contains(k))
                .sorted(Comparator.comparing(Posting::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<Posting> searchByTitle(String keyword) {
        String k = requireNonBlank(keyword, "제목 키워드").toLowerCase();

        return repo.findAll().stream()
                .filter(p -> safeLower(p.getTitle()).contains(k))
                .sorted(Comparator.comparing(Posting::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    private static String requireNonBlank(String v, String fieldName) {
        if (v == null || v.isBlank()) {
            throw new IllegalArgumentException(fieldName + "은(는) 비어있을 수 없습니다.");
        }
        return v.trim();
    }

    private static String safeLower(String v) {
        return v == null ? "" : v.toLowerCase();
    }
}
