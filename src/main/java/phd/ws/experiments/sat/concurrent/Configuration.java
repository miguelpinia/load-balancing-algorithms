/*
 */
package phd.ws.experiments.sat.concurrent;

import phd.ws.gen.WSType;

/**
 *
 * @author miguel
 */
public class Configuration {

    private String datafile;
    private WSType type;
    private int structSize;
    private int processors;

    public Configuration(String datafile, WSType type, int structSize, int processors) {
        this.datafile = datafile;
        this.type = type;
        this.structSize = structSize;
        this.processors = processors;
    }

    public String getDatafile() {
        return datafile;
    }

    public void setDatafile(String datafile) {
        this.datafile = datafile;
    }

    public WSType getType() {
        return type;
    }

    public void setType(WSType type) {
        this.type = type;
    }

    public int getStructSize() {
        return structSize;
    }

    public void setStructSize(int structSize) {
        this.structSize = structSize;
    }

    public int getProcessors() {
        return processors;
    }

    public void setProcessors(int processors) {
        this.processors = processors;
    }

}
