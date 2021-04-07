//The swing and awt are used as the graphics classes that make a lot of the coding way easier b/c it is built by someone else
//why reinvent the wheel when the car needs to be built?
import javax.swing.*;
import java.awt.*;

//Java.awt.event.* is used to track bother keyboard and mouse inputs when the user has the window selected
import java.awt.event.*;

//The arraylists are used as I'm not aware of an alternative to have a dynamic list of objects
import java.util.ArrayList;

public class MainSecond {

        public static JFrame frame = new JFrame("Game!= Mario");
        protected GameCanvas program;

        public MainSecond() {

            Container c = frame.getContentPane();
            c.setBackground(Color.red);

            frame.setBackground(Color.yellow);

            frame.setLocationRelativeTo(null);
            program = new GameCanvas();
            frame.add(program);
            frame.setVisible(true);

        }
    }
    class GameCanvas extends JComponent {

        //General World Settings


        //increment for the program time
        private int frameSeconds = 0;
        private int frameMinutes = 0;

        private boolean gamePaused = false; // this is a toggle for the 'p' button to pause all movement players and arrows at the time of creation but potentially enemies

        //GraphicsOn Is used to determine if the program should be re-drawing on each loop of the paincomponent function, otherwise the program continues just doesn't display.
        //This is largely legacy as it was used when i was playing with basic graphics and wanted to see how certain numbers would progress rather than see it happen b/c I would also speed up game time to a high level
        private boolean graphicsOn = true;

        private int initPopulationSize;//used as the beginning population of the frogs

        private int GameSpeed = 1;//each increase is in every loop is how many times it per count eg: if 2 then every 10 millseconds all calculations are run twice

        private int pelletCount = 1; // the number of pellets added per round

        //framecount using maths can sorta be used to get seconds / minutes ect but can be out of sync due to program / hardware lag
        private int framecount=1; //the total count of all frames for the duration of the program running

        private int roundDuration= 2;//this is in minutes
        private int roundCount = 0;//this is the current round count

        //use a variable size player list to allow for more players later on / to allow for some to die
        private  ArrayList<Frog> frogList;

        //The reason this isn't an object is because I hate myself and actually thought I might get more than 1 person to play this
        private Player PlayerZero;

        /**
         * Dynamic arraylist of all the buildings that are currently built
         */
        private ArrayList<Building> buildingsList  = new ArrayList<>();

        //Background image is just used by the map to represent the background so that it looks pretty for all the pretty people.
        private ArrayList<BackgroundImage> backgroundImageList = new ArrayList<BackgroundImage>();

        //The GameMap is used as the object with all things Map Related: where the view of the map is, the size of the map, drawing any/ all background images
        private Map gameMap;

        //This object is a basically used as a way to contain any meta inputs I want access too eg: spawn 5 enemies or change the map size
        private devTools developerTool = new devTools();

        /**
         *
         */
        protected GameCanvas() {

            //Set the size of the grid tiles
            int tile_width = 25;
            //For ease of change I keep the height and width as
            // different objects but for now but I just set them to the same value
            int tile_height = tile_width;


            //By multiplying the desired tiles by the respective size it ensures proper fitting.
            int map_width = tile_width *200;
            int map_height = tile_width *31;

            //Initialize the object used to track the fundamentals: gravity, grid sizes, the position and size of the players view of the overall map
            gameMap = new Map(0,0,400,400,
                    map_width, map_height, tile_width, tile_height,
                    0, 0);

            //Call the init default values
            InitializeDefaultValues();

            gamePaused = false;

            firstTimeinitialization();
        }

        /**
         * Heavy Legacy Code here but can't be asked to write it into the rest of the code.
         *
         * One time on start up : General World Settings
         */
        private void InitializeDefaultValues() {

            //Initialize the player using the BasePoulate class to help keep the code simpler
            PlayerZero = BasePopulateLists.basePopulatePlayers(1,gameMap);



            buildingsList = new ArrayList<>();
            graphicsOn = true;

        }

        /**
         * The InputTracker is used to track keyboard actions as both listed under the developer commands and the
         * various commands of the players in the player lists
         * ToDo
         * I would like to see a comination of commands eg: shift + r to reverese the direction of rotation
         *
         */
        KeyListener InputTracker = new KeyListener() {

            public void keyPressed(KeyEvent e) {
                calcPlayerInput(e, gameMap);

                developerTool.calcCommands(e,graphicsOn,gamePaused,gameMap, PlayerZero);


                int key = e.getKeyCode();
            }
            public void keyTyped(KeyEvent e){
            }

            public void keyReleased(KeyEvent e) {

                Player.calcPlayerReleasedInput(e,PlayerZero);
            }

        };



        private void overrideGameValues(String fileName) {

            System.out.println("\noverrideGameValues\n");

            FileReader file = new FileReader(fileName);

            //Overrride all the player values
            OverrideAllPlayerValues();

            //Finally handle overriding the game cmd buttons
            OverridingValuesClass.OverrideGameCmds(file, pelletCount, gamePaused, developerTool.isDebug(),
                    graphicsOn, roundDuration, initPopulationSize, developerTool.getKeycmd_IncreaseSpeed(),
                    developerTool.getKeycmd_DecreaseSpeed(),developerTool.getKeycmd_repopulateFood(),
                    developerTool.getKeycmd_ToggleGraphics(),developerTool.getKeycmd_StepRound(),
                    developerTool.getKeycmd_repopulateFood(), developerTool
            );
        }

        /**
         * KeyBoard Inputs: buttonUP, buttonDown, buttonLeft, buttonRight ,buttonFire, buttonAltFire
         * Player Values: Height, Width, VSpeed (and sets the default), HSpeed (and sets the default), health, name (entirely for role play)
         *  Player reference values: FIRECOOLDOWN, BOMBCOOLDOWN, DefaultProjectileHeight, DefaultProjectileWidth
         *
         *  and will safely loop through and override if any such values are found
         *
         *  This looping allows for easy changes to the numbers of players without having to add additional code but
         *             means that to set a value it will look for buttonUp = 'Player_' + (the position, yes from 0)+'buttonUp'
         *             so as a whole it might set the 3rd players buttonAltFire = 'Player_2_buttonAltFire'
         */
        private void OverrideAllPlayerValues() {
            System.out.println("OverrideAllPlayerValues");

            FileReader file = new FileReader("Players Model\\Player_0\\playersettings.txt");
            String temp = "";
            String players_folder = "Players Model\\Player_";
            String fileName = "playersettings.txt";
            String type = "Player";


                file.setFileName("Players Model\\Player_0\\playersettings.txt");

                file.setFileFolder("Players Model\\Player_0\\");

                System.out.println("OverridingPlayer: "+file.getFileName());

            PlayerZero = BasePopulateLists.basePopulatePlayers(1,gameMap);


            //use a variable size player list to allow for more players later on / to allow for some to die
            frogList = new ArrayList<>();

            int XEndValue = gameMap.getTileList().size() -       4;
            int YEndValue = gameMap.getTileList().get(0).size() -      4;

            frogList = BasePopulateLists.basePopulateFrogs(1, developerTool, gameMap,
                    0,XEndValue,
                                YEndValue,YEndValue );

            System.out.println("frogList = BasePopulateLists.basePopulateFrogs: "+
                    XEndValue                    +", "
                    +YEndValue);
        }

        /**
         * Given e handle any relevant action that should occur with the players
         * @param e - Keyevent
         *
         * calls ArrayList PlayerList<Plane>
         *
         * This will move the plane on a speed fom the planes default speed value
         *
         */
        private void calcPlayerInput(KeyEvent e, Map gameMap){

            int key = e.getKeyCode();

            /**
             * Loop through each plane in the arraylist and handle the relevant action if any match each individuals list of actions
             */
            PlayerZero.calcPlayerPressedKey(e, gameMap);

        }


        private void firstTimeinitialization() {

            //use prebuilt values, make players and put them into the frogList arrayList

            String temp = "PlayerCount";
            FileReader file = new FileReader("GameSettings.txt");

            int value = 0;

            if (!file.findValue(temp).equals("")
                    && file.convertStringToInt(file.findValue(temp)) != -1)
                value =
                        file.  convertStringToInt(file.findValue(temp))
                        ;
            overrideGameValues("GameSettings.txt");

            //make sure that the window will actually listen for inputs
            initListeners();

            Thread animationThread = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        repaint();
                        try {
                            Thread.sleep(10);
                        } catch (Exception ex) {
                        }
                    }
                }
            });
            animationThread.start();
        }

        public void initListeners() {
            //Gotta actually inialize the keyboard tracker to track all those key presses
            this.addKeyListener(InputTracker);
            this.setFocusable(true);
        }


        /**
         * Although called by other functions, this is the function that the gome runs out of and loops through to simulate time in the game
         * @param g
         */
        public void paintComponent(Graphics g) {

            //Cast the Graphics object g into a 2D object, this allows for 2D optimization / design
            Graphics2D gg = (Graphics2D) g;

            gameMap.CycleUpdate( PlayerZero, gg,this.getWidth(), this.getHeight(), backgroundImageList);

            //loop the game per regular cycle timers the game speed which means that there is a functional fast forward button
            for(int i = 0;i<GameSpeed;i++) {

                //Only draw each object if the graphics are on and only calculate the movement if the game is not paused

                if(graphicsOn) {
                    /**
                     * The order that these following lines are very important
                     * this is the order that things are drawn and collisions calculated and so for any sublists
                     *          each object posses eg calling the player draw it will also draw that players projectiles
                     * the last on on the list gets to draw over everyone else and thus will appear if overlapping with another object
                     */

                    //BUILDINGS
                    Building.drawBuildings(gg, buildingsList, gameMap);

                    //FROGS
                    Frog.drawFrog(gg, frogList, gameMap, !gamePaused, PlayerZero);


                    //PLAYER
                    Player.drawPlayers(gg, PlayerZero, gameMap, !gamePaused,
                            this.getMousePosition());

                    //Draw the UI for the player (health bar, sprint, hover time, ect
                    PlayerZero.drawUI(gg, gameMap);

                    //need to update the dev info so that we get the various variables I need on the fly
                    if(developerTool.isDebug())developerTool.drawScorebaord(gg, framecount, frameSeconds, frameMinutes
                            , roundCount, graphicsOn, gameMap, buildingsList,
                            PlayerZero, frogList
                    );

                    //increment the framecount
                    framecount++;

                    frameSeconds = (framecount / 60); //calculates the seconds per the frame count
                    frameMinutes = frameSeconds / 60; // calculates the minutes based on the seconds which come from the frame count

                }
            }

        }



    }
