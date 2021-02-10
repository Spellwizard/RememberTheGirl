
import java.awt.*;

public class Projectile extends MovingObject {

            private boolean isExplode;

            private double distanceTraveled;

            private double MaxDistance;

            private double maxSpeed;

            private int damage = 1;

    /**
     *
     * @param posX
     * @param posY
     * @param objWidth
     * @param objHeight
     * @param c
     * @param defaultHSpeed
     * @param defaultVSpeed
     * @param isExplode
     * @param maxDistance
     * @param maxSpeed
     * @param damage
     */
            public Projectile(double posX, double posY, int objWidth, int objHeight, Color c, double defaultHSpeed, double defaultVSpeed,
                              boolean isExplode, int maxDistance, double maxSpeed, int damage) {
                super(posX, posY, objWidth, objHeight, defaultHSpeed, defaultVSpeed);
                super.setObjColour(c);
                this.isExplode = isExplode;
                this.MaxDistance = maxDistance;
                this.maxSpeed = maxSpeed;
                this.damage = damage;
            }


    protected boolean exceededMaxDistance(){
                return (distanceTraveled>MaxDistance);
    }

            public boolean isExplode() {
                return isExplode;
            }

            public void setExplode(boolean explode) {
                isExplode = explode;
            }

            public double getDistanceTraveled() {
                return distanceTraveled;
            }

            public void setDistanceTraveled(double distanceTraveled) {
                this.distanceTraveled = distanceTraveled;
            }

            public double getMaxDistance() {
                return MaxDistance;
            }

            public void setMaxDistance(double maxDistance) {
                MaxDistance = maxDistance;
            }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
