package com.KoreaIT.cwy.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {
	
//	@Insert()
	void join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

}
