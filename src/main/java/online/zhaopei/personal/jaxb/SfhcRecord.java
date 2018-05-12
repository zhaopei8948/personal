package online.zhaopei.personal.jaxb;

import javax.xml.bind.annotation.*;

/**
 * Created by zhaopei on 18/5/10.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder =  {
    "gmsfhm", "xm", "gmsfhmPpddm", "xmPpddm", "swbs"
})
public class SfhcRecord {

    @XmlAttribute
    private Integer no;

    @XmlElement(name = "GMSFHM")
    private String gmsfhm;

    @XmlElement(name = "XM")
    private String xm;

    @XmlElement(name = "GMSFHM_PPDDM")
    private String gmsfhmPpddm;

    @XmlElement(name = "XM_PPDDM")
    private String xmPpddm;

    @XmlElement(name = "SWBS")
    private String swbs;

    public String getGmsfhm() {
        return gmsfhm;
    }

    public void setGmsfhm(String gmsfhm) {
        this.gmsfhm = gmsfhm;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getGmsfhmPpddm() {
        return gmsfhmPpddm;
    }

    public void setGmsfhmPpddm(String gmsfhmPpddm) {
        this.gmsfhmPpddm = gmsfhmPpddm;
    }

    public String getXmPpddm() {
        return xmPpddm;
    }

    public void setXmPpddm(String xmPpddm) {
        this.xmPpddm = xmPpddm;
    }

    public String getSwbs() {
        return swbs;
    }

    public void setSwbs(String swbs) {
        this.swbs = swbs;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}
