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
	    System.out.println("调用存储过程");
		String sql="{call dbo.mypro(?,?,?)}";  
	    try {
			this.st=this.ct.prepareCall(sql);
			this.st.setInt(1, year);
			this.st.setInt(2, month);
			this.st.setInt(3, day);
			re=st.executeQuery();
		    while(this.re.next())
		    	{
		    	System.out.println("成交额："+re.getString(1));
		    	System.out.println("转手率："+re.getString(2));
		    	text.append("成交额："+re.getString(1)+'\n');
		    	text.append("转手率："+re.getString(2)+'\n');

		    	} 
		    this.re.close();
		    this.st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  //调用 
	}
	
	
	public ChartPanel func2(int year,int month) throws IOException
	{
	    //Vector<String> data = null;
	    //Vector<String> day=null;
	    System.out.println("调用存储过程");
		String sql="{call dbo.mypro1(?,?)}";  
		DefaultCategoryDataset dateset = new DefaultCategoryDataset();
	    try {
			this.st=this.ct.prepareCall(sql);
			this.st.setInt(1, year);
			this.st.setInt(2, month);
			re=st.executeQuery();
		    while(this.re.next())
		    	{
		    	System.out.println("转手率："+re.getString(1));
		    	System.out.println("day："+re.getString(2));
		    	dateset.setValue(Double.parseDouble(re.getString(1)), "a", re.getString(2));
		    	} 
		    this.re.close();
		    this.st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  //调用

	    JFreeChart chart=ChartFactory.createLineChart(
	    		year+"年"+month+"月转手率折线图", 
	    		"日",
	    		"转手率",
	    		dateset,
	    		PlotOrientation.VERTICAL, //图表放置模式水平/垂直 
	    		true, //显示lable
	    		false, //显示提示
	    		false //显示urls
	    		);
	    chart.getTitle().setFont(new Font("宋体", Font.BOLD,12));
	    CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象
        CategoryAxis domainAxis=plot.getDomainAxis();//水平底部列表
        domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));         //水平底部标题
        domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));  //垂直标题
        ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));

        
	    OutputStream os=new FileOutputStream("f:\\test.jpg");
	    ChartUtilities.writeChartAsJPEG(os, chart, 500, 500);
	      //读取本地图片输入流
        ChartPanel frame1=new ChartPanel(chart,true);
        return frame1;
	    
	}
	
	
}
