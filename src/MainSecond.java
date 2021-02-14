//The swing and awt are used as the graphics classes that make a lot of the coding way easier b/c it is built by someone else
//why reinvent the wheel when the car needs to be built?
import javax.swing.*;
import java.awt.*;

//Java.awt.event.* is used to track bother keyboard and mouse inputs when the user has the window selected
import java.awt.event.*;

//The arraylists are used because they are 1000% easier and faster to use then the limited arrays
import java.util.ArrayList;
//The random Class is used primarily to randomly populate a number of objects across the map
import java.util.Random;

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

            //On Close of game window go back to the menu window
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.out.println("Closing Window");

                    FileReader ouputData = new FileReader("DataOutput.txt");

                    for (String line : program.getDateOutput()) {
                        ouputData.addLine(line);
                        ouputData.addLine("funny");
                    }

                    ouputData.addLine("Funny;");

                    Menu.makeVisible();
                }
            });


        }
    }
    class GameCanvas extends JComponent {


        private int linex1 = 0;
        private int liney1= 0;
        private int linex2 = 0;
        private int liney2 =0;

        private Pipe defaultPipe;

        //General World Settings
        private boolean gamePaused = false; // this is a toggle for the 'p' button to pause all movement players and arrows at the time of creation but potentially enemies

        private boolean graphicsOn = true;

        private int initPopulationSize;//used as the beginning population of the frogs



        private int GameSpeed = 1;//each increase is in every loop is how many times it per count eg: if 2 then every 10 millseconds all calculations are run twice



        private int pelletCount = 1; // the number of pellets added per round

        //framecount using maths can sorta be used to get seconds / minutes ect but can be out of sync due to program / hardware lag
        private int framecount=1; //the total count of all frames for the duration of the program running

        private int roundDuration= 2;//this is in minutes
        private int roundCount = 0;//this is the current round count

        private int tempRoundCount = 0;

        private Random random = new Random(); // called in various places; mostly used to get a random nuber in a range using nextInt()

        //use a variable size player list to allow for more players later on / to allow for some to die
        private  ArrayList<Frog> frogList;

        private ArrayList<Player> playerList;

        /**
         * Dynamic arraylist of all the buildings that are currently built
         */
        private ArrayList<Building> buildingsList  = new ArrayList<>();

        public ArrayList<String> getDateOutput() {
            return dateOutput;
        }

        public ArrayList<String> dateOutput = new ArrayList<String>();


        private ArrayList<BackgroundImage> backgroundImageList = new ArrayList<BackgroundImage>();

        private Map gameMap;

        private devTools developerTool = new devTools();



        /**
         *
         */
        protected GameCanvas() {

            int tile_width = 35;
            int tile_height = tile_width;

            //by multipying the desired tiles by the respective size it ensures proper fitting.
            int map_width = tile_width *100;
            int map_height = tile_width *25;

            /**
             *
             * @param viewX
             * @param viewY
             * @param viewWidth
             * @param viewHeight
             * @param mapWidth
             * @param mapHeight
             * @param tileWidth
             * @param tileHeight
             * @param verticial_gravityConstant
             * @param horizontal_gravityConstant
             * @param tileList
             */

            gameMap = new Map(0,0,400,400,
                    map_width, map_height, tile_width, tile_height,
                    0, 0);

            InitializeDefaultValues();


            System.out.println("GameCanvas");

            gamePaused = false;

            //populateDefaultVariables();

            firstTimeinitialization();
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

                /**
                 * This function is given e
                 * @param e - a keyboard input
                 * Then test the keyboard button against the dev buttons and activate various commands as needed
                 * @param graphicsOn
                 * @param gameMap
                 * @param gamePaused
                 * @param Keycmd_PauseGame
                 * @param Keycmd_repopulateFood
                 * @param Keycmd_ToggleGraphics
                 * @param Keycmd_IncreaseSpeed
                 * @param Keycmd_DecreaseSpeed
                 */
                developerTool.calcCommands(e,graphicsOn,gamePaused,gameMap, playerList);


                int key = e.getKeyCode();
            }
            public void keyTyped(KeyEvent e){


            }

            public void keyReleased(KeyEvent e) {
                Player.calcPlayerReleasedInput(e,playerList);
            }

        };



        private void overrideGameValues(String fileName) {

            System.out.println("\noverrideGameValues\n");

            FileReader file = new FileReader(fileName);

            //Overrride all the player values
            OverrideAllPlayerValues(playerList);

            //Finally handle overriding the game cmd buttons
            OverridingValuesClass.OverrideGameCmds(file, pelletCount, gamePaused, developerTool.isDebug(),
                    graphicsOn, roundDuration, initPopulationSize, developerTool.getKeycmd_IncreaseSpeed(),
                    developerTool.getKeycmd_DecreaseSpeed(),developerTool.getKeycmd_repopulateFood(),
                    developerTool.getKeycmd_ToggleGraphics(),developerTool.getKeycmd_StepRound(),
                    developerTool.getKeycmd_repopulateFood(), developerTool
            );
        }

        /**
         * @param list This is at time of creation the player list which is referenced against internal values:
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
        private void OverrideAllPlayerValues(ArrayList<Player> list) {
            System.out.println("OverrideAllPlayerValues");

            FileReader file = new FileReader("Players Model\\Player_0\\playersettings.txt");
            String temp = "";
            String players_folder = "Players Model\\Player_";
            String fileName = "playersettings.txt";
            String type = "Player";

            for (int position = 0; position< list.size();position++) {

                Player self = list.get(position);

                // file.setFileName(players_folder+position+"/"+fileName);
                // file.setFileName(fileName+position+"\\"+fileName);

                file.setFileName("Players Model\\Player_0\\playersettings.txt");

                file.setFileFolder("Players Model\\Player_0\\");

                System.out.println("OverridingPlayer: "+file.getFileName());
            }


            playerList = BasePopulateLists.basePopulatePlayers(1,gameMap);

            frogList = BasePopulateLists.basePopulateFrogs(20, developerTool, gameMap);
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

            if(playerList!=null)for(Player self: playerList) {
                self.calcPlayerPressedKey(e, gameMap);
            }
        }


        private void firstTimeinitialization() {

            System.out.println("firstTimeinitialization");


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
            this.addKeyListener(InputTracker);
            this.addMouseListener(ratListner);

            this.setFocusable(true);
        }




        public void paintComponent(Graphics g) {

            Point currentMousePos = this.getMousePosition();


            int frameSeconds =0; //calculates the seconds per the frame count
            int frameMinutes = 0; // calculates the minutes based on the seconds which come from the frame count

            Graphics2D gg = (Graphics2D) g;

            gameMap.CycleUpdate(playerList.get(0), gg,this.getWidth(), this.getHeight(), backgroundImageList);

            //stop the program doing anything when the program is paused

            //loop the game per regular cycle timers the game speed which means that there is a functional fast forward button
            for(int i = 0;i<GameSpeed;i++) {

                //Only draw each object if the graphics are on and only calculate the movmenet if the game is not paused

                if(graphicsOn) {
                    /**
                     * The order that these following lines are very important
                     * this is the order that things are drawn,
                     * the last on on the list gets to draw over everyone else and thus will appear if overlapping with another object
                     */

                    Building.drawBuildings(gg, buildingsList, gameMap);

                    Frog.drawFrog(gg, frogList, gameMap, !gamePaused, playerList);

                    Player.drawPlayers(gg, playerList, gameMap, !gamePaused,
                            currentMousePos);

                    playerList.get(i).drawUI(gg, gameMap);

                    if(developerTool.isDebug())developerTool.drawScorebaord(gg, framecount, frameSeconds, frameMinutes
                            , roundCount, graphicsOn, gameMap, buildingsList,
                        playerList, frogList
                    );




                    gameMap.calcGravity_ArrayList(playerList);

                    framecount++;

                    frameSeconds = (framecount / 60); //calculates the seconds per the frame count
                    frameMinutes = frameSeconds / 60; // calculates the minutes based on the seconds which come from the frame count

                    //NEW ROUND
                    /**
                     * Given RoundDuration calculate when a new round occurs
                     *
                     * eg: round duration = 2 //it's in minutes
                     * everytime the frame minutes is evenly divisble into it then start a roun
                     */
                    try {
                        if (roundCount < frameMinutes / roundDuration
                            //|| noMovesLeft
                        ) {
                            roundCount++;
                        }
                    } catch (Exception e) {

                    }

                   // repaint();
                    linex2 = (playerList.get(0).getPosX()/gameMap.getMapWidth());
                    liney2 = (playerList.get(0).getPosY()/gameMap.getMapHeight());

                    gg.setColor(Color.black);

                    System.out.println("");

                    gg.drawLine(linex1,liney1,linex2,liney2);

                }
            }

        }

        public void calcNewRound(){
            //this is run to populate a new round
        }

        /**
         * This function calculates and handles the                    collisions for the following arrayLists:
         *
         * This function should be called before any objects are drwawn
         * Explosions: explosionList
         * Frog: frogList
         * GroundFighters: groundFighters
         * Projectiles: projectileList
         */
        protected void calcCollisions(){

            Collisions.calcPlayerCollisions(playerList,frogList,gameMap);

            Collisions.calcFrogPlayerCollisions(playerList,frogList,gameMap);

        }




        /**
         * This function will call various sub functions to populate the default objects
         * the default objects are pre populated to store the various values of images and sizes, colours, ect
         * to prevent serious load issues on calling each objet as the function caries out its duties
         */
        private void populateDefaultVariables(){
        }


        private void InitializeDefaultValues() {
            Color defaultC = new Color(50,50,50);

            System.out.println("InitializeDefaultValues");
            dateOutput.add("InitializeDefaultValues");

            defaultPipe = new Pipe(0,0,0,0,defaultC,
                    gameMap, null,null,null,null, false);


            //THE BLUE PLAYER FILE PATH
            // blueJetFilePath = "BlueJet.png";
            //blueJetFlipPath = "BlueJetFlipped.png";

            //The background image
            //General World Settings
            gamePaused = false; // this is a toggle for the 'p' button to pause all movement players and arrows at the time of creation but potentially enemies

            initPopulationSize = 1;

            //the following must have additional lines for additional built players

            //GROUND TROOP VALUES
            //use a variable size player list to allow for more players later on / to allow for some to die
            frogList = new ArrayList<>();

            playerList = new ArrayList<>();

            buildingsList = new ArrayList<>();

            graphicsOn = true;

            int roundDuration= 2;//this is in minutes
        }



        /**
         * The mouse listener is used to activate various actions when the player / user should use the mouse
         */

        MouseListener ratListner = new MouseListener() {


            @Override
            public void mouseClicked(MouseEvent e) {
                /**
                 System.out.println("Mouse: " + e.getX() + ", " + e.getY()+" Button: "+e.getButton());


                 if (playerList != null) {
                 for (Player john : playerList) {

                 if (john != null) {

                 if (john.getWeaponList() != null) {

                 john.calcMousePressedWeaponListKeys(e);

                 }

                 }

                 }
                 }
                 */
            }

            /**
             * ToDo
             * use the functions mouse pressed and mouse released to store a starting tile and ending tile
             * from these two tiles attempt to place from the top left the maximum of the player selected building
             * such buildings list should then indidually be compared for the saefty functions eg: collision of buildings
             * <p>
             * This should allow the player to place a set of objects in a rectangular space
             * <p>
             * adding a player alt button possibly shift might allow for additional safety
             * <p>
             * eg: draggin and relaseing the mouse will create either along the columns or rows
             * but if the shift key is currenlty pressed then the mouse should default as described above wherin it goes along both the columns and rows
             * <p>
             * this may help faster building and more intutive design
             * <p>
             * such design should attempt to on some level provide a visual output of the currenlty selected tiles first by showing
             * the rectangle and then later by showing graphically but softened to show they haven't been placed images of the selected object as they will be placed
             */
            @Override
            /*
             * This function will activate when a mouse button is pressed
             */
            public void mousePressed(MouseEvent e) {
                if (playerList != null) {
                    for (Player john : playerList) {

                        if (john != null) {

                            if (john.getWeaponList() != null) {

                                john.calcMousePressedWeaponListKeys();



                                linex1 = e.getX();
                                liney1 = e.getY();
                            }

                        }

                    }
                }

            }


            @Override
            /*
             * Activates whenever a mouse button is released
             */
            public void mouseReleased(MouseEvent e) {


                if (playerList != null) {
                    for (Player john : playerList) {

                        if (john != null) {

                            if (john.getWeaponList() != null) {

                                john.calcMouseReleasedWeaponListKeys();

                            }

                        }

                    }
                }

            }


            @Override
            /*
             * This function will trigger with the first position of the mouse as it enters the window
             */
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            /*
             * This function will trigger with the last position of the mouse as it exits the window
             */
            public void mouseExited(MouseEvent e) {

            }

        };



}
