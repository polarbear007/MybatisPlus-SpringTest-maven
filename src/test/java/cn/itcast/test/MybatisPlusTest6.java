package cn.itcast.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.itcast.entity.Student;
import cn.itcast.service.impl.StudentServiceImpl;

                
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisPlusTest6 {
	@Autowired
	private StudentServiceImpl studentServiceImpl;
	
	// 使用自动生成的 StudentServiceImpl 进行数据库操作
	@Test
	public void test() {
		IPage<Student> page = studentServiceImpl.page(new Page<Student>(1, 10), null);
		List<Student> list = page.getRecords();
		System.out.println(list.size());
	}
	
	@Test
	public void test2() {
		List<Student> list = studentServiceImpl.list(new QueryWrapper<Student>().between("sid", 10, 20));
		if(list != null && list.size() > 0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
}
