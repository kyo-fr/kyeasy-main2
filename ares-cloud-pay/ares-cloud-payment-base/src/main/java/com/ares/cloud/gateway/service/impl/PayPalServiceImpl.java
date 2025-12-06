package com.ares.cloud.gateway.service.impl;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import com.ares.cloud.gateway.service.IPayPalService;
import org.springframework.stereotype.Service;

/**
 * @author hugo
 * @version 1.0.0
 * @description TODO
 * @date 2022/10/20 11:27
 */
@Service
@Slf4j
public class PayPalServiceImpl implements IPayPalService {
    /**
     * 支付回调
     */
//    @Override
//    public void callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        log.info("receivePaypalStatus >>>>>>>>>> 进入paypal后台支付通知");
//        PrintWriter out = response.getWriter();
//        try {
//            /**
//             * 获取paypal请求参数,并拼接验证参数
//             */
//            Enumeration<String> en = request.getParameterNames();
//            String str = "cmd=_notify-validate";
//            while (en.hasMoreElements()) {
//                String paramName = en.nextElement();
//                String paramValue = request.getParameter(paramName);
//                //此处的编码一定要和自己的网站编码一致，不然会出现乱码，paypal回复的通知为‘INVALID’
//                str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue, "utf-8");
//            }
//            //建议在此将接受到的信息 str 记录到日志文件中以确认是否收到 IPN 信息
//            log.info("paypal后台支付通知：" + str);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
    /**
     * 支付回调
     * @param data
     */
    @Override
    public void callback(JSONObject data) {
        String payStr = data.getString("data");
         System.out.println(payStr);
//        if(StringUtils.isBlank(payStr)) throw new BusinessException(MessageUtils.get("pay_no_data"));
//        JSONObject payData = JSONObject.parseObject(AesUtil.decrypt(data.getString("data")));
//        CallbackPayBean callbackPayBean =  new CallbackPayBean();
//        callbackPayBean.setOrderNo(payData.getString("orderNo"));
//        callbackPayBean.setTotalAmount(payData.get("totalAmount")+"");
//        callbackPayBean.setPayAccount(payData.getString("payAccount"));
//        callbackPayBean.setCardNumber(payData.getString("cardNumber"));
//        callbackPayBean.setPayType(payData.getString("payType"));
//        callbackPayBean.setPayTypeCode(payData.getString("payType"));
//        callbackPayBean.setCallBackData(payData);
//        payCallbackService.callBack(callbackPayBean);
    }
}
