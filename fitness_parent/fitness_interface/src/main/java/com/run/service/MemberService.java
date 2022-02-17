package com.run.service;

import com.run.domain.Member;

import java.util.List;
import java.util.Map;


public interface MemberService {
    List<Integer> findMenberData(List<String> month);
    List<Map<String,Object>> findSetmeal();
}
