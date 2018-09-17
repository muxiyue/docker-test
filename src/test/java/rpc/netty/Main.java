package rpc.netty;

public class Main {
    public static void main(String [] args){
		HelloRpc helloRpc = new HelloRpcImpl();
		helloRpc = RPCProxy.create(helloRpc);
		System.err.println(helloRpc.hello("rpc"));
	}
}
