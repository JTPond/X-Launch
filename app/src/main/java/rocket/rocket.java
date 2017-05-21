package rocket;

import org.jblas.DoubleMatrix;
import java.util.HashMap;
import java.util.Set;

import rocket.booster;
import rocket.stabilizer;

/**
 * Created by jpond on 5/16/17.
 */

public class rocket {

    private DoubleMatrix position;
    private DoubleMatrix orientation;
    private DoubleMatrix velocity;
    private DoubleMatrix omega;
    private Double mass;
    private Double length;
    private Double radius;
    private HashMap<String,stabilizer> stabilizers = new HashMap<String,stabilizer>();
    private HashMap<String,booster> boosters = new HashMap<String,booster>();
    private DoubleMatrix MOI;

    public rocket(DoubleMatrix position, DoubleMatrix orientation, DoubleMatrix velocity, DoubleMatrix omega, Double mass, Double length, Double radius) {
        this.position = position;
        this.orientation = orientation;
        this.velocity = velocity;
        this.omega = omega;
        this.mass = mass;
        this.length = length;
        this.radius = radius;
        this.MOI = new DoubleMatrix(3,3);
        this.update_MOI();
    }

    public rocket(String name){
        if (name == "Saturn V"){
            this.position = new DoubleMatrix(new double[] {0.0,0.0,5.0});
            this.orientation = new DoubleMatrix(new double[] {0.0,0.0,1.0});
            this.velocity = new DoubleMatrix(new double[] {0.0,0.0,0.0});
            this.omega = new DoubleMatrix(new double[] {0.0,0.0,0.0});
            this.mass = 100.0;
            this.length = 10.0;
            this.radius = 0.5;
            this.add_booster("Booster1",new DoubleMatrix(new double[] {0.0,0.0,-5.0}),60.0,0.45,20000.0,1000.0,5000000.0);
            this.add_stabilizer("StabilizerXP",new DoubleMatrix(new double[] {0.5,0.0,4.5}),new DoubleMatrix(new double[] {1.0,0.0,0.0}),1100.0);
            this.add_stabilizer("StabilizerXN",new DoubleMatrix(new double[] {-0.5,0.0,4.5}),new DoubleMatrix(new double[] {-1.0,0.0,0.0}),1100.0);
            this.add_stabilizer("StabilizerYP",new DoubleMatrix(new double[] {0.0,0.5,4.5}),new DoubleMatrix(new double[] {0.0,0.1,0.0}),1100.0);
            this.add_stabilizer("StabilizerYN",new DoubleMatrix(new double[] {0.0,-0.5,4.5}),new DoubleMatrix(new double[] {0.0,-1.0,0.0}),1100.0);
            this.MOI = new DoubleMatrix(3,3);
            this.update_MOI();
        }
        else{
            this.position = new DoubleMatrix(new double[] {0.0,0.0,5.0});
            this.orientation = new DoubleMatrix(new double[] {0.0,0.0,1.0});
            this.velocity = new DoubleMatrix(new double[] {0.0,0.0,0.0});
            this.omega = new DoubleMatrix(new double[] {0.0,0.0,0.0});
            this.mass = 100.0;
            this.length = 10.0;
            this.radius = 0.5;
            this.add_booster("Booster1",new DoubleMatrix(new double[] {0.0,0.0,-5.0}),60.0,0.45,20000.0,1000.0,5000000.0);
            this.add_stabilizer("StabilizerXP",new DoubleMatrix(new double[] {0.5,0.0,4.5}),new DoubleMatrix(new double[] {1.0,0.0,0.0}),1100.0);
            this.add_stabilizer("StabilizerXN",new DoubleMatrix(new double[] {-0.5,0.0,4.5}),new DoubleMatrix(new double[] {-1.0,0.0,0.0}),1100.0);
            this.add_stabilizer("StabilizerYP",new DoubleMatrix(new double[] {0.0,0.5,4.5}),new DoubleMatrix(new double[] {0.0,0.1,0.0}),1100.0);
            this.add_stabilizer("StabilizerYN",new DoubleMatrix(new double[] {0.0,-0.5,4.5}),new DoubleMatrix(new double[] {0.0,-1.0,0.0}),1100.0);
            this.MOI = new DoubleMatrix(3,3);
            this.update_MOI();
        }
    }

    public void update_MOI(){
        this.MOI.zeros(3,3);
        this.MOI.put(0,0, (1.0/12.0)*this.m()*(3.0*Math.pow(this.radius,2.0) + Math.pow(this.length,2.0)));
        this.MOI.put(1,1, (1.0/12.0)*this.m()*(3.0*Math.pow(this.radius,2.0) + Math.pow(this.length,2.0)));
        this.MOI.put(2,2, (1.0/12.0)*this.m()*(Math.pow(this.radius,2.0)));
    }

    public Double m(){
        Double ret = 0.0;
        for (String key:this.boosters.keySet()) {
            ret = ret + this.boosters.get(key).getFuel();
        }
        return this.mass + ret;
    }

    public void add_booster(String name,DoubleMatrix position, Double length, Double radius, Double cStar, Double fRho, Double thrust){
        this.boosters.put(name, new booster(position, length, radius, cStar, fRho, thrust));
    }

    public void add_stabilizer(String name, DoubleMatrix position, DoubleMatrix orientation, Double thrust){
        this.stabilizers.put(name, new stabilizer(position, orientation, thrust));
    }

    public DoubleMatrix alpha(){
        DoubleMatrix tor = new DoubleMatrix(3);
        for (String stab:this.stabilizers.keySet()) {
            tor.put(0, tor.get(0) + (this.stabilizers.get(stab).getPosition(2))*this.stabilizers.get(stab).getThrust()*this.stabilizers.get(stab).getThrottle()*this.stabilizers.get(stab).getOrientation(1));
            tor.put(1, tor.get(1) -(this.stabilizers.get(stab).getPosition(2))*this.stabilizers.get(stab).getThrust()*this.stabilizers.get(stab).getThrottle()*this.stabilizers.get(stab).getOrientation(0));
        }
        return tor.div(new DoubleMatrix(new double[]{this.MOI.get(0,0),this.MOI.get(1,1),this.MOI.get(2,2)}));
    }

    public DoubleMatrix a(){
        DoubleMatrix ret = new DoubleMatrix(3);
        //ret.zeros(3);
        for (String boo:this.boosters.keySet()) {
            ret = ret.add((this.orientation.mul(this.boosters.get(boo).getOn()*this.boosters.get(boo).getThrust())).div(this.m()));
        }
        return ret;
    }

    public void rotate(Double dt){
        for (Integer j = 0; j < 3; j++){
            Integer i = null;
            Integer k = null;
            switch (j){
                case 0: i = 1; k = 2; break;
                case 1: i = 2; k= 0; break;
                case 2: i = 0; k = 1; break;
            }
            if (this.omega.get(j)*dt + 0.5*this.alpha().get(j)*dt*dt > 0.0) {
                if (this.orientation.get(i) > 0.0 && this.orientation.get(k) >= 0.0){
                    this.orientation.put(i,this.orientation.get(i) - (this.omega.get(j)*dt + 0.5*this.alpha().get(j)*dt*dt));
                    this.orientation.put(k,this.orientation.get(k) + (this.omega.get(j)*dt + 0.5*this.alpha().get(j)*dt*dt));
                }
                else if (this.orientation.get(i) <= 0.0 && this.orientation.get(k) > 0.0){
                    this.orientation.put(i,this.orientation.get(i) - (this.omega.get(j)*dt + 0.5*this.alpha().get(j)*dt*dt));
                    this.orientation.put(k,this.orientation.get(k) - (this.omega.get(j)*dt + 0.5*this.alpha().get(j)*dt*dt));
                }
                else if (this.orientation.get(i) < 0.0 && this.orientation.get(k) <= 0.0){
                    this.orientation.put(i,this.orientation.get(i) + (this.omega.get(j)*dt + 0.5*this.alpha().get(j)*dt*dt));
                    this.orientation.put(k,this.orientation.get(k) - (this.omega.get(j)*dt + 0.5*this.alpha().get(j)*dt*dt));
                }
                else if (this.orientation.get(i) >= 0.0 && this.orientation.get(k) < 0.0){
                    this.orientation.put(i,this.orientation.get(i) + (this.omega.get(j)*dt + 0.5*this.alpha().get(j)*dt*dt));
                    this.orientation.put(k,this.orientation.get(k) + (this.omega.get(j)*dt + 0.5*this.alpha().get(j)*dt*dt));
                }
            }
            else if (this.omega.get(j)*dt + 0.5*this.alpha().get(j)*dt*dt < 0.0) {
                if (this.orientation.get(i) >= 0.0 && this.orientation.get(k) > 0.0) {
                    this.orientation.put(i, this.orientation.get(i) + (this.omega.get(j) * dt + 0.5 * this.alpha().get(j) * dt * dt));
                    this.orientation.put(k, this.orientation.get(k) - (this.omega.get(j) * dt + 0.5 * this.alpha().get(j) * dt * dt));
                } else if (this.orientation.get(i) < 0.0 && this.orientation.get(k) >= 0.0) {
                    this.orientation.put(i, this.orientation.get(i) + (this.omega.get(j) * dt + 0.5 * this.alpha().get(j) * dt * dt));
                    this.orientation.put(k, this.orientation.get(k) + (this.omega.get(j) * dt + 0.5 * this.alpha().get(j) * dt * dt));
                } else if (this.orientation.get(i) <= 0.0 && this.orientation.get(k) < 0.0) {
                    this.orientation.put(i, this.orientation.get(i) - (this.omega.get(j) * dt + 0.5 * this.alpha().get(j) * dt * dt));
                    this.orientation.put(k, this.orientation.get(k) + (this.omega.get(j) * dt + 0.5 * this.alpha().get(j) * dt * dt));
                } else if (this.orientation.get(i) > 0.0 && this.orientation.get(k) <= 0.0) {
                    this.orientation.put(i, this.orientation.get(i) - (this.omega.get(j) * dt + 0.5 * this.alpha().get(j) * dt * dt));
                    this.orientation.put(k, this.orientation.get(k) - (this.omega.get(j) * dt + 0.5 * this.alpha().get(j) * dt * dt));
                }
            }
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

    public DoubleMatrix getOrientation() {
        return orientation;
    }

    public Double getOrientation(Integer i) {
        return orientation.get(i);
    }

    public void setOrientation(DoubleMatrix orientation) {
        this.orientation = orientation;
    }

    public DoubleMatrix getVelocity() {
        return velocity;
    }

    public Double getVelocity(Integer i) {
        return velocity.get(i);
    }

    public void setVelocity(DoubleMatrix velocity) {
        this.velocity = velocity;
    }

    public DoubleMatrix getOmega() {
        return omega;
    }

    public Double getOmega(Integer i) {
        return omega.get(i);
    }

    public void setOmega(DoubleMatrix omega) {
        this.omega = omega;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
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

    public stabilizer getStabilizer(String name) {
        return this.stabilizers.get(name);
    }

    public Set<String> getStabilizers(){
        return this.stabilizers.keySet();
    }

    public booster getBooster(String name) {
        return this.boosters.get(name);
    }

    public Set<String> getBooosters(){
        return this.boosters.keySet();
    }

    public DoubleMatrix getMOI() {
        return MOI;
    }

    public void setMOI(DoubleMatrix MOI) {
        this.MOI = MOI;
    }
}
