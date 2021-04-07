

import java.awt.*;
import java.util.ArrayList;

public class Frog extends  Plane {


    private int energy = 100;

    private int defaultEnergy;

    private Food targetPellet;


    /**
     * @param posX
     * @param posY
     * @param objWidth
     * @param objHeight
     * @param defaultHSpeed
     * @param defaultVSpeed
     * @param c
     * @param name
     * @param sight
     * @param projwidth
     * @param projheight
     * @param health
     */
    public Frog(int posX, int posY, int objWidth, int objHeight, int defaultHSpeed,
                int defaultVSpeed, Color c, String name, int sight, int projwidth, int projheight, int health, boolean isGravityBound) {

        super(posX, posY, objWidth, objHeight, defaultHSpeed,defaultVSpeed,projwidth,projheight,health, name,isGravityBound);

        super.setObjColour(c);
        Sight = sight;
    }


    /**
     *
     * @param gg
     * @param list
     * @param maps
     * @param calcmovment
     * @param self
     */
    static void drawFrog(

            Graphics2D gg, ArrayList<Frog> list,
            Map maps, boolean calcmovment,
            Player self)

    {
        calcmovment=false;

        //safety check
        if(list!=null){

            //Loop through each object in the arraylist

            for(Frog a: list){

                //only draw the object if it collides with the map to prevent unnecessary clutter

                if(a!=null&&self!=null){

                    if(  self!=null &&   calcmovment
                    ){
                        //Calculate motion of the object
                        a.calcMovement();
                        //Call the function to adjust motion towards the player
                        a.moveTowardsobj(self, maps);
                    }

                    //So check if the object is overlapping with the players view then draw that object
                    if(

                            a.isCollision(
                                    maps.getViewX()     ,   maps.getViewY()     ,
                                    maps.getMapWidth()  ,   maps.getMapHeight()
                                        )
                    )
                        {

                        int w = a.getObjWidth();
                        int h = a.getObjHeight();


                        a.setObjHeight(h*maps.getTileHeight());
                        a.setObjWidth(w*maps.getTileWidth());


                            /**
                             * OMG just spent the last however long debugging,
                             * no idea wtf the problem is, calling this overrides the Y values of the frogs
                             */
                        //then if they exceeded any border then loop them around the value
                       // a.PlaneBorderTest(a, true, maps);


                        a.drawobj(gg, maps);

                        a.setObjWidth(w);
                        a.setObjHeight(h);

                    }
                }}
        }
    }

    private int Sight;

    private int foodSupply;

    private String name;

    public void increaseFoodSupply(int additionalFood){
        foodSupply+=additionalFood;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getFoodSupply() {
        return foodSupply;
    }

    public void setFoodSupply(int foodSupply) {
        this.foodSupply = foodSupply;
    }


    public int getSight() {
        return Sight;
    }

    public void setSight(int sight) {
        Sight = sight;
    }


    public Food getTargetPellet() {
        return targetPellet;
    }

    public void setTargetPellet(Food targetPellet) {
        this.targetPellet = targetPellet;
    }

    public int getDefaultEnergy() {
        return defaultEnergy;
    }

    public void setDefaultEnergy(int defaultEnergy) {
        this.defaultEnergy = defaultEnergy;
    }
}
