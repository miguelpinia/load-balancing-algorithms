package org.mx.unam.imate.concurrent.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.mx.unam.imate.concurrent.algorithms.experiments.App;

public class Main {

    public static void main(String[] args) {
        String header
                = "=====================================\n"
                + "=         reading properties        =\n"
                + "=====================================\n";
        File f = new File("config.json");
        JSONObject props = readProperties(f);
        System.out.println(header + props.toString(2));
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
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "ERROR: Can't read properties file");
        return null;
    }

}
