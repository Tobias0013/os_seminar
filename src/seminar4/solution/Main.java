package seminar4.solution;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static Scanner scanner;

    public static void main(String[] args) {
        List<String> input;
        List<String[]> errors = new ArrayList<>();
        String commandLine;
        scanner = new Scanner(System.in);
        System.out.println("\n\n***** Welcome to the Java Command Shell *****");
        System.out.println("If you want to exit the shell, type END and press RETURN.\n");

        while (true) {
            System.out.print("jsh>");
            commandLine = scanner.nextLine();
            // if user entered a return, just loop again
            if (commandLine.isBlank()) {
                continue;
            }

            input = Arrays.asList(commandLine.split(" "));

            switch (input.get(0).toLowerCase()){
                case "end":
                    System.out.println("\n***** Command Shell Terminated. See you next time. BYE for now. *****\n");
                    scanner.close();
                    System.exit(0);
                    break;
                case "showerrlog":
                    showerrlog(input, errors);
                    break;
                case "filedump":
                    filedump(input);
                    break;
                case "copyfile":
                    copyfile(input);
                    break;
                case "createfile":
                    createFile(input);
                    break;
                case "renamefile":
                    renameFile(input);
                    break;
                case "delfile":
                    delFile(input);
                    break;
                case "movefile":
                    moveFile(input);
                    break;
                case "createdir":
                    createDir(input);
                    break;
                case "renamedir":
                    renameDir(input);
                    break;
                case "deldir":
                    delDir(input);
                    break;
                default:
                    BashExecutorThread bet = new BashExecutorThread(input, errors);
                    Thread thread = new Thread(bet);
                    thread.start();
            }
        }
    }

    /**
     * Deletes a directory at the specified path.
     * <p>
     * This method expects a specific syntax for its input, where the second element in
     * the 'input' list represents the path of the directory to be deleted. It verifies
     * the path, checks if it represents a directory, and deletes the directory and its
     * contents if it exists.
     *
     * @param input A list of input strings provided by the user. The first element
     *             (input.get(0)) is expected to contain the command "deldir," and
     *             the second element (input.get(1)) should be a valid directory path.
     *             Syntax: delDir [directory path]
     */
    public static void delDir(List<String> input) {
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
     * Recursively deletes a directory and its contents.
     * <p>
     * This method is used internally by other methods to delete directories and their
     * contents. It performs a recursive deletion, starting from the specified directory
     * and recursively deleting subdirectories and their contents.
     *
     * @param directory The path of the directory to be deleted.
     * @throws IOException If an I/O error occurs during the directory deletion process.
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
     * Renames a directory at the specified path with a new name.
     * <p>
     * This method expects a specific syntax for its input, where the second element in
     * the 'input' list represents the current directory path, and the third element
     * represents the new name for the directory. It verifies the path, checks if it
     * represents a directory, and renames the directory if it exists.
     *
     * @param input A list of input strings provided by the user. The first element
     *             (input.get(0)) is expected to contain the command "renameDir," the
     *             second element (input.get(1)) should be the current directory path,
     *             and the third element (input.get(2)) should be the new name.
     *             Syntax: renameDir [current directory path] [new name]
     */
    public static void renameDir(List<String> input) {
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
            Files.move(path, path.resolveSibling(input.get(2)), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("error renaming the file: " + e.getMessage());
        }
    }

    /**
     * Creates a new directory at the specified path.
     * <p>
     * This method expects a specific syntax for its input, where the second element in
     * the 'input' list represents the path of the directory to be created. It verifies
     * the path, checks if it represents a directory, and creates the directory if it
     * doesn't exist.
     *
     * @param input A list of input strings provided by the user. The first element
     *             (input.get(0)) is expected to contain the command "createdir," and
     *             the second element (input.get(1)) should be a valid directory path.
     *             Syntax: createDir [directory path]
     */
    public static void createDir(List<String> input) {
        if (input.size() != 2){
            System.out.println("createDir syntax error. syntax: createDir [filename]");
            return;
        }
        Path path = Paths.get(input.get(1));

        if (Files.isRegularFile(path) || Files.isDirectory(path)){
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
     * Moves a file from the source path to the destination path.
     * <p>
     * This method expects a specific syntax for its input, where the second element in
     * the 'input' list represents the source file path, and the third element represents
     * the destination file path. It verifies the source and destination paths, checks
     * if the source path represents a valid file, and moves the file to the destination.
     *
     * @param input A list of input strings provided by the user. The first element
     *             (input.get(0)) is expected to contain the command "movefile," the
     *             second element (input.get(1)) should be the source file path, and
     *             the third element (input.get(2)) should be the destination file path.
     *             Syntax: moveFile [source file path] [destination file path]
     */
    public static void moveFile(List<String> input) {
        if (input.size() != 3){
            System.out.println("renameFile syntax error. syntax: renameFile [filePath] [new filePath]");
            return;
        }
        Path path = Paths.get(input.get(1));
        Path newPath = Paths.get(input.get(2));

        if (!Files.exists(path) || Files.isDirectory(path)){
            System.out.println("renameFile error. invalid path");
            return;
        }
        else if (Files.exists(newPath) || Files.isDirectory(newPath)) {
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
     * Deletes a file at the specified path.
     * <p>
     * This method expects a specific syntax for its input, where the second element in
     * the 'input' list represents the path of the file to be deleted. It verifies the
     * path, checks if it represents a valid file, and deletes the file if it exists.
     *
     * @param input A list of input strings provided by the user. The first element
     *             (input.get(0)) is expected to contain the command "delfile," and
     *             the second element (input.get(1)) should be a valid file path.
     *             Syntax: delFile [file path]
     */
    public static void delFile(List<String> input) {
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
     * Renames a file at the specified path with a new name.
     * <p>
     * This method expects a specific syntax for its input, where the second element in
     * the 'input' list represents the current file path, and the third element represents
     * the new name for the file. It verifies the path, checks if it represents a valid
     * file, and renames the file if it exists.
     *
     * @param input A list of input strings provided by the user. The first element
     *             (input.get(0)) is expected to contain the command "renamefile," the
     *             second element (input.get(1)) should be the current file path, and
     *             the third element (input.get(2)) should be the new name.
     *             Syntax: renameFile [current file path] [new name]
     */
    public static void renameFile(List<String> input) {
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
     * Creates a new file at the specified path.
     * <p>
     * This method expects a specific syntax for the 'input' list where the second element in
     * the list represents the desired filename to create. The method checks if
     * the 'input' list has exactly two elements and if the specified file does not already
     * exist or is not a directory. If these conditions are met it creates the file at
     * the specified path.
     *
     * @param input A list of input strings provided by the user. The first element
     *             (input.get(0)) is expected to contain the command "createdir," and
     *             the second element (input.get(1)) should be a valid directory path.
     *             Syntax: createDir [directory path]
     */
    public static void createFile(List<String> input) {
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
     * Copies a file from the source path to the destination path.
     * <p>
     * This method expects a specific syntax for its input, where the second element in
     * the 'input' list represents the source file path, and the third element represents
     * the destination file path. It verifies the source and destination paths, checks
     * if the source path represents a valid file, and copies the file to the destination.
     *
     * @param input A list of input strings provided by the user. The first element
     *             (input.get(0)) is expected to contain the command "copyfile," the
     *             second element (input.get(1)) should be the source file path, and
     *             the third element (input.get(2)) should be the destination file path.
     *             Syntax: copyfile [source file path] [destination file path]
     */
    public static void copyfile(List<String> input) {
        if (input.size() != 3){
            System.out.println("copyfile syntax error. syntax: copyfile [sourceFile] [destinationFile]");
            return;
        }
        Path sourcePath = Paths.get(input.get(1));
        Path destinationPath = Paths.get(input.get(2));

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
     * Displays the content of a text file at the specified path.
     * <p>
     * This method expects a specific syntax for its input, where the second element in
     * the 'input' list represents the path of the text file to be displayed. It verifies
     * the path, checks if it represents a valid text file, and displays its content if it
     * exists.
     *
     * @param input A list of input strings provided by the user. The first element
     *             (input.get(0)) is expected to contain the command "filedump," and
     *             the second element (input.get(1)) should be a valid text file path.
     *             Syntax: filedump [text file path]
     */
    public static void filedump(List<String> input) {
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
     * Displays error logs based on user input.
     * <p>
     * This method allows the user to retrieve error logs by providing an optional
     * error log number as input. If no input is provided or if the input is invalid,
     * it displays a list of available error logs with their corresponding numbers.
     *
     * @param input A list of input strings provided by the user. The first element
     *             (input.get(0)) is expected to contain the command "showerrlog,"
     *             and the optional second element (input.get(1)) should be a valid
     *             error log number.
     * @param errors A list of error log entries, where each entry is represented as
     *              an array containing two elements: the error input (errors[i][0]) and
     *              the error message (errors[i][1]).
     */
    public static void showerrlog(List<String> input, List<String[]> errors){
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
