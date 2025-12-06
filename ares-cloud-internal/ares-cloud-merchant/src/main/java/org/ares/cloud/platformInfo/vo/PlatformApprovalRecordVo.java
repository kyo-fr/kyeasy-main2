package org.ares.cloud.platformInfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;

public class PlatformApprovalRecordVo {
//    @Schema(description = "租户id")
//    private String tenantId;

    @Schema(description = "总存储")
    private Long sumMemory;

    @Schema(description = "已使用存储")
    private Long usedMemory;

    @Schema(description = "剩余存储")
    private Long residueMemory;


    @Schema(description = "图片占用存储")
    private Long imgMemory;

    @Schema(description = "视频占用存储")
    private Long videoMemory;

//    @Schema(description = "数据行占用存储")
//    private Long dataMemory;

    @Schema(description = "订单占用存储")
    private Long orderMemory;

    @Schema(description = "发票占用存储")
    private Long invoiceMemory;

    @Schema(description = "商品占用存储")
    private Long productMemory;


    public Long getSumMemory() {
        return sumMemory;
    }

    public void setSumMemory(Long sumMemory) {
        this.sumMemory = sumMemory;
    }

    public Long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(Long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public Long getImgMemory() {
        return imgMemory;
    }

    public void setImgMemory(Long imgMemory) {
        this.imgMemory = imgMemory;
    }

    public Long getVideoMemory() {
        return videoMemory;
    }

    public void setVideoMemory(Long videoMemory) {
        this.videoMemory = videoMemory;
    }

//    public Long getDataMemory() {
//        return dataMemory;
//    }
//
//    public void setDataMemory(Long dataMemory) {
//        this.dataMemory = dataMemory;
//    }

    public Long getOrderMemory() {
        return orderMemory;
    }

    public void setOrderMemory(Long orderMemory) {
        this.orderMemory = orderMemory;
    }

    public Long getInvoiceMemory() {
        return invoiceMemory;
    }

    public void setInvoiceMemory(Long invoiceMemory) {
        this.invoiceMemory = invoiceMemory;
    }

    public Long getProductMemory() {
        return productMemory;
    }

    public void setProductMemory(Long productMemory) {
        this.productMemory = productMemory;
    }

    public Long getResidueMemory() {
        return residueMemory;
    }

    public void setResidueMemory(Long residueMemory) {
        this.residueMemory = residueMemory;
    }

//    public String getTenantId() {
//        return tenantId;
//    }
//    public void setTenantId(String tenantId) {
//        this.tenantId = tenantId;
//    }



}
