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
		//1.��ѯ���е���Ʒ��Ϣ��������Ʒ��ɵ�list����
		List<Product> list = findProdList();
		//2.��list���ϴ洢��request������Ĵ������
		request.setAttribute("list", list);
		//3.ͨ������ת��������Ʒ��Ϣ����prod_list.jsp
		request.getRequestDispatcher("/backend/prod_list.jsp").forward(request, response);
	}
	
	/*��ѯ������Ʒ�ķ���*/
	private List<Product> findProdList(){
		//list���ϣ���˳��������ظ����ֵļ��ϣ�������һ�µ�
		//��ȡ���������Ӷ���
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.��ȡ���Ӷ���
			conn = JDBCUtils.getConn();
			//2.����sql���
			String sql = "select * from product";
			//3.��ȡ����������PreparedStatement����
			ps = conn.prepareStatement(sql);
			//4.ִ��sql���
			rs = ps.executeQuery();
			//5.����list��װ������Ʒ��Ϣ
			List<Product> list = new ArrayList<Product>();//ArrayList�ײ�������
			Product prod = null;
			//6.��������������ݴ洢��LIst��
			while(rs.next()) {
				//7.ʵ����Product�������û��ʵ����product����ᷢ��NullPointerException����ָ���쳣��
				prod = new Product();
				//8.��ÿһ�еļ�¼��װ��product��
				prod.setId(rs.getInt("id"));//���ݿ��е�id�ֶΣ�һ��Ҫ�����ݿ��ֶ���ƥ��
				prod.setName(rs.getString("name"));
				prod.setCategory(rs.getString("category"));
				prod.setPrice(rs.getDouble("price"));
				prod.setPnum(rs.getInt("pnum"));
				prod.setDescription(rs.getString("description"));
				//9.��product���������list������
				list.add(prod);
			}
			//����list
			return list;
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