package com.qianyu.wxapp.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.qianyu.wxapp.entity.*;
import com.qianyu.wxapp.repository.*;
import com.qianyu.wxapp.service.WxService;
import com.qianyu.wxapp.utils.Constant;
import com.qianyu.wxapp.utils.WxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lijing
 * @date 2019-04-20-18:21
 * @discroption 微信小程序业务层实现
 */
@Service
public class WxServiceImpl implements WxService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CountDownRepository countDownRepository;
    @Autowired
    private LetterRepository letterRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private DiaryRepository diaryRepository;

    @Override
    public Map<String, Object> login(String code, String rawData, String signature, String encryptedData, String iv) {
        //System.out.println("code = [" + code + "], rawData = [" + rawData + "], signature = [" + signature + "], encryptedData = [" + encryptedData + "], iv = [" + iv + "]");
        HashMap<String, Object> map = new HashMap<>();
        if (code == null || signature == null || encryptedData == null || iv == null) {
            return Constant.paraErrorMap;
        }
        // 获取session_key
        JSONObject respData = getRespData(code);
        System.out.println("respData = " + respData);

        //解密操作
        JSONObject userInfo = WxUtils.getUserInfo(encryptedData, respData.getString("session_key"), iv);
        //System.out.println("userInfo = " + userInfo);

        List<User> userList = userRepository.findByOpenId(respData.getString("openid"));
        User u = null;
        if (userList.size() == 0) {
            u = new User();
            u.setCreateTime(new Date());
        } else {
            u = userList.get(0);
        }
        User userUpdated = getUser(u, respData, userInfo);
        User save = userRepository.save(userUpdated);

        // 返回数据
        Map<String, Object> respUserinfo = new HashMap<>();
        respUserinfo.put("uid", save.getId());
        respUserinfo.put("skey", save.getSkey());
        respUserinfo.put("create_time", DateUtil.formatDateTime(save.getCreateTime()));
        respUserinfo.put("update_time", DateUtil.formatDateTime(save.getUpdateTime()));
        map.put("error_code", 0);
        map.put("data", respUserinfo);
        return map;
    }

    @Override
    public HashMap<String, Object> addCountDown(JSONObject realData) {
        HashMap<String, Object> map = new HashMap<>();

        // 取出参数
        Integer uid = Integer.valueOf(realData.getString("uid"));
        String event = realData.getString("event");
        String time = realData.getString("endTime");
        Date endTime = DateUtil.parse(time, new SimpleDateFormat("yyyy-MM-dd"));
        // 校验参数
        if (uid == null || event == null || time == null) {
            return Constant.paraErrorMap;
        } else if (endTime == null) {
            return Constant.paraInvalidMap;
        }

        // 查询是否有重复项
        List<CountDown> list = countDownRepository.findByUidAndEndTimeAndEvent(uid, endTime, event);
        if (list.size() > 0) {
            return Constant.itemRepeatMap;
        }

        // 创建CountDown对象
        CountDown countDown = new CountDown();
        countDown.setUid(uid);
        countDown.setEvent(event);
        countDown.setEndTime(endTime);
        //插入数据
        CountDown save = countDownRepository.save(countDown);

        // 返回数据
        map.put("error_code", "0");
        map.put("error_msg", "倒计时添加成功");
        return map;
    }

    @Override
    public HashMap<String, Object> addLetter(JSONObject realData) {
        HashMap<String, Object> map = new HashMap<>();

        // 取出参数
        Integer uid = Integer.valueOf(realData.getString("uid"));
        String title = realData.getString("title");
        String content = realData.getString("content");
        String mail = realData.getString("mail");
        String noticeTime = realData.getString("noticeTime");
        Date parse = DateUtil.parse(noticeTime, new SimpleDateFormat("yyyy-MM-dd"));

        // 校验参数
        if (uid == null || title == null || content == null || mail == null) {
            return Constant.paraErrorMap;
        } else if (parse == null) {
            return Constant.paraInvalidMap;
        }

        // 查询重复
        List<Letter> list = letterRepository.findByUidAndTitleAndContentAndMailAndNoticeTime(uid, title, content, mail, parse);
        if (list.size() > 0) {
            return Constant.itemRepeatMap;
        }

        // 创建Letter对象
        Letter letter = new Letter();
        letter.setUid(uid);
        letter.setTitle(title);
        letter.setContent(content);
        letter.setMail(mail);
        letter.setNoticeTime(parse);
        letter.setCreateTime(new Date());
        Letter save = letterRepository.save(letter);

        // 返回数据
        map.put("error_code", "0");
        map.put("error_msg", "给未来的一封信添加成功");
        return map;
    }

    @Override
    public HashMap<String, Object> addPlan(JSONObject realData) {
        System.out.println("realData = [" + realData + "]");
        HashMap<String, Object> map = new HashMap<>();
        // 取出参数
        Integer uid = Integer.valueOf(realData.getString("uid"));
        String content = realData.getString("content");
        String startTimeStr = DateUtil.today() + " " + realData.getString("startTime");
        String endTimeStr = DateUtil.today() + " " + realData.getString("endTime");

        Date startTime = DateUtil.parse(startTimeStr, "yyyy-MM-dd HH:mm");
        Date endTime = DateUtil.parse(endTimeStr, "yyyy-MM-dd HH:mm");
        System.out.println(DateUtil.formatDateTime(startTime));
        System.out.println(DateUtil.formatDateTime(endTime));

        // 校验参数
        if (uid == null || content == null || startTimeStr == null || endTimeStr == null) {
            return Constant.paraErrorMap;
        } else if (startTime == null || endTime == null) {
            return Constant.paraInvalidMap;
        }

        //判断是否有重复项
        List<Plan> list = planRepository.findByUidAndEndTimeAndStartTimeAndContent(uid, endTime, startTime, content);
        if (list.size() > 0) {
            return Constant.itemRepeatMap;
        }


        // 创建plan对象
        Plan plan = new Plan();
        plan.setUid(uid);
        plan.setContent(content);
        plan.setStartTime(startTime);
        plan.setEndTime(endTime);
        plan.setStatus(0);
        Plan save = planRepository.save(plan);
        // 返回数据
        map.put("error_code", "0");
        map.put("error_msg", "计划" + plan.getContent() + "添加成功");
        return map;
    }

    @Override
    public HashMap<String, Object> addDiary(JSONObject realData) {
        HashMap<String, Object> map = new HashMap<>();
        // 取出参数
        Integer uid = Integer.valueOf(realData.getString("uid"));
        String timeStr = realData.getString("time");
        String weather = realData.getString("weather");
        String content = realData.getString("content");
        String week = realData.getString("week");
        Date time = DateUtil.parse(timeStr, new SimpleDateFormat("yyyy-MM-dd"));
        // 校验参数
        if (uid == null || timeStr == null || weather == null || content == null) {
            return Constant.paraErrorMap;
        } else if (time == null) {
            return Constant.paraInvalidMap;
        }

        // 校验重复
        List<Diary> list = diaryRepository.findByUidAndTimeAndWeatherAndWeekAndContent(uid, time, weather, week, content);
        if (list.size() > 0) {
            return Constant.itemRepeatMap;
        }
        // 创建Diary对象
        Diary diary = new Diary();
        diary.setUid(uid);
        diary.setWeather(weather);
        diary.setWeek(week);
        diary.setTime(time);
        diary.setContent(content);
        Diary save = diaryRepository.save(diary);
        // 返回数据
        map.put("error_code", "0");
        map.put("error_msg", "日记添加成功");
        return map;
    }

    @Override
    public Map<String, Object> findAllCountDowns(int uid) {
        List<CountDown> list = countDownRepository.findByUid(uid);
        Comparator<CountDown> comparator = new Comparator<CountDown>() {
            @Override
            public int compare(CountDown o1, CountDown o2) {
                return -o1.getEndTime().compareTo(o2.getEndTime());
            }
        };
        list.sort(comparator);
        HashMap<String, Object> map = new HashMap<>();
        map.put("error_code", 0);
        map.put("data", list);
        return map;
    }

    @Override
    public Map<String, Object> findCountDownAfterNow(int uid) {
        HashMap<String, Object> map = new HashMap<>();
        Date date = DateUtil.parse(DateUtil.today());
        List<CountDown> list = countDownRepository.findByUidAndEndTimeAfter(uid, date);
        Comparator<CountDown> comparator = new Comparator<CountDown>() {
            @Override
            public int compare(CountDown o1, CountDown o2) {
                return o1.getEndTime().compareTo(o2.getEndTime());
            }
        };
        list.sort(comparator);
        List<Map<String, Object>> resList = new ArrayList<>();
        for (CountDown cd : list) {
            Map<String, Object> cdMap = BeanUtil.beanToMap(cd);
            long betweenDay = DateUtil.between(date, cd.getEndTime(), DateUnit.DAY);
            if (betweenDay <= 3) {
                cdMap.put("color", "red");
            } else if (betweenDay <= 14) {
                cdMap.put("color", "yellow");
            } else {
                cdMap.put("color", "green");
            }
            cdMap.put("restDays", betweenDay);
            resList.add(cdMap);
        }

        //region print result list
        for (Map<String, Object> tmpMap : resList) {
            System.out.println(tmpMap);
        }
        //endregion

        map.put("error_code", 0);
        map.put("data", resList);
        return map;
    }

    @Override
    public Map<String, Object> findCountDownByDate(int uid, Date date) {
        List<CountDown> list = countDownRepository.findByUidAndEndTime(uid, date);
        Comparator<CountDown> comparator = new Comparator<CountDown>() {
            @Override
            public int compare(CountDown o1, CountDown o2) {
                return -o1.getEndTime().compareTo(o2.getEndTime());
            }
        };
        list.sort(comparator);
        HashMap<String, Object> map = new HashMap<>();
        map.put("error_code", 0);
        map.put("data", list);
        return map;
    }

    @Override
    public Map<String, Object> getAllPlan(int uid) {
        HashMap<String, Object> map = new HashMap<>();
        List<Plan> list = planRepository.findByUid(uid);
        map.put("error_code", 0);
        map.put("data", list);
        return map;
    }

    @Override
    public Map<String, Object> getPlanToday(int uid) {
        HashMap<String, Object> map = new HashMap<>();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Plan> list = planRepository.findByUidAndStartTimeAfter(uid, date);
        Comparator<Plan> comparator = new Comparator<Plan>() {
            @Override
            public int compare(Plan o1, Plan o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        };
        list.sort(comparator);

        ArrayList<Map<String, Object>> newList = new ArrayList<>();
        ArrayList<Plan> uncomList = new ArrayList<>();
        for (Plan plan : list) {
            if (plan.getEndTime().before(new Date()) && plan.getStatus() == 0) {
                plan.setStatus(-1);
                planRepository.save(plan);
            }
            if(plan.getStatus() != 0){
                uncomList.add(plan);
                continue;
            }
            Map<String, Object> resMap = BeanUtil.beanToMap(plan);
            resMap.put("startTime", DateUtil.format(plan.getStartTime(), "HH:mm:ss"));
            resMap.put("endTime", DateUtil.format(plan.getEndTime(), "HH:mm:ss"));
            newList.add(resMap);
        }

        Comparator<Plan> comparator2 = new Comparator<Plan>() {
            @Override
            public int compare(Plan o1, Plan o2) {
                return -o1.getStartTime().compareTo(o2.getStartTime());
            }
        };
        uncomList.sort(comparator2);
        ArrayList<Map<String,Object>> uncomMapList = new ArrayList<>();
        for (Plan plan : uncomList) {
            Map<String, Object> resMap = BeanUtil.beanToMap(plan);
            resMap.put("startTime", DateUtil.format(plan.getStartTime(), "HH:mm:ss"));
            resMap.put("endTime", DateUtil.format(plan.getEndTime(), "HH:mm:ss"));
            uncomMapList.add(resMap);
        }

        for (Map<String, Object> stringObjectMap : uncomMapList) {
            newList.add(stringObjectMap);
        }

        //region print result list
        for (Map<String, Object> tmpMap : newList) {
            System.out.println(tmpMap);
        }
        //endregion

        map.put("error_code", 0);
        map.put("data", newList);
        return map;
    }

    @Override
    public Map<String, Object> getLetter(int uid, Date parse) {
        if (parse == null) {
            return Constant.paraErrorMap;
        }
        HashMap<String, Object> map = new HashMap<>();
        List<Letter> list = letterRepository.findByUidAndCreateTimeAfter(uid, parse);
        Comparator<Letter> comparator = new Comparator<Letter>() {
            @Override
            public int compare(Letter o1, Letter o2) {
                return -o1.getCreateTime().compareTo(o2.getCreateTime());
            }
        };
        list.sort(comparator);
        map.put("error_code", 0);
        map.put("data", list);
        return map;
    }

    @Override
    public Map<String, Object> getAllLetter(int uid) {
        HashMap<String, Object> map = new HashMap<>();
        List<Letter> list = letterRepository.findByUid(uid);
        Comparator<Letter> comparator = new Comparator<Letter>() {
            @Override
            public int compare(Letter o1, Letter o2) {
                return -o1.getCreateTime().compareTo(o2.getCreateTime());
            }
        };
        list.sort(comparator);
        map.put("error_code", 0);
        map.put("data", list);
        return map;
    }

    @Override
    public Map<String, Object> getDiary(int uid, Date parse) {
        if (parse == null) {
            return Constant.paraInvalidMap;
        }
        HashMap<String, Object> map = new HashMap<>();
        List<Diary> list = diaryRepository.findByUidAndTime(uid, parse);
        Comparator<Diary> comparator = new Comparator<Diary>() {
            @Override
            public int compare(Diary o1, Diary o2) {
                return -o1.getTime().compareTo(o2.getTime());
            }
        };
        list.sort(comparator);
        map.put("error_code", 0);
        map.put("data", list);
        return map;
    }

    @Override
    public Map<String, Object> getAllDiary(int uid) {
        HashMap<String, Object> map = new HashMap<>();
        List<Diary> list = diaryRepository.findByUid(uid);
        Comparator<Diary> comparator = new Comparator<Diary>() {
            @Override
            public int compare(Diary o1, Diary o2) {
                return -o1.getTime().compareTo(o2.getTime());
            }
        };
        list.sort(comparator);
        map.put("error_code", 0);
        map.put("data", list);
        return map;
    }

    @Override
    public Map<String, Object> deleteCountDown(int id) {
        System.out.println("id = [" + id + "]");
        countDownRepository.deleteById(id);
        return Constant.deleteSuccessMap;
    }

    @Override
    public Map<String, Object> deleteLetter(int id) {
        letterRepository.deleteById(id);
        return Constant.deleteSuccessMap;
    }

    @Override
    public Map<String, Object> deleteDiary(int id) {
        diaryRepository.deleteById(id);
        return Constant.deleteSuccessMap;
    }

    @Override
    public Map<String, Object> deletePlan(int id) {
        planRepository.deleteById(id);
        return Constant.deleteSuccessMap;
    }

    @Override
    public Map<String, Object> upadteCountDown(int id, JSONObject data) {
        // 判断参数是否完整
        if (data == null) {
            return Constant.paraErrorMap;
        }
        // 找到对应id的信息
        Optional<CountDown> byId = countDownRepository.findById(id);
        if (!byId.isPresent()) {
            return Constant.paraInvalidMap;
        }
        CountDown countDown = byId.get();
        System.out.println("countDown = " + countDown);

        // 设置参数
        String event = data.getString("event");
        String endTime = data.getString("endTime");
        if (event != null) {
            countDown.setEvent(event);
        }
        if (endTime != null) {
            Date parse = null;
            try {
                parse = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (endTime != null) {
                countDown.setEndTime(parse);
            } else {
                return Constant.paraErrorMap;
            }
        }

        // 更新数据
        CountDown save = countDownRepository.save(countDown);
        System.out.println("save = " + save);
        // 返回参数
        return Constant.getUpdateSuccessMap(save);
    }

    @Override
    public Map<String, Object> upadteLetter(int id, JSONObject data) {
        // 判断参数是否完整
        if (data == null) {
            return Constant.paraErrorMap;
        }
        // 找到对应id的信息
        Optional<Letter> byId = letterRepository.findById(id);
        if (!byId.isPresent()) {
            return Constant.paraInvalidMap;
        }
        Letter letter = byId.get();
        System.out.println("letter = " + letter);
        // 设置参数
        String title = data.getString("title");
        String content = data.getString("content");
        String mail = data.getString("mail");
        Date date = null;
        try {
            String noticeTimeStr = data.getString("noticeTime");
            if (noticeTimeStr != null) {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(noticeTimeStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (title != null) {
            letter.setTitle(title);
        }
        if (content != null) {
            letter.setContent(content);
        }
        if (mail != null) {
            letter.setMail(mail);
        }
        if (date != null) {
            letter.setNoticeTime(date);
        }
        // 更新数据
        Letter save = letterRepository.save(letter);
        System.out.println("save = " + save);
        // 返回参数
        return Constant.getUpdateSuccessMap(save);
    }

    @Override
    public Map<String, Object> upadteDiary(int id, JSONObject data) {
        System.out.println("id = [" + id + "], data = [" + data + "]");
        // 判断参数是否完整
        if (data == null) {
            return Constant.paraErrorMap;
        }
        // 找到对应id的信息
        Optional<Diary> byId = diaryRepository.findById(id);
        if (!byId.isPresent()) {
            return Constant.paraInvalidMap;
        }
        Diary diary = byId.get();
        System.out.println("diary = " + diary);
        // 设置参数
        Date time = null;
        try {
            String timeStr = data.getString("time");
            if (timeStr != null) {
                time = new SimpleDateFormat("yyyy-MM-dd").parse(timeStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return Constant.paraInvalidMap;
        }
        String weather = data.getString("weather");
        String week = data.getString("week");
        String content = data.getString("content");
        if (time != null) {
            diary.setTime(time);
        }
        if (weather != null) {
            diary.setWeather(weather);
        }
        if (week != null) {
            diary.setWeek(week);
        }
        if (content != null) {
            diary.setContent(content);
        }
        // 更新数据
        Diary save = diaryRepository.save(diary);
        System.out.println("save = " + save);
        // 返回参数
        return Constant.getUpdateSuccessMap(save);
    }

    @Override
    public Map<String, Object> upadtePlan(int id, JSONObject data) {
        // 判断参数是否完整
        if (data == null) {
            return Constant.paraErrorMap;
        }
        // 找到对应id的信息
        Optional<Plan> byId = planRepository.findById(id);
        if (!byId.isPresent()) {
            return Constant.paraInvalidMap;
        }
        Plan plan = byId.get();
        System.out.println("plan = " + plan);
        // 设置参数
        String content = data.getString("content");
        Integer status = data.getInteger("status");
        Date startTime = null;
        Date endTime = null;
        try {
            String startTimeStr = data.getString("startTime");
            String endTimeStr = data.getString("endTime");
            if (startTimeStr != null) {
                startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(DateUtil.today() + " " + startTimeStr);
            }
            if (endTimeStr != null) {
                endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(DateUtil.today() + " " + endTimeStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return Constant.paraInvalidMap;
        }
        if (content != null) {
            plan.setContent(content);
        }
        if (status != null) {
            plan.setStatus(status);
        }
        if (startTime != null) {
            plan.setStartTime(startTime);
        }
        if (endTime != null) {
            plan.setEndTime(endTime);
        }
        // 更新数据
        Plan save = planRepository.save(plan);
        System.out.println("save = " + save);
        // 返回参数
        return Constant.getUpdateSuccessMap(save);
    }

    @Override
    public Map<String, Integer> getPlanCount(Integer uid) {
        Integer[] ints = setPlanStatus(uid);
        Integer com = ints[0];
        Integer uncom = ints[1];
//        Integer com = planRepository.countAllByStatusAndUid(1, uid);
//        Integer uncom = planRepository.countAllByStatusAndUid(-1, uid);
        HashMap<String, Integer> resMap = new HashMap<>();
        resMap.put("completeNum", com);
        resMap.put("unConpleteNum", uncom);
        return resMap;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getPlanGroupByDate(Integer uid) {
        setPlanStatus(uid);
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                DateTime date1 = DateUtil.parse(o1);
                DateTime date2 = DateUtil.parse(o2);
                return -date1.compareTo(date2);
            }
        };
        TreeMap<String, List<Map<String, Object>>> map = new TreeMap<>(comparator);
        List<Plan> allPlans = planRepository.findByUid(uid);

        if (allPlans.size() != 0) {
            for (Plan plan : allPlans) {
                if (plan.getEndTime().after(DateUtil.parse(DateUtil.today()))) {
                    continue;
                }
                String date = DateUtil.formatDate(plan.getStartTime());
                // 将日期格式化
                Map<String, Object> planMap = BeanUtil.beanToMap(plan);
                planMap.put("startTime", DateUtil.format(plan.getStartTime(), "HH:mm:ss"));
                planMap.put("endTime", DateUtil.format(plan.getEndTime(), "HH:mm:ss"));

                if (map.containsKey(date)) {
                    map.get(date).add(planMap);
                } else {
                    List<Map<String, Object>> list = new ArrayList<>();
                    list.add(planMap);
                    map.put(date, list);
                }
            }

            //region print sorted map
            for (String key : map.keySet()) {
                System.out.println(key + " = " + map.get(key));
            }
            //endregion
        }
        return map;
    }

    /**
     * 判断计划是否完成，并更新status的值,并返回未完成的已完成数
     *
     * @param uid
     * @return
     */
    private Integer[] setPlanStatus(Integer uid) {
        Integer[] ints = new Integer[2];
        ints[0] = 0;
        ints[1] = 0;
        List<Plan> planList = planRepository.findByUid(uid);
        for (Plan plan : planList) {
            if (plan.getEndTime().before(DateUtil.parse(DateUtil.today()))) {
                if (plan.getStatus() == 0) {
                    plan.setStatus(-1);
                    planRepository.save(plan);
                }
                if (plan.getStatus().equals(1)) {
                    ints[0]++;
                } else if (plan.getStatus().equals(-1)) {
                    ints[1]++;
                }
            }
        }
        return ints;
    }

    /**
     * 根据UserInfo和respData创建User对象
     *
     * @param userInfo
     * @return 一个User对象
     */
    private User getUser(User u, JSONObject respData, JSONObject userInfo) {
        u.setName(userInfo.getString("nickName"));
        u.setGender(userInfo.getInteger("gender"));
        u.setSkey(UUID.randomUUID().toString());
        u.setAddress(userInfo.getString("country") + "-" + userInfo.getString("province") + "-" + userInfo.getString("city"));
        u.setAvatar(userInfo.getString("avatarUrl"));
        u.setOpenId(respData.getString("openid"));
        u.setSessionKey(respData.getString("session_key"));
        u.setUpdateTime(new Date());
        return u;
    }

    /**
     * 发送GET请求，换取session_key
     *
     * @param code
     * @return
     */
    private JSONObject getRespData(String code) {
        Map<String, Object> requestDataMap = new HashMap<>();
        requestDataMap.put("appid", "wxeedd8807e8f9532b");
        requestDataMap.put("secret", "61ec2ed6e73c16e78397c04f8a46f317");
        requestDataMap.put("js_code", code);
        requestDataMap.put("grant_type", "authorization_code");
        //https://api.weixin.qq.com/sns/jscode2session?appid=wxeedd8807e8f9532b&secret=61ec2ed6e73c16e78397c04f8a46f317&js_code=001yI9U30VIsUK14vLW30ar1U30yI9U7&grant_type=authorization_code
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("https://api.weixin.qq.com/sns/jscode2session?" +
                "appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}", String.class, requestDataMap);
        return JSONObject.parseObject(forEntity.getBody());
    }
}
