package app.client.testchain;

import app.client.net.protocol.ResponseProtocol;

/**
 * Created by zh on 2017/11/21.
 */
public abstract class ProtocolListenNode extends AbstractChainNode{
    @Override
    public boolean canExecuteImmediately(){
        return false;
    }

}
