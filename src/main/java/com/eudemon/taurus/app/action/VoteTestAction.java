package com.eudemon.taurus.app.action;

import java.awt.Color;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eudemon.taurus.app.entity.OperResult;
import com.eudemon.taurus.app.util.JasonUtils;
import com.eudemon.taurus.app.util.RequestUtils;
import com.eudemon.taurus.app.util.VerifycodeUtil;

@Controller
@RequestMapping(value = "/tt")
public class VoteTestAction {
	@RequestMapping(value = "vote")
	public void vote(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		OperResult or = new OperResult();
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet("http://vote.cyol.com/wenchuanbang/codePic/n/1");
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			String code = VerifycodeUtil.getCode(response1.getEntity().getContent(), new Color(41, 163, 238));
			response1.close();
			System.out.println("code=" + code);

			HttpPost httpPost = new HttpPost("http://vote.cyol.com/wenchuanbang/vote/c/1");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("code", code));
			nvps.add(new BasicNameValuePair("data", "c38"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response2 = httpclient.execute(httpPost);
			try {
				System.out.println("rs=" + IOUtils.toString(response2.getEntity().getContent()));
			} finally {
				response2.close();
			}
		} finally {
			httpclient.close();
		}
		
		JasonUtils.writeJasonP(request, response, or);
	}
	
	@RequestMapping(value = "vote1")
	public void vote1(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String code = RequestUtils.getParameterAndTrimDecode(request, "code", "utf-8");
		OperResult or = new OperResult();
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			System.out.println("code=" + code);

			HttpPost httpPost = new HttpPost("http://vote.cyol.com/wenchuanbang/vote/c/1");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("code", code));
			nvps.add(new BasicNameValuePair("data", "c38"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response2 = httpclient.execute(httpPost);
			try {
				System.out.println("rs=" + IOUtils.toString(response2.getEntity().getContent()));
			} finally {
				response2.close();
			}
		} finally {
			httpclient.close();
		}
		
		JasonUtils.writeJasonP(request, response, or);
	}
	
	public void vote() throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet("http://vote.cyol.com/wenchuanbang/codePic/n/1");
			httpGet.addHeader("Cookie", "");
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			response1.close();
			
			for (int i = 0; i < 2; i++) {
				System.out.println(i);
				
				HttpPost httpPost = new HttpPost("http://vote.cyol.com/wenchuanbang/vote/c/1");
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("code", "aaaa"));
				nvps.add(new BasicNameValuePair("data", "c38"));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				CloseableHttpResponse response2 = httpclient.execute(httpPost);
				try {
					HttpEntity entity2 = response2.getEntity();
					String ct = IOUtils.toString(entity2.getContent());
					String code = ct.substring(ct.length() - 6, ct.length() - 2);
					System.out.println(ct);
					EntityUtils.consume(entity2);

					List<NameValuePair> nvpss = new ArrayList<NameValuePair>();
					nvpss.add(new BasicNameValuePair("code", code));
					nvpss.add(new BasicNameValuePair("data", "c38"));
					httpPost.setEntity(new UrlEncodedFormEntity(nvpss));
					CloseableHttpResponse response3 = httpclient.execute(httpPost);
					System.out.println(IOUtils.toString(response3.getEntity().getContent()));
				} finally {
					response2.close();
				}
			}
		} finally {
			httpclient.close();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			VoteTestAction tt = new VoteTestAction();
			try {
				tt.vote(null, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String[] aa = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(",");
		for (int i = 0; i < aa.length; i++) {
			String code = aa[i];
			String[] bb = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(",");
			for (int j = 0; j < bb.length; j++) {
				code += bb[j];
				String[] cc = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(",");
				for (int k = 0; k < cc.length; k++) {
					code += cc[k];
					String[] dd = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(",");
					for (int m = 0; m < cc.length; m++) {
						code += dd[m];
						System.out.println("vote code=" + code);
						try {
							//vote
						} catch (Exception e) {
							e.printStackTrace();
						}
						code = code.substring(0, 3);
					}
					code = code.substring(0, 2);
				}
				code = code.substring(0, 1);
			}
		}
	}

}
