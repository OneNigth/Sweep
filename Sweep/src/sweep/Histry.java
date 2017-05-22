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
	// 数据库语句
	String sql = null;
	DB db1 = null;

	// 历史记录面板
	JFrame jFrame;
	JPanel jPanel;
	JLabel label;
	
	public Histry() {
		fragment();
	}
	// 胜利后--添加历史记录
	public Histry(int time, String model) {
		String sql = " insert into histry(time,model) values('" + time + "','"
				+ model + "')";// SQL语句
		db1 = new DB(sql);// 创建DB对象

		try {
			// 添加数据
			db1.pst.execute();

			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 菜单栏--查询所有历史记录
	public void select() {
		ResultSet ret = null;
		sql = "select *from histry";// SQL语句
		db1 = new DB(sql);// 创建DB对象

		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				String id = ret.getString(1);
				String time = ret.getString(2);
				String model = ret.getString(3);
				// 显示数据       
					
					JTextArea text = new JTextArea(id + "                          " + time + "秒                   " + model);
					text.setEnabled(false);
					jPanel.add(text);
				
//				System.out.println("id\t时间\t模式");
//				System.out.println(id + "\t" + time + "秒\t" + model);
			}
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//组件
	public void fragment() {
		jFrame = new JFrame();
		jPanel = new JPanel(); 
		
		jFrame.add(jPanel);
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jFrame.setVisible(true);
		jFrame.setBounds(0, 0, 300, 300);
		jFrame.setResizable(false);
		//窗口获取焦点
		label = new JLabel( "ID                      时间                    模式" );
		jPanel.add(label);
		jFrame.requestFocus();
	}

	
}
