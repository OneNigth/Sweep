package sweep;

import java.awt.TextArea;
import java.awt.TextField;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import DB.DB;

public class Histry {
	// ���ݿ����
	String sql = null;
	DB db1 = null;

	// ��ʷ��¼���
	JFrame jFrame;
	JPanel jPanel;
	JLabel label;
	
	public Histry() {
		fragment();
	}
	// ʤ����--�����ʷ��¼
	public Histry(int time, String model) {
		String sql = " insert into histry(time,model) values('" + time + "','"
				+ model + "')";// SQL���
		db1 = new DB(sql);// ����DB����

		try {
			// �������
			db1.pst.execute();

			db1.close();// �ر�����
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// �˵���--��ѯ������ʷ��¼
	public void select() {
		ResultSet ret = null;
		sql = "select *from histry";// SQL���
		db1 = new DB(sql);// ����DB����

		try {
			ret = db1.pst.executeQuery();// ִ����䣬�õ������
			while (ret.next()) {
				String id = ret.getString(1);
				String time = ret.getString(2);
				String model = ret.getString(3);
				// ��ʾ����       
					
					JTextArea text = new JTextArea(id + "                          " + time + "��                   " + model);
					text.setEnabled(false);
					jPanel.add(text);
				
//				System.out.println("id\tʱ��\tģʽ");
//				System.out.println(id + "\t" + time + "��\t" + model);
			}
			ret.close();
			db1.close();// �ر�����
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//���
	public void fragment() {
		jFrame = new JFrame();
		jPanel = new JPanel(); 
		
		jFrame.add(jPanel);
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jFrame.setVisible(true);
		jFrame.setBounds(0, 0, 300, 300);
		jFrame.setResizable(false);
		//���ڻ�ȡ����
		label = new JLabel( "ID                      ʱ��                    ģʽ" );
		jPanel.add(label);
		jFrame.requestFocus();
	}

	
}
