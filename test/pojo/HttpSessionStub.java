/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Collections;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionContext;

public class HttpSessionStub implements HttpSession {
    private boolean invalidated = false;

    @Override
    public void invalidate() {
        this.invalidated = true;
    }

    public boolean isInvalidated() {
        return invalidated;
    }
    @Override
    public long getCreationTime() {
        return 0;
    }

    @Override
    public String getId() {
        return "test-session-id";
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {}

    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public Object getValue(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.emptyEnumeration();
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public void setAttribute(String name, Object value) {}

    @Override
    public void putValue(String name, Object value) {}

    @Override
    public void removeAttribute(String name) {}

    @Override
    public void removeValue(String name) {}

    @Override
    public boolean isNew() {
        return false;
    }
}

