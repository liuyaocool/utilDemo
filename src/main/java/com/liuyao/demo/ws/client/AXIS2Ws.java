package com.liuyao.demo.ws.client;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import javax.xml.namespace.QName;

public class AXIS2Ws {

    public static void main(String[] args) throws AxisFault {

        String value = "";
        String wsdlUrl = "";
        String nameSpace = "";
        String methodName = "";
        String returnStr = "" ;// 返回参数
        // 使用RPC方式调用WebService  
        RPCServiceClient rpcServiceClient = new RPCServiceClient();
        Options options = rpcServiceClient.getOptions();
        OMElement om = null;
        // 指定调用WebService的URL  
        EndpointReference to = new EndpointReference(wsdlUrl);
        options.setTo(to);
        // 指定方法返回值的数据类型的Class对象  
        Class[] classes = new Class[]{String.class};
        // 指定要调用的方法及WSDL文件的命名空间  
        QName opAddEntry = new QName(nameSpace, methodName);
        // 指定getGreeting方法的参数值  
        Object[] opAddEntryArgs = new Object[]{value};
        om = rpcServiceClient.invokeBlocking(opAddEntry, opAddEntryArgs);


        returnStr = om.toString();
        System.out.println(returnStr);
    }

//    public static String invokeGetGreeting(String value , String wsdlUrl , String nameSpace , String methodName) throws AxisFault
//    {  
//      String returnStr = "" ;  // 返回参数
//     
//      // 使用RPC方式调用WebService  
//         RPCServiceClient rpcServiceClient = new RPCServiceClient();  
//         Options options = rpcServiceClient.getOptions();  
//        
//         OMElement om = null;
//        
//         // 指定调用WebService的URL  
//         EndpointReference to = new EndpointReference(wsdlUrl);  
//         options.setTo(to);  
//        
//         // 指定方法返回值的数据类型的Class对象  
//         Class[] classes = new Class[]{String.class};  
//        
//         // 指定要调用的方法及WSDL文件的命名空间  
//         QName opAddEntry = new QName(nameSpace, methodName);
//        
//         // 指定getGreeting方法的参数值  
//         Object[] opAddEntryArgs = new Object[]{value}; 
//        
//         om = rpcServiceClient.invokeBlocking(opAddEntry, opAddEntryArgs);
//
//         returnStr = om.toString();
//        
//         return returnStr ;
//    }  
}
