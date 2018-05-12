package online.zhaopei.personal.dao;

import online.zhaopei.personal.domain.PersonalInfoLog;

import java.util.List;

/**
 * Created by zhaopei on 18/5/8.
 */
public interface PersonalInfoLogDao {

    int insert(PersonalInfoLog personalInfoLog);

    int[] batchInsert(List<PersonalInfoLog> personalInfoLogList);
}
