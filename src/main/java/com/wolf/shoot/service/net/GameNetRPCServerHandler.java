package com.wolf.shoot.service.net;

import com.wolf.shoot.common.constant.Loggers;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;

import java.util.Map;

/**
 * Created by jwp on 2017/3/7.
 */
public class GameNetRPCServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = Loggers.rpcLogger;

    @Override
    public void channelRead0(final ChannelHandlerContext ctx,final RpcRequest request) throws Exception {
//        RpcServer.submit(new Runnable() {
//            @Override
//            public void run() {
//                LOGGER.debug("Receive request " + request.getRequestId());
//                RpcResponse response = new RpcResponse();
//                response.setRequestId(request.getRequestId());
//                try {
//                    Object result = handle(request);
//                    response.setResult(result);
//                } catch (Throwable t) {
//                    response.setError(t.toString());
//                    LOGGER.error("RPC Server handle request error",t);
//                }
//                ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
//                    @Override
//                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                        LOGGER.debug("Send response for request " + request.getRequestId());
//                    }
//                });
//            }
//        });
    }

    private Object handle(RpcRequest request) throws Throwable {
//        String className = request.getClassName();
//        Object serviceBean = handlerMap.get(className);
//
//        Class<?> serviceClass = serviceBean.getClass();
//        String methodName = request.getMethodName();
//        Class<?>[] parameterTypes = request.getParameterTypes();
//        Object[] parameters = request.getParameters();
//
//        logger.debug(serviceClass.getName());
//        logger.debug(methodName);
//        for (int i = 0; i < parameterTypes.length; ++i) {
//            logger.debug(parameterTypes[i].getName());
//        }
//        for (int i = 0; i < parameters.length; ++i) {
//            logger.debug(parameters[i].toString());
//        }
//        // Cglib reflect
//        FastClass serviceFastClass = FastClass.create(serviceClass);
//        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
//        return serviceFastMethod.invoke(serviceBean, parameters);
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("server caught exception", cause);
        ctx.close();
    }
}

