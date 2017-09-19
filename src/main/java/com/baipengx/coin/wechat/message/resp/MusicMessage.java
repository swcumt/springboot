package com.baipengx.coin.wechat.message.resp;

/**
 * 音乐消息
 * 
 * @author siwei
 */
public class MusicMessage extends BaseMessage {
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}
