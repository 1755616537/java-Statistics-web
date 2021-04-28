package cn.miteng.service.http;

import cn.miteng.entity.Summary;
import cn.miteng.entity.SummaryRepository;
import cn.miteng.entity.User;
import cn.miteng.entity.UserRepository;
import cn.miteng.util.Public;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Component
public class run {
    @Autowired
    private SummaryRepository summaryRepository;
    @Autowired
    private UserRepository userRepository;

    public static run run;
    @PostConstruct
    public void init() {
        run = this;
        run.summaryRepository = this.summaryRepository;
        run.userRepository = this.userRepository;
    }

    //    public JSONObject upload(MultipartFile file,JSONObject requestBody, HttpSession session, HttpServletResponse response, HttpServletRequest request){
//        if (file.isEmpty())return new Public().Return("1","上传失败，请选择文件");
//        if (file.getSize()>=20000)return new Public().Return("1","上传失败，文件过大");
//        String fileName = file.getOriginalFilename();
//        String filePath = "/Users/itinypocket/workspace/temp/";
//        File dest = new File(filePath + fileName);
//        try {
//            file.transferTo(dest);
//            return new Public().Return("0","成功");
//        } catch (IOException e) {
//            System.out.println(e.toString());
//        }
//
//        return new Public().Return("0","成功");
//    }
    public JSONObject login(JSONObject requestBody, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        User user;
        String userid=requestBody.getString("userid");
        Integer useridInt = null;
        try {
            useridInt=Integer.valueOf(userid);
            Optional<User> dataList = run.userRepository.findById(useridInt);
            if (!dataList.isPresent()) {
                List<User> userlist = run.userRepository.findByName(userid);
                if (userlist.size()==0) {
                    return new Public().Return("1", "用户名称错误");
                }
                user = userlist.get(0);
//            return new Public().Return("1", "用户id错误");
            }else {
                user = dataList.get();
            }
        }catch (Exception e){
            List<User> userlist = run.userRepository.findByName(userid);
            if (userlist.size()==0) {
                return new Public().Return("1", "用户名称错误");
            }
            user = userlist.get(0);
        }
        if (!user.getPassword().equals(requestBody.getString("password"))) {
            return new Public().Return("1", "密码错误");
        }

        JSONObject data =new JSONObject();
        data.put("userid", user.getId());
        data.put("type", user.getType());
        return new Public().Return("0", "成功",data);
    }

    public JSONObject register(JSONObject requestBody, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        User user = new User();
        user.setType(requestBody.getIntValue("type"));
        user.setName(requestBody.getString("name"));
        user.setPassword(requestBody.getString("password"));
        user.setSex(requestBody.getString("sex"));
        user.setStudentID(requestBody.getString("StudentID"));
        user.setMajor(requestBody.getString("major"));
        user.set_Class(requestBody.getString("_class"));

        if (run.userRepository.findByName(user.getName()).size()!=0){
            return new Public().Return("1", "用户名称已被占用");
        }

        try {
            run.userRepository.save(user);
        } catch (Exception e) {
            return new Public().Return("1", "参数错误(或内部错误)");
        }

        JSONObject data =new JSONObject();
        data.put("userid", user.getId());
        data.put("type", user.getType());
        return new Public().Return("0", "成功",data);
    }

    public JSONObject get(JSONObject requestBody, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        List<Summary> dataList=dataList = run.summaryRepository.findAll();
        if (dataList.size() == 0)return new Public().Return("0", "数据为空");
        Map<Integer, Optional<User>> userlist = new HashMap<>();

        JSONArray data = new JSONArray();
        for (int i = 0; i < dataList.size(); i++) {
            Optional<User> user = userlist.get(dataList.get(i).getUserid());
            if (null==user) {
                user = run.userRepository.findById(dataList.get(i).getUserid());
                if (!user.isPresent()) {
                    continue;
                }
                userlist.put(dataList.get(i).getUserid(), user);
            }
            JSONObject val = new JSONObject();
            val.put("ID", dataList.get(i).getId());
            val.put("姓名", user.get().getName());
            val.put("性别", user.get().getSex());
            val.put("学号", user.get().getStudentID());
            val.put("专业", user.get().getMajor());
            val.put("班级", user.get().get_Class());
            val.put("科目", dataList.get(i).getCourse());
            val.put("分数", dataList.get(i).getBranch());
            val.put("时间", dataList.get(i).getTime());
            val.put("评语", dataList.get(i).getComment());
            data.add(val);
        }

        return new Public().Return("0", "成功", data);
    }

    public JSONObject set(JSONObject requestBody, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        JSONArray data=requestBody.getJSONArray("data");
        Integer userid=requestBody.getInteger("userid");
        Optional<User> user;
        try {
            user=run.userRepository.findById(userid);
            if (null==user.get()){
                return new Public().Return("1", "用户错误");
            }
//            if (user.get().getType()!=2)return new Public().Return("1", "权限不足");
        }catch (Exception e){
            return new Public().Return("1", "内部错误");
        }

        JSONArray ccdata=new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            JSONObject json =data.getJSONObject(i);
            if (null!=json.getInteger("ID") && json.getInteger("ID")!=0){
                if (user.get().getType()!=2)continue;
            }
            ccdata.add(json);
        }

        List<Summary> summaryList =new ArrayList<>();
        for (int i = 0; i < ccdata.size(); i++) {
            JSONObject json=ccdata.getJSONObject(i);

            Summary summary=new Summary();
            summary.setTime(new Date());
            if (user.get().getType()==2){
                List<User> user_ =run.userRepository.findByName(json.getString("姓名"));
                try {
                    if (user_.size()==0){
                        User _user=new User();
                        _user.setType(1);
                        _user.setName(json.getString("姓名"));
//                    _user.setPassword("0");
                        _user.setSex(json.getString("性别"));
                        _user.setStudentID(json.getString("学号"));
                        _user.setMajor(json.getString("专业"));
                        _user.set_Class(json.getString("班级"));
                        try {
                            run.userRepository.save(_user);
                            summary.setUserid(_user.getId());
                        }catch (Exception e){
                            summary.setUserid(1);
                        }
                    }else {
                        summary.setUserid(user_.get(0).getId());
                    }
                }catch (Exception e){
                    continue;
                }
                summary.setComment(json.getString("评语"));
            }else {
                summary.setUserid(user.get().getId());
            }
            summary.setBranch(json.getInteger("分数"));
            summary.setCourse(json.getString("科目"));
            summaryList.add(summary);
        }
        System.out.println(summaryList);
        try {
            run.summaryRepository.saveAll(summaryList);
        }catch (Exception e){
            return new Public().Return("1", "参数错误(或内部错误)");
        }

        return new Public().Return("0", "成功");
    }
}
