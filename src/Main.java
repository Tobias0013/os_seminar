import java.io.*;
import java.util.*;

//command to compile, javac Main.java BashExecutorThread.java

public class Main {
    public static void main(String[] args) throws java.io.IOException {
        String commandLine;
        List<String[]> errors = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n***** Welcome to the Java Command Shell *****");
        System.out.println("If you want to exit the shell, type END and press RETURN.\n");

        while (true) {
            System.out.print("jsh>");
            commandLine = scanner.nextLine();
            // if user entered a return, just loop again
            if (commandLine.equals("")) {
                continue;
            }
            if (commandLine.equalsIgnoreCase("end")) { //User wants to end shell
                System.out.println("\n***** Command Shell Terminated. See you next time. BYE for now. *****\n");
                scanner.close();
                System.exit(0);
            }
            else if (commandLine.equalsIgnoreCase("showerrlog")) {
                System.out.println("Type showerrlog [nmr] to show the produced error");
                for (int i = 0; i < errors.size(); i++) {
                    System.out.println((i + 1) + ": " + errors.get(i)[0]);
                }
            }
            else if (commandLine.toLowerCase().contains("showerrlog")) {
                if (!commandLine.replace("showerrlog ", "").matches("\\d+")){
                    System.out.println("");
                }
            }
            else {
                BashExecutorThread bet = new BashExecutorThread(commandLine, errors);
                Thread thread = new Thread(bet);
                thread.start();
            }
        }
    }
}
