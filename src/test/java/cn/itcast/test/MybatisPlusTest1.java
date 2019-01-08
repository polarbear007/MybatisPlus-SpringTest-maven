package cn.itcast.test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.entity.User;
import cn.itcast.mapper.UserMapper;

// 测试一下最基本的增删改查

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisPlusTest1 {
	@Autowired
	private UserMapper userMapper;
	
	// 添加一条数据，方便后面进行测试
	@Test
	public void test() {
		User user = new User();
		user.setUsername("张三");
		user.setBirthday(new Date());
		user.setAddress("福建漳州 ");
		user.setGender('男');
		userMapper.insert(user);
	}
	
	// 获取自增主键值
	// 【注意】 原生的mybatis 如果想要在插入数据的时候获取自增的主键值，需要添加 useGeneratedKeys 和 keyProperty 属性
	//        而使用 mybatisPlus 默认就会帮我们获取主键值
	@Test
	public void test2() {
		User user = new User();
		user.setUsername("狗蛋");
		user.setBirthday(new Date());
		user.setAddress("福建漳州 ");
		user.setGender('男');
		userMapper.insert(user);
		System.out.println("刚才插入的记录的主键值：" + user.getUid());
	}
	
	// 通过 id 进行查询
	@Test
	public void test3() {
		User user = userMapper.selectById(1);
		System.out.println(user);
	}
	
	// 通过一个 id 集合进行批量查询
	// SELECT uid,username,birthday,gender,address FROM t_user WHERE uid IN ( ? , ? , ? , ? )
	@Test
	public void test4() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4);
		List<User> userList = userMapper.selectBatchIds(list);
		if(list != null && list.size() > 0) {
			for (User user : userList) {
				System.out.println(user);
			}
		}
	}
	
	// 通过一个 map 集合来添加查询条件
	// SELECT uid,username,birthday,gender,address FROM t_user WHERE gender = ? AND username = ? 
	@Test
	public void test5() {
		HashMap<String, Object> columnMap = new HashMap<>();
		// 【注意】 这里的键应该使用 数据库里面的列名，而不要使用实体类的属性名
		columnMap.put("username", "李四");
		columnMap.put("gender", '男');
		
		List<User> list = userMapper.selectByMap(columnMap);
		
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	// 通过 id 进行更新
	// 【注意】 会自动过滤 null 值，只更新不为 null 的字段
	@Test
	public void test6() {
		User user = new User();
		user.setUid(1);
		user.setUsername("李四");
		userMapper.updateById(user);
	}
	
	// 通过 id 删除数据
	@Test
	public void test7() {
		userMapper.deleteById(4);
	}
	
	// 根据 id 批量删除数据
	// DELETE FROM t_user WHERE uid IN ( ? , ? , ? ) 
	@Test
	public void test8() {
		List<Integer> idList = Arrays.asList(2, 3, 4);
		userMapper.deleteBatchIds(idList);
	}
	
	// 使用 map 集合来添加 删除条件
	// DELETE FROM t_user WHERE gender = ? AND username = ? 
	@Test
	public void test9() {
		HashMap<String, Object> columnMap = new HashMap<>();
		// 【注意】 这里的键应该使用 数据库里面的列名，而不要使用实体类的属性名
		columnMap.put("username", "李四");
		columnMap.put("gender", '男');
		
		userMapper.deleteByMap(columnMap);
	}
}
