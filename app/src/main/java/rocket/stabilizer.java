package rocket;

import org.jblas.DoubleMatrix;

import static java.lang.Math.abs;

/**
 * Created by jpond on 5/16/17.
 */

public class stabilizer {

    private DoubleMatrix position;
    private DoubleMatrix orientation;
    private Double thrust;
    private Double throttle;

    public stabilizer(DoubleMatrix position, DoubleMatrix orientation, Double thrust){
        this.position = position;
        this.orientation = orientation;
        this.thrust = thrust;
        this.throttle = 0.0;
    }

    public DoubleMatrix getPosition() {
        return position;
    }

    public Double getPosition(Integer i) {
        return position.get(i);
    }

    public void setPosition(DoubleMatrix position) {
        this.position = position;
    }

    public DoubleMatrix getOrientation() {
        return orientation;
    }

    public Double getOrientation(Integer i) {
        return orientation.get(i);
    }

    public void setOrientation(DoubleMatrix orientation) {
        this.orientation = orientation;
    }

    public Double getThrust() {
        return thrust;
    }

    public Double getThrottle() {
        return throttle;
    }

    public void setThrottle(Double throttle) {
        this.throttle = throttle;
    }
}
