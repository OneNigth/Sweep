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
	// ��������������
	int[][] num;
	// ��ʼ������ʹ�õ�
	UI ui;
	// �������ڵ�����
	int rows[];
	int cols[];

	public ButtonTem(JButton bt[][], int rows[], int cols[], UI ui) {
		// ��ʼ������
		this.bt = bt;
		this.rows = rows;
		this.cols = cols;
		this.ui = ui;
		// �������������飬��ȫ����ʼֵΪ0
		num = new int[bt.length][bt.length];
		for (int i = 0; i < num.length; i++) {
			for (int j = 0; j < num[i].length; j++) {
				// bt[i][j].setEnabled(true);
				num[i][j] = 0;

			}
		}
		// ���µ���num�ı仯...���ȼ���ü������Ƿ񰤱ߵ����
		for (int i = 0; i < bt.length; i++) {
			putIt(i);
		}
		for (int i = 0; i < bt.length; i++)
			num[rows[i]][cols[i]] = 9;

		// �����ڵĵط�----����
//		for (int i = 0; i < num.length; i++) {
//			for (int j = 0; j < num[i].length; j++) {
//
//				System.out.print(num[i][j] + ",");
//			}
//			System.out.println();
//		}

		// ���ð�ť����
		for (int i = 0; i < bt.length; i++) {
			for (int j = 0; j < bt[i].length; j++) {
				bt[i][j].addActionListener(this);
			}
		}
	}

	private void putIt(int count) {
		// �Աߵİ˸�λ��++ if��Խ��
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
		// ��ȡ������ĸ�λ��
		for (int i = 0; i < bt.length; i++) {
			for (int j = 0; j < bt[i].length; j++) {
				if (e.getSource() == bt[i][j]) {
					ii = i;
					jj = j;
				}
			}
		}
		if (num[ii][jj] == 9) {
			// ����----���׵�ͼ���е���죬senenabled�ǵ�
			bt[ii][jj].setEnabled(false);
			bt[ii][jj].setIcon(new ImageIcon("boom.jpg"));
			bt[ii][jj].setFocusPainted(false);
			bt[ii][jj].setContentAreaFilled(false);
			int option = JOptionPane.showConfirmDialog(null, "���ȷ�����¿�ʼ", "������",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_NO_OPTION) {
				// ���ȷ���������¿�ʼ
				ui.dispose();
				ui = new UI(ui.Remaining);
			} else if (option == JOptionPane.NO_OPTION) {
				// �����ȡ��������ʾȫ�����ף���ʱֹͣ
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

		// ��ť���º�ݹ�
		// 0123�ǿ������ķ��򣬷ֱ������£����ϣ����£�����
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
		

		// �Ƿ� ʤ��
		int count = 0;// ������
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
		// ʤ���ĵ���
		if (count == ui.Remaining
				&& land == bt.length * bt.length - ui.Remaining) {
			int time = ui.GTime;
			String model = "";
			JOptionPane.showConfirmDialog(null, "ʹ���ˣ�" + time + "��",
					"YOU WIN!!", JOptionPane.YES_NO_OPTION);
			// ��ʱֹͣ-----��¼����
			ui.timer.stop();
			if (ui.Remaining == 10) {
				model = "��";
			} else if (ui.Remaining == 15) {
				model = "��ͨ";
			} else if (ui.Remaining == 20) {
				model = "����";
			}
			Histry histry = new Histry(time, model);
		}
	}
	//����߳�
	class myThread extends Thread{
		int num = 0 ;
		public myThread(int num) {
			this.num = num;
		}
		public void run() {
			Enabled(ii, jj, num);
		}
		
	}
	// �ݹ����
	private void Enabled(int ii, int jj, int model) {
		// ��ʾ�ķ���
		setButton(ii, jj);
		if (model == 0) {
			// ����
			if (ii < bt.length - 1 && num[ii][jj] == 0) {
				if (num[ii + 1][jj] != 0) {
					// ��Ե������
					// around(ii+1, jj);
					// return;
				}
				Enabled(ii + 1, jj, model);

			}
			if (jj < bt.length - 1 && num[ii][jj] == 0) {
				if (num[ii][jj + 1] != 0) {
					// ��Ե������
					// around(ii, jj+1);
					// return;
				}
				Enabled(ii, jj + 1, model);
			}
		}
		if (model == 1) {
			// ����
			if (ii > 0 && num[ii][jj] == 0) {
				if (num[ii - 1][jj] != 0) {
					// ��Ե������
					// around(ii-1, jj);
					// return;
				}
				Enabled(ii - 1, jj, model);
			}
			if (jj < bt.length - 1 && num[ii][jj] == 0) {
				if (num[ii][jj + 1] != 0) {
					// ��Ե������
					// around(ii, jj+1);
					// return;
				}
				Enabled(ii, jj + 1, model);
			}
		}
		if (model == 2) {
			// ����
			if (ii < bt.length - 1 && num[ii][jj] == 0) {
				if (num[ii + 1][jj] != 0) {
					// ��Ե������
					// around(ii+1, jj);
					// return;
				}
				Enabled(ii + 1, jj, model);
			}
			if (jj > 0 && num[ii][jj] == 0) {
				if (num[ii][jj - 1] != 0) {
					// ��Ե������
					// around(ii, jj-1);
					// return;
				}
				Enabled(ii, jj - 1, model);
			}
		}
		if (model == 3) {
			// ����
			if (ii > 0 && num[ii][jj] == 0) {
				if (num[ii - 1][jj] != 0) {
					// ��Ե������
					// around(ii-1, jj);
					// return;
				}
				Enabled(ii - 1, jj, model);
			}
			if (jj > 0 && num[ii][jj] == 0) {
				if (num[ii][jj - 1] != 0) {
					// ��Ե������
					// around(ii, jj-1);
					// return;
				}
				Enabled(ii, jj - 1, model);
			}
		}

	}

	// // ��Χ�˸����顣����������
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

	// ��ʾ��ť״̬�����£���Χ����
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
