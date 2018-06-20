/**
 * 
 */
package km.demo;

/**
 * @author Madan Kavarthapu
 *
 */

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * How to send a request via proxy.
 *
 * @since 4.0
 */
public class Main {

    public static void main(String[] args)throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpHost target = new HttpHost("amd-mpcs.saas.appdynamics.com", 443, "https");
            HttpHost proxy = new HttpHost("genproxy.amdocs.com", 8080, "http");

            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            HttpGet request = new HttpGet("/controller/rest/applications/MICROSERVICES/metrics?output=JSON");
            request.setConfig(config);
            
            request.addHeader("Authorization",
    				String.format("Basic %s", new Object[] { "YmFsYWNoYXJAYW1kLW1wY3M6QkFMQUNIQVI=" }));

            System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);

            CloseableHttpResponse response = httpclient.execute(target, request);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}