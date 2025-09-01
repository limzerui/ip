package kobe.ui;

public class Ui {
    private static final String BORDER = "____________________________________________________________";

    public void border() {
        System.out.println(BORDER);
    }

    public void block(String[] lines) {
        border();
        for (String line : lines) {
            System.out.println(line);
        }
        border();
    }
}
