public class Kobe {
    public static void main(String[] args) {
        final String BORDER = "----------------------------------------";
        System.out.println(BORDER);
        System.out.println(" Hello! I'm Kobe");
        System.out.println(" What can I do for you?");
        System.out.println(BORDER);

        try (java.util.Scanner sc = new java.util.Scanner(System.in)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().equalsIgnoreCase("bye")) {
                    System.out.println(BORDER);
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println(BORDER);
                    break;
                }
                System.out.println(BORDER);
                System.out.println(" " + line);
                System.out.println(BORDER);
            }
        }
    }
}