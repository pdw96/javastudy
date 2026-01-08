import java.util.*;

public class App {
    // 오늘은 "돌아가게"가 목표라서 App.java 하나로만 간다.
    static Scanner sc = new Scanner(System.in);

    // 공고 데이터(오늘은 Map 대신 List로 시작)
    static long seq = 1;
    static List<Map<String, Object>> postings = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int menu = readInt("메뉴 선택: ");

            if (menu == 1) {
                addPosting();
            } else if (menu == 2) {
                listPostings();
            } else if (menu == 3) {
                System.out.println("종료합니다.");
                break;
            } else {
                System.out.println("잘못된 입력입니다. 1~3 중 선택하세요.");
            }
        }
        sc.close();
    }

    static void printMenu() {
        System.out.println("\n=== 채용공고 스크랩 (Day1) ===");
        System.out.println("1. 공고 추가");
        System.out.println("2. 공고 목록");
        System.out.println("3. 종료");
    }

    static void addPosting() {
        System.out.println("\n[공고 추가]");
        String company = readLine("회사명: ");
        String title = readLine("제목: ");
        String url = readLine("URL: ");

        // 최소 검증(빈 값 방지)
        if (company.isBlank() || title.isBlank() || url.isBlank()) {
            System.out.println("회사명/제목/URL은 비어있을 수 없습니다.");
            return;
        }

        Map<String, Object> p = new HashMap<>();
        p.put("id", seq++);
        p.put("company", company);
        p.put("title", title);
        p.put("url", url);
        p.put("createdAt", new Date());

        postings.add(p);
        System.out.println("✅ 저장 완료!");
    }

    static void listPostings() {
        System.out.println("\n[공고 목록]");
        if (postings.isEmpty()) {
            System.out.println("저장된 공고가 없습니다.");
            return;
        }

        for (Map<String, Object> p : postings) {
            System.out.printf(
                "- #%d | %s | %s | %s\n",
                (long) p.get("id"),
                (String) p.get("company"),
                (String) p.get("title"),
                ((Date) p.get("createdAt")).toString()
            );
        }
    }

    // ---------- 입력 유틸 ----------
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

    static String readLine(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }
}
