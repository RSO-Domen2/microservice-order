package si.fri.rso.domen2.order.lib.RadarShema;

public class RadarResponseGeodesic {
    public RadarResponseValue distance;

    public RadarResponseGeodesic() {
        this.distance = new RadarResponseValue();
    }

    public RadarResponseGeodesic(RadarResponseValue distance) {
        this.distance = distance;
    }
}
