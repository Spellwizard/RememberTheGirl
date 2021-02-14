
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LongRangedWeapon{

    //The click of the mouse button that should be linked to activating the weapon
    private int MouseClick;
    //The button that should indicate that this weapon should be the selected weapon
    private int selectionButton;

    //isPressed is used to determine if the button is still being pressed and thus a continuing fire should occur
    private boolean isPressed = false;

    //The firecooldown is the current position and the TOTALFIRECOOLDOWN is the original set position
    //As such if firecooldown is = 0 then the fire should occur again and reset to TOTALFIRECOOLDOWN to again iterate down
    private int FireCooldown;
    private int TOTALFIRECOOLDOWN;

    //The example projectile settings that is used as the starting point of the projectiles
    private Projectile defaultProjectile;
    //the count of the shot per fire eg if 3 then fire 3 shots
    private int shotCount;
    //a number from 0 - 100 that is directly used to allow for offset of the projectile accuracy
    //later on i may implent an accuracy range which would allow for the below accuracy but start to have a major drop off in accuracy after a specific range
    private double accuracy;


    //the image to display on the player's weapon belt
    private BufferedImage weaponImage;
    //the background color to display on the player's wepaon belt
    private Color color;



    /**
     *
     * @param mouseClick The click of the mouse button that should be linked to activating the weapon
     * @param selectionButton The button that should indicate that this weapon should be the selected weapon
     *isPressed is used to determine if the button is still being pressed and thus a continuing fire should occur
     * The firecooldown is the current position and the TOTALFIRECOOLDOWN is the original set position
     * @param TOTALFIRECOOLDOWN As such if firecooldown is = 0 then the fire should occur again and reset to TOTALFIRECOOLDOWN to again iterate down
     * @param defaultProjectile The example projectile settings that is used as the starting point of the projectiles
     */
    /**
     *
     * @param mouseClick
     * @param selectionButton
     * @param TOTALFIRECOOLDOWN
     * @param defaultProjectile
     * @param shotCount
     * @param accuracy
     * @param weaponImage
     * @param color
     */
    public LongRangedWeapon(int mouseClick, int selectionButton,int TOTALFIRECOOLDOWN, Projectile defaultProjectile,
                            int shotCount, double accuracy, BufferedImage weaponImage, Color color) {
        MouseClick = mouseClick;
        this.selectionButton = selectionButton;
        this.TOTALFIRECOOLDOWN = TOTALFIRECOOLDOWN;
        this.defaultProjectile = defaultProjectile;
        this.shotCount = shotCount;
        this.accuracy = accuracy;
        this.weaponImage = weaponImage;
        this.color = color;
    }


    /**
     * To allow proper testing and easy reverse usage -
     * adding a simple function that then based on an added boolan calls another function
     */
    protected ArrayList<Projectile> calcPlayerShotMouseClick(Point m, Map map, SolidObject shooter,boolean useOriginalFunction) {

        if(useOriginalFunction){
            return calcPlayerShotMouseClick_original(m,map,shooter);
        }
        else{
            return calcPlayerShotMouseClick_line(m,map,shooter);
        }

    }

    /**
         *
         * @param m - the desired shot location
         * @param map - the map the shot should be factored on
         * @param shooter the object from which the shot is calculated from
         */
        protected ArrayList<Projectile> calcPlayerShotMouseClick_original(Point m, Map map, SolidObject shooter) {

            //clear / init the projectile list array
            //This ensures we don't get any function situations of adding a projectile to the overall projectile list twice
            ArrayList<Projectile> projectileShots = new ArrayList<>();

            //always good safety checks to not call stuff that is not initialized
            if (m != null & map != null) {


                /**
                 * Set the X,Y position of the projectile starting position
                 *
                 * ... this has caused me no end of grief and has undergone multiple rewrites -
                 *
                 * the 2/14/2021 attempt will try to back up a step and draw a line then have the projectile try to follow the line as close as possible
                 */
                //The PosX of where the projectile starts -
                //this took more than a bit of revision and we are using a double to help keep the overall computation down but allow for more finesse direction than the grid square sizes allow
                double posx = shooter.getActualPosX()
                        + (shooter.getObjWidth() * (map.getTileWidth()) / 2);

                //POSition Y - starting projectile value
                double posy = shooter.getActualPosY()
                        + ((shooter.getObjHeight() * map.getTileHeight()) / 2);


                //this is an eye at maybe after getting the accuracy setup, to then add some inaccuracy and maybe a shotgun type weapon with multiple projectiles per shot
                for(int i = 0; i< shotCount;i++) {

                    //Initilize tango which is used as a base
                    Projectile tango = new Projectile(posx, posy, 1, 1, Color.orange,
                            0, 0, false, 0, 0, 0);

                    //this feels un necessary and it probably is
                    //OverridingValuesClass.OverrideProjectile(tango, this.getDefaultProjectile());

                    /** - Removed as it seems unneeded since when calling the clase we already set this up
                     //set the x,y starting position which is typically in the vicinity of the shooter
                     tango.setPosX(posx);
                     tango.setPosY(posy);
                     */

                    //move the projectile towards the desired target
                    /**
                     *
                     * @param map - the map
                     * @param x - the object to move towards
                     * @param y - the y position of the object to move towards
                     *
                     * @param desiredActualSpeed the desired speed hypotentuse to set the speed of the interceptor
                     *
                     * There is an inherent variablity due to the nature of the calculation simply looping to the max and min of the hypotenuse by scalling with 1.001
                     * but it seems to work so 'why fix it if it ain't broke'?
                     */

                    tango.moveTowards(map, m.getX(), m.getY(),
                            (   this.getDefaultProjectile().getMaxSpeed()  )
                    );

                    //finally actually add the projectile to the answer array
                    projectileShots.add(tango);

                }

            }
            else{
                System.out.println("Error shooting: "+(m!=null)+", "+(map!=null));
            }

            return projectileShots;
    }


    /**
     *
     * @param m - the desired shot location
     * @param map - the map the shot should be factored on
     * @param shooter the object from which the shot is calculated from
     */
    protected ArrayList<Projectile> calcPlayerShotMouseClick_line(Point m, Map map, SolidObject shooter) {

        //clear / init the projectile list array
        //This ensures we don't get any function situations of adding a projectile to the overall projectile list twice
        ArrayList<Projectile> projectileShots = new ArrayList<>();

        //always good safety checks to not call stuff that is not initialized
        if (m != null & map != null) {


            /**
             * Set the X,Y position of the projectile starting position
             *
             * ... this has caused me no end of grief and has undergone multiple rewrites -
             *
             * the 2/14/2021 attempt will try to back up a step and draw a line then have the projectile try to follow the line as close as possible
             */
            //The PosX of where the projectile starts -
            //this took more than a bit of revision and we are using a double to help keep the overall computation down but allow for more finesse direction than the grid square sizes allow
            double posx = shooter.getActualPosX()
                    + (shooter.getObjWidth() * (map.getTileWidth()) / 2);

            //POSition Y - starting projectile value
            double posy = shooter.getActualPosY()
                    + ((shooter.getObjHeight() * map.getTileHeight()) / 2);


            //this is an eye at maybe after getting the accuracy setup, to then add some inaccuracy and maybe a shotgun type weapon with multiple projectiles per shot
            for(int i = 0; i< shotCount;i++) {

                //Initilize tango which is used as a base
                Projectile tango = new Projectile(posx, posy, 1, 1, Color.orange,
                        0, 0, false, 0, 0, 0);

                //this feels un necessary and it probably is
                //OverridingValuesClass.OverrideProjectile(tango, this.getDefaultProjectile());

                /** - Removed as it seems unneeded since when calling the clase we already set this up
                 //set the x,y starting position which is typically in the vicinity of the shooter
                 tango.setPosX(posx);
                 tango.setPosY(posy);
                 */

                //move the projectile towards the desired target
                /**
                 *
                 * @param map - the map
                 * @param x - the object to move towards
                 * @param y - the y position of the object to move towards
                 *
                 * @param desiredActualSpeed the desired speed hypotentuse to set the speed of the interceptor
                 *
                 * There is an inherent variablity due to the nature of the calculation simply looping to the max and min of the hypotenuse by scalling with 1.001
                 * but it seems to work so 'why fix it if it ain't broke'?
                 */

                tango.moveTowards(map, m.getX(), m.getY(),
                        (   this.getDefaultProjectile().getMaxSpeed()  )
                );

                //finally actually add the projectile to the answer array
                projectileShots.add(tango);

            }

        }
        else{
            System.out.println("Error shooting: "+(m!=null)+", "+(map!=null));
        }

        return projectileShots;
    }

    //Per the weapons own increment (eg by 1 or 3), cool down the weapon
    public void increment_cooldownWeapon(){
        FireCooldown--;
    }

    public int getMouseClick() {
        return MouseClick;
    }

    public void setMouseClick(int mouseClick) {
        MouseClick = mouseClick;
    }

    public int getSelectionButton() {
        return selectionButton;
    }

    public void setSelectionButton(int selectionButton) {
        this.selectionButton = selectionButton;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public int getFireCooldown() {
        return FireCooldown;
    }

    public void setFireCooldown(int fireCooldown) {
        FireCooldown = fireCooldown;
    }

    public int getTOTALFIRECOOLDOWN() {
        return TOTALFIRECOOLDOWN;
    }

    public void setTOTALFIRECOOLDOWN(int TOTALFIRECOOLDOWN) {
        this.TOTALFIRECOOLDOWN = TOTALFIRECOOLDOWN;
    }

    public Projectile getDefaultProjectile() {
        return defaultProjectile;
    }

    public void setDefaultProjectile(Projectile defaultProjectile) {
        this.defaultProjectile = defaultProjectile;
    }

    public BufferedImage getWeaponImage() {
        return weaponImage;
    }

    public void setWeaponImage(BufferedImage weaponImage) {
        this.weaponImage = weaponImage;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
