package com.example.demo.controller;

import com.example.demo.bean.BaseResponse;
import com.example.demo.kafka.KafkaService;
import com.example.demo.util.MySqlUtil;
import com.example.demo.impl.MySqlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/demo")
public  class DemoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    /**
     * 该注解 相当于声明一个对象，并赋予配置，不需要再进行new MySqlUtil
     */
    @Autowired
    private MySqlUtil mySqlUtil;


    @Autowired
    private KafkaService kafkaService;

    @Value("${kafka.platform.user.data.topic}")
    private String topic;

    /**
     * hello wold
     * 并携带发送一条kafka数据
     * @return
     */
    @GetMapping("/hello")
    public String put(){
        String message = "Hello world";
        kafkaService.sendMessage(topic,message);
        return message;
    }

    /**
     * readMsg
     * 获取发送的kafka数据
     */
    @GetMapping("/readMsg")
    public BaseResponse readMsg(){
        kafkaService.readMessage();
        return BaseResponse.createOK("成功");
    }



    /**
     * 常用CURD操作大致使用以下三个方法:
     * JdbcTemplate jdbcTemplate
     * 以下为jdbcTemplate 的三个方法
     * 1.execute方法，用于直接执行SQL语句
     * 2.update方法，用户新增修改删除操作
     * 3.query方法，用于查询方法
     * -----------------------------------------------------------
     * method = RequestMethod.GET
     * method = RequestMethod.POST
     * 两种数据传递方式get和post
     * --------------------------------------------------------------
     * @RequestParam(value = "userName",required = true)String name
     * 方法参数内 若设置了value，则传递过程所用的key必须为新命名的，若没有则使用与传入值一样的key
     * 例如 上面的访问方式就是/url?userName=1111
     * 若没有@RequestParam的设置 则 /url?name=1111
     *-------------------------------------------------------------------------------
     * @RequestParam(required = true)
     * required = true 代表该字段值不能不传必须存在没有null值数据，不能抓取并返回错误
     * required = false 代表该字段不传情况下能拿到为null的数据，可以进行错误返回
     * 获取student所有数据
     * @return
     */
    @RequestMapping("/getUsers")
    public List<Map<String, Object>> getDbType(){
        String sql = "select * from student";
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);

        for (Map<String, Object> map : list) {

            Set<Map.Entry<String, Object>> entries = map.entrySet( );

            if(entries != null) {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator( );
                while(iterator.hasNext( )) {
                    Map.Entry<String, Object> entry =(Map.Entry<String, Object>) iterator.next( );
                    Object key = entry.getKey( );
                    Object value = entry.getValue();
                    System.out.println(key+":"+value);
                }
            }
        }

        return list;
    }


    /**
     * 查询单条数据id = 1
     * @return
     */
    @RequestMapping("/myGetUsers")
    public List<Map<String,Object>> getMyDbType(int id){
        String sql = "select * from student where  id = ?";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,id);

        //for 循环遍历获取到的数据
        for (int i=0;i<list.size();i++){

            //获取某条数据
            Map<String,Object> map = list.get(i);

            //获取Map值
            Set<Map.Entry<String,Object>> mapSet = map.entrySet();

            //获取Map内部key-value值
            Iterator<Map.Entry<String,Object>> iterator = mapSet.iterator();

            //判断是否为空
            if (iterator!=null){

                //while循环
                while (iterator.hasNext()){

                    //通过getKey() 和 getValue()获取值
                    Map.Entry<String,Object> mapIterator = iterator.next();
                    Object key = mapIterator.getKey();
                    Object value = mapIterator.getValue();
                    System.out.println(key+":"+value);
                }
            }
        }

        return list;
    }


    /***
     * 增加数据
     */
    @RequestMapping(value = "/addStudent",method = RequestMethod.GET)
    public String addStudent(@RequestParam(value = "name",required = true)int name,
                             @RequestParam(value = "password",required = true)int pwd){

        if (mySqlUtil.isHasUser(name)){
            return "用户名已经存在";
        }

        String sql = "INSERT INTO student(name,password) VALUES (?,?) ";
        int num = jdbcTemplate.update(sql,name,pwd);
        if (num >0){
            return "增加成功";
        }else {
            return "增加失败";
        }

    }


    /**
     * 删除数据
     * @return
     */
    @RequestMapping(value = "/deleteStudent",method = RequestMethod.GET)
    public String deleteStudent(@RequestParam(value = "userName",required = true)int name){
        String sql = "delete from student where name = ?";
        int num = jdbcTemplate.update(sql,name);
        if (num>0){
            return "删除成功";
        }else {
            return "删除失败";
        }
    }

    /**
     * 修改数据
     */
    @RequestMapping("/updateStudent")
    public String updateStudent(int oldName,int newName,int pwd){
        if (!mySqlUtil.isHasUser(oldName)){
            return "用户名不存在";
        }

        String sql = "update student set name = ?,password = ? where name = ?";
        int num = jdbcTemplate.update(sql,newName,pwd,oldName);
        if (num>0){
            return "修改成功";
        }else {
            return "修改失败";
        }
    }
}
