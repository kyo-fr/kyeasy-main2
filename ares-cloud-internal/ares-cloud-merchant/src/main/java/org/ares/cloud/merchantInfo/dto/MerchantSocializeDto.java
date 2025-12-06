package org.ares.cloud.merchantInfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;

/**
* @author hugo tangxkwork@163.com
* @description 商户社交 数据模型
* @version 1.0.0
* @date 2024-10-09
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户社交")
public class MerchantSocializeDto extends TenantDto {
	private static final long serialVersionUID = 1L;

	@Schema(description = "微信二维码")
	@JsonProperty(value = "wechat")
	@Size(max = 1024, message = "wechat长度不能超过1024")
	private String wechat;

	@Schema(description = "whatsApp")
	@JsonProperty(value = "whatsApp")
	@Size(max = 1024, message = "whatsApp长度不能超过1024")
	private String whatsApp;

	@Schema(description = "tiktok")
	@JsonProperty(value = "tiktok")
	@Size(max = 1024, message = "tiktok长度不能超过1024")
	private String tiktok;


	@Schema(description = "youtube")
	@JsonProperty(value = "youtube")
	@Size(max = 1024, message = "youtube长度不能超过1024")
	private String youtube;

	@Schema(description = "脸书")
	@JsonProperty(value = "faceBook")
	@Size(max = 1024, message = "faceBook长度不能超过1024")
	private String faceBook;


	@Schema(description = "抖音中国")
	@JsonProperty(value = "douYin")
	@Size(max = 1024, message = "douYin长度不能超过1024")
	private String douYin;


	@Schema(description = "小红书")
	@JsonProperty(value = "redBook")
	@Size(max = 1024, message = "redBook长度不能超过1024")
	private String redBook;

	@Schema(description = "推特")
	@JsonProperty(value = "twitter")
	@Size(max = 1024, message = "twitter长度不能超过1024")
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