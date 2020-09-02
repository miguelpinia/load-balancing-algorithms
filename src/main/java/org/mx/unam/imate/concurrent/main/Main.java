package org.mx.unam.imate.concurrent.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.experiments.TestBattery;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.stepSpanningTree.StepSpanningTreeType;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphType;

public class Main {

    public static void main(String[] args) {
        File f = new File("config.json");
        Map<String, Object> props = readJsonFile(f);
        System.out.println(props);
        JSONArray algs = (JSONArray) props.get("algorithms");
        List<AlgorithmsType> types = new ArrayList<>();
        for (Object alg : algs) {
            types.add(AlgorithmsType.valueOf((String) alg));
        }
        System.out.println("types: " + types);

        GraphType type = GraphType.valueOf((String) props.get("graphType"));
        int vertexSize = (Integer) props.get("vertexSize");
        StepSpanningTreeType stepType = StepSpanningTreeType.valueOf((String) props.get("stepSpanningTree"));
        int iterations = (Integer) props.get("iterations");
        boolean directed = (Boolean) props.get("directed");
        boolean stealTime = (Boolean) props.get("stealTime");
        boolean putSteals = (Boolean) props.get("putSteals");
        TestBattery battery = new TestBattery(type, vertexSize, stepType, iterations, types, directed, stealTime, putSteals);
        battery.compareAlgs();
    }

    private static Map<String, Object> readJsonFile(File f) {
        Map<String, Object> props = new HashMap<>();
        try {
            String json = new String(Files.readAllBytes(Paths.get(f.getName())));
            JSONObject obj = new JSONObject(json);
            props.put("algorithms", obj.getJSONArray("algorithms"));
            props.put("graphType", obj.getString("graphType"));
            props.put("vertexSize", obj.getInt("vertexSize"));
            props.put("stepSpanningTree", obj.getString("stepSpanningType"));
            props.put("iterations", obj.getInt("iterations"));
            props.put("directed", obj.getBoolean("directed"));
            props.put("stealTime", obj.getBoolean("stealTime"));
            props.put("putSteals", obj.getBoolean("putSteals"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return props;
    }

}
