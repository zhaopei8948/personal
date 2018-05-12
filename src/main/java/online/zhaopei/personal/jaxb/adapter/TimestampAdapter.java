package online.zhaopei.personal.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampAdapter extends XmlAdapter<String, Date> {
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public Date unmarshal(String v) throws Exception {
		return this.simpleDateFormat.parse(v);
	}

	@Override
	public String marshal(Date v) throws Exception {
		return this.simpleDateFormat.format(v);
	}

}
