package wechat.function.stock;

public class StockImg {
	public String getData(String tpye, String stockCode){
		String result = "";
		String title = "";
		if(tpye.equals("分")){
			tpye = "min";
			title = "分时线___" + stockCode;
		}else if(tpye.equals("日")){
			tpye = "daily";
			title = "日K线___" + stockCode;
		}else if(tpye.equals("周")){
			tpye = "weekly";
			title = "周K线___" + stockCode;
		}else if(tpye.equals("月")){
			tpye = "monthly";
			title = "月K线___" + stockCode;
		}
			String pUrl = "http://image.sinajs.cn/newchart/" + tpye + "/n/" + stockCode + ".gif";
			String url = "http://finance.sina.com.cn/realstock/company/" + stockCode + "/nc.shtml";
			result = title + "," + url + "," + pUrl;
		return result;
	}
	
}
