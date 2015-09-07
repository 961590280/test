package com.weimingfj.webcollection.job;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.weimingfj.common.utils.MapUtils;

/**
 * �ɼ��й�����������-��Դ��Ϣ
 * @author �ƶ���
 *
 */
@Component("collectionWljyw")
public class CollectionWljyw {

	@Resource
	private JdbcTemplate wlgjJdbcTemplate;//�����ܼ�
	
	private static String ZGWLJYW = "http://www.56135.com/56135/trade/goodsindex/P6357750001.html";
	
	
	@Scheduled(cron = "0/10 * * * * ?")
	public void exec(){
		String str = getHtmlContent(ZGWLJYW);
		
		Document doc = Jsoup.parse(str);
		
		Elements masthead = doc.select("div.i_line");
		for (int i = 0; i < masthead.size(); i++) {
			Element e = masthead.get(i);
			Document doc2 = Jsoup.parse(e.outerHtml());
			Elements link = doc2.select("a");
			String linkHref = link.attr("href");//��ȡ�����URL
			//System.out.println(linkHref);
			//System.out.println(linkHref.lastIndexOf("/"));
			String id = linkHref.substring(linkHref.lastIndexOf("/")+1, linkHref.lastIndexOf("."));//先获取id

			if( !isColl(id)) {
				String detailUrl = "http://www.56135.com"+linkHref;
				String detailHtml = getHtmlContent(detailUrl);
				
				
				Document docDetail = Jsoup.parse(detailHtml);
				Elements eDetail = docDetail.select("div.tips2");//��Ϣ����
				//Elements eDetail = docDetail.select("span.lmark");
				String star = docDetail.select("span.start").text();//������
				String end = docDetail.select("span.end").text();//Ŀ�ĵ�
				
				Elements lmark = eDetail.select("li");
				Elements titleEle = docDetail.select("span.yuntitle");//����
				
				
				String titile = "";//����
				String xxlx = "";//��Ϣ����
				String goodName = "";//��Ʒ���
				String goodType = "";//��Ʒ����
				String weight = "";//����/���
				String sendTime = "";//��������
				String endTime = "";//��ֹ����
				String note = "";//��ע
				
				if(titleEle != null) {
					String s = titleEle.get(0).after("</span>").html();
					titile = s.substring(s.lastIndexOf(">")+1, s.length());
				}
				
				if(lmark != null ){
					String s = lmark.get(1).after("</span>").html();
					xxlx = s.substring(s.lastIndexOf(">")+1, s.length());
					
					 s = lmark.get(2).after("</span>").html();
					 goodName = s.substring(s.lastIndexOf(">")+1, s.length());
					 
					 s = lmark.get(3).after("</span>").html();
					 goodType = s.substring(s.lastIndexOf(">")+1, s.length());
					 
					 s = lmark.get(4).after("</span>").html();
					 weight = s.substring(s.lastIndexOf(">")+1, s.length());
					 
					 s = lmark.get(5).after("</span>").html();
					 sendTime = s.substring(s.lastIndexOf(">")+1, s.length());
					 
					 
					 //��Щ��û�н�ֹʱ���
					 if(lmark.size() > 7) {
						 s = lmark.get(6).after("</span>").html();
						 endTime = s.substring(s.lastIndexOf(">")+1, s.length());
							
						 note = lmark.get(7).select("p").first().text();
					 } else {
						 note = lmark.get(6).select("p").first().text();
					 }
					 
					
				}
				
				
				String name=docDetail.select("span#l_Name").text();//��������
				String mob=docDetail.select("span#sp_mob").text();//�ֻ����
				String tel=docDetail.select("span#l_Tel").text();//�̶��绰
				String qq=docDetail.select("span#l_QQ").text();//QQ
				
				//ȡ�����غ�Ŀ�ĵ�code
				String starCode = "";
				String endCode = "";
				if(star != null && !"".equals(star)) {
					String[] s = star.split("-");
					if(s != null && s.length > 0) {
						for(int m=s.length-1; m>=0;m--) {
							Map<String,Object> map = getAreaInfo(s[m]);
							if(map != null) {
								starCode = MapUtils.getString(map, "CODE");
								star = MapUtils.getString(map, "CITY_TEXT");
							}
						}
					}
				}
				
				if(end != null && !"".equals(end)) {
					String[] s = end.split("-");
					if(s != null && s.length > 0) {
						for(int m=s.length-1; m>=0;m--) {
							Map<String,Object> map = getAreaInfo(s[m]);
							if(map != null) {
								endCode = MapUtils.getString(map, "CODE");
								end = MapUtils.getString(map, "CITY_TEXT");
							}
						}
					}
				}
				
				if(goodName == null || "".equals(goodName)) {
					goodName = goodType;
				}
				
				String g_size = "";
				String g_size_type = "";
				if(weight != null && !"".equals(weight)) {
					char[] ary = weight.toCharArray();
					for (char c : ary) {
						if("0123456789.".indexOf(c) > -1 ) {
							g_size += c;
						} else {
							g_size_type +=c;
						}
					}
				}
				
				System.out.println(titile + "===");
	//			System.out.println(star + "----" + end);
	//			System.out.println(xxlx + "===" + goodName + "===" + goodType +
	//					"==="+weight + "===" + sendTime + "===" + endTime + "===" + note);
	//			System.out.println(name + "====" +mob +"==="+ tel + "===" + qq);
				//System.out.println(detailHtml);
				
				insertWebCollCar(id);
				String sql = "insert into sys_cargo_tab(seq,s_area,s_code_val,t_area,t_code_val,g_type,g_size,g_size_type,con_phone,con_man," +
						"create_time,pub_name,pub_mobile,source_way,is_delete,remark ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
				
				wlgjJdbcTemplate.update(sql,new Object[]{UUID.randomUUID(),starCode,star,endCode,end,goodName,g_size,g_size_type,mob,name,sendTime,name,mob,"4","N",note});
				
			}
			
			break;
		}
	}
	
	public boolean isColl(String id) {
		String sql = "select count(1) from web_collection_cargo where webname=? and objid=?";
		int result = wlgjJdbcTemplate.queryForInt(sql);
		
		return result > 0;
	}
	
	/**
	 * ���뵽�м��
	 * @param id
	 */
	public void insertWebCollCar(String id) {
		String sql = "insert into web_collection_cargo(webname,objid) values(?,?)";
		wlgjJdbcTemplate.update(sql,new Object[] {ZGWLJYW,id});
	}
	
	
	/**
	 * �����ƻ�ȡ�����CODE�����
	 * @param name
	 * @return
	 */
	public Map<String,Object> getAreaInfo(String name) {
		String sql = "select code,city_text from base_area_tab  where text like ? ";
		List<Map<String,Object>> map = wlgjJdbcTemplate.queryForList(sql, new Object[]{"%"+ name  + "%"});
		Map<String,Object> result = null;
		if(map != null && map.size() > 0) {
			result = map.get(0);
		}
		return result;
	}
	
	
	/**
	 * ��ȡURL��HTML����
	 * @param url
	 * @return
	 */
	public String getHtmlContent(String url) {
		 // ����Ĭ�ϵ�httpClientʵ��.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // ����httppost    
        HttpPost httppost = new HttpPost(url);  
        // �����������    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
//        formparams.add(new BasicNameValuePair("txtAccount", "13067212786"));  
//        formparams.add(new BasicNameValuePair("txtPwd", "abc123��@#"));  
//        formparams.add(new BasicNameValuePair("txtCheckCode", "8888"));  
        UrlEncodedFormEntity uefEntity;  
        String result = "";
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            //System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    result = EntityUtils.toString(entity, "UTF-8");
                }  
                
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // �ر�����,�ͷ���Դ    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        } 
        
        return result;
	}
}
