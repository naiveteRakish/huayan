package com.whayer.cloud.storage.business.base.data.msg;
/**
 * @author fs
 * 消息订阅数据对象
 * 
 */
public class MsgSubscribeData {
	/**
     * 消息订阅ID
     */
	private String id;
	/**
     *  消息订阅对象
     */
	private MsgSubscribeObject subscribeObj;
	/**
     *  消息订阅者对象
     */
	private MsgSubscriber subscriber;
	/**
	 * 获取
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取
	 * @return subscribeObj
	 */
	public MsgSubscribeObject getSubscribeObj() {
		return subscribeObj;
	}
	/**
	 * 设置
	 * @param subscribeObj
	 */
	public void setSubscribeObj(MsgSubscribeObject subscribeObj) {
		this.subscribeObj = subscribeObj;
	}
	/**
	 * 获取
	 * @return subscriber
	 */
	public MsgSubscriber getSubscriber() {
		return subscriber;
	}
	/**
	 * 设置
	 * @param subscriber
	 */
	public void setSubscriber(MsgSubscriber subscriber) {
		this.subscriber = subscriber;
	}
	
}
