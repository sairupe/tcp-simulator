package app.client.net.protocol.response;

import app.client.net.annotation.Protocol;
import app.client.net.protocol.ProtocolType;
import app.client.net.protocol.ResponseProtocol;
import com.google.protobuf.InvalidProtocolBufferException;
import com.gowild.protocol.SdkMsgType;
import com.gowild.protocol.SdkTcp2DeviceProtocol;
import com.gowild.sdktcp.metadata.pb.SdkBothMsgProto;

@Protocol(moduleId = SdkMsgType.SDK_DEVICE_CLIENT_TYPE, sequenceId = SdkTcp2DeviceProtocol.SDK_DEVICE_HEART_BEAT_S, type = ProtocolType.RESPONSE)
public class S_DEVICE_HEART_BEAT extends ResponseProtocol{

    SdkBothMsgProto.SdkDeviceHeartBeatMsg heartBeatMsg;

    @Override
    public boolean readBinaryData(){
        try {
            heartBeatMsg = SdkBothMsgProto.SdkDeviceHeartBeatMsg.parseFrom(buffer.array());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return true;
    }

    public SdkBothMsgProto.SdkDeviceHeartBeatMsg getHeartBeatMsg() {
        return heartBeatMsg;
    }
}