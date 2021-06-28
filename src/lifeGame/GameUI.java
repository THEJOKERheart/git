package lifeGame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3183157984243514253L;
	
	private JPanel contentPane;
	private final int Rect_x = 800;
	private final int Rect_y = 800;
	private int RectSize_x;		 
	private int RectSize_y;		 
	private int RowNum;			 
	private int ColNum;			 
	private GameMap gameMap;	 
	private boolean[][] map;
	
	private JPanel BtnPanel;
	private JButton CreateBtn;
	private JButton SingleStepBtn;
	private JButton ContinueStepBtn;
	private JButton EndBtn;
	private JSpinner RowSpinner;
	private JLabel RowLabel;
	private JLabel ColLabel;
	private JSpinner ColSpinner;
	private MShowPanel ShowPanel;
	private JButton ChangeBtn;
	private MouseDragListener MouseDrag;
	private MouseDownListener MouseDown;
	private ChangeBtnListener RandomGeneration;
	private CreateBtnListener Create;
	private ClearBtnListener Clear;
	private BeginBtnListener Begin;
	private SingleStepBtnListener SingleStep;
	private ContinueStepBtnListener ContinueStep;
	private EndBtnListener End;
	private JButton BeginBtn;
	private JButton ClearBtn;
	private Timer t;
	private JSpinner DeathSpinner;
	private JLabel DeathLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameUI frame = new GameUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameUI() {
		initData();
		
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, Rect_x+17,Rect_y+100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		BtnPanel = new JPanel();
		BtnPanel.setForeground(Color.WHITE);
		contentPane.add(BtnPanel, BorderLayout.NORTH);
		BtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		DeathLabel = new JLabel("死亡率");
		BtnPanel.add(DeathLabel);
		
		DeathSpinner = new JSpinner();
		DeathSpinner.setModel(new SpinnerNumberModel(50, 0, 100, 10));
		BtnPanel.add(DeathSpinner);
		
		RowLabel = new JLabel("行数");
		RowLabel.setForeground(Color.BLACK);
		BtnPanel.add(RowLabel);
		
		RowSpinner = new JSpinner();
		RowSpinner.setModel(new SpinnerNumberModel(100, 0, 100, 1));
		BtnPanel.add(RowSpinner);
		
		ColLabel = new JLabel("列数");
		BtnPanel.add(ColLabel);
		
		ColSpinner = new JSpinner();
		ColSpinner.setModel(new SpinnerNumberModel(100, 0, 100, 1));
		BtnPanel.add(ColSpinner);
		
		ChangeBtn = new JButton("修改");
		RandomGeneration = new ChangeBtnListener();
		ChangeBtn.addActionListener(RandomGeneration);
		BtnPanel.add(ChangeBtn);
		
		CreateBtn = new JButton("生成");
		Create = new CreateBtnListener();
		CreateBtn.addActionListener(Create);
		BtnPanel.add(CreateBtn);
		
		ClearBtn = new JButton("清空");
		Clear = new ClearBtnListener();
		ClearBtn.addActionListener(Clear);
		BtnPanel.add(ClearBtn);
		
		
		BeginBtn = new JButton("开始演化");
		Begin = new BeginBtnListener();
		BeginBtn.addActionListener(Begin);
		BtnPanel.add(BeginBtn);
		
		SingleStepBtn = new JButton("单步");
		SingleStep = new SingleStepBtnListener();
		BtnPanel.add(SingleStepBtn);
		
		ContinueStepBtn = new JButton("持续");
		ContinueStep = new ContinueStepBtnListener();
		BtnPanel.add(ContinueStepBtn);
		
		EndBtn = new JButton("结束");
		End = new EndBtnListener();
		BtnPanel.add(EndBtn);
		
		ShowPanel = new MShowPanel();
		MouseDrag = new MouseDragListener();
		MouseDown = new MouseDownListener();
		ShowPanel.addMouseMotionListener(MouseDrag);
		ShowPanel.addMouseListener(MouseDown);
		contentPane.add(ShowPanel, BorderLayout.CENTER);
	}
	private void initData()
	{
		RowNum = 100;
		ColNum = 100;
		RectSize_x = 800/ColNum;
		RectSize_y = 800/RowNum;
		gameMap = new GameMap(RowNum,ColNum);
		gameMap.randomMap();
		map = gameMap.getMap();
	}
	public void drawMap()
	{
		ShowPanel.paint(ShowPanel.getGraphics());
	} 
	public void changeMap(int row, int col) {
		map[row][col] = !map[row][col];
		
		Graphics g = ShowPanel.getGraphics();
		if(map[row][col] == true) {
			g.setColor(new Color(0,0,0));
			g.fillRect(col*RectSize_x + 1, row*RectSize_y + 1, RectSize_x - 1, RectSize_y -1);
		}
		else {
			g.clearRect(col*RectSize_x + 1, row*RectSize_y + 1, RectSize_x - 1, RectSize_y -1);
		}
	}
	/**
	 ** 内部类定义区
	 **/
	private class MShowPanel extends JPanel{
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			//画地图
			for(int i = 0;i<=RowNum;i++)
			{
				g.drawLine(0, i*RectSize_y, RectSize_x*ColNum, i*RectSize_y);
			}
			for(int j = 0;j<=ColNum;j++)
			{
				g.drawLine(j*RectSize_x, 0, j*RectSize_x, RectSize_y*RowNum);
			}
			//画细胞
			for(int i = 0;i<RowNum;i++) {
				for(int j = 0; j < ColNum; j++) {
					if(map[i][j] == true) {
						g.setColor(new Color(0,0,0));
						g.fillRect(j*RectSize_x + 1, i*RectSize_y + 1, RectSize_x - 1, RectSize_y -1);
					}
					else {
						g.clearRect(j*RectSize_x + 1, i*RectSize_y + 1, RectSize_x - 1, RectSize_y -1);
					}
				}
			}
		}
	}
	//提交修改
	private class ChangeBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			RowNum = Integer.parseInt(RowSpinner.getValue().toString());
			ColNum = Integer.parseInt(ColSpinner.getValue().toString());
			GameLogic.setDeathRate(Integer.parseInt(DeathSpinner.getValue().toString()));
			
			RectSize_x = 800/ColNum;
			RectSize_y = 800/RowNum;
			
			ShowPanel.getGraphics().clearRect(0, 0, Rect_x + 1, Rect_y + 1);
	
			gameMap.changeMap(RowNum, ColNum);
			gameMap.randomMap();
			map = gameMap.getMap();
			
			drawMap();
		}	
	}
	//清空
	private class ClearBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			gameMap.clearMap();
			map = gameMap.getMap();
			drawMap();
		}
	}
	//鼠标滑动事件
	private class MouseDragListener implements MouseMotionListener{
		public void mouseDragged(MouseEvent e) {
			int y = e.getPoint().y;
			int x = e.getPoint().x;
			if(x <= 0 || x >= RectSize_x*ColNum || y <= 0 || y >= RectSize_y*RowNum)
				return;
			int row = y / RectSize_y;
			int col = x / RectSize_x;
			
			changeMap(row,col); 
		}
		@Override
		public void mouseMoved(MouseEvent arg0) {};
	}
	//鼠标点击事件
	private class MouseDownListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			int c = e.getButton();
			if(c == MouseEvent.BUTTON1)
			{
				int y = e.getY();
				int x = e.getX();
				if(x <= 0 || x >= RectSize_x*ColNum || y <= 0 || y >= RectSize_y*RowNum)
					return;
				int row = y / RectSize_y;
				int col = x / RectSize_x;
				
				changeMap(row,col); 
				
			}
		}
	}

	//随机生成
	private class CreateBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			gameMap.randomMap();
			map = gameMap.getMap();
			//���»�ͼ
			drawMap();
		}
	}
	//开始
	private class BeginBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//点击开始之后，诸多事件不在响应。将相应监听器注销
			ChangeBtn.removeActionListener(RandomGeneration);
			ClearBtn.removeActionListener(Clear);
			CreateBtn.removeActionListener(Create);
			ShowPanel.removeMouseListener(MouseDown);
			ShowPanel.removeMouseMotionListener(MouseDrag);
			BeginBtn.removeActionListener(Begin);
			//一些响应开始。注册响应监听器
			SingleStepBtn.addActionListener(SingleStep);
			ContinueStepBtn.addActionListener(ContinueStep);
			EndBtn.addActionListener(End);
			
			t = new Timer();
		}
	}
	//单步
	private class SingleStepBtnListener implements ActionListener{ 
		public void actionPerformed(ActionEvent e) {
			if(t.isActive)
				t.isActive = false;
			gameMap.NextStatus();
			map = gameMap.getMap();
			drawMap();
		}
	}
	//持续
	private class ContinueStepBtnListener implements ActionListener{ 
		public void actionPerformed(ActionEvent e) {
			if(t.isActive == false) {
				t = new Timer();
			}
			if(t.isRun() == false)
				t.start();
		}
	}
	private class EndBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//点击结束后，诸多事件不在响应。
			//与开始按钮操作相反
			ChangeBtn.addActionListener(RandomGeneration);
			ClearBtn.addActionListener(Clear);
			CreateBtn.addActionListener(Create);
			ShowPanel.addMouseListener(MouseDown);
			ShowPanel.addMouseMotionListener(MouseDrag);
			BeginBtn.addActionListener(Begin);
			SingleStepBtn.removeActionListener(SingleStep);
			ContinueStepBtn.removeActionListener(ContinueStep);
			EndBtn.removeActionListener(End);
			
			if(t.isActive)
				t.isActive = false;
		}
		
	}
	private class Timer extends Thread{
		public boolean isActive = true;
		private boolean isRun = false;
		public boolean isRun() {
			boolean temp = isRun;
			isRun = true;
			return temp;
		}
		public void run() {
			while(isActive) {
				gameMap.NextStatus();
				map = gameMap.getMap();
				drawMap();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

