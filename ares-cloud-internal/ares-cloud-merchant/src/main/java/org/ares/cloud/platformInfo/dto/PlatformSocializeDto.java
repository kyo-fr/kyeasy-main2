package org.ares.cloud.platformInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 平台海外社交 数据模型
* @version 1.0.0
* @date 2024-10-15
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "平台海外社交")
public class PlatformSocializeDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "whatsApp",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "whatsApp")
	@Size(max = 255, message = "validation.size.max")
	private String whatsApp;

	@Schema(description = "微信",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "wechat")
	@Size(max = 255, message = "validation.size.max")
	private String wechat;


	@Schema(description = "tiktok",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "tiktok")
	@Size(max = 255, message = "validation.size.max")
	private String tiktok;


	@Schema(description = "youtube",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "youtube")
	@Size(max = 255, message = "validation.size.max")
	private String youtube;

	@Schema(description = "facebook",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "facebook")
	@Size(max = 255, message = "validation.size.max")
	private String facebook;

	@Schema(description = "哔哩哔哩",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "bilibili")
	@Size(max = 255, message = "validation.size.max")
	private String bilibili;

	@Schema(description = "抖音中国",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "douYin")
	@Size(max = 255, message = "validation.size.max")
	private String douYin;

	@Schema(description = "twitter",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "twitter")
	@Size(max = 255, message = "validation.size.max")
	private String twitter;


	@Schema(description = "官方介绍视频(中国)",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "chinaVideo")
	@Size(max = 255, message = "validation.size.max")
	private String chinaVideo;


	@Schema(description = "官方介绍视频(海外)",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "foreignVideo")
	@Size(max = 255, message = "validation.size.max")
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