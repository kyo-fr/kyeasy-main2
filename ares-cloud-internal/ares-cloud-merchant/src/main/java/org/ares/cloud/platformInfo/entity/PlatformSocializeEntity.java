package org.ares.cloud.platformInfo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 平台海外社交 实体
* @version 1.0.0
* @date 2024-10-15
*/
@TableName("platform_socialize")
@EqualsAndHashCode(callSuper = false)
public class PlatformSocializeEntity extends BaseEntity {

	private String whatsApp;

	private String wechat;

	private String tiktok;

	private String youtube;

	private String facebook;

	private String bilibili;

	private String douYin;

	private String twitter;

	private String chinaVideo;

	private String foreignVideo;


	public String getWhatsApp() {
		return whatsApp;
	}

	public String getWechat() {
		return wechat;
	}

	public String getTiktok() {
		return tiktok;
	}

	public String getYoutube() {
		return youtube;
	}

	public String getFacebook() {
		return facebook;
	}

	public String getBilibili() {
		return bilibili;
	}

	public String getDouYin() {
		return douYin;
	}

	public String getTwitter() {
		return twitter;
	}

	public String getChinaVideo() {
		return chinaVideo;
	}

	public String getForeignVideo() {
		return foreignVideo;
	}

	public void setWhatsApp(String whatsApp) {
		this.whatsApp = whatsApp;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public void setTiktok(String tiktok) {
		this.tiktok = tiktok;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public void setBilibili(String bilibili) {
		this.bilibili = bilibili;
	}

	public void setDouYin(String douYin) {
		this.douYin = douYin;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public void setChinaVideo(String chinaVideo) {
		this.chinaVideo = chinaVideo;
	}

	public void setForeignVideo(String foreignVideo) {
		this.foreignVideo = foreignVideo;
	}

}