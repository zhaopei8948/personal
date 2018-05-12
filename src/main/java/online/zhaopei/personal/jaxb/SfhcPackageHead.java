package online.zhaopei.personal.jaxb;


import online.zhaopei.personal.jaxb.adapter.TimestampAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by zhaopei on 18/5/10.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "fwqqbh", "qqbwdm", "qqbwmc", "sjlyxtdm", "sjlyxtmc",
        "jls", "fssj", "fhdm", "fhms", "qqrgmsfhm", "qqrxm", "qqrdwdm", "qqrdwmc",
        "qqywlxdm", "qqywlxmc", "qqywxtdm", "qqywxtmc"
})
public class SfhcPackageHead {

    @XmlElement(name = "FWQQBH")
    private String fwqqbh;

    @XmlElement(name = "QQBWDM")
    private String qqbwdm;

    @XmlElement(name = "QQBWMC")
    private String qqbwmc;

    @XmlElement(name = "SJLYXTDM")
    private String sjlyxtdm;

    @XmlElement(name = "SJLYXTMC")
    private String sjlyxtmc;

    @XmlElement(name = "JLS")
    private Integer jls;

    @XmlElement(name = "FSSJ")
    @XmlJavaTypeAdapter(TimestampAdapter.class)
    private Date fssj;

    @XmlElement(name = "FHDM")
    private String fhdm;

    @XmlElement(name = "FHMS")
    private String fhms;

    @XmlElement(name = "QQRGMSFHM")
    private String qqrgmsfhm;

    @XmlElement(name = "QQRXM")
    private String qqrxm;

    @XmlElement(name = "QQRDWDM")
    private String qqrdwdm;

    @XmlElement(name = "QQRDWMC")
    private String qqrdwmc;

    @XmlElement(name = "QQYWLXDM")
    private String qqywlxdm;

    @XmlElement(name = "QQYWLXMC")
    private String qqywlxmc;

    @XmlElement(name = "QQYWXTDM")
    private String qqywxtdm;

    @XmlElement(name = "QQYWXTMC")
    private String qqywxtmc;

    public String getFwqqbh() {
        return fwqqbh;
    }

    public void setFwqqbh(String fwqqbh) {
        this.fwqqbh = fwqqbh;
    }

    public String getQqbwdm() {
        return qqbwdm;
    }

    public void setQqbwdm(String qqbwdm) {
        this.qqbwdm = qqbwdm;
    }

    public String getQqbwmc() {
        return qqbwmc;
    }

    public void setQqbwmc(String qqbwmc) {
        this.qqbwmc = qqbwmc;
    }

    public String getSjlyxtdm() {
        return sjlyxtdm;
    }

    public void setSjlyxtdm(String sjlyxtdm) {
        this.sjlyxtdm = sjlyxtdm;
    }

    public String getSjlyxtmc() {
        return sjlyxtmc;
    }

    public void setSjlyxtmc(String sjlyxtmc) {
        this.sjlyxtmc = sjlyxtmc;
    }

    public Integer getJls() {
        return jls;
    }

    public void setJls(Integer jls) {
        this.jls = jls;
    }

    public Date getFssj() {
        return fssj;
    }

    public void setFssj(Date fssj) {
        this.fssj = fssj;
    }

    public String getQqrgmsfhm() {
        return qqrgmsfhm;
    }

    public void setQqrgmsfhm(String qqrgmsfhm) {
        this.qqrgmsfhm = qqrgmsfhm;
    }

    public String getQqrxm() {
        return qqrxm;
    }

    public void setQqrxm(String qqrxm) {
        this.qqrxm = qqrxm;
    }

    public String getQqrdwdm() {
        return qqrdwdm;
    }

    public void setQqrdwdm(String qqrdwdm) {
        this.qqrdwdm = qqrdwdm;
    }

    public String getQqrdwmc() {
        return qqrdwmc;
    }

    public void setQqrdwmc(String qqrdwmc) {
        this.qqrdwmc = qqrdwmc;
    }

    public String getQqywlxdm() {
        return qqywlxdm;
    }

    public void setQqywlxdm(String qqywlxdm) {
        this.qqywlxdm = qqywlxdm;
    }

    public String getQqywlxmc() {
        return qqywlxmc;
    }

    public void setQqywlxmc(String qqywlxmc) {
        this.qqywlxmc = qqywlxmc;
    }

    public String getQqywxtdm() {
        return qqywxtdm;
    }

    public void setQqywxtdm(String qqywxtdm) {
        this.qqywxtdm = qqywxtdm;
    }

    public String getQqywxtmc() {
        return qqywxtmc;
    }

    public void setQqywxtmc(String qqywxtmc) {
        this.qqywxtmc = qqywxtmc;
    }

    public String getFhdm() {
        return fhdm;
    }

    public void setFhdm(String fhdm) {
        this.fhdm = fhdm;
    }

    public String getFhms() {
        return fhms;
    }

    public void setFhms(String fhms) {
        this.fhms = fhms;
    }
}
