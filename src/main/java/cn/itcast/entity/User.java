package cn.itcast.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

@TableName(value="t_user")
public class User extends Model<User> implements Serializable {
	private static final long serialVersionUID = 8162488390812814831L;
	
	@TableId(value="uid", type=IdType.AUTO)
	private Integer uid;
	
	@TableField(value="username")
	private String username;
	private Date birthday;
	private Character gender;
	private String address;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", birthday=" + birthday + ", gender=" + gender
				+ ", address=" + address + "]";
	}

	@Override
	protected Serializable pkVal() {
		return uid;
	}

}
