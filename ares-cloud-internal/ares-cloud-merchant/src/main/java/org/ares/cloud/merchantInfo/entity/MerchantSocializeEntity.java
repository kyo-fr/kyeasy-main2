package org.ares.cloud.merchantInfo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户社交 实体
* @version 1.0.0
* @date 2024-10-09
*/
@TableName("merchant_socialize")
@EqualsAndHashCode(callSuper = false)
public class MerchantSocializeEntity extends TenantEntity {

	private String wechat;


	private String whatsApp;

	private String tiktok;

	private String youtube;

	private String faceBook;

	private String douYin;

	private String redBook;

	private String twitter;


	public String getWechat() {
		return wechat;
	}

	public String getWhatsApp() {
		return whatsApp;
	}

	public String getTiktok() {
		return tiktok;
	}

	public String getYoutube() {
		return youtube;
	}

	public String getFaceBook() {
		return faceBook;
	}

	public String getDouYin() {
		return douYin;
	}

	public String getRedBook() {
		return redBook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public void setWhatsApp(String whatsApp) {
		this.whatsApp = whatsApp;
	}

	public void setTiktok(String tiktok) {
		this.tiktok = tiktok;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public void setFaceBook(String faceBook) {
		this.faceBook = faceBook;
	}

	public void setDouYin(String douYin) {
		this.douYin = douYin;
	}

	public void setRedBook(String redBook) {
		this.redBook = redBook;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
}