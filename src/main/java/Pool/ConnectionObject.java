package Pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

abstract public class ConnectionObject {
    private List<Connection> sessionMounted;
    private List<Connection> usedSession;
    private int nConnection;

    public ConnectionObject(int n) {
        nConnection = n;
        sessionMounted = new ArrayList<>();
        usedSession = new ArrayList<>();
    }

    protected abstract Connection create() throws SQLException;

    public abstract boolean validate(Connection con) throws SQLException;

    public synchronized Connection checkOut() throws SQLException {
        Connection con = null;
        if(sessionMounted.size() > 0) {
            List<Connection> garbege = new ArrayList<>();
            Iterator e = sessionMounted.iterator();
            while (e.hasNext()) {
                con = (Connection) e.next();
                if(false){
//                    sessionMounted.remove(con);
                    con = null;
                } else {
                    if (validate(con)) {

//                        sessionMounted.remove(sessionMounted.indexOf(con));
                        garbege.add(con);
                        usedSession.add(con);
                    } else {
                        // object failed validation
                        garbege.add(con);
//                        sessionMounted.remove(sessionMounted.indexOf(con));
                        con = null;
                    }
                }
            }
            sessionMounted.removeAll(garbege);
        }
        else {
            for (int i = 0; i < nConnection; i++) {
                Connection init = create();
                if(i == 0) {
                    con = init;
                    usedSession.add(init);
                } else
                    sessionMounted.add(init);

            }
        }
        return (con);
    }
    public synchronized void checkIn(Connection con) {
//        expire(con);
        System.out.println(con);
        usedSession.remove(usedSession.indexOf(con));
        sessionMounted.add(con);
    }
    public abstract void expire(Connection o);

    public int getSize() {
        return sessionMounted.size();
    }
}
