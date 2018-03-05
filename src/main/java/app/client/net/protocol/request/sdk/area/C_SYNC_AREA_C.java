package app.client.net.protocol.request.sdk.area;

import app.client.net.annotation.Protocol;
import app.client.net.protocol.ProtocolType;
import app.client.net.protocol.RequestProtocol;
import app.client.net.protocol.request.sdk.vo.AddAreaInfoVo;
import app.client.net.protocol.request.sdk.vo.AddFloorInfoVo;
import com.gowild.core.util.StringUtil;
import com.gowild.protocol.Device2SdkTcpProtocol;
import com.gowild.protocol.SdkMsgType;
import com.gowild.sdktcp.metadata.pb.SdkUploadMsgProto;

import java.util.List;


@Protocol(moduleId = SdkMsgType.SDK_DEVICE_CLIENT_TYPE, sequenceId = Device2SdkTcpProtocol.SDK_SYNC_AREA_C, type = ProtocolType.REQUSET)
public class C_SYNC_AREA_C extends RequestProtocol{

    private List<AddAreaInfoVo> syncAreaInfoVoList;

    @Override
    public void writeBinaryData(){

        SdkUploadMsgProto.SdkAddAreaMsg.Builder build = SdkUploadMsgProto.SdkAddAreaMsg.newBuilder();
        for(AddAreaInfoVo addAreaInfoVo : syncAreaInfoVoList){
            SdkUploadMsgProto.SdkAddAreaInfo.Builder second = SdkUploadMsgProto.SdkAddAreaInfo.newBuilder();
            second.setAreaId(addAreaInfoVo.getAreaId());
            second.setAreaName(addAreaInfoVo.getAreaName());
            String bindFloorId = addAreaInfoVo.getBindFloorId();
            if (!StringUtil.isNullOrEmpty(bindFloorId)) {
                second.setFloorId(addAreaInfoVo.getBindFloorId());
            }
            build.addAreaList(second.build());
        }
        byte[] bytes = build.build().toByteArray();
        for(byte b : bytes){
            writeByte(b);
        }
    }

    public List<AddAreaInfoVo> getSyncAreaInfoVoList() {
        return syncAreaInfoVoList;
    }

    public void setSyncAreaInfoVoList(List<AddAreaInfoVo> syncAreaInfoVoList) {
        this.syncAreaInfoVoList = syncAreaInfoVoList;
    }
}
