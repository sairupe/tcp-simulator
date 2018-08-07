package app.client.testchain.sdk.protocol.device;

import app.client.net.protocol.ProtocolFactory;
import app.client.net.protocol.request.sdk.batch.device.C_DEVICE_LOGIN;
import app.client.testchain.ProtocolListenNode;

/**
 * Created by zh on 2017/11/21.
 */
public class SdkLoginCommandNode extends ProtocolListenNode {
    @Override
    public void doExecute() {
        C_DEVICE_LOGIN login = ProtocolFactory.createRequestProtocol(C_DEVICE_LOGIN.class,
                userSession.getCtx());
        login.setUniqueCode("12355");
        login.setDeviceType(12315);
        login.setDeviceId("deviceId");
        login.setLoginTime(12315);
        login.setDeviceSn("12315");
        login.setEncrypCode("12315");
        userSession.sendMsg(login);
    }

    @Override
    public boolean canExecuteImmediately(){
        return true;
    }
}
