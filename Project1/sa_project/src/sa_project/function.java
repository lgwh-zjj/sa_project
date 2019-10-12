package sa_project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class function {
	public JTextArea text;
	public Connection ct = null;
	public ResultSet re=null;
	public CallableStatement st=null;
	public function(Connection ct,JTextArea text)
	{
		this.ct=ct;
		this.text=text;
	}
	
	public void func1(int year,int month,int day)
	{
		String s=null;
	    System.out.println("���ô洢����");
		String sql="{call dbo.mypro(?,?,?)}";  
	    try {
			this.st=this.ct.prepareCall(sql);
			this.st.setInt(1, year);
			this.st.setInt(2, month);
			this.st.setInt(3, day);
			re=st.executeQuery();
		    while(this.re.next())
		    	{
		    	System.out.println("�ɽ��"+re.getString(1));
		    	System.out.println("ת���ʣ�"+re.getString(2));
		    	text.append("�ɽ��"+re.getString(1)+'\n');
		    	text.append("ת���ʣ�"+re.getString(2)+'\n');

		    	} 
		    this.re.close();
		    this.st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  //���� 
	}
	
	
	public ChartPanel func2(int year,int month) throws IOException
	{
	    //Vector<String> data = null;
	    //Vector<String> day=null;
	    System.out.println("���ô洢����");
		String sql="{call dbo.mypro1(?,?)}";  
		DefaultCategoryDataset dateset = new DefaultCategoryDataset();
	    try {
			this.st=this.ct.prepareCall(sql);
			this.st.setInt(1, year);
			this.st.setInt(2, month);
			re=st.executeQuery();
		    while(this.re.next())
		    	{
		    	System.out.println("ת���ʣ�"+re.getString(1));
		    	System.out.println("day��"+re.getString(2));
		    	dateset.setValue(Double.parseDouble(re.getString(1)), "a", re.getString(2));
		    	} 
		    this.re.close();
		    this.st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  //����

	    JFreeChart chart=ChartFactory.createLineChart(
	    		year+"��"+month+"��ת��������ͼ", 
	    		"��",
	    		"ת����",
	    		dateset,
	    		PlotOrientation.VERTICAL, //ͼ�����ģʽˮƽ/��ֱ 
	    		true, //��ʾlable
	    		false, //��ʾ��ʾ
	    		false //��ʾurls
	    		);
	    chart.getTitle().setFont(new Font("����", Font.BOLD,12));
	    CategoryPlot plot=chart.getCategoryPlot();//��ȡͼ���������
        CategoryAxis domainAxis=plot.getDomainAxis();//ˮƽ�ײ��б�
        domainAxis.setLabelFont(new Font("����",Font.BOLD,14));         //ˮƽ�ײ�����
        domainAxis.setTickLabelFont(new Font("����",Font.BOLD,12));  //��ֱ����
        ValueAxis rangeAxis=plot.getRangeAxis();//��ȡ��״
        rangeAxis.setLabelFont(new Font("����",Font.BOLD,15));

        
	    OutputStream os=new FileOutputStream("f:\\test.jpg");
	    ChartUtilities.writeChartAsJPEG(os, chart, 500, 500);
	      //��ȡ����ͼƬ������
        ChartPanel frame1=new ChartPanel(chart,true);
        return frame1;
	    
	}
	
	
}
