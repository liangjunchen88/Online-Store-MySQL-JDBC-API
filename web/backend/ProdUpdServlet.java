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

public class ProdUpdServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//0.�������루�������˹������������룩
		// >> ��������Ĳ���
		request.setCharacterEncoding("utf-8");
		// >> ��Ӧ��������
		response.setContentType("text/html;charset=utf-8");
		//1.��ȡ��Ʒ��Ϣ
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String category = request.getParameter("category");
		double price = Double.parseDouble(request.getParameter("price"));//��װ�࣬��StringתΪdouble
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		String description = request.getParameter("description");
		updProdById(name, category, price, pnum, description, id);
		//3.��ʾ�û���Ʒ��ӳɹ�
		response.getWriter().write("<h1 style='color:red;text-align:center'>");
		response.getWriter().write("��Ʒ�޸ĳɹ���3�����ת����ҳ");
		response.getWriter().write("</h1>");
		//4.��ʱ��ˢ�£���ת��prodlistServlet�У���ѯ��Ʒ�б�
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/ProdListServlet");
	}
	
	/*������Ʒ��Ϣ*/
	private void updProdById(String name, String category, double price, int pnum, String description, int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.��ȡ���Ӷ���
			conn = JDBCUtils.getConn();
			//2.����sql���
			String sql = "update product set name=?, category=?, price =?, pnum=?, description=? where id =?";
			//3.��ȡ����������PreparedStatement����
			ps = conn.prepareStatement(sql);
			//4.����sql����
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setDouble(3, price);
			ps.setInt(4, pnum);
			ps.setString(5, description);
			ps.setInt(6, id);
			//5.ִ��sql���
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�޸�ʧ��");
		}finally {
			JDBCUtils.close(conn, ps, rs);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}