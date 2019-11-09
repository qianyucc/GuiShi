package com.qianyu.wxapp.controller;

import com.alibaba.fastjson.JSONObject;
import com.qianyu.wxapp.service.WxService;
import com.qianyu.wxapp.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lijing
 * @date 2019-04-20-18:19
 * @discroption 微信小程序控制器
 */
@RestController
public class WxController {
    @Autowired
    private WxService wxService;

    @PostMapping(value = "/api/user/login")
    public Map<String, Object> login(HttpServletRequest req) {
        JSONObject reqData = getReqData(req);
        String code = reqData.getString("code");
        String rawData = reqData.getString("rawData");
        String signature = reqData.getString("signature");
        String encryptedData = reqData.getString("encryptedData");
        String iv = reqData.getString("iv");
        return wxService.login(code, rawData, signature, encryptedData, iv);
    }

    @PostMapping(value = "/api/add")
    public Map<String, Object> add(HttpServletRequest req) {
        HashMap<String, Object> map = null;
        JSONObject reqData = getReqData(req);
        String type = reqData.getString("type");
        JSONObject realData = reqData.getJSONObject("data");
        if (type == null || realData == null) {
            return Constant.paraErrorMap;
        }
        if ("countDown".equals(type)) {
            map = wxService.addCountDown(realData);
        } else if ("letter".equals(type)) {
            map = wxService.addLetter(realData);
        } else if ("plan".equals(type)) {
            map = wxService.addPlan(realData);
        } else if ("diary".equals(type)) {
            map = wxService.addDiary(realData);
        } else {
            return Constant.paraInvalidMap;
        }
        return map;
    }

    @GetMapping(value = "/api/getCountDown")
    public Map<String, Object> getCountDown(@RequestParam(value = "is_all", required = true) int isAll,
                                            @RequestParam(value = "uid", required = true) int uid,
                                            @RequestParam(value = "date", required = false) String date) {
        if (isAll == 1) {
            return wxService.findAllCountDowns(uid);
        } else if (isAll == 0) {
            if(date==null){
                return wxService.findCountDownAfterNow(uid);
            }
            Date parse = null;
            try {
                parse = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return Constant.paraInvalidMap;
            }
            return wxService.findCountDownByDate(uid, parse);
        } else {
            return Constant.paraInvalidMap;
        }
    }

    @GetMapping(value = "/api/getPlan")
    public Map<String, Object> getPlan(int isToday, int uid) {
        if (isToday == 1) {
            return wxService.getPlanToday(uid);
        } else if (isToday == 0) {
            return wxService.getAllPlan(uid);
        } else {
            return Constant.paraInvalidMap;
        }
    }

    @GetMapping(value = "/api/getLetter")
    public Map<String, Object> getLetter(int uid,
                                         @RequestParam(value = "is_all") int isAll,
                                         @RequestParam(required = false) String date) {

        if (isAll == 0) {
            Date parse = null;
            try {
                parse = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return Constant.paraInvalidMap;
            }
            return wxService.getLetter(uid, parse);
        } else if (isAll == 1) {
            return wxService.getAllLetter(uid);
        } else {
            return Constant.paraInvalidMap;
        }
    }

    @GetMapping(value = "/api/getDiary")
    public Map<String, Object> getDiary(int uid,
                                        @RequestParam(value = "is_all") int isAll,
                                        @RequestParam(required = false) String date) {
        if (isAll == 0) {
            Date parse = null;
            try {
                parse = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return Constant.paraInvalidMap;
            }
            return wxService.getDiary(uid, parse);
        } else if (isAll == 1) {
            return wxService.getAllDiary(uid);
        } else {
            return Constant.paraInvalidMap;
        }
    }

    @GetMapping(value = "/api/delete")
    public Map<String, Object> delete(int id, String type) {
        if ("countDown".equals(type)) {
            return wxService.deleteCountDown(id);
        } else if ("letter".equals(type)) {
            return wxService.deleteLetter(id);
        } else if ("diary".equals(type)) {
            return wxService.deleteDiary(id);
        } else if ("plan".equals(type)) {
            return wxService.deletePlan(id);
        } else {
            return Constant.paraInvalidMap;
        }
    }

    @PostMapping(value = "/api/update")
    public Map<String, Object> update(HttpServletRequest req) {
        JSONObject reqData = getReqData(req);
        int id = reqData.getInteger("id");
        String type = reqData.getString("type");
        JSONObject data = reqData.getJSONObject("data");
        if ("countDown".equals(type)) {
            return wxService.upadteCountDown(id,data);
        } else if ("letter".equals(type)) {
            return wxService.upadteLetter(id,data);
        } else if ("diary".equals(type)) {
            return wxService.upadteDiary(id,data);
        } else if ("plan".equals(type)) {
            return wxService.upadtePlan(id,data);
        } else {
            return Constant.paraErrorMap;
        }
    }

    @GetMapping(value = "/api/getPlanCount")
    public Map<String,Integer> getPlanCount(Integer uid){
        return wxService.getPlanCount(uid);
    }

    @GetMapping(value = "/api/getPlanGroupByDate")
    public Map<String, List<Map<String, Object>>> getPlanGroupByDate(Integer uid){
        return wxService.getPlanGroupByDate(uid);
    }

    /**
     * 解析HttpServletRequest中的json数据，返回一个JSONObject对象
     *
     * @param req
     * @return
     */
    private JSONObject getReqData(HttpServletRequest req) {
        JSONObject jsonObject = null;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(req.getInputStream()));
            String s = null;
            StringBuffer sb = new StringBuffer();
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s);
            }
            jsonObject = JSONObject.parseObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }
}
