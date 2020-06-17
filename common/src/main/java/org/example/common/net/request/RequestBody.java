package org.example.common.net.request;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author haifeng.pang [panghaifeng@weidian.com]
 * @version 1.0 : RequestBody v0.1 2020/6/16 17:22 haifeng.pang Exp $
 **/
public class RequestBody implements Serializable {

    /**
     * for serialization
     */
    private static final long serialVersionUID = -1288207208017808618L;

    /**
     * id
     */
    private String id;

    /**
     * msg
     */
    private String msg;

    /**
     * body
     */
    private byte[] body;

    public RequestBody() {
    }

    public RequestBody(String id, String msg, byte[] body) {
        this.id = id;
        this.msg = msg;
        this.body = body;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
