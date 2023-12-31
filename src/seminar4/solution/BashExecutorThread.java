package seminar4.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class BashExecutorThread implements Runnable{

    List<String> input;
    List<String[]> log;

    public BashExecutorThread(List<String> command, List<String[]> log) {
        this.input = command;
        this.log = log;
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

        } catch (IOException ioe) {
            System.out.println("Error");
            System.out.println(ioe.getMessage());
            this.log.add(new String[]{input.toString().replace("[", "").replace("]", ""),
                    ioe.getMessage()});
        } finally {
            if (bufferReader != null) {
                try {
                    bufferReader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    this.log.add(new String[]{input.toString().replace("[", "").replace("]", ""),
                            e.getMessage()});
                }
            }
        }
    }
}
