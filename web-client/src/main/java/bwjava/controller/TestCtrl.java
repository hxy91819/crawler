package bwjava.controller;


import bwjava.entity.UserInfo;
import bwjava.service.RedisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by xiaour on 2017/4/19.
 */
@RestController
@RequestMapping(value = "/test")
public class TestCtrl {

    @Resource
    private RedisService redisService;

    @RequestMapping(value = "/index")
    public String index() {
        return "hello world";
    }

    /**
     * 向redis存储值
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/set")
    public String set(String key, String value) {

        redisService.set(key, value);
        return "success";
    }

    /**
     * 获取redis中的值
     * @param key
     * @return
     */
    @RequestMapping("/get")
    public String get(String key) {
        try {
            return redisService.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取数据库中的用户
     * @param id
     * @return
     */
    @GetMapping("/getUser/{id}")
    public UserInfo get(@PathVariable("id") int id) {
//        try {
//            return userInfoService.selectByPrimaryKey(id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("id", "编号");
        keyMap.put("name", "名称");

        String[] cnCloumn = {"编号", "名称"};

        System.out.println(Arrays.asList(convertMap(keyMap, cnCloumn)));

    }

    public static String[] convertMap(Map<String, Object> keyMap, String[] dataList) {

        for (int i = 0; i < dataList.length; i++) {

            for (Map.Entry<String, Object> m : keyMap.entrySet()) {
                if (m.getValue().equals(dataList[i])) {
                    dataList[i] = m.getKey();
                }
            }
        }

        return dataList;
    }


    public static String getName(String name, String add) {
        return null;
    }

    public static void testGetClassName() {
        // 方法1：通过SecurityManager的保护方法getClassContext()
        String clazzName = new SecurityManager() {
            public String getClassName() {
                return getClassContext()[1].getName();
            }
        }.getClassName();
        System.out.println(clazzName);
        // 方法2：通过Throwable的方法getStackTrace()
        String clazzName2 = new Throwable().getStackTrace()[1].getClassName();
        System.out.println(clazzName2);
        // 方法3：通过分析匿名类名称()
        String clazzName3 = new Object() {
            public String getClassName() {
                String clazzName = this.getClass().getName();
                return clazzName.substring(0, clazzName.lastIndexOf('$'));
            }
        }.getClassName();
        System.out.println(clazzName3);
        //方法4：通过Thread的方法getStackTrace()
        String clazzName4 = Thread.currentThread().getStackTrace()[2].getClassName();
        System.out.println(clazzName4);
    }


}
