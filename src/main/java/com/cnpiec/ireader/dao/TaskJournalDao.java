package com.cnpiec.ireader.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cnpiec.ireader.model.TaskJournal;

@Mapper
public interface TaskJournalDao {
    @Select("select * from BDC_TASK_JOURNAL_1 where copy = #{copy}  ")
    public List<TaskJournal> queryTaskJournal23(int copy);

    @Select("select * from BDC_TASK_JOURNAL_1 where name = #{name} or name like #{name1} and copy = '0' order by created desc")
    public List<TaskJournal> queryTaskJournal23ByName0(@Param("name")String name,@Param("name1")String name1);

    @Update("update BDC_TASK_JOURNAL_1 set  copy = '1' where taskId = #{taskId} ")
    public void updateCopyStatus(String taskId);

    @Update("update BDC_TASK_JOURNAL_1 set  memo = #{memo} where taskId = #{taskId} ")
    public void updateMemo(@Param("memo")String memo, @Param("taskId")String taskId);

    @Select("select * from BDC_TASK_JOURNAL_0 where name = #{name} or  name like #{name1} and status = '10' order by created desc ")
    public List<TaskJournal> queryTaskJournalByName0(@Param("name")String name,@Param("name1")String name1);

}
