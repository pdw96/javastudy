import java.util.*;

public class App {
    static Scanner sc = new Scanner(System.in);

    static long seq = 1;
    static List<Posting> postings = new ArrayList<>();

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
                default -> System.out.println("잘못된 입력입니다. 1~5 중 선택하세요.");
            }
        }
    }

    static void printMenu() {
        System.out.println("\n=== 채용공고 스크랩 (Day2) ===");
        System.out.println("1. 공고 추가");
        System.out.println("2. 공고 목록");
        System.out.println("3. 공고 상세");
        System.out.println("4. 공고 삭제");
        System.out.println("5. 종료");
        System.out.println("6. 공고 수정");
    }

    static void addPosting() {
        System.out.println("\n[공고 추가]");
        String company = readNonBlank("회사명: ");
        String title = readNonBlank("제목: ");
        String url = readNonBlank("URL: ");
        String location = readNonBlank("위치: ");
        Posting p = new Posting(seq++, company, title, url, location);
        postings.add(p);

        System.out.println("✅ 저장 완료! id=" + p.getId());
    }

    static void listPostings() {
        System.out.println("\n[공고 목록]");
        if (postings.isEmpty()) {
            System.out.println("저장된 공고가 없습니다.");
            return;
        }

        for (Posting p : postings) {
            System.out.printf("- #%d | %s | %s | %s%n",
                    p.getId(),
                    p.getTitle(),
                    p.getCompany(),
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
        Posting p = findById(id);

        if (p == null) {
            System.out.println("해당 id의 공고가 없습니다.");
            return;
        }

        System.out.println("id: " + p.getId());
        System.out.println("회사: " + p.getCompany());
        System.out.println("제목: " + p.getTitle());
        System.out.println("URL : " + p.getUrl());
        System.out.println("위치: " + p.getLocation());
        System.out.println("등록: " + p.getCreatedAt());
    }

    static void deletePosting() {
        System.out.println("\n[공고 삭제]");
        if (postings.isEmpty()) {
            System.out.println("저장된 공고가 없습니다.");
            return;
        }

        long id = readLong("삭제할 id: ");
        Posting p = findById(id);

        if (p == null) {
            System.out.println("해당 id의 공고가 없습니다.");
            return;
        }

        postings.remove(p);
        System.out.println("✅ 삭제 완료! id=" + id);
    }

    static Posting findById(long id) {
        for (Posting p : postings) {
            if (p.getId() == id) return p;
        }
        return null;
    }
    static void updatePosting() {
    System.out.println("\n[공고 수정]");
    if (postings.isEmpty()) {
        System.out.println("저장된 공고가 없습니다.");
        return;
        }

    long id = readLong("수정할 id: ");
    Posting p = findById(id);

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
