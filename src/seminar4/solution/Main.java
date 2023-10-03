package seminar4.solution;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//command to compile, javac seminar1.Main.java seminar1.BashExecutorThread.java

public class Main {
    private static List<String> input;
    private static List<String[]> errors;
    private static Scanner scanner;

    public static void main(String[] args) {
        input = new ArrayList<>();
        errors = new ArrayList<>();
        String commandLine;
        scanner = new Scanner(System.in);
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

            if (input.get(0).equalsIgnoreCase("end")) { //User wants to end shell
                System.out.println("\n***** Command Shell Terminated. See you next time. BYE for now. *****\n");
                scanner.close();
                System.exit(0);
            }
            else if (input.get(0).equalsIgnoreCase("showerrlog")) {
                showerrlog();
            }
            else if (input.get(0).equalsIgnoreCase("filedump")){
                filedump();
            }
            else if (input.get(0).equalsIgnoreCase("copyfile")){
                copyfile();
            }
            else if (input.get(0).equalsIgnoreCase("createfile")){
                createFile();
            }
            else if (input.get(0).equalsIgnoreCase("renamefile")){
                renameFile();
            }
            else if (input.get(0).equalsIgnoreCase("delfile")){
                delFile();
            }
            else if (input.get(0).equalsIgnoreCase("movefile")){
                moveFile();
            }
            else if (input.get(0).equalsIgnoreCase("createdir")){
                createDir();
            }
            else if (input.get(0).equalsIgnoreCase("renamedir")){
                renameDir();
            }
            else if (input.get(0).equalsIgnoreCase("deldir")){
                delDir();
            }
            else if (input.get(0).equalsIgnoreCase("test")){ // TODO: 2023-10-03 ta bort
                Path path = Paths.get("C:\\Users\\tv-dr\\IdeaProjects\\os_seminar\\src\\seminar4\\test");
                try {
                    deleteDirectory(path);
                } catch (IOException e) {
                    System.out.println("error renaming the file: " + e.getMessage());
                }
                return;
            }
            else{
                BashExecutorThread bet = new BashExecutorThread(input, errors);
                Thread thread = new Thread(bet);
                thread.start();
            }

        }
    }

    /**
     *   Write at least 10 lines describing the new command and how it works.
     */
    public static void delDir() {
        if (input.size() != 2){
            System.out.println("renameDir syntax error. syntax: renameDir [filename]");
            return;
        }
        Path path = Paths.get(input.get(1));

        if (Files.isRegularFile(path) || !Files.isDirectory(path)){
            System.out.println("renameDir error. invalid path");
            return;
        }

        try {
            deleteDirectory(path);
        } catch (IOException e) {
            System.out.println("error renaming the file: " + e.getMessage());
        }
    }

    /**
     * @param directory
     * @throws IOException
     */
    public static void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                for (Path p : stream) {
                    if (Files.isDirectory(p)) {
                        // Recursively deletes subdirectories
                        deleteDirectory(p);
                    } else {
                        // Deletes regular files
                        Files.delete(p);
                    }
                }
            }

            // Deletes the empty directory itself
            Files.delete(directory);
        }
    }

    /**
     *   Write at least 10 lines describing the new command and how it works.
     */
    public static void renameDir() {
        if (input.size() != 3){
            System.out.println("renameDir syntax error. syntax: renameDir [filename] [new name]");
            return;
        }
        Path path = Paths.get(input.get(1));

        if (Files.isRegularFile(path) || !Files.isDirectory(path)){
            System.out.println("renameDir error. invalid path");
            return;
        }

        try {
            Files.move(path, path.resolveSibling("test2"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("error renaming the file: " + e.getMessage());
        }
    }

    /**
     *   Write at least 10 lines describing the new command and how it works.
     */
    public static void createDir() {
        if (input.size() != 2){
            System.out.println("createDir syntax error. syntax: createDir [filename]");
            return;
        }
        Path path = Paths.get(input.get(1));

        if (Files.isRegularFile(path) || !Files.isDirectory(path)){
            System.out.println("createDir error. invalid path");
            return;
        }

        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            System.out.println("error creating the file: " + e.getMessage());
        }
    }

    /**
     *   Write at least 10 lines describing the new command and how it works.
     */
    public static void moveFile() {
        if (input.size() != 3){
            System.out.println("renameFile syntax error. syntax: renameFile [filename] [new path]");
            return;
        }
        Path path = Paths.get(input.get(1));
        Path newPath = Paths.get(input.get(2));

        if (!Files.exists(path) || Files.isDirectory(path)){
            System.out.println("renameFile error. invalid path");
            return;
        }
        else if (Files.exists(path)) {
            System.out.println("renameFile error. invalid path");
            return;
        }

        try {
            Files.move(path, newPath);
        } catch (IOException e) {
            System.out.println("error renaming the file: " + e.getMessage());
        }
    }

    /**
     *   Write at least 10 lines describing the new command and how it works.
     */
    public static void delFile() {
        if (input.size() != 2){
            System.out.println("delFile syntax error. syntax: delFile [filename]");
            return;
        }
        Path path = Paths.get(input.get(1));

        if (!Files.exists(path) || Files.isDirectory(path)){
            System.out.println("delFile error. invalid path");
            return;
        }

        try {
            Files.delete(path);
        } catch (IOException e) {
            System.out.println("error deleting the file: " + e.getMessage());
        }
    }

    /**
     *   Write at least 10 lines describing the new command and how it works.
     */
    public static void renameFile() {
        if (input.size() != 3){
            System.out.println("renameFile syntax error. syntax: renameFile [filename] [new name]");
            return;
        }
        Path path = Paths.get(input.get(1));

        if (!Files.exists(path) || Files.isDirectory(path)){
            System.out.println("renameFile error. invalid path");
            return;
        }

        try {
            Files.move(path, path.resolveSibling(input.get(2)), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("error renaming the file: " + e.getMessage());
        }
    }

    /**
     *   Write at least 10 lines describing the new command and how it works.
     */
    public static void createFile() {
        if (input.size() != 2){
            System.out.println("createFile syntax error. syntax: createFile [filename]");
            return;
        }
        Path path = Paths.get(input.get(1));

        if (Files.exists(path) || Files.isDirectory(path)){
            System.out.println("createFile error. invalid path");
            return;
        }

        try {
            Files.createFile(path);
        } catch (IOException e) {
            System.out.println("error creating the file: " + e.getMessage());
        }
    }

    /**
     *   Write at least 10 lines describing the new command and how it works.
     */
    public static void copyfile() {
        if (input.size() != 3){
            System.out.println("copyfile syntax error. syntax: copyfile [sourceFile] [destinationFile]");
            return;
        }
        Path sourcePath = Paths.get(input.get(1));
        Path destinationPath = Paths.get(input.get(2));
        System.out.println(input);

        if (!Files.exists(sourcePath) || Files.isDirectory(sourcePath)){
            System.out.printf("copyfile error. file not found \"%s\"%n", sourcePath);
            return;
        }
        else if (Files.isDirectory(destinationPath)){
            System.out.printf("copyfile error. file not found \"%s\"%n", destinationPath);
            return;
        }

        if (Files.exists(destinationPath)){
            System.out.println("destination file already exists. do you want to replace file?");
            System.out.println("Type y to proceed");
            if (!scanner.nextLine().equalsIgnoreCase("y")){
                return;
            }
        }

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("error copying the file: " + e.getMessage());
        }

    }

    /**
     *   Write at least 10 lines describing the new command and how it works.
     */
    public static void filedump() {
        if (input.size() != 2){
            System.out.println("filedump syntax error. syntax: filedump [filename]");
            return;
        }
        Path path = Paths.get(input.get(1));

        if (!Files.exists(path) || Files.isDirectory(path)){
            System.out.printf("filedump error. file not found \"%s\"%n", path);
            return;
        }

        try {
            List<String> lines = Files.readAllLines(path);
            for (String l : lines) {
                System.out.println(l);
            }
        } catch (IOException e) {
            System.out.println("error reading the file: " + e.getMessage());
        }
    }

    /**
     *   Write at least 10 lines describing the new command and how it works.
     */
    public static void showerrlog(){
        if (input.size() == 1){
            System.out.println("Type showerrlog [number] to show the error code");
            for (int i = 0; i < errors.size(); i++) {
                System.out.printf("%s:%s%n",i , errors.get(i)[0]);
            }
        }
        else {
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
}
