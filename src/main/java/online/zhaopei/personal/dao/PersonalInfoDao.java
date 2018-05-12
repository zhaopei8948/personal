package online.zhaopei.personal.dao;

import online.zhaopei.personal.domain.PersonalInfo;

import java.util.List;

/**
 * Created by zhaopei on 18/5/8.
 */
public interface PersonalInfoDao {

    boolean existsByIdNumber(String idNumber);

    int insert(PersonalInfo personalInfo);

    int updatePersonalNameByIdNumber(String personalName, String idNumber);

    int updateStatusByIdNumber(String status, String idNumber);

    int[] batchInsert(List<PersonalInfo> personalInfoList);

    int[] batchUpdateStatus(List<PersonalInfo> personalInfoList);

    int[] batchUpdateNameAndStatus(List<PersonalInfo> personalInfoList);

    String findStatusByIdNumber(String idNumber);
}
