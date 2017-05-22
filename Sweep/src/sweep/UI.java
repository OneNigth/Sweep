package sweep;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class UI extends JFrame implements ActionListener {
	// 组件
	JButton[][] bt;
	JPanel jPanelSweep, jPanelInfo, jPanelAll;
	JMenuBar menuBar;
	JMenu menu1, menu2, menu3;
	JMenuItem model, record, help;
	JButton icbutton;
	// 计时使用的量
	JLabel timeCount;
	Timer timer;
	int GTime;
	// 地雷数量
	int Remaining = 10;
	// 地雷所在的行列
	int rows[];
	int cols[];
	
	//得到屏幕的尺寸-------屏幕设置
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 

	
	// 构造界面
	public UI(int Remaining) {
		
		// 模式更改------顺便改成整个屏幕大小的几分之一
		this.Remaining = Remaining;
		rows = new int[Remaining];
		cols = new int[Remaining];
		// 初始化组件及菜单栏
		initView();
		barView();
		// 布局流
		/**
		 * 模式选择待更改
		 */
		//模式更改布局随着更改
		GridLayout gridLayout = new GridLayout(Remaining, Remaining);

		jPanelSweep.setLayout(gridLayout);

		// 添加组件——————————菜单栏
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);

		menu1.add(model);
		menu2.add(record);
		menu3.add(help);

		// 菜单栏的子菜单事件监听
		model.addActionListener(this);
		record.addActionListener(this);
		help.addActionListener(this);

		// 信息栏
		// 滑稽图片
		icbutton = new JButton(new ImageIcon("smile.jpg"));
		icbutton.setBorderPainted(false);
		icbutton.setFocusPainted(false);
		icbutton.setMargin(new Insets(0, 0, 0, 0));
		icbutton.setContentAreaFilled(false);
		icbutton.addActionListener(this);
		// 计时功能

		timeCount = new JLabel("计时：" + 0);
		timer = new Timer(1000, new ActionListener() {
			int time = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				time++;

				// System.out.println(time);
				timeCount.setText("计时：" + time);
				GTime = time ;
			}
		});
		timer.start();
		
		// 剩余雷数
		JLabel less = new JLabel("雷数：" + Remaining);

		// 信息栏组装
		jPanelInfo.add(timeCount, BorderLayout.WEST);
		jPanelInfo.add(icbutton, BorderLayout.CENTER);
		jPanelInfo.add(less);

		// 添加组件——————————界面
		add(jPanelInfo, BorderLayout.NORTH);
		add(jPanelSweep, BorderLayout.CENTER);
		setJMenuBar(menuBar);

		setTitle("扫雷");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 界面的可控大小
		int height = screenSize.height; //windows的高度
		setBounds(0, 0, height/5*4, height/5*4);
		setResizable(false);
		setVisible(true);

	}

	// 菜单栏的组装
	public void barView() {
		menuBar = new JMenuBar();
		menu1 = new JMenu("开始");
		menu2 = new JMenu("历史纪录");
		menu3 = new JMenu("帮助");
		model = new JMenuItem("模式");
		record = new JMenuItem("历史记录");
		help = new JMenuItem("帮助");
		jPanelInfo = new JPanel();

		/**
		 * 模式选择待更改
		 */

		jPanelSweep = new JPanel();
		for (int i = 0; i < bt.length; i++) {
			for (int j = 0; j < bt[i].length; j++) {
				bt[i][j].addActionListener(this);
				// 组装进面板
				jPanelSweep.add(bt[i][j]);
			}

		}

	}

	public void initView() {
		// 初始化Button
		bt = new JButton[Remaining][Remaining];
		for (int i = 0; i < bt.length; i++) {
			for (int j = 0; j < bt[i].length; j++) {
				bt[i][j] = new JButton();
			}
		}

		// 组装地雷和数字
		// int[][] ran = new int[bt.length][bt.length];
		Random rans = new Random();

		int land = 0;
		for (int i = 0; land < Remaining;) {
			// 获取随机数
			int r = rans.nextInt(Remaining);
			int c = rans.nextInt(Remaining);
			for (int j = 0; j <= i; j++) {
				// 随机数不能重复
				if (rows[j] == r || cols[j] == c) {
					break ;
				}else{
					rows[land] = r;
					cols[land] = c;
					land++;
				}
			}
		}
		// 测试有无重复数组用的
//		 for(int i = 0 ; i<rows.length ; i++){
//		 System.out.print(rows[i]+ " "+cols[i]+"...");
//		 }
//		 System.out.println();
		// 地雷设置
		new ButtonTem(bt, rows, cols, this);
	}

	@Override
	// 菜单栏事件监听
	public void actionPerformed(ActionEvent arg0) {
		// 滑稽图片监听
		if (arg0.getSource() == icbutton) {
			initView();
//			System.out.println("重置了");
			dispose();
			new UI(Remaining);
		}
		// 开始菜单
		if (arg0.getSource() == model) {
			modelCheck();
		}
		// 历史纪录，可能删除
		if(arg0.getSource() == record){
			Histry histry = new Histry();
			histry.select();
		}
		// 帮助
		if(arg0.getSource() == help){
			JOptionPane.showConfirmDialog(null, "点击滑稽按钮可以重新开始\n参与制作组员：翁堉钏,许楠雄,姚俊\n完事", "只是看起来像帮助而已", JOptionPane.YES_NO_OPTION);
		}
	}

	// 模式的选择
	private void modelCheck() {

		Object[] possibleValues = { "简单模式", "普通模式", "困难模式" };
		Object Value = JOptionPane.showInputDialog(null, "我要玩：", "模式选择",
				JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
				possibleValues[0]);
		if (Value == "简单模式") {
			Remaining = 10;
			dispose();
			new UI(Remaining);
		}
		if (Value == "普通模式") {
			Remaining = 15;
			dispose();
			new UI(Remaining);
		}
		if (Value == "困难模式") {
			Remaining = 20;
			dispose();
			new UI(Remaining);
		}
	}
}
