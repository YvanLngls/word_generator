import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private String path;

    public Parser(String filePath) {
        this.path = filePath;
    }

    public String[] parse() throws Exception {
        BufferedReader br = createBufferedReader();
        String line;
        ArrayList<String> backTable = new ArrayList<>();
        while ((line = br.readLine()) != null) backTable.add(line);
        br.close();
        return backTable.toArray(new String[0]);
    }

    private BufferedReader createBufferedReader() throws FileNotFoundException {
        Reader reader = new FileReader(path);
        return new BufferedReader(reader);
    }

    private int parseInt(String value) {
        return Integer.parseInt(value);
    }

    private double parseDouble(String value) {
        return Double.parseDouble(value);
    }
}
