package Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Util.JdbcUtils;

public class Suggest implements ActionListener,ItemListener{
	 private JComboBox comboBox ;
	 public JLabel label1=new JLabel("建议列表  ");
	 public JLabel label2=new JLabel("建议者  ");
	 public JLabel label3=new JLabel("建议内容  ");
	 public JLabel label4=new JLabel("共有建议多少条");
	 private JPanel panel;
	 private JTextField field1,field2;
	 private JTextArea area;
	 Object[] date;
	 
	 public JPanel getPanel(){
		   panel=new JPanel(null);//要为null才是自定义布局
		   
		    date=getId();
		    comboBox =new JComboBox(date) ;
			//添加点击事件
			comboBox.addItemListener(this);
			
			field1 = new JTextField("",10) ;
			field1.setEditable(false);
			
			field2 = new JTextField("",10) ;
			
			area = new JTextArea() ;
			area.setEditable(false);
			
			
			label1.setBounds(120, 0, 120, 30);
			comboBox.setBounds(200, 0, 80, 30);
			label2.setBounds(120, 40, 120, 30);
			field1.setBounds(200, 40, 120, 30);
			label3.setBounds(120, 80, 120, 30);
			area.setBounds(200,80, 360,240);
			
			panel.add(label1);
			panel.add(comboBox) ;
			panel.add(label2);
			panel.add(field1);
			panel.add(label3);
			panel.add(area);
			return panel;
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == comboBox){
			if(e.getStateChange() == ItemEvent.SELECTED){
				int i = comboBox.getSelectedIndex() ;
				//处理下拉事件
				//获取到桌子的名称
				
				int id=(Integer)date[i];
				//String tableName=temp.substring(temp.length()-2, temp.length());
				
				//往面板输出消费内容
				ResultSet rs=null;
				String sql="select * from T_suggests where id=?";
				
				try {
					rs=JdbcUtils.executeQuery(sql, id);
					if(rs.next()){
						field1.setText(rs.getString("userName"));
		
						area.setText(rs.getString("suggestText"));
					}
				} catch (SQLException e1) {
					System.out.println("往面板输出消费内容异常");
					e1.printStackTrace();
				}finally{
					JdbcUtils.closeAll(rs);
				}
			}
		}
	}
	
	/**
	 * 获取建议列表的id
	 */
	public Object[] getId(){
		List list=new ArrayList();
		list.add("选择建议");
		
		Connection conn=null;
		ResultSet rs=null;
		
		try {
			String sql="select *from T_suggests";
			conn=JdbcUtils.createConnection();
			rs=JdbcUtils.executeQuery(conn, sql);
			
			while(rs.next()){
				list.add(rs.getObject("id"));
			}
			
		} catch (SQLException e) {
		System.out.println("操作数据库异常");
			e.printStackTrace();
		}finally{
			JdbcUtils.close(conn);
		}
		
		return list.toArray();
	}
}
