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

public class ProdListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.查询所有的商品信息。返回商品组成的list集合
		List<Product> list = findProdList();
		//2.将list集合存储到request域对象。四大域对象
		request.setAttribute("list", list);
		//3.通过请求转发，将商品信息带到prod_list.jsp
		request.getRequestDispatcher("/backend/prod_list.jsp").forward(request, response);
	}
	
	/*查询所有商品的方法*/
	private List<Product> findProdList(){
		//list集合，有顺序，允许可重复出现的集合，类型是一致的
		//获取基本的连接对象
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.获取连接对象
			conn = JDBCUtils.getConn();
			//2.声明sql语句
			String sql = "select * from product";
			//3.获取传输器对象，PreparedStatement对象
			ps = conn.prepareStatement(sql);
			//4.执行sql语句
			rs = ps.executeQuery();
			//5.创建list封装所有商品信息
			List<Product> list = new ArrayList<Product>();//ArrayList底层是数组
			Product prod = null;
			//6.遍历结果集将数据存储在LIst中
			while(rs.next()) {
				//7.实例化Product对象。如果没有实例化product，则会发生NullPointerException（空指针异常）
				prod = new Product();
				//8.将每一行的记录封装在product中
				prod.setId(rs.getInt("id"));//数据库中的id字段，一定要和数据库字段相匹配
				prod.setName(rs.getString("name"));
				prod.setCategory(rs.getString("category"));
				prod.setPrice(rs.getDouble("price"));
				prod.setPnum(rs.getInt("pnum"));
				prod.setDescription(rs.getString("description"));
				//9.将product对象添加在list集合中
				list.add(prod);
			}
			//返回list
			return list;
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