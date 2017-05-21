package rocket;

import org.jblas.DoubleMatrix;

/**
 * Created by jpond on 5/17/17.
 */

public class satellite {

    private Double mass;
    private Double radius;
    private DoubleMatrix position;
    private Double orbital_velocity;


    public satellite(Double mass, Double radius, DoubleMatrix position, Double orbital_velocity) {
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.orbital_velocity = orbital_velocity;
    }

    public void translate(Double dt){
        Double size = Math.PI*this.position.get(2);
        if (this.position.get(1) + this.orbital_velocity*dt > size){
            this.position.put(1, this.position.get(1) + this.orbital_velocity*dt - 2.0*size);
        }
        else{
            this.position.put(1, this.position.get(1) + this.orbital_velocity*dt);
        }
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public DoubleMatrix getPosition() {
        return position;
    }

    public void setPosition(DoubleMatrix position) {
        this.position = position;
    }

    public Double getOrbital_velocity() {
        return orbital_velocity;
    }

    public void setOrbital_velocity(Double orbital_velocity) {
        this.orbital_velocity = orbital_velocity;
    }
}
