package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.Message;

/**
 * 
 * @author echo lovely
 *
 */
public class MessageDao {
	
	// 发送消息
	public int sendMsg(Message msg) {
		int count = -1;
		String sql = "insert into ws_message values (seq_message.nextval, ?, ?, ?, sysdate, 0)";
		
		Connection conn = BaseDao.getConn();
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, msg.getMsgContent());
			ps.setString(2, msg.getFromName());
			ps.setString(3, msg.getToName());
			
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeAll(conn, ps, null);
		}
		
		return count;
	}
	
	// 已读消息
	public List<Message> hasReadMsg(Message msg) {
		List<Message> msgList = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ws_message where 1=1 ");
		sql.append("and ( (fromName = ? and toName = ?) or ");
		sql.append("(toName = ? and fromName = ? and msgStatus = 1) ) ");
		sql.append("order by msgdate asc");
		
		Connection conn = BaseDao.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql.toString());
			
			ps.setString(1, msg.getFromName());
			ps.setString(2, msg.getToName());
			
			ps.setString(3, msg.getFromName());
			ps.setString(4, msg.getToName());
						
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Message m = new Message();
				
				m.setMsgId(rs.getInt("msgId"));
				m.setFromName(rs.getString("fromName"));
				m.setToName(rs.getString("toName"));
				m.setMsgContent(rs.getString("msgContent"));
				m.setMsgStatus(rs.getInt("msgStatus"));
				m.setMsgDate(rs.getTimestamp("msgDate"));
				
				msgList.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeAll(conn, ps, rs);
		}
		
		return msgList;
	}
	
	// 未读消息 用于sse
	public Message notReadMsg(Message msg) {
		Message m = new Message();
		
		String sql = "select * from ws_message where toName = ? and fromName = ? and msgStatus = 0";
		
		Connection conn = BaseDao.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, msg.getFromName());
			ps.setString(2, msg.getToName());
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				
				m.setMsgId(rs.getInt("msgId"));
				m.setFromName(rs.getString("fromName"));
				m.setToName(rs.getString("toName"));
				m.setMsgContent(rs.getString("msgContent"));
				m.setMsgStatus(rs.getInt("msgStatus"));
				m.setMsgDate(rs.getTimestamp("msgDate"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeAll(conn, ps, rs);
		}
		return m;
	}	

	// 修改当前消息为已读
	public int updMsgStatus(Message msg) {
		int count = -1;
		
		String sql = "update ws_message set msgStatus = 1 where fromName = ? and toName = ? and msgStatus = 0";
		
		Connection conn = BaseDao.getConn();
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, msg.getToName());
			ps.setString(2, msg.getFromName());
			
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeAll(conn, ps, null);
		}
		
		return count;		
	}

	/**
	 * 修改发给我的所有消息为已读
	 * @param msg
	 * @return
	 */
	public int updMsgStatusInLoad(Message msg) {
		int count = -1;
		
		StringBuffer sql = new StringBuffer("update ws_message set msgstatus = 1 ");		
		sql.append("where fromname = ? and toname = ? ");
		sql.append("and msgstatus = 0");
		
		Connection conn = BaseDao.getConn();
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, msg.getToName());
			ps.setString(2, msg.getFromName());
			
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeAll(conn, ps, null);
		}
		
		return count;			
	}
	
}
