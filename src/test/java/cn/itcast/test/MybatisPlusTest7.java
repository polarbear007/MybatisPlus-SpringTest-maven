package cn.itcast.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.itcast.entity.Student;
import cn.itcast.service.impl.StudentServiceImpl;

// 测试一下mybatisPlus 插件的使用          
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisPlusTest7 {
	@Autowired
	private StudentServiceImpl studentServiceImpl;
	
	// 测试  com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor
	//  不支持 mysql 8.0.12 ， 我的学生表数据全部被删除了
	@Test
	public void test() {
//		删除全表
//		boolean result = studentServiceImpl.remove(null);
		// 更新全表某列的值
		Student stu = new Student();
		stu.setBirthDate(new Date());
		
		boolean result = studentServiceImpl.update(stu, null);
		System.out.println(result);
	}
	
	// 测试 com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor
	@Test
	public void test2() {
		Student stu = new Student();
		stu.setSname("张三");
		stu.setAge(3);
		List<Student> list = studentServiceImpl.list(new QueryWrapper<Student>(stu));
		System.out.println(list.size());
	}
	
	// 测试 com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor
	// UPDATE t_student SET sname=?, version=? WHERE sid=? AND version=? 
	// rose(String), 2(Integer), 3(Integer), 1(Integer)
	@Test
	public void test3() {
		Student stu = new Student();
		stu.setSid(3);
		stu.setSname("rose");
		stu.setVersion(1);
		studentServiceImpl.updateById(stu);
	}
	
	// UPDATE t_student SET sname=?, version=? WHERE sid < ? AND version = ? 
	// rose(String), 2(Integer), 10(Integer), 1(Integer)
	@Test
	public void test4() {
		Student stu = new Student();
		stu.setSname("rose");
		stu.setVersion(1);
		studentServiceImpl.update(stu, new QueryWrapper<Student>().lt("sid", 10));
	}
	
	// PageHelper 分页插件
	@Test
	public void test5() {
		Page<Student> page = PageHelper.startPage(1, 10);
		studentServiceImpl.list(new QueryWrapper<Student>());
		for (Student student : page) {
			System.out.println(page);
		}
	}
	
	// PageHelper 分页插件
	@Test
	public void test6() {
		IPage<Student> page = studentServiceImpl.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Student>(1, 10), null);
		System.out.println(page.getSize());
	}
}
