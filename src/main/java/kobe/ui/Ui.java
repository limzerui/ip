package kobe.ui;

/**
 * Handles user interaction: prints divider borders and message blocks.
 */
public class Ui {
    private static final String BORDER = "____________________________________________________________";

    /**
     * Prints the divider border line.
     */
    public void border() {
        System.out.println(BORDER);
    }

    /**
     * Prints a bordered block containing the given lines.
     * @param lines lines to print inside the border
     */
    public void block(String[] lines) {
        border();
        for (String line : lines) {
            System.out.println(line);
        }
        border();
    }
}
