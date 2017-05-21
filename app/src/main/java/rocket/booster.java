package rocket;


import org.jblas.DoubleMatrix;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

/**
 * Created by jpond on 5/16/17.
 */

public class booster {

    private DoubleMatrix position;
    private Double length;
    private Double radius;
    private Double cStar;
    private Double fRho;
    private Double thrust;
    private Double on;
    private Double dfdt;
    private Double fuel;

    public booster(DoubleMatrix position, Double length, Double radius, Double cStar, Double fRho, Double thrust) {
        this.position = position;
        this.length = length;
        this.radius = radius;
        this.cStar = cStar;
        this.fRho = fRho;
        this.thrust = thrust;
        this.on = 0.0;
        this.dfdt = this.thrust/this.cStar;
        this.fuel = Math.PI*Math.pow(this.radius,2.0)*this.length*this.fRho;
    }

    public void ignition(){
        this.on = abs(this.on - 1.0);
    }

    public Boolean update_fuel(Double dt){
        if (this.fuel > 0.0){
            this.fuel = this.fuel - this.dfdt*dt;
            return true;
        }
        else{
            this.thrust = 0.0;
            return false;
        }
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

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getcStar() {
        return cStar;
    }

    public void setcStar(Double cStar) {
        this.cStar = cStar;
    }

    public Double getfRho() {
        return fRho;
    }

    public void setfRho(Double fRho) {
        this.fRho = fRho;
    }

    public Double getThrust() {
        return thrust;
    }

    public void setThrust(Double thrust) {
        this.thrust = thrust;
    }

    public Double getOn() {
        return on;
    }

    public Double getFuel() {
        return fuel;
    }

    public void setFuel(Double fuel) {
        this.fuel = fuel;
    }
}
