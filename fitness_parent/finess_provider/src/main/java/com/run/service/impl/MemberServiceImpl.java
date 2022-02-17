package com.run.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.run.dao.MemberDao;
import com.run.domain.Member;
import com.run.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements  MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public List<Integer> findMenberData(List<String> month) {
        List<Integer> list  = new ArrayList<>();
        for (String months : month){
            months = months +".31";
            Integer mon =  memberDao.findMemberData(months);
            list.add(mon);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findSetmeal() {
        return memberDao.findSetmeal();
    }

}
