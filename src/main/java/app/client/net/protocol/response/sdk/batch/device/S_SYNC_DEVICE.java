package app.client.net.protocol.response.sdk.batch.device;

import app.client.net.annotation.Protocol;
import app.client.net.protocol.ProtocolType;
import app.client.net.protocol.ResponseProtocol;
import com.google.protobuf.InvalidProtocolBufferException;
import com.gowild.sdk.metadata.pb.SdkBothMsgProto;
import com.gowild.sdk.protocol.SdkMsgType;
import com.gowild.sdk.protocol.Tcp2DeviceProtocol;

@Protocol(moduleId = SdkMsgType.XB_CLIENT_TYPE, sequenceId = Tcp2DeviceProtocol.SDK_SYNC_DEVICE_RESULT_S, type = ProtocolType.RESPONSE)
public class S_SYNC_DEVICE extends ResponseProtocol{

    SdkBothMsgProto.SdkCommonResponseMsg commonResponseMsg;

    @Override
    public boolean readBinaryData(){
        try {
            commonResponseMsg = SdkBothMsgProto.SdkCommonResponseMsg.parseFrom(buffer.array());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return true;
    }

    public SdkBothMsgProto.SdkCommonResponseMsg getCommonResponseMsg() {
        return commonResponseMsg;
    }
}