package sweep;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.CharBuffer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ButtonTem implements ActionListener {
	JButton[][] bt;
	// 地雷数量的数组
	int[][] num;
	// 初始化界面使用的
	UI ui;
	// 地雷所在的行列
	int rows[];
	int cols[];

	public ButtonTem(JButton bt[][], int rows[], int cols[], UI ui) {
		// 初始化数据
		this.bt = bt;
		this.rows = rows;
		this.cols = cols;
		this.ui = ui;
		// 地雷数量的数组，先全部初始值为0
		num = new int[bt.length][bt.length];
		for (int i = 0; i < num.length; i++) {
			for (int j = 0; j < num[i].length; j++) {
				// bt[i][j].setEnabled(true);
				num[i][j] = 0;

			}
		}
		// 埋下地雷num的变化...首先计算好几个雷是否挨边的情况
		for (int i = 0; i < bt.length; i++) {
			putIt(i);
		}
		for (int i = 0; i < bt.length; i++)
			num[rows[i]][cols[i]] = 9;

		// 地雷在的地方----测试
//		for (int i = 0; i < num.length; i++) {
//			for (int j = 0; j < num[i].length; j++) {
//
//				System.out.print(num[i][j] + ",");
//			}
//			System.out.println();
//		}

		// 设置按钮监听
		for (int i = 0; i < bt.length; i++) {
			for (int j = 0; j < bt[i].length; j++) {
				bt[i][j].addActionListener(this);
			}
		}
	}

	private void putIt(int count) {
		// 旁边的八个位置++ if不越界
		if (rows[count] - 1 >= 0 && cols[count] - 1 >= 0)
			num[rows[count] - 1][cols[count] - 1]++;
		if (rows[count] - 1 >= 0)
			num[rows[count] - 1][cols[count]]++;
		if (rows[count] - 1 >= 0 && cols[count] + 1 < bt.length)
			num[rows[count] - 1][cols[count] + 1]++;
		if (cols[count] - 1 >= 0)
			num[rows[count]][cols[count] - 1]++;
		if (cols[count] + 1 < bt.length)
			num[rows[count]][cols[count] + 1]++;
		if (rows[count] + 1 < bt.length && cols[count] - 1 >= 0)
			num[rows[count] + 1][cols[count] - 1]++;
		if (rows[count] + 1 < bt.length)
			num[rows[count] + 1][cols[count]]++;
		if (rows[count] + 1 < bt.length && cols[count] + 1 < bt.length)
			num[rows[count] + 1][cols[count] + 1]++;
	}

	int ii = 0;
	int jj = 0;
	@Override
	public void actionPerformed(ActionEvent e) {
		// 获取点击在哪个位置
		for (int i = 0; i < bt.length; i++) {
			for (int j = 0; j < bt[i].length; j++) {
				if (e.getSource() == bt[i][j]) {
					ii = i;
					jj = j;
				}
			}
		}
		if (num[ii][jj] == 9) {
			// 输了----地雷的图案有点怪异，senenabled惹得
			bt[ii][jj].setEnabled(false);
			bt[ii][jj].setIcon(new ImageIcon("boom.jpg"));
			bt[ii][jj].setFocusPainted(false);
			bt[ii][jj].setContentAreaFilled(false);
			int option = JOptionPane.showConfirmDialog(null, "点击确定重新开始", "你输了",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_NO_OPTION) {
				// 点击确定，则重新开始
				ui.dispose();
				ui = new UI(ui.Remaining);
			} else if (option == JOptionPane.NO_OPTION) {
				// 点击了取消，则显示全部地雷，计时停止
				ui.timer.stop();
				for (int i = 0; i < bt.length; i++) {
					for (int j = 0; j < bt[i].length; j++) {
						if (num[i][j] == 9) {
							bt[i][j].setEnabled(false);
							bt[i][j].setIcon(new ImageIcon("boom.jpg"));
						}
					}
				}
			}

		}

		// 按钮按下后递归
		// 0123是空雷区的方向，分别是右下，右上，左下，左上
		for(int i = 0 ; i<4 ; i++){
			Thread thread = new myThread(i);
			thread.start();
			try {
				thread.sleep(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
		}
//		new Thread(){
//			public void run() {
//				Enabled(ii, jj, 0);
//			};
//		}.start();
//		new Thread(){
//			public void run() {
//				Enabled(ii, jj, 1);
//			};
//		}.start();
//		new Thread(){
//			public void run() {
//				Enabled(ii, jj, 2);
//			};
//		}.start();
//		new Thread(){
//			public void run() {
//				Enabled(ii, jj, 3);
//			};
//		}.start();
		

		// 是否 胜利
		int count = 0;// 计数器
		int land = 0;
		for (int i = 0; i < bt.length; i++) {
			for (int j = 0; j < bt[i].length; j++) {
				if (bt[i][j].isEnabled() && num[i][j] == 9) {
					count++;
				} else if (!bt[i][j].isEnabled()) {
					land++;
//					System.out.println(land);
				}
			}
		}
		// 胜利的弹窗
		if (count == ui.Remaining
				&& land == bt.length * bt.length - ui.Remaining) {
			int time = ui.GTime;
			String model = "";
			JOptionPane.showConfirmDialog(null, "使用了：" + time + "秒",
					"YOU WIN!!", JOptionPane.YES_NO_OPTION);
			// 计时停止-----记录数据
			ui.timer.stop();
			if (ui.Remaining == 10) {
				model = "简单";
			} else if (ui.Remaining == 15) {
				model = "普通";
			} else if (ui.Remaining == 20) {
				model = "困难";
			}
			Histry histry = new Histry(time, model);
		}
	}
	//添加线程
	class myThread extends Thread{
		int num = 0 ;
		public myThread(int num) {
			this.num = num;
		}
		public void run() {
			Enabled(ii, jj, num);
		}
		
	}
	// 递归调用
	private void Enabled(int ii, int jj, int model) {
		// 显示的方块
		setButton(ii, jj);
		if (model == 0) {
			// 右下
			if (ii < bt.length - 1 && num[ii][jj] == 0) {
				if (num[ii + 1][jj] != 0) {
					// 边缘的雷数
					// around(ii+1, jj);
					// return;
				}
				Enabled(ii + 1, jj, model);

			}
			if (jj < bt.length - 1 && num[ii][jj] == 0) {
				if (num[ii][jj + 1] != 0) {
					// 边缘的雷数
					// around(ii, jj+1);
					// return;
				}
				Enabled(ii, jj + 1, model);
			}
		}
		if (model == 1) {
			// 右上
			if (ii > 0 && num[ii][jj] == 0) {
				if (num[ii - 1][jj] != 0) {
					// 边缘的雷数
					// around(ii-1, jj);
					// return;
				}
				Enabled(ii - 1, jj, model);
			}
			if (jj < bt.length - 1 && num[ii][jj] == 0) {
				if (num[ii][jj + 1] != 0) {
					// 边缘的雷数
					// around(ii, jj+1);
					// return;
				}
				Enabled(ii, jj + 1, model);
			}
		}
		if (model == 2) {
			// 左下
			if (ii < bt.length - 1 && num[ii][jj] == 0) {
				if (num[ii + 1][jj] != 0) {
					// 边缘的雷数
					// around(ii+1, jj);
					// return;
				}
				Enabled(ii + 1, jj, model);
			}
			if (jj > 0 && num[ii][jj] == 0) {
				if (num[ii][jj - 1] != 0) {
					// 边缘的雷数
					// around(ii, jj-1);
					// return;
				}
				Enabled(ii, jj - 1, model);
			}
		}
		if (model == 3) {
			// 左上
			if (ii > 0 && num[ii][jj] == 0) {
				if (num[ii - 1][jj] != 0) {
					// 边缘的雷数
					// around(ii-1, jj);
					// return;
				}
				Enabled(ii - 1, jj, model);
			}
			if (jj > 0 && num[ii][jj] == 0) {
				if (num[ii][jj - 1] != 0) {
					// 边缘的雷数
					// around(ii, jj-1);
					// return;
				}
				Enabled(ii, jj - 1, model);
			}
		}

	}

	// // 周围八个方块。。。即雷数
	// private void around(int ii, int jj) {
	// if (jj - 1 >= 0)
	// setButton(ii, jj - 1);
	// if (jj + 1 < bt.length)
	// setButton(ii, jj + 1);
	// if (ii - 1 >= 0)
	// setButton(ii - 1, jj);
	// if (ii + 1 < bt.length)
	// setButton(ii + 1, jj);
	// if (ii + 1 < bt.length && jj + 1 < bt.length)
	// setButton(ii + 1, jj + 1);
	// if (ii + 1 < bt.length && jj - 1 >= 0)
	// setButton(ii + 1, jj - 1);
	// if (ii - 1 >= 0 && jj + 1 < bt.length)
	// setButton(ii - 1, jj + 1);
	// if (ii - 1 >= 0 && jj - 1 >= 0)
	// setButton(ii - 1, jj - 1);
	// }

	// 显示按钮状态：按下，周围雷数
	private void setButton(int ii, int jj) {
		if (num[ii][jj] > 0 && num[ii][jj] < 9) {
			if (bt[ii][jj].isEnabled()) {
				bt[ii][jj].setEnabled(false);
			}
			bt[ii][jj].setText("" + num[ii][jj]);
//			return;
		}
		if (num[ii][jj] == 0) {
			if (bt[ii][jj].isEnabled()) {
				if (jj - 1 >= 0 && num[ii][jj - 1] != 0) {

					bt[ii][jj - 1].setEnabled(false);
					bt[ii][jj - 1].setText("" + num[ii][jj - 1]);
				}
				if (jj + 1 < bt.length && num[ii][jj + 1] != 0) {
					bt[ii][jj + 1].setEnabled(false);
					bt[ii][jj + 1].setText("" + num[ii][jj + 1]);

				}
				if (ii - 1 >= 0 && num[ii - 1][jj] != 0) {
					bt[ii - 1][jj].setEnabled(false);
					bt[ii - 1][jj].setText("" + num[ii - 1][jj]);
				}
				if (ii + 1 < bt.length && num[ii + 1][jj] != 0) {
					bt[ii + 1][jj].setEnabled(false);
					bt[ii + 1][jj].setText("" + num[ii + 1][jj]);
				}
				if (ii + 1 < bt.length && jj + 1 < bt.length
						&& num[ii + 1][jj + 1] != 0) {
					bt[ii + 1][jj + 1].setEnabled(false);
					bt[ii + 1][jj + 1].setText("" + num[ii + 1][jj + 1]);
				}
				if (ii + 1 < bt.length && jj - 1 >= 0
						&& num[ii + 1][jj - 1] != 0) {
					bt[ii + 1][jj - 1].setEnabled(false);
					bt[ii + 1][jj - 1].setText("" + num[ii + 1][jj - 1]);

				}
				if (ii - 1 >= 0 && jj + 1 < bt.length
						&& num[ii - 1][jj + 1] != 0) {
					bt[ii - 1][jj + 1].setEnabled(false);
					bt[ii - 1][jj + 1].setText("" + num[ii - 1][jj + 1]);
				}
				if (ii - 1 >= 0 && jj - 1 >= 0 && num[ii - 1][jj - 1] != 0) {
					bt[ii - 1][jj - 1].setEnabled(false);
					bt[ii - 1][jj - 1].setText("" + num[ii - 1][jj - 1]);
				}
				bt[ii][jj].setEnabled(false);
			}
		}
	}
}
