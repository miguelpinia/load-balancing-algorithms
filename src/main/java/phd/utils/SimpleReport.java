package phd.utils;

/**
 *
 * @author miguel
 */
public class SimpleReport {

    private int takes;
    private int puts;
    private int steals;

    public SimpleReport() {
        takes = 0;
        puts = 0;
        steals = 0;
    }

    public void incTakes() {
        takes++;
    }

    public void incPuts() {
        puts++;
    }

    public void incSteals() {
        steals++;
    }

    public int getTakes() {
        return takes;
    }

    public int getPuts() {
        return puts;
    }

    public int getSteals() {
        return steals;
    }

    public void setPuts(int puts) {
        this.puts = puts;
    }

    public void setTakes(int takes) {
        this.takes = takes;
    }

    public void setSteals(int steals) {
        this.steals = steals;
    }

}
