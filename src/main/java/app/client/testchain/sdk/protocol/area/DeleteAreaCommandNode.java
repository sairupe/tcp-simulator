package app.client.testchain.sdk.protocol.area;

import app.client.net.protocol.ProtocolFactory;
import app.client.net.protocol.request.sdk.batch.area.C_DELETE_AREA;
import app.client.testchain.ProtocolListenNode;
import app.client.testchain.sdk.SdkTestConst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zh on 2018/2/27.
 */
public class DeleteAreaCommandNode extends ProtocolListenNode {
    @Override
    public void doExecute() {
        String firstAreaId = SdkTestConst.FIRST_AREA_TID;
        String secondAreaId = SdkTestConst.SECOND_AREA_TID;
        List<String> deleteAreaIdList = new ArrayList<>();
        deleteAreaIdList.add(firstAreaId);
        deleteAreaIdList.add(secondAreaId);

        C_DELETE_AREA protocol = ProtocolFactory.createRequestProtocol(C_DELETE_AREA.class,
                userSession.getCtx());
        protocol.setDeleteAreaIdList(deleteAreaIdList);
        userSession.sendMsg(protocol);
    }

    @Override
    public boolean canExecuteImmediately(){
        return true;
    }
}
