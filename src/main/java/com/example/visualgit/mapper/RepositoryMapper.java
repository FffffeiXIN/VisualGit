package com.example.visualgit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.visualgit.entity.Developer;
import com.example.visualgit.entity.Issue;
import com.example.visualgit.entity.Repository;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RepositoryMapper extends BaseMapper<Repository> {
    @Select("select name,submission from repository_developer where repository_id = #{id}")
    List<Developer> selectDeveloperByRepositoryId(int id);

    @Select("select issue_rank,open_time,close_time from issue where state==#{state} and repository_id = #{repos_id}")
    List<Issue> selectIssueByState(String state, String repos_id);

    @Select("select submission from repository_developer where repository_id = #{id} and name = #{name}")
    List<Integer> selectSubmissionRepositoryDeveloper(int id,String name);

    @Insert("insert into commit values(#{id},#{commit_time},#{repository})")
    void insertCommit(String id,String commit_time,String repository);

    @Insert("insert into release values(#{id},#{time},#{repository})")
    void insertRelease(String id,String time,String repository);

    @Insert("insert into repository_developer values(#{repos_id},#{name},#{submission})")
    void insertDeveloper(String repos_id,int name,int submission);

    @Insert("insert into issue values(#{rank},#{id},#{state},#{open_time},#{close_time})")
    void insertIssue(int rank,String id,String state,String open_time,String close_time);
}
