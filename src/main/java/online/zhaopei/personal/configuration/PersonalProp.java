package online.zhaopei.personal.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by zhaopei on 18/5/8.
 */
@Component
@ConfigurationProperties(prefix = "personal")
public class PersonalProp {

    private String receiveDir;

    private String receiveBackupDir;

    private String receiveBackupPrefix;

    private String receiveFilePattern;

    private String sendDir;

    private String sendBackupDir;

    private String errorForwardDir;

    private String errorForwardBackupDir;

    private Integer messageQueue;

    private Integer poolSize;

    private Integer scanRate;

    private String sendPrefix;

    private String sendBackupPrefix;

    private String suffix;

    private String jaxbPackages;

    private Boolean jaxbFormated;

    private String charset;

    private String serviceHost;

    private String getKeyUrl;

    private String sfhcUrl;

    private String sfhcUserName;

    private String sfhcPassword;

    private String sid;

    private String rid;

    private String secret;

    private String secretFile;

    private Integer retryCount;

    private Integer maxRequestCount;

    private String sfhcFwqqbh;

    private String sfhcQqbwdm;

    private String sfhcQqbwmc;

    private String sfhcSjlyxtdm;

    private String sfhcSjlyxtmc;

    private String sfhcQqrgmsfhm;

    private String sfhcQqrxm;

    private String sfhcQqrdwdm;

    private String sfhcQqrdwmc;

    private String sfhcQqywlxdm;

    private String sfhcQqywlxmc;

    private String sfhcQqywxtdm;

    private String sfhcQqywxtmc;

    public String getReceiveDir() {
        return receiveDir;
    }

    public void setReceiveDir(String receiveDir) {
        this.receiveDir = receiveDir;
    }

    public String getSendDir() {
        return sendDir;
    }

    public void setSendDir(String sendDir) {
        this.sendDir = sendDir;
    }

    public String getSendBackupDir() {
        return sendBackupDir;
    }

    public void setSendBackupDir(String sendBackupDir) {
        this.sendBackupDir = sendBackupDir;
    }

    public String getErrorForwardDir() {
        return errorForwardDir;
    }

    public void setErrorForwardDir(String errorForwardDir) {
        this.errorForwardDir = errorForwardDir;
    }

    public String getErrorForwardBackupDir() {
        return errorForwardBackupDir;
    }

    public void setErrorForwardBackupDir(String errorForwardBackupDir) {
        this.errorForwardBackupDir = errorForwardBackupDir;
    }

    public Integer getMessageQueue() {
        return messageQueue;
    }

    public void setMessageQueue(Integer messageQueue) {
        this.messageQueue = messageQueue;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    public Integer getScanRate() {
        return scanRate;
    }

    public void setScanRate(Integer scanRate) {
        this.scanRate = scanRate;
    }

    public String getSendPrefix() {
        return sendPrefix;
    }

    public void setSendPrefix(String sendPrefix) {
        this.sendPrefix = sendPrefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getReceiveFilePattern() {
        return receiveFilePattern;
    }

    public void setReceiveFilePattern(String receiveFilePattern) {
        this.receiveFilePattern = receiveFilePattern;
    }

    public String getJaxbPackages() {
        return jaxbPackages;
    }

    public void setJaxbPackages(String jaxbPackages) {
        this.jaxbPackages = jaxbPackages;
    }

    public Boolean getJaxbFormated() {
        return jaxbFormated;
    }

    public void setJaxbFormated(Boolean jaxbFormated) {
        this.jaxbFormated = jaxbFormated;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public String getGetKeyUrl() {
        return getKeyUrl;
    }

    public void setGetKeyUrl(String getKeyUrl) {
        this.getKeyUrl = getKeyUrl;
    }

    public String getSfhcUrl() {
        return sfhcUrl;
    }

    public void setSfhcUrl(String sfhcUrl) {
        this.sfhcUrl = sfhcUrl;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public String getSfhcUserName() {
        return sfhcUserName;
    }

    public void setSfhcUserName(String sfhcUserName) {
        this.sfhcUserName = sfhcUserName;
    }

    public String getSfhcPassword() {
        return sfhcPassword;
    }

    public void setSfhcPassword(String sfhcPassword) {
        this.sfhcPassword = sfhcPassword;
    }

    public String getSecretFile() {
        return secretFile;
    }

    public void setSecretFile(String secretFile) {
        this.secretFile = secretFile;
    }

    public Integer getMaxRequestCount() {
        return maxRequestCount;
    }

    public void setMaxRequestCount(Integer maxRequestCount) {
        this.maxRequestCount = maxRequestCount;
    }

    public String getSfhcFwqqbh() {
        return sfhcFwqqbh;
    }

    public void setSfhcFwqqbh(String sfhcFwqqbh) {
        this.sfhcFwqqbh = sfhcFwqqbh;
    }

    public String getSfhcQqbwdm() {
        return sfhcQqbwdm;
    }

    public void setSfhcQqbwdm(String sfhcQqbwdm) {
        this.sfhcQqbwdm = sfhcQqbwdm;
    }

    public String getSfhcQqbwmc() {
        return sfhcQqbwmc;
    }

    public void setSfhcQqbwmc(String sfhcQqbwmc) {
        this.sfhcQqbwmc = sfhcQqbwmc;
    }

    public String getSfhcSjlyxtdm() {
        return sfhcSjlyxtdm;
    }

    public void setSfhcSjlyxtdm(String sfhcSjlyxtdm) {
        this.sfhcSjlyxtdm = sfhcSjlyxtdm;
    }

    public String getSfhcSjlyxtmc() {
        return sfhcSjlyxtmc;
    }

    public void setSfhcSjlyxtmc(String sfhcSjlyxtmc) {
        this.sfhcSjlyxtmc = sfhcSjlyxtmc;
    }

    public String getSfhcQqrgmsfhm() {
        return sfhcQqrgmsfhm;
    }

    public void setSfhcQqrgmsfhm(String sfhcQqrgmsfhm) {
        this.sfhcQqrgmsfhm = sfhcQqrgmsfhm;
    }

    public String getSfhcQqrxm() {
        return sfhcQqrxm;
    }

    public void setSfhcQqrxm(String sfhcQqrxm) {
        this.sfhcQqrxm = sfhcQqrxm;
    }

    public String getSfhcQqrdwdm() {
        return sfhcQqrdwdm;
    }

    public void setSfhcQqrdwdm(String sfhcQqrdwdm) {
        this.sfhcQqrdwdm = sfhcQqrdwdm;
    }

    public String getSfhcQqrdwmc() {
        return sfhcQqrdwmc;
    }

    public void setSfhcQqrdwmc(String sfhcQqrdwmc) {
        this.sfhcQqrdwmc = sfhcQqrdwmc;
    }

    public String getSfhcQqywlxdm() {
        return sfhcQqywlxdm;
    }

    public void setSfhcQqywlxdm(String sfhcQqywlxdm) {
        this.sfhcQqywlxdm = sfhcQqywlxdm;
    }

    public String getSfhcQqywlxmc() {
        return sfhcQqywlxmc;
    }

    public void setSfhcQqywlxmc(String sfhcQqywlxmc) {
        this.sfhcQqywlxmc = sfhcQqywlxmc;
    }

    public String getSfhcQqywxtdm() {
        return sfhcQqywxtdm;
    }

    public void setSfhcQqywxtdm(String sfhcQqywxtdm) {
        this.sfhcQqywxtdm = sfhcQqywxtdm;
    }

    public String getSfhcQqywxtmc() {
        return sfhcQqywxtmc;
    }

    public void setSfhcQqywxtmc(String sfhcQqywxtmc) {
        this.sfhcQqywxtmc = sfhcQqywxtmc;
    }

    public String getReceiveBackupDir() {
        return receiveBackupDir;
    }

    public void setReceiveBackupDir(String receiveBackupDir) {
        this.receiveBackupDir = receiveBackupDir;
    }

    public String getReceiveBackupPrefix() {
        return receiveBackupPrefix;
    }

    public void setReceiveBackupPrefix(String receiveBackupPrefix) {
        this.receiveBackupPrefix = receiveBackupPrefix;
    }

    public String getSendBackupPrefix() {
        return sendBackupPrefix;
    }

    public void setSendBackupPrefix(String sendBackupPrefix) {
        this.sendBackupPrefix = sendBackupPrefix;
    }
}
