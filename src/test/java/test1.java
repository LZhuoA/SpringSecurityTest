import com.Application;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dao.UserMapper;
import com.entity.SysUser;
import com.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class test1 {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SysUserService sysUserService;

    @Test
    public void test1(){
        String name = sysUserService.NameTest();
        if(name.equals("NameTest")){
            System.out.println("成功");
        }
        System.out.println(sysUserService.NameTest());
    }

    @Test
    public void test2(){
        String orderId = "1";
        String orderNo = "2";
        List<SysUser> user = sysUserService.list(null);
        System.out.println("user:"+user);
        JSONObject data = new JSONObject();
        data.put("orderId",orderId);
        data.put("orderNo",orderNo);
        data.put("user",user);
        String str = JSON.toJSONString(data);
        System.out.println("json字符串:"+str);

        //string 转 json
        JSONObject json = JSON.parseObject(str);
        String id = json.getString("orderId");
        String no = json.getString("orderNo");
        List<SysUser> u = JSONObject.parseObject(json.getString("user"),new TypeReference<List<SysUser>>() {});
        System.out.println("转换的user:"+u);
        System.out.println(id);
        System.out.println(no);
    }

    @Test
    public void test3(){
        int a = 1;
        int b = 1;
        a++;
        ++b;
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void test5(){
        int a = 3;
        if(a==3){
            System.out.println("成功");
        }
    }

    @Test
    public void test6(){
        String a = null;
        JSONObject data = new JSONObject();
        data.put("test",a);
        data.put("str","");
        String str1 = JSON.toJSONString(data);

        //string 转 json
        JSONObject json = JSON.parseObject(str1);
        String str2 = json.getString("str");
        String str3 = json.getString("test");
        System.out.println(str2);
        System.out.println(str3);
        if(str2.equals("")){
            System.out.println("成功");
        }
    }

    @Test
    public void test7(){
//        String a = null;
        String b = null;
        if(b==null||b.isEmpty()){
            System.out.println("b成功");
        }
    }

    @Test
    public void test8(){
        String orderId = "1";
        String orderNo = "2";
        SysUser user = sysUserService.getByUserName("111");
        System.out.println("user:"+user);
        JSONObject data = new JSONObject();
        data.put("orderId",orderId);
        data.put("orderNo",orderNo);
        data.put("user",user);
        String str = JSON.toJSONString(data);
        System.out.println("json字符串:"+str);

        //string 转 json
        JSONObject json = JSON.parseObject(str);
        String id = json.getString("orderId");
        String no = json.getString("orderNo");
        SysUser u = JSONObject.parseObject(json.getString("user"),SysUser.class);
        System.out.println("转换的user:"+u);
        System.out.println(id);
        System.out.println(no);
    }

    @Test
    public void test9(){
        Integer a = 10;
        Integer b = 10;
        if(a<=b){
            System.out.println("成功");
        }
    }


    @Test
    public void test11(){
        if(true){
            System.out.println("进入true");
        }else{
            System.out.println("进入false");
        }
        System.out.println("没进入");
    }

    @Test
    public void test12(){
        int reg = 1;
        JSONObject json = new JSONObject();
        json.put("reg",reg);
        int a = (int)json.get("reg");
        System.out.println(a);
    }

    @Test
    public void test13(){
        String allorderNos = "abc123";
        String[] orderNoArray = StringUtils.split(allorderNos, ",");
        System.out.println(orderNoArray);
        System.out.println(orderNoArray.length);
    }

    @Test
    public void test14(){
        List<String> str = new LinkedList<>();
        str.add("893915705057000008");
        str.add("893915705296000022");
        str.add("1");
        str.add("2");
        System.out.println("str:"+str);
        String str1 = String.join("\',\'", str);
        String str2 = "(\'" + str1 + "\')";
        System.out.println("str1"+str1);
        System.out.println("str2"+str2);
    }

    @Test
    public void test15(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("111");
        System.out.println(password);
    }

}