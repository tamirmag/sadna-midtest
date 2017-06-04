package ServerClient;
//package ServiceLayer;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ServiceLayer.ServiceUser;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
//import io.vertx.core.json.JsonObject;

public class Http_Client extends AbstractVerticle {
    static String address="132.72.226.127";//in nofar computer
    //static String address="192.168.56.1";
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        //HttpClient httpClient = vertx.createHttpClient();
    }


    public static void  register(String username, String password, String email, int wallet) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        System.out.println("new user http - I'm HERE!! in client\n");
        HttpClient httpClient = vertx.createHttpClient();
        //httpClient.get("132.72.226.127: 8081/register/moar/123/m@gmail.com/1");
        httpClient.getNow(8081, address, "/register/"+username+"/"+password+"/"+email+"/"+wallet, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        String s=buffer.getString(0, buffer.length());
                        //JsonObject js2=new JsonObject(s);
                        //ServiceUser su=(ServiceUser)js2.getValue("key");
                        Gson gson=new GsonBuilder().create();
                        ServiceUser p=gson.fromJson(s,ServiceUser.class);
                        System.out.println("user:"+p.getUsername()+" pass:"+p.getPassword());
                    }
                });
            }
        });
    }

    public static void  editProfile(String username, String password, String email) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/editProfile/"+username+"/"+password+"/"+email, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        String s=buffer.getString(0, buffer.length());
                        //JsonObject js2=new JsonObject(s);
                        //ServiceUser su=(ServiceUser)js2.getValue("key");
                        Gson gson=new GsonBuilder().create();
                        ServiceUser p=gson.fromJson(s,ServiceUser.class);
                        System.out.println("user:"+p.getUsername()+" pass:"+p.getPassword());
                    }
                });
            }
        });
    }

    public static void  logout(String username) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/logout/"+username, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }

    public static void login(String username, String password) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/login/"+username+"/"+password, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        String s=buffer.getString(0, buffer.length());
                        //JsonObject js2=new JsonObject(s);
                        //ServiceUser su=(ServiceUser)js2.getValue("key");
                        Gson gson=new GsonBuilder().create();
                        ServiceUser p=gson.fromJson(s,ServiceUser.class);
                        System.out.println("user:"+p.getUsername()+" pass:"+p.getPassword());
                    }
                });
            }
        });
    }

    public static void findSpectatableGames(String username) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        //ArrayList<Integer>[] p1 ;//= new ArrayList<>[1];
        //final Integer[][] p1 = new Integer[1][1];
        httpClient.getNow(8081,address, "/findSpectatableGames/"+username, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        String s=buffer.getString(0, buffer.length());
                        //JsonObject js2=new JsonObject(s);
                        //ServiceUser su=(ServiceUser)js2.getValue("key");
                        Gson gson=new GsonBuilder().create();
                        ArrayList<Integer> p = gson.fromJson(s,new TypeToken<ArrayList<Integer>>(){}.getType());
                        //p1[0] = new Integer[p.size()];
                        //p1[0] = (Integer[]) p.toArray();
                        for(int i = 0; i< p.size(); i++){
                            System.out.print(p.get(i)+" ");
                        }

                        System.out.println();
                    }
                });
            }
        });
        //return p1[0][0];
    }

    public static void  createGame(String username, String gameType, int BuyInPolicy, int ChipPolicy, int minimumBet,
                                   int minimalAmountOfPlayers, int maximalAmountOfPlayers, boolean spectatingMode) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/createGame/"+username+"/"+ gameType+"/"+ BuyInPolicy+"/"+ ChipPolicy+"/"+
                minimumBet+"/"+ minimalAmountOfPlayers+"/"+ maximalAmountOfPlayers+"/"+ spectatingMode, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        String s=buffer.getString(0, buffer.length());
                        int ans=Integer.parseInt(s);
                        System.out.println("answer:"+ans);
                    }
                });
            }
        });
    }

    public static void joinGame(int gamenum, String username) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081,address, "/joinGame/"+gamenum+"/"+username, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }

    public static void spectateGame(int gamenum, String username) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/spectateGame/"+gamenum+"/"+username, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }


    public static void viewReplay(int gamenum, String username) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/viewReplay/"+gamenum+"/"+username, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        String s=buffer.getString(0, buffer.length());
                        //JsonObject js2=new JsonObject(s);
                        //ServiceUser su=(ServiceUser)js2.getValue("key");
                        Gson gson=new GsonBuilder().create();
                        ArrayList<String> p=gson.fromJson(s,new TypeToken<ArrayList<String>>(){}.getType());
                        for(int i=0;i<p.size();i++){
                            System.out.print(p.get(i)+" ");
                        }

                        System.out.println();
                    }
                });
            }
        });
    }

    public static void findActiveGamesByPotSize(int potSize, String username) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/findActiveGamesByPotSize/"+potSize+"/"+username, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        String s=buffer.getString(0, buffer.length());
                        //JsonObject js2=new JsonObject(s);
                        //ServiceUser su=(ServiceUser)js2.getValue("key");
                        Gson gson=new GsonBuilder().create();
                        ArrayList<Integer> p=gson.fromJson(s,new TypeToken<ArrayList<Integer>>(){}.getType());
                        for(int i=0;i<p.size();i++){
                            System.out.print(p.get(i)+" ");
                        }

                        System.out.println();
                    }
                });
            }
        });
    }

    public static void check(String username, int gameID) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/check/"+username+"/"+gameID, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }

    public static void bet(String username, int gameID, int amount) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081,address, "/bet/"+username+"/"+gameID+"/"+amount, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }

    public static void raise(String username, int gameID, int amount) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/raise/"+username+"/"+gameID+"/"+amount, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }

    public static void allIn(String username, int gameID) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/allIn/"+username+"/"+gameID, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }

    public static void fold(String username, int gameID) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/fold/"+username+"/"+gameID, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }

    public static void terminateGame(int gameID) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/terminateGame/"+gameID, new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }

    public static void clearLoggedInUsers() throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/clearLoggedInUsers/", new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }

    public static void clearUsers() throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, address, "/clearUsers/", new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }

    public static void clearAllFinishedGameLogs() throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Client());
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081,address, "/clearAllFinishedGameLogs/", new Handler<HttpClientResponse>() {

            @Override
            public void handle(HttpClientResponse httpClientResponse) {
                httpClientResponse.bodyHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        System.out.println("Response (" + buffer.length() + "): ");
                        System.out.println(buffer.getString(0, buffer.length()));
                    }
                });
            }
        });
    }
}
