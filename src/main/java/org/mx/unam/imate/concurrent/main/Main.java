package org.mx.unam.imate.concurrent.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.experiments.TestBattery;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.StepSpanningTreeType;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

public class Main {

    public static void main(String[] args) {
        File f = new File("properties.props");
        Map<String, String> props = readProperties(f);
        System.out.println(props);
        String data[] = props.get("algorithms").split(";");
        List<AlgorithmsType> types = new ArrayList<>();
        for (String stringType : data) {
            types.add(AlgorithmsType.valueOf(stringType));
        }
        TestBattery battery = new TestBattery((GraphType.valueOf(props.get("graphType"))),
                Integer.valueOf(props.get("vertexSize")),
                StepSpanningTreeType.valueOf(props.get("stepSpanningType")),
                Integer.valueOf(props.get("iterations")),
                types);
        battery.compareAlgs();
    }

    private static Map<String, String> readProperties(File f) {
        Map<String, String> props = new HashMap<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String data[] = line.split(":");
                props.put(data[0].trim(), data[1].trim());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado, cerrando el programa");
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return props;

    }

}
