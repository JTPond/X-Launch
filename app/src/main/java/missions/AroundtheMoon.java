package missions;

import rocket.rocket;
import rocket.satellite;
import org.jblas.DoubleMatrix;
/**
 * Created by jpond on 5/17/17.
 */

public class AroundtheMoon {

    private rocket satV;
    private satellite moon;

    public AroundtheMoon(){
        this.satV = new rocket("Saturn V");
        this.moon = new satellite(7.352e22,1737.1e3,new DoubleMatrix(new double[] {1500e3,1500e3,400e6}),1.022e3);
    }

    private DoubleMatrix gravity(){
        Double G = 6.67408e-11;
        Double Me = 6e24;
        Double Mm = this.moon.getMass();
        Double rmrp = this.satV.getPosition(2) + 6371e3;
        DoubleMatrix rRrM = this.satV.getPosition().sub(this.moon.getPosition());
        Double R2 = rRrM.dot(rRrM);
        DoubleMatrix ag = (new DoubleMatrix(new double[] {0.0,0.0,-1.0})).mul((G*Me)/Math.pow(rmrp,2.0));
        DoubleMatrix mG = (rRrM.div(Math.sqrt(R2))).mul((-G*Mm)/R2);
        return ag.add(mG);
    }

    private Boolean translate(Double dt){
        if (this.satV.getPosition(2) + this.satV.getVelocity(2) + 0.5*(this.satV.a().get(2) + this.gravity().get(2))*dt*dt >= 0.0){
            this.satV.setPosition(this.satV.getPosition().add(this.satV.getVelocity()).add((this.satV.a().add(this.gravity())).mul(0.5*dt*dt)));
            return true;
        }
        else{
            return false;
        }
    }

    public String timeStep(Double dt){
        DoubleMatrix a1 = this.satV.a().add(this.gravity());
        DoubleMatrix alpha1 = this.satV.alpha();
        if (this.translate(dt)){
            this.moon.translate(dt);
            if ((this.moon.getPosition().sub(this.satV.getPosition())).dot(this.moon.getPosition().sub(this.satV.getPosition())) < this.moon.getRadius()) {
                return "crashed";
            }
            this.satV.rotate(dt);
            this.satV.setVelocity(this.satV.getVelocity().add((a1.add(this.satV.a()).add(this.gravity())).mul(0.5*dt)));
            this.satV.setOmega(this.satV.getOmega().add((alpha1.add(this.satV.alpha())).mul(0.5*dt)));
            for (String boo:this.satV.getBooosters()) {
                if (! this.satV.getBooster(boo).update_fuel(dt)){
                    return "empty";
                }
                this.satV.update_MOI();
                return "success";
            }
        }
        return "grounded";
    }

    public void getAction(String password){
        Double thro = Double.valueOf(password.split(":")[1]);
        switch (password.split(":")[0]){
            case "igniteStabilizerXP": this.getSatV().getStabilizer("StabilizerXP").setThrottle(thro); break;
            case "igniteStabilizerXN": this.getSatV().getStabilizer("StabilizerXN").setThrottle(thro); break;
            case "igniteStabilizerYP": this.getSatV().getStabilizer("StabilizerYP").setThrottle(thro); break;
            case "igniteStabilizerYN": this.getSatV().getStabilizer("StabilizerYN").setThrottle(thro); break;

        }
    }

    public rocket getSatV() {
        return satV;
    }

    public void setSatV(rocket satV) {
        this.satV = satV;
    }

    public satellite getMoon() {
        return moon;
    }

    public void setMoon(satellite moon) {
        this.moon = moon;
    }

    public DoubleMatrix getGravity(){
        return this.gravity();
    }
}
