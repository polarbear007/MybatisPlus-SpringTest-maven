package cn.itcast.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.itcast.entity.User;
import cn.itcast.mapper.UserMapper;

// 使用 QueryWrapper 进行一些比较复杂的查询

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisPlusTest3 {
	@Autowired
	private UserMapper userMapper;
	
	// 设置查询列表方法1：
	//  public QueryWrapper(T entity, String... column)
	//  第一个参数 entity 相当于 example , 如果这个对象有值的话，那么全部是等值条件
	//  	如果我们不想使用等值条件的话，那么我们可以new 一个空的对象进去，然后对整体的 QueryWrapper 对象添加非等值条件
	//  第二个参数是一个可变参数，就是我们想要的查询列表，这个列表必须对应数据库的字段名
	
	// SELECT uid,username FROM t_user 
	@Test
	public void test() {
		List<User> list = userMapper.selectList(new QueryWrapper<User>(new User() , "uid", "username"));
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	
	// SELECT uid,username FROM t_user WHERE uid < ? 
	@Test
	public void test2() {
		List<User> list = userMapper.selectList(new QueryWrapper<User>(new User() , "uid", "username")
																.lt("uid", 10)
												);
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	
	//  设置查询列表方法2： 【推荐使用】
	//  public QueryWrapper<T> select(String... sqlSelect) 
	
	// SELECT uid,username,gender FROM t_user WHERE address = ? 
	@Test
	public void test3() {
		List<User> list = userMapper.selectList(new QueryWrapper<User>()
								.select("uid", "username", "gender")
								.eq("address", "福建漳州")
							);
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	// 一般情况下，不管你怎么设置查询列表，返回的都是 实体类对象或者是实体类对象的集合
	// 如果我们的查询列表查到的数据无法封装到 实体类对象中的话，那么我们必须使用
	//   selectMaps() ，  这样子查询出来的结果就可以自动封装到一个 Map 集合中，一个字段对应一个键值对
	//   【注意】 这个方法返回值是一个 list 集合，元素类型是 map 集合。 因为如果是分组查询，聚合查询也是会返回多行的。
	
	// SELECT count(*) count,avg(uid) avg FROM t_user 
	
	// 查询结果：  [{avg=6.0000, count=3}]
	@Test
	public void test4() {
		List<Map<String, Object>> list = userMapper.selectMaps(new QueryWrapper<User>()
							   					.select("count(*) count", "avg(uid) avg")
											);
		 System.out.println(list);
	}
	
	// 设置排序字段
	//  QueryWrapper<T> orderByDesc(String... columns) 
	//   QueryWrapper<T> orderByAsc(String... columns) 
	// 【注意】 虽然可以接收可变参数，但是好像接收多个参数会有点问题。 
	//        所以如果需要使用多个字段进行排序，而且排序方向不一样，还是尽量多次调用方法，每个方法只带一个参数
	// SELECT uid,username,birthday,gender,address FROM t_user WHERE 1=1 ORDER BY username,uid DESC 
	@Test
	public void test5() {
		List<User> list = userMapper.selectList(new QueryWrapper<User>()
												.orderByDesc("username" , "uid")
												);
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	//SELECT uid,username,birthday,gender,address FROM t_user WHERE 1=1 ORDER BY username DESC , uid ASC 
	@Test
	public void test6() {
		List<User> list = userMapper.selectList(new QueryWrapper<User>()
												.orderByDesc("username" )
												.orderByAsc("uid")
												);
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	
	// 设置分组字段 1
	// QueryWrapper<T> groupBy(String... columns) 
	
	// SELECT uid,username,birthday,gender,address FROM t_user WHERE 1=1 GROUP BY username,uid 
	@Test
	public void test7() {
		List<User> list = userMapper.selectList(new QueryWrapper<User>()
												.groupBy("username", "uid")
												);
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	// 设置分组字段2 
	// 分组查询的时候可能会进行聚合查询，这个时候我们就需要配合使用  selectMaps() 方法进行查询
	
	// SELECT count(*) count,avg(uid) avg FROM t_user WHERE 1=1 GROUP BY username,uid 
	@Test
	public void test8() {
		List<Map<String, Object>> list = userMapper.selectMaps(new QueryWrapper<User>()
												.select("count(*) count", "avg(uid) avg")
												.groupBy("username", "uid")
												);
		if(list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
		}
	}
	
	// 设置分组字段3
	// 分组查询后，可能还需要使用分组后的查询条件，这个时候我们可以配合使用  having() 添加筛选条件
	// QueryWrapper<T> having(String sqlHaving, Object... params)
	// 例1: having("sum(age) > 10")     【直接用这个，别传什么参数了】
    // 例2: having("sum(age) > {0}", 10) 
	
	// SELECT count(*) count,avg(uid) avg 
	// FROM t_user WHERE 1=1 
	// GROUP BY username,uid 
	// HAVING count > 10 
	@Test
	public void test9() {
		List<Map<String, Object>> list = userMapper.selectMaps(new QueryWrapper<User>()
												.select("count(*) count", "avg(uid) avg")
												.groupBy("username", "uid")
												.having("count > 10")
												);
		if(list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
		}
	}
}
