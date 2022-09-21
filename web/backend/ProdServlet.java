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
		//1.��ȡ��Ʒid
		int pid = Integer.parseInt(request.getParameter("pid"));//pid����ҳ���ʺź����������ͬ
		//2.����id��ѯ
		Product prod = findProdById(pid);
		//3.����Ʒ��Ϣ����request����
		request.setAttribute("prod", prod);
		//4.����ת�����޸�ҳ����ʾ����
		request.getRequestDispatcher("/backend/prod_upd.jsp").forward(request, response);
	}
	
	/* ����id��ѯ��Ʒ��Ϣ */
	private Product findProdById(int pid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.��ȡ���Ӷ���
			conn = JDBCUtils.getConn();
			//2.����sql���
			String sql = "select * from product where id = ?";
			//3.��ȡ����������PreparedStatement����
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pid);
			//4.ִ��sql���
			rs = ps.executeQuery();
			//5.
			Product prod = null;
			//6.��������������ݴ洢��LIst��
			if(rs.next()) {
				//7.ʵ����Product�������û��ʵ����product����ᷢ��NullPointerException����ָ���쳣��
				prod = new Product();
				//8.��ÿһ�еļ�¼��װ��product��
				prod.setId(rs.getInt("id"));//���ݿ��е�id�ֶΣ�һ��Ҫ�����ݿ��ֶ���ƥ��
				prod.setName(rs.getString("name"));
				prod.setCategory(rs.getString("category"));
				prod.setPrice(rs.getDouble("price"));
				prod.setPnum(rs.getInt("pnum"));
				prod.setDescription(rs.getString("description"));
			}
			//����prod
			return prod;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��Ʒ��ѯʧ��");
		}finally {
			JDBCUtils.close(conn, ps, rs);//�ر����ݿ����Ӷ���
		}
		return null;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}