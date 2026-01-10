package app;

import domain.Posting;
import service.PostingService;
import store.InMemoryPostingRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {

    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        PostingService postingService = new PostingService(new InMemoryPostingRepository());

        while (true) {
            printMenu();
            int menu = readInt("메뉴 선택: ");

            try {
                switch (menu) {
                    case 1 -> addPosting(postingService);
                    case 2 -> listPostings(postingService);
                    case 3 -> viewPosting(postingService);
                    case 4 -> deletePosting(postingService);
                    case 5 -> {
                        System.out.println("종료합니다.");
                        sc.close();
                        return;
                    }
                    case 6 -> updatePosting(postingService);
                    case 7 -> searchByCompany(postingService);
                    case 8 -> searchByTitle(postingService);
                    default -> System.out.println("잘못된 입력입니다. 1~8 중 선택하세요.");
                }
            } catch (IllegalArgumentException | NoSuchElementException e) {
                System.out.println("❗ " + e.getMessage());
            }
        }
    }

    static void printMenu() {
        System.out.println("\n=== 채용공고 스크랩 (Day4: Layered) ===");
        System.out.println("1. 공고 추가");
        System.out.println("2. 공고 목록(최신순)");
        System.out.println("3. 공고 상세");
        System.out.println("4. 공고 삭제");
        System.out.println("5. 종료");
        System.out.println("6. 공고 수정");
        System.out.println("7. 회사명 검색");
        System.out.println("8. 제목 검색");
    }

    static void addPosting(PostingService service) {
        System.out.println("\n[공고 추가]");
        String company = readNonBlank("회사명: ");
        String title = readNonBlank("제목: ");
        String url = readNonBlank("URL: ");
        String location = readNonBlank("지역: ");

        Posting p = service.addPosting(company, title, url, location);
        System.out.println("✅ 저장 완료! id=" + p.getId());
    }

    static void listPostings(PostingService service) {
        System.out.println("\n[공고 목록 - 최신순]");
        List<Posting> list = service.listPostingsLatestFirst();
        if (list.isEmpty()) {
            System.out.println("저장된 공고가 없습니다.");
            return;
        }
        for (Posting p : list) {
            System.out.printf("- #%d | %s | %s | %s | %s%n",
                    p.getId(),
                    p.getCompany(),
                    p.getTitle(),
                    p.getLocation(),
                    p.getCreatedAt());
        }
    }

    static void viewPosting(PostingService service) {
        System.out.println("\n[공고 상세]");
        long id = readLong("조회할 id: ");
        Posting p = service.getPosting(id);

        System.out.println("id: " + p.getId());
        System.out.println("회사: " + p.getCompany());
        System.out.println("제목: " + p.getTitle());
        System.out.println("URL : " + p.getUrl());
        System.out.println("지역: " + p.getLocation());
        System.out.println("등록: " + p.getCreatedAt());
    }

    static void deletePosting(PostingService service) {
        System.out.println("\n[공고 삭제]");
        long id = readLong("삭제할 id: ");
        service.deletePosting(id);
        System.out.println("✅ 삭제 완료! id=" + id);
    }

    static void updatePosting(PostingService service) {
        System.out.println("\n[공고 수정]");
        long id = readLong("수정할 id: ");

        Posting before = service.getPosting(id);
        System.out.println("※ 그냥 Enter 치면 기존 값 유지");

        String company = readLine("회사명(" + before.getCompany() + "): ");
        String title = readLine("제목(" + before.getTitle() + "): ");
        String url = readLine("URL(" + before.getUrl() + "): ");
        String location = readLine("지역(" + before.getLocation() + "): ");

        service.updatePosting(id, company, title, url, location);
        System.out.println("✅ 수정 완료! id=" + id);
    }

    static void searchByCompany(PostingService service) {
        System.out.println("\n[회사명 검색]");
        String keyword = readNonBlank("회사명 키워드: ");
        List<Posting> results = service.searchByCompany(keyword);
        printSearchResults(results);
    }

    static void searchByTitle(PostingService service) {
        System.out.println("\n[제목 검색]");
        String keyword = readNonBlank("제목 키워드: ");
        List<Posting> results = service.searchByTitle(keyword);
        printSearchResults(results);
    }

    static void printSearchResults(List<Posting> results) {
        if (results.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }
        System.out.println("검색 결과 (" + results.size() + "건)");
        for (Posting p : results) {
            System.out.printf("- #%d | %s | %s | %s | %s%n",
                    p.getId(),
                    p.getCompany(),
                    p.getTitle(),
                    p.getLocation(),
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
