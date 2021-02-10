
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BasePopulateLists {




    protected static ArrayList<Frog> basePopulateFrogs(int count, devTools developerTool, Map gameMap){

        ArrayList<Frog> frogyList = new ArrayList<>();

        Frog defaultFrog =new Frog(0,0,0,1,1,0, Color.white,"",0,0,0,1,false );


        FileReader filey = new FileReader("Frog\\Frog\\settings.txt");
        filey.setFileFolder("Frog\\Frog\\");
        OverridingValuesClass.OverrideFrog(defaultFrog,filey);

        System.out.println("\n" +"Default Frog: " +developerTool.getVars_MovingObject(defaultFrog, null, false, false, false, false, true, false,false,false)
                +"\n");

        int x = gameMap.getMapWidth()/2;
        int y = gameMap.getMapHeight()/2;

        String name = "FROG";

        //Default Plane values
        int width = 1;
        int height = 1;
        int VSpeed = 1;
        int HSpeed = 1;
        int health = 50;

        //Reference Values
        int fireCool = 0;
        int bombCool = 0;

        int projHeight = 100;
        int projWidth = 100;

        boolean isgravity = false;

        Color c = new Color(24,24,24);

        int sight = 10000;

        Random rand = new Random();

        for(int i = 0; i<count;i++) {



            x = rand.nextInt(
                    gameMap.getMapWidth()
            );

            y = rand.nextInt(
                    gameMap.getMapHeight()

            );


            Frog jennifer = new Frog(
                    x, y, width, height, HSpeed, VSpeed, c, name,
                    sight, projWidth, projHeight, health,isgravity);

            OverridingValuesClass.OverrideFrog(jennifer, defaultFrog);

            frogyList.add(jennifer);
        }


        return frogyList;
    }

    /**
     KeyBoard Inputs: buttonUP, buttonDown, buttonLeft, buttonRight ,buttonFire, buttonAltFire
     Player Values: Height, Width, VSpeed (and sets the default), HSpeed (and sets the default), health, name (entirely for role play)
     Player reference values: FIRECOOLDOWN, BOMBCOOLDOWN, DefaultProjectileHeight, DefaultProjectileWidth
     * @param count - the amount of players to be added
     */
    protected static ArrayList<Player> basePopulatePlayers(int count, Map gameMap){

        ArrayList<Player> playerList = new ArrayList<>();



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
        int width = 1;
        int height = 3;
        int VSpeed = 1;
        int HSpeed = 1;
        int health = 50;

        //Reference Values
        int fireCool = 0;
        int bombCool = 0;

        int projHeight = 100;
        int projWidth = 100;

        ArrayList<BeltSlot> beltList =null;

        boolean isGravityBound = true;

        /**
         *
         * @param posX - the X position of the top left of the square
         * @param posY - the Y position of the top left of the square
         * @param objWidth - the width
         * @param objHeight height
         * @param defaultHSpeed the H value that is referenced as how fast it can/ should be going
         * @param defaultVSpeed the V value that is referenced as how fast it can/ should be going
         *
         * @param buttonUp
         * @param buttonDown
         * @param buttonLeft
         * @param buttonRight
         *
         * @param health the health of the player
         * @param name the name of the player
         *
         * @param beltList
         * @param weaponList
         */

        Player player = new Player(
                x,y,width,height,HSpeed,VSpeed,defaultUp,defaultDown,defaultLeft,defaultRight, health, name, null,
                Player.getDefaultWeaponList(), isGravityBound
        );

        FileReader file = new FileReader("Players Model\\Player_John\\playersettings.txt");
        file.setFileFolder("Players Model\\Player_John\\");

        OverridingValuesClass.OverridePlayer(player,file);


        player.setObjColour(new Color(179, 245, 255));

        playerList.add(player);


        return playerList;
    }




}
