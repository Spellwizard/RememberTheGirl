import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
//This is used to track keyboard inputs
import java.awt.event.KeyListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu{

    //make the class and start the initial sheet construction needed

    public static MenuSub frame = new MenuSub("Game Menu");
   public static void makeVisible(){
    //   frame.setVisible(true);
       frame.betterVisible();
   }

    public static void main(String[]args){
        frame.setTitle("Program Main Menu");

        //make sure the window will stop program on closing window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //make the window visible
        frame.setVisible(true);
        }
    }


class MenuSub extends JFrame implements ActionListener {

    OptionsMenu options_Menu = null;

    AboutWindow About_Menu = null;

    String BackgroundPath = "MenuBackground.png";

    private BufferedImage BackgroundImage;

    JFrame GameJFrame = null;

    private Button SGame = new Button("Start Program");

    private BCredit CreditMenu = null;


    //make a new container to have the subitems
    Container MenuButtonsContainer = new Container();


    public void makeVisible(){this.setVisible(true);}

    KeyListener InputTracker = new KeyListener() {


        @Override
        public void keyPressed(KeyEvent e) {
            // for quick testing and use of find each key code on the fly
            //System.out.println(e.getKeyCode()+" ");

            //Frog movements for W,A,S,D
            // and make sure that such a move won't move the player off the map

            //Check if key 'W' is pressed to move character up

            //System.out.println("KeyPressed: "+e.getKeyCode());
        }
        //these two are needed but will be unused
        @Override
        public void keyTyped(KeyEvent e){}
        @Override
        public void keyReleased(KeyEvent e) {}

    };

    private BufferedImage imageGetter(String filePathName){
        try {

            return ImageIO.read(new File(filePathName));
        } catch (IOException e) {


            e.printStackTrace();
        }
        return null;
    }

    public MenuSub(String windowName){
        BackgroundImage=imageGetter(BackgroundPath);


        System.out.println("New Sheet made: "+windowName);

        this.setName(windowName);

        this.init();


    }
    public void betterVisible(){
        this.makeVisible();
    }

    //used on making a new sheet class object
    private void init(){
        this.setBackground(Color.BLACK);
        this.setSize(1000,800);

        //Add a focus on the window to track for keyboard inputs
        this.addKeyListener(InputTracker);
        MainMenu();
        System.out.println("\nThis only runs once\n");
        repaint();

    }

    @Override
    public void paint(Graphics g){
        super.paintComponents(g);
        super.paint(g);
        if(BackgroundImage!=null) {
            g.drawImage(BackgroundImage, 0, 0,getWidth(),getHeight(), null);
        }

    }

    //called when the mainmenu needs to be drawn
    //it should clear the window and set the mainmenu up with its buttons and items

    private void MainMenu(){

        //the result should be the remaining space that won't be taken up by the buttons and thus can be divided by the number of buttons
        //and used as the y offset
        int ButtonYOffset = 40;

        initMenuImages();

        //MenuButtonsContainer.add(new ImageIO(BackgroundImage));

        System.out.println("MainMenu Function running");

        //This is used for some 'fancy' math but must be updated if additional buttons are added/ removed
        int MainMenuButtonCount = 4;

        //The Button's width in pixels
        int ButtonWidth = 250;
        //the buttons height in pixels
        int ButtonHeight = 60;
        //The starting Y position of the Buttons
        int ButtonYPos = (getHeight()-(
                ButtonHeight+ButtonHeight)
                 ) -
                (MainMenuButtonCount *
                        (ButtonHeight + ButtonYOffset));

        //The X coordinate for all the button positions below
        int ButtonXPos = (570)
        -ButtonWidth;

        //The gap between Buttons by calculating the available space compared to the number of buttons

        //the usable range is from the ButtonYPos to the height of the window, then subtract that number from
        int totalUsableYSpace = ButtonYPos-getHeight();
        //the height of the buttons by the number of total buttons
        int totalYButtonnsUsed = ButtonHeight - MainMenuButtonCount;


        //Button Font
        Font bFont = new Font("TimesRoman", Font.PLAIN, 25);

        //Primary buttons

        //Start Game button 'Play'
        //set a code to check when any buttons are clicked

        SGame.setActionCommand("SGame");
        SGame.setVisible(true);
        SGame.setBackground(Color.GREEN); // set the background color of the button
        // Bconnect.setForeground(Color.WHITE); // set the text color of the button
        SGame.addActionListener(this);
        SGame.setFont(bFont);



        //Program Options
        Button BOptions = new Button("Programs Options");

        BOptions.setActionCommand("option");
        BOptions.setVisible(true);
        BOptions.setBackground(Color.orange); // set the background color of the button
        BOptions.addActionListener(this);
        BOptions.setFont(bFont);

        //Program About
        Button BAbout = new Button("About Programs");

        BAbout.setActionCommand("BAbout");
        BAbout.setVisible(true);
        BAbout.setBackground(Color.blue); // set the background color of the button
        BAbout.setForeground(Color.white); // set the text color of the button
        BAbout.addActionListener(this);
        BAbout.setFont(bFont);

        //Program Credits
        Button BCredit = new Button("Credits");

        BCredit.setActionCommand("BCredit");
        BCredit.setVisible(true);
        BCredit.setBackground(Color.blue); // set the background color of the button
        BCredit.setForeground(Color.white); // set the text color of the button
        BCredit.addActionListener(this);
        BCredit.setFont(bFont);


        //quit button the close the window
        //make the quit button
        Button BQuit = new Button("Quit Program");
        //set a code to check when any buttons are clicked
        BQuit.setActionCommand("BQuit");
        BQuit.addActionListener(this);
        BQuit.setVisible(true);
        BQuit.setBackground(Color.RED);
        BQuit.setForeground(Color.WHITE);
        BQuit.setFont(bFont);


        //Set the bounds for the buttons
        //Start Game
        SGame.setBounds(ButtonXPos,ButtonYPos,
                ButtonWidth, ButtonHeight);
        ButtonYPos=(ButtonYPos+ButtonYOffset+ButtonHeight);
        //Program options
        BOptions.setBounds(ButtonXPos, ButtonYPos,
                ButtonWidth, ButtonHeight);
        ButtonYPos=(ButtonYPos+ButtonYOffset+ButtonHeight);

        BAbout.setBounds(ButtonXPos, ButtonYPos,
                ButtonWidth, ButtonHeight);
        ButtonYPos=(ButtonYPos+ButtonYOffset+ButtonHeight);

        BCredit.setBounds(ButtonXPos, ButtonYPos,
                ButtonWidth, ButtonHeight);
        ButtonYPos=(ButtonYPos+ButtonYOffset+ButtonHeight);

        //Exit Program
        BQuit.setBounds(ButtonXPos, ButtonYPos,
                ButtonWidth, ButtonHeight);
        ButtonYPos=(ButtonYPos+ButtonYOffset+ButtonHeight);

        System.out.println("Window Height: "+this.getHeight()+" Final Button Y Pos: "+ButtonYPos);

        //add all the buttons to the Container
        MenuButtonsContainer.add(SGame);
        MenuButtonsContainer.add(BOptions);
        MenuButtonsContainer.add(BAbout);
        MenuButtonsContainer.add(BCredit);
        MenuButtonsContainer.add(BQuit);

        //Make sure the container will be visible
        MenuButtonsContainer.setVisible(true);

        MenuButtonsContainer.setForeground(Color.black);

        this.add(MenuButtonsContainer);


    }


    //this can be used to check if a button, check box or the like is used
    public void actionPerformed(ActionEvent ae){

        String action = ae.getActionCommand();

        //check to see what button was pressed and execute relevant actions
        if(action.equals("BQuit")){
            System.exit(0);
        }
        //start the game
        else if(action.equals("SGame")){
            GameJFrame = null;
            GameJFrame = makeNewGameWindow();
            this.setVisible(false); // hides the menu window

        }
        else if(action.equals("BCredit")){
            System.out.println("Starting Credits Window");

            CreditMenu = new BCredit("Credit Window");

            //On Close of game window go back to the menu window
            CreditMenu.addWindowListener(new WindowAdapter() {
                                             @Override
                                             public void windowClosing(WindowEvent e) {
                                                 System.out.println("Closing Window");


                                                 Menu.makeVisible();
                                                 CreditMenu = null;
                                             }
            });



            //make the window visible
            CreditMenu.setVisible(true);

            this.setVisible(false);
        }

        //open the option window
        else if(action.equals("option")){
            System.out.println("Starting options");


            options_Menu = new OptionsMenu("Options Window");

            //On Close of game window go back to the menu window
            options_Menu.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.out.println("Closing Window");



                    Menu.makeVisible();
                    options_Menu= null;

                }
            });

                //make the window visible
            options_Menu.setVisible(true);

        this.setVisible(false);
        }

        else if(action.equals("BAbout")){
            System.out.println("Starting About Window");

            About_Menu = new AboutWindow("About Window");

            //On Close of game window go back to the menu window
            About_Menu.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.out.println("Closing Window");



                    Menu.makeVisible();
                    About_Menu= null;
                }
            });



            //make the window visible
            About_Menu.setVisible(true);

            this.setVisible(false);
        }

        }

        public JFrame makeNewGameWindow(){
                JFrame frame = new JFrame("RememberTheGirl Project 2/10/2021");
                Container c = frame.getContentPane();
                c.setBackground(Color.lightGray);
                frame.setSize(900,700);
                frame.setLocationRelativeTo(null);

                try {
                    frame.add(
                            new GameCanvas()
                    );
                    System.out.println("\n"+"Somehow this worked???"+"\n");
                }
                catch(Exception e){
                    System.out.println("\n\nSystemError"+e.toString()+"\n");
                }

                frame.setVisible(true);

                //On Close of game window go back to the menu window
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.out.println("Closing Window");
                        Menu.makeVisible();
                    }
                });

        return frame;
        }


        public void initMenuImages(){

        }

    }




