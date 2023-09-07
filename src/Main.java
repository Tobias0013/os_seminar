import java.io.*;
import java.util.*;

//command to compile, javac Main.java BashExecutorThread.java

public class Main {
    public static void main(String[] args) throws java.io.IOException {
        String commandLine;
        List<String> input;
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

            input = Arrays.asList(commandLine.split(" "));

            if (commandLine.equalsIgnoreCase("end")) { //User wants to end shell
                System.out.println("\n***** Command Shell Terminated. See you next time. BYE for now. *****\n");
                scanner.close();
                System.exit(0);
            }
            else if (commandLine.equalsIgnoreCase("showerrlog")) {
                System.out.println("Type showerrlog [number] to show the error code");
                for (int i = 0; i < errors.size(); i++) {
                    System.out.printf("%s:%s%n",i , errors.get(i)[0]);
                }
            }
            else if (input.get(0).equalsIgnoreCase("showerrlog")) {
                showerrlog(input, errors);
            }
            else {
                BashExecutorThread bet = new BashExecutorThread(input, errors);
                Thread thread = new Thread(bet);
                thread.start();
            }
        }
    }

    public static void showerrlog(List<String> input, List<String[]> errors){
        if (!input.get(1).matches("\\d+")) {
            System.out.println("showerrlog syntax error. syntax: showerrlog [number]");
            return;
        }
        else if (Integer.parseInt(input.get(1)) < 0 || Integer.parseInt(input.get(1)) > errors.size()){
            System.out.println("showerrlog number out of range. syntax: showerrlog [number]");
            return;
        }
        System.out.printf("User input: %s%n", errors.get(Integer.parseInt(input.get(1)))[0]);
        System.out.printf("Error message: %s%n", errors.get(Integer.parseInt(input.get(1)))[1]);

    }
}
