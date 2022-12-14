package com.tedu.jt.web.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tedu.jt.utils.JDBCUtils;

/**
 * 商品添加servlet
 */
public class ProdAddServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//0.处理乱码（如果添加了过滤器处理乱码）
		// >> 请求乱码的参数
		request.setCharacterEncoding("utf-8");
		// >> 响应正文乱码
		response.setContentType("text/html;charset=utf-8");
		//1.获取商品信息
		String name = request.getParameter("name");
		String category = request.getParameter("category");
		double price = Double.parseDouble(request.getParameter("price"));//包装类，将String转为double
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		String description = request.getParameter("description");
		/*System.out.println(name);
		System.out.println(category);
		System.out.println(price);
		System.out.println(pnum);
		System.out.println(description);*/
		//2.将商品信息存储进数据库
		addProd(name, category, price, pnum, description);
		//3.提示用户商品添加成功
		response.getWriter().write("<h1 style='color:red;text-align:center'>");
		response.getWriter().write("商品添加成功，3秒后跳转至主页");
		response.getWriter().write("</h1>");
		//4.定时器刷新，跳转至prodlistServlet中，查询商品列表
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/ProdListServlet");
	}
	
	/*将商品信息存入到数据库中*/
	private void addProd(String name, String category, double price, int pnum, String description) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.获取连接对象
			conn = JDBCUtils.getConn();
			//2.声明sql语句
			String sql = "insert into product values(null,?,?,?,?,?)";
			//3.获取传输器对象，PreparedStatement对象
			ps = conn.prepareStatement(sql);
			//4.设置sql参数
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setDouble(3, price);
			ps.setInt(4, pnum);
			ps.setString(5, description);
			//5.执行sql语句
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("添加失败");
		}finally {
			JDBCUtils.close(conn, ps, rs);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
