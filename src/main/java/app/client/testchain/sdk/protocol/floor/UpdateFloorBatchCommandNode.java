package app.client.testchain.sdk.protocol.floor;

import app.client.net.protocol.ProtocolFactory;
import app.client.net.protocol.request.sdk.batch.floor.C_UPDATE_FLOOR_BATCH;
import app.client.vo.AddFloorInfoVo;
import app.client.testchain.ProtocolListenNode;
import app.client.testchain.sdk.SdkTestConst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zh on 2018/2/27.
 */
public class UpdateFloorBatchCommandNode extends ProtocolListenNode {
    @Override
    public void doExecute() {

        List<AddFloorInfoVo> updateFloorInfoVoList = new ArrayList<>();

        String firstFloorTid = SdkTestConst.FIRST_FLOOR_TID;

        String firstFloorName = SdkTestConst.FIRST_FLOOR_NAME;

        AddFloorInfoVo addFloorInfoVo1 = new AddFloorInfoVo();
        addFloorInfoVo1.setFloorId(firstFloorTid);
        addFloorInfoVo1.setFloorName(firstFloorName);

        String secondFloorTid = SdkTestConst.SECOND_FLOOR_TID;
        String secondFloorName = SdkTestConst.SECOND_FLOOR_NAME;

        AddFloorInfoVo addFloorInfoVo2 = new AddFloorInfoVo();
        addFloorInfoVo2.setFloorId(secondFloorTid);
        addFloorInfoVo2.setFloorName(secondFloorName);

        updateFloorInfoVoList.add(addFloorInfoVo1);
        updateFloorInfoVoList.add(addFloorInfoVo2);


        C_UPDATE_FLOOR_BATCH protocol = ProtocolFactory.createRequestProtocol(C_UPDATE_FLOOR_BATCH.class,
                userSession.getCtx());
        protocol.setUpdateHomeInfoVoList(updateFloorInfoVoList);
        userSession.sendMsg(protocol);
    }

    @Override
    public boolean canExecuteImmediately(){
        return true;
    }
}
