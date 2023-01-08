package si.fri.rso.domen2.order.lib.RadarShema;

public class RadarResponseTransport {
    public RadarResponseValue distance;
    public RadarResponseValue duration;

    public RadarResponseTransport() {
        this.distance = new RadarResponseValue();
        this.duration = new RadarResponseValue();
    }

    public RadarResponseTransport(RadarResponseValue distance, RadarResponseValue duration) {
        this.distance = distance;
        this.duration = duration;
    }
}
