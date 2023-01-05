package si.fri.rso.domen2.order.lib;

import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseMeta;
import si.fri.rso.domen2.order.lib.RadarShema.RadarResponseRoutes;

public class RadarResponseDistance {
    public RadarResponseMeta meta;
    public RadarResponseRoutes routes;

    public RadarResponseDistance() {
        this.meta = null;
        this.routes =  null;
    }

    public RadarResponseDistance(RadarResponseMeta meta, RadarResponseRoutes routes) {
        this.meta = meta;
        this.routes = routes;
    }
}





