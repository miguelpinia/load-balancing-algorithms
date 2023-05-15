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

    public static void main(String[] args) {
        String header
                = """
                  =====================================
                  =====================================
                  """;
        File f = new File("config.json");
        JSONObject props = readProperties(f);
        if (props == null) {
            System.out.println("Error: Can't read properties file. Try again.");
            return;
        }
        var footer = """
                     =================================
                     =        Properties read        =
                     =================================
                     """;
        System.out.println(header + props.toString(2) + footer);
        App battery = new App(props);
        battery.compareAlgs();
    }

    private static JSONObject readProperties(File f) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(f.getName())), StandardCharsets.UTF_8.displayName());
            JSONObject obj = new JSONObject(json);
            return obj;
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "ERROR: Can't read properties file", ex);
        }
        return null;
    }

}
