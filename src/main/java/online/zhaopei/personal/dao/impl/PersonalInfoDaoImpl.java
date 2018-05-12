package online.zhaopei.personal.dao.impl;

import online.zhaopei.personal.dao.BaseDao;
import online.zhaopei.personal.dao.PersonalInfoDao;
import online.zhaopei.personal.domain.PersonalInfo;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaopei on 18/5/8.
 */
@Repository
public class PersonalInfoDaoImpl extends BaseDao implements PersonalInfoDao {

    @Override
    public boolean existsByIdNumber(String idNumber) {
        String sql = "select count(1) from personal_info t where t.id_number = ?";
        return 0 < this.getJdbcTemplate().queryForObject(sql, Integer.class, idNumber);
    }

    @Override
    public int insert(PersonalInfo personalInfo) {
        String sql = "insert into personal_info(seq_no, personal_name, id_number, dec_time) values (:seqNo, :personalName, :idNumber, :decTime)";
        return this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(personalInfo));
    }

    @Override
    public int updatePersonalNameByIdNumber(String personalName, String idNumber) {
        String sql = "update personal_info t set t.personal_name = ?, t.status = 'W' where t.id_number = ?";
        return this.getJdbcTemplate().update(sql, personalName, idNumber);
    }

    @Override
    public int updateStatusByIdNumber(String status, String idNumber) {
        String sql = "update personal_info t set t.status = ? where t.id_number = ?";
        return this.getJdbcTemplate().update(sql, status, idNumber);
    }

    @Override
    public int[] batchInsert(List<PersonalInfo> personalInfoList) {
        String sql = "insert into personal_info(seq_no, personal_name, id_number, dec_time, status) values (:seqNo, :personalName, :idNumber, :decTime, :status)";
        return this.getNamedParameterJdbcTemplate().batchUpdate(sql, SqlParameterSourceUtils.createBatch(personalInfoList));
    }

    @Override
    public int[] batchUpdateStatus(List<PersonalInfo> personalInfoList) {
        String sql = "update personal_info pi set pi.status = :status where pi.id_number = :idNumber";
        return this.getNamedParameterJdbcTemplate().batchUpdate(sql, SqlParameterSourceUtils.createBatch(personalInfoList));
    }

    @Override
    public int[] batchUpdateNameAndStatus(List<PersonalInfo> personalInfoList) {
        String sql = "update personal_info pi set pi.personal_name = :personalName, pi.status = :status where pi.id_number = :idNumber";
        return this.getNamedParameterJdbcTemplate().batchUpdate(sql, SqlParameterSourceUtils.createBatch(personalInfoList));
    }

    @Override
    public String findStatusByIdNumber(String idNumber) {
        String sql = "select t.status from personal_info t where t.id_number = ?";
        String result = null;

        if (this.existsByIdNumber(idNumber)) {
            result = this.getJdbcTemplate().queryForObject(sql, String.class, idNumber);
        }

        return result;
    }
}
