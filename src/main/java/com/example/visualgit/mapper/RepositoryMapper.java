package com.example.visualgit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.visualgit.entity.Developer;
import com.example.visualgit.entity.Issue;
import com.example.visualgit.entity.Repository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RepositoryMapper extends BaseMapper<Repository> {
    @Select("select name,count(*) from repository where repository_id = #{id} group by name")
    List<Developer> selectDeveloperByRepositoryId(int id);

    @Select("select issue_rank,open_time,close_time from issue where state==#{state}")
    List<Issue> selectIssueByState(String state);

    @Select("select submission from repository_developer where repository_id = #{id} and name = #{name}")
    List<Integer> selectSubmissionRepositoryDeveloper(int id,String name);



}
