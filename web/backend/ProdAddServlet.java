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
 * ��Ʒ����servlet
 */
public class ProdAddServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//0.�������루��������˹������������룩
		// >> ��������Ĳ���
		request.setCharacterEncoding("utf-8");
		// >> ��Ӧ��������
		response.setContentType("text/html;charset=utf-8");
		//1.��ȡ��Ʒ��Ϣ
		String name = request.getParameter("name");
		String category = request.getParameter("category");
		double price = Double.parseDouble(request.getParameter("price"));//��װ�࣬��StringתΪdouble
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		String description = request.getParameter("description");
		/*System.out.println(name);
		System.out.println(category);
		System.out.println(price);
		System.out.println(pnum);
		System.out.println(description);*/
		//2.����Ʒ��Ϣ�洢�����ݿ�
		addProd(name, category, price, pnum, description);
		//3.��ʾ�û���Ʒ���ӳɹ�
		response.getWriter().write("<h1 style='color:red;text-align:center'>");
		response.getWriter().write("��Ʒ���ӳɹ���3�����ת����ҳ");
		response.getWriter().write("</h1>");
		//4.��ʱ��ˢ�£���ת��prodlistServlet�У���ѯ��Ʒ�б�
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/ProdListServlet");
	}
	
	/*����Ʒ��Ϣ���뵽���ݿ���*/
	private void addProd(String name, String category, double price, int pnum, String description) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.��ȡ���Ӷ���
			conn = JDBCUtils.getConn();
			//2.����sql���
			String sql = "insert into product values(null,?,?,?,?,?)";
			//3.��ȡ����������PreparedStatement����
			ps = conn.prepareStatement(sql);
			//4.����sql����
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setDouble(3, price);
			ps.setInt(4, pnum);
			ps.setString(5, description);
			//5.ִ��sql���
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����ʧ��");
		}finally {
			JDBCUtils.close(conn, ps, rs);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}