// version: 1.1
// made by Vitali Shulha
// 4-Jan-2019
package model;

import java.util.List;

public class Airport {

    private final List<? extends Plane> planes;

    public Airport(List<? extends Plane> planes) {
        this.planes = planes;
    }

    public List<? extends Plane> getPlanes() {
        return planes;
    }

    @Override
    public String toString() {
        return "model.Airport{" +
                "Planes=" + planes.toString() +
                '}';
    }
}
