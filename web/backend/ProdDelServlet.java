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
		//0.�������루�������˹������������룩
		// >> ��������Ĳ���
		request.setCharacterEncoding("utf-8");
		// >> ��Ӧ��������
		response.setContentType("text/html;charset=utf-8");
		//1.��ȡ��Ʒid
		int pid = Integer.parseInt(request.getParameter("pid"));//pid����ҳ���ʺź����������ͬ
		//2.����idɾ��
		 delProdById(pid);
		//3.��ʾ�û���Ʒɾ���ɹ�
		response.getWriter().write("<h1 style='color:red;text-align:center'>");
		response.getWriter().write("��Ʒɾ���ɹ���3�����ת����ҳ");
		response.getWriter().write("</h1>");
		//4.��ʱ��ˢ�£���ת��prodlistServlet�У���ѯ��Ʒ�б�
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/ProdListServlet");
		
	}
	
	/* ����idɾ����Ʒ��Ϣ */
	private void delProdById(int pid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.��ȡ���Ӷ���
			conn = JDBCUtils.getConn();
			//2.����sql���
			String sql = "DELETE FROM product WHERE id = ?";
			//3.��ȡ����������PreparedStatement����
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pid);
			//4.ִ��sql���
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��Ʒɾ��ʧ��");
		}finally {
			JDBCUtils.close(conn, ps, rs);//�ر����ݿ����Ӷ���
		}

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}