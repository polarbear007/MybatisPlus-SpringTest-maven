package cn.itcast.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.itcast.entity.User;
import cn.itcast.mapper.UserMapper;

// 分页查询
// 【注意】 selectPage()   方法的返回值是 Ipage<T>   接口类型，但是一般我们都是直接强转成 Page<T> 类型
//       这个 Page<T>  对象既可以作为携带分页条件的参数， 也可以把分页查询的返回值
//                   
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisPlusTest4 {
	@Autowired
	private UserMapper userMapper;
	
//	【注意】 如果我们没有使用分页插件的话，默认还是使用 mybatis 的内存分页，先查询所有数据到内存中再根据		
//			分页条件，在内存中挑出我们需要的数据。 
//	【注意】 内存分页是不会先执行 count 查询的，所以如果没有使用分页插件的话，那么getTotal() 返回值默认为0
//	         =======>   后面我们会讲怎么使用 mybatisPlus 自带的分页插件的。
	@Test
	public void test() {
		Page<User> page = (Page<User>) userMapper.selectPage(new Page<User>(1, 5), 
												 new QueryWrapper<User>()
												 	.like("username", "as")
												);
		// 遍历查询的结果
		List<User> list = page.getRecords();
		for (User user : list) {
			System.out.println(user);
		}
		
		System.out.println("=========分页查询的常用参数=========");
		System.out.println("获取当前页数：" + page.getCurrent());
		System.out.println("获取每页展示数量：" + page.getSize());
		System.out.println("获取总的记录数：" + page.getTotal());
		System.out.println("获取总页数：" + page.getPages());
		System.out.println("是否有下一页：" + page.hasNext());
		System.out.println("是否是上一页：" + page.hasPrevious());
	}
}
