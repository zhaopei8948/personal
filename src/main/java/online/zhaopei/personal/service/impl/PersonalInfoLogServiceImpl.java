package online.zhaopei.personal.service.impl;

import online.zhaopei.personal.dao.PersonalInfoLogDao;
import online.zhaopei.personal.domain.PersonalInfoLog;
import online.zhaopei.personal.service.PersonalInfoLogService;
import online.zhaopei.personal.service.PersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaopei on 18/5/8.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PersonalInfoLogServiceImpl implements PersonalInfoLogService {

    @Autowired
    private PersonalInfoLogDao personalInfoLogDao;

    @Override
    public void insert(PersonalInfoLog personalInfoLog) {
        this.personalInfoLogDao.insert(personalInfoLog);
    }
}
