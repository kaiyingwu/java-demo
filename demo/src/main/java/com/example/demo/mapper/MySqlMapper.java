package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dao.StudentAndTeacherDo;
import com.example.demo.dao.StudentDo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface MySqlMapper {

    /**
     * 添加一行学生数据
     */
    @Insert("insert into student (student_name,student_password) value " +
            "(#{name},#{password})")
    Integer integer(@Param("name") String name, @Param("password")String password);


    /**
     * 添加一行学生数据并返回自增主键
     * @Options(useGeneratedKeys = true, keyProperty = "id")
     */
    @Insert("insert into student (student_name,student_password) value " +
            "(#{studentName},#{studentPassword})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addStudentId(StudentDo studentDo);



    /**
     * 添加一行学生数据并返回非自增主键
     * @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType = Long.class, before = false)
     */
    @Insert("insert into student (student_name,student_password) value " +
            "(#{studentName},#{studentPassword})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType = Long.class, before = false)
    void addStudentIdNoUp(StudentDo studentDo);


    /**
     * 删除一行学生数据
     */
    @Delete("delete from student where studentName = #{name}")
    Integer delStudent(String name);


    /**
     * 修改一个学生数据
     */
    @Update("update student set state = #{state} where name = #{name}")
    Integer updateStudent(@Param("state") int state , @Param("name") String name);


    /**
     * 查询所有学生数据
     */
    @Select("select id,student_name,student_password,state from student")
    @Results(value = {@Result(column = "student_name",property = "studentName"),
                      @Result(column = "student_password",property = "studentPassword")})
    List<StudentDo> selectStudent();


    /**
     * 根据条件查找学生数据
     */
    @Select("select * from student where id = #{id}")
    List<StudentDo> selectStudentById(int studentId);


    /**
     * 登录（判断是否能查到指定学生数据）
     */
    @Select("select * from student where student_name = #{name} and student_password = #{password}")
    StudentDo studentLogin(String name, String password);


    /**
     * 多表查询  jdbcType 如果传入是空值需要给定数据类型
     * 若没有驼峰规则，需要as 方式 转义
     * 比如 数据库id字段名为id
     * 实体类命名为studentId
     * 没有驼峰匹配
     * 则需要把s.id转义成 student_id
     * 然后映射处 id->student_id 最终实现如下
     * @Result(property = "studentId" ,column = "student_id")
     * 避免获取的id为0的情况  应该所有int类型都可以这么解决
     */
    @Results(value = {@Result(property = "studentName" ,column = "student_name"),
            @Result(property = "teacherName" ,column = "teacher_name") ,
            @Result(property = "studentId" ,column = "student_id"),
            @Result(property = "studentState",column = "student_state")})
    @Select("select s.id as student_id,s.student_name,t.teacher_name,s.state as student_state from student s left join teacher t on s.id = t.id ${ew.customSqlSegment}")
    List<StudentAndTeacherDo> selectStudentAndTeacher(@Param(Constants.WRAPPER)Wrapper<StudentAndTeacherDo> wrapper);


    /**
     * 多表分页查询
     */
    @Results(value = {@Result(property = "studentName" ,column = "student_name"),
            @Result(property = "teacherName" ,column = "teacher_name") ,
            @Result(property = "studentId" ,column = "student_id"),
            @Result(property = "studentState",column = "student_state")})
    @Select("select s.id as student_id,s.student_name,t.teacher_name,s.state as student_state from student s left join teacher t on s.id = t.id ${ew.customSqlSegment}")
    IPage<StudentAndTeacherDo> selectStudentAndTeacherPage(Page<StudentAndTeacherDo> page,@Param(Constants.WRAPPER)Wrapper<StudentAndTeacherDo> wrapper);

    /**
     * post 查询老师数据
     * 实现全局异常处理
     */
    @Select("select teacher_name from teacher where id = #{id}")
    String selectTeacher(int id);



}
