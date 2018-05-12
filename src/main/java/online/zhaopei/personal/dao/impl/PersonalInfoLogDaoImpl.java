package online.zhaopei.personal.dao.impl;

import online.zhaopei.personal.dao.BaseDao;
import online.zhaopei.personal.dao.PersonalInfoLogDao;
import online.zhaopei.personal.domain.PersonalInfoLog;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhaopei on 18/5/8.
 */
@Repository
public class PersonalInfoLogDaoImpl extends BaseDao implements PersonalInfoLogDao {

    @Override
    public int insert(PersonalInfoLog personalInfoLog) {
        String sql = "insert into personal_info_log(seq_no, personal_name, id_number, request_time, response) values (:seqNo, :personalName, :idNumber, :requestTime, :response)";
        return this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(personalInfoLog));
    }

    @Override
    public int[] batchInsert(List<PersonalInfoLog> personalInfoLogList) {
        String sql = "insert into personal_info_log(seq_no, personal_name, id_number, request_time, response) values (:seqNo, :personalName, :idNumber, :requestTime, :response)";
        return this.getNamedParameterJdbcTemplate().batchUpdate(sql, SqlParameterSourceUtils.createBatch(personalInfoLogList));
    }
}
