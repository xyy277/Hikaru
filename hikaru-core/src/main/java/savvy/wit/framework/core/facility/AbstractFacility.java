package savvy.wit.framework.core.facility;

/***************************
 * Title:
 * File name: AbstractFacility
 * Author: zhoujiajun
 * Date: 2020/1/20 16:09
 * Description:
 ***************************/
public abstract class AbstractFacility implements FacilityCommunication {

    private String host;

    private int port;

    /*
     * 运行状态
     */
    protected boolean running;

    public AbstractFacility(int port) {
        this("127.0.0.1", port);
    }

    public AbstractFacility(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void start() {
        running = true;
    }

    public void close() {
        running = false;
    }




    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
