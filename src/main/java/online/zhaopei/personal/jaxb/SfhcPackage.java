package online.zhaopei.personal.jaxb;

import javax.xml.bind.annotation.*;

/**
 * Created by zhaopei on 18/5/11.
 */
@XmlRootElement(name = "PACKAGE")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "sfhcPackageHead", "sfhcData"
})
public class SfhcPackage {

    @XmlElement(name = "PACKAGEHEAD")
    private SfhcPackageHead sfhcPackageHead;

    @XmlElement(name = "DATA")
    private SfhcData sfhcData;

    public SfhcPackageHead getSfhcPackageHead() {
        return sfhcPackageHead;
    }

    public void setSfhcPackageHead(SfhcPackageHead sfhcPackageHead) {
        this.sfhcPackageHead = sfhcPackageHead;
    }

    public SfhcData getSfhcData() {
        return sfhcData;
    }

    public void setSfhcData(SfhcData sfhcData) {
        this.sfhcData = sfhcData;
    }
}
