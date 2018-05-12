package online.zhaopei.personal.service;

import online.zhaopei.personal.domain.PersonalInfo;
import online.zhaopei.personal.jaxb.IdentityRequest;

import java.util.List;

/**
 * Created by zhaopei on 18/5/8.
 */
public interface PersonalInfoService {

    boolean existsByIdNumber(String idNumber);

    void insert(PersonalInfo personalInfo);

    void batchInsert(List<PersonalInfo> personalInfoList);

    void processIdentityRequest(IdentityRequest identityRequest);
}
