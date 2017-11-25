package app.client.testchain;

import app.client.net.protocol.ResponseProtocol;
import app.client.user.session.UserSession;
import com.gowild.basic.exception.ResponseCode;

import java.sql.Connection;

/**
 * Created by zh on 2017/11/21.
 */
public interface IChainNode {

    public void start(UserSession userSession, Connection connection);

    public void execute();

    public void doExecute();

    public void end();

    public AbstractChainNode registListenProtocol(Class<? extends ResponseProtocol> listenningPotocol);

    public void sniff(ResponseProtocol responseProtocol);

    public void addLastNext(AbstractChainNode chainNode);

    public IChainNode next();

    public void next(IChainNode nextNode);

    public boolean canExecuteImmediately();

    public void setVar(UserSession userSession, Connection connection);

}