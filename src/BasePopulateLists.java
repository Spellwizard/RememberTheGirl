
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class BasePopulateLists {


    /**
     * basePopulateFrogs
     * @param count the number of Frogs to populate (should be positive or errors will result)
     * @param developerTool - an object called for various values contained
     * @param gameMap - the Map object used to track various values and used to ensure correct sizing occurs
     * @return an ArrayList of Frogs with the requested basic values
     */
    protected static ArrayList<Frog> basePopulateFrogs(int count, devTools developerTool,
                                                       Map gameMap){

        //The Arraylist from which all newly populated frogs shall be made with
        ArrayList<Frog> frogyList = new ArrayList<>();

        /**
         * The defaultFrog is an interesting moment and on another reading seems like a weird choice in coding
         * The defaultFrog Value is first initialized with basic values, overridden with the values found in the folder (pictures, height, width, speed, ect)
         * and then we use the defaultFrog to Override the values of the Frog Jennifer.
         * One on first reading might ask why not remove the default frog and just use the OverrideFrog on Jennifer?
         * Well the answer is based purely on looping: as the Override Function is called, it pulls pictures and that looped 19 or 100 times seems to cause significant start up delay
         * Thus this interesting workaround.
         */
        Frog defaultFrog =new Frog(0,0,0,
                1,1,0, Color.white,"",0,0,0,1,false );

        //FILE READING
        //Pulling from the File Location to Override with various desired values eg: pictures, size, speeds
        FileReader filey = new FileReader("Frog\\Frog\\settings.txt");
        filey.setFileFolder("Frog\\Frog\\");
        OverridingValuesClass.OverrideFrog(defaultFrog,filey);

        System.out.println("\n" +"Default Frog: " +developerTool.getVars_MovingObject(defaultFrog, null, false, false, false, false, true, false,false,false)
                +"\n");

        //END FILE READING

        int x = gameMap.getMapWidth() / 2;
        int y = gameMap.getMapHeight() / 2;

        String name = "FROG";

        //Default Plane values
        int width = 1;
        int height = 1;
        int VSpeed = 1;
        int HSpeed = 1;
        int health = 50;

        //Default Projectile values

        int projHeight = 100;
        int projWidth = 100;

        //Set if the Frog obeys Gravity
        boolean isgravity = false;

        //Set the Frogs Colour (this is rarely seen as the square is typically drawn with a picture
        Color c = new Color(24,24,24);

        //Set the sight of the object (this is used to allow for basic targeting by the Frogs)
        int sight = 10000;

        //Make a random number generator
        Random rand = new Random();

        //The frog to be overriden time after time in the looping like a horror story :)
        Frog jennifer =null;

        //Loop for the number that was given as the count number
        for(int i = 0; i<count;i++) {

            //Set the Frogs X, Y positions randomly within the map position

            //Position X within the width of the map
            x = rand.nextInt(   gameMap.getMapWidth()  );

            //Position Y within the height of the map
            y = rand.nextInt(  gameMap.getMapHeight()  );

            //Override jennifer with the values of the frog
             jennifer = new Frog(
                    x, y, width, height, HSpeed, VSpeed, c, name,
                    sight, projWidth, projHeight, health, isgravity);

             //Now loop through and Override the values of Jennifer with defaultFrog
            OverridingValuesClass.OverrideFrog(jennifer, defaultFrog);

            //And finally add this iteration of jennifer to the frogList array which is then used as the return object
            frogyList.add(jennifer);
        }

        //return the frog list
        return frogyList;
    }

    /** basePopulateFrogs
     * Create and return an ArrayList of Frogs within certain values and using the values / pictures in the designated folder
     *
     * @param count the number of Frogs to populate (should be positive or errors will result)
     *
     * The X and Y Start / End Value are used to describe the block positions and range between that the Frogs X,Y positions will be set
     * EG: XStartValue = 1; XEndValue = 4; gameMap.getMapTile()=10; with a result of 2 between the range multiplied by 10 results in an X of 20
     *
     *              Start/End Values CANNOT BE Negative!!
     *
     * @param XStartValue - the gameMap X min position of the range to determine the  position of the Frog
     * @param XEndValue the gameMap X max position of the range to determine the  position of the Frog
     *
     * @param YStartValue - the gameMap Y min position of the range to determine the  position of the Frog
     * @param YEndValue the gameMap Y max position of the range to determine the  position of the Frog
     *
     * @param developerTool an object called for various values contained
     * @param gameMap the Map object used to track various values and used to ensure correct sizing occur
     * @return an ArrayList of Frogs with the requested basic values
     */
    protected static ArrayList<Frog> basePopulateFrogs(int count,
                                                       devTools developerTool,
                                                       Map gameMap,
                                                       int XStartValue, int XEndValue,
                                                       int YStartValue, int YEndValue

    ){

        //The Arraylist from which all newly populated frogs shall be made with
        ArrayList<Frog> frogyList = new ArrayList<>();


        /**
         * The defaultFrog is an interesting moment and on another reading seems like a weird choice in coding
         * The defaultFrog Value is first initialized with basic values, overridden with the values found in the folder (pictures, height, width, speed, ect)
         * and then we use the defaultFrog to Override the values of the Frog Jennifer.
         * One on first reading might ask why not remove the default frog and just use the OverrideFrog on Jennifer?
         * Well the answer is based purely on looping: as the Override Function is called, it pulls pictures and that looped 19 or 100 times seems to cause significant start up delay
         * Thus this interesting workaround.
         */
        Frog defaultFrog =new Frog(0,0,0,
                1,1,0, Color.white,"",0,0,0,1,false );

        //FILE READING
        //Pulling from the File Location to Override with various desired values eg: pictures, size, speeds
        FileReader filey = new FileReader("Frog\\Frog\\settings.txt");
        filey.setFileFolder("Frog\\Frog\\");
        OverridingValuesClass.OverrideFrog(defaultFrog,filey);

        System.out.println("\n" +"Default Frog: " +developerTool.getVars_MovingObject(defaultFrog, null, false, false, false, false, true, false,false,false)
                +"\n");

        //END FILE READING

        String name = "FROG";

        //Default Plane values
        int width = 1;
        int height = 1;
        int VSpeed = 1;
        int HSpeed = 1;
        int health = 50;

        //Default Projectile values

        int projHeight = 100;
        int projWidth = 100;

        //Set if the Frog obeys Gravity
        boolean isgravity = false;

        //Set the Frogs Colour (this is rarely seen as the square is typically drawn with a picture
        Color c = new Color(24,24,24);

        //Set the sight of the object (this is used to allow for basic targeting by the Frogs)
        int sight = 0;

        //Make a random number generator
        Random rand = new Random();

        //The frog to be overriden time after time in the looping like a horror story :)
        Frog jennifer =null;

        //Loop for the number that was given as the count number
        for(int i = 0; i<count;i++) {

            //Override jennifer with the values of the frog
            jennifer = new Frog(
                    0, 0, width, height, HSpeed, VSpeed, c, name,
                    sight, projWidth, projHeight, health, isgravity);


            //Now loop through and Override the values of Jennifer with defaultFrog

                OverridingValuesClass.OverrideFrog(jennifer, defaultFrog);

            /**
             * Now these following lines are the ones that actually set the proper x/y positions
             */
            //Set the Frogs X, Y positions randomly within the map position
            //Multiplying the size of the tile to the tile provided to set the x/y positions
            //to compensate, (StartValue - EndValue) = difference to randomize the number with
            //Then simply take that difference add the StartValue to ensure we get a proper range between, then multiply by the tile size
            //to get the proper position

            int randomvariable= 0;

            //Position X within the width of the map
            int difference = (XEndValue-XStartValue);

            //prevent start minus end values equaling 0 and causing nextInt to throw an exception :)
            if(difference!=0)
            randomvariable =    rand.nextInt(       difference      );

            jennifer.setPosX(
                    (           randomvariable
                                    +
                                XStartValue
                    )
                                    *
                            gameMap.getTileWidth()
                                                            );



            //Position Y within the height of the map
            difference = (YEndValue-YStartValue);

            //prevent start minus end values equaling 0 and causing nextInt to throw an exception :)
            if(difference!=0)
                randomvariable =    rand.nextInt(       difference      );


            jennifer.setPosY(
                    (           randomvariable
                            +
                            YStartValue
                    )
                            *
                            gameMap.getTileHeight()
            );

            System.out.println("jennifer (X,Y) ("+jennifer.getPosX()+" ,"+jennifer.getPosY()+")");
            System.out.println("jennifer blocks: ("+jennifer.getPosX()/gameMap.getTileWidth()+" ,"+jennifer.getPosY()/gameMap.getTileHeight()+")");


            jennifer.setObjHSpeed(0);
            jennifer.setObjVSpeed(0);
            jennifer.setDefaultHSpeed(0);
            jennifer.setDefaultVSpeed(0);

            //And finally add this iteration of jennifer to the frogList array which is then used as the return object
            frogyList.add(jennifer);
        }

        //return the frog list
        return frogyList;
    }

    /**
     KeyBoard Inputs: buttonUP, buttonDown, buttonLeft, buttonRight ,buttonFire, buttonAltFire
     Player Values: Height, Width, VSpeed (and sets the default), HSpeed (and sets the default), health, name (entirely for role play)
     Player reference values: FIRECOOLDOWN, BOMBCOOLDOWN, DefaultProjectileHeight, DefaultProjectileWidth
     * @param count - the amount of players to be added
     */
    protected static Player basePopulatePlayers(int count, Map gameMap){

        int x = gameMap.getMapWidth()/2;
        int y = gameMap.getMapHeight()/2;

        String name = "PLAYER";

        //Default KeyBoard values
        int defaultUp = 0;
        int defaultDown = 0;
        int defaultLeft =0;
        int defaultRight = 0;
        int defaultFire = 69;
        int defaultAltFire = 81;

        //Default Plane values
        int width = 2;
        int height = 2;
        int VSpeed = 1;
        int HSpeed = 4;
        int health = 50;

        //Reference Values
        int fireCool = 0;
        int bombCool = 0;

        int projHeight = 100;
        int projWidth = 100;

        ArrayList<BeltSlot> beltList =null;

        boolean isGravityBound = true;

        Player player = new Player(
                x,y,width,height,HSpeed,VSpeed,defaultUp,defaultDown,defaultLeft,defaultRight, health, name, null,
                Player.getDefaultWeaponList(), isGravityBound
        );

        FileReader file = new FileReader("Players Model\\Player_John\\playersettings.txt");
        file.setFileFolder("Players Model\\Player_John\\");

        OverridingValuesClass.OverridePlayer(player,file);


        player.setObjColour(new Color(179, 245, 255));


        return player;
    }




}
