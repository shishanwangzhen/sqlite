package com.corewell.sqlite.time;

import com.alibaba.fastjson.JSON;
import com.corewell.sqlite.dao.DoatableMapper;
import com.corewell.sqlite.domain.Doatable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: wangzhen
 * @Date: 2022/11/08/14:28
 * @Description: 将tlink传感器数据同步到省农业平台
 */
@Component
public class ScheduledTasks {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DoatableMapper doatableMapper;

    private static final String JIANG_NING_URL = "http://124.70.193.186:9100/api/1.0/public/iotdata/receive";
    private static final String SEND_SINGLE_INFO_URL = "http://210.12.220.220:8012/dataswitch-sq/sendApi/sendSingleInfo";
    Map<String, String> map1 = new HashMap<>();
    Map<String, Object> map2 = new HashMap<>();
    HttpHeaders headers = new HttpHeaders();
    DecimalFormat df = new DecimalFormat("0.0");


    /**
     * 常用表达式例子
     * (1)0/2 * * * * ? 表示每2秒执行任务
     * (1)0 0/2 * * * ? 表示每2分钟 执行任务
     * (1)0 0 2 1 * ?表示在每月的1日的凌晨2点调整任务
     * (2)0 15 10 ? * MON-FRI 表示周一到周五每天上午10:15执行作业
     * (3) 0 15 10 ? 6L 2002-2006 表示2002-2006年的每个月的最后一个星期五上午10:15执行作
     * (4)0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
     * (5) 0 0/30 9-17 * * ? 朝九晚五工作时间内每半小时
     * (6)0 0 12 ? * WED 表示每个星期三中午12点
     * (7)0 0 12 * * ? 每天中午12点触发
     * (8)0 15 10 ? * * 每天上午10:15触发
     * (9)0 15 10 * * ? 每天上午10:15触发
     * (10)0 15 10 * * ? 每天上午10:15触发
     * (11) 0 15 10 * * ? 2005 2005年的每天上午10:15触发
     * (12)0 * 14 * * ? 在每天下午2点到下午2:59期间的每1分钟触发(13)00/514**? 在每天下午2点到下午2:55期间的每5分钟触发
     * (14)0 0/5 14,18 * * ? 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
     * (15)0 0-5 14 * * ? 在每天下午2点到下午2:05期间的每1分钟触发
     */


    @Scheduled(cron = "0/20 * * * * ?")
    public void ScheduledTasksDw() {
        System.out.println("————————————$$$$$$$$$$$" + new Date() + "定时任务ScheduledTasksDw开始————————————————————");
        try {
            Doatable doatable = doatableMapper.findOne();
            System.out.println("参数：：" + JSON.toJSONString(doatable));
            String hour = doatable.getStime().substring(11, 13);
            if (hour.startsWith("0")) {
                hour.replace("0", "");
            }
            String min = doatable.getStime().substring(14, 16);
            if (min.startsWith("0")) {
                min.replace("0", "");
            }
            String s = doatable.getStime().substring(17);
            if (s.startsWith("0")) {
                s.replace("0", "");
            }
            System.out.println("时分秒: " + hour + ":" + min + ":" + s);

            double n = Math.sin(Double.valueOf(hour) * Double.valueOf(min) * Double.valueOf(s));
            double tempvalue = doatable.getTempvalue();
            double phvalue = doatable.getPhvalue();
            double dovalue = doatable.getDovalue();
            String waterTemp = df.format(tempvalue * (n * 0.1 + 0.95));
            String ph = df.format(phvalue * (n * 0.1 + 0.95));
            String dissolveO = df.format(dovalue * (n * 0.1 + 0.95));
            map1.put("waterTemp", waterTemp);
            map1.put("ph", ph);
            map1.put("dissolveO", dissolveO);

            map2.put("deviceId", "KWCARXCJ0A211101");
            map2.put("sessionKey", "121345");
            map2.put("data", map1);
            System.out.println("KWCARXCJ0A211101参数："+map2);
            ResponseEntity<String> response1 = restTemplate.postForEntity(SEND_SINGLE_INFO_URL, new HttpEntity<Map>(map2, null), String.class);
            System.out.println(response1.getBody());

            waterTemp = df.format(tempvalue * (n * 0.1 + 1.1));
            ph = df.format(phvalue * (n * 0.1 + 1.1));
            dissolveO = df.format(dovalue * (n * 0.1 + 1.1));
            map1.put("waterTemp", waterTemp);
            map1.put("ph", ph);
            map1.put("dissolveO", dissolveO);

            map2.put("deviceId", "KWCARXCJ0A211102");
            map2.put("sessionKey", "121345");
            map2.put("data", map1);
            System.out.println("KWCARXCJ0A211102参数："+map2);
            ResponseEntity<String> response2 = restTemplate.postForEntity(SEND_SINGLE_INFO_URL, new HttpEntity<Map>(map2, null), String.class);
            System.out.println(response2.getBody());

            waterTemp = df.format(tempvalue * (n * 0.12 + 0.94));
            ph = df.format(phvalue * (n * 0.12 + 0.94));
            dissolveO = df.format(dovalue * (n * 0.12 + 0.94));
            map1.put("waterTemp", waterTemp);
            map1.put("ph", ph);
            map1.put("dissolveO", dissolveO);

            map2.put("deviceId", "KWCARXCJ0A211104");
            map2.put("sessionKey", "121345");
            map2.put("data", map1);
            System.out.println("KWCARXCJ0A211103参数："+map2);
            ResponseEntity<String> response3 = restTemplate.postForEntity(JIANG_NING_URL, new HttpEntity<Map>(map2, null), String.class);
            System.out.println(response3.getBody());
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}






