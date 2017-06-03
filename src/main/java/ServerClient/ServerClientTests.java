package ServerClient;
import io.vertx.core.Vertx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class ServerClientTests {
    
	 Vertx vertx;
	 HttpServer server;
	 String address="192.168.56.1";
	 
	 @Before
	  public void before(TestContext context) {
	    vertx = Vertx.vertx();
	    //vertx.deployVerticle(new Http_Server());
	    //server = vertx.createHttpServer().listen(8081, "132.72.209.150");
	    server =
	    	      vertx.createHttpServer().requestHandler(req -> req.response().end("action successful")).
	    	          listen(8081, context.asyncAssertSuccess());
	  }
	 
	 @After
	  public void after(TestContext context) {
	    vertx.close(context.asyncAssertSuccess());
	  }
	  
	  @Test
	  public void test1(TestContext context) {
	    // Send a request and get a response
	    HttpClient client = vertx.createHttpClient();
	    Async async = context.async();
	    client.getNow(8081, address, "/register/morrrt/bla/a@gmail.com/1", resp -> {
	      resp.bodyHandler(body -> {
	        context.assertEquals("action successful", body.toString());
	        client.close();
	        async.complete();
	      });
	    });
	  }
    
	  @Test
	  public void test2(TestContext context) {
	    // Send a request and get a response
	    HttpClient client = vertx.createHttpClient();
	    Async async = context.async();
	    client.getNow(8081, address, "/register/mortz/blaa/aa@gmail.com/12", resp -> {
	      resp.bodyHandler(body -> {
	        context.assertEquals("action successful", body.toString());
	        client.close();
	        async.complete();
	      });
	    });
	  }
}
