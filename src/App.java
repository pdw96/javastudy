import java.util.*;
import java.util.stream.Collectors;

public class App {
    static Scanner sc = new Scanner(System.in);

    static long seq = 1;

    // Day3 핵심: List -> Map (id로 즉시 조회 가능)
    static Map<Long, Posting> postings = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int menu = readInt("메뉴 선택: ");

            switch (menu) {
                case 1 -> addPosting();
                case 2 -> listPostings();
                case 3 -> viewPosting();
                case 4 -> deletePosting();
                case 5 -> {
                    System.out.println("종료합니다.");
                    sc.close();
                    return;
                }
                case 6 -> updatePosting();
                case 7 -> searchByCompany();
                case 8 -> searchByTitle();
                default -> System.out.println("잘못된 입력입니다. 1~8 중 선택하세요.");
            }
        }
    }

    static void printMenu() {
        System.out.println("\n=== 채용공고 스크랩 (Day3) ===");
        System.out.println("1. 공고 추가");
        System.out.println("2. 공고 목록(최신순)");
        System.out.println("3. 공고 상세");
        System.out.println("4. 공고 삭제");
        System.out.println("5. 종료");
        System.out.println("6. 공고 수정");
        System.out.println("7. 회사명 검색");
        System.out.println("8. 제목 검색");
    }

    static void addPosting() {
        System.out.println("\n[공고 추가]");
        String company = readNonBlank("회사명: ");
        String title = readNonBlank("제목: ");
        String url = readNonBlank("URL: ");
        String location = readNonBlank("지역: ");
        Posting p = new Posting(seq++, company, title, url, location);

        postings.put(p.getId(), p);

        System.out.println("✅ 저장 완료! id=" + p.getId());
    }

    static void listPostings() {
        System.out.println("\n[공고 목록 - 최신순]");
        if (postings.isEmpty()) {
            System.out.println("저장된 공고가 없습니다.");
            return;
        }

        List<Posting> sorted = postings.values().stream()
                .sorted(Comparator.comparing(Posting::getCreatedAt).reversed())
                .collect(Collectors.toList());

        for (Posting p : sorted) {
            System.out.printf("- #%d | %s | %s | %s%n",
                    p.getId(),
                    p.getCompany(),
                    p.getTitle(),
                    p.getCreatedAt());
        }
    }

    static void viewPosting() {
        System.out.println("\n[공고 상세]");
        if (postings.isEmpty()) {
            System.out.println("저장된 공고가 없습니다.");
            return;
        }

        long id = readLong("조회할 id: ");
        Posting p = postings.get(id);

        if (p == null) {
            System.out.println("해당 id의 공고가 없습니다.");
            return;
        }

        System.out.println("id: " + p.getId());
        System.out.println("회사: " + p.getCompany());
        System.out.println("제목: " + p.getTitle());
        System.out.println("URL : " + p.getUrl());
        System.out.println("등록: " + p.getCreatedAt());
    }

    static void deletePosting() {
        System.out.println("\n[공고 삭제]");
        if (postings.isEmpty()) {
            System.out.println("저장된 공고가 없습니다.");
            return;
        }

        long id = readLong("삭제할 id: ");
        Posting removed = postings.remove(id);

        if (removed == null) {
            System.out.println("해당 id의 공고가 없습니다.");
            return;
        }

        System.out.println("✅ 삭제 완료! id=" + id);
    }

    static void updatePosting() {
        System.out.println("\n[공고 수정]");
        if (postings.isEmpty()) {
            System.out.println("저장된 공고가 없습니다.");
            return;
        }

        long id = readLong("수정할 id: ");
        Posting p = postings.get(id);

        if (p == null) {
            System.out.println("해당 id의 공고가 없습니다.");
            return;
        }

        System.out.println("※ 그냥 Enter 치면 기존 값 유지");

        String company = readLine("회사명(" + p.getCompany() + "): ");
        String title   = readLine("제목(" + p.getTitle() + "): ");
        String url     = readLine("URL(" + p.getUrl() + "): ");

        if (!company.isBlank()) p.setCompany(company);
        if (!title.isBlank()) p.setTitle(title);
        if (!url.isBlank()) p.setUrl(url);

        System.out.println("✅ 수정 완료! id=" + id);
    }

    static void searchByCompany() {
        System.out.println("\n[회사명 검색]");
        if (postings.isEmpty()) {
            System.out.println("저장된 공고가 없습니다.");
            return;
        }

        String keyword = readNonBlank("회사명 키워드: ").toLowerCase();

        List<Posting> results = postings.values().stream()
                .filter(p -> p.getCompany() != null && p.getCompany().toLowerCase().contains(keyword))
                .sorted(Comparator.comparing(Posting::getCreatedAt).reversed())
                .collect(Collectors.toList());

        printSearchResults(results);
    }

    static void searchByTitle() {
        System.out.println("\n[제목 검색]");
        if (postings.isEmpty()) {
            System.out.println("저장된 공고가 없습니다.");
            return;
        }

        String keyword = readNonBlank("제목 키워드: ").toLowerCase();

        List<Posting> results = postings.values().stream()
                .filter(p -> p.getTitle() != null && p.getTitle().toLowerCase().contains(keyword))
                .sorted(Comparator.comparing(Posting::getCreatedAt).reversed())
                .collect(Collectors.toList());

        printSearchResults(results);
    }

    static void printSearchResults(List<Posting> results) {
        if (results.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        System.out.println("검색 결과 (" + results.size() + "건)");
        for (Posting p : results) {
            System.out.printf("- #%d | %s | %s | %s%n",
                    p.getId(),
                    p.getCompany(),
                    p.getTitle(),
                    p.getCreatedAt());
        }
    }

    // ---------------- 입력 유틸 ----------------
    static int readInt(String msg) {
        while (true) {
            System.out.print(msg);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            }
        }
    }

    static long readLong(String msg) {
        while (true) {
            System.out.print(msg);
            String line = sc.nextLine().trim();
            try {
                return Long.parseLong(line);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            }
        }
    }

    static String readNonBlank(String msg) {
        while (true) {
            System.out.print(msg);
            String v = sc.nextLine().trim();
            if (!v.isBlank()) return v;
            System.out.println("빈 값은 입력할 수 없습니다.");
        }
    }

    static String readLine(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }
}
