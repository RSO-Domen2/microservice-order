package si.fri.rso.domen2.order.lib.RadarShema;

public class RadarResponseValue {
    public double value;
    public String text;

    public RadarResponseValue() {
        this.value = -1;
        this.text = null;
    }

    public RadarResponseValue(double distance, String text) {
        this.value = distance;
        this.text = text;
    }
}
