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

public class ProdDelServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//0.处理乱码（如果添加了过滤器处理乱码）
		// >> 请求乱码的参数
		request.setCharacterEncoding("utf-8");
		// >> 响应正文乱码
		response.setContentType("text/html;charset=utf-8");
		//1.获取商品id
		int pid = Integer.parseInt(request.getParameter("pid"));//pid和网页中问号后的属性名相同
		//2.根据id删除
		 delProdById(pid);
		//3.提示用户商品删除成功
		response.getWriter().write("<h1 style='color:red;text-align:center'>");
		response.getWriter().write("商品删除成功，3秒后跳转至主页");
		response.getWriter().write("</h1>");
		//4.定时器刷新，跳转至prodlistServlet中，查询商品列表
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/ProdListServlet");
		
	}
	
	/* 根据id删除商品信息 */
	private void delProdById(int pid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.获取连接对象
			conn = JDBCUtils.getConn();
			//2.声明sql语句
			String sql = "DELETE FROM product WHERE id = ?";
			//3.获取传输器对象，PreparedStatement对象
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pid);
			//4.执行sql语句
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("商品删除失败");
		}finally {
			JDBCUtils.close(conn, ps, rs);//关闭数据库连接对象
		}

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}