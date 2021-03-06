package app.client.net.protocol.request.sdk.batch.device;

import app.client.net.annotation.Protocol;
import app.client.net.protocol.ProtocolType;
import app.client.net.protocol.RequestProtocol;
import com.gowild.sdk.protocol.Device2TcpProtocol;
import com.gowild.sdk.protocol.SdkMsgType;
import com.gowild.sdk.metadata.pb.BaseBothMsgProto;



@Protocol(moduleId = SdkMsgType.XB_CLIENT_TYPE, sequenceId = Device2TcpProtocol.SDK_GET_ALL_DATA_BY_XB_MAC_C, type = ProtocolType.REQUSET)
public class C_GET_ALL_XB_BIND_MASTER extends RequestProtocol{

    private String xbMac;

    @Override
    public void writeBinaryData(){
        BaseBothMsgProto.StringMsg.Builder build = BaseBothMsgProto.StringMsg.newBuilder();
        build.setValue(xbMac);
        byte[] bytes = build.build().toByteArray();
        for(byte b : bytes){
            writeByte(b);
        }
    }

    public String getXbMac() {
        return xbMac;
    }

    public void setXbMac(String xbMac) {
        this.xbMac = xbMac;
    }
}
