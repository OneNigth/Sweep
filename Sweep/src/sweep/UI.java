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
	// ���
	JButton[][] bt;
	JPanel jPanelSweep, jPanelInfo, jPanelAll;
	JMenuBar menuBar;
	JMenu menu1, menu2, menu3;
	JMenuItem model, record, help;
	JButton icbutton;
	// ��ʱʹ�õ���
	JLabel timeCount;
	Timer timer;
	int GTime;
	// ��������
	int Remaining = 10;
	// �������ڵ�����
	int rows[];
	int cols[];
	
	//�õ���Ļ�ĳߴ�-------��Ļ����
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 

	
	// �������
	public UI(int Remaining) {
		
		// ģʽ����------˳��ĳ�������Ļ��С�ļ���֮һ
		this.Remaining = Remaining;
		rows = new int[Remaining];
		cols = new int[Remaining];
		// ��ʼ��������˵���
		initView();
		barView();
		// ������
		/**
		 * ģʽѡ�������
		 */
		//ģʽ���Ĳ������Ÿ���
		GridLayout gridLayout = new GridLayout(Remaining, Remaining);

		jPanelSweep.setLayout(gridLayout);

		// ���������������������������˵���
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);

		menu1.add(model);
		menu2.add(record);
		menu3.add(help);

		// �˵������Ӳ˵��¼�����
		model.addActionListener(this);
		record.addActionListener(this);
		help.addActionListener(this);

		// ��Ϣ��
		// ����ͼƬ
		icbutton = new JButton(new ImageIcon("smile.jpg"));
		icbutton.setBorderPainted(false);
		icbutton.setFocusPainted(false);
		icbutton.setMargin(new Insets(0, 0, 0, 0));
		icbutton.setContentAreaFilled(false);
		icbutton.addActionListener(this);
		// ��ʱ����

		timeCount = new JLabel("��ʱ��" + 0);
		timer = new Timer(1000, new ActionListener() {
			int time = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				time++;

				// System.out.println(time);
				timeCount.setText("��ʱ��" + time);
				GTime = time ;
			}
		});
		timer.start();
		
		// ʣ������
		JLabel less = new JLabel("������" + Remaining);

		// ��Ϣ����װ
		jPanelInfo.add(timeCount, BorderLayout.WEST);
		jPanelInfo.add(icbutton, BorderLayout.CENTER);
		jPanelInfo.add(less);

		// ������������������������������
		add(jPanelInfo, BorderLayout.NORTH);
		add(jPanelSweep, BorderLayout.CENTER);
		setJMenuBar(menuBar);

		setTitle("ɨ��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ����Ŀɿش�С
		int height = screenSize.height; //windows�ĸ߶�
		setBounds(0, 0, height/5*4, height/5*4);
		setResizable(false);
		setVisible(true);

	}

	// �˵�������װ
	public void barView() {
		menuBar = new JMenuBar();
		menu1 = new JMenu("��ʼ");
		menu2 = new JMenu("��ʷ��¼");
		menu3 = new JMenu("����");
		model = new JMenuItem("ģʽ");
		record = new JMenuItem("��ʷ��¼");
		help = new JMenuItem("����");
		jPanelInfo = new JPanel();

		/**
		 * ģʽѡ�������
		 */

		jPanelSweep = new JPanel();
		for (int i = 0; i < bt.length; i++) {
			for (int j = 0; j < bt[i].length; j++) {
				bt[i][j].addActionListener(this);
				// ��װ�����
				jPanelSweep.add(bt[i][j]);
			}

		}

	}

	public void initView() {
		// ��ʼ��Button
		bt = new JButton[Remaining][Remaining];
		for (int i = 0; i < bt.length; i++) {
			for (int j = 0; j < bt[i].length; j++) {
				bt[i][j] = new JButton();
			}
		}

		// ��װ���׺�����
		// int[][] ran = new int[bt.length][bt.length];
		Random rans = new Random();

		int land = 0;
		for (int i = 0; land < Remaining;) {
			// ��ȡ�����
			int r = rans.nextInt(Remaining);
			int c = rans.nextInt(Remaining);
			for (int j = 0; j <= i; j++) {
				// ����������ظ�
				if (rows[j] == r || cols[j] == c) {
					break ;
				}else{
					rows[land] = r;
					cols[land] = c;
					land++;
				}
			}
		}
		// ���������ظ������õ�
//		 for(int i = 0 ; i<rows.length ; i++){
//		 System.out.print(rows[i]+ " "+cols[i]+"...");
//		 }
//		 System.out.println();
		// ��������
		new ButtonTem(bt, rows, cols, this);
	}

	@Override
	// �˵����¼�����
	public void actionPerformed(ActionEvent arg0) {
		// ����ͼƬ����
		if (arg0.getSource() == icbutton) {
			initView();
//			System.out.println("������");
			dispose();
			new UI(Remaining);
		}
		// ��ʼ�˵�
		if (arg0.getSource() == model) {
			modelCheck();
		}
		// ��ʷ��¼������ɾ��
		if(arg0.getSource() == record){
			Histry histry = new Histry();
			histry.select();
		}
		// ����
		if(arg0.getSource() == help){
			JOptionPane.showConfirmDialog(null, "���������ť�������¿�ʼ\n����������Ա���̈���,�����,Ҧ��\n����", "ֻ�ǿ��������������", JOptionPane.YES_NO_OPTION);
		}
	}

	// ģʽ��ѡ��
	private void modelCheck() {

		Object[] possibleValues = { "��ģʽ", "��ͨģʽ", "����ģʽ" };
		Object Value = JOptionPane.showInputDialog(null, "��Ҫ�棺", "ģʽѡ��",
				JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
				possibleValues[0]);
		if (Value == "��ģʽ") {
			Remaining = 10;
			dispose();
			new UI(Remaining);
		}
		if (Value == "��ͨģʽ") {
			Remaining = 15;
			dispose();
			new UI(Remaining);
		}
		if (Value == "����ģʽ") {
			Remaining = 20;
			dispose();
			new UI(Remaining);
		}
	}
}
