package com.tedu.jt.web.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tedu.jt.utils.JDBCUtils;

public class ProdServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取商品id
		int pid = Integer.parseInt(request.getParameter("pid"));//pid和网页中问号后的属性名相同
		//2.根据id查询
		Product prod = findProdById(pid);
		//3.将商品信息存入request域中
		request.setAttribute("prod", prod);
		//4.请求转发给修改页面显示数据
		request.getRequestDispatcher("/backend/prod_upd.jsp").forward(request, response);
	}
	
	/* 根据id查询商品信息 */
	private Product findProdById(int pid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.获取连接对象
			conn = JDBCUtils.getConn();
			//2.声明sql语句
			String sql = "select * from product where id = ?";
			//3.获取传输器对象，PreparedStatement对象
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pid);
			//4.执行sql语句
			rs = ps.executeQuery();
			//5.
			Product prod = null;
			//6.遍历结果集将数据存储在LIst中
			if(rs.next()) {
				//7.实例化Product对象。如果没有实例化product，则会发生NullPointerException（空指针异常）
				prod = new Product();
				//8.将每一行的记录封装在product中
				prod.setId(rs.getInt("id"));//数据库中的id字段，一定要和数据库字段相匹配
				prod.setName(rs.getString("name"));
				prod.setCategory(rs.getString("category"));
				prod.setPrice(rs.getDouble("price"));
				prod.setPnum(rs.getInt("pnum"));
				prod.setDescription(rs.getString("description"));
			}
			//返回prod
			return prod;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("商品查询失败");
		}finally {
			JDBCUtils.close(conn, ps, rs);//关闭数据库连接对象
		}
		return null;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}