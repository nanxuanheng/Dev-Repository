package com.tensquare.sms.consumer;


import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: tensquare_parent
 * @description:
 **/
@Component
@RabbitListener(queues = "tensquare-sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @RabbitHandler
    public void sendSms(Map<String, String> map) {
        try {
            String mobile = map.get("mobile");
            String templatecode = map.get("templatecode");
            String tempateparam = map.get("tempateparam");
            //调用阿里云通信发送短信
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(mobile, templatecode, tempateparam);
            System.out.println(sendSmsResponse.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
