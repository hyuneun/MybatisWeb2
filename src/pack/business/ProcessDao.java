package pack.business;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pack.mybatis.SqlMapConfig;

public class ProcessDao {
	private SqlSessionFactory factory = SqlMapConfig.getSqlSession();
	
	public List selectDataAll(Map<String, String> map){
		SqlSession sqlsession = factory.openSession();
		List list = null;
		
		try {
			list = sqlsession.selectList("selectDataAll",map);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(sqlsession != null) sqlsession.close();
		}
		
		return list;
	}
	
	public DataDto selectdataById(String id){
	
			SqlSession sqlsession = factory.openSession();
			DataDto dto = null;
			
			try {
				dto = sqlsession.selectOne("selectdataById",id);
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				if(sqlsession != null) sqlsession.close();
			}
			
			return dto;
		}
		
	public boolean insertData(DataDto dto){
		boolean b = false;
		SqlSession sqlsession = factory.openSession();
		try {
			if(sqlsession.insert("insertData",dto) > 0) b = true;
			sqlsession.commit();
		} catch (Exception e) {
			sqlsession.rollback();
		}finally {
			if(sqlsession != null) sqlsession.close();
		}
		return b;
	}
	
	public boolean updateData(DataDto dto){
		boolean b = false;
		SqlSession sqlsession = factory.openSession();
		try {
			//비번비교
			DataDto dto2 = selectdataById(dto.getId());
			if(dto2.isCheckPasswd(dto.getPassword()) == false){
				return b;
			}
			if(sqlsession.update("updateData",dto) > 0) b = true;
			sqlsession.commit();
		} catch (Exception e) {
			sqlsession.rollback();
		}finally {
			if(sqlsession != null) sqlsession.close();
		}
		return b;
	}

	
	public boolean deleteData(String id){
		boolean b = false;
		SqlSession sqlsession = factory.openSession();
		try {
			if(sqlsession.delete("deleteData",id) > 0) b = true;
			sqlsession.commit();
		} catch (Exception e) {
			sqlsession.rollback();
		}finally {
			if(sqlsession != null) sqlsession.close();
		}
		return b;
	}
	
}
