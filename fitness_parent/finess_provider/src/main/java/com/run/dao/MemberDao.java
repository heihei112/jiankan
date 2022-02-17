package com.run.dao;

import com.run.domain.Member;
import java.util.List;
import java.util.Map;

public interface MemberDao {
    public Integer findMemberData(String data);

    public List<Map<String,Object>> findSetmeal();
}
