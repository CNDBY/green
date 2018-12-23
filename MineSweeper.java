package bukeyong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class MineSweeper{
	//Declare some attribute.
	private JFrame frame;	
	private GridBagConstraints constraints ;
	private JMenuBar menuBar;
	//gameMenu	
	private JMenu gameMenu;
	private JMenuItem newGameItem;
	private JMenuItem optionItem;
	private JMenuItem exitItem;
	//helpMenu
	private JMenu helpMenu;
	private JMenuItem viewHelpMenu;
	private JMenuItem aboutMenu;
	
	//option
	private JMenuItem difficultyItem;
	private JMenuItem mediumItem;
	private JMenuItem simpleItem; 
	//Game attribute
	Timer timer;
	private String recordTime = "0.0s";
	private boolean firstClickFlag = true;
	private double gameTime;
	private int mineFlagNumber = 0;
	private int mineNumber = 10;
	private int yNumber = 9;
	private int xNumber = 9;
	private boolean isFail;
	private String[][] buttonValues;
	private Map<JButton,Integer> checkMap;
	private Map<JButton,Point>loadMap;
	//ButtonPane
	private JPanel buttonPane;
	private JButton[][] buttons;	
	private int buttonHeight;
	private Dimension buttonSize;
	//labelPane
	JPanel leftPane;
	JPanel rightPane;
	private JLabel timeLabel;
	private JTextField timeText;
	private JLabel mineNumLabel;
	private JTextField mineNumText;
	private JLabel sumMineLabel;
	private JTextField sumMineText;
	private JLabel recordLabel;
	private JTextField recordText;
	//Constructor. Create some variables.
	public MineSweeper() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		initializeWindowBar();
		initializeLabel();
		initializeButtons();
		do_LayoutChildWindow();
		//Set frame maximized.
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.repaint();
		frame.setVisible(true);
	}
	//Initialize MenuBar.
	private void initializeWindowBar() {		
				
		
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.BLACK);
		//gameMenu
		gameMenu = new JMenu("Game");
		gameMenu.setForeground(Color.WHITE);
		gameMenu.setFont(new Font("Dialog", Font.BOLD, 16));
		newGameItem = new JMenuItem("New Game");
		newGameItem.addActionListener(new MenuIteam());
		optionItem = new JMenu("Option");
		optionItem.addActionListener(new MenuIteam());
		difficultyItem = new JMenuItem("Difficulty");
		mediumItem = new JMenuItem("Medium");
		simpleItem = new JMenuItem("Simple");
		difficultyItem.addActionListener(new MenuIteam());
		simpleItem.addActionListener(new MenuIteam());
		mediumItem.addActionListener(new MenuIteam());
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new MenuIteam());
		//helpMenu
		helpMenu = new JMenu("Help");
		helpMenu.setForeground(Color.WHITE);
		helpMenu.setFont(new Font("Dialog", Font.BOLD, 16));
		viewHelpMenu = new JMenuItem("View Help");
		viewHelpMenu.addActionListener(new MenuIteam());
		aboutMenu = new JMenuItem("About");	
		aboutMenu.addActionListener(new MenuIteam());
		//Layout menu bar
		frame.setJMenuBar(menuBar);
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		//Layout Game menu
		gameMenu.add(newGameItem);
		gameMenu.add(optionItem);
		gameMenu.add(exitItem);
		optionItem.add(difficultyItem);
		optionItem.add(mediumItem);
		optionItem.add(simpleItem);
		//Layout Help menu
		helpMenu.add(viewHelpMenu);
		helpMenu.add(aboutMenu);			
	}
	//Initialize labels and texts.
	private void initializeLabel() {
		//Create variable
		leftPane = new JPanel();
		rightPane = new JPanel();
		constraints = new GridBagConstraints();
		timeLabel = new JLabel("    Times    ");
		timeText = new JTextField();				
		mineNumLabel = new JLabel("    Mines    ");
		mineNumText = new JTextField();
		sumMineLabel = new JLabel(" MineSum ");
		sumMineText = new JTextField();
		recordLabel = new JLabel("Recoed");
		recordText = new JTextField();
		//Set mineNumlable and mineNumText.
		//LeftPane
		leftPane.setLayout(new GridBagLayout());
		leftPane.setBackground(Color.BLACK);
		
		mineNumLabel.setForeground(Color.WHITE);
		mineNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mineNumLabel.setFont(new Font("Dialog", Font.BOLD, 34));
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.insets = new Insets(10,10,10,10);
		leftPane.add(mineNumLabel,constraints);		
		constraints.gridx=0;
		constraints.gridy=1;
		constraints.fill = GridBagConstraints.BOTH;
		mineNumText.setFont(new Font("Dialog", Font.BOLD, 34));
		mineNumText.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
		mineNumText.setEditable(false);
		mineNumText.setHorizontalAlignment(JTextField.CENTER);
		mineNumText.setText(String.valueOf(mineFlagNumber));
		mineNumText.setBackground(Color.BLACK);
		mineNumText.setForeground(Color.WHITE);
		leftPane.add(mineNumText,constraints);
		
		sumMineLabel.setForeground(Color.WHITE);
		sumMineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sumMineLabel.setFont(new Font("Dialog", Font.BOLD, 34));
		constraints.gridx=0;
		constraints.gridy=2;
		constraints.insets = new Insets(10,10,10,10);
		leftPane.add(sumMineLabel,constraints);		
		constraints.gridx=0;
		constraints.gridy=3;
		constraints.fill = GridBagConstraints.BOTH;
		sumMineText.setFont(new Font("Dialog", Font.BOLD, 34));
		sumMineText.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
		sumMineText.setEditable(false);
		sumMineText.setHorizontalAlignment(JTextField.CENTER);
		sumMineText.setText(String.valueOf(mineFlagNumber));
		sumMineText.setBackground(Color.BLACK);
		sumMineText.setForeground(Color.WHITE);
		sumMineText.setText(String.valueOf(mineNumber));
		leftPane.add(sumMineText,constraints);
		
		
		constraints.fill = GridBagConstraints.NONE;
		//RightPane
		
		rightPane.setLayout(new GridBagLayout());
		rightPane.setBackground(Color.black);
		constraints.gridx=0;
		constraints.gridy=0;
		timeLabel.setForeground(Color.white);
		timeLabel.setFont(new Font("Dialog", Font.BOLD, 34));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rightPane.add(timeLabel,constraints);		
		constraints.gridx=0;
		constraints.gridy=1;
		constraints.fill = GridBagConstraints.BOTH;
		timeText.setFont(new Font("Dialog", Font.BOLD, 34));
		timeText.setBackground(Color.BLACK);
		timeText.setForeground(Color.WHITE);
		timeText.setText(0.0+"s");
		timeText.setEditable(false);
		timeText.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
		timeText.setHorizontalAlignment(JTextField.CENTER);
		rightPane.add(timeText,constraints);
		constraints.fill = GridBagConstraints.BOTH;
		
		constraints.gridx=0;
		constraints.gridy=2;
		recordLabel.setForeground(Color.white);
		recordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recordLabel.setFont(new Font("Dialog", Font.BOLD, 34));
		rightPane.add(recordLabel,constraints);		
		constraints.gridx=0;
		constraints.gridy=3;
		constraints.fill = GridBagConstraints.BOTH;
		recordText.setFont(new Font("Dialog", Font.BOLD, 34));
		recordText.setBackground(Color.BLACK);
		recordText.setForeground(Color.WHITE);
		recordText.setText(0.0+"s");
		recordText.setEditable(false);
		recordText.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
		recordText.setHorizontalAlignment(JTextField.CENTER);
		rightPane.add(recordText,constraints);
		constraints.fill = GridBagConstraints.BOTH;
		
		
	}
	//Initialize buttons
	private void initializeButtons() {
		//Set buttonPane.
		buttonPane = new JPanel();	
		buttonPane.setLayout(new GridBagLayout());
		buttonPane.setBackground(Color.black);
		
		//Initialize buttons.
		buttons = new JButton[30][16];
		buttonValues = new String[30][16];
		checkMap = new HashMap<JButton,Integer>();
		loadMap = new HashMap<JButton,Point>();
		for(int i = 0;i<xNumber;i++) {
			for(int j=0;j<yNumber;j++) {
				buttonValues[i][j] = "";
			}
		}
		buttonHeight = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/yNumber*1.8/3);
		if(xNumber == 9) {
			buttonSize = new Dimension(buttonHeight,buttonHeight);
		}else if(xNumber == 16) {
			buttonSize = new Dimension(buttonHeight,buttonHeight);
		}else if(xNumber == 30) {
			buttonSize = new Dimension(buttonHeight,buttonHeight);
		}
		
		addButton();
		addMineToButton();
		addNumberToButton();
		
		for(int i =0 ;i<xNumber;i++) {
			for(int j = 0;j<yNumber;j++) {
				checkMap.put(buttons[i][j], 1);
				loadMap.put(buttons[i][j], new Point(i,j));
			}
		}
	}
	//Layout panes.
	private void do_LayoutChildWindow() {
		
		//Add leftPane;
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;		
		frame.getContentPane().add(leftPane,constraints);
		
		//Add buttonPane
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0;
		frame.getContentPane().add(buttonPane,constraints);
		
		//Add rightPane
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.weightx = 1;
	
		frame.getContentPane().add(rightPane,constraints);
		frame.getContentPane().setBackground(Color.black);
	
	}
	//Add button to buttonPane.	
	private void addButton() {
		for(int i = 0 ; i<xNumber ; i++) {
			for(int j = 0; j<yNumber;j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setOpaque(false);
				buttons[i][j].setBackground(Color.WHITE);
				buttons[i][j].setForeground(Color.BLACK);
				buttons[i][j].setFont(new Font("Dialog", Font.BOLD, 20));
				buttons[i][j].setMaximumSize(buttonSize);
				buttons[i][j].setMinimumSize(buttonSize);
				buttons[i][j].setPreferredSize(buttonSize);
				buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.white));
				buttons[i][j].addMouseListener(new ButtonActionListener());
				constraints.gridx = i;
				constraints.gridy = j;
				constraints.insets= new Insets(1,1,1,1);
				buttonPane.add(buttons[i][j],constraints);
			}
		}
	}
	//Generate mines.
	private void addMineToButton() {
		Random radom = new Random();
		int yRadom;
		int xRadom;
		for(int i =0 ;i<mineNumber;i++) {
			yRadom = radom.nextInt(yNumber);
			xRadom = radom.nextInt(xNumber);
			if(buttonValues[xRadom][yRadom].equals("*")) {
				i--;
			}else {
				buttonValues[xRadom][yRadom] ="*";
			}
		}
	}
	//Generate number for buttons according to mines.
	private void addNumberToButton() {
		int mineCount = 0;
		//Add the number to buttonValues according to mines.
		for(int i=0;i<xNumber;i++) {
			for(int j=0;j<yNumber;j++) {
				if(buttonValues[i][j].equals("*")) {
					continue;
				}else {
					mineCount =0;
					if(i+1<xNumber&&buttonValues[i+1][j].equals("*")) mineCount++;
					if(i-1>-1&&buttonValues[i-1][j].equals("*")) mineCount++;
					if(j+1<yNumber&&buttonValues[i][j+1].equals("*")) mineCount++;
					if(j-1>-1&&buttonValues[i][j-1].equals("*"))mineCount++;					
					if(i+1<xNumber&&j+1<yNumber&&buttonValues[i+1][j+1].equals("*"))mineCount++;
					if(i-1>-1&&j-1>-1&&buttonValues[i-1][j-1].equals("*"))mineCount++;
					if(i+1<xNumber&&j-1>-1&&buttonValues[i+1][j-1].equals("*"))mineCount++;
					if(i-1>-1&&j+1<yNumber&&buttonValues[i-1][j+1].equals("*"))mineCount++;
					if(mineCount == 0) {
						buttonValues[i][j] ="";
					}else {
						buttonValues[i][j] = String.valueOf(mineCount);
					}
					
				}
			}
		}

	}
	//Button ActionListener.
	class ButtonActionListener extends MouseAdapter{

		@Override
		public void mouseReleased(MouseEvent e) {
			//Execute first time.
			if(firstClickFlag) {
				firstClickFlag = false;
				timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {
						DecimalFormat format = new DecimalFormat("0.0");
						gameTime += 0.1;
						timeText.setText(String.valueOf(format.format(gameTime))+"s");
						timeText.repaint();
					}	
				},0, 100);
			}
			//Deal with the event.
			if(!isFail&&!isWin()) {
				JButton sourceButton = (JButton)e.getSource();
				int x = (int)loadMap.get(sourceButton).getX();
				int y = (int)loadMap.get(sourceButton).getY();
				if(e.getButton() == MouseEvent.BUTTON1&&checkMap.get(sourceButton)==1&&!sourceButton.getText().equals("#")) {
					if(buttonValues[x][y].equals("*")) {
						isFail = true;
						fail();
					}else {
						open(x,y);
						buttonPane.repaint();
					}
					if(isWin()) {
						win();
					}
				
				}else if(e.getButton() == MouseEvent.BUTTON3&&checkMap.get(sourceButton)==1) {
					if(sourceButton.getText().equals("#")) {
						sourceButton.setOpaque(false);
						sourceButton.setBackground(Color.black);
						sourceButton.setForeground(Color.white);
						sourceButton.setText("");
						mineFlagNumber--;
						
					}else {
						sourceButton.setOpaque(true);
						sourceButton.setBackground(Color.black);
						sourceButton.setForeground(Color.white);
						sourceButton.setText("#");
						mineFlagNumber++;						
					}
					mineNumText.setText(String.valueOf(mineFlagNumber));
					mineNumText.repaint();
					if(isWin()) {
						win();
					}
				}
				
			}
			
		}
	
	}
	//Layout the buttonPane when failing.
	private void fail() {
		if(timer!=null) {
			timer.cancel();
		}	
		for(int i =0 ;i<xNumber;i++) {
			for(int j = 0;j<yNumber;j++) {
				checkMap.put(buttons[i][j], 0);		
				if(buttonValues[i][j].equals("*")) {					
					buttons[i][j].setText("*");
					buttons[i][j].setForeground(Color.black);
					buttons[i][j].setBackground(Color.red);
					buttons[i][j].setFont(new Font("Dialog", Font.BOLD, 40));
					buttons[i][j].setOpaque(true);
				}
			}
		}
		JOptionPane.showMessageDialog(frame, "You fail", "Fail", JOptionPane.INFORMATION_MESSAGE);
	}
	//Layout the buttonPane when wining
	private void win() {
		//Update the recordTime value.
		if(Double.valueOf(timeText.getText().substring(0, timeText.getText().length()-1))
				<Double.valueOf(recordTime.substring(0, recordTime.length()-1))||recordTime.equals("0.0s")){
			recordTime = timeText.getText();		
		}
		
		if(timer!=null) {
			timer.cancel();
		}
		//Make buttons not enable.
		for(int i =0 ;i<xNumber;i++) {
			for(int j = 0;j<yNumber;j++) {
				checkMap.put(buttons[i][j], 0);		
			}
		}
		JOptionPane.showMessageDialog(frame, "You win", "Win", JOptionPane.INFORMATION_MESSAGE);
	}
	//open the buttons when click the buttons.
	private int open(int x,int y) {
		JButton sourceButton = buttons[x][y];
		if(checkMap.get(sourceButton)==1) {
			sourceButton.setText(buttonValues[x][y]);
			sourceButton.setOpaque(true);
			sourceButton.setBackground(Color.WHITE);
			sourceButton.setForeground(Color.black);
			checkMap.put(sourceButton, 0);			
			if(buttonValues[x][y].equals("*")) {
				return 0;
			}else {
				//Open else buttons.
				if(x+1<xNumber&&!buttonValues[x+1][y].equals("*")&&buttonValues[x][y].equals("")) open(x+1,y);
				if(x-1>-1&&!buttonValues[x-1][y].equals("*")&&buttonValues[x][y].equals("")) open(x-1,y);
				if(y+1<yNumber&&!buttonValues[x][y+1].equals("*")&&buttonValues[x][y].equals("")) open(x,y+1);
				if(y-1>-1&&!buttonValues[x][y-1].equals("*")&&buttonValues[x][y].equals("")) open(x,y-1);	
				if(x+1<xNumber&&y+1<yNumber&&!buttonValues[x+1][y+1].equals("*")&&buttonValues[x][y].equals("")) open(x+1,y+1);
				if(x-1>-1&&y-1>-1&&!buttonValues[x-1][y-1].equals("*")&&buttonValues[x][y].equals("")) open(x-1,y-1);
				if(x+1<xNumber&&y-1>-1&&!buttonValues[x+1][y-1].equals("*")&&buttonValues[x][y].equals("")) open(x+1,y-1);
				if(x-1>-1&&y+1<yNumber&&!buttonValues[x-1][y+1].equals("*")&&buttonValues[x][y].equals("")) open(x-1,y+1);
				
			}
		}
		return 0;		
	}	
	//Menu ActionListener.
	class MenuIteam implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case "Exit":
				System.exit(0);
				break;
			case "New Game":
				reSet();			
				recordText.setText(recordTime);
				break;
			case "View Help":
				JOptionPane.showMessageDialog(frame, "This is a MineSweeper game", "View Help", JOptionPane.INFORMATION_MESSAGE);
				break;
			case "About":
				JOptionPane.showMessageDialog(frame, "Name:", "About", JOptionPane.INFORMATION_MESSAGE);
				break;
			case "Difficulty":
				yNumber = 16;
				xNumber = 30;
				mineNumber = 40;
				reSet();
				break;
			case "Simple":
				yNumber = 9;
				xNumber = 9;
				mineNumber = 10;
				reSet();
				
				break;
			case "Medium":
				yNumber = 16;
				xNumber = 16;
				mineNumber = 25;
				reSet();
				
			}
		}

	}
	//Layout the Pane.
	private void reSet() {		
		if(timer!=null) {
			timer.cancel();
		}
		//Initialize the pane.
		frame.getContentPane().removeAll();
		frame.getContentPane().repaint();		
		leftPane.removeAll();
		leftPane.repaint();
		rightPane.removeAll();
		rightPane.repaint();
		buttonPane.removeAll();
		buttonPane.repaint();
		initializeLabel();
		initializeButtons();
		do_LayoutChildWindow();
		frame.getContentPane().revalidate();
		leftPane.revalidate();
		rightPane.revalidate();
		buttonPane.revalidate();
		isFail = false;
		mineFlagNumber = 0;
		firstClickFlag =true;
		gameTime = 0;
	}
	//Determine if is wining.
	private boolean isWin() {
		for(int i= 0;i<xNumber;i++) {
			for(int j = 0;j<yNumber;j++) {
				if(buttonValues[i][j].equals("*")) {
					if(!buttons[i][j].getText().equals("#")) {
						return false;
					}
				}else {
					if(checkMap.get(buttons[i][j])==1) {
						return false;
					}
				}
			}
				
		}
		return true;
	}
	//Run the class.
	public static void main(String[] args) {
		MineSweeper mineSweeper = new MineSweeper();
	}
}