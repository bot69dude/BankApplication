package util;

public class ConsoleUtils {
    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    // Background colors
    public static final String BG_BLACK = "\u001B[40m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";
    public static final String BG_WHITE = "\u001B[47m";
    
    // Text formatting
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void printHeader(String text) {
        int width = text.length() + 10;
        printLine(width);
        System.out.println(BOLD + BLUE + "│" + centerText(text, width - 2) + "│" + RESET);
        printLine(width);
    }
    
    public static void printSuccess(String message) {
        System.out.println(GREEN + "✓ " + message + RESET);
    }
    
    public static void printError(String message) {
        System.out.println(RED + "✗ " + message + RESET);
    }
    
    public static void printInfo(String message) {
        System.out.println(CYAN + "ℹ " + message + RESET);
    }
    
    public static void printMenuOption(int number, String description) {
        System.out.println(YELLOW + "  " + number + ". " + RESET + description);
    }
    
    public static void printMenu(String title, String[] options) {
        printHeader(title);
        System.out.println();
        for (int i = 0; i < options.length; i++) {
            printMenuOption(i + 1, options[i]);
        }
        System.out.println();
        System.out.print(BOLD + "Enter your choice: " + RESET);
    }
    
    public static void printLine(int width) {
        System.out.print(BLUE + "┌");
        for (int i = 0; i < width - 2; i++) {
            System.out.print("─");
        }
        System.out.println("┐" + RESET);
    }
    
    public static void printBottomLine(int width) {
        System.out.print(BLUE + "└");
        for (int i = 0; i < width - 2; i++) {
            System.out.print("─");
        }
        System.out.println("┘" + RESET);
    }
    
    public static void printBox(String message) {
        int width = message.length() + 4;
        printLine(width);
        System.out.println(BLUE + "│" + WHITE + " " + message + " " + BLUE + "│" + RESET);
        printBottomLine(width);
    }
    
    public static void printProgressBar(int percentage, int width) {
        System.out.print("\r[");
        int completed = width * percentage / 100;
        for (int i = 0; i < width; i++) {
            if (i < completed) {
                System.out.print(GREEN + "█" + RESET);
            } else {
                System.out.print(" ");
            }
        }
        System.out.print("] " + percentage + "%");
    }
    
    public static void printTable(String[] headers, String[][] data) {
        // Calculate column widths
        int[] colWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            colWidths[i] = headers[i].length();
            for (String[] row : data) {
                if (row[i].length() > colWidths[i]) {
                    colWidths[i] = row[i].length();
                }
            }
            colWidths[i] += 2; // Add padding
        }
        
        // Print headers
        System.out.print(BLUE + "┌");
        for (int width : colWidths) {
            for (int i = 0; i < width; i++) {
                System.out.print("─");
            }
            System.out.print("┬");
        }
        System.out.println("┐" + RESET);
        
        System.out.print(BLUE + "│" + RESET);
        for (int i = 0; i < headers.length; i++) {
            System.out.print(BOLD + centerText(headers[i], colWidths[i]) + RESET + BLUE + "│" + RESET);
        }
        System.out.println();
        
        // Print separator
        System.out.print(BLUE + "├");
        for (int width : colWidths) {
            for (int i = 0; i < width; i++) {
                System.out.print("─");
            }
            System.out.print("┼");
        }
        System.out.println("┤" + RESET);
        
        // Print data
        for (String[] row : data) {
            System.out.print(BLUE + "│" + RESET);
            for (int i = 0; i < row.length; i++) {
                System.out.print(centerText(row[i], colWidths[i]) + BLUE + "│" + RESET);
            }
            System.out.println();
        }
        
        // Print bottom
        System.out.print(BLUE + "└");
        for (int width : colWidths) {
            for (int i = 0; i < width; i++) {
                System.out.print("─");
            }
            System.out.print("┴");
        }
        System.out.println("┘" + RESET);
    }
    
    public static void printWelcomeBanner(String appName) {
        clearScreen();
        String[] lines = {
            " _______  _______  __    _  ___   _",
            "|  _    ||   _   ||  |  | ||   | | |",
            "| |_|   ||  |_|  ||   |_| ||   |_| |",
            "|       ||       ||       ||      _|",
            "|  _   | |       ||  _    ||     |_ ",
            "| |_|   ||   _   || | |   ||    _  |",
            "|_______||__| |__||_|  |__||___| |_|"
        };
        
        System.out.println();
        for (String line : lines) {
            System.out.println(BOLD + BLUE + line + RESET);
        }
        System.out.println();
        System.out.println(BOLD + CYAN + centerText("Welcome to " + appName, 40) + RESET);
        System.out.println();
    }
    
    private static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - text.length() - leftPadding;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < leftPadding; i++) {
            sb.append(" ");
        }
        sb.append(text);
        for (int i = 0; i < rightPadding; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
