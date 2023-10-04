package seminar4.solutionTest;

import org.junit.jupiter.api.*;
import seminar4.solution.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainTest {
    String userPath;

    @BeforeEach
    void setUp() throws IOException {
        userPath = new File(".").getCanonicalPath();
        if (!userPath.contains("src")){
            userPath += "\\src";
        }
    }

    @AfterEach
    void tearDown() {
    }

    // -------------------------------------------------

    @Test
    @Order(1)
    void createDir() {
        String path = userPath + "\\seminar4\\files\\testDir";
        Main.createDir(Arrays.asList(" ", path));

        boolean testDirExist = Files.isDirectory(Paths.get(path));

        assertTrue(testDirExist);
    }

    @Test
    @Order(2)
    void renameDir() {
        String path = userPath + "\\seminar4\\files\\testDir";
        String newName = "realTestDir";
        Main.renameDir(Arrays.asList(" ", path, newName));

        String newPath = userPath + "\\seminar4\\files\\" + newName;
        boolean testDirExist = Files.isDirectory(Paths.get(newPath));

        assertTrue(testDirExist);
    }

    @Test
    @Order(3)
    void createFile() {
        String path = userPath + "\\seminar4\\files\\realTestDir\\test.txt";
        Main.createFile(Arrays.asList(" ", path));

        boolean testFileExist = Files.isRegularFile(Paths.get(path));

        assertTrue(testFileExist);
    }

    @Test
    @Order(4)
    void renameFile() {
        String path = userPath + "\\seminar4\\files\\realTestDir\\test.txt";
        String newName = "realTest.txt";
        Main.renameFile(Arrays.asList(" ", path, newName));

        String newPath = userPath + "\\seminar4\\files\\realTestDir\\" + newName;
        boolean testFileExist = Files.isRegularFile(Paths.get(newPath));

        assertTrue(testFileExist);
    }

    @Test
    @Order(5)
    void moveFile() {
        String path = userPath + "\\seminar4\\files\\realTestDir\\test";
        Main.createDir(Arrays.asList(" ", path));

        path = userPath + "\\seminar4\\files\\realTestDir\\realTest.txt";
        String newPath = userPath + "\\seminar4\\files\\realTestDir\\test\\realTest.txt";
        Main.moveFile(Arrays.asList(" ", path, newPath));

        boolean testFileExist = Files.isRegularFile(Paths.get(newPath));
        boolean testFileNotExist = !Files.isRegularFile(Paths.get(path));
        assertTrue(testFileExist);
        assertTrue(testFileNotExist);
    }


    @Test
    @Order(6)
    void copyfile() {
        String pathToCopy = userPath + "\\seminar4\\files\\test.txt";
        String path = userPath + "\\seminar4\\files\\realTestDir\\test\\realTest.txt";

        InputStream savedSystemIn = System.in;
        System.setIn(new ByteArrayInputStream("y".getBytes()));

        Main.scanner = new Scanner(System.in);
        Main.copyfile(Arrays.asList(" ", pathToCopy, path));

        // Reset System.in to the original input stream
        System.setIn(savedSystemIn);

        List<String> firstFileContent;
        List<String> secondFileContent;

        try {
            firstFileContent = Files.readAllLines(Paths.get(pathToCopy));
            secondFileContent = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean isSame = firstFileContent.equals(secondFileContent);

        if (secondFileContent.isEmpty()){
            isSame = false;
        }

        assertTrue(isSame);
    }

    @Test
    @Order(7)
    void delFile() {
        String path = userPath + "\\seminar4\\files\\realTestDir\\test\\realTest.txt";

        Main.delFile(Arrays.asList(" ", path));

        boolean testFileNotExist = !Files.isRegularFile(Paths.get(path));

        assertTrue(testFileNotExist);
    }

    @Test
    @Order(8)
    void delDir() {
        String path = userPath + "\\seminar4\\files\\realTestDir";

        Main.delDir(Arrays.asList(" ", path));

        boolean testDirNotExist = !Files.isDirectory(Paths.get(path));

        assertTrue(testDirNotExist);
    }


}