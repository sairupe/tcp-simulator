package app.client.testchain.sdk.protocol.scene;

import app.client.net.protocol.ProtocolFactory;
import app.client.net.protocol.request.sdk.batch.scene.C_UPDATE_SCENE_BATCH;
import app.client.testchain.ProtocolListenNode;
import app.client.testchain.sdk.SdkTestConst;
import com.gowild.sdk.vo.db.AddSceneInfoVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zh on 2018/2/27.
 */
public class UpdateSceneBatchCommandNode extends ProtocolListenNode {
    @Override
    public void doExecute() {

        List<AddSceneInfoVo> updateSceneInfoVoList = new ArrayList<>();

        String firstSceneTid = SdkTestConst.FIRST_SCENE_TID;
        String secondSceneTid = SdkTestConst.SECOND_SCENE_TID;
        String firstSceneName = SdkTestConst.FIRST_SCENE_NAME;
        String secondSceneName = SdkTestConst.SECOND_SCENE_NAME;

        AddSceneInfoVo addSceneInfoVo1 = new AddSceneInfoVo();
        addSceneInfoVo1.setSceneTid(firstSceneTid);
        addSceneInfoVo1.setSceneName(firstSceneName);

        AddSceneInfoVo addSceneInfoVo2 = new AddSceneInfoVo();
        addSceneInfoVo2.setSceneTid(secondSceneTid);
        addSceneInfoVo2.setSceneName(secondSceneName);

        updateSceneInfoVoList.add(addSceneInfoVo1);
        updateSceneInfoVoList.add(addSceneInfoVo2);


        C_UPDATE_SCENE_BATCH protocol = ProtocolFactory.createRequestProtocol(C_UPDATE_SCENE_BATCH.class,
                userSession.getCtx());
        protocol.setUpdateSceneInfoVoList(updateSceneInfoVoList);
        userSession.sendMsg(protocol);
    }

    @Override
    public boolean canExecuteImmediately(){
        return true;
    }
}
