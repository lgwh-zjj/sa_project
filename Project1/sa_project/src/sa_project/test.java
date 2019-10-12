package sa_project;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;



public class test {
 public static void main(String [] args)throws SQLException
 {
  String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
  String dbURL="jdbc:sqlserver://database-1.ce6lobyfxsbk.us-east-1.rds.amazonaws.com:1433;DatabaseName=test";
  String userName="admin";
  String userPwd="lgwh0830qie";
  Connection ct;
  ResultSet re;
  CallableStatement st;
 try
{
    Class.forName(driverName);
    System.out.println("加载驱动成功！");
    ct=DriverManager.getConnection(dbURL,userName,userPwd);
    System.out.println("连接数据库成功！");
	
	JFrame frame=new JFrame();
    JPanel panel1=new JPanel();
    JPanel panel2=new JPanel();
    JPanel panel3=new JPanel();
    JButton button1=new JButton("查询某日转手率");
    JButton button2=new JButton("查询某月换手率");
    JLabel label1=new JLabel("年");
    JLabel label2=new JLabel("月");
    JLabel label3=new JLabel("日");
    
    JTextField text1=new JTextField(4);
    JTextField text2=new JTextField(2);
    JTextField text3=new JTextField(2);
    JTextArea text4=new JTextArea(20, 10);
    
    panel1.add(text1);
    panel1.add(label1);

    panel1.add(text2);
    panel1.add(label2);
    

    panel1.add(text3);
    panel1.add(label3);
    
    panel2.add(text4);
    
    panel3.add(button1);
    panel3.add(button2);
    
    frame.setLayout(new GridLayout(3, 1, 10, 10));
    frame.add(panel1);
    frame.add(panel2);
    frame.add(panel3);

    
    frame.setSize(400, 200);
    frame.show();
    
    function f=new function(ct,text4);
    
    button1.addActionListener(new  ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int year=Integer.parseInt(text1.getText());
			int month=Integer.parseInt(text2.getText());
			int day=Integer.parseInt(text3.getText());
			System.out.println(year);
			System.out.println(month);
			System.out.println(day);
			f.func1(year, month, day);
		}    
		});
		
		button2.addActionListener(new  ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame frame2 = new JFrame();
				int year=Integer.parseInt(text1.getText());
				int month=Integer.parseInt(text2.getText());
				System.out.println(year);
				System.out.println(month);
				try {
					frame2.add(f.func2(year, month));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				frame2.setSize(400, 400);
				frame2.setVisible(true);
			    frame2.show();
			    
				
			}
    	
    });

}catch(Exception e)
{
    e.printStackTrace();
}        
}

}






//select *
//FROM [test].[dbo].[实验数据_上证某公司股市数据$]
//
// create proc mypro @date datetime
// as
// select [换手率(%)],[成交金额(元)]
// from  实验数据_上证某公司股市数据$
// where 日期=@date
//
// create proc mypro1 @year tinyint,@month tinyint
// as 
// select [涨跌幅(%)]
// from 实验数据_上证某公司股市数据$
// where YEAR(日期)=@year and MONTH(日期)=@month
