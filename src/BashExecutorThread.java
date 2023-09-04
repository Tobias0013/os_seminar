import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BashExecutorThread implements Runnable{

    List<String> input = new ArrayList<>();
    ErrorLog log;

    public BashExecutorThread(String command, ErrorLog log) {
        this.input = Arrays.asList(command.split(" "));
        this.log = log;
    }

    public BashExecutorThread(List<String> input) {
        this.input = input;
    }

    public List<String> getInput() {
        return input;
    }

    public void setInput(List<String> input) {
        this.input = input;
    }

    @Override
    public void run() {
        ProcessBuilder processBuilder = new ProcessBuilder(input);

        BufferedReader bufferReader = null;

        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            bufferReader = new BufferedReader(isr);

            String line;
            while ((line = bufferReader.readLine()) != null) {
                System.out.println(line );
            }
            bufferReader.close();

        } catch (java.io.IOException ioe) {
            System.err.println("Error");
            System.err.println(ioe);
            this.log.addError(ioe.getMessage());
        } finally {
            if (bufferReader != null) {
                try {
                    bufferReader.close();
                } catch (IOException e) {
                    System.out.println(e);
                    this.log.addError(e.getMessage());
                }
            }
        }
    }
}
