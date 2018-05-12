package online.zhaopei.personal.service.impl;

import com.alibaba.fastjson.JSONObject;
import online.zhaopei.personal.configuration.PersonalProp;
import online.zhaopei.personal.constant.MessageTypeEnum;
import online.zhaopei.personal.constant.PersonalInfoConstant;
import online.zhaopei.personal.dao.PersonalInfoDao;
import online.zhaopei.personal.dao.PersonalInfoLogDao;
import online.zhaopei.personal.domain.PersonalInfo;
import online.zhaopei.personal.domain.PersonalInfoLog;
import online.zhaopei.personal.jaxb.*;
import online.zhaopei.personal.service.PersonalInfoService;
import online.zhaopei.personal.transformer.CommonTransformer;
import online.zhaopei.personal.util.HttpUtil;
import online.zhaopei.personal.util.QueueUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhaopei on 18/5/8.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private static final Log LOGGER = LogFactory.getLog(PersonalInfoServiceImpl.class);

    @Autowired
    private PersonalInfoDao personalInfoDao;

    @Autowired
    private PersonalInfoLogDao personalInfoLogDao;

    @Autowired
    private CommonTransformer commonTransformer;

    @Autowired
    private PersonalProp personalProp;

    @Override
    public boolean existsByIdNumber(String idNumber) {
        return this.personalInfoDao.existsByIdNumber(idNumber);
    }

    @Override
    public void insert(PersonalInfo personalInfo) {
        this.personalInfoDao.insert(personalInfo);
    }

    @Override
    public void batchInsert(List<PersonalInfo> personalInfoList) {
        this.personalInfoDao.batchInsert(personalInfoList);
    }

    @Override
    public void processIdentityRequest(IdentityRequest identityRequest) {
        Assert.notNull(identityRequest, "IdentityRequest must not be null");
        JSONObject httpResponse = null;
        PersonalInfoNew personalInfoNew = new PersonalInfoNew();
        PersonalInfoHead personalInfoHead = null;
        List<PersonalInfoList> personalInfoListList = personalInfoNew.getPersonalInfoListList();
        PersonalInfoList personalInfoListJaxb = null;
        SfhcData responseData = null;
        IdentityHead identityHead = identityRequest.getIdentityHead();
        List<Person> personList = identityRequest.getPersons().getPersonList();
        Person person = null;
        SfhcPackage sfhcPackage = new SfhcPackage();
        SfhcData sfhcData = new SfhcData();
        List<PersonalInfo> personalInfoList = new ArrayList<PersonalInfo>();
        List<PersonalInfo> updatePersonalInfoList = new ArrayList<PersonalInfo>();
        List<PersonalInfoLog> personalInfoLogList = new ArrayList<PersonalInfoLog>();
        PersonalInfoLog personalInfoLog = null;
        PersonalInfo personalInfo = null;
        List<SfhcRecord> sfhcRecordList = sfhcData.getSfhcRecordList();
        SfhcRecord sfhcRecord = null;
        SfhcPackageHead sfhcPackageHead = new SfhcPackageHead();
        sfhcPackageHead.setFwqqbh(personalProp.getSfhcFwqqbh());
        sfhcPackageHead.setQqbwdm(personalProp.getSfhcQqbwdm());
        sfhcPackageHead.setQqbwmc(personalProp.getSfhcQqbwmc());
        sfhcPackageHead.setSjlyxtdm(personalProp.getSfhcSjlyxtdm());
        sfhcPackageHead.setSjlyxtmc(personalProp.getSfhcSjlyxtmc());
        sfhcPackageHead.setQqrgmsfhm(personalProp.getSfhcQqrgmsfhm());
        sfhcPackageHead.setQqrxm(personalProp.getSfhcQqrxm());
        sfhcPackageHead.setQqrdwdm(personalProp.getSfhcQqrdwdm());
        sfhcPackageHead.setQqrdwmc(personalProp.getSfhcQqrdwmc());
        sfhcPackageHead.setQqywlxdm(personalProp.getSfhcQqywlxdm());
        sfhcPackageHead.setQqywlxmc(personalProp.getSfhcQqywlxmc());
        sfhcPackageHead.setQqywxtdm(personalProp.getSfhcQqywxtdm());
        sfhcPackageHead.setQqywxtmc(personalProp.getSfhcQqywxtmc());
        int maxRequestCount = personalProp.getMaxRequestCount();
        int receiveCount = identityHead.getCount();

        for (Person p : personList) {
            if (PersonalInfoConstant.STATUS_PASS.toString().equals(this.personalInfoDao.findStatusByIdNumber(p.getId()))) {
                personList.remove(p);
            }
        }
        receiveCount = personList.size();
        int pageCount = receiveCount / maxRequestCount;
        if (0 < receiveCount % maxRequestCount) {
            pageCount++;
        }

        for (int i = 0; i < pageCount; i++) {
            sfhcPackageHead.setJls(0);
            sfhcRecordList.clear();
            for (int j = 0; j < (i + 1 * maxRequestCount) && j < receiveCount; j++) {
                person = personList.get(i * maxRequestCount + j);
                personalInfo = new PersonalInfo();
                sfhcRecord = new SfhcRecord();
                sfhcPackageHead.setJls(j + 1);
                personalInfo.setDecTime(Calendar.getInstance().getTime());
                personalInfo.setIdNumber(person.getId());
                personalInfo.setPersonalName(person.getName());
                sfhcRecord.setNo(j + 1);
                sfhcRecord.setGmsfhm(person.getId());
                sfhcRecord.setXm(person.getName());
                sfhcRecordList.add(sfhcRecord);
                personalInfo.setStatus(PersonalInfoConstant.STATUS_WAIT_FOR_VERIFICATION.toString());
                if (StringUtils.isEmpty(this.personalInfoDao.findStatusByIdNumber(person.getId()))) {
                    personalInfo.setSeqNo(UUID.randomUUID().toString());
                    personalInfoList.add(personalInfo);
                } else {
                    updatePersonalInfoList.add(personalInfo);
                }
            }
            try {

                this.personalInfoDao.batchInsert(personalInfoList);
                this.personalInfoDao.batchUpdateNameAndStatus(updatePersonalInfoList);
                sfhcPackageHead.setFssj(Calendar.getInstance().getTime());
                sfhcPackage.setSfhcData(sfhcData);
                sfhcPackage.setSfhcPackageHead(sfhcPackageHead);
                int retryCount = this.personalProp.getRetryCount();
                int currRetryNum = 0;
                boolean retry = false;
                int sleep = 500;
                int sleepIncrement = 200;
                do {
                    if (0 < currRetryNum) {
                        Thread.sleep(currRetryNum * sleep + sleepIncrement);
                    }
                    httpResponse = HttpUtil.requestSfhc(this.personalProp, this.commonTransformer, sfhcPackage);

                    if (httpResponse.getBoolean("success")) {
                        retry = false;
                        responseData = httpResponse.getObject("data", SfhcData.class);
                        updatePersonalInfoList.clear();
                        personalInfoHead = new PersonalInfoHead();
                        personalInfoHead.setMessageNo(UUID.randomUUID().toString());
                        personalInfoHead.setMessageTime(Calendar.getInstance().getTime());
                        personalInfoHead.setEcssCspNo("hniftz");
                        personalInfoHead.setCount(responseData.getSfhcRecordList().size());
                        for (SfhcRecord sr : responseData.getSfhcRecordList()) {
                            personalInfo = new PersonalInfo();
                            personalInfoListJaxb = new PersonalInfoList();
                            personalInfoLog = new PersonalInfoLog();
                            personalInfo.setIdNumber(sr.getGmsfhm());
                            personalInfoListJaxb.setBillType("1");
                            personalInfoListJaxb.setIdNumber(sr.getGmsfhm());
                            personalInfoListJaxb.setPersonalName(sr.getXm());
                            personalInfoListJaxb.setSignTime(Calendar.getInstance().getTime());
                            if ("1".equals(sr.getGmsfhmPpddm()) && "1".equals(sr.getXmPpddm())) {
                                personalInfo.setStatus(PersonalInfoConstant.STATUS_PASS.toString());
                            } else {
                                personalInfo.setStatus(PersonalInfoConstant.STATUS_FAIL.toString());
                            }
                            personalInfoListJaxb.setStatus(personalInfo.getStatus());
                            personalInfoLog.setSeqNo(UUID.randomUUID().toString());
                            personalInfoLog.setIdNumber(sr.getGmsfhm());
                            personalInfoLog.setPersonalName(sr.getXm());
                            personalInfoLog.setRequestTime(Calendar.getInstance().getTime());
                            personalInfoLog.setResponse(JSONObject.toJSONString(sr));
                            updatePersonalInfoList.add(personalInfo);
                            personalInfoLogList.add(personalInfoLog);
                            personalInfoListList.add(personalInfoListJaxb);
                        }
                        this.personalInfoDao.batchUpdateStatus(updatePersonalInfoList);
                        this.personalInfoLogDao.batchInsert(personalInfoLogList);
                        personalInfoNew.setPersonalInfoHead(personalInfoHead);
                        QueueUtil.sendMessage(personalInfoNew, MessageTypeEnum.TYPE_SEND);
                    } else {
                        retry = true;
                        if (StringUtils.isEmpty(httpResponse.getString("fhdm"))) {
                            LOGGER.error("request sfhc error " + httpResponse.getString("faultCode") + ":"
                                    + httpResponse.getString("faultCause"));
                        } else {
                            LOGGER.error("request sfhc error " + httpResponse.getString("fhdm") + ":"
                                    + httpResponse.getString("fhms"));
                            //switch (httpResponse.getString("fhdm")) {
                            //    case "-10":
                            //    case "-20":
                            //    case "-80":
                            //    case "-90":
                            //        retry = true;
                            //        break;
                            //    default:
                            //        retry = false;
                            //}
                        }
                    }
                    currRetryNum++;
                } while (retry && currRetryNum < retryCount);
                if (retry) {
                    IdentityRequest errorForwardIr = new IdentityRequest();
                    IdentityHead errorIh = new IdentityHead();
                    Persons errorPs = new Persons();
                    List<Person> errorPersonList = errorPs.getPersonList();
                    Person errorPerson = null;
                    errorIh.setMessageNo(UUID.randomUUID().toString());
                    errorIh.setEcssCspNo(identityHead.getEcssCspNo());
                    errorIh.setCompanyId(identityHead.getCompanyId());
                    errorIh.setMessageTime(Calendar.getInstance().getTime());
                    errorIh.setCount(sfhcRecordList.size());
                    errorForwardIr.setIdentityHead(errorIh);
                    errorForwardIr.setPersons(errorPs);
                    for (SfhcRecord sr : sfhcRecordList) {
                        errorPerson = new Person();
                        errorPerson.setId(sr.getGmsfhm());
                        errorPerson.setName(sr.getXm());
                        errorPersonList.add(errorPerson);
                    }
                    QueueUtil.sendMessage(errorForwardIr, MessageTypeEnum.TYPE_ERROR_FORWARD);
                }
            } catch (Exception e) {
                LOGGER.error("processIdentityRequest error ", e);
            }
        }
    }
}
