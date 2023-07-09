package phd.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import phd.ws.experiments.App;

public class Main {

    private static void callExperiments(JSONObject properties) {
        App application = new App(properties);
        application.executeExperiments();
    }

    public static void main(String[] args) {
        // TODO: Implement a version for choose any of experiments (MAPA 2023-06-12)
        // Current applications:
        // - Zero cost experiments
        // - Spanning tree
        //   - Counting repeated work
        //   - Statistical measurement
        // - SAT
        //   - Counting repeated work
        //   - Statistical measurement

        // First read config file and pass it as json structure to APP
        if (args.length > 0) {
            String option = args[0].strip();
            JSONObject properties;
            if ("--file".equals(option)) {
                String fileName = args[1];
                properties = readProperties(new File(fileName));
                callExperiments(properties);
            } else {
                System.err.println("Error: Can't read properties file. Try again.");
                System.exit(0);
            }
        } else {
            JSONObject properties = readProperties(new File("config.json"));
            callExperiments(properties);
        }
    }

    private static JSONObject readProperties(File f) {
        try {
            var header
                    = """
                  =====================================
                  =====================================
                  """;
            var footer = """
                     =================================
                     =        Properties read        =
                     =================================
                     """;

            String json = new String(Files.readAllBytes(Paths.get(f.getName())), StandardCharsets.UTF_8.displayName());
            JSONObject obj = new JSONObject(json);
            System.out.println(header + obj.toString(2) + footer);
            return obj;
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "ERROR: Can't read properties file", ex);
        }
        return null;
    }

}
