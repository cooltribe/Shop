package com.searun.shop.data;

import java.io.Serializable;


/**
  * 消息传输类
  * @类名: MessageDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月27日 上午8:49:49
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class MessageDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7326769180320181485L;

	private String title;// 消息标题
	
	private String content;// 消息内容
	
	//// 删除状态（未删除、发送者删除、接收者删除）（nonDelete, fromDelete, toDelete）
	private String deleteStatus;// 删除状态
	
	private Boolean isRead;// 是否标记已读
	
	private Boolean isSaveDraftbox;// 是否保存在草稿箱
	
	private String toMemberUsername;// 消息接收方会员用户名
	
	private MemberDto fromMember;// 消息发出会员,为null时表示管理员
	
	private MemberDto toMember;// 消息接收会员,为null时表示管理员
	
	//草稿箱列表：DreaftMessages, 
	//发件箱列表：OutMessages,
	//收件箱列表：InMessages
	private String command;//操作命令
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Boolean getIsSaveDraftbox() {
		return isSaveDraftbox;
	}

	public void setIsSaveDraftbox(Boolean isSaveDraftbox) {
		this.isSaveDraftbox = isSaveDraftbox;
	}

	public MemberDto getFromMember() {
		return fromMember;
	}

	public void setFromMember(MemberDto fromMember) {
		this.fromMember = fromMember;
	}

	public MemberDto getToMember() {
		return toMember;
	}

	public void setToMember(MemberDto toMember) {
		this.toMember = toMember;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getToMemberUsername() {
		return toMemberUsername;
	}

	public void setToMemberUsername(String toMemberUsername) {
		this.toMemberUsername = toMemberUsername;
	}
	
	
	
}