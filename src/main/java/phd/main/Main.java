package phd.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import phd.utils.WorkStealingUtils;
import phd.ws.experiments.sat.concurrent.BruteSolver;

public class Main {

    public static void main(String[] args) {
        //  TODO: Implement a version for choose any of experiments (MAPA 2023-06-12)
//        String header
//                = """
//                  =====================================
//                  =====================================
//                  """;
//        File f = new File("config.json");
//        JSONObject props = readProperties(f);
//        if (props == null) {
//            System.out.println("Error: Can't read properties file. Try again.");
//            return;
//        }
//        var footer = """
//                     =================================
//                     =        Properties read        =
//                     =================================
//                     """;
//        System.out.println(header + props.toString(2) + footer);
//        App battery = new App(props);
//        battery.compareAlgs();
//        String[][] formula = {{"-p", "q", "-r", "s"}, {"-q", "-r", "s"}, {"r"}, {"-p", "-s"}, {"-p", "r"}};
//        String[][] formula2 = {{"a1"}, {"a2", "-a3"}, {"-a3"}, {"-a2", "a4"}};
//        VariableFactory varFactory = new VariableFactory();
//        LiteralFactory literalFactory = new LiteralFactory(varFactory);
//        Formula f = new Formula(formula2, literalFactory);
//        // expected output:
//        //  (¬p v q v ¬r v s) ^ (¬q v ¬r v s) ^ (r) ^ (¬p v ¬s) ^ (¬p v r)
//        System.out.println(f.getLiteralsLexi());
//        DPLL.enableLog();
//        DPLL.solve(f);
//        try {
//            String configFile = "config_sat.json";
//            JSONObject result = experiment(configFile);
//            SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
//            String time = format.format(new Date());
//            String title = String.format("experiment-sat-result-%s-stats.json", time);
//            WorkStealingUtils.saveJsonObjectToFile(result, title);
//            System.out.println("Finish evaluation");
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Solver.class.getName()).log(Level.SEVERE, null, ex);
//        }

        String configFile = "config_sat.json";
        JSONObject properties = BruteSolver.readProperties(configFile);
        int step = properties.getInt("step");
//        JSONObject result = Statistics.evaluationSATST(configFile);
        JSONObject result = BruteSolver.experimentMeasurements(configFile);
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        String time = format.format(new Date());
//        String title = String.format("experiment-brute-sat-st-%s-%d.json", time, structSize);
        String title = String.format("experiment-brute-sat-measurements-%d-%s.json", step, time);
        WorkStealingUtils.saveJsonObjectToFile(result, title);
        System.out.println("Finish Evaluation");
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
