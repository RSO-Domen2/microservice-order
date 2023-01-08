package si.fri.rso.domen2.order.lib.RadarShema;

public class RadarResponseRoutes {
    public RadarResponseGeodesic geodesic;
    public RadarResponseTransport car;
    public RadarResponseTransport bike;
    public RadarResponseTransport foot;

    public RadarResponseRoutes() {
        this.geodesic = new RadarResponseGeodesic();
        this.car = new RadarResponseTransport();
        this.bike = new RadarResponseTransport();
        this.foot = new RadarResponseTransport();
    }

    public RadarResponseRoutes(RadarResponseGeodesic geodesic, RadarResponseTransport car, RadarResponseTransport bike, RadarResponseTransport foot) {
        this.geodesic = geodesic;
        this.car = car;
        this.bike = bike;
        this.foot = foot;
    }
}