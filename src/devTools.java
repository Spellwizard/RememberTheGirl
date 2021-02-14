

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class devTools {

    //User Input keyboard button values
    private int Keycmd_PauseGame=80; //pause / unpause the round
    private int Keycmd_StepRound=75; //allow for 1 run of the game and then pause before next round
    private int Keycmd_IncreaseSpeed=74;//increase round speed
    private int Keycmd_DecreaseSpeed=76;//decrease round speed
    private int Keycmd_ToggleGraphics= 0;//used to toggle if the graphics are on
    private int Keycmd_repopulateFood = 0;//resets food


    private boolean isDebug;

    public devTools() {
    }

    /**
     *
     * @param gg
     * @param framecount
     * @param frameSeconds
     * @param frameMinutes
     * @param roundCount
     * @param graphicsOn
     * @param gameMap
     * @param buildingsList
     * @param playerList
     * @param frogList
     */
    protected void drawScorebaord(Graphics2D gg, int framecount, int frameSeconds, int frameMinutes
                                         , int roundCount, boolean graphicsOn,
                                         Map gameMap,
                                         ArrayList<Building> buildingsList,
                                         ArrayList<Player> playerList, ArrayList<Frog> frogList
            ){
            //  SCOREBOARD

            gg.setColor(Color.black);

            int pos = 60;

            int posy = 35;

            gg.setFont(new Font("TimesRoman", Font.PLAIN, 13));

            if(gameMap!=null)gg.drawString("Frame Count: " + framecount +
                            " Second count: " +  frameSeconds+
                            " Minute count: " +  frameMinutes
                            +" Map View: "+gameMap.getViewX()+", "+gameMap.getViewY()
                            +" Map Size: "+gameMap.getMapWidth()+" , "+gameMap.getMapHeight()
                            +" Round: "+roundCount
                            +" graphicsOn: "+graphicsOn
                            +" Map size: W"+gameMap.getTileWidth()
                            +" H"+gameMap.getTileHeight()
                    , 50, 30);


            posy+=25;


            if(playerList!=null){
                for(Player john: playerList) {
                    String line = "Player: "+getVars_Player(john, gameMap,true, true, true, false, false, false, true, false
                    ,false, false, false, false, true, true,true);


                    gg.drawString(line
                            , 50, (50+posy));

                    posy+=15;

                    if(
                            playerList.get(0).getProjectileArrayList()!=null

                    ) {
                        ArrayList<Projectile> projectileList = playerList.get(0).getProjectileArrayList();

                        for (int i = projectileList.size()-1;i>=0;i--) {

                            Projectile greeks = projectileList.get(i);

                            String sub_line = "Projectiles: " + getVars_Projectile(greeks, gameMap, false, true, true,
                                    false, false, false, false, false, false);
                            gg.drawString(sub_line
                                    , 50, 50 + posy);
                            posy += 15;
                        }
                    }

                }
            }




                if (false) {


                    if(frogList!=null)
                        for(Plane john: frogList) {
                            String line = "Frog: "+getVars_Plane(john,gameMap,true, false, true,
                                    false, true, false, true, false,
                                    false,false,true,false);
                            gg.drawString(line
                                    , 50, 50+posy);
                            posy+=15;
                        }


                    /**
                     * make some text on screen with some detail for the object list of the buildings
                     */
                    if(buildingsList!=null)
                        for(Building john: buildingsList) {
                            String line = "Building: "+getVars_SolidObject(john,gameMap,true, false, true,
                                    false, true);
                            gg.drawString(line
                                    , 50, 50+posy);
                            posy+=15;
                        }
                }
            }

    /**
     * This function is given e
     * @param e - a keyboard input
     * Then test the keyboard button against the dev buttons and activate various commands as needed
     * @param gameMap
     * @param gamePaused
     */
    protected void calcCommands(KeyEvent e,  boolean graphicsOn,boolean gamePaused,
                                Map gameMap,
                                       ArrayList<Player> playerList
    ) {

        if (isDebug) {
            int key = e.getKeyCode();

            //'P' key
            //stop ALL motion until the 'p' key is hit again
            //all sorts of problems with running the freeze code.... just getting mvp
            if (key == Keycmd_PauseGame) {
                //toggle the game paused variable
                gamePaused = !gamePaused;
            } else if (key == Keycmd_repopulateFood) {

            } else if (key == Keycmd_ToggleGraphics) {
                graphicsOn = !graphicsOn;
            } else if (key == Keycmd_IncreaseSpeed) {
                int increment = 3;
                gameMap.setTileHeight(gameMap.getTileHeight() + increment);
                gameMap.setTileWidth(gameMap.getTileWidth() + increment);
/**
 for(Plane john: playerList){
 john.setDefaultVSpeed(john.getDefaultVSpeed()+increment);
 john.setDefaultHSpeed(john.getDefaultHSpeed()+increment);
 }

 */
            } else if (key == Keycmd_DecreaseSpeed) {
                int increment = -3;

                gameMap.setTileHeight(gameMap.getTileHeight() + increment);
                gameMap.setTileWidth(gameMap.getTileWidth() + increment);

                /** for(Plane john: playerList){
                 john.setDefaultVSpeed(john.getDefaultVSpeed()+increment);
                 john.setDefaultHSpeed(john.getDefaultHSpeed()+increment);
                 }*/
            }

        }
    }

    /**
     * Given obj and the various booleans, return a string with a complete list of the desired values with indiactors of each:
     * eg: if coords is true then the string is added with 'Coords: (X,Y)'
     * @param obj - The object that the values are pulled from
     * @param maps - the map to compensate values for the map
     * @param coords - the actual x,y coordinates
     * @param compCoords - the coordinates in comparision to the window
     * @param size - the width & height of the object
     * @param color - the colour of the object
     * @param images - the up,down, left, right and if they are null
     * @return a string of the values that were set as true
     */
    protected String getVars_SolidObject(SolidObject obj,Map maps, boolean coords,boolean compCoords,
                                         boolean size, boolean color, boolean images){
        String answer = "";

        if(coords){
            answer+=" Coords: ("+obj.getActualPosX()+", "+obj.getActualPosY()+")";
        }
        if(compCoords&&maps!=null){
            answer+=" Compensated Coords: ("+
                    (obj.getPosX()+maps.getViewX())
                    +", "+
                    (obj.getPosY()+maps.getViewY())
                    +")";
        }
        if(size){
            answer+=" W: "+obj.getObjWidth()+ " H: "+obj.getObjHeight();
        }
        if(color){
            answer+=" Colour: "+obj.getObjColour();
        }
        if(images){
            answer+=
            " Images - Up: "+ (obj.getUp_Image()!=null)+
                    " Down: "+(obj.getDown_Image()!=null)+
                    " Left: "+(obj.getL_Image()!=null)+
                    " Right: "+(obj.getR_Image()!=null)
            ;
        }

        return answer;
    }

    /**
     * Given obj and the various booleans, return a string with a complete list of the desired values with indiactors of each:
     * eg: if coords is true then the string is added with 'Coords: (X,Y)'
     * @param obj - The object that the values are pulled from
     * @param maps - the map to compensate values for the map
     * @param coords - the actual x,y coordinates
     * @param compCoords - the coordinates in comparision to the window
     * @param size - the width & height of the object
     * @param color - the colour of the object
     * @param images - the up,down, left, right and if they are null
     * @param defaultSpeeds -
     * @param Speeds -
     * @param lastCoords -
     * @return String of the desired values
     */
    protected String getVars_MovingObject(
            MovingObject obj, Map maps, boolean coords, boolean compCoords, boolean size, boolean color, boolean images
            , boolean defaultSpeeds, boolean Speeds, boolean lastCoords
    ){
        String answer = getVars_SolidObject(obj,maps,coords,compCoords,size,color,images);

        if(defaultSpeeds){
            answer+=" Default VSpeed: "+obj.getDefaultVSpeed()+" Default HSpeed: "+obj.getDefaultHSpeed();
        }
        if(Speeds){
            answer+=" VSpeed: "+obj.getObjVSpeed()+" HSpeed: "+obj.getObjHSpeed();
        }
        if(lastCoords){
            answer+=" LastCoords: ("+obj.getLastX()+", "+obj.getLastY()+")";
        }

        return answer;
    }

    /**
     * Given obj and the various booleans, return a string with a complete list of the desired values with indiactors of each:
     * eg: if coords is true then the string is added with 'Coords: (X,Y)'
     * @param obj - The object that the values are pulled from
     * @param maps - the map to compensate values for the map
     * @param coords - the actual x,y coordinates
     * @param compCoords - the coordinates in comparision to the window
     * @param size - the width & height of the object
     * @param color - the colour of the object
     * @param images - the up,down, left, right and if they are null
     * @param defaultSpeeds -
     * @param Speeds -
     * @param lastCoords -
     * @return String of the desired values
     *
     * @param coolDowns
     * @param projSize
     * @param health
     * @param name
     */
    protected String getVars_Plane(Plane obj, Map maps, boolean coords, boolean compCoords, boolean size, boolean color, boolean images
            , boolean defaultSpeeds, boolean Speeds, boolean lastCoords
            ,boolean coolDowns, boolean projSize, boolean health, boolean name
    ){
        String answer = getVars_MovingObject(obj,maps,coords,compCoords,size,color,images,defaultSpeeds,Speeds, lastCoords);

        if(coolDowns){
            answer+=
            " Primary CoolDown: "+obj.getFireCooldown()+" Alt Cooldown: "+obj.getAltFireCooldown()

            ;
        }
        if(projSize){
            answer+=" projHeight: "+ obj.getDefaultProjectileHeight()+" projWidth: "+obj.getDefaultProjectileWidth();
        }

        if(health){
            answer+=" Health: "+(obj.getHealth());
        }
        if(name){
            answer+= " Name: "+ obj.getName();
        }

        return answer;
    }


    /**
     * Given obj and the various booleans, return a string with a complete list of the desired values with indiactors of each:
     * eg: if coords is true then the string is added with 'Coords: (X,Y)'
     * @param obj - The object that the values are pulled from
     * @param maps - the map to compensate values for the map
     * @param coords - the actual x,y coordinates
     * @param compCoords - the coordinates in comparision to the window
     * @param size - the width & height of the object
     * @param color - the colour of the object
     * @param images - the up,down, left, right and if they are null
     * @param defaultSpeeds -
     * @param Speeds -
     * @param lastCoords -
     * @return String of the desired values
     * @param coolDowns
     * @param projSize
     * @param health
     * @param name
     * @param projCount
     */
    protected String getVars_Player(Player obj, Map maps, boolean coords, boolean compCoords, boolean size, boolean color, boolean images
            , boolean defaultSpeeds, boolean Speeds, boolean lastCoords
            ,boolean coolDowns, boolean projSize, boolean health, boolean name,
                                    boolean projCount, boolean Sprint, boolean isMouse
    ){

        String answer = getVars_Plane(obj,maps,coords,compCoords,size,color,images,defaultSpeeds,Speeds, lastCoords,coolDowns,projSize,health,name);

        if(projCount){
            if(obj.getProjectileArrayList()!=null)answer+=" ProjCount: "+obj.getProjectileArrayList().size();
        }
        if(Sprint){
            answer+= " Sprint: "+obj.getCurrentSprintCount()+"/"+obj.getMAXSPRINTSPEED()+" IsRunning: "+obj.isRunning();
        }

        if(isMouse){
            if(obj.getMouseLocation()!=null)answer+= " Mouse: "+obj.getMouseLocation().getX()+", "+ obj.getMouseLocation().getY();
        }

        return answer;
    }

    protected String getVars_Projectile(    Projectile obj, Map maps, boolean coords, boolean compCoords, boolean size, boolean color, boolean images
            , boolean defaultSpeeds, boolean Speeds, boolean lastCoords, boolean actualSpeed

                                            ){
        String output = getVars_MovingObject(obj,maps,coords,compCoords,size,color,images,defaultSpeeds,Speeds, lastCoords);

        if(actualSpeed){
            output+=" Hypotenuse Speed: "+Math.hypot(
                   obj.getObjHSpeed(),
                    obj.getObjVSpeed()
            );
        }


        return  output;
    }

    public int getKeycmd_PauseGame() {
        return Keycmd_PauseGame;
    }

    public void setKeycmd_PauseGame(int keycmd_PauseGame) {
        Keycmd_PauseGame = keycmd_PauseGame;
    }

    public int getKeycmd_StepRound() {
        return Keycmd_StepRound;
    }

    public void setKeycmd_StepRound(int keycmd_StepRound) {
        Keycmd_StepRound = keycmd_StepRound;
    }

    public int getKeycmd_IncreaseSpeed() {
        return Keycmd_IncreaseSpeed;
    }

    public void setKeycmd_IncreaseSpeed(int keycmd_IncreaseSpeed) {
        Keycmd_IncreaseSpeed = keycmd_IncreaseSpeed;
    }

    public int getKeycmd_DecreaseSpeed() {
        return Keycmd_DecreaseSpeed;
    }

    public void setKeycmd_DecreaseSpeed(int keycmd_DecreaseSpeed) {
        Keycmd_DecreaseSpeed = keycmd_DecreaseSpeed;
    }

    public int getKeycmd_ToggleGraphics() {
        return Keycmd_ToggleGraphics;
    }

    public void setKeycmd_ToggleGraphics(int keycmd_ToggleGraphics) {
        Keycmd_ToggleGraphics = keycmd_ToggleGraphics;
    }

    public int getKeycmd_repopulateFood() {
        return Keycmd_repopulateFood;
    }

    public void setKeycmd_repopulateFood(int keycmd_repopulateFood) {
        Keycmd_repopulateFood = keycmd_repopulateFood;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }
}
