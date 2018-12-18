package com.cnpiec.ireader.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnpiec.ireader.dao.TaskJournalDao;
import com.cnpiec.ireader.model.TaskJournal;

@Service
@SuppressWarnings("unused")
public class ErrorDataServiceImpl implements ErrorDataService {
    private Logger logger = LoggerFactory.getLogger(ErrorDataService.class);

    @Autowired
    private TaskJournalDao taskJournalDao;
    
    @Override
    public void queryErrorData1() {
        // 去重
        // 查询所有FLA为空的数据
        List<TaskJournal> list23 = taskJournalDao.queryTaskJournal23(0);
        for (TaskJournal taskJournal : list23) {
            String name = taskJournal.getName().split("\\.r")[0];
            if ("LRT_1950_15_8_IEStrans".equals(name)) {
                System.out.println("DEBUG");
            }
            List<TaskJournal> list = taskJournalDao.queryTaskJournal23ByName0(name, name + ".r%");
            // 不是唯一值,有重复值,只取时间最晚的一个
            if (list.size() > 1) {
                for (int i = 1; i < list.size(); i++) {
                    // 更新name前缀相同的created不是最新的记录
                    taskJournalDao.updateCopyStatus(list.get(i).getTaskId());
                }
            }
        }

    }

    /**
     * 已入库但入库文件时间小于该压缩包 2(代表最新文件未入库) 已入库入库文件时间早于或等于该压缩包 1(代表最新文件已入库,历史文件有未成功入库记录)  未入库 0
     */
    @Override
    public void queryErrorData2() {
        // 判断是否入库
        List<TaskJournal> list1 = taskJournalDao.queryTaskJournal23(0);
        for (TaskJournal taskJournal : list1) {
            String name = taskJournal.getName().split("\\.r")[0];
            List<TaskJournal> list = taskJournalDao.queryTaskJournalByName0(name, name + ".r%");
            if (list.size() > 0) {
                //String类型的日期比较，如果大于的话返回的是正整数，等于是0，小于的话就是负整数，而不仅仅局限于1,0和-1
                int compareTo = list.get(0).getCreated().compareTo(taskJournal.getCreated());
                if (compareTo == -1) {
                    taskJournalDao.updateMemo("2", taskJournal.getTaskId());
                } else {
                    taskJournalDao.updateMemo("1", taskJournal.getTaskId());
                }
            } else {
                taskJournalDao.updateMemo("0", taskJournal.getTaskId());
            }
        }

    }

}
