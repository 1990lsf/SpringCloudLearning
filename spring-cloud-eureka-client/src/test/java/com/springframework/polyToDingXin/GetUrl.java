package com.springframework.polyToDingXin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springframework.util.MD5Util;
import com.springframework.util.ParamSortUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/3/27
 * @see GetUrl
 * @since 1.0
 */
public class GetUrl {

    private static Logger logger= LoggerFactory.getLogger(GetUrl.class);
    //测试环境
    private String format="json";
    private String pid="90047";
    private String prodPid="11124";
    private String authCode="I(U@3r{W$-";
    private String dingXinUrl="http://api.platform.yinghezhong.com";
    //生产环境
    private String prodAuthCode="B!:h`Q=-&f";
    private String prodDingXinUrl="http://api.open.yinghezhong.com";
    public void getUrl(Map<String,Object> requestMap) {


        requestMap.put("format", format);
        requestMap.put("pid", prodPid);
        String paramResult = "";
        StringBuffer param = new StringBuffer();
        String _sig = "";
        Map<String, Object> sortMap = ParamSortUtil.sortMapByKey(requestMap);
        if (sortMap != null && !sortMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
                param.append(entry.getKey());
                param.append("=");
                param.append(entry.getValue());
                param.append("&");
            }
            paramResult = param.toString();
            if (paramResult.endsWith("&")) {
                paramResult = paramResult.substring(0, paramResult.length() - 1);
            }
        }
        _sig = MD5Util.Md32(MD5Util.Md32(prodAuthCode + paramResult) + prodAuthCode);
        String url = prodDingXinUrl + "/"+requestMap.get("method")+"/?" + paramResult + "&_sig=" + _sig;
        System.out.println(url);

    }

    public static void main(String[] args){


//        getSeatSessionState();
//
//        getScreenInfo();
////
//        getSessionDetail();

//        getCinemas();
        getCinemaConfig();

//        getCinemaSessions();

//        getPrintTicket();

    }

    public static void getSeatSessionState(){
        logger.info("[泵]-[鼎新]-[场次座位图]-开始");
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("cid", 20002929);
        requestMap.put("play_id", 43592715);
        requestMap.put("play_update_time", "2017-03-31 20:03:00");
        requestMap.put("method","play/seat-status");
        GetUrl getUrl=new GetUrl();
        getUrl.getUrl(requestMap);
        logger.info("[泵]-[鼎新]-[场次座位图]-结束");
    }


    public static void getScreenInfo(){
        logger.info("[泵]-[鼎新]-[影厅信息]-开始");
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("cid", 11);
        requestMap.put("hall_id", 438);
        requestMap.put("method","cinema/hall-seats");
        GetUrl getUrl=new GetUrl();
        getUrl.getUrl(requestMap);
        logger.info("[泵]-[鼎新]-[影厅信息]-结束");

    }

    public static void getSessionDetail(){
        logger.info("[泵]-[鼎新]-[场次信息]-开始");
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("cid", 11);
        requestMap.put("play_id", 226036);
        requestMap.put("method","play/info");
        GetUrl getUrl=new GetUrl();
        getUrl.getUrl(requestMap);
        logger.info("[泵]-[鼎新]-[场次信息]-结束");
    }

    public static void getCinemas(){
        logger.info("[泵]-[鼎新]-[影城列表]-开始");
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("method","partner/cinemas");
        GetUrl getUrl=new GetUrl();
        getUrl.getUrl(requestMap);
        logger.info("[泵]-[鼎新]-[影城列表]-结束");
    }


    public static void getCinemaConfig(){
        logger.info("[泵]-[鼎新]-[影城配置信息]-开始");
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("cid",20002929);
        requestMap.put("method","cinema/config");
        GetUrl getUrl=new GetUrl();
        getUrl.getUrl(requestMap);
        logger.info("[泵]-[鼎新]-[影城配置信息]-结束");
    }


    public static void getCinemaSessions(){
        logger.info("[泵]-[鼎新]-[影城场次信息]-开始");
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("cid",20002921);
        requestMap.put("start","2017-4-29");
        requestMap.put("end","2017-4-30");
        requestMap.put("method","cinema/plays");
        GetUrl getUrl=new GetUrl();
        getUrl.getUrl(requestMap);
        logger.info("[泵]-[鼎新]-[场次场次信息]-结束");
    }

    public static  void getPrintTicket(){
        logger.info("[泵]-[鼎新]-[打印影票]-开始");
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("cid",31);
        requestMap.put("ticket_flag1","021001");
        requestMap.put("ticket_flag2","311053");
        requestMap.put("tickets","1000000000194534");
        requestMap.put("method","ticket/print");
        GetUrl getUrl=new GetUrl();
        getUrl.getUrl(requestMap);
        logger.info("[泵]-[鼎新]-[打印影票]-结束");
    }


}
