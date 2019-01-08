package cn.itcast.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.itcast.entity.User;
import cn.itcast.mapper.UserMapper;

// 测试一下使用 条件构造器进行查询
//  其实就之前我们使用的 QBC 查询

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisPlusTest2 {
	@Autowired
	private UserMapper userMapper;
	
	// 传入一个 entity 对象来构造查询条件， 全部等值
	//  【注意】 是的于 QBE查询
	// SELECT uid,username,birthday,gender,address FROM t_user WHERE uid=? AND username=? AND gender=? 
	@Test
	public void test() {
		User user = new User();
		user.setUid(5);
		user.setUsername("狗蛋");
		user.setGender('男');
		
		QueryWrapper<User> wrapper = new QueryWrapper<User>(user);
		List<User> list = userMapper.selectList(wrapper);
		if(list != null &&  list.size() > 0) {
			for (User user2 : list) {
				System.out.println(user2);
			}
		}
	}
	
	// 使用 Wrapper 来构造查询条件1
	// 查询 username = "狗蛋"  and  gender = ‘男’ 的记录
	// 【注意】如果有多个条件， Wrapper 接口默认使用的是 and 进行连接
	// SELECT uid,username,birthday,gender,address FROM t_user WHERE username = ? AND gender = ?
	@Test
	public void test2() {
		// 【注意】 eq 里面的第一个参数必须写的是数据库的列名
		List<User> list = userMapper.selectList(new QueryWrapper<User>()
								.eq("username", "狗蛋")
								.eq("gender", '男'));
		
		if(list != null &&  list.size() > 0) {
			for (User user2 : list) {
				System.out.println(user2);
			}
		}
	}
	
	// 使用 Wrapper 来构造查询条件2
	// 查询用户名中带有a 的并且出生日期不早于1990-1-1 的记录
	// SELECT uid,username,birthday,gender,address FROM t_user WHERE username LIKE ? AND birthday <= ? 
	// 【注意】 like()  		  %xxx%
	//        likeRight()     xxxx%
	//        likeLeft()      %xxxx
	@Test
	public void test3() throws ParseException {
		List<User> list = userMapper.selectList(new QueryWrapper<User>()
								.likeRight("username", "a")
								.le("birthday", new SimpleDateFormat("yyyy-MM-dd").parse("1990-1-1"))
							);
		if(list != null &&  list.size() > 0) {
			for (User user2 : list) {
				System.out.println(user2);
			}
		}
	}
	
	// 使用 Wrapper 来构造查询条件3
	// 查询 uid 小于10  并且  gender 列的值为 null 的记录
	// SELECT uid,username,birthday,gender,address FROM t_user WHERE uid < ? AND gender IS NULL 
	@Test
	public void test4() {
		List<User> list = userMapper.selectList(new QueryWrapper<User>()
								.lt("uid", 10)
								.isNull("gender")
							);
		if(list != null &&  list.size() > 0) {
			for (User user2 : list) {
				System.out.println(user2);
			}
		}
	}
	
	// 使用 Wrapper 来构造查询条件4
	// 查询 uid 在 10 到 100 之间， gender 为 ‘女’ 的记录
	// SELECT uid,username,birthday,gender,address FROM t_user WHERE uid BETWEEN ? AND ? AND gender = ? 
	@Test
	public void test5() {
		List<User> list = userMapper.selectList(new QueryWrapper<User>()
								.between("uid", 10, 100)
								.eq("gender", '女')
							);
		if(list != null &&  list.size() > 0) {
			for (User user2 : list) {
				System.out.println(user2);
			}
		}
	}
	
	// 使用 Wrapper 来构造查询条件5
	// 查询一个 uid = 4 或者  gender = '女'   的记录
	// 【注意】 如果多个条件中间是用 and 连接，那么不需要特意写 .and() 方法，但是如果是 or 的话，一定要写 .or() 方法
	// SELECT uid,username,birthday,gender,address FROM t_user WHERE uid = ? OR gender = ? 
	@Test
	public void test6() {
		List<User> list = userMapper.selectList(new QueryWrapper<User>()
								.eq("uid", 4)
								.or()
								.eq("gender", '女')
							);
		if(list != null &&  list.size() > 0) {
			for (User user2 : list) {
				System.out.println(user2);
			}
		}
	}
	
	// 使用 Wrapper 来构造查询条件6
	// 拼接一个既带and 又带 or 的查询条件      (uid = 4 and gender = '女' )  or username like "%a%"
	// 【注意】 如果查询条件中既带有 and ，又带有 or ， 那么一定要使用 括号分隔清楚，不然这条sql 语句就非常难以读懂
	//          但是这个 nested()  方法又需要lambda  表达式，我们又不会！！！
	//          所以这个功能还是尽量少用吧，如果遇到这种需求的时候，我们尽量使用其他的方式来实现
	@Test
	public void test7() {
		List<User> list = userMapper.selectList(new QueryWrapper<User>()
								.nested(i -> i.eq("uid", 4).eq("gender", '女'))
								.or()
								.like("username", "a")
							);
		if(list != null &&  list.size() > 0) {
			for (User user2 : list) {
				System.out.println(user2);
			}
		}
	}
}
