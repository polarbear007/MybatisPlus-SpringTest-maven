package cn.itcast.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.itcast.entity.User;

                
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisPlusTest5 {
	
	// 插入操作
	// INSERT INTO t_user ( username, birthday, gender, address ) VALUES ( ?, ?, ?, ? ) 
	@Test
	public void test() {
		User user = new User();
		user.setUsername("小王");
		user.setBirthday(new Date());
		user.setAddress("福建厦门");
		user.setGender('女');
		// 不需要指定 uid
		user.insert();
	}
	
	@Test
	public void test2() {
		User user = new User();
		user.setUid(1);
		user.setUsername("小李");
		user.setBirthday(new Date());
		user.setAddress("福建厦门");
		user.setGender('女');
		// 如果指定了 id 的话，那么我们可以执行 updateById() 或者 insertOrUpdate() 方法
		// 【注意】 如果实体对象有 id, 这个 insertOrUpdate() 方法会先试图根据 id 去更新
		//        如果更新影响了 0 行数据，那么就会再执行一条 insert 语句
		user.insertOrUpdate();
	}
	
	// SELECT uid,username,birthday,gender,address FROM t_user 
	@Test
	public void test3() {
		User user = new User();
		List<User> list = user.selectAll();
		if(list != null && list.size() > 0) {
			for (User user2 : list) {
				System.out.println(user2);
			}
		}
	}
	
	// 根据 id 去更新数据
	@Test
	public void test4() {
		User user = new User();
		user.setUid(1);
		user.setUsername("小李");
		user.setBirthday(new Date());
		user.setAddress("福建厦门");
		user.setGender('女');

		user.updateById();
	}
	
	// 根据 id 删除数据
	// DELETE FROM t_user WHERE uid=? 
	@Test
	public void test5() {
		User user = new User();
		user.setUid(1);
		user.deleteById();
	}
	
	// 添加查询条件
	// 用起来跟使用 BaseMapper 的方法几乎是完全一样的， 这里就不再一一列举了
	// SELECT uid,username,birthday,gender,address FROM t_user WHERE username = ? AND uid > ? 
	@Test
	public void test6() {
		User user = new User();
		List<User> list = user.selectList(new QueryWrapper<User>()
							.eq("username", "小李")
							.gt("uid", 10)
						);
		if(list != null && list.size() > 0) {
			for (User user2 : list) {
				System.out.println(user2);
			}
		}
	}
}
