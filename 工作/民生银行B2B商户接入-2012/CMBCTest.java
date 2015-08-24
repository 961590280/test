package com.test;

import com.hitrust.B2B.CMBC.request.BindMerchantCertificateRequest;
import com.hitrust.B2B.CMBC.response.GenericResponse;

public class CMBCTest {
		
	
	public static void main(String[] args) {
		BindMerchantCertificateRequest bmc=new BindMerchantCertificateRequest();
		bmc.setMerchantTrnxNo("4213c3769d194243b6ca548d479c50a4");
		bmc.setTrnxCode("B001");
		bmc.setMerchantPassword("1111");
		bmc.setMerchantNo("2004903604");
		bmc.setCertificationInfo("-----BEGIN CERTIFICATE-----"+
			"MIIDpjCCAw+gAwIBAgIQWmsEDWD+OZdZ1RdBEruuvjANBgkqhkiG9w0BAQUFADAq"
		+"	 MQswCQYDVQQGEwJDTjEbMBkGA1UEChMSQ0ZDQSBPcGVyYXRpb24gQ0EyMB4XDTEw"
		+"	MTIyOTA4MTA0OVoXDTE1MTIyODA4MTA0OVowdzELMAkGA1UEBhMCQ04xGzAZBgNV"
			+"BAoTEkNGQ0EgT3BlcmF0aW9uIENBMjENMAsGA1UECxMEQ01CQzESMBAGA1UECxMJ"
			+"Q3VzdG9tZXJzMSgwJgYDVQsQDFB9DTUJDQDIyMDEwMTIyOTIwMTAxMjI5QDAwMDAw"
			+"MDAxMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDa6RFQGJezpjbl0ZF6bpoG "+
			"jIkhDOcpZgIc0XDtsaBN0Uct8rU75lQqhLvPKI60Fe2IqoVECMX+3UClYddOhPW6"+
			"n/JZbEL3KQuyaDJf/HJ3uHk27ryFge6vj1woBSKShupj9uEBHBbx8X275IXODnpG"+
			"kH72JiG2LNND1y+tTp5izwIDAQABo4IBfjCCAXowHwYDVR0jBBgwFoAU8I3ts0G7"+
			"++8IHlUCwzE37zwUTs0wHQYDVR0OBBYEFDNFdgAECyFovVLhesV+1KVqCgFTMAsG"+
			"A1UdDwQEAwIF4DAMBgNVHRMEBTADAQEAMB0GA1UdJQQWMBQGCCsGAQUFBwMCBggr"+
			"BgEFBQcDBDCB/QYDVR0fBIH1MIHyMFagVKBSpFAwTjELMAkGA1UEBhMCQ04xGzAZ"+
			"BgNVBAoTEkNGQ0EgT3BlcmF0aW9uIENBMjEMMAoGA1UECxMDQ1JMMRQwEgYDVQQD"+
			"EwtjcmwxMDFfNzMwNzCBl6CBlKCBkYaBjmxkYXA6Ly9jZXJ0ODYzLmNmY2EuY29t"+
			"LmNuOjM4OS9DTj1jcmwxMDFfNzMwNyxPVT1DUkwsTz1DRkNBIE9wZXJhdGlvbiBD"+
			"QTIsQz1DTj9jZXJ0aWZpY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Y2xh"+
			"c3M9Y1JMRGlzdHJpYnV0aW9uUG9pbnQwDQYJKoZIhvcNAQEFBQADgYEANf1I0tIG"+
			"Vc1ZyjD6e4e1ENHK5J2uHjRY8PpcJglZmd+ZpAjyfmQgMiiVxlJUeQxYzgOIxFPh"+
			"WFi1BPT1RXOVefcsaLc/yRE5s7io4GUq0Ceu8pI1zHMA1euOwB1qwS1UbOLRh4lq"+
			"VFU8Ow5W3M2PUfCk5+5i1CNc+zRHG0p0rXI=" +
			"-----END CERTIFICATE-----");
		GenericResponse res=bmc.postRequest();
		String txt=res.getCode();
		String str=res.getMessage();
		System.out.println(txt+"=="+str);
			
	}
	
}
